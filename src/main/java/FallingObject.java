import java.awt.*;

/**
 * Created by Catherine on 2019-02-04.
 */
public class FallingObject {
    private int centerX, centerY, speedY, radius, points;
    private String type;
    private boolean visible;
    private Rectangle r;

    public FallingObject(String type) {
        this.centerX = Helpers.randomWithRange(60, 430);
        this.centerY = 0;
        this.radius = 25;
        this.speedY = Helpers.randomWithRange(2, 5);
        this.visible = true;
        this.type = type;
        this.r = new Rectangle(0, 0, 0, 0);
    }

    public void update() {
        centerY += speedY;
        r.setBounds(centerX, centerY, 30, 30);
        if (centerY > 680) {
            visible = false;
            r = null;
        } if (centerY < 680) {
            checkCollision();
        }
    }

    private void checkCollision() {
        if (r.intersects(Cat.rectangle)) {
            visible = false;
            // calculate score here
            if (this.type == "sushi")
                StartingClass.score += 10;
            else if (this.type == "milk") {
                StartingClass.score -= 5;
                // decrement number of cat's lives
                Cat.lives -= 1;
                if (Cat.lives == 0) {
                    System.out.println("you ded son");
                    Cat.alive = false;
                }
            }
        }
    }

    public boolean isVisible() {
        return visible;
    }

    public int getCenterX() {
        return centerX;
    }

    public int getCenterY() {
        return centerY;
    }
}
