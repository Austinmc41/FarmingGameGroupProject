package game.entity;

import game.graphics.FarmPlot;
import game.graphics.PlayerAnimation;
import game.graphics.SpriteSheet;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;

public class Player extends Entity {
    private boolean isMoving = true;
    private final int up = 0;  //forward
    private final int down = 1;  //back
    private final int left = 2;
    private final int right = 3;
    private int currentDirection = 0;
    //private int maxSpeed = 3;

    private final SpriteSheet playerSS = new SpriteSheet("PlayerSS.png", 16, 18);

    private final ImageView pfoward = playerSS.getIndividualSprite(1, 1);
    private final ImageView pback = playerSS.getIndividualSprite(2, 1);
    private final ImageView pleft = playerSS.getIndividualSprite(3, 1);
    private final ImageView pright = playerSS.getIndividualSprite(4, 1);

    private final ImageView pfoward1 = playerSS.getIndividualSprite(1, 2);
    private final ImageView pfoward2 = playerSS.getIndividualSprite(1, 3);

    private final ImageView pback1 = playerSS.getIndividualSprite(2, 2);
    private final ImageView pback2 = playerSS.getIndividualSprite(2, 3);

    private final ImageView pleft1 = playerSS.getIndividualSprite(3, 2);
    private final ImageView pleft2 = playerSS.getIndividualSprite(3, 3);

    private final ImageView pright1 = playerSS.getIndividualSprite(4, 2);
    private final ImageView pright2 = playerSS.getIndividualSprite(4, 3);

    private final ImageView[] walkDown = {pfoward, pfoward1, pfoward2};
    private final ImageView[] walkUp = {pback, pback1, pback2};
    private final ImageView[] walkLeft = {pleft, pleft1, pleft2};
    private final ImageView[] walkRight = {pright, pright1, pright2};

    private final PlayerAnimation moveUp = new PlayerAnimation(this, walkUp);
    private final PlayerAnimation moveDown = new PlayerAnimation(this, walkDown);
    private final PlayerAnimation moveLeft = new PlayerAnimation(this, walkLeft, 3);
    private final PlayerAnimation moveRight = new PlayerAnimation(this, walkRight, 3);


    public Player(ImageView player, double x, double y) {
        super(player, x, y);
        requestFocus();
    }

    public Player(ImageView player, double x, double y, double scale) {
        super(player, x, y, scale);
        requestFocus();
    }

    public void setVelocityX(double x) {
        this.setX(x + getX());
    }
    public void setVelocityY(double y) {
        this.setY(y + getY());
    }

    public double getVelocityX(double x) {
        return this.getX() + x;
    }
    public double getVelocityY(double y) {
        return this.getY() + y;
    }


    public void move(KeyEvent in) {
        if (isMoving) {
            if (in.getCode() == KeyCode.UP || in.getCode() == KeyCode.W) {
                isMoving = true;
                currentDirection = 0;
                setVelocityY(-5);
                //if (getVelocityY(-3) > maxSpeed) {
                //  setVelocityY(-1);
                //}
                moveUp.animate();
            }
            if (in.getCode() == KeyCode.DOWN  || in.getCode() == KeyCode.S) {
                isMoving = true;
                currentDirection = 1;
                setVelocityY(5);
                moveDown.animate();
            }
            if (in.getCode() == KeyCode.LEFT || in.getCode() == KeyCode.A) {
                isMoving = true;
                currentDirection = 2;
                setVelocityX(-5);
                moveLeft.animate();
            }
            if (in.getCode() == KeyCode.RIGHT || in.getCode() == KeyCode.D) {
                isMoving = true;
                currentDirection = 3;
                setVelocityX(5);
                moveRight.animate();
            }
        }
    }

    public void still() {
        if (currentDirection == up) {
            moveUp.stoppedMovement();
        }
        if (currentDirection == down) {
            moveDown.stoppedMovement();
        }
        if (currentDirection == left) {
            moveLeft.stoppedMovement();
        }
        if (currentDirection == right) {
            moveRight.stoppedMovement();
        }
    }


    public void collision() {
        isMoving = false;
        this.setOnKeyPressed(e -> {
            if (!this.isMoving) {
                if (this.currentDirection == 0) {
                    if (e.getCode() == KeyCode.DOWN || e.getCode() == KeyCode.RIGHT
                            || e.getCode() == KeyCode.LEFT || e.getCode() == KeyCode.S
                            || e.getCode() == KeyCode.D || e.getCode() == KeyCode.A) {
                        this.isMoving = true;
                        setVelocityY(3);
                    }
                }
                if (this.currentDirection == 1) {
                    if (e.getCode() == KeyCode.UP || e.getCode() == KeyCode.RIGHT
                            || e.getCode() == KeyCode.LEFT || e.getCode() == KeyCode.W
                            || e.getCode() == KeyCode.D || e.getCode() == KeyCode.A) {
                        this.isMoving = true;
                        setVelocityY(-3);
                    }
                }
                if (this.currentDirection == 2) {
                    if (e.getCode() == KeyCode.UP || e.getCode() == KeyCode.DOWN
                            || e.getCode() == KeyCode.RIGHT || e.getCode() == KeyCode.W
                            || e.getCode() == KeyCode.S || e.getCode() == KeyCode.D) {
                        this.isMoving = true;
                        setVelocityX(3);
                    }
                }
                if (this.currentDirection == 3) {
                    if (e.getCode() == KeyCode.UP || e.getCode() == KeyCode.DOWN
                            || e.getCode() == KeyCode.LEFT || e.getCode() == KeyCode.W
                            || e.getCode() == KeyCode.S || e.getCode() == KeyCode.A) {
                        this.isMoving = true;
                        setVelocityX(-3);
                    }
                }
            }
        });
    }

    public void plotColllision() {
        isMoving = false;

        if (this.currentDirection == 0) {
            setVelocityY(5);
        }

        if (this.currentDirection == 1) {
            setVelocityY(-5);
        }

        if (this.currentDirection == 2) {
            setVelocityX(5);
        }
        if (this.currentDirection == 3) {
            setVelocityX(-5);
        }
    }

    public boolean isIntersectsPlot(ArrayList<FarmPlot> farmPlots) {
        for (FarmPlot plot : farmPlots) {
            if (plot.isIntersects()) {
                return true;
            }
        }
        return false;
    }

    public void setMoving(boolean moving) {
        isMoving = moving;
    }

}
