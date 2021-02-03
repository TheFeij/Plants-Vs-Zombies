package Component.Plants;
import Component.Bullets.Pea;
import MusicPlayer.AudioPlayer;
import Template.GameState;
import javax.swing.*;
import java.util.TimerTask;

/**
 * This class represents a repeater
 *
 * @author Feij, Mohammad
 */
public class Repeater extends Plant {

    /**
     * Perform any initialization that is required
     * @param locX The pea shooter x coordinate
     * @param locY The pea shooter y coordinate
     * @param state The game state
     */
    public Repeater(int locX, int locY, GameState state){
        super(locX, locY, 100, state);
        setCurrentImage(new ImageIcon("./Pics/repeater.gif").getImage());
        getTimer().schedule(new Shooter(), 0, 500);
    }



    /**
     * A method to shoot a pea
     */
    private void shoot(){
        Pea pea = new Pea(getLocX() + 50, getLocY(), getState());
        getState().addBullet(pea);
        if(!getState().isMute()){
            AudioPlayer shootSound = new AudioPlayer("./Sounds/shoot.wav", 0);
        }
    }

    /**
     *A method to set preparations to load the repeater
     */
    public void load(){
        super.load();
        setCurrentImage(new ImageIcon("./Pics/repeater.gif").getImage());
        setTask(new Shooter());
        getTimer().schedule(getTask(), 500 - getLoadTime() ,500);
    }




    /**
     * A class to handle shooting process
     */
    private class Shooter extends TimerTask {

        /**
         * The action to be performed by this timer task.
         */
        @Override
        public void run() {
            if(getState().isZombieInWay(getLocY(), getLocX()))
                shoot();
            setLoadTime(0);
            setTimeHolder(System.currentTimeMillis());
        }
    }

}
