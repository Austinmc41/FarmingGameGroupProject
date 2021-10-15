package game.ui;

import game.util.Constants;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class ConfigurationScreen implements Constants {
    private static final Font FONT = Font.font("Bell MT", FontWeight.BOLD, 50);

    private String farmFile;

    private StackPane pane = new StackPane();
    private ImageView farm;

    private VBox nameBox;
    private Button next;
    private Button exit;
    private HBox hold = new HBox(20);
    private TextField nameField = new TextField();
    private String name;

    private VBox levelBox;
    private String level;
    private int mAmount;

    private VBox cropBox;
    private String crop;

    private Stage stage;

    //Pass in the farm image when you want to get to configure screen
    public ConfigurationScreen(String farmImageFile) {
        ROOT.getChildren().add(pane);
        pane.setPrefSize(WIDTH, HEIGHT);
        //Makes the farm image
        farm = new ImageView(new Image(getClass().getResourceAsStream("/game/res/"
                + farmImageFile)));
        farm.setFitWidth(WIDTH);
        farm.setFitHeight(HEIGHT);
        pane.getChildren().add(farm);
        //name screen
        nameBox = new VBox(20);
        nameBox.setAlignment(Pos.CENTER);
        pane.getChildren().addAll(nameBox);
        Name enterName = new Name();
        nameBox.getChildren().addAll(enterName);
        //level screen
        levelBox = new VBox(20);
        levelBox.setAlignment(Pos.CENTER);
        Level easy = new Level("Easy");
        easy.setMaxWidth(500);
        Level medium = new Level("Medium");
        medium.setMaxWidth(500);
        Level hard = new Level("Hard");
        hard.setMaxWidth(500);
        Level backLevel = new Level("Back");
        backLevel.setMaxWidth(500);
        levelBox.getChildren().addAll(easy, medium, hard, backLevel);
        //crop screen
        cropBox = new VBox(20);
        cropBox.setAlignment(Pos.CENTER);
        Crop wheat = new Crop("Wheat");
        wheat.setMaxWidth(500);
        Crop corn = new Crop("Corn");
        corn.setMaxWidth(500);
        Crop potato = new Crop("Potato");
        potato.setMaxWidth(500);
        Crop backCrop = new Crop("Back");
        cropBox.getChildren().addAll(wheat, corn, potato, backCrop);
        //confirmation screen
        StackPane canvas = new StackPane();
        Rectangle white = new Rectangle(450, 250);
        white.setFill(Color.WHITE);
        Text textName = new Text();
        Text textLevel = new Text();
        Text textCrop = new Text();
        VBox text = new VBox(20);
        text.setAlignment(Pos.CENTER);
        text.getChildren().addAll(textName, textLevel, textCrop);
        VBox textBox = new VBox(20);
        Button nextUI = new Button("Play");
        nextUI.setMinHeight(HEIGHT / 7.0);
        nextUI.setMinWidth(WIDTH / 11.0);
        nextUI.setFont(FONT);
        Button backFix = new Button("Back");
        backFix.setMinHeight(HEIGHT / 7.0);
        backFix.setMinWidth(WIDTH / 11.0);
        backFix.setFont(FONT);
        HBox buttonBox = new HBox(20);
        buttonBox.getChildren().addAll(backFix, nextUI);
        buttonBox.setAlignment(Pos.CENTER);
        canvas.getChildren().addAll(white, text);
        textBox.getChildren().addAll(canvas, buttonBox);
        textBox.setAlignment(Pos.CENTER);
        //actions
        //name screen actions
        exit.setOnAction(e -> { //exit to the welcome screen
            ROOT.getChildren().remove(pane);
        });
        next.setOnAction(e -> {
            //proceed to choose levels
            if (!(nameField.getText().isEmpty())) {
                name = nameField.getText();
                textName.setText("Hello, " + name);
                textName.setFont(FONT);
                pane.getChildren().remove(nameBox);
                pane.getChildren().addAll(levelBox);
            } else {
                nameField.setPromptText("Error: Please enter a name");
            }
        });
        //level screen actions
        easy.setOnMouseClicked(e -> {
            level = "Easy";
            textLevel.setText("Level: " + level);
            textLevel.setFont(FONT);
            pane.getChildren().remove(levelBox);
            pane.getChildren().addAll(cropBox);
        });
        medium.setOnMouseClicked(e -> {
            level = "Medium";
            textLevel.setText("Level: " + level);
            textLevel.setFont(FONT);
            pane.getChildren().remove(levelBox);
            pane.getChildren().addAll(cropBox);
        });
        hard.setOnMouseClicked(e -> {
            level = "Hard";
            textLevel.setText("Level: " + level);
            textLevel.setFont(FONT);
            pane.getChildren().remove(levelBox);
            pane.getChildren().addAll(cropBox);
        });
        backLevel.setOnMouseClicked(e -> {
            pane.getChildren().remove(levelBox);
            pane.getChildren().addAll(nameBox);
        });
        //crop screen actions
        wheat.setOnMouseClicked(e -> {
            crop = "Wheat";
            textCrop.setText("Crop: " + crop);
            textCrop.setFont(FONT);
            pane.getChildren().remove(cropBox);
            pane.getChildren().addAll(textBox);
        });
        corn.setOnMouseClicked(e -> {
            crop = "Corn";
            textCrop.setText("Crop: " + crop);
            textCrop.setFont(FONT);
            pane.getChildren().remove(cropBox);
            pane.getChildren().addAll(textBox);
        });
        potato.setOnMouseClicked(e -> {
            crop = "Potato";
            textCrop.setText("Crop: " + crop);
            textCrop.setFont(FONT);
            pane.getChildren().remove(cropBox);
            pane.getChildren().addAll(textBox);
        });
        backCrop.setOnMouseClicked(e -> {
            pane.getChildren().remove(cropBox);
            pane.getChildren().addAll(levelBox);
        });
        backFix.setOnAction(e -> {
            pane.getChildren().remove(textBox);
            pane.getChildren().addAll(cropBox);
        });
        nextUI.setOnAction(e -> { //going to the UI
            ROOT.getChildren().remove(pane);
            UIFarm farmScreen = new UIFarm(level, crop);
        });
    }


    private class Name extends HBox {
        public Name() {
            setAlignment(Pos.CENTER);
            nameField.setPromptText("Enter Name");
            nameField.setStyle("-fx-prompt-text-fill: "
                    + "derive(-fx-control-inner-background,-30%); }");
            nameField.setPrefWidth(WIDTH / 1.5);
            nameField.setPrefHeight(HEIGHT / 7.0);
            nameField.setFont(FONT);
            next = new Button("Next");
            next.setMinHeight(HEIGHT / 7.0);
            next.setMinWidth(WIDTH / 10.0);
            exit = new Button("Back");
            exit.setMinHeight(HEIGHT / 7.0);
            exit.setMinWidth(WIDTH / 10.0);
            Font font = new Font(30);
            next.setFont(font);
            exit.setFont(font);
            hold.getChildren().addAll(exit, nameField, next);
            getChildren().addAll(hold);
        }
    }

    private class Level extends StackPane {
        public Level(String lvl) {
            Text txt = new Text(lvl);

            txt.setFont(FONT);
            txt.setFill(Color.BLACK);

            Rectangle r = new Rectangle(500, 75);
            r.setFill(Color.CORNFLOWERBLUE);
            getChildren().addAll(r, txt);

            setOnMouseEntered(e -> {
                switch (lvl) {
                case "Easy":
                    mAmount = 100;
                    r.setFill(Color.GREEN);
                    break;
                case "Medium":
                    mAmount = 50;
                    r.setFill(Color.YELLOW);
                    break;
                case "Hard":
                    mAmount = 25;
                    r.setFill(Color.RED);
                    break;
                default:
                    r.setFill(Color.LIGHTGRAY);
                    break;
                }
            });

            setOnMouseExited(e -> {
                r.setFill(Color.CORNFLOWERBLUE);
            });
        }
    }

    private class Crop extends StackPane {
        public Crop(String crop) {
            Text txt = new Text(crop);

            txt.setFont(FONT);
            txt.setFill(Color.BLACK);

            Rectangle r = new Rectangle(500, 75);
            r.setFill(Color.CORNFLOWERBLUE);
            getChildren().addAll(r, txt);

            setOnMouseEntered(e -> {
                if (!(crop.equals("Back"))) {
                    r.setFill(Color.BURLYWOOD);
                } else {
                    r.setFill(Color.LIGHTGRAY);
                }
            });

            setOnMouseExited(e -> {
                r.setFill(Color.CORNFLOWERBLUE);
            });
        }
    }


}



