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

    private Cat cat;
    private Monster m1, m2;
    private Image image, currentSprite, character, character2, character3, character4, character5, character6,
            character7, character8, characterDown, characterJumped, characterJumpedLeft, monster, monster2,
            monster3, monster4, monster5, background;
    private URL charBase, charBase2, charBase3, charBase4, charBase5, charBase6,
            charBase7, charBase8, charDownBase, charJumpedBase, charJumpedLeftBase, monsterBase,
            monsterBase2, monsterBase3, monsterBase4, monsterBase5, bgBase;
    private Graphics second;
    private static Background bg1, bg2;
    private Animation runAnim, runLeftAnim, manim;

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
        frame.setTitle("ROBOT ADVENTURE");
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

            charDownBase = this.getClass().getResource("/data/down.png");
            charJumpedBase = this.getClass().getResource("/data/character3.png");
            charJumpedLeftBase = this.getClass().getResource("/data/leftcharacter3.png");

            monsterBase = this.getClass().getResource("/data/monster.png");
            monsterBase2 = this.getClass().getResource("/data/monster2.png");
            monsterBase3 = this.getClass().getResource("/data/monster3.png");
            monsterBase4 = this.getClass().getResource("/data/monster4.png");
            monsterBase5 = this.getClass().getResource("/data/monster5.png");

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

        characterDown = getImage(charDownBase);
        characterJumped = getImage(charJumpedBase);
        characterJumpedLeft = getImage(charJumpedLeftBase);

        monster = getImage(monsterBase);
        monster2 = getImage(monsterBase2);
        monster3 = getImage(monsterBase3);
        monster4 = getImage(monsterBase4);
        monster5 = getImage(monsterBase5);
        background = getImage(bgBase);

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

        manim = new Animation();
        manim.addFrame(monster, 100);
        manim.addFrame(monster2, 100);
        manim.addFrame(monster3, 100);
        manim.addFrame(monster4, 100);
        manim.addFrame(monster5, 100);
        manim.addFrame(monster4, 100);
        manim.addFrame(monster3, 100);
        manim.addFrame(monster2, 100);
    }

    @Override
    public void start() {
        bg1 = new Background(0, 0);
        bg2 = new Background(1024, 0);

        cat = new Cat();
        m1 = new Monster(340, 350);
        m2 = new Monster(700, 360);
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
        while(true) {
            cat.update();
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

            ArrayList projectiles = cat.getProjectiles();
            for (int i = 0; i < projectiles.size(); i++) {
                Projectile p = (Projectile) projectiles.get(i);
                if (p.isVisible() == true) {
                    p.update();
                } else {
                    projectiles.remove(i);
                }
            }
            m1.update();
            m2.update();
            bg1.update();
            bg2.update();

            animate();
            // calls paint method every 17 ms
            repaint();
            try {
                Thread.sleep(17);
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
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
            case KeyEvent.VK_DOWN:
                currentSprite = characterDown;
                if (cat.isJumped() == false) {
                    cat.setDucked(true);
                    cat.setSpeedX(0);
                }
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
            case KeyEvent.VK_CONTROL:
                if (cat.isDucked() == false && cat.isJumped() == false) {
                    cat.shoot();
                }
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
        g.drawImage(background, bg1.getBgX(), bg1.getBgY(), this);
        g.drawImage(background, bg2.getBgX(), bg2.getBgY(), this);

        ArrayList projectiles = cat.getProjectiles();
        for (int i = 0; i < projectiles.size(); i++) {
            Projectile p = (Projectile) projectiles.get(i);
            g.setColor(Color.YELLOW);
            g.fillRect(p.getX(), p.getY(), 10, 5);
        }


        g.drawImage(currentSprite, cat.getCenterX() - 61, cat.getCenterY() - 63, this);
        //g.drawImage(manim.getImage(), m1.getCenterX() - 48, m1.getCenterY() - 48, this);
        //g.drawImage(manim.getImage(), m2.getCenterX() - 48, m2.getCenterY() - 48, this);
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
        manim.update(50);
    }
}
