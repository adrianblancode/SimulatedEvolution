/**
 * Created by Adrian on 2014-02-19.
 */
public class Vector{
    double x;
    double y;

    public Vector(){
        x = 0;
        y = 0;
    }

    public Vector(double xv, double yv){
        x = xv;
        y = yv;
    }

    public double getX(){
            return x;
    }

    public void setX(double xv){
        x = xv;
    }

    public double getY(){
        return y;
    }

    public void setY(double yv){
        y = yv;
    }

    //Returns the euclidian length of the vector
    public double getLength(){
        return Math.sqrt(x*x + y*y);
    }

    //Adds the vector v to the current vector
    public void add(Vector v){
        setX(getX() + v.getX());
        setY(getY() + v.getY());
    }

    public void normalize(){
    	double len = getLength();
    	
        if(len != 0){
            x = x / len;
            y = y / len;
        }
    }

    //Scales the x and y values with a factor f
    public void scale(double f){

        x = x * f;
        y = y * f;
    }

    public boolean isNull(){
        if(Math.abs(x) < 0.001 && Math.abs(y) < 0.001){
            return true;
        }
        else{
            return false;
        }
    }
}
