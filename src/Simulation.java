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
        Bacteria b = new Bacteria(WIDTH, HEIGHT);
        bacteriaList.add(b);

        for(int i = 0; i < bacteriaList.size(); i++){
            bacteriaList.get(i).act();
        }
    }

    //Converts 2D coordinates into our 1D matrix
    public int simulationGetPos(int w, int h){
        return (h * WIDTH) + w;
    }

    public void addSimulation(Bacteria b){
        simulationArray[simulationGetPos(b.getXpos(), b.getYpos())] = 0;
    }

    public ArrayList<Bacteria> getBacteriaList(){
        return bacteriaList;
    }
}
