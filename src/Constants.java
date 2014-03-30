/**
 * Created by Adrian on 2014-02-19.
 */
public final class Constants {

    public static int WIDTH = 1600;
    public static int HEIGHT = 900;
    public static int maxAge = 10000;
    public static int maxVision = 200;
    public static int maxEnergy = 10000;
    public static int absoluteMaxAge = (int)(maxAge*1.2);
    public static int plantEnergy = 1300;
    
    public static int reproductionAge = 500;
    public static int reproductionEnergy = (int) (maxEnergy * 0.9);
    public static float maxAttributeOffset = 0.2f;
    public static float safeEating = 0.6f;
    public static float maxHungerDesperation = 0.3f;
    
    private Constants(){
        throw new AssertionError();
    }
}
