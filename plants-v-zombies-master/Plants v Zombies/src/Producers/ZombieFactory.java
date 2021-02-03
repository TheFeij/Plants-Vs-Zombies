package Producers;
import Component.Zombies.*;
import Coordinates.Coordinates;
import MusicPlayer.AudioPlayer;
import Template.GameState;

import javax.swing.*;
import java.io.Serializable;
import java.security.SecureRandom;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * this class represent a zombie factory
 * it is responsible for producing zombies
 *
 * @author Feij
 */
public class ZombieFactory implements Serializable {

    //time unit used for waves
    private int time;
    //timers responsible for each wave
    private transient Timer firstWave, secondWave, finalWave;
    //tasks to be done in each wave
    private transient TimerTask wave1, wave2, wave3;
    //state of the game
    private GameState state;
    //load time is used when saving and loading
    private long loadTime;
    //it is used to hold system tome in certain places
    private long timeHolder;


    /**
     * A constructor o create a new zombie factory
     * @param state state of the game
     */
    public ZombieFactory(GameState state){
        time = 0;
        this.state = state;
        loadTime = 0L;
        timeHolder = System.currentTimeMillis();

        finalWave = new Timer();
        firstWave = new Timer();
        secondWave = new Timer();

        wave1 = new Wave1();
        wave2 = new Wave2();
        wave3 = new Wave3();

        state.setWaveNum(0);
        firstWave.scheduleAtFixedRate(wave1, 50000, 30000);
    }

    /**
     *  A method to set preparations to load the zombie factory
     */
    public void load(){
        finalWave = new Timer();
        firstWave = new Timer();
        secondWave = new Timer();

        wave1 = new Wave1();
        wave2 = new Wave2();
        wave3 = new Wave3();

        timeHolder = System.currentTimeMillis();

        if(time == 0){
            firstWave.scheduleAtFixedRate(wave1, 50000 - loadTime, 30000);
        }
        else if(time <= 5){
            firstWave.scheduleAtFixedRate(wave1, 30000 - loadTime, 30000);
        }
        else if(time <= 11){
            secondWave.scheduleAtFixedRate(wave2, 30000 - loadTime, 30000);
        }
        else if(time <= 17){
            finalWave.scheduleAtFixedRate(wave3, 25000 - loadTime, 25000);
        }
    }

    /**
     * A method to set preparations to save zombie factory
     */
    public void save(){
        loadTime += System.currentTimeMillis() - timeHolder;
        finalWave.cancel();
        secondWave.cancel();
        firstWave.cancel();
    }

    /**
     * A class to handle events in first wave
     */
    private class Wave1 extends TimerTask{
        /**
         * The action to be performed by this timer task.
         */
        @Override
        public void run() {
            state.setWaveNum(1);
            loadTime = 0L;
            timeHolder = System.currentTimeMillis();
            if(time == 0){
                if(!state.isMute()){
                    AudioPlayer zombiesAreComming = new AudioPlayer("./Sounds/zombies_coming.wav", 0);
                }
            }
            if(time == 5){
                firstWave.cancel();
                //GameFrame.printWave2();
                state.setWaveNum(2);
                secondWave.scheduleAtFixedRate(wave2, 0, 30000);
            }
            else {
                zombieProducer();
                time += 1;
            }
        }

        /**
         * A method to produce zombies
         */
        private void zombieProducer(){
            Zombie zombie;
            SecureRandom random = new SecureRandom();
            int number = random.nextInt(10) + 1;
            int y = Coordinates.ys[random.nextInt(5)] - 10;


            if(number <= 7){
                zombie = new NormalZombie(1280, y, state);
            }
            else if(number <= 9){
                zombie = new ConeHeadZombie(1280, y, state, state.getType());
            }
            else
                zombie = new BucketHeadZombie(1280, y, state, state.getType());
            state.addZombie(zombie);
        }
    }

    /**
     * A class to handle events in second wave
     */
    private class Wave2 extends TimerTask{
        /**
         * The action to be performed by this timer task.
         */
        @Override
        public void run() {
            loadTime = 0L;
            timeHolder = System.currentTimeMillis();
            if(time == 11){
                secondWave.cancel();
                //GameFrame.printWave3();
                state.setWaveNum(3);
                finalWave.scheduleAtFixedRate(wave3, 0, 25000);
            }
            else{
                zombieProducer();
                time += 1;
            }
        }

        /**
         * A method to produce zombies
         */
        private void zombieProducer(){
            SecureRandom random = new SecureRandom();
            int number1, number2;
            number1 = random.nextInt(10) + 1;
            number2 = random.nextInt(10) + 1;

            int y1, y2;
            do{
                y1 = Coordinates.ys[random.nextInt(5)] - 10;
                y2 = Coordinates.ys[random.nextInt(5)] - 10;
            }while (y1 == y2);

            produceZombie(number1, y1);
            produceZombie(number2, y2);
        }

        private void produceZombie(int number, int y){
            Zombie zombie;
            if(number <= 4){
                //produce normal zombie
                zombie = new NormalZombie(1280, y, state);
            }
            else if(number <= 7){
                //produce conHead zombie
                zombie = new ConeHeadZombie(1280, y, state, state.getType());
            }
            else if(number <= 9) {
                //produce bucketHead zombie
                zombie = new BucketHeadZombie(1280, y, state, state.getType());
            }
            else
                zombie = new FootballZombie(1280, y, state, state.getType());

            state.addZombie(zombie);
        }
    }

    /**
     * A class to handle events in final wave
     */
    private class Wave3 extends TimerTask{
        /**
         * The action to be performed by this timer task.
         */
        @Override
        public void run() {
            loadTime = 0L;
            timeHolder = System.currentTimeMillis();
            if(time == 17){
                state.setWaveNum(4);
                time++;
                finalWave.cancel();
            }
            else{
                zombieProducer();
                time += 1;
            }
        }

        /**
         * A method to produce zombies
         */
        private void zombieProducer(){
            SecureRandom random = new SecureRandom();
            int number1, number2;
            number1 = random.nextInt(10) + 1;
            number2 = random.nextInt(10) + 1;

            int y1, y2;
            do{
                y1 = Coordinates.ys[random.nextInt(5)] - 10;
                y2 = Coordinates.ys[random.nextInt(5)] - 10;
            }while (y1 == y2);

            produceZombie(number1, y1);
            produceZombie(number2, y2);
        }

        private void produceZombie(int number, int y){
            Zombie zombie;
            if(number <= 3){
                //produce conHead zombie
                zombie = new ConeHeadZombie(1280, y, state, state.getType());
            }
            else if(number <= 6) {
                //produce bucketHead zombie
                zombie = new BucketHeadZombie(1280, y, state, state.getType());
            }
            else
                zombie = new FootballZombie(1280, y, state, state.getType());

            state.addZombie(zombie);
        }
    }
}
