import java.util.ArrayList;
import java.util.Iterator;

public class Simulation {

    private ArrayList<Bacteria> bacteriaList;
    private ArrayList<SimulationEntity> foodList; //empty for now
    private int ticks;

    public Simulation(){
        bacteriaList = new ArrayList<Bacteria>();
        ticks = 0;
    }

    public void run(){

        incrementTicks();

        //We remove the old bacteria before the simulation tick, so that it does not affect it mid simulation
        removeDeadBacteria();

        //Adds a new bacteria to the simulation
        Bacteria b = new Bacteria();
        bacteriaList.add(b);

        //Each bacteria acts independently (very prototype)
        for(Bacteria bac : bacteriaList){
            bac.act(bacteriaList);
        }
    }

    //Removes all the dead bacteria from the list and sets their pointers to null
    public void removeDeadBacteria(){

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

    public ArrayList<Bacteria> getBacteriaList(){
        return bacteriaList;
    }

    public void incrementTicks(){
        ticks = ticks + 1;
    }

    public int getTicks(){
        return ticks;
    }
}
