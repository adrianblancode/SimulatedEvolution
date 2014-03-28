import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class Logger {
	private ArrayList<Tuple> bacteriaCount;
	private ArrayList<Tuple> plantCount;
	private ArrayList<Tuple> dyingCount;
	private ArrayList<Tuple> aggressionValues;
	private ArrayList<Tuple> herbivoreAttractions;
	private ArrayList<Tuple> carnivoreAttractions;
	private ArrayList<Tuple> omnivoreAttractions;
	private ArrayList<Tuple> plantAttractions;
	private ArrayList<Tuple> dyingAttractions;
	private ArrayList<Tuple> birthRate;
	private ArrayList<Tuple> deathRate;

	private int oldReproductionsDone;
	private int oldDeadRemoved;
	
	public Logger() {
		bacteriaCount = new ArrayList<Tuple>();
		plantCount = new ArrayList<Tuple>();
		dyingCount = new ArrayList<Tuple>();
		aggressionValues = new ArrayList<Tuple>();
		herbivoreAttractions = new ArrayList<Tuple>();
		carnivoreAttractions = new ArrayList<Tuple>();
		omnivoreAttractions = new ArrayList<Tuple>();
		plantAttractions = new ArrayList<Tuple>();
		dyingAttractions = new ArrayList<Tuple>();
		birthRate = new ArrayList<Tuple>();
		deathRate = new ArrayList<Tuple>();
		
		oldReproductionsDone = 0;
		oldDeadRemoved = 0;
	}
	
	public void doLogging(int tick, ArrayList<Bacteria> baclist, ArrayList<Plant> plantlist, int deadRemoved, int reproductionsDone) {
		plantCount.add(new Tuple(tick, plantlist.size()));
		
		int numLiving = 0;
		int numDying = 0;
		
		for (Bacteria b : baclist) {
			if (b.isDead())
				continue; // Hoppa över döda bakterier
			
			if (b.isDying()) {
				++numDying;
				continue; // Logga inte värden för dying
			} else {
				++numLiving;
			}
			aggressionValues.add(new Tuple(tick, b.getGenetics().getAggression()));
			herbivoreAttractions.add(new Tuple(tick, b.getGenetics().getHerbivoreAttraction()));
			carnivoreAttractions.add(new Tuple(tick, b.getGenetics().getCarnivoreAttraction()));
			omnivoreAttractions.add(new Tuple(tick, b.getGenetics().getOmnivoreAttraction()));
			plantAttractions.add(new Tuple(tick, b.getGenetics().getPlantAttraction()));
			dyingAttractions.add(new Tuple(tick, b.getGenetics().getDyingAttraction()));
		}
		
		dyingCount.add(new Tuple(tick, numDying));
		bacteriaCount.add(new Tuple(tick, numLiving));
		

		deathRate.add(new Tuple(tick, deadRemoved-oldDeadRemoved));
		birthRate.add(new Tuple(tick, reproductionsDone-oldReproductionsDone));
		
		oldDeadRemoved = deadRemoved;
		oldReproductionsDone = reproductionsDone;
		
	}
	
	public void doPrintout() {
		String filename = "SimEv_"+new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime())+".txt";
		
		StringBuilder sb = new StringBuilder();
		
		
		sb.append("Num bacteria:\n");
		listToString(sb, bacteriaCount);
		
		sb.append("Num dying:\n");
		listToString(sb, dyingCount);
		
		sb.append("Birth rate:\n");
		listToString(sb, birthRate);
		
		sb.append("Death rate:\n");
		listToString(sb, deathRate);
		
		sb.append("Num plants:\n");
		listToString(sb, plantCount);
		
		sb.append("Agression values:\n");
		listToString(sb, aggressionValues);
		
		sb.append("Carnivore attractions:\n");
		listToString(sb, carnivoreAttractions);
		
		sb.append("Herbivore attractions:\n");
		listToString(sb, herbivoreAttractions);
		
		sb.append("Omnivore attractions:\n");
		listToString(sb, omnivoreAttractions);
		
		sb.append("Plant attractions:\n");
		listToString(sb, plantAttractions);
		
		sb.append("Dying attractions:\n");
		listToString(sb, dyingAttractions);
		
		
		PrintWriter writer;
		try {
			writer = new PrintWriter(filename, "UTF-8");
			writer.print(sb.toString());
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		
	}
	
	
	private void listToString(StringBuilder sb, ArrayList<Tuple> array) {
		sb.append("[");
		
		boolean first = true;
		for (Tuple t : array) {
			if (first) {
				first = false;
			} else {
				sb.append(",");
			}
			sb.append(t.toString());
		}
		sb.append("]\n");
		
	}
	
	
	private class Tuple implements Comparable<Tuple> {
		public int tick;
		public double value;
		
		public Tuple(int tick, double value) {
			this.tick = tick;
			this.value = value;
		}
		
		@Override
		public String toString() {
			return tick/100+","+value+";";
		}
		
		@Override
		public int compareTo(Tuple t) {
			if (tick > t.tick) {
				return 1;
			} else if (tick < t.tick) {
				return -1;
			}
			return 0;
		}
		
	}
	
	
}

