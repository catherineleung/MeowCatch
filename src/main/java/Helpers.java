/**
 * Created by Catherine on 2019-02-04.
 */
public class Helpers {
    public static int randomWithRange(int min, int max)
    {
        int range = (max - min) + 1;
        return (int)(Math.random() * range) + min;
    }
}
