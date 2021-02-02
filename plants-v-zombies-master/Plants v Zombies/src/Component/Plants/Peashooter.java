package Component.Plants;
import Template.GameState;

import javax.swing.*;
import java.io.Serializable;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A class to make pea shooter
 *
 * @author Mohammad
 */
public class Peashooter extends Plant implements Serializable {

    /**
     * Perform any initialization that is required
     * @param locX The pea shooter x coordinate
     * @param locY The pea shooter y coordinate
     * @param state The game state
     */
    public Peashooter(int locX, int locY, GameState state){
        super(locX, locY, 70, state);
        setCurrentImage(new ImageIcon("./Pics/peashooter.gif").getImage());
        getTimer().schedule(new Shooter(), 0, 1000);
    }

    /**
     * Shoot pea
     */
//    private void shoot(){
//        Pea pea = new Pea(getLocX() + 50, getLocY(), getState());
//        getState().addBullet(pea);
//        AudioPlayer shootSound = new AudioPlayer("./Sounds/shoot.wav", 0);
//    }

    /**
     * A class to handle shooting process
     */
    private class Shooter extends TimerTask {

        /**
         * The action to be performed by this timer task.
         */
        @Override
        public void run() {
//            if(getState().isZombieInWay(getLocY(), getLocX()))
//                shoot();
            setLoadTime(0);
            setTimeHolder(System.currentTimeMillis());
        }
    }

    public void load(){
        super.load();
        setTask(new Shooter());
        getTimer().schedule(getTask(), 1000 - getLoadTime() ,1000);
    }
}
