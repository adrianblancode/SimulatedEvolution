import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.ArrayList;

class MainCanvas extends Canvas{

    int WIDTH;
    int HEIGHT;
    int scale;

    private BufferedImage buffer;
    private WritableRaster raster;
    int[] pixelArray;

    private Graphics2D gt;
    private Font systemFont;
    private Simulation sim;

    public MainCanvas(int W, int H){
        WIDTH = W;
        HEIGHT = H;
        scale = 4;
        pixelArray = new int[WIDTH * HEIGHT * 3]; //RGB, 3 colors for each pixel

        buffer = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        raster = buffer.getRaster();

        gt = (Graphics2D) buffer.getGraphics(); //this is the double buffer
        systemFont = new Font("Arial", Font.BOLD ,12);
        gt.setFont(systemFont);
        gt.setColor(Color.white);
    }

    /**
     * Draws the screen. Uses double buffering.
     */
    public void paint(Graphics g){

        //Filling values for lulz
        for (int i = 0; i < WIDTH * HEIGHT * 3; i++){
            pixelArray[i] = 55;
        }

        //We get the bacteria and paint them out (TEMPORARY)
        Bacteria b;
        ArrayList<Bacteria> bl = sim.getBacteriaList();

        for(int i = 0; i < bl.size(); i++){

            b = bl.get(i);

            //We get the upper right corner position of the bacteria
            int baseR = canvasGetPos(b.getXpos(), b.getYpos()) * 3;
            int baseG = canvasGetPos(b.getXpos(), b.getYpos()) * 3 + 1;
            int baseB = canvasGetPos(b.getXpos(), b.getYpos()) * 3 + 2;

            //We paint the bacteria, pixel by pixel
            for(int y = 0; y < scale; y++){
                for(int x = 0; x < scale; x++){

                    pixelArray[baseR + y * WIDTH * 3 + x * 3] = 255;
                    pixelArray[baseG + y * WIDTH * 3 + x * 3] = 255;
                    pixelArray[baseB + y * WIDTH * 3 + x * 3] = 255;
                }
            }

        }

        drawText("Hello World!", 0, 10);

        //Write the pixelArray to the raster
        raster.setPixels(0, 0, WIDTH, HEIGHT, pixelArray);

        g.drawImage(buffer, 0, 0, null);
    }

    //We need to call paint from this function to be able to use double buffering
    public void update(Graphics g){
        paint(g);
    }

    //Gets position from simulation 2D array to canvas 1D array
    public int canvasGetPos(int w, int h){
        return ((h * scale * WIDTH) + w * scale);
    }

    public void drawText(String st, int x, int y){
        gt.drawString(st, x, y);
    }

    public void setSimulation(Simulation sim){
        this.sim = sim;
    }
}