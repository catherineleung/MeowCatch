package meowcatch;

import java.applet.Applet;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Graphics;
import java.net.URL;
import java.util.ArrayList;

import meowcatch.framework.Animation;

/**
 * Created by catherineleung on 2017-01-05.
 */
public class StartingClass extends Applet implements Runnable, KeyListener {

    enum GameState {
        Running, Dead
    }

    GameState state = GameState.Running;

    public static Cat cat;
    private ArrayList<FallingObject> sushiList = new ArrayList<FallingObject>();
    private ArrayList<FallingObject> milkList = new ArrayList<FallingObject>();
    private Image image, currentSprite, character, character2, character3, character4, character5, character6,
            character7, character8, characterJumped, characterJumpedLeft, sushi, milk, heart, background;
    private URL charBase, charBase2, charBase3, charBase4, charBase5, charBase6,
            charBase7, charBase8, charJumpedBase, charJumpedLeftBase, sushiBase, milkBase, heartBase, bgBase;
    private Graphics second;
    private static Background bg1, bg2;
    private Animation runAnim, runLeftAnim;
    public static int score = 0;
    public static int missed = 0;
    private Font font = new Font("Verdana", Font.BOLD, 30);
    private Font livesFont = new Font("Verdana", Font.BOLD, 15);

    @Override
    public void init() {
        setSize(480, 800);
        setBackground(Color.BLACK);

        // ensure that when game starts, applet takes over
        // input goes directly to applet
        setFocusable(true);

        // add implemented KeyListener
        addKeyListener(this);

        // assign applet window to frame variable
        Frame frame = (Frame) this.getParent().getParent();
        frame.setTitle("MeowCatch");
        try {
            // define URL base
            charBase = this.getClass().getResource("/data/character.png");
            charBase2 = this.getClass().getResource("/data/character2.png");
            charBase3 = this.getClass().getResource("/data/character3.png");
            charBase4 = this.getClass().getResource("/data/character4.png");

            charBase5 = this.getClass().getResource("/data/leftcharacter.png");
            charBase6 = this.getClass().getResource("/data/leftcharacter2.png");
            charBase7 = this.getClass().getResource("/data/leftcharacter3.png");
            charBase8 = this.getClass().getResource("/data/leftcharacter4.png");

            charJumpedBase = this.getClass().getResource("/data/character3.png");
            charJumpedLeftBase = this.getClass().getResource("/data/leftcharacter3.png");

            sushiBase = this.getClass().getResource("/data/sushi.png");
            milkBase = this.getClass().getResource("/data/milk.png");
            heartBase = this.getClass().getResource("/data/heart.gif");

            bgBase = this.getClass().getResource("/data/background.png");
        } catch (Exception e) {
            // TODO: handle exception
        }

        // image setups (assign value to character and background)
        character = getImage(charBase);
        character2 = getImage(charBase2);
        character3 = getImage(charBase3);
        character4 = getImage(charBase4);

        character5 = getImage(charBase5);
        character6 = getImage(charBase6);
        character7 = getImage(charBase7);
        character8 = getImage(charBase8);

        characterJumped = getImage(charJumpedBase);
        characterJumpedLeft = getImage(charJumpedLeftBase);

        sushi = getImage(sushiBase);
        milk = getImage(milkBase);
        background = getImage(bgBase);
        heart = getImage(heartBase);

        // run right
        runAnim = new Animation();
        runAnim.addFrame(character, 90);
        runAnim.addFrame(character2, 90);
        runAnim.addFrame(character3, 90);
        runAnim.addFrame(character4, 90);

        // run left
        runLeftAnim = new Animation();
        runLeftAnim.addFrame(character5, 90);
        runLeftAnim.addFrame(character6, 90);
        runLeftAnim.addFrame(character7, 90);
        runLeftAnim.addFrame(character8, 90);
    }

    @Override
    public void start() {
        bg1 = new Background(0, 0);
        bg2 = new Background(1024, 0);

        cat = new Cat();

        // generate 10 random sushis
        for (int i = 0; i < 10; i++) {
            FallingObject sushi = new FallingObject("sushi");
            sushiList.add(sushi);
        }
        for (int i = 0; i < 2; i++) {
            FallingObject milk = new FallingObject("milk");
            milkList.add(milk);
        }

        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public void stop() {
        super.stop();
    }

    @Override
    public void destroy() {
        super.destroy();
    }

    @Override
    public void run() {
        if (state == GameState.Running) {
            while(true) {
                cat.update();
                checkForCollision();
                if (cat.isJumped()) {
                    if (cat.getDirection() == "right") {
                        currentSprite = characterJumped;
                    } else {
                        currentSprite = characterJumpedLeft;
                    }
                } else if(cat.isJumped() == false && cat.isDucked() == false && cat.isMovingRight() == false && cat.isMovingLeft() == false) {
                    if (cat.getDirection() == "right") {
                        currentSprite = character;
                    } else {
                        currentSprite = character5;
                    }
                } else if(cat.isMovingRight() == true && cat.isMovingLeft() == false) {
                    currentSprite = runAnim.getImage();
                } else if(cat.isMovingRight() == false && cat.isMovingLeft() == true) {
                    currentSprite = runLeftAnim.getImage();
                }

                if (sushiList.size() == 10 || sushiList.size() == 0) {
                    // generate 10 random sushis
                    for (int i = 0; i < 10; i++) {
                        FallingObject sushi = new FallingObject("sushi");
                        sushiList.add(sushi);
                    }
                }

                if (milkList.size() == 2 || sushiList.size() == 0) {
                    // generate 2 random milks
                    for (int i = 0; i < 2; i++) {
                        FallingObject milk = new FallingObject("milk");
                        milkList.add(milk);
                    }
                }

                if (sushiList.size() > 0) {
                    FallingObject o = (FallingObject) sushiList.get(0);
                    FallingObject o1 = (FallingObject) sushiList.get(1);
                    FallingObject o2 = (FallingObject) sushiList.get(2);
                    if (o.isVisible() == true) {
                        o.update();
                    }
                    if (o1.isVisible() == true) {
                        o1.update();
                    }
                    if (o2.isVisible() == true) {
                        o2.update();
                    }
                    if (o.isVisible() == false) {
                        sushiList.remove(0);
                    } if (o1.isVisible() == false) {
                        sushiList.remove(1);
                    } if (o2.isVisible() == false) {
                        sushiList.remove(2);
                    }
                }

                if (milkList.size() > 0) {
                    FallingObject o = (FallingObject) milkList.get(0);
                    FallingObject o1 = (FallingObject) milkList.get(1);
                    if (o.isVisible() == true) {
                        o.update();
                    } if (o.isVisible() == false) {
                        milkList.remove(0);
                    } if (o1.isVisible() == true) {
                        o1.update();
                    } if (o1.isVisible() == false) {
                        milkList.remove(1);
                    }
                }

                animate();
                // calls paint method every 17 ms
                repaint();
                try {
                    Thread.sleep(17);
                } catch(InterruptedException e) {
                    e.printStackTrace();
                }
                if (cat.alive == false)
                    state = GameState.Dead;
            }
        }

    }

    private void checkForCollision() {
        int catX = cat.getCenterX();
        int catY = cat.getCenterY();
        int catRadius = cat.getRadius();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()) {
            case KeyEvent.VK_UP:
                System.out.println("Move up");
                break;
            case KeyEvent.VK_LEFT:
                cat.setDirection("left");
                cat.moveLeft();
                cat.setMovingLeft(true);
                cat.setMovingRight(false);
                break;
            case KeyEvent.VK_RIGHT:
                cat.setDirection("right");
                cat.moveRight();
                cat.setMovingRight(true);
                cat.setMovingLeft(false);
                break;
            case KeyEvent.VK_SPACE:
                cat.jump();
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch(e.getKeyCode()) {
            case KeyEvent.VK_UP:
                System.out.println("Stop moving up");
                break;
            case KeyEvent.VK_DOWN:
                currentSprite = runAnim.getImage();
                cat.setDucked(false);
                break;
            case KeyEvent.VK_LEFT:
                cat.stopLeft();
                break;
            case KeyEvent.VK_RIGHT:
                cat.stopRight();
                break;
            case KeyEvent.VK_SPACE:
                break;
        }
    }

    @Override
    public void update(Graphics g) {
        if (image == null) {
            image = createImage(this.getWidth(), this.getHeight());
            second = image.getGraphics();
        }
        second.setColor(getBackground());
        second.fillRect(0, 0, getWidth(), getHeight());
        second.setColor(getForeground());
        paint(second);

        // image, x, y, observer
        g.drawImage(image, 0, 0, this);
    }

    // draw graphics to screen
    @Override
    public void paint(Graphics g) {
        if (state == GameState.Running) {
            g.drawImage(background, bg1.getBgX(), bg1.getBgY(), this);
            g.drawImage(background, bg2.getBgX(), bg2.getBgY(), this);
            g.drawImage(heart, 15, 15, this);

            if (sushiList.size() > 0) {
                FallingObject o = (FallingObject) sushiList.get(0);
                g.drawImage(sushi, o.getCenterX() - 50, o.getCenterY() - 50, this);
                FallingObject o1 = (FallingObject) sushiList.get(1);
                g.drawImage(sushi, o1.getCenterX() - 50, o1.getCenterY() - 50, this);
                FallingObject o2 = (FallingObject) sushiList.get(2);
                g.drawImage(sushi, o2.getCenterX() - 50, o2.getCenterY() - 50, this);
            }

            if (milkList.size() > 0) {
                FallingObject o = (FallingObject) milkList.get(0);
                FallingObject o1 = (FallingObject) milkList.get(1);
                g.drawImage(milk, o.getCenterX() - 50, o.getCenterY() - 50, this);
                g.drawImage(milk, o1.getCenterX() - 50, o1.getCenterY() - 50, this);
            }

            g.drawImage(currentSprite, cat.getCenterX() - 61, cat.getCenterY() - 63, this);

            g.setFont(livesFont);
            g.setColor(Color.WHITE);
            g.drawString(Integer.toString(cat.lives), 35, 28);
            g.setFont(font);
            g.drawString(Integer.toString(score), 240, 200);
        }
        else if (state == GameState.Dead) {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, 800, 480);
            g.setColor(Color.WHITE);
            g.drawString("u ded", 360, 240);
        }
    }

    public static Background getBg1() {
        return bg1;
    }

    public static Background getBg2() {
        return bg2;
    }

    public void animate() {
        runAnim.update(10);
        runLeftAnim.update(10);
    }
}
