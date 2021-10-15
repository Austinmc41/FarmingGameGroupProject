package game.graphics;

import game.entity.Player;
import javafx.scene.image.ImageView;

public class PlayerAnimation {
    private Player player;
    private ImageView[] playerFrames;
    private int totalStates;  //# of possible images that player can be seen as in current animation

    private int animationDelay = 5;  //change to increase/decrease animation speed
    private int currentState = 0;  //the current image being displayed
    private int counter = 0;

    public PlayerAnimation(Player player, ImageView[] playerFrames) {
        this.playerFrames = playerFrames;
        totalStates = playerFrames.length - 1;
        this.player = player;
    }

    public PlayerAnimation(Player player, ImageView[] playerFrames, int animationDelay) {
        this.playerFrames = playerFrames;
        totalStates = playerFrames.length - 1;
        this.player = player;
        this.animationDelay = animationDelay;
    }


    public void animate() {
        /*won't animate character until counter is larger then delay;
          this method is likely being called at 60 fps so the slight delay
          allows for the player to look like it's moving
        */
        counter++;
        if (counter > animationDelay) {
            counter = 0;
            currentState++;
            /*
            counter is reset so delay will occur again between next animation
             */
            if (currentState > totalStates) {
                currentState = 0;
            }
        }
        player.setEntityIV(playerFrames[currentState]);
    }

    public void stoppedMovement() {
        player.setEntityIV(playerFrames[0]);
    }

}
