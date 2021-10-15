package game.ui;

import game.util.Constants;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;



public class Market implements Constants {

    private static final Font FONT = Font.font("Bell MT", FontWeight.BOLD, 22);
    private Stage stage;
    private Text inventory;
    private Text money;
    private double mAmount;
    private int diffMultiplier;
    private double cornSeedPrice;
    private double wheatSeedPrice;
    private double potatoSeedPrice;
    private double cornSellPrice;
    private double wheatSellPrice;
    private double potatoSellPrice;
    private final  double randomVariance = 0.05;



    public Market(double mAmount, int diffMultiplier) {
        //added
        this.mAmount = mAmount;
        this.diffMultiplier = diffMultiplier;

        setItemPrice();
        inventory = new Text("     Inventory " + System.lineSeparator() + System.lineSeparator()
                + "Wheat Seeds: "
                + INVENTORYCOUNT[0] + System.lineSeparator() + "Corn Seeds: "
                + INVENTORYCOUNT[1] + System.lineSeparator()
                + "Potato Seeds: " + INVENTORYCOUNT[2] + System.lineSeparator()
                + System.lineSeparator() + "Wheat: "
                + INVENTORYCOUNT[3] + System.lineSeparator() + "Corn: " + INVENTORYCOUNT[4]
                + System.lineSeparator() + "Potato: " + INVENTORYCOUNT[5]); // Label for inventory
        inventory.setFill(Color.BLACK);
        inventory.setFont(Font.font("verdana", 20));
        inventory.setTranslateY(265);
        inventory.setTranslateX(118);


        money = new Text("Money: " + mAmount); // Label for inventory
        money.setFill(Color.BLACK);
        money.setFont(Font.font("verdana", 20));
        money.setTranslateY(160);
        money.setTranslateX(135);

        Pane pane = new Pane();
        pane.setPrefSize(WIDTH, HEIGHT);
        ImageView image = new ImageView("/game/res/marketN.png");
        image.setFitHeight(HEIGHT);
        image.setFitWidth(WIDTH);

        Button pButton = new Button("Buy Seeds");
        pButton.setMinSize(60, 40);
        Button sButton = new Button("Sell");
        sButton.setMinSize(60, 40);
        Button rButton = new Button("Return To Farm");
        rButton.setMinSize(60, 40);

        HBox buttons = new HBox(15);
        buttons.getChildren().addAll(pButton, sButton, rButton);
        buttons.setTranslateX(65);
        buttons.setTranslateY(550);


        pane.getChildren().addAll(image, buttons, inventory, money);

        //added
        ROOT.getChildren().add(pane);
        //

        pButton.setOnMouseClicked((e) -> {
            ROOT.getChildren().remove(pane);
            purchaseItems();
        });

        sButton.setOnMouseClicked((e) -> {
            ROOT.getChildren().remove(pane);
            sellItems();
        });
        rButton.setOnAction((e) -> {
            ROOT.getChildren().remove(pane);
            UIFarm ui = new UIFarm(mAmount);
        });


        Constants.flood();
        Constants.drought();
        Constants.tornado();
    }

    private void purchaseItems() {
        setItemPrice();
        inventory = new Text("     Inventory " + System.lineSeparator()
                + System.lineSeparator() + "Wheat Seeds: "
                + INVENTORYCOUNT[0] + System.lineSeparator() + "Corn Seeds: "
                + INVENTORYCOUNT[1] + System.lineSeparator()
                + "Potato Seeds: " + INVENTORYCOUNT[2] + System.lineSeparator()
                + System.lineSeparator() + "Wheat: "
                + INVENTORYCOUNT[3] + System.lineSeparator() + "Corn: " + INVENTORYCOUNT[4]
                + System.lineSeparator() + "Potato: " + INVENTORYCOUNT[5]); // Label for inventory
        inventory.setFill(Color.BLACK);
        inventory.setFont(Font.font("verdana", 20));
        inventory.setTranslateY(265);
        inventory.setTranslateX(118);

        money = new Text("Money: " + mAmount); // Label for inventory
        money.setFill(Color.BLACK);
        money.setFont(Font.font("verdana", 20));
        money.setTranslateY(160);
        money.setTranslateX(135);

        Pane pane = new Pane();
        pane.setPrefSize(WIDTH, HEIGHT);
        ImageView image = new ImageView("/game/res/marketN.png");
        image.setFitHeight(HEIGHT);
        image.setFitWidth(WIDTH);
        Button buyWheat = new Button("Wheat Seeds");
        buyWheat.setMinSize(60, 40);
        Button buyCorn = new Button("Corn Seeds");
        buyCorn.setMinSize(60, 40);
        Button buyPotato = new Button("Potato Seeds");
        buyPotato.setMinSize(60, 40);
        Button aReturn = new Button("Return");
        aReturn.setMinSize(60, 40);
        HBox buttons = new HBox(15);
        buttons.getChildren().addAll(buyWheat, buyCorn, buyPotato, aReturn);
        buttons.setTranslateX(22);
        buttons.setTranslateY(550);



        //added
        ROOT.getChildren().add(pane);
        //

        buyCorn.setOnMouseClicked((e) -> {
            if (this.mAmount - cornSeedPrice >= 0) {
                this.mAmount -= cornSeedPrice;
                mAmount = Math.round(mAmount * 100.0) / 100.0;
                int cs = UIFarm.getCornSeeds();
                cs++;
                UIFarm.setCornSeeds(cs);
                //UIFarm.cornSeeds++;
                Constants.updateInventory();
                ROOT.getChildren().remove(pane);
                purchaseItems();
            }
        });

        buyWheat.setOnMouseClicked((e) -> {
            if (this.mAmount - wheatSeedPrice >= 0) {
                this.mAmount -= wheatSeedPrice;
                mAmount = Math.round(mAmount * 100.0) / 100.0;
                int ws = UIFarm.getWheatSeeds();
                ws++;
                UIFarm.setWheatSeeds(ws);
                //UIFarm.wheatSeeds++;
                Constants.updateInventory();
                ROOT.getChildren().remove(pane);
                purchaseItems();
            }

        });

        buyPotato.setOnMouseClicked((e) -> {
            if (this.mAmount - potatoSeedPrice >= 0) {
                this.mAmount -= potatoSeedPrice;
                mAmount = Math.round(mAmount * 100.0) / 100.0;
                int sp = UIFarm.getSeedPotato();
                sp++;
                UIFarm.setSeedPotato(sp);
                //UIFarm.seedPotato++;
                Constants.updateInventory();
                ROOT.getChildren().remove(pane);
                purchaseItems();
            }
        });
        aReturn.setOnAction((e) -> {
            ROOT.getChildren().remove(pane);
            Market market = new Market(mAmount, diffMultiplier);
        });

        pane.getChildren().addAll(image, buttons, inventory, money);

    }

    private void sellItems() {
        setItemPrice();
        inventory = new Text("     Inventory " + System.lineSeparator()
                + System.lineSeparator() + "Wheat Seeds: "
                + INVENTORYCOUNT[0] + System.lineSeparator() + "Corn Seeds: "
                + INVENTORYCOUNT[1] + System.lineSeparator()
                + "Potato Seeds: " + INVENTORYCOUNT[2] + System.lineSeparator()
                + System.lineSeparator() + "Wheat: "
                + INVENTORYCOUNT[3] + System.lineSeparator() + "Corn: " + INVENTORYCOUNT[4]
                + System.lineSeparator() + "Potato: " + INVENTORYCOUNT[5]); // Label for inventory
        inventory.setFill(Color.BLACK);
        inventory.setFont(Font.font("verdana", 20));
        inventory.setTranslateY(265);
        inventory.setTranslateX(118);

        money = new Text("Money: " + mAmount); // Label for inventory
        money.setFill(Color.BLACK);
        money.setFont(Font.font("verdana", 20));
        money.setTranslateY(160);
        money.setTranslateX(135);

        Pane pane = new Pane();
        ImageView image = new ImageView("/game/res/marketN.png");
        image.setFitHeight(HEIGHT);
        image.setFitWidth(WIDTH);
        pane.setPrefSize(WIDTH, HEIGHT);

        Button sellWheat = new Button("Sell Wheat");
        sellWheat.setMinSize(60, 40);
        Button sellCorn = new Button("Sell Corn");
        sellCorn.setMinSize(60, 40);
        Button sellPotato = new Button("Sell Potato");
        sellPotato.setMinSize(60, 40);
        Button aReturn = new Button("Return");
        aReturn.setMinSize(60, 40);
        HBox buttons = new HBox(15);
        buttons.getChildren().addAll(sellWheat, sellCorn, sellPotato, aReturn);
        buttons.setTranslateX(45);
        buttons.setTranslateY(550);


        //added
        ROOT.getChildren().add(pane);
        //

        sellCorn.setOnMouseClicked((e) -> {
            if (INVENTORYCOUNT[4] > 0) {
                this.mAmount += cornSellPrice;
                mAmount = Math.round(mAmount * 100.0) / 100.0;
                int cc = UIFarm.getCornCrops();
                cc--;
                UIFarm.setCornCrops(cc);
                //UIFarm.cornCrops--;
                Constants.updateInventory();
                ROOT.getChildren().remove(pane);
                sellItems();
            }
        });

        sellWheat.setOnMouseClicked((e) -> {
            if (INVENTORYCOUNT[3] > 0) {
                this.mAmount += wheatSellPrice;
                mAmount = Math.round(mAmount * 100.0) / 100.0;
                int wc = UIFarm.getWheatCrops();
                wc--;
                UIFarm.setWheatCrops(wc);
                //UIFarm.wheatCrops--;
                Constants.updateInventory();
                ROOT.getChildren().remove(pane);
                sellItems();
            }
        });

        sellPotato.setOnMouseClicked((e) -> {
            if (INVENTORYCOUNT[5] > 0) {
                this.mAmount += potatoSellPrice;
                mAmount = Math.round(mAmount * 100.0) / 100.0;
                int pc = UIFarm.getPotatoCrops();
                pc--;
                UIFarm.setPotatoCrops(pc);
                //UIFarm.potatoCrops--;
                Constants.updateInventory();
                ROOT.getChildren().remove(pane);
                sellItems();
            }
        });
        aReturn.setOnAction((e) -> {
            ROOT.getChildren().remove(pane);
            Market market = new Market(mAmount, diffMultiplier);
        });
        pane.getChildren().addAll(image, buttons, inventory, money);

    }

    private void setItemPrice() {
        cornSeedPrice = 0.33 * diffMultiplier + randomVariance;
        wheatSeedPrice = 5.00 * diffMultiplier + randomVariance;
        potatoSeedPrice = 4.00 * diffMultiplier + randomVariance;
        cornSellPrice = 3.50;
        wheatSellPrice = 4.50;
        potatoSellPrice = 1.00;
    }


}

