package Producers;

import Component.Sun;
import Template.GameState;
import java.io.Serializable;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A class to make sky
 *
 * @author Mohammad
 */
public class Sky implements Serializable {
    //random number generator
    Random random = new Random();
    //state of the game
    GameState state;
    //period of producing suns
    private int period;
    //type of the game
    private String type;
    //load time is used in loading and saving sky
    private long loadTime;
    //a field to save system time in certain places
    private long timeHolder;
    //timer responsible to produce suns
    private transient Timer timer;


    /**
     * Perform any initialization that is required
     * @param state The game state
     * @param type The game type
     */
    public Sky(GameState state, String type) {
        this.state = state;
        if(type.equals("Hard"))
            period = 30000;
        else
            period = 25000;
        this.type = type;
        loadTime = 0L;
        timeHolder = System.currentTimeMillis();
        timer = new Timer();
        timer.schedule(new SunMaker(), 2000, period);
    }


    /**
     * A method Make sun
     */
    private void make() {
        int x = random.nextInt(9) * 130 + 110;
        int finalY = (random.nextInt(5) + 1) * 110 + 30;
        Sun sun = new Sun(x, 35, state, finalY);
        state.addSun(sun);
    }

    /**
     * A method to set preparations to save sky
     */
    public void save(){
        loadTime += System.currentTimeMillis() - timeHolder;
        if(timer != null){
            timer.cancel();
            timer.purge();
        }
    }

    /**
     * A method to set preparations to load sky
     */
    public void load(){
        timer = new Timer();
        timeHolder = System.currentTimeMillis();
        timer.schedule(new SunMaker(), period - loadTime, period);
    }



    /**
     *A class to make sun
     */
    private class SunMaker extends TimerTask {

        /**
         * The action to be performed by this timer task.
         */
        @Override
        public void run() {
            make();
            loadTime = 0L;
            timeHolder = System.currentTimeMillis();
        }


    }

}
