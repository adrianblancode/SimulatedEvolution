import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Simulation {

    private ArrayList<Bacteria> bacteriaList;
    private ArrayList<Plant> plantList;
    private int ticks;
    
    private Logger logger;
    private int deadRemoved;
    private int reproductionsDone;

    public Simulation(){
        bacteriaList = new ArrayList<Bacteria>();
        plantList = new ArrayList<Plant>();
        ticks = 0;
        logger = new Logger();
        deadRemoved = 0;
        reproductionsDone = 0;
    }
    
    // Creates a carnivore and spawns it on a random position
    public void spawnCarnivore() {
        Genetics g = new Genetics();
        g.setAggression(1.0f);
        g.setHerbivoreAttraction(0.4f);
        g.setOmnivoreAttraction(0.2f);
        g.setCarnivoreAttraction(0.0f);
        g.setDyingAttraction(0.6f);
        g.setPlantAttraction(0.0f);

        Bacteria b = new Bacteria(g);
        bacteriaList.add(b);
    }

    // Creates a herbivore and spawns it on a random position
    public void spawnHerbivore() {
        Genetics g = new Genetics();
        g.setAggression(-1.0f);
        g.setHerbivoreAttraction(0.0f);
        g.setOmnivoreAttraction(-0.7f);
        g.setCarnivoreAttraction(-0.9f);
        g.setDyingAttraction(0.0f);
        g.setPlantAttraction(0.2f);

        Bacteria b = new Bacteria(g);
        bacteriaList.add(b);
    }
    
    public void spawnNeutral() {
    	Random r = new Random();
        Genetics g = new Genetics();
        g.setAggression((float) (0.1-r.nextFloat()*0.2));
        g.setHerbivoreAttraction((float) (0.1-r.nextFloat()*0.2));
        g.setOmnivoreAttraction(0.0f);
        g.setCarnivoreAttraction((float) (0.1-r.nextFloat()*0.2));
        g.setDyingAttraction((float) (0.1-r.nextFloat()*0.2));
        g.setPlantAttraction((float) (0.2-r.nextFloat()*0.2));

        Bacteria b = new Bacteria(g);
        bacteriaList.add(b);
    }
    
    public void spawnDying() {
    	Bacteria b =  new Bacteria();
    	b.setEnergy((int)(Constants.maxEnergy*0.29));
    	b.setDying(true);
        bacteriaList.add(b);
    }
    
    public void start(int numPlants){
    	for (int i = 0; i < numPlants; ++i) {
            //Adds a new bacteria to the simulation
           // Bacteria b = new Bacteria();
          //  bacteriaList.add(b);
            
            // Add a new plant to the simulation
            Plant p = new Plant();
            plantList.add(p);
            
    	}
    }

    public void run() {

        incrementTicks();
        
        //Logs internally every second
        if (ticks % 100 == 0) {
        	logger.doLogging(ticks, bacteriaList, plantList, deadRemoved, reproductionsDone);
        }
        
        //Writes to the logs every minute
        if (ticks % (60 * 100) == 0) {
        	logger.doPrintout();
        }

        // We do the reproduction first. That way the old bacteria will be cleaned away right afterwards.
        doReproduction();

        //We remove the old bacteria before the simulation tick, so that it does not affect it mid simulation
        removeDeadBacteria();
        removeDeadPlants();

        // Add a new plant to the simulation
        Random r = new Random();
        if (r.nextDouble() < 0.20) {
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
            	++deadRemoved;
                iter.remove();
                bac.remove();
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
                p.remove();
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
    	    	++reproductionsDone;
    	    	
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
    	float offset = (2 * (r.nextFloat() -0.5f)) * (Constants.maxAttributeOffset);
    	int attribute = r.nextInt(6); // NOTE: H�rdkodatat. Fyra olika attributes att �ndra.
    	
    	switch (attribute) {
    	case 0:
    		gen1.setAggression(gen1.getAggression()+offset);
    		gen2.setAggression(gen2.getAggression()-offset);
    		break;
    	case 1:
    		gen1.setPlantAttraction(gen1.getPlantAttraction()+offset);
    		gen2.setPlantAttraction(gen2.getPlantAttraction()-offset);
    		break;
    	case 2:
    		gen1.setHerbivoreAttraction(gen1.getHerbivoreAttraction()+offset);
    		gen2.setHerbivoreAttraction(gen2.getHerbivoreAttraction()-offset);
    		break;
    	case 3:
    		gen1.setCarnivoreAttraction(gen1.getCarnivoreAttraction()+offset);
    		gen2.setCarnivoreAttraction(gen2.getCarnivoreAttraction()-offset);
    		break;
    	case 4:
    		gen1.setDyingAttraction(gen1.getDyingAttraction()+offset);
    		gen2.setDyingAttraction(gen2.getDyingAttraction()-offset);
    		break;
    	case 5:
    		gen1.setOmnivoreAttraction(gen1.getOmnivoreAttraction()+offset);
    		gen2.setOmnivoreAttraction(gen2.getOmnivoreAttraction()-offset);
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
