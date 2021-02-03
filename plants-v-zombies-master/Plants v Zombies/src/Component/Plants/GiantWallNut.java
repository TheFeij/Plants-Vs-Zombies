package Component.Plants;
import Template.GameState;
import javax.swing.*;
import java.io.Serializable;
import java.util.TimerTask;

/**
 * A class to make giant wall nut
 *
 * @author Mohammad
 */
public class GiantWallNut extends Plant implements Serializable {

    /**
     * Perform any initialization that is required
     * @param locX The giant wall nut x coordinate
     * @param locY The giant wall nut y coordinate
     * @param state The game state
     */
    public GiantWallNut(int locX, int locY, GameState state){
        super(locX, locY, 150, state);
        setCurrentImage(new ImageIcon("./Pics/walnut_full_life.gif").getImage());
        getTimer().schedule(new Living(), 0, 30);
    }



    /**
     * A method to update image of the plant
     */
    public void updateImage(){
        if (getLife() < 75){
            setCurrentImage(new ImageIcon("./Pics/walnut_half_life.gif").getImage());
        }
        else
            setCurrentImage(new ImageIcon("./Pics/walnut_full_life.gif").getImage());
    }

    /**
     *A method to set preparations to load the plant
     */
    public void load(){
        super.load();
        updateImage();
        setTask(new Living());
        getTimer().schedule(getTask(), 0 ,30);
    }




    /**
     * A class to make life
     */
    private class Living extends TimerTask {

        /**
         * The action to be performed by this timer task.
         */
        @Override
        public void run() {
            updateImage();
        }
    }

}
