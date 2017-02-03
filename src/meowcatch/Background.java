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

    public int getBgX() {
        return bgX;
    }

    public int getBgY() {
        return bgY;
    }
}
