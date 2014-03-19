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
        setPlantAttraction(getAggression()*(-1));
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
    }

    public float getAggression() {
        return aggression;
    }

    public void setAggression(float aggression) {
        if(aggression >= -1.0 && aggression <= 1.0){
            this.aggression = aggression;
        }
    }

    public float getPlantAttraction() {
        return plantAttraction;
    }

    public void setPlantAttraction(float foodAttraction) {
        if(foodAttraction >= 0 && foodAttraction <= 1.0){
            this.plantAttraction = foodAttraction;
        }
    }

    public float getHerbivoreAttraction() {
        return herbivoreAttraction;
    }

    public void setHerbivoreAttraction(float herbivoreAttraction) {
        if(herbivoreAttraction >= -1.0 && herbivoreAttraction <= 1.0){
            this.herbivoreAttraction = herbivoreAttraction;
        }
    }

    public float getCarnivoreAttraction() {
        return carnivoreAttraction;
    }

    public void setCarnivoreAttraction(float carnivoreAttraction) {
        if(carnivoreAttraction >= -1.0 && carnivoreAttraction <= 1.0){
            this.carnivoreAttraction = carnivoreAttraction;
        }
    }

	public float getDyingAttraction() {
		return dyingAttraction;
	}

	public void setDyingAttraction(float dyingAttraction) {
        if(dyingAttraction >= 0 && dyingAttraction <= 1.0){
        	this.dyingAttraction = dyingAttraction;
        }
	}
    
}
