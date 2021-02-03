package Component.Cards;
import Component.Component;
import Template.GameState;
import java.io.Serializable;
import java.util.Timer;
import java.util.TimerTask;


/**
 * A class to make card
 *
 * @author Mohammad
 */
public abstract class Card extends Component implements Serializable {

    //if card is active to choose or not
    private boolean isActive;


    /**
     * A constructor to create a new Card
     * @param locX x location of the card
     * @param locY y location of the card
     * @param state state of the game
     */
    public Card (int locX, int locY, GameState state){
        super (locX, locY, 0, 0, 0, state) ;
        isActive = true;
        setTask(new LifeTime());
    }




    //setters///////////////////////////////////////////////////
    public void setActive(boolean active) {
        isActive = active;
    }
    public abstract void setActivePic();
    public abstract void setInActivePic();
    /////////////////////////////////////////////////////////////

    //getters////////////////////////////////////////////////////
    public boolean getActive() {
        return isActive;
    }
    /////////////////////////////////////////////////////////////

    /**
     * A method to select a card
     */
    public void select(){
        setTimeHolder(System.currentTimeMillis());
        setLoadTime(0);
        setInActivePic();
        setActive(false);
        setTimer(new Timer());
        setTask(new LifeTime());
        getTimer().schedule(getTask(), getPeriod(), 1);
    }

    /**
     * A method to set preparations for loading the component
     */
    public void load(){
        super.load();
        if(isActive){
            setLoadTime(0);
            setActivePic();
        }
        else{
            setInActivePic();
            setTask(new LifeTime());
            getTimer().schedule(getTask(), getPeriod() - getLoadTime(), 1);
        }
    }




    /**
     * A class to handel lifeTime of the card
     * managing inactive time of the card
     * when run method is called, card will become active
     */
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

}
