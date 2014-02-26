import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

class MainCanvas extends Canvas{

    private BufferedImage buffer;
    private Graphics2D doubleBuffer;
    private Font systemFont;
    private Simulation sim;

    public MainCanvas(){

        buffer = new BufferedImage(Constants.WIDTH, Constants.HEIGHT, BufferedImage.TYPE_INT_RGB);
        doubleBuffer = (Graphics2D) buffer.getGraphics(); //this is the double buffer
        doubleBuffer.setBackground(new Color(20, 20, 20)); //Set the background color to be redrawn each frame

        systemFont = new Font("Arial", Font.BOLD ,12);
        doubleBuffer.setFont(systemFont);
    }

    //Draws everything on the screen
    public void paint(){

        //Clears the picture to the default color
        doubleBuffer.clearRect(0, 0, Constants.WIDTH, Constants.HEIGHT);

        for(Plant p : sim.getPlantList()){
            drawEntity(p);
        }
        
        for(Bacteria bac : sim.getBacteriaList()){
            drawEntity(bac);
        }

        drawText("", 0, 0); //For some reason we need this to avoid a NullPointerException in drawText()
        update();
    }

    //Quickly update the canvas
    public void update(){
        getGraphics().drawImage(buffer, 0, 0, null);
    }

    public void drawText(String st, int x, int y){
        doubleBuffer.setColor(Color.white);
        doubleBuffer.drawString(st, x, y);
    }

    public void drawEntity(SimulationEntity se){
        doubleBuffer.setColor(se.getColor());
        doubleBuffer.fillRect(se.getXpos(), se.getYpos(), 4, 4);
    }

    public void setSimulation(Simulation sim){
        this.sim = sim;
    }
}