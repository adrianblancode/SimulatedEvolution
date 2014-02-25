/**
 * Created by Adrian on 2014-02-24.
 */
public class Genetics {

    private float aggression; //herbivore vs carnivore
    //private float speed; implement later

    private float foodAttraction;
    private float herbivoreAttraction;
    private float carnivoreAttraction;

    //Creates a gene with pre-defined values.
    public Genetics(){
        setAggression(0f);
        setFoodAttraction(0.5f);
        setHerbivoreAttraction(0f);
        setCarnivoreAttraction(0f);
    }
    
    // Creates a copy of some existing genetics.
    public Genetics(Genetics oldGen){
        setAggression(oldGen.getAggression());
        setFoodAttraction(oldGen.getFoodAttraction());
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

    public float getFoodAttraction() {
        return foodAttraction;
    }

    public void setFoodAttraction(float foodAttraction) {
        if(foodAttraction >= -1.0 && foodAttraction <= 1.0){
            this.foodAttraction = foodAttraction;
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
}
