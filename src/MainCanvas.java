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

    int WIDTH;
    int HEIGHT;
    int scale;

    private BufferedImage buffer;
    private WritableRaster raster;

    private Graphics2D doubleBuffer;
    private Font systemFont;
    private Simulation sim;

    public MainCanvas(int W, int H){
        WIDTH = W;
        HEIGHT = H;
        scale = 4;

        buffer = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        raster = buffer.getRaster();

        doubleBuffer = (Graphics2D) buffer.getGraphics(); //this is the double buffer
        doubleBuffer.setBackground(new Color(40, 40, 40)); //Set the background color to be redrawn each frame
        systemFont = new Font("Arial", Font.BOLD ,12);
        doubleBuffer.setFont(systemFont);

    }

    //Draws everything on the screen
    public void paint(){

        //Clears the picture to the default color
        doubleBuffer.clearRect(0, 0, WIDTH, HEIGHT);

        //We get each bacteria and paint them out (TEMPORARY)
        Bacteria bac;
        ArrayList<Bacteria> bacList = sim.getBacteriaList();

        for(int i = 0; i < bacList.size(); i++){

            bac = bacList.get(i);

            int[] white =  {255 - 2 * bac.getAge(), 255 - 2 * bac.getAge(), 255 - 2 * bac.getAge()};

            //We paint the bacteria, pixel by pixel
            for(int y = 0; y < bac.getSize(); y++){
                for(int x = 0; x < bac.getSize(); x++){

                    raster.setPixel(bac.getXpos() * scale + x, bac.getYpos() * scale + y, white);
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

    //Gets position from simulation 2D array to canvas 1D array
    public int canvasGetPos(int w, int h){
        return ((h * scale * WIDTH) + w * scale);
    }

    public void drawText(String st, int x, int y){
        doubleBuffer.drawString(st, x, y);
    }

    public void setSimulation(Simulation sim){
        this.sim = sim;
    }
}