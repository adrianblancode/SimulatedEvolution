import java.util.ArrayList;
import java.util.Random;

public class Bacteria extends SimulationEntity{

    private int age;
    private int dying;

    public Bacteria(){
        age = 0;
        setDead(false);
        spawn();
    }

    //How the bacteria acts each tick
    public void act(ArrayList<Bacteria> bl){

        incrementAge();

        Vector movement = new Vector(0, 0);
        Vector dis;
        double len;

        for(Bacteria bac : bl){

            dis = distance(bac);
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

        //Move according to movement vector
        //Make more pretty, now they only move diagoanlly
        if(movement.getX() > 0){
            movement.setX(1);
        }
        else if(movement.getX() < 0){
            movement.setX(-1);
        }

        if(movement.getY() > 0){
            movement.setY(1);
        }
        else if(movement.getY() < 0){
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

    //Adds one to age, if age is over max set dead
    public void incrementAge(){
        age = age + 1;

        if(age >= Constants.maxAge){
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
