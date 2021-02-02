package Component.Cards;
import Component.Component;
import Template.GameState;

import javax.swing.*;
import java.io.Serializable;
import java.util.Timer;
import java.util.TimerTask;


/**
 * A class to make card
 *
 * @author Mohammad
 */
public abstract class Card extends Component implements Serializable {

    private boolean isActive;
//    private TimerTask lifeTime;


    public Card (int locX, int locY, GameState state){
        super (locX, locY, 0, 0, 0, state) ;
        isActive = true;
        setTask(new LifeTime());
    }


    public void setActive(boolean active) {
        isActive = active;
    }

//    public void setLifeTime(TimerTask lifeTime) {
//        this.lifeTime = lifeTime;
//    }
//
//    public TimerTask getLifeTime() {
//        return lifeTime;
//    }

    public boolean getActive() {
        return isActive;
    }

    public abstract void setActivePic();
    public abstract void setInActivePic();

    public void select(){
        setTimeHolder(System.currentTimeMillis());
        setInActivePic();
        setActive(false);
        setTimer(new Timer());
        setTask(new LifeTime());
        getTimer().schedule(getTask(), getPeriod() - getLoadTime(), 1);
    }

    private class LifeTime extends TimerTask {
        /**
         * The action to be performed by this timer task.
         */
        @Override
        public void run() {
            getTimer().cancel();
            setActivePic();
            setActive(true);
            setTimeHolder(System.currentTimeMillis());
            setLoadTime(0L);
        }
    }



//    public void load(){
//        super.load();
//        setTask(new LifeTime());
//        getTimer().schedule(getTask(), getPeriod() - getLoadTime(), 1);
//    }

}
