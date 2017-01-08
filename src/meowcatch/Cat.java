package meowcatch;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by catherineleung on 2017-01-05.
 */
public class Cat {
    final int JUMPSPEED = -15;
    final int MOVESPEED = 5;
    final int GROUND = 670;

    // coordinates of character's center
    private int centerX = 30;
    private int centerY = GROUND;

    // true if character is in the air, false if character is on ground
    private boolean jumped = false;
    private boolean movingLeft = false;
    private boolean movingRight = false;
    private boolean ducked = false;

    private String direction = "right";

    private static Background bg1 = StartingClass.getBg1();
    private static Background bg2 = StartingClass.getBg2();

    // rate at which these x and y positions change
    private int speedX = 0;
    private int speedY = 1;

    // moves character or scrolls background accordingly
    public void update() {
        if (speedX < 0) {
            // change centerX by adding speed
            centerX += speedX;
        } if (centerX <= 480 && speedX > 0) {
            centerX += speedX;
        }

        // updates y position
        centerY += speedY;
        if (centerY + speedY >= GROUND)
            centerY = GROUND;

        // handles jumping
        if (jumped == true) {
            // while the character is in the air, add 1 to its speedY
            // (brings character downwards)
            speedY += 1;

            if (centerY + speedY >= GROUND) {
                centerY = GROUND;
                speedY = 0;
                jumped = false;
            }
        }

        // prevents going beyond X coordinate of 0...
        // if speedX plus centerX would bring the character outside screen
        if (centerX + speedX <= 60) {
            // fix character's centerX at 61
            centerX = 61;
        }
    }

    public void moveRight() {
        if (ducked == false) {
            speedX = MOVESPEED;
            movingRight = true;
            movingLeft = false;
        }
    }

    public int getCenterX() {
        return centerX;
    }

    public void setCenterX(int centerX) {
        this.centerX = centerX;
    }

    public int getCenterY() {
        return centerY;
    }

    public void setCenterY(int centerY) {
        this.centerY = centerY;
    }

    public boolean isJumped() {
        return jumped;
    }

    public void setJumped(boolean jumped) {
        this.jumped = jumped;
    }

    public int getSpeedX() {
        return speedX;
    }

    public void setSpeedX(int speedX) {
        this.speedX = speedX;
    }

    public int getSpeedY() {
        return speedY;
    }

    public void setSpeedY(int speedY) {
        this.speedY = speedY;
    }

    public void moveLeft() {
        if (ducked == false) {
            speedX = -MOVESPEED;
            movingLeft = true;
            movingRight = false;
        }
    }

    public void stopRight() {
        setMovingRight(false);
        stop();
    }

    public void stopLeft() {
        setMovingLeft(false);
        stop();
    }

    public void stop() {
        if(isMovingRight() == false && isMovingLeft() == false) {
            speedX = 0;
        } if (isMovingRight() == false && isMovingLeft() == true) {
            moveLeft();
        } if (isMovingRight() == true && isMovingLeft() == false) {
            moveRight();
        }
    }

    public void jump() {
        if (jumped == false) {
            speedY = JUMPSPEED;
            jumped = true;
        }
    }

    public boolean isDucked() {
        return ducked;
    }

    public void setDucked(boolean ducked) {
        this.ducked = ducked;
    }

    public boolean isMovingRight() {
        return movingRight;
    }

    public void setMovingRight(boolean movingRight) {
        this.movingRight = movingRight;
    }

    public boolean isMovingLeft() {
        return movingLeft;
    }

    public void setMovingLeft(boolean movingLeft) {
        this.movingLeft = movingLeft;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }
}
