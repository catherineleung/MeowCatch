/**
 * Created by Catherine on 2019-02-04.
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
