package game.util;

import game.entity.Entity;
import game.entity.Player;
import game.graphics.FarmPlot;
import game.graphics.SpriteSheet;
import game.input.PlayerInputHandler;
import game.ui.UIFarm;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;

import static game.ui.UIFarm.*;

public interface Constants {
    int WIDTH = 1280;
    int HEIGHT = WIDTH / 16 * 9;

    Stage STAGE = new Stage();
    Group ROOT = new Group();
    BorderPane SCREEN = new BorderPane();
    Timeline GAMELOOP = new Timeline();

    int[] INVENTORYCOUNT = new int[]{UIFarm.getWheatSeeds(), UIFarm.getCornSeeds(),
            UIFarm.getSeedPotato(), UIFarm.getWheatCrops(),
            UIFarm.getCornCrops(), UIFarm.getPotatoCrops()};
    ArrayList<Entity> ENTITIES = new ArrayList();
    ArrayList<FarmPlot> FARMPLOTS = new ArrayList<>();
    Font FT = Font.font("Bell MT", FontWeight.BOLD, 30);

    ImageView PL = new SpriteSheet("PlayerSS.png", 16, 18).getIndividualSprite(1, 1);
    Player PLAYER = new Player(PL, 100, 100, 3);

    Pane OPTIONS = new Pane();
    final FarmPlot[] SELECTEDPLOT = new FarmPlot[1];
    Button RET = new Button("Return");
    Button NEWRET = new Button("Return");
    Button HBT = new Button("Harvest");
    Text HARV = new Text("Crop is ready to be harvested:");
    final int[] HARVESTCOUNTER = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    Text MAXH = new Text("Can't harvest crop anymore today.");
    Button WATER = new Button("Water");
    Text PSEEDS = new Text("Ready to plant seeds!");
    Button PLANTBT = new Button("Plant Seeds");
    Button WS = new Button("Wheat Seeds");
    Button CS = new Button("Corn Seeds");
    Button PS = new Button("Potato Seeds");
    final int[] PLOTINDEX = {0};

    static void updateInventory() {
        INVENTORYCOUNT[0] = UIFarm.getWheatSeeds();
        INVENTORYCOUNT[1] = UIFarm.getCornSeeds();
        INVENTORYCOUNT[2] = UIFarm.getSeedPotato();
        INVENTORYCOUNT[3] = UIFarm.getWheatCrops();
        INVENTORYCOUNT[4] = UIFarm.getCornCrops();
        INVENTORYCOUNT[5] = UIFarm.getPotatoCrops();
    }


    static void playerKeyFrame() {
        KeyFrame kf = new KeyFrame(
                Duration.seconds(0.017),  //60 FPS
            e -> {
                PlayerInputHandler in = new PlayerInputHandler(PLAYER);
                if (PLAYER.isIntersects(ENTITIES) || !PLAYER.isIntersects(gameBounds())) {
                    PLAYER.collision();
                }
                if (PLAYER.isIntersectsPlot(FARMPLOTS)) {
                    if (!ROOT.getChildren().contains(OPTIONS)) {
                        OPTIONS.getChildren().clear();
                        int i = 0;
                        while (!FARMPLOTS.get(i).isIntersects()) {
                            i++;
                        }
                        PLOTINDEX[0] = i;
                        SELECTEDPLOT[0] = FARMPLOTS.get(i); //FarmPlot that player collides with
                        if (!(SELECTEDPLOT[0].isHarvMax()) && HARVESTCOUNTER[PLOTINDEX[0]] >= 10) {
                            HARVESTCOUNTER[PLOTINDEX[0]] = 0;
                        }
                        Text cropType = new Text("Crop Type:  " + SELECTEDPLOT[0].getCropType());
                        cropType.setFont(FT);
                        cropType.setFill(Color.WHITE);
                        cropType.setX(10);
                        cropType.setY(30);
                        OPTIONS.getChildren().add(cropType);
                        if ((SELECTEDPLOT[0].canHarvest()) && !(SELECTEDPLOT[0].isHarvMax())) {
                            HARV.setText("Crop is ready to be harvested:");
                            HARV.setFont(FT);
                            HARV.setFill(Color.WHITE);
                            HARV.setX(10);
                            HARV.setY(110);
                            HBT.setTranslateX(425);
                            HBT.setTranslateY(90);
                            HBT.setScaleX(1.5);
                            HBT.setScaleY(1.5);
                            OPTIONS.getChildren().addAll(HARV, HBT);
                        } else {
                            if (OPTIONS.getChildren().contains(MAXH)) {
                                OPTIONS.getChildren().remove(MAXH);
                            }
                            HARV.setText("Crop is not ready to be harvested!");
                            HARV.setFont(FT);
                            HARV.setFill(Color.WHITE);
                            HARV.setX(10);
                            HARV.setY(110);
                            OPTIONS.getChildren().add(HARV);
                        }
                        WATER.setScaleX(1.5);
                        WATER.setScaleY(1.5);
                        WATER.setTranslateX(425);
                        WATER.setTranslateY(30);
                        Text waterLvl = new Text();
                        if (SELECTEDPLOT[0].getWaterLevel() == -2) {
                            waterLvl.setText("Water Level = " + SELECTEDPLOT[0].getWaterLevel()
                                    + " |Bruh, I'm thirsty. Give me water|");
                        } else if (SELECTEDPLOT[0].getWaterLevel() == 2) {
                            waterLvl.setText("Water Level = " + SELECTEDPLOT[0].getWaterLevel()
                                    + " |At max water level. Wait a couple of days|");
                        } else if (SELECTEDPLOT[0].getWaterLevel() < -2) {
                            waterLvl.setText("Water Level = " + SELECTEDPLOT[0].getWaterLevel()
                                    + " |You killed your plant with too little water|");
                        } else if (SELECTEDPLOT[0].getWaterLevel() > 2) {
                            waterLvl.setText("Water Level = " + SELECTEDPLOT[0].getWaterLevel()
                                    + " |You killed your plant with too much water|");
                        } else {
                            waterLvl.setText("Water Level = " + SELECTEDPLOT[0].getWaterLevel()
                                    + " |In optimal water level range|");
                        }
                        waterLvl.setFont(FT);
                        waterLvl.setFill(Color.WHITE);
                        waterLvl.setX(500);
                        waterLvl.setY(50);
                        OPTIONS.getChildren().addAll(WATER, waterLvl);
                        WATER.setOnAction(waterE -> {
                            OPTIONS.getChildren().removeAll(WATER, waterLvl);
                            SELECTEDPLOT[0].setWaterLevel(SELECTEDPLOT[0].getWaterLevel() + 1);
                            if (SELECTEDPLOT[0].getWaterLevel() == -2) {
                                waterLvl.setText("Water Level = " + SELECTEDPLOT[0].getWaterLevel()
                                        + " |Bruh, I'm thirsty. Give me water|");
                            } else if (SELECTEDPLOT[0].getWaterLevel() == 2) {
                                waterLvl.setText("Water Level = " + SELECTEDPLOT[0].getWaterLevel()
                                        + " |At max water level. Wait a couple of days|");
                            } else if (SELECTEDPLOT[0].getWaterLevel() < -2) {
                                waterLvl.setText("Water Level = " + SELECTEDPLOT[0].getWaterLevel()
                                        + " |You killed your plant with too little water|");
                            } else if (SELECTEDPLOT[0].getWaterLevel() > 2) {
                                waterLvl.setText("Water Level = " + SELECTEDPLOT[0].getWaterLevel()
                                        + " |You killed your plant with too much water|");
                            } else {
                                waterLvl.setText("Water Level = " + SELECTEDPLOT[0].getWaterLevel()
                                        + " |In optimal water level range|");
                            }
                            OPTIONS.getChildren().addAll(WATER, waterLvl);
                        });
                        if (SELECTEDPLOT[0].getCropType().contains("Dead")
                                && (UIFarm.getWheatSeeds() > 0 || UIFarm.getCornSeeds() > 0
                                || UIFarm.getSeedPotato() > 0)
                                && !SELECTEDPLOT[0].isNewSeedNewDay()) {
                            PSEEDS.setFill(Color.WHITE);
                            PSEEDS.setFont(FT);
                            PSEEDS.setX(10);
                            PSEEDS.setY(190);
                            PLANTBT.setTranslateX(315);
                            PLANTBT.setTranslateY(167);
                            PLANTBT.setScaleX(1.5);
                            PLANTBT.setScaleY(1.5);
                            OPTIONS.getChildren().addAll(PSEEDS, PLANTBT);
                        } else if (SELECTEDPLOT[0].getCropType().contains("Dead")
                                && UIFarm.getWheatSeeds() == 0 && UIFarm.getCornSeeds() == 0
                                && UIFarm.getSeedPotato() == 0) {
                            Text no = new Text("You're out of Seeds!");
                            no.setFill(Color.WHITE);
                            no.setFont(FT);
                            no.setTranslateX(10);
                            no.setTranslateY(190);
                            OPTIONS.getChildren().add(no);
                        }
                        PLAYER.plotColllision();
                        OPTIONS.setMinWidth(WIDTH);
                        OPTIONS.setMinHeight(HEIGHT);
                        RET.setScaleX(1.7);
                        RET.setScaleY(1.7);
                        RET.setTranslateX(25);
                        RET.setTranslateY(HEIGHT - 90);
                        ColorAdjust adj = new ColorAdjust(0, -0.5, -0.5, 0);
                        GaussianBlur blur = new GaussianBlur(25);
                        adj.setInput(blur);
                        SCREEN.setEffect(adj);
                        RET.setAlignment(Pos.CENTER);
                        OPTIONS.getChildren().add(RET);
                        ROOT.getChildren().add(OPTIONS);
                    }
                    handlers();
                }
            });
        GAMELOOP.getKeyFrames().add(kf);
    }


    static void handlers() {
        RET.setOnMouseClicked(retE -> {
            PLAYER.setMoving(true);
            SCREEN.setEffect(null);
            OPTIONS.getChildren().clear();
            ROOT.getChildren().remove(OPTIONS);
        });
        NEWRET.setOnMouseClicked(retE -> {
            PLAYER.setMoving(true);
            SCREEN.setEffect(null);
            OPTIONS.getChildren().clear();
            ROOT.getChildren().remove(OPTIONS);
            ROOT.getChildren().remove(SCREEN);
            UIFarm farm = new UIFarm();
        });
        HBT.setOnMouseClicked(btE -> {
            HARVESTCOUNTER[PLOTINDEX[0]]++;
            if (HARVESTCOUNTER[PLOTINDEX[0]] > 10) {
                OPTIONS.getChildren().removeAll(HBT, HARV);
                MAXH.setFont(FT);
                MAXH.setFill(Color.WHITE);
                MAXH.setX(10);
                MAXH.setY(110);
                SELECTEDPLOT[0].setHarvMax(true);
                OPTIONS.getChildren().add(MAXH);
            } else {
                if (SELECTEDPLOT[0].getCropType().contains("Wheat")) {
                    int wc = UIFarm.getWheatCrops();
                    wc++;
                    UIFarm.setWheatCrops(wc);
                } else if (SELECTEDPLOT[0].getCropType().contains("Corn")) {
                    int cc = UIFarm.getCornCrops();
                    cc++;
                    UIFarm.setCornCrops(cc);
                } else if (SELECTEDPLOT[0].getCropType().contains("Potato")) {
                    int pc = UIFarm.getPotatoCrops();
                    pc++;
                    UIFarm.setPotatoCrops(pc);
                }
                updateInventory();
            }
            OPTIONS.getChildren().remove(RET);
            if (!OPTIONS.getChildren().contains(NEWRET)) {
                NEWRET.setScaleX(1.7);
                NEWRET.setScaleY(1.7);
                NEWRET.setTranslateX(25);
                NEWRET.setTranslateY(HEIGHT - 90);
                OPTIONS.getChildren().add(NEWRET);
            }
        });
        PLANTBT.setOnMouseClicked(btS -> {
            OPTIONS.getChildren().remove(PSEEDS);
            OPTIONS.getChildren().remove(PLANTBT);
            Text q = new Text("Which seeds would you like to plant?");
            q.setFont(FT);
            q.setFill(Color.WHITE);
            q.setTranslateX(10);
            q.setTranslateY(190);
            if (UIFarm.getWheatSeeds() > 0) {
                Text left = new Text("Wheat Seeds: " + UIFarm.getWheatSeeds());
                left.setFont(Font.font("Bell MT", FontWeight.BOLD, 15));
                left.setFill(Color.WHITE);
                left.setTranslateX(20);
                left.setTranslateY(230);
                WS.setTranslateX(30);
                WS.setTranslateY(245);
                WS.setScaleX(1.5);
                WS.setScaleY(1.5);
                OPTIONS.getChildren().addAll(left, WS);
            }
            if (UIFarm.getCornSeeds() > 0) {
                Text left = new Text("Corn Seeds: " + UIFarm.getCornSeeds());
                left.setFont(Font.font("Bell MT", FontWeight.BOLD, 15));
                left.setFill(Color.WHITE);
                left.setTranslateX(270);
                left.setTranslateY(230);
                CS.setTranslateX(280);
                CS.setTranslateY(245);
                CS.setScaleX(1.5);
                CS.setScaleY(1.5);
                OPTIONS.getChildren().addAll(left, CS);
            }
            if (UIFarm.getSeedPotato() > 0) {
                Text left = new Text("Potato Seeds: " + UIFarm.getSeedPotato());
                left.setFont(Font.font("Bell MT", FontWeight.BOLD, 15));
                left.setFill(Color.WHITE);
                left.setTranslateX(520);
                left.setTranslateY(230);
                PS.setTranslateX(530);
                PS.setTranslateY(245);
                PS.setScaleX(1.5);
                PS.setScaleY(1.5);
                OPTIONS.getChildren().addAll(left, PS);
            }
            OPTIONS.getChildren().add(q);
        });
        WS.setOnMouseClicked(wE -> {
            int ws = UIFarm.getWheatSeeds();
            ws--;
            UIFarm.setWheatSeeds(ws);
            updateInventory();
            FARMPLOTS.get(PLOTINDEX[0]).setSelectionInt(0);
            FARMPLOTS.get(PLOTINDEX[0]).setJustPlanted(true);
            PLAYER.setMoving(true);
            PLAYER.setEffect(null);
            OPTIONS.getChildren().clear();
            ROOT.getChildren().remove(OPTIONS);
            ROOT.getChildren().remove(SCREEN);
            UIFarm farm = new UIFarm();
        });
        CS.setOnMouseClicked(wE -> {
            int cs = UIFarm.getCornSeeds();
            cs--;
            UIFarm.setCornSeeds(cs);
            updateInventory();
            FARMPLOTS.get(PLOTINDEX[0]).setSelectionInt(1);
            FARMPLOTS.get(PLOTINDEX[0]).setJustPlanted(true);
            PLAYER.setMoving(true);
            SCREEN .setEffect(null);
            OPTIONS.getChildren().clear();
            ROOT.getChildren().remove(OPTIONS);
            ROOT.getChildren().remove(SCREEN);
            UIFarm farm = new UIFarm();
        });
        PS.setOnMouseClicked(wE -> {
            int sp = UIFarm.getSeedPotato();
            sp--;
            UIFarm.setSeedPotato(sp);
            updateInventory();
            FARMPLOTS.get(PLOTINDEX[0]).setSelectionInt(2);
            FARMPLOTS.get(PLOTINDEX[0]).setJustPlanted(true);
            PLAYER.setMoving(true);
            SCREEN.setEffect(null);
            OPTIONS.getChildren().clear();
            ROOT.getChildren().remove(OPTIONS);
            ROOT.getChildren().remove(SCREEN);
            UIFarm farm = new UIFarm();
        });
    }

    static Rectangle2D gameBounds() {
        return new Rectangle2D(70, 90, WIDTH - 120, HEIGHT - 175);
    }

    static void flood() {
        if (UIFarm.getDay() == 10) {
            //increase all water plots level to beyond max
            for (int i = 0; i < FARMPLOTS.size(); i++) {
                FARMPLOTS.get(i).setWaterLevel(3);
            }
            Text t = new Text("Breaking News: Flood at FarmLife!");
            t.setFont(FT);
            t.setFill(Color.RED);
            t.setX(500);
            t.setY(50);
            ROOT.getChildren().addAll(t);
        }
    }

    static void drought() {
        if (UIFarm.getDay() == 7) {
            //decrease all water plots level to beyond min
            for (int i = 0; i < FARMPLOTS.size(); i++) {
                FARMPLOTS.get(i).setWaterLevel(-3);
            }
            Text t = new Text("Breaking News: Drought at FarmLife!");
            t.setFont(FT);
            t.setFill(Color.RED);
            t.setX(500);
            t.setY(50);
            ROOT.getChildren().addAll(t);
        }
    }

    static void tornado() {
        if (UIFarm.getDay() == 3) {
            //destroy all the plants - set to dead plants
            for (int i = 0; i < FARMPLOTS.size(); i++) {
                FARMPLOTS.get(i).setCropType("Dead Plant");
            }
            Text t = new Text("Breaking News: Tornado at FarmLife!");
            t.setFont(FT);
            t.setFill(Color.RED);
            t.setX(500);
            t.setY(50);
            ROOT.getChildren().addAll(t);
        }
    }

}
