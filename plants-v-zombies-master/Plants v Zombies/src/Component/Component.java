package Component;
import Template.GameState;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


/**
 * This class represents a components in the game, such as a zombie, plant or pea!
 * it is the superclass of Zombie, Plant, Bullets, LawnMower and sun;
 *
 * @author Feij
 * @since 2021.1.24
 */
public class Component implements Serializable {

    //x location of the component in the game map
    private int locX;
    //y location of the component in the game map
    private int locY;
    //speed of the component
    private int speed;
    //life of the component
    private int life;
    //damage of the component
    private int damage;
    //current image of the component
    private transient Image currentImage;
    //game state
    private GameState state;
    // a timer object to handle bullet movement
    private transient Timer timer;
    // task to be repeated to simulate bullet movement
    private transient TimerTask task;
    // period of a component task, for example for peashooter
    //it is the time between shooting a pea
    private int period;
    //if the component is dead or not
    private boolean isDead;
    //a field to hold time of the system for load and save purposes
    private long timeHolder;
    //a field to show the load time period for load and save purposes
    private long loadTime;

    /**
     * A constructor to create a new Component
     * @param locX x location of the component in the game map
     * @param locY y location of the component in the game map
     * @param speed speed of the component
     * @param life life of the component
     * @param damage damage of the component
     * @param state game state
     */
    public Component(int locX, int locY,int speed,int life, int damage, GameState state) {
        this.locX = locX;
        this.locY = locY;
        this.speed = speed;
        this.life = life;
        this.damage = damage;
        this.state = state;
        this.isDead = false;
        timer = new Timer();
        loadTime = 0L;
        timeHolder = System.currentTimeMillis();
    }

    //getters for component's fields//
    public int getLocX() {
        return locX;
    }
    public int getLocY() {
        return locY;
    }
    public int getLife() {
        return life;
    }
    public int getDamage() {
        return damage;
    }
    public Image getCurrentImage() {
        return currentImage;
    }
    public int getSpeed() {
        return speed;
    }
    public GameState getState() {
        return state;
    }
    public Timer getTimer() {
        return timer;
    }
    public TimerTask getTask() {
        return task;
    }
    public int getPeriod() { return period; }
    public boolean getIsDead(){
        return isDead;
    }
    public long getLoadTime() {
        return loadTime;
    }
    //////////////////////////////////////////////////////////////

    //setters for component's fields//
    public void setLocY(int locY) {
        this.locY = locY;
    }
    public void setLocX(int locX) {
        this.locX = locX;
    }
    public void setCurrentImage(Image currentImage) {
        this.currentImage = currentImage;
    }
    public void setDamage(int damage) {
        this.damage = damage;
    }
    public void setLife(int life) {
        this.life = life;
    }
    public void setSpeed(int speed) {
        this.speed = speed;
    }
    public void setState(GameState state) {
        this.state = state;
    }
    public void setTask(TimerTask task) {
        this.task = task;
    }
    public void setTimer(Timer timer) {
        this.timer = timer;
    }
    public void setPeriod(int period) { this.period = period; }
    public void setIsDead(boolean dead) {
        isDead = dead;
    }
    public void setLoadTime(long loadTime) {
        this.loadTime = loadTime;
    }
    public void setTimeHolder(long timeHolder) {
        this.timeHolder = timeHolder;
    }
    //////////////////////////////////////////////////////////////


    /**
     * A method to cancel components timer
     */
    public void cancelTimer(){ timer.cancel(); }

    /**
     * A method to decrease components life by a certain amount
     * @param decreaseAmount amount of life to be decreased
     */
    public void decreaseLife(int decreaseAmount){
        life -= decreaseAmount;
    }

    /**
     * A method to set preparations for saving the component
     */
    public void save(){
        loadTime += System.currentTimeMillis() - timeHolder;
        if(timer != null){
            timer.cancel();
            timer.purge();
        }
    }

    /**
     * A method to set preparations for loading the component
     */
    public void load(){
        timer = new Timer();
        timeHolder = System.currentTimeMillis();
    }
}
