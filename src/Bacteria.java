import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

public class Bacteria extends SimulationEntity{

    private int age;
    private boolean dying;
    private Genetics gen;
    private Random rand;

    private Vector lastMovement;

    public Bacteria(){
        this(new Genetics());
    }
    
    public Bacteria(Genetics genetics){
    	rand = new Random();
    	lastMovement = new Vector(rand.nextInt(3) - 1, rand.nextInt(3) - 1);
        age = 0;
        setDead(false);
        setDying(false);
        setEnergy((int)(Constants.maxEnergy * 0.5));
        gen = genetics;
        spawn();
    }
    
    public Bacteria(Genetics genetics, int xpos, int ypos){
    	this(genetics);

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
        
        //We give a small positive movement bonus to herbivores, and negative to carnivores
        double bonus = gen.getAggression()*(-0.20);
        if ((0.5*(1-getHunger())) + 0.5 + bonus < rand.nextDouble()) {
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

            int energy = Math.min(pl.getEnergy(), 20);
            pl.dropEnergy(energy);
            addEnergy(energy - (int) (energy * (getGenetics().getAggression()+0.5)));
        }
    }

    private void eatBacteria(Bacteria bac){
    	
    	int e = bac.getEnergy();
    	
        if (!bac.isDead() && e > 0) {

            if(bac.isDying() || (this.getGenetics().getAggression() - bac.getGenetics().getAggression()) > 0.5){ //Scale by hunger later
            	int eatenEnergy = Math.min(e, 50);
                bac.dropEnergy(eatenEnergy);
                if (e - eatenEnergy <= 0) {
                	bac.setDead(true);
                }

                addEnergy(eatenEnergy + (int) (eatenEnergy * getGenetics().getAggression() - 0.5));
            }
        }
    }

    private Vector see(Vector dis, SimulationEntity otherObject){

        double len = dis.getLength();

        //We only act on what's in range of vision
        if(len > 0 && len <= Constants.maxVision){

            //First we normalize the vector to get the direction
            dis.normalize();

            if (otherObject.isPlant()) {
            	dis.scale(gen.getPlantAttraction());
            }
            else if (otherObject.isBacteria()) {
            	if (!otherObject.isDying()) {

            		//We classify depending on whether their aggression is above or below zero. 
            		//For example at 0.2 aggression we classify as 80% omnivore, 20% carnivore 
            		
                    //If the bacteria is exactly an omnivore
            		if (otherObject.getGenetics().getAggression() == 0) {
            			dis.scale(gen.getOmnivoreAttraction());
                    //If leans towards herbivore
            		} else if (otherObject.getGenetics().getAggression() < 0){
            			dis.scale(gen.getHerbivoreAttraction() * -otherObject.getGenetics().getAggression() + gen.getOmnivoreAttraction() * (1 + otherObject.getGenetics().getAggression()));
                    //If leans toward carnivore
            		} else if (otherObject.getGenetics().getAggression() > 0){
                        dis.scale(gen.getCarnivoreAttraction() * otherObject.getGenetics().getAggression() + gen.getOmnivoreAttraction() * (1 - otherObject.getGenetics().getAggression()));
                    }
            	} else {
            		dis.scale(gen.getDyingAttraction());
            	}
            }

            //We make sure that vectors further away are less important
            dis.scale(1 / Math.pow(len, 0.8));

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

        int xdestination = 0;
        int ydestination = 0;

        if(movement.isNull()){
             xdestination = getXpos() + (int) lastMovement.getX();
             ydestination = getYpos() + (int) lastMovement.getY();
        }
        else {
            xdestination = getXpos() + (int) movement.getX();
            ydestination = getYpos() + (int) movement.getY();

            lastMovement = movement;
        }

        //If there is a bacteria blocking that position, do nothing
        for(Bacteria bac : baclist){
            if(bac != this && Math.abs(bac.getXpos() - xdestination) <= 0 && Math.abs(bac.getYpos() - ydestination) <= 0){
                return;
            }
        }

        setXpos(xdestination);
        setYpos(ydestination);

        //We will need to have a random initial movement just in case the movement vector is 0,
        //so the bacteria can always continue moving in the last direction
    }

    private void spawn(){
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

            if(getEnergy() < (int)(Constants.maxEnergy * 0.25)){
                setDying(true);
            }
        }
        else{
            setEnergy(0);
            setDead(true);
        }
    }

    public float getHunger(){
        return (float)1-((float)getEnergy() / Constants.maxEnergy);
    }
    
    public Genetics getGenetics() {
		return gen;
	}

	public Color getColor() {
        //Background is (30, 30 , 30), with 50 it's still visible while almost dead
        int red = 50 + (int) ((100 + 100 * gen.getAggression()) * (1-getHunger()));
        int blue = 50 + (int)((100 - 100 * gen.getAggression()) * (1-getHunger()));
        int green = 50 + (int) (1-getHunger());
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
        
        if(isDying()){
            dropEnergy(1);
        }
        else{
            dropEnergy(2);
        }

        if (getEnergy() <= 0) {
        	setDead(true);
        }
    }
    
    public boolean isBacteria() {
    	return true;
    }
}
