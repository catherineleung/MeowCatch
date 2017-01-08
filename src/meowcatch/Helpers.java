package meowcatch;

/**
 * Created by catherineleung on 2017-01-08.
 */
public class Helpers {
    public static int randomWithRange(int min, int max)
    {
        int range = (max - min) + 1;
        return (int)(Math.random() * range) + min;
    }
}
