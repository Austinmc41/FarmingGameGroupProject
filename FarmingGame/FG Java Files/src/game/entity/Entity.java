package game.entity;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import java.util.ArrayList;

public class Entity extends ImageView {
    protected ImageView sprite;
    private double scale;

    private int currentDirection = 0;  //0 = forward, 1 = back, 2 = left, 3 = right

    public Entity(ImageView sprite, double x, double y) {
        this.sprite = sprite;
        this.setImage(sprite.getImage());
        this.setX(x);
        this.setY(y);
    }

    public Entity(ImageView sprite, double x, double y, double scale) {
        this(sprite, x, y);
        this.setScaleX(scale);
        this.setScaleY(scale);
        this.scale = scale;
    }

    public ImageView getEntityIV() {
        return this;
    }

    public void setEntityIV(ImageView img) {
        this.setImage(img.getImage());
    }

    public void setCurrentDirection(int currentDirection) {
        if (currentDirection > 3 || currentDirection < 0) {
            this.currentDirection = 0;
        } else {
            this.currentDirection = currentDirection;
        }
    }

    public int getCurrentDirection() {
        return currentDirection;
    }


    //using rectangle instead of provided Bounds so I can increase collision area
    public Rectangle2D getBounds() {
        //return this.getBoundsInParent();
        //        return new Rectangle((int)this.getX(),
        //        (int)this.getY(), (int) (this.getImage().getWidth() * scale),
        //                (int)(this.getImage().getHeight() * scale) + 5);
        return new Rectangle2D(getX(), getY(),
                getImage().getWidth() * scale, (getImage().getHeight() * scale) + 10);
    }

    public boolean isIntersects(Entity entity) {
        return this.getBounds().intersects(entity.getBounds());
    }

    public boolean isIntersects(ArrayList<Entity> entities) {
        for (Entity entity : entities) {
            if (this.isIntersects(entity)) {
                return true;
            }
        }
        return false;
    }

    public boolean isIntersects(Rectangle2D bounds) {
        return this.getBounds().intersects(bounds);
    }

}
