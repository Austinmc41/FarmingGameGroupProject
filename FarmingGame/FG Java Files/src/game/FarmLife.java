package game; //package game;

import game.ui.WelcomeScreen;
import game.util.Constants;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

public class FarmLife extends Application implements Constants {

    public FarmLife() {
        GAMELOOP.setCycleCount(Timeline.INDEFINITE);

        final WelcomeScreen[] startScreen = {null};
        KeyFrame kf = new KeyFrame(
            Duration.seconds(0.017),
            e -> {
                if (startScreen[0] == null) {
                    startScreen[0] = new WelcomeScreen();
                }
            });
        GAMELOOP.getKeyFrames().add(kf);
        GAMELOOP.play();
    }


    public void start(Stage primaryStage) {
        //stage is constant so need for primaryStage
        FarmLife game = new FarmLife();
        Scene scene = new Scene(ROOT);
        STAGE.setTitle("FarmLife v1.02");
        STAGE.setMaxWidth(WIDTH);
        STAGE.setMinWidth(WIDTH);
        STAGE.setMaxHeight(HEIGHT);
        STAGE.setMinHeight(HEIGHT);
        STAGE.setScene(scene);
        STAGE.show();
    }
}
