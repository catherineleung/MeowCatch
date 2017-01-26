package meowcatch;

/**
 * Created by catherineleung on 2017-01-08.
 */
public class FallingObject {
    private int centerX, centerY, speedY, radius, points;
    private boolean visible;

    public FallingObject() {
        this.centerX = Helpers.randomWithRange(60, 430);   //(int) Math.floor(Math.random() * 800 - 50);
        this.centerY = 0;
        this.radius = 25;
        this.speedY = Helpers.randomWithRange(2, 5);
        this.visible = true;
    }

    public void update() {
        centerY += speedY;
        if (centerY > 680) {
            visible = false;
        }
    }

    public boolean isVisible() {
        return visible;
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

    public int getSpeedY() {
        return speedY;
    }

    public void setSpeedY(int speedY) {
        this.speedY = speedY;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getRadius() {
        return radius;
    }
}
