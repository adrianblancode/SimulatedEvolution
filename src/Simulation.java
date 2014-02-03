import java.util.ArrayList;

public class Simulation {

    private int WIDTH;
    private int HEIGHT;
    private int [] simulationArray;
    private ArrayList<Bacteria> bacteriaList;

    public Simulation(int W, int H){
        WIDTH = W;
        HEIGHT = H;
        simulationArray = new int[WIDTH * HEIGHT];
        bacteriaList = new ArrayList<Bacteria>();
    }

    public void run(){

        killOldBacteria();

        //Adds a new bacteria to the simulation
        Bacteria b = new Bacteria(WIDTH, HEIGHT);
        bacteriaList.add(b);

        //Each bacteria acts independently (very prototype)
        for(int i = 0; i < bacteriaList.size(); i++){
            bacteriaList.get(i).act();
        }
    }

    public void killOldBacteria(){

        Bacteria b;

        for(int i = 0; i < bacteriaList.size(); i++){
            b = bacteriaList.get(i);

            if (b.getAge() > 100){
                bacteriaList.remove(b);
            }
        }
    }

    //Converts 2D coordinates into our 1D matrix
    public int simulationGetPos(int w, int h){
        return (h * WIDTH) + w;
    }

    //Placeholder for adding a bacteria to the simulation matrix
    public void addSimulation(Bacteria b){
        simulationArray[simulationGetPos(b.getXpos(), b.getYpos())] = 0;
    }

    public ArrayList<Bacteria> getBacteriaList(){
        return bacteriaList;
    }
}
