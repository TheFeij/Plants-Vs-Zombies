package Component.Plants;
import Component.Sun;
import Template.GameState;
import javax.swing.*;
import java.io.Serializable;
import java.util.TimerTask;

/**
 * A class to make sunflower
 *
 * @author Mohammad
 */
public class Sunflower extends Plant implements Serializable {

    /**
     * Perform any initialization that is required
     * @param locX The sunflower x coordinate
     * @param locY The sunflower y coordinate
     * @param state The game state
     */
    public Sunflower(int locX, int locY, GameState state){
        super(locX, locY, 50, state);
        setCurrentImage(new ImageIcon("./Pics/sun_flower.gif").getImage());
        setPeriod(20000);
        if (getState().getType().equals("Hard")){
            setPeriod(25000);
        }
        getTimer().schedule(new SunMaker(), 2000, getPeriod());
    }



    /**
     * A method to make a sun
     */
    private void make(){
        Sun sun = new Sun(getLocX() + 60, getLocY(), getState(), getLocY() + 30);
        getState().addSun(sun);
    }

    /**
     *A method to set preparations to load the sunflower
     */
    public void load(){
        super.load();
        setCurrentImage(new ImageIcon("./Pics/sun_flower.gif").getImage());
        setTask(new SunMaker());
        if(getState().getType().equals("Hard")){
            getTimer().schedule(getTask(), 25000 - getLoadTime() ,25000);
        }
        else
            getTimer().schedule(getTask(), 20000 - getLoadTime() ,20000);

    }




    /**
     * A class to handel sun production
     */
    private class SunMaker extends TimerTask {

        /**
         * The action to be performed by this timer task.
         */
        @Override
        public void run() {
            make();
            setLoadTime(0);
            setTimeHolder(System.currentTimeMillis());
        }
    }

}
