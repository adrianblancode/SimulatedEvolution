import java.awt.Color;
import java.util.Random;

public class Plant extends SimulationEntity {

    private Random rand;
    private Color color;
    
    public Plant() {
    	rand = new Random();
    	color = new Color(0, 255, 0);

    	this.setEnergy(100);
    	
    	spawn();
    }
	
    private void spawn() {
        int value = rand.nextInt(Constants.WIDTH);
        setXpos(value);

        value = rand.nextInt(Constants.HEIGHT);
        setYpos(value);
    }

	public Color getColor() {
		return color;
	}
	
}
