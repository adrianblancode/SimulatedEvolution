/**
 * Created by Adrian on 2014-02-19.
 */
public final class Constants {

    public static int WIDTH = 1000;
    public static int HEIGHT = 600;
    public static int maxAge = 10000;
    public static int maxVision = 400;
    public static int maxEnergy = 1000;
    public static int absoluteMaxAge = (int)(maxAge*1.2);
    
    public static int reproductionAge = 500;
    public static int reproductionEnergy = (int) (maxEnergy * 0.9);
    public static float maxAttributeOffset = 0.2f;

    private Constants(){
        throw new AssertionError();
    }
}
