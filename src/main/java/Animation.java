import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Catherine on 2019-02-04.
 */
public class Animation {
    // contain AnimFrame objects that will have 2 values (animation and time displayed)
    private ArrayList frames;
    // int value index of current frame in ArrayList
    private int currentFrame;
    // keeps track of how much time has elapsed
    private long animationTime;
    // total duration that animation will be displayed for
    private long totalDuration;

    // constructor
    public Animation() {
        frames = new ArrayList();
        totalDuration = 0;

        // if animationTime and currentFrame are synchronized, will always be called sequentially
        synchronized (this) {
            animationTime = 0;
            currentFrame = 0;
        }
    }

    // take AnimFrame object and append to ArrayList frames
    public synchronized void addFrame(Image image, long duration) {
        totalDuration += duration;
        frames.add(new AnimFrame(image, totalDuration));
    }

    // called repeatedly and will switch frames as necessary
    public synchronized void update(long elapsedTime) {
        if (frames.size() > 1) {
            animationTime += elapsedTime;
            if (animationTime >= totalDuration) {
                animationTime = animationTime % totalDuration;
                currentFrame = 0;
            }
            while (animationTime > getFrame(currentFrame).endTime) {
                currentFrame++;
            }
        }
    }

    // returns current AnimFrame of animation sequence
    private AnimFrame getFrame(int i) {
        return (AnimFrame) frames.get(i);
    }

    // returns image that belongs to the currentFrame
    public synchronized Image getImage() {
        if (frames.size() == 0) {
            return null;
        } else {
            return getFrame(currentFrame).image;
        }
    }

    private class AnimFrame {
        Image image;
        long endTime;

        public AnimFrame(Image image, long endTime) {
            this.image = image;
            this.endTime = endTime;
        }
    }
}
