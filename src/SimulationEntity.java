/**
 * Created by Adrian on 2014-02-19.
 */
public abstract class SimulationEntity {

    private int xpos;
    private int ypos;
    private boolean dead;
	private int energy;

    public int getXpos(){
        return xpos;
    }

    public void setXpos(int i){
        xpos = i;
    }

    public int getYpos(){
        return ypos;
    }

    public void setYpos(int i){
        ypos = i;
    }

    public void setDead(boolean d){
        dead = d;
    }

    public boolean isDead(){
        return dead;
    }
    
    public int getEnergy() {
		return energy;
	}

	public void setEnergy(int energy) {
		this.energy = energy;
		if (this.energy > Constants.maxEnergy)
			this.energy = Constants.maxEnergy;
		else if (this.energy < 0)
			this.energy = 0;
	}
	
	public void addEnergy(int energy) {
		setEnergy(getEnergy()+energy);
	}
	
	public void dropEnergy(int energy) {
		setEnergy(getEnergy()-energy);
	}

    public Vector distance(SimulationEntity se){
        int xd = se.getXpos() - getXpos();
        int yd = se.getYpos() - getYpos();
        return new Vector(xd, yd);
    }
}
