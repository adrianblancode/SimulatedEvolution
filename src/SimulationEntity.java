/**
 * Created by Adrian on 2014-02-19.
 */
public abstract class SimulationEntity {

    private int xpos;
    private int ypos;
    private boolean dead;

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

    public Vector distance(SimulationEntity se){
        int xd = se.getXpos() - getXpos();
        int yd = se.getYpos() - getYpos();
        return new Vector(xd, yd);
    }
}
