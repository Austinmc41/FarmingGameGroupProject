package game.input;

import game.entity.Player;

public class PlayerInputHandler {
    public PlayerInputHandler(Player player) {
        player.requestFocus();
        player.setOnKeyPressed(e -> {
            player.move(e);
        });
        player.setOnKeyReleased(e -> {
            player.still();
        });
    }
}
