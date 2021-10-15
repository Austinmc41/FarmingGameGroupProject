package game.ui;

import game.util.Constants;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * Implementation of Welcome Screen for FarmLife.
 * @version 1.02
 */

public class WelcomeScreen implements Constants {
    private static final Font FONT = Font.font("Bell MT", FontWeight.BOLD, 22);

    private Pane pane;
    private VBox mainMenuBox;
    private VBox farmSelectionMenuBox;
    private ImageView imageView;
    private HBox header;
    private String farmName = "FarmMainMenu.jpg";


    public WelcomeScreen() {
        pane = new Pane();
        pane.setPrefSize(WIDTH, HEIGHT);
        ROOT.getChildren().add(pane);

        //Makes farm image the Pane background
        //Now does everything
        farmSelectionTask("FarmMainMenu.jpg");
    }

    /**
     * Takes String of .jpg file and changes farm background to that image.
     * Also calls the createHeader and initializeMenus method so this method
     * is where the magic happens.
     * @param farm name of the .jpg file for the farm in the background
     */
    private void farmSelectionTask(String farm) {
        pane.getChildren().remove(imageView);
        boolean firstStart = true;
        //if it's not the first start then it will remove farmSelectionMenuBox
        if (pane.getChildren().contains(farmSelectionMenuBox)) {
            //removed so that there won't be duplicate children
            pane.getChildren().remove(farmSelectionMenuBox);
            firstStart = false;
        }
        imageView = new ImageView(new Image(getClass().getResourceAsStream("/game/res/" + farm)));
        imageView.setFitWidth(WIDTH);
        imageView.setFitHeight(HEIGHT);
        pane.getChildren().add(imageView);

        if (!firstStart) {
            pane.getChildren().add(farmSelectionMenuBox);
        }

        if (farm == "Farm3.jpg") {
            createHeader(Color.BLUE);
            initializeMenus(Color.BLUE, Color.WHITE);
        } else if (farm == "Farm2.jpg") {
            createHeader(Color.AQUA);
            initializeMenus(Color.AQUA, Color.BLACK);
        } else {
            createHeader(Color.RED);
            initializeMenus(Color.RED, Color.FORESTGREEN);
        }
    }

    /**
     * Creates "FarmLife v1.02" title
     * @param color the color of the FarmLife title
     */
    public void createHeader(Color color) {
        double x = WIDTH / 2;
        double y = HEIGHT / 3;

        //Removes old header if there's one
        if (pane.getChildren().contains(header)) {
            pane.getChildren().remove(header);
        }

        //create box for Farm Life title
        header = new HBox();
        header.setAlignment(Pos.CENTER);
        header.setTranslateX(x - 600);
        header.setTranslateY(y - 50);

        //create title
        Text title = new Text("Farm Life v1.02");
        Font titleFont = Font.font("Arial", FontWeight.BOLD, 60);
        title.setFont(titleFont);
        title.setFill(color);
        //add shadow to font
        DropShadow shadow = new DropShadow();
        shadow.setOffsetX(10);
        shadow.setOffsetY(10);
        title.setEffect(shadow);
        //add title to header Hbox
        header.getChildren().add(title);
        //add Hbox to Pane
        pane.getChildren().add(header);
    }

    /**
     * Creates the main menu and farm selection menu
     * @param textC color of the text in the menus
     * @param recC color of the rectangle background in the menu
     */
    private void initializeMenus(Color textC, Color recC) {
        double x = WIDTH / 2;
        double y = HEIGHT / 3;

        //if menu boxes aren't created already, then create and position them
        if (mainMenuBox == null && farmSelectionMenuBox == null) {
            //creates the menu box (box holding menu items)
            mainMenuBox = new VBox(20);
            //Moves mainMenuBox to left screen
            mainMenuBox.setTranslateX(x - 585);
            mainMenuBox.setTranslateY(y + 65);
            pane.getChildren().add(mainMenuBox);

            //creates chooseFarm menu
            farmSelectionMenuBox = new VBox(20);
            farmSelectionMenuBox.setTranslateX(x - 585);
            farmSelectionMenuBox.setTranslateY(y + 65);
        }

        //if mainMenuBox already has children then remove them
        if (!mainMenuBox.getChildren().isEmpty()) {
            mainMenuBox.getChildren().clear();
        }

        //if farmSelectionMenuBox already has children then remove them
        if (!farmSelectionMenuBox.getChildren().isEmpty()) {
            farmSelectionMenuBox.getChildren().clear();
        }

        //if colors are null, then produce the default menus
        if (textC == null && recC == null) {
            //Create Main Menu options and sets actions
            MainMenuOption startGame = new MainMenuOption("Start Game");
            startGame.setOnAction(() -> {
                ConfigurationScreen configScreen = new ConfigurationScreen(farmName);
            });


            MainMenuOption chooseFarm = new MainMenuOption("Choose Farm");
            chooseFarm.setOnAction(() -> {
                pane.getChildren().remove(mainMenuBox);
                pane.getChildren().add(farmSelectionMenuBox);
            });

            MainMenuOption exit = new MainMenuOption("Exit to Desktop");
            exit.setOnAction(() -> Platform.exit());

            mainMenuBox.getChildren().addAll(startGame, chooseFarm, exit);
            //END OF MAIN MENU ITEMS

            //Creates chooseFarm menu options and sets actions
            MainMenuOption farm1 = new MainMenuOption("Farm 1");
            farm1.setOnAction(() -> {
                farmName = "FarmMainMenu.jpg";
                farmSelectionTask(farmName);
            });

            MainMenuOption farm2 = new MainMenuOption("Farm 2");
            farm2.setOnAction(() -> {
                farmName = "Farm2.jpg";
                farmSelectionTask(farmName);
            });

            MainMenuOption farm3 = new MainMenuOption("Farm 3");
            farm3.setOnAction(() -> {
                farmName = "Farm3.jpg";
                farmSelectionTask(farmName);
            });

            MainMenuOption returnBar = new MainMenuOption("Return");
            returnBar.setOnAction(() -> {
                pane.getChildren().remove(farmSelectionMenuBox);
                pane.getChildren().add(mainMenuBox);
                System.out.println(farmName);
            });

            farmSelectionMenuBox.getChildren().addAll(farm1, farm2, farm3, returnBar);

        } else { //if colors are NOT null, creates menus with provided colors
            //Create Main Menu options and sets actions
            MainMenuOption startGame = new MainMenuOption("Start Game", textC, recC);
            startGame.setOnAction(() -> {
                ConfigurationScreen configScreen = new ConfigurationScreen(farmName);
            });


            MainMenuOption chooseFarm = new MainMenuOption("Choose Farm", textC, recC);
            chooseFarm.setOnAction(() -> {
                pane.getChildren().remove(mainMenuBox);
                pane.getChildren().add(farmSelectionMenuBox);
                System.out.println(ENTITIES.size());
            });

            MainMenuOption exit = new MainMenuOption("Exit to Desktop", textC, recC);
            exit.setOnAction(() -> Platform.exit());

            mainMenuBox.getChildren().addAll(startGame, chooseFarm, exit);
            //END OF MAIN MENU ITEMS

            //Creates chooseFarm menu options and sets actions
            MainMenuOption farm1 = new MainMenuOption("Farm 1", textC, recC);
            farm1.setOnAction(() -> {
                farmName = "FarmMainMenu.jpg";
                farmSelectionTask(farmName);
            });

            MainMenuOption farm2 = new MainMenuOption("Farm 2", textC, recC);
            farm2.setOnAction(() -> {
                farmName = "Farm2.jpg";
                farmSelectionTask(farmName);
            });

            MainMenuOption farm3 = new MainMenuOption("Farm 3", textC, recC);
            farm3.setOnAction(() -> {
                farmName = "Farm3.jpg";
                farmSelectionTask(farmName);
            });

            MainMenuOption returnBar = new MainMenuOption("Return", textC, recC);
            returnBar.setOnAction(() -> {
                pane.getChildren().remove(farmSelectionMenuBox);
                pane.getChildren().add(mainMenuBox);
                System.out.println(farmName);
            });

            farmSelectionMenuBox.getChildren().addAll(farm1, farm2, farm3, returnBar);
        }
    }



    /**
     * Class for all the menu items seen in the welcome screen and farm selection screen
     */
    private class MainMenuOption extends StackPane {
        public MainMenuOption(String menuBarName) {
            //StackPane allows for these items to stack on top of each other
            setAlignment(Pos.CENTER);

            //creates menu bar item
            Text text = new Text(menuBarName);
            text.setFont(FONT);
            text.setTranslateX(5);
            text.setFill(Color.RED);

            //creates background rectangle behind each menu item
            Rectangle rec = new Rectangle(250, 30);
            rec.setOpacity(0.7);
            rec.setFill(Color.FORESTGREEN);
            getChildren().addAll(rec, text);

            /*When the mouse is on a menu item, it moves it slightly to right
              and changes color to give indication of selection
             */
            setOnMouseEntered(event -> {
                rec.setTranslateX(25);
                rec.setFill(Color.RED);
                text.setTranslateX(25);
                text.setFill(Color.GREEN);
            });

            /*When the mouse leaves a menu item, it moves it back to its original
              position and changes color back to normal
             */
            setOnMouseExited(event -> {
                rec.setTranslateX(0);
                rec.setFill(Color.GREEN);
                text.setTranslateX(0);
                text.setFill(Color.RED);
            });
        }

        public MainMenuOption(String menuBarName, Color textC, Color recC) {
            //StackPane allows for these items to stack on top of each other
            setAlignment(Pos.CENTER);

            //creates menu bar item
            Text text = new Text(menuBarName);
            text.setFont(FONT);
            text.setTranslateX(5);
            text.setFill(textC);

            //creates background rectangle behind each menu item
            Rectangle rec = new Rectangle(250, 30);
            rec.setOpacity(0.7);
            rec.setFill(recC);
            getChildren().addAll(rec, text);

            /*When the mouse is on a menu item, it moves it slightly to right
              and changes color to give indication of selection
             */
            setOnMouseEntered(event -> {
                rec.setTranslateX(25);
                rec.setFill(textC);
                text.setTranslateX(25);
                text.setFill(recC);
            });

            /*When the mouse leaves a menu item, it moves it back to its original
              position and changes color back to normal
             */
            setOnMouseExited(event -> {
                rec.setTranslateX(0);
                rec.setFill(recC);
                text.setTranslateX(0);
                text.setFill(textC);
            });
        }

        //Allows for menu options to have a Runnable action when clicked
        public void setOnAction(Runnable action) {
            setOnMouseClicked(e -> action.run());
        }
    }

}
