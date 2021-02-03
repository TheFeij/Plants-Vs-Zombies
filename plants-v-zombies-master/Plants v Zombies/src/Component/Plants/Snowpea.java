package Component.Plants;
import Component.Bullets.FrozenPea;
import MusicPlayer.AudioPlayer;
import Template.GameState;
import javax.swing.*;
import java.io.Serializable;
import java.util.TimerTask;

/**
 * A class to make snow pea shooter
 *
 * @author Mohammad
 */
public class Snowpea extends Plant implements Serializable {

    /**
     * Perform any initialization that is required
     * @param locX The snow pea shooter x coordinate
     * @param locY The snow pea shooter y coordinate
     * @param state The game state
     */
    public Snowpea(int locX, int locY, GameState state){
        super(locX, locY, 100, state);
        setCurrentImage(new ImageIcon("./Pics/freezepeashooter.gif").getImage());
        getTimer().schedule(new Shooter(), 0, 1000);
    }



    /**
     * A method to shoot a pea
     */
    private void shoot(){
        // checking life for changing image...
        FrozenPea frozenPea = new FrozenPea(getLocX() + 50, getLocY(), getState());
        getState().addBullet(frozenPea);
        if(!getState().isMute()){
            AudioPlayer shootSound = new AudioPlayer("./Sounds/shoot.wav", 0);
        }
    }

    /**
     *A method to set preparations to load the snowPea
     */
    public void load(){
        super.load();
        setTask(new Shooter());
        setCurrentImage(new ImageIcon("./Pics/freezepeashooter.gif").getImage());
        getTimer().schedule(getTask(), 1000 - getLoadTime() ,1000);
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