import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

public class Bacteria extends SimulationEntity{

    private int age;
    private boolean dying;
    private Genetics gen;

    public Bacteria(){
        this(new Genetics());
    }
    
    public Bacteria(Genetics genetics){
        age = 0;
        setDead(false);
        setDying(false);
        setEnergy(Constants.maxEnergy / 2);
        gen = genetics;
        spawn();
        System.out.println("New bacteria created with random positions.");
    }
    
    public Bacteria(Genetics genetics, int xpos, int ypos){
        age = 0;
        setDead(false);
        setDying(false);
        setEnergy(Constants.maxEnergy/2);
        gen = genetics;

        setXpos(xpos);
        setYpos(ypos);
        
        System.out.println("New bacteria created with starting positions.");
    }

    //How the bacteria acts each tick
    public void act(ArrayList<Plant> plist, ArrayList<Bacteria> baclist){

        incrementAge();

        if(isDying() || isDead()){
            return;
        }
        
        Vector movement = new Vector(0, 0);

        for(Bacteria b : baclist){

            // If we're next to a dying bacteria, eat it!
            if (this.distance(b).getLength() < 5 && this.getEnergy() < (int)(Constants.maxEnergy * 0.9)) {
                eatBacteria(b);
            }
        	// TODO: Implementera PvP h�r!

            //We add the sum of all movements to the movement vector
            movement.add(see(this.distance(b), b));
        }
        
        for(Plant pl : plist){

            // If we're next to a plant and it's alive. Eat it!
            if (this.distance(pl).getLength() < 5 && this.getEnergy() < (int)(Constants.maxEnergy * 0.9)) {
                eatPlants(pl);
            }

            //We add the sum of all movements to the movement vector
            movement.add(see(this.distance(pl), pl));
        }
        

        //Normalize the movement vector to get properly scaled end results
        movement.normalize();

        move(movement, baclist);
    }


    private void eatPlants(SimulationEntity pl){

        if (!pl.isDead() && pl.getEnergy() > 0) {

            pl.dropEnergy(100);

            int energy = Math.min(pl.getEnergy(), 100);
            addEnergy(energy - (int) (energy * getGenetics().getAggression()));
        }
    }

    private void eatBacteria(Bacteria bac){
    	int e = bac.getEnergy();
    	
        if (!bac.isDead() && e > 0) {

            if(bac.isDying() || (this.getGenetics().getAggression() - bac.getGenetics().getAggression()) > 0.2 * getHunger()){ //Scale by hunger later
                bac.dropEnergy(e);
                bac.setDead(true);

                int energy = e;
                addEnergy(energy + (int) (energy * getGenetics().getAggression()));
            }
        }
    }

    private Vector see(Vector dis, SimulationEntity otherObject){

        double len = dis.getLength();

        //We only act on what's in range of vision
        if(len > 0 && len <= Constants.maxVision){

            //First we normalize the vector to get the direction
            dis.normalize();

            //TODO Scale according to attraction
            
            
            if (otherObject.isPlant() && getGenetics().getAggression() < 0.2) {
            	dis.scale(gen.getPlantAttraction());
            } else if (otherObject.isBacteria()) {
            	if (!otherObject.isDying()) {
            		dis.scale(gen.getCarnivoreAttraction() * (0.5 + otherObject.getGenetics().getAggression() / 2));
                	dis.scale(gen.getHerbivoreAttraction() * (0.5 - otherObject.getGenetics().getAggression() / 2));
            	} else {
            		dis.scale(gen.getDyingAttraction() * (0.5 + otherObject.getGenetics().getAggression() / 2));
            	}
            }

            //We make sure that vectors further away are less important
            dis.scale(1 / len);

            return dis;
        }
        else{
            return new Vector(0, 0);
        }
    }

	private void move(Vector movement, ArrayList<Bacteria> baclist){
        //Moves according to the movement vector
        if(movement.getX() > 0.1){
            movement.setX(1);
        }
        else if(movement.getX() < - 0.1){
            movement.setX(-1);
        }

        if(movement.getY() > 0.1){
            movement.setY(1);
        }
        else if(movement.getY() < -0.1){
            movement.setY(-1);
        }

        int xdestination = getXpos() + (int) movement.getX();
        int ydestination = getYpos() + (int) movement.getY();

        //If there is a bacteria blocking that position, do nothing
        for(Bacteria bac : baclist){
            if(bac != this && Math.abs(bac.getXpos() - xdestination) <= 0 && Math.abs(bac.getYpos() - ydestination) <= 0){
                return;
            }
        }

        setXpos(getXpos() + (int) movement.getX());
        setYpos(getYpos() + (int) movement.getY());

        //We will need to have a random initial movement just in case the movement vector is 0,
        //so the bacteria can always continue moving in the last direction
    }

    private void spawn(){
        //lol sämst, we are creating new randoms too much
        Random rand = new Random();

        //OBS hårdkodat
        int value = rand.nextInt(Constants.WIDTH);
        setXpos(value);

        value = rand.nextInt(Constants.HEIGHT);
        setYpos(value);
    }

    public int getAge(){
        return age;
    }

    public boolean isDying(){
        return dying;
    }

    public void setDying(boolean b){
        dying = b;
    }

    public void dropEnergy(int energy) {
        if(getEnergy() - energy > 0){
            setEnergy(getEnergy() - energy);

            if(getEnergy() < (int)(Constants.maxEnergy * 0.2)){
                setDying(true);
            }
        }
        else{
            setEnergy(0);
            setDead(true);
        }
    }

    public float getHunger(){
        return ((float) getEnergy() / Constants.maxEnergy);
    }
    
    public Genetics getGenetics() {
		return gen;
	}

	public Color getColor() {
        //Background is (30, 30 , 30), with 50 it's still visible while almost dead
        int red = 50 + (int) ((100 + 100 * gen.getAggression()) * getHunger());
        int blue = 50 + (int)((100 - 100 * gen.getAggression()) * getHunger());
        int green = 50 + (int) (getHunger());
        return new Color(red, green, blue);
    }

    //Adds one to age, if age is over max set dead
    public void incrementAge(){
        age = age + 1;

        if(age >= Constants.maxAge){
            setDying(true);
        }
        
        if (age >= Constants.absoluteMaxAge) {
        	setDead(true);
        	setEnergy(0);
        }
        
        if(!isDying()){
            dropEnergy(1);
        }

        if (getEnergy() == 0) {
        	setDead(true);
        }
    }
    
    public boolean isBacteria() {
    	return true;
    }
}
