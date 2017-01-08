package meowcatch;

/**
 * Created by catherineleung on 2017-01-07.
 */
public class Background {
    private int bgX, bgY, speedX;

    // constructor
    public Background(int x, int y) {
        bgX = x;
        bgY = y;
        speedX = 0;
    }

    public void update() {

    }

    public int getBgX() {
        return bgX;
    }

    public void setBgX(int bgX) {
        this.bgX = bgX;
    }

    public int getBgY() {
        return bgY;
    }

    public void setBgY(int bgY) {
        this.bgY = bgY;
    }

    public int getSpeedX() {
        return speedX;
    }

    public void setSpeedX(int speedX) {
        this.speedX = speedX;
    }
}
