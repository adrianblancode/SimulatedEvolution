import java.util.ArrayList;
import java.util.Iterator;

public class Simulation {

    private ArrayList<Bacteria> bacteriaList;
    private ArrayList<Plant> foodList;
    private int ticks;

    public Simulation(){
        bacteriaList = new ArrayList<Bacteria>();
        foodList = new ArrayList<Plant>();
        ticks = 0;
    }

    public void run() {

        incrementTicks();

        //We remove the old bacteria before the simulation tick, so that it does not affect it mid simulation
        removeDeadBacteria();
        removeDeadPlants();

        //Adds a new bacteria to the simulation
        Bacteria b = new Bacteria();
        bacteriaList.add(b);
        
        
        // Add a new plant to the simulation
        Plant p = new Plant();
        foodList.add(p);
        

        //Each bacteria acts independently (very prototype)
        for(Bacteria bac : bacteriaList){
            bac.act(foodList);
        }
    }

    //Removes all the dead bacteria from the list and sets their pointers to null
    public void removeDeadBacteria() {

        //We use an iterator since we are removing elements while iterating
        Iterator<Bacteria> iter = bacteriaList.iterator();

        while(iter.hasNext()){

            Bacteria bac = iter.next();

            if(bac.isDead()){
                iter.remove();
                bac = null;
            }
        }
    }
    
    
    //Removes all the dead plants from the list and sets their pointers to null
    public void removeDeadPlants() {

        //We use an iterator since we are removing elements while iterating
        Iterator<Plant> iter = foodList.iterator();

        while(iter.hasNext()){

            Plant p = iter.next();

            if(p.isDead()){
                iter.remove();
                p = null;
            }
        }
    }

    public ArrayList<Bacteria> getBacteriaList(){
        return bacteriaList;
    }
    
    public ArrayList<Plant> getFoodList(){
        return foodList;
    }

    public void incrementTicks(){
        ticks = ticks + 1;
    }

    public int getTicks(){
        return ticks;
    }
}
