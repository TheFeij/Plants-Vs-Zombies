package Component.Plants;
import Template.GameState;
import javax.swing.*;
import java.io.Serializable;
import java.util.TimerTask;


/**
 * A class to make cherry bomb
 *
 * @author Mohammad
 */
public class CherryBomb extends Plant implements Serializable {


    /**
     * Perform any initialization that is required
     * @param locX The cherry bomb x coordinate
     * @param locY The cherry bomb y coordinate
     * @param state The game state
     */
    public CherryBomb(int locX, int locY, GameState state){
        super(locX, locY, 70, state);
        setCurrentImage(new ImageIcon("./Pics/cherrybomb.gif").getImage());
        setTimeHolder(System.currentTimeMillis());
        getTimer().schedule(new Explosion(), 1000, 1);
    }



    /**
     *A method to set preparations to load the plant
     */
    public void load(){
        super.load();
        setCurrentImage(new ImageIcon("./Pics/cherrybomb.gif").getImage());
        setTask(new Explosion());
        getTimer().schedule(getTask(), 1000 - getLoadTime(), 1);
    }



    /**
     * A class to handle explosion
     */
    private class Explosion extends TimerTask {

        /**
         * The action to be performed by this timer task.
         */
        @Override
        public void run() {
            setIsDead(true);
            getTimer().cancel();
        }
    }

}
