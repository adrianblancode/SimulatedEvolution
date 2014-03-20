import java.awt.*;
import java.awt.event.*;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.KeyStroke;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

public class Start {

    public static void main(String[] args) {

        Simulation sim;
        MainCanvas canvas;

        int vSync = 100; //Number of FPS we have as upper limit

        final JFrame myFrame = new JFrame("Simulated Evolution"); //title

        myFrame.setSize(new Dimension(Constants.WIDTH + 6, Constants.HEIGHT + 28)); //Including offsets for the frame borders
        //myFrame.setExtendedState(JFrame.MAXIMIZED_BOTH); //fullscreen
        //myFrame.setUndecorated(true); //noborder
        myFrame.setVisible(true);
        myFrame.setResizable(false);

        initWindowClose(myFrame);

        sim = new Simulation();
        canvas = new MainCanvas();
        canvas.setSimulation(sim);
        myFrame.add("Center", canvas);
        myFrame.addKeyListener(new MKeyListener());

        int sleep = 0;
        float res = 0;
        
        // Runs statup on the simulation.
        sim.start(100);
        for (int i = 0; i < 80; ++i) {
        	sim.spawnHerbivore();
        }

        for (int i = 0; i < 5; ++i) {
        	sim.spawnCarnivore();
        }
        
        //Simulation loop
        while(true){

            long time = System.nanoTime();

            sim.run();
            canvas.paint();

            canvas.drawText(Integer.toString(sim.getTicks()), 0, 12);
            canvas.drawText(Integer.toString((int) res), 0, 24);
            canvas.update();

            time = System.nanoTime() - time;

            //We pause the thread a bit so we don't run it full speed
            sleep = (1000 / vSync) - (int) time / 1000000;

            if(sleep > 0){
                try {Thread.sleep((sleep));}
                catch (InterruptedException e) {e.printStackTrace();}
            }
            else{
                sleep = 0;
            }

            res = 1000 / ((time/1000000) + sleep);
        }
    }

    //Enables the user to exit the program by pressing escape
    public static void initWindowClose(JFrame myFrame){

        Action escapeAction = new AbstractAction() {
            // close the frame when the user presses escape
            @Override
            public void actionPerformed(ActionEvent e) {
                //myFrame.dispose();
                System.exit(0);
            }
        };

        myFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent ev) {
                System.exit(0);
            }
        });

        KeyStroke escapeKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false);
        myFrame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(escapeKeyStroke, "ESCAPE");
        myFrame.getRootPane().getActionMap().put("ESCAPE", escapeAction);
    }
}

class MKeyListener extends KeyAdapter {

    @Override
    public void keyPressed(KeyEvent event) {

        if (event.getKeyCode() == KeyEvent.VK_LEFT) {
            System.out.println("Left");
        }

        if (event.getKeyCode() == KeyEvent.VK_RIGHT){
            System.out.println("Right");
        }

        if (event.getKeyCode() == KeyEvent.VK_HOME) {
            System.out.println("Key codes: " + event.getKeyCode());
        }
    }
}