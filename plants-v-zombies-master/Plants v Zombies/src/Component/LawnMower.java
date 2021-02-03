package Component;
import MusicPlayer.AudioPlayer;
import Template.GameState;
import javax.swing.*;
import java.io.Serializable;
import java.util.Timer;
import java.util.TimerTask;

/**
 * This class represents a lawn-Mower.
 *
 * @author Feij
 * @since 2021.1.25
 */
public class LawnMower extends Component implements Serializable {

    //it is used to hold a time unit in Mover class
    private int time;
    //lawnMower has started moving or not
    private boolean started;

    /**
     * A constructor to create a new Component
     * @param locX x location of the component in the game map
     * @param locY y location of the component in the game map
     * @param state game state
     */
    public LawnMower(int locX, int locY, GameState state) {
        super(locX, locY, 2, 0, 0, state);
        time = -1;
        started = false;
        setCurrentImage(new ImageIcon("./Pics/Lawn_Mower.png").getImage());
        setTimer(new Timer());
        setTask(new Mover());
    }



    //setters////////////////////////////////////////////////////////
    public int getTime() { return time; }
    /**
     * A method to set image of lawn mower
     * to starting position
     */
    public void setStartImage(){
        setCurrentImage(new ImageIcon("./Pics/lawnmowerActivated.gif").getImage());
    }
    /**
     * َ A method to set mower image to moving
     */
    public void setMovingImage(){
        setCurrentImage(new ImageIcon("./Pics/lawn_mower.gif").getImage());
    }
    /////////////////////////////////////////////////////////////////

    /**
     * A method to activate lawnMower
     */
    public void start(){
        started = true;
        time = -1;
        if(!getState().isMute()){
            AudioPlayer startSound = new AudioPlayer("./Sounds/lamborghini.wav", 0);
        }
        setTimer(new Timer());
        setTask(new Mover());
        getTimer().schedule(getTask(), 0, 10);
    }

    /**
     * A method to set preparations for loading the component
     */
    public void load(){
        super.load();
        if(time == -1)
            setCurrentImage(new ImageIcon("./Pics/Lawn_Mower.png").getImage());
        else if(time < 30)
            setStartImage();
        else
            setMovingImage();
        if(started){
            setTask(new Mover());
            getTimer().schedule(getTask(), 0, 10);
        }
    }





    /**
     *Class responsible for lawn mower movement
     */
    private class Mover extends TimerTask{

        /**
         * The action to be performed by this timer task.
         */
        @Override
        public void run() {
            time++;
            if(time == 0){
                setStartImage();
            }
            else if(time == 30){
                setMovingImage();
            }
            else if(time > 30){
                setLocX(getLocX() + getSpeed());
            }
        }
    }

}
