package Component.Bullets;
import Component.Component;
import Template.GameState;
import java.io.Serializable;
import java.util.Timer;
import java.util.TimerTask;

/**
 * This class represents a Bullet. it is the superclass of Pea,
 * and frozenPea
 *
 * @author Feij
 * @since 2021.1.24
 */
public abstract class Bullet extends Component implements Serializable {



    /**
     * A constructor to create a new Bullet
     * @param locX x location of the Bullet in the game map
     * @param locY y location of the Bullet in the game map
     * @param speed speed of the Bullet
     * @param damage damage of the Bullet
     * @param state game state
     */
    public Bullet(int locX, int locY,int speed, int damage, GameState state) {
        super(locX, locY, speed, 0, damage, state);
        setTimer(new Timer());
        setTask(new Mover());
    }



    /**
     * A method to set preparations for loading the component
     */
    public void load(){
        super.load();
        setTask(new Mover());
    }





    /**
     *Class responsible for bullet movement
     */
    private class Mover extends TimerTask {

        /**
         * The action to be performed by this timer task.
         */
        @Override
        public void run() {
            setLocX(getLocX() + getSpeed());
        }
    }

}
