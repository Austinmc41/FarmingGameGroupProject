package game.graphics;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class SpriteSheet {
    private BufferedImage spriteSheet;
    private String ssFileName;
    private int xTileSize;
    private int yTileSize;

    /**
     * @param ssFileName the file name of the sprite sheet (including extension)
     * @param xTileSize the width of each sprite in pixels
     * @param yTileSize the height of each sprite in pixels
     */
    public SpriteSheet(String ssFileName, int xTileSize, int yTileSize) {
        this.ssFileName = ssFileName;
        this.xTileSize = xTileSize;
        this.yTileSize = yTileSize;
        try {
            loadSpriteSheet();
        } catch (IOException e) {
            System.out.println("Invalid file name entered.");
        }
    }

    /**
     * @return the BufferedImage of the full sprite sheet
     * @throws IOException throws IOException if the file name is invalid
     */
    private BufferedImage loadSpriteSheet() throws IOException {
        spriteSheet = ImageIO.read(getClass().getResourceAsStream("/game/res/" + ssFileName));
        return spriteSheet;
    }

    /**
     * @param row the row of the sprite you want (there's no row 0, starts at 1)
     * @param col the column of the sprite you want (there's no col 0, starts at 1)
     * @return an ImageView of the specified sprite
     */
    public ImageView getIndividualSprite(int row, int col) {
        if (spriteSheet == null) {
            try {
                spriteSheet = loadSpriteSheet();
            } catch (IOException e) {
                System.out.println("Invalid file name entered.");
            }
        }
        BufferedImage selectedSprite = spriteSheet.getSubimage((col * xTileSize) - xTileSize,
                (row * yTileSize) - yTileSize, xTileSize, yTileSize);
        Image sprite = SwingFXUtils.toFXImage(selectedSprite, null);
        ImageView img = new ImageView(sprite);
        return img;
    }
}
