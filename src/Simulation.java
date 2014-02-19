import java.util.ArrayList;

public class Simulation {

    private ArrayList<Bacteria> bacteriaList;
    private ArrayList<SimulationEntity> foodList; //empty for now

    public Simulation(){
        bacteriaList = new ArrayList<Bacteria>();
    }

    public void run(){

        //We remove the old bacteria before the simulation tick, so that it does not affect it mid simulation
        killBacteria();

        //Adds a new bacteria to the simulation
        Bacteria b = new Bacteria();
        bacteriaList.add(b);

        //Each bacteria acts independently (very prototype)
        for(int i = 0; i < bacteriaList.size(); i++){
            bacteriaList.get(i).act(bacteriaList);
        }
    }

    //Removes all the dead bacteria from the list and sets their pointers to null
    public void killBacteria(){

        Bacteria b;

        for(int i = 0; i < bacteriaList.size(); i++){
            b = bacteriaList.get(i);

            if(b.isDead()){
                bacteriaList.remove(b);
                b = null;
            }
        }
    }

    public ArrayList<Bacteria> getBacteriaList(){
        return bacteriaList;
    }
}
