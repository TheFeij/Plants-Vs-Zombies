package Component;
import Template.GameState;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A class to make sun
 */
public class Sun extends Component implements Serializable {

    private int deltaY;
    private int finalY;
    private int lifeTime;

    /**
     * Perform any initialization that is required
     * @param locX The sun x coordinate
     * @param locY The sun y coordinate
     * @param state The game state
     * @param finalY The sun final location x coordinate
     */
    public Sun(int locX, int locY, GameState state, int finalY){
        super(locX, locY, 0, 0, 0, state);
        setCurrentImage(new ImageIcon("./Pics/sun.gif").getImage());
        deltaY = 2;
        this.finalY = finalY;
        lifeTime = 0;
        getTimer().schedule(new Movement(), 0, 50);
    }



    // getters of sun fields
    public int getLifeTime() {
        return lifeTime;
    }
    ////////////////////////////////////////



    /**
     * A class to handle sun moving process
     */
    private class Movement extends TimerTask {
        @Override
        public void run() {
            if (getLocY() >= finalY){
                deltaY = 0;
            }
            setLocY(getLocY() + deltaY);
            if (deltaY == 0){
                lifeTime ++ ;
            }
        }

    }


    public void load(){
        super.load();
        setCurrentImage(new ImageIcon("./Pics/sun.gif").getImage());
        setTask(new Movement());
        getTimer().schedule(getTask(), 0, 50);
    }

}
