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
        setEnergy(Constants.maxEnergy/2);
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
    public void act(ArrayList<Plant> pl){

        incrementAge();

        Vector movement = new Vector(0, 0);
        Vector dis;
        double len;

        if(isDying()) return;

        for(Plant p : pl){

            dis = distance(p);
            
            // If we're next to a plant and it's alive. Eat it!
            if (dis.getLength() < 5 && this.getEnergy() < (int)(Constants.maxEnergy * 0.9)) {
            	if (!p.isDead() && p.getEnergy() > 0) {
            		p.dropEnergy(100);
            		if (p.getEnergy() <= 0) {
            			p.setDead(true);
            		}
            		addEnergy(Math.min(p.getEnergy(), 100));
            	}
            }
            
            len = dis.getLength();

            //We only act on what's in range of vision
            if(len > 0 && len <= Constants.maxVision){

                //First we normalize the vector to get the direction
                dis.normalize();

                //We make sure that vectors further away are less important
                dis.scale(1 / len);

                //We add the sum of all movements to the movement vector
                movement.add(dis);
            }
        }

        //Normalize the movement vector to get properly scaled end results
        movement.normalize();

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

        setXpos(checkXbounds(getXpos() + (int) movement.getX()));
        setYpos(checkYbounds(getYpos() + (int) movement.getY()));

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

    public float getHunger(){
        return ((float) getEnergy() / Constants.maxEnergy);
    }
    
    public Genetics getGen() {
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
        
        dropEnergy(1);
        if (getEnergy() == 0) {
        	setDead(true);
        }
    }

    private int checkXbounds(int x){

        if(x < 0){
            return 0;
        }

        else if(x >= Constants.WIDTH){
            return (Constants.WIDTH - 1);
        }

        else return x;
    }

    private int checkYbounds(int y){

        if(y < 0){
            return 0;
        }

        else if(y >= Constants.HEIGHT){
            return (Constants.HEIGHT - 1);
        }

        else return y;
    }

}
