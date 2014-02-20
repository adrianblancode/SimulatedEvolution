import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.ArrayList;
import java.util.Arrays;

class MainCanvas extends Canvas{

    private BufferedImage buffer;
    private WritableRaster raster;

    private Graphics2D doubleBuffer;
    private Font systemFont;
    private Simulation sim;

    public MainCanvas(){

        buffer = new BufferedImage(Constants.WIDTH, Constants.HEIGHT, BufferedImage.TYPE_INT_RGB);
        raster = buffer.getRaster();

        doubleBuffer = (Graphics2D) buffer.getGraphics(); //this is the double buffer
        doubleBuffer.setBackground(new Color(30, 30, 30)); //Set the background color to be redrawn each frame

        systemFont = new Font("Arial", Font.BOLD ,12);
        doubleBuffer.setFont(systemFont);
    }

    //Draws everything on the screen
    public void paint(){

        //Clears the picture to the default color
        doubleBuffer.clearRect(0, 0, Constants.WIDTH, Constants.HEIGHT);

        //We get each bacteria and paint them out (TEMPORARY)
        Bacteria bac;
        ArrayList<Bacteria> bacList = sim.getBacteriaList();

        for(int i = 0; i < bacList.size(); i++){

            bac = bacList.get(i);

            int[] colour =  {255 - bac.getAge(), 255 - bac.getAge(), 255 - bac.getAge()};

            //We paint the bacteria, pixel by
            //MAKE NEW PRETTIER METHOD
            for(int y = 0; y < 4; y++){
                for(int x = 0; x < 4; x++){

                    if(!outOfBounds(bac.getXpos() + x, bac.getYpos() + y)){
                        raster.setPixel(bac.getXpos() + x, bac.getYpos() + y, colour);
                    }
                }
            }

        }
        drawText("", 0, 0); //For some reason we need this to avoid a NullPointerException in drawText()
        update();
    }

    //Quickly update the canvas
    public void update(){
        getGraphics().drawImage(buffer, 0, 0, null);
    }

    public void drawText(String st, int x, int y){
        doubleBuffer.drawString(st, x, y);
    }

    public void setSimulation(Simulation sim){
        this.sim = sim;
    }

    public boolean outOfBounds(int x, int y){
        if(x < 0 || x >= Constants.WIDTH){
            return true;
        }
        else if(y < 0 || y >= Constants.HEIGHT){
            return true;
        }
        else return false;
    }
}