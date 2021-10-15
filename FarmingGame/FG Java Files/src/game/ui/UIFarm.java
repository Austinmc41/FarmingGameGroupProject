package game.ui;

import game.graphics.FarmPlot;
import game.util.Constants;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.Random;


public class UIFarm implements Constants {
    private Text day;
    private StackPane stackDay;

    private Text money;
    private StackPane stackMoney;
    private GridPane plots;
    private VBox moneyAndDate;

    private static int wheatSeeds = 0;
    private static int cornSeeds = 0;
    private static int seedPotato = 0;
    private static int wheatCrops = 0;
    private static int cornCrops = 0;
    private static int potatoCrops = 0;

    private Stage stage;
    private static double mAmount;
    private String difficulty;
    private int difficultyMultiplier;
    private String startingSeed;
    //    private int[] inventoryCount;

    private static int dayCount;

    public UIFarm(String level, String crop) {
        //added
        SCREEN.getChildren().clear();
        difficulty = level;
        startingSeed = crop;
        //
        dayCount = 0;

        setDifficulty();
        setStartingSeeds();
        setDay();
        setMoney(mAmount);
        setPlots();
        setScreen();
        setInventory();
        //        setInventoryArray();

        createGrid(3, 5);
        Constants.updateInventory();

        //added
        SCREEN.getChildren().add(PLAYER); //this puts the player in the game
        Constants.playerKeyFrame(); //allows player to move
        ROOT.getChildren().add(SCREEN);

        //        return screen;
    }

    public UIFarm(double mAmount) {
        //added
        SCREEN.getChildren().clear();
        this.mAmount = mAmount;
        //        this.inventoryCount = inventoryCount;

        dayCount++;
        setDay();
        setMoney(mAmount);
        setPlots();
        setScreen();
        //        updatedArray();
        setInventory();
        //        return screen;

        createGrid(3, 5);
        Constants.updateInventory();

        //added
        SCREEN.getChildren().add(PLAYER); //this puts the player in the game
        Constants.playerKeyFrame(); //allows player to move
        ROOT.getChildren().add(SCREEN);
    }

    //added for updating inventory when harvest button pressed
    public UIFarm() {
        //added
        SCREEN.getChildren().clear();
        //        this.mAmount = mAmount;
        //        this.inventoryCount = inventoryCount;

        //        dayCount++;
        setDay();
        setMoney(mAmount);
        setPlots();
        setScreen();
        //        updatedArray();
        setInventory();
        //        return screen;

        createGrid(3, 5);
        Constants.updateInventory();

        //added
        SCREEN.getChildren().add(PLAYER); //this puts the player in the game
        Constants.playerKeyFrame(); //allows player to move
        ROOT.getChildren().add(SCREEN);
    }


    private void setDay() {
        day = new Text("Day : " + dayCount); //Label for day
        day.setFill(Color.BLACK);
        day.setFont(Font.font("verdana", 15));
        stackDay = new StackPane();
        stackDay.getChildren().addAll(new Rectangle(110, 30, Color.LIGHTGOLDENRODYELLOW), day);
    }

    private void setMoney(double mAmount) {
        money = new Text("Money : " + mAmount); //Label for money
        money.setFill(Color.BLACK);
        money.setFont(Font.font("verdana", 15));
        stackMoney = new StackPane();
        stackMoney.getChildren().addAll(new Rectangle(110, 30, Color.LIGHTGOLDENRODYELLOW), money);
    }

    private void setPlots() {
        plots = new GridPane();
    }

    public void createGrid(int nrows, int ncols) {
        if (FARMPLOTS.size() == 0) {
            createFarmPlots(15);
        }
        double spaceX = 0;
        double spaceY;
        int plotnum = 0;
        for (int col = 0; col < ncols; col++) {
            spaceY = 0;
            for (int row = 0; row < nrows; row++) {
                FarmPlot plot = FARMPLOTS.get(plotnum);
                plot.setX(325 + (col * plot.getFitWidth()) + spaceX);
                plot.setY(140 + (row * plot.getFitHeight()) + spaceY);
                spaceY += 100;

                if (plot.isJustPlanted()) {
                    plot.setDaysAlive(0);
                    plot.setJustPlanted(false);
                    plot.setNewSeedNewDay(true);

                    plot.centerText(9); //still 9 until day is advanced
                    SCREEN.getChildren().addAll(plot, plot.getText(), plot.imageCrop());
                } else if (plot.isNewSeedNewDay() && plot.getGameDayCount() == dayCount) {
                    plot.centerText(9); //still 9 until day is advanced
                    SCREEN.getChildren().addAll(plot, plot.getText(), plot.imageCrop());
                } else if (plot.isNewSeedNewDay() && plot.getGameDayCount() < dayCount) {
                    plot.setDaysAlive(1);
                    plot.setNewSeedNewDay(false);
                    plot.updateCrop();
                    plot.setGameDayCount(dayCount);
                    plot.setHarvMax(false);
                    SCREEN.getChildren().addAll(plot, plot.rtCenterText(), plot.imageCrop());
                } else if (plot.getGameDayCount() < dayCount) {
                    plot.setDaysAlive(plot.getDaysAlive() + 1);
                    plot.setGameDayCount(dayCount);
                    plot.setHarvMax(false);
                    SCREEN.getChildren().addAll(plot, plot.rtCenterText(), plot.imageCrop());
                } else if (plot.getGameDayCount() == dayCount) {
                    SCREEN.getChildren().addAll(plot, plot.rtCenterText(), plot.imageCrop());
                }
                FARMPLOTS.set(plotnum, plot);
                plotnum++;
            }
            spaceX += 100;
        }
    }

    public void createFarmPlots(int count) {
        while (count != 0) {
            FarmPlot plot = new FarmPlot(difficulty);
            FARMPLOTS.add(plot);
            count--;
        }
    }

    private StackPane plantStages(ImageView image) {
        StackPane stackPlot = new StackPane();
        String[] stages = {"Wheat" + System.lineSeparator() + "Seeds", "Corn"
                + System.lineSeparator() + "Seeds", "Potato"
                + System.lineSeparator() + "Seeds", "Mature" + System.lineSeparator()
                + "Wheat Plant", "Mature" + System.lineSeparator() + "Corn Plant", "Mature"
                + System.lineSeparator() + "Potato Plant", "Immature" + System.lineSeparator()
                + "Wheat Plant", "Immature" + System.lineSeparator() + "Corn Plant", "Immature"
                + System.lineSeparator() + "Potato Plant"};
        Random rand = new Random();
        int index = rand.nextInt(9);
        Text text = new Text(stages[index]);
        text.setTextAlignment(TextAlignment.CENTER);
        text.setFill(Color.WHITE);
        text.setFont(Font.font("verdana", 25));
        if (stages[index].equals(stages[3])) {
            Button harvestButton = new Button("Harvest");
            VBox layoutInsidePlot = new VBox(20);
            layoutInsidePlot.setAlignment(Pos.CENTER);
            layoutInsidePlot.getChildren().addAll(text, harvestButton);
            stackPlot.getChildren().addAll(image, layoutInsidePlot);
            harvestButton.setOnAction(e -> {
                wheatCrops++;
                setInventory();
            });
        } else if (stages[index].equals(stages[4])) {
            Button harvestButton = new Button("Harvest");
            VBox layoutInsidePlot = new VBox(20);
            layoutInsidePlot.setAlignment(Pos.CENTER);
            layoutInsidePlot.getChildren().addAll(text, harvestButton);
            stackPlot.getChildren().addAll(image, layoutInsidePlot);
            harvestButton.setOnAction(e -> {
                cornCrops++;
                setInventory();
            });
        } else if (stages[index].equals(stages[5])) {
            Button harvestButton = new Button("Harvest");
            VBox layoutInsidePlot = new VBox(20);
            layoutInsidePlot.setAlignment(Pos.CENTER);
            layoutInsidePlot.getChildren().addAll(text, harvestButton);
            stackPlot.getChildren().addAll(image, layoutInsidePlot);
            harvestButton.setOnAction(e -> {
                potatoCrops++;
                setInventory();
            });
        } else {
            stackPlot.getChildren().addAll(image, text);
        }
        return stackPlot;
    }

    private void setScreen() {

        ImageView imageView = new ImageView("game/res/Grass.jpg");
        imageView.setFitHeight(HEIGHT);
        imageView.setFitWidth(WIDTH);
        imageView.fitHeightProperty().bind(SCREEN.heightProperty());
        imageView.fitWidthProperty().bind(SCREEN.widthProperty());
        SCREEN.getChildren().add(imageView);
        SCREEN.setPrefSize(WIDTH, HEIGHT);
        moneyAndDate = new VBox(10);
        moneyAndDate.getChildren().addAll(stackDay, stackMoney);
        SCREEN.setTop(moneyAndDate);
        BorderPane.setMargin(moneyAndDate, new Insets(40, 15, 15, 15));
        SCREEN.setCenter(plots);
        BorderPane.setMargin(plots, new Insets(10, 15, 15, 15));
    }

    private void setInventory() {
        //inventory
        Inventory invent = new Inventory();
        invent.setAlignment(Pos.CENTER);
        SCREEN.setLeft(invent);
        SCREEN.setMargin(invent, new Insets(0, 0, 15, 40));
        VBox box1 = new VBox();
        Button quit = new Button("Quit");
        quit.setAlignment(Pos.BOTTOM_RIGHT);
        box1.getChildren().add(quit);
        box1.setAlignment(Pos.BOTTOM_RIGHT);
        SCREEN.setBottom(box1);
        SCREEN.setMargin(box1, new Insets(0, 22, 15, 0));

        quit.setOnAction(e -> Platform.exit());
    }


    private void setDifficulty() {
        if (difficulty.equals("Easy")) {
            mAmount = 100;
            difficultyMultiplier = 1;
        } else if (difficulty.equals("Medium")) {
            mAmount = 50;
            difficultyMultiplier = 1;
        } else {
            mAmount = 25;
            difficultyMultiplier = 1;
        }
    }

    private void setStartingSeeds() {
        if (startingSeed.equals("Wheat")) {
            wheatSeeds = 10;
        } else if (startingSeed.equals("Corn")) {
            cornSeeds = 10;
        } else {
            seedPotato = 10;
        }
    }

    //getters
    public static int getDay() {
        return dayCount;
    }
    public static int getWheatSeeds() {
        return wheatSeeds;
    }
    public static int getCornSeeds() {
        return cornSeeds;
    }
    public static int getSeedPotato() {
        return seedPotato;
    }
    public static int getWheatCrops() {
        return wheatCrops;
    }
    public static int getCornCrops() {
        return cornCrops;
    }
    public static int getPotatoCrops() {
        return potatoCrops;
    }
    public static double getmAmount() {
        return mAmount;
    }

    //setters
    public static void setDay(int d) {
        dayCount = d;
    }

    public static void setWheatSeeds(int ws) {
        wheatSeeds = ws;
    }
    public static void setCornSeeds(int cs) {
        cornSeeds = cs;
    }
    public static void setSeedPotato(int sp) {
        seedPotato = sp;
    }
    public static void setWheatCrops(int wc) {
        wheatCrops = wc;
    }
    public static void setCornCrops(int cc) {
        cornCrops = cc;
    }
    public static void setPotatoCrops(int pc) {
        potatoCrops = pc;
    }
    public static void setmAmount(double m) {
        mAmount = m;
    }

    private class Inventory extends VBox {
        private final Font font = Font.font("Bell MT", FontWeight.BOLD, 20);
        private final Font type = Font.font("Bell MT", FontWeight.BOLD, FontPosture.ITALIC, 20);

        public Inventory() {
            getChildren().clear();
            setPrefSize(WIDTH / 7.0, HEIGHT / 8.0);
            setBackground(new Background(new BackgroundFill(Color.LIGHTGOLDENRODYELLOW,
                    CornerRadii.EMPTY, Insets.EMPTY)));
            setOpacity(1);

            //header
            Text header = new Text("Inventory");
            header.setFont(Font.font("Bell MT", FontWeight.BOLD, 33));
            header.setUnderline(true);
            header.setTextAlignment(TextAlignment.JUSTIFY);
            //seeds
            Text seeds = new Text("Seeds");
            seeds.setFont(type);
            Text wheatS = new Text("Wheat Seeds: " + wheatSeeds);
            wheatS.setFont(font);
            Text cornS = new Text("Corn Seeds: " + cornSeeds);
            cornS.setFont(type);
            Text potatoS = new Text("Potato Seeds: " + seedPotato);
            potatoS.setFont(font);

            Text blank = new Text();
            blank.setFont(font);
            Text blank2 = new Text();
            blank2.setFont(font);
            Text blank3 = new Text(System.lineSeparator());
            blank3.setFont(font);
            Text blank4 = new Text(System.lineSeparator() + System.lineSeparator());
            blank4.setFont(font);

            //crops
            Text crops = new Text("Crops");
            crops.setFont(type);
            Text wheatC = new Text("Wheat: " + wheatCrops);
            wheatC.setFont(type);
            Text cornC = new Text("Corn: " + cornCrops);
            cornC.setFont(type);
            Text potatoC = new Text("Potato: " + potatoCrops);
            potatoC.setFont(type);

            //buttons
            HBox bbox = new HBox();
            Button marketButton = new Button("Market");
            marketButton.setMinWidth(WIDTH / 12.5);
            marketButton.setMinHeight(HEIGHT / 12.5);
            bbox.getChildren().add(marketButton);
            bbox.setAlignment(Pos.BOTTOM_CENTER);

            getChildren().addAll(header, blank4, seeds, wheatS, cornS, potatoS,
                    blank, crops, wheatC, cornC, potatoC);
            getChildren().addAll(blank2, blank3, bbox);
            setAlignment(Pos.CENTER);

            marketButton.setOnAction(e -> {
                ROOT.getChildren().remove(SCREEN);
                Market market = new Market(mAmount, 1);
            });
        }

    }






}
