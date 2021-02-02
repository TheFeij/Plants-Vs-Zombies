package Component.Zombies;
import Component.Component;
import Template.GameState;
import javax.swing.*;
import java.io.Serializable;
import java.util.Timer;
import java.util.TimerTask;

/**
 * This class represents a zombie. it is the superclass of normal zombie,
 * coneHead zombie and bucketHead zombie
 *
 * @author Feij
 * @since 2021.1.31
 */
public abstract class Zombie extends Component implements Serializable {

    //if the zombie is affected by frozen pea or not
    private boolean isFrozen;
    //amount of time zombie will be frozen
    private int freezeTime;
    // timer for showing dead zombie pic
    private int deathTimer;
    //if zombie is destroyed in map or not
    private boolean isDestroyed;
    //the plant which zombie is eating
//    private Plant plantToEat;   //to be added
    //A timer to manage eating action of the zombie
    private transient Timer timerForEating;
    //is zombie burning or not
    private boolean isBurning;
    //is zombie eating or not
    private boolean isEating;
//    private  transient AudioPlayer eatingSound;   //to be added

    /**
     * A constructor to create a new zombie
     * @param locX x location of the zombie in the game map
     * @param locY y location of the zombie in the game map
     * @param life life of the zombie
     * @param damage damage of the zombie
     * @param state game state
     */
    public Zombie(int locX, int locY, int life, int damage, GameState state) {
        super(locX, locY, 2, life, damage, state);
        freezeTime = 0;
        isFrozen = false;
        isDestroyed = false;
        setTimer(new Timer());
        setTask(new Mover());
        deathTimer = 25;
        isBurning = false;
        isEating = false;
    }


    //getters for component's fields//
    public int getDeathTimer() {
        return deathTimer;
    }
    public int getFreezeTime() {
        return freezeTime;
    }
    public boolean isFrozen() {
        return isFrozen;
    }
    public boolean isDestroyed() {
        return isDestroyed;
    }
    //////////////////////////////////////////////////////////////

    //setters for component's fields//
    public void setDeathTimer(int deathTimer) {
        this.deathTimer = deathTimer;
    }
    public void setFrozen(boolean frozen) {
        isFrozen = frozen;
    }
    public void setFreezeTime(int freezeTime) {
        this.freezeTime = freezeTime;
    }
    public void setDestroyed(boolean destroyed) {
        isDestroyed = destroyed;
    }
    //////////////////////////////////////////////////////////////


    /**
     * A method to set zombie image to dying
     */
    public void setDyingImage(){
        setCurrentImage(new ImageIcon("zombie_normal_dying.gif").getImage());
    }

    /**
     * A method to set image of zombie to burning
     */
    public void setBurningImage(){ setCurrentImage(new ImageIcon("burntZombie.gif").getImage()); }

    /**
     * A method to burn zombie
     */
    public void burn(){
        setBurningImage();
        isBurning = true;
    }

    /**
     * A method to destroy zombie
     */
    public void destroyZombie(){
        getTimer().cancel();
        if(timerForEating != null){
            timerForEating.cancel();
        }
//        if(eatingSound != null)       //class to be written in future
//            eatingSound.stop();
    }

    /**
     * A method to freeze zombie
     */
    public void freezeZombie(){
        isFrozen = true;
        if (getSpeed() == 2) { setSpeed(getSpeed() / 2); }
        resetFreezeTime();
    }

    /**
     * A method to unfreeze zombie
     */
    public void unFreezeZombie(){
        isFrozen = false;
        setSpeed(getSpeed() * 2);
    }

    /**
     * A method to decrease freeze time
     */
    public void decreaseFreezeTime(){
        freezeTime -= 1;
    }

    /**
     * A method to reset freeze time to default value
     */
    public void resetFreezeTime(){
        freezeTime = 25;
    }

    /**
     * A method to update zombie state
     */
    public void update(){
        if(isFrozen){
            decreaseFreezeTime();
            if(freezeTime == 0){
                unFreezeZombie();
            }
        }
    }

//    public void startEating(Plant plant){      //first need to have plant classes
//        if(!isEating){
//            setLoadTime(0);
//            setTimeHolder(System.currentTimeMillis());
//            setSpeed(0);
//            plantToEat = plant;
//            timerForEating = new Timer();
//            isEating = true;
//            timerForEating.schedule(new Eating(), 0, 1000);
//        }
//    }

    /**
     * A method to stop eating
     */
    public void stopEating(){
        timerForEating.cancel();
//        eatingSound.stop();       //need a class to play audio
        isEating = false;
        moving();
    }

    /**
     * A method to change zombie situation to moving in map
     * this method should be overridden
     */
    public abstract void moving();

    /**
     *Class responsible for zombie movement
     */
    private class Mover extends TimerTask{

        /**
         * The action to be performed by this timer task.
         */
        @Override
        public void run() {
            if (isBurning){
                setSpeed(0);
                decreaseLife(3);
            }
            setLocX(getLocX() - getSpeed());
            update();
        }
    }

//    /**
//     * A method to change zombie situation to eating plants    //first need to have plant classes
//     * this method should be overridden
//     */
//    public void eating(){
//        eatingSound = new AudioPlayer("./Sounds/chomp.wav", 0);
//        if (plantToEat.getLife() <= 0){
//            stopEating();
//            plantToEat.cancelTimer();
//        }
//        plantToEat.decreaseLife(getDamage());
//    }

    private class Eating extends TimerTask{
        /**
         * The action to be performed by this timer task.
         */
        @Override
        public void run() {
//            eating();
            setLoadTime(0);
            setTimeHolder(System.currentTimeMillis());
        }
    }

    /**
     * A method to load zombie
     */
    public void load(){
        super.load();
        timerForEating = new Timer();
        if(isEating)
            timerForEating.schedule(new Eating(), 1000 - getLoadTime(), 1000);

        setTask(new Mover());
    }
}
