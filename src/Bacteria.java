import java.util.Random;

public class Bacteria {

    private int xpos;
    private int ypos;

    private int WIDTH;
    private int HEIGHT;

    private int age;

    public Bacteria(int W, int H){
        WIDTH = W;
        HEIGHT = H;
        age = 0;
        spawn();
    }

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

    public void act(){
        age++;
        int pos = getXpos() + 1;
        setXpos(checkXbounds(pos));
    }

    private void spawn(){
        //lol sämst
        Random rand = new Random();

        //OBS hårdkodat
        int value = rand.nextInt(WIDTH);
        setXpos(value);

        value = rand.nextInt(HEIGHT);
        setYpos(value);
    }

    private int checkXbounds(int x){

        if(x < 0){
            return 0;
        }

        else if(x >= WIDTH){
            return (WIDTH - 1);
        }

        else return x;
    }

    private int checkYbounds(int y){

        if(y < 0){
            return 0;
        }

        else if(y >= HEIGHT){
            return (HEIGHT - 1);
        }

        else return y;
    }
}
