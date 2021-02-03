package Component.Zombies;
import Template.GameState;
import javax.swing.*;
import java.io.Serializable;

/**
 * This class represents a football zombie
 */
public class FootballZombie extends Zombie implements Serializable {


    /**
     * A constructor to create a new football zombie
     * @param locX x location of the zombie
     * @param locY x location of the zombie
     * @param state state of the game
     */
    public FootballZombie(int locX, int locY, GameState state, String type){
        super(locX, locY, 1600, 25, state);
        setCurrentImage(new ImageIcon("./Pics/zombie_football.gif").getImage());
        if(type.equals("Normal"))
            getTimer().schedule(getTask(), 0, 53);
        else if(type.equals("Hard")){
            setDamage(30);
            getTimer().schedule(getTask(), 0, 45);
        }
    }


    /**
     * A method to change zombie situation to moving in map
     * this method should be overridden
     */
    public void moving() {
        setSpeed(2);
    }

    /**
     * A method to update zombie state
     */
    public void update(){
        super.update();
    }

    /**
     *A method to set preparations to load the zombie
     */
    public void load(){
        super.load();
        setCurrentImage(new ImageIcon("./Pics/zombie_football.gif").getImage());
        if(getState().getType().equals("Normal"))
            getTimer().schedule(getTask(), 0, 53);
        else if(getState().getType().equals("Hard")){
            getTimer().schedule(getTask(), 0, 45);
        }
    }
}
