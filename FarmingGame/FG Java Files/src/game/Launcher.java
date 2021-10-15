package game;

import game.entity.Entity;
import game.entity.Player;
import game.graphics.SpriteSheet;
import game.input.PlayerInputHandler;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;

public class Launcher extends Application {
    private static int width = 1280;
    private static int height = width / 16 * 9;

    private Stage stage;
    private ArrayList<Entity> entities = new ArrayList<>();
    private Group root = new Group();
    private Scene scene = new Scene(root);
    private Pane pane = new Pane();
    private Timeline gameLoop = new Timeline();
    private ImageView pl = new SpriteSheet("PlayerSS.png", 16, 18).getIndividualSprite(1, 1);
    private ImageView pl2 = new SpriteSheet("PlayerSS.png", 16, 18).getIndividualSprite(1, 1);
    private Player player = new Player(pl, 100, 100, 3);
    private Entity cpu = new Entity(pl2, 300, 300, 3);

    public Launcher() {
        gameLoop.setCycleCount(Timeline.INDEFINITE);

        cpu.setCurrentDirection(0);
        entities.add(cpu);

        pane.setPrefSize(width, height);
        pane.getChildren().addAll(player, cpu);
        root.getChildren().addAll(pane);

        playerKeyFrame();
        gameLoop.play();

    }

    public void playerKeyFrame() {
        KeyFrame kf = new KeyFrame(
            Duration.seconds(0.017),  //60 FPS
            e -> {
                PlayerInputHandler in = new PlayerInputHandler(player);
                if (player.isIntersects(entities) || !player.isIntersects(gameBounds())) {
                    player.collision();
                }
            });
        gameLoop.getKeyFrames().add(kf);
    }

    public Rectangle2D gameBounds() {
        return new Rectangle2D(70, 90, width - 115, height - 135);
    }


    public ImageView getpl() {
        return pl;
    }

    public ImageView getpl2() {
        return pl2;
    }


    public void start(Stage stage) throws Exception {
        this.stage = stage;
        stage.setTitle("FarmLife v1.01");
        Launcher game = new Launcher();
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
