import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Simulation {

    private ArrayList<Bacteria> bacteriaList;
    private ArrayList<Plant> plantList;
    private int ticks;

    public Simulation(){
        bacteriaList = new ArrayList<Bacteria>();
        plantList = new ArrayList<Plant>();
        ticks = 0;
    }
    
    public void start(int numBacteria){
    	for (int i = 0; i < numBacteria; ++i) {
            //Adds a new bacteria to the simulation
            Bacteria b = new Bacteria();
            bacteriaList.add(b);
            
            // Add a new plant to the simulation
            Plant p = new Plant();
            plantList.add(p);
            
    	}
    }

    public void run() {

        incrementTicks();

        // We do the reproduction first. That way the old bacteria will be cleaned away right afterwards.
        doReproduction();

        //We remove the old bacteria before the simulation tick, so that it does not affect it mid simulation
        removeDeadBacteria();
        removeDeadPlants();

        // Add a new plant to the simulation
        Random r = new Random();
        if (r.nextInt(2) == 0) { // Fulkodat, g�r f�rre plants.
        	Plant p = new Plant();
        	plantList.add(p);
        }

        //Each bacteria acts independently
        for(Bacteria bac : bacteriaList){
            bac.act(plantList, bacteriaList);
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

                System.out.println("Dead bacteria removed. Num bacteria:"+bacteriaList.size());
            }
        }
    }
    
    
    //Removes all the dead plants from the list and sets their pointers to null
    public void removeDeadPlants() {

        //We use an iterator since we are removing elements while iterating
        Iterator<Plant> iter = plantList.iterator();

        while(iter.hasNext()){

            Plant p = iter.next();

            if(p.isDead()){
                iter.remove();
                p = null;
            }
        }
    }
    
    public void doReproduction() {
    	// We first put the new bacteria into a list and add them afterwards.
    	// That way the new bacteria won't be run in the loop.
    	ArrayList<Bacteria> newBacteria = new ArrayList<Bacteria>();

    	for (Bacteria b : bacteriaList) {
    		// If it meets the reproduction criteria and isn't dead, then reproduce.
    		if (b.getAge() >= Constants.reproductionAge && b.getEnergy() >= Constants.reproductionEnergy && !b.isDead() && !b.isDying()) {
    			Genetics[] newGens = makeNewGenetics(b.getGenetics());
    			newBacteria.add(new Bacteria(newGens[0], b.getXpos()-10, b.getYpos()));
    			newBacteria.add(new Bacteria(newGens[1], b.getXpos()+10, b.getYpos()));
    			
    			// Kills the old bacteria
    			b.setDead(true);
    		}
    	}
    	
    	// Add the new bacteria to the bacteria list
    	for (Bacteria b : newBacteria) {
    		bacteriaList.add(b);
    	}
    }
    
    // Returns a pair of new genetics based on the input genetics.
    // The return array will always have 2 elements.
    private Genetics[] makeNewGenetics(Genetics oldGen) {
    	System.out.println("makeNewGenetics() is running");
    	Genetics gen1 = new Genetics(oldGen);
    	Genetics gen2 = new Genetics(oldGen);
    	Random r =  new Random();
    	
    	// OBS inte s�ker p� att detta blir helt r�tt.
    	// f�rst har vi ett tal mellan 0.0 och 1.0, dra ifr�n en halv f�r -0.5-0.5, multiplicera med 2*0.2 = 0.4 ger mellan -0.2 och 0.2
    	// r�tt?
    	float offset = (r.nextFloat()-0.5f)*(Constants.maxAttributeOffset*2);
    	int attribute = r.nextInt(4); // NOTE: H�rdkodatat. Fyra olika attributes att �ndra.
    	
    	switch (attribute) {
    	case 0:
    		gen1.setAggression(gen1.getAggression()+offset);
    		gen2.setAggression(gen2.getAggression()-offset);
    		break;
    	case 1:
    		gen1.setFoodAttraction(gen1.getFoodAttraction()+offset);
    		gen2.setFoodAttraction(gen2.getFoodAttraction()-offset);
    		break;
    	case 2:
    		gen1.setHerbivoreAttraction(gen1.getHerbivoreAttraction()+offset);
    		gen2.setHerbivoreAttraction(gen2.getHerbivoreAttraction()-offset);
    		break;
    	case 3:
    		gen1.setCarnivoreAttraction(gen1.getCarnivoreAttraction()+offset);
    		gen2.setCarnivoreAttraction(gen2.getCarnivoreAttraction()-offset);
    		break;
    		
    	}
    	
		return new Genetics[] {gen1, gen2};
    }

    public ArrayList<Bacteria> getBacteriaList(){
        return bacteriaList;
    }
    
    public ArrayList<Plant> getPlantList(){
        return plantList;
    }

    public void incrementTicks(){
        ticks = ticks + 1;
    }

    public int getTicks(){
        return ticks;
    }
}
