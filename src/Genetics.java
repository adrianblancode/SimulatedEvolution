import java.util.Random;

/**
 * Created by Adrian on 2014-02-24.
 */
public class Genetics {

    private float aggression; //herbivore vs carnivore
    //private float speed; implement later

    private float plantAttraction;
    private float herbivoreAttraction;
    private float carnivoreAttraction;
    private float dyingAttraction;

    //Creates a gene with pre-defined values.
    public Genetics() {
    	Random r = new Random();
        setAggression((float) ((r.nextFloat()*2)-1));
        setPlantAttraction((getAggression()>0) ? 0 : getAggression()*(-1));
        setHerbivoreAttraction(0f);
        setCarnivoreAttraction((getAggression()>0) ? 0 : getAggression()*(-1));
        
        if (getAggression() < 0) {
            setCarnivoreAttraction(-1f);
        }
    }
    
    // Creates a copy of some existing genetics.
    public Genetics(Genetics oldGen){
        setAggression(oldGen.getAggression());
        setPlantAttraction(oldGen.getPlantAttraction());
        setHerbivoreAttraction(oldGen.getHerbivoreAttraction());
        setCarnivoreAttraction(oldGen.getCarnivoreAttraction());
        setDyingAttraction(oldGen.getDyingAttraction());
        System.out.println("New bacteria! Aggression: "+oldGen.getAggression());
    }

    public float getAggression() {
        return aggression;
    }

    public void setAggression(float aggression) {
    	this.aggression = boundaryCheck(aggression, -1.0f, 1.0f);
    }

    public float getPlantAttraction() {
        return plantAttraction;
    }

    public void setPlantAttraction(float plantAttraction) {
    	this.plantAttraction = boundaryCheck(plantAttraction, 0.0f, 1.0f);
    }

    public float getHerbivoreAttraction() {
        return herbivoreAttraction;
    }

    public void setHerbivoreAttraction(float herbivoreAttraction) {
    	this.herbivoreAttraction = boundaryCheck(herbivoreAttraction, -1.0f, 1.0f);
    }

    public float getCarnivoreAttraction() {
        return carnivoreAttraction;
    }

    public void setCarnivoreAttraction(float carnivoreAttraction) {
           this.carnivoreAttraction = boundaryCheck(carnivoreAttraction, -1.0f, 1.0f);
    }

	public float getDyingAttraction() {
		return dyingAttraction;
	}

	public void setDyingAttraction(float dyingAttraction) {
        this.dyingAttraction = boundaryCheck(dyingAttraction, 0.0f, 1.0f);
	}
	
	private float boundaryCheck(float value, float lowerBound, float upperBound) {
		if (value > upperBound) {
			return upperBound;
		}
		else if (value < lowerBound) {
			return lowerBound;
		}
		else {
			return value;
		}
	}
    
}
