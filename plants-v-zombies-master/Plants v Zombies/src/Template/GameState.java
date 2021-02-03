/*** In The Name of Allah ***/
package Template;
import Component.Plants.*;
import Component.Zombies.*;
import Component.*;
import Component.Cards.*;
import Component.Bullets.*;
import Coordinates.Coordinates;
import Producers.*;
import MusicPlayer.AudioPlayer;
import javax.swing.*;
import java.awt.event.*;
import java.io.Serializable;
import java.util.*;


/**
 * This class holds the state of game and all of its elements.
 * 
 * @author Feij, Mohammd
 */
public class GameState implements Serializable {

	//list of all bullets
	private ArrayList<Bullet> bullets;
	//list of all plants
	private ArrayList<Plant> plants;
	//list of all zombies
	private ArrayList<Zombie> zombies;
	//cards in the game
	private Card[] cards;
	//lawn mowers in the game
	private LawnMower[] lawnMowers;
	//list of all suns in the game
	private ArrayList<Sun> suns;
	//current selected card
	private String currentCard;
	//state of the game
	private GameState state;
	//mouse handler
	private transient MouseHandler mouseHandler;
	//point of the game
	private int point;
	//type of the game
	private String type;
	//wave number
	private int waveNum;
	//zombie factory responsible for producing zombies
	private ZombieFactory factory;
	//sky responsible for producing suns
	private Sky sky;
	//background audio player
	private transient AudioPlayer backgroundSound;
	//is game ended or not
	private boolean endGame;
	//is game over or not
	private boolean gameOver;
	//is game saved or not
	private boolean saved;
	//username of the player
	private String username;
	//is game sound muted or not
	private boolean mute;


	/**
	 * A constructor to create a new game state
	 * @param type type of the game
	 * @param username username of the user
	 * @param sound is game mute or not
	 */
	public GameState(String type,String username, String sound) {

		bullets = new ArrayList<>();
		zombies = new ArrayList<>();
		plants = new ArrayList<>();
		suns = new ArrayList<>();
		cards = new Card[6];
		lawnMowers = new LawnMower[5];
		currentCard = null;
		state = this;
		mouseHandler = new MouseHandler();
		point = 0;
		this.type = type;
		waveNum = -1;
		endGame = false;
		saved = false;
		gameOver = false;
		mute = sound.equals("Mute");
		this.username = username;
		init();
	}




	//setters/////////////////////////////////////////////////////////////////////
	public void setSaved(boolean saved) {
		this.saved = saved;
	}
	public void setWaveNum(int waveNum) {
		this.waveNum = waveNum;
	}
	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}
	public void setEndGame(boolean endGame) {
		this.endGame = endGame;
	}
	/////////////////////////////////////////////////////////////////////////////

	//getters/////////////////////////////////////////////////////////////////////
	public List<Bullet> getBullets() { return bullets; }
	public int getWaveNum() {
		return waveNum;
	}
	public List<Plant> getPlants() { return plants; }
	public String getUsername() {
		return username;
	}
	public List<Zombie> getZombies() { return zombies; }
	public Card[] getCards() { return cards; }
	public List<Sun> getSuns() { return suns; }
	public boolean getEndGame(){
		return endGame;
	}
	public LawnMower[] getLawnMowers() { return lawnMowers; }
	public MouseListener getMouseListener() { return mouseHandler; }
	public MouseMotionListener getMouseMotionListener() { return mouseHandler; }
	public boolean isMute(){
		return mute;
	}
	public int getPoint() { return point; }
	public String getType() { return type; }
	public boolean isGameOver() {
		return gameOver;
	}
	public boolean isSaved() {
		return saved;
	}
	//////////////////////////////////////////////////////////////////////////////


	/**
	 * A method to initialize game state
	 */
	public void init(){
		sky = new Sky(state,type);
		factory = new ZombieFactory(state);
		Card card1 = new Card_Sunflower(150, 35, state) ;
		Card card2 = new Card_Peashooter(250, 35, state) ;
		Card card6 = new Card_Repeater(350, 35, state) ;
		Card card3 = new Card_Snowpea(450, 35, state) ;
		Card card4 = new Card_Wallnut(550, 35, state) ;
		Card card5 = new Card_Cherrybomb(650, 35, state) ;


		LawnMower lawnMower1 = new LawnMower(-30, 130, state);
		LawnMower lawnMower2 = new LawnMower(-30, 235, state);
		LawnMower lawnMower3 = new LawnMower(-30, 345, state);
		LawnMower lawnMower4 = new LawnMower(-30, 470, state);
		LawnMower lawnMower5 = new LawnMower(-30, 565, state);

		cards[0] = card1;
		cards[1] = card2;
		cards[2] = card3;
		cards[3] = card4;
		cards[4] = card5;
		cards[5] = card6;

		lawnMowers[0] = lawnMower1;
		lawnMowers[1] = lawnMower2;
		lawnMowers[2] = lawnMower3;
		lawnMowers[3] = lawnMower4;
		lawnMowers[4] = lawnMower5;

		if(!mute)
			backgroundSound = new AudioPlayer("./Sounds/background.wav", -1);
	}

	/**
	 * The method which updates the game state.
	 */
	public void update() throws ConcurrentModificationException {
		Iterator<Zombie> iterator1 = zombies.iterator();
		while (iterator1.hasNext()) {
			Zombie zombie  = iterator1.next();
			//if a zombie is dead, remove it from list
			if (zombie.getLife() <= 0){
				zombie.destroyZombie();
				iterator1.remove();
			}

			//what happens when zombies reach the end of line
			if (zombie.getLocX() <= 20){
				//if there is a lawnMower in path then activate it
				if (lawnMowers[Coordinates.getRow(zombie.getLocY())].getTime() == -1) {
					lawnMowers[Coordinates.getRow(zombie.getLocY())].start();
				}

				//if there are no lawnMower then its a game over
				if (zombie.getLocX() < 0){
					JOptionPane.showMessageDialog(null, "Game over!", null, JOptionPane.INFORMATION_MESSAGE);
					if(!mute){
						AudioPlayer gameOverSound = new AudioPlayer("./Sounds/atebrains.wav", 0);
					}

					endGame = true;
					gameOver = true;
				}
			}

			Iterator<Bullet> iterator4 = bullets.iterator();
			while (iterator4.hasNext()) {
				Bullet bullet = iterator4.next();
				if (bullet.getLocX() > 1280){
					iterator4.remove();
				}
				else if (bullet.getLocX() - zombie.getLocX() >= 15 && bullet.getLocX() - zombie.getLocX() <= 70
						&& Coordinates.checkRowEquality(zombie.getLocY(), bullet.getLocY())) {
					iterator4.remove();
					if (bullet instanceof FrozenPea) {
						zombie.decreaseLife(35);
						zombie.freezeZombie();
					}
					else {
						zombie.decreaseLife(30);
					}
				}

			}

			Iterator<Plant> iterator5 = plants.iterator();
			while (iterator5.hasNext()){
				Plant plant = iterator5.next();
				if (plant.getLife() <= 0){
					iterator5.remove();
				}
				else if (Coordinates.checkRowEquality(plant.getLocY(), zombie.getLocY())){
					if(zombie.getLocX() >= plant.getLocX() && zombie.getLocX() <= plant.getLocX() + 40){
						zombie.startEating(plant);
					}
				}

				if (plant instanceof CherryBomb){
					CherryBomb bomb = (CherryBomb) plant;
					if(bomb.getIsDead()){
						Iterator<Zombie> iterator7 = zombies.iterator();
						while (iterator7.hasNext()){
							Zombie zombie1 = iterator7.next();
							if (Math.abs(zombie1.getLocX() - plant.getLocX()) < 140 && Math.abs(zombie1.getLocY() - plant.getLocY()) < 130){
								zombie1.destroyZombie();
								iterator7.remove();
							}
						}
						bomb.setLife(0);
					}
				}
			}

			for (LawnMower lawnMower : lawnMowers){
				if (Coordinates.checkRowEquality(lawnMower.getLocY(), zombie.getLocY())
						&& zombie.getLocX() - lawnMower.getLocX() < 20){
					iterator1.remove();
				}
			}

			checkEndGame();
		}

		Iterator<Plant> plantIterator = plants.iterator();
		while (plantIterator.hasNext()){
			Plant plant = plantIterator.next();
			if (plant instanceof CherryBomb){
				CherryBomb bomb = (CherryBomb) plant;
				if(bomb.getIsDead())
					plantIterator.remove();
			}
		}

		Iterator<Sun> iterator2 = suns.iterator();
		while (iterator2.hasNext()){
			Sun sun = iterator2.next();
			if (sun.getLifeTime() >= 200){
				iterator2.remove();
			}
		}

	}

	/**
	 * A method to check is game ended or not
	 */
	public void checkEndGame(){
		if(saved){
			endGame = true;
		}
		else
			endGame = getWaveNum() == 4 && zombiesDestroyed();
	}

	/**
	 * A method to add a bullet to bullets list
	 * @param bullet bullet to be added
	 */
	public void addBullet(Bullet bullet){ bullets.add(bullet); }

	/**
	 * A method to add a plant to plants list
	 * @param plant plant to be added
	 */
	public void addPlant(Plant plant){
		plants.add(plant);
	}

	/**
	 * A method to add a zombie to zombies list
	 * @param zombie zombie to be added
	 */
	public void addZombie(Zombie zombie){
		zombies.add(zombie);
	}

	/**
	 * A method to check if all zombies are destroyed or not
	 * @return true if all zombies are destroyed
	 */
	public boolean zombiesDestroyed(){
		return zombies.size() == 0;
	}

	/**
	 * A method to add a sun to suns list
	 * @param sun sun to be added
	 */
	public void addSun(Sun sun) { suns.add(sun); }

	/**
	 * A method to check if zombies are in way or not
	 * @param locY y location of the plant
	 * @param locX x location of the plant
	 * @return true if zombie is in way
	 */
    public boolean isZombieInWay(int locY, int locX){
		for(Zombie zombie : zombies){
			if(Coordinates.checkRowEquality(zombie.getLocY(), locY) && zombie.getLocX() > locX)
				return true;
		}
		return false;
	}

	/**
	 * A method to check if house is free or not
	 * @param x x location of the house
	 * @param y y location of the house
	 * @return true if house is free
	 */
	public boolean isHouseFree(int x, int y){
		for (Plant plant : plants){
			if (plant.getLocX() == x && plant.getLocY() == y){
				return false;
			}
		}
		return true;
	}

	/**
	 * A method to set preparations to save game state
	 */
	public void save(){
		for(Zombie zombie : zombies)
			zombie.save();
		for(Plant plant : plants)
			plant.save();
		for(Bullet bullet : bullets)
			bullet.save();
		for(Card card : cards)
			card.save();
		for(LawnMower mower : lawnMowers)
			mower.save();
		for(Sun sun : suns)
			sun.save();
		factory.save();
		sky.save();
		if(!mute)
			backgroundSound.stop();
	}

	/**
	 * A method to set preparations to load game state
	 */
	public void load(){
		for(Zombie zombie : zombies)
			zombie.load();
		for(Plant plant : plants)
			plant.load();
		for(Bullet bullet : bullets)
			bullet.load();
		for(Card card : cards)
			card.load();
		for(LawnMower mower : lawnMowers)
			mower.load();
		for(Sun sun : suns)
			sun.load();
		factory.load();
		sky.load();
		mouseHandler = new MouseHandler();
		if(!mute)
			backgroundSound = new AudioPlayer("./Sounds/background.wav", -1);
	}

	/**
	 * A class to handle mouse events in game frame
	 */
	class MouseHandler extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent event) {
			int x = event.getX();
			int y = event.getY();

			while (true){
				try{
					if (currentCard == null){
						Iterator<Sun> iterator = suns.iterator();
						while (iterator.hasNext()){
							Sun sun = iterator.next();
							if(Math.abs(sun.getLocX() + 25 - x) <= 30 && Math.abs(sun.getLocY() + 25 - y) <= 40){
								iterator.remove();
								if(!mute){
									AudioPlayer ting = new AudioPlayer("./Sounds/ting.wav", 0);
								}
								point += 25;
							}
						}
					}

					if (currentCard != null) {
						Plant plant = null;
						int[] plantCoordinates = Coordinates.getCoordinates(x, y);
						int planX = plantCoordinates[0];
						int plantY = plantCoordinates[1];
						if (isHouseFree(planX, plantY)) {
							if (x > 55 && x < 1210 && y > 130 && y < 685) {
								switch (currentCard) {
									case "sun_flower.png":
										point -= 50;
										cards[0].select();
										plant = new Sunflower(planX, plantY, state);
										break;
									case "peashooter.png":
										point -= 100;
										cards[1].select();
										plant = new Peashooter(planX, plantY, state);
										break;
									case "repeater.png":
										point -= 200;
										cards[5].select();
										plant = new Repeater(planX, plantY, state);
										break;
									case "freezepeashooter.png":
										point -= 175;
										cards[2].select();
										plant = new Snowpea(planX, plantY, state);
										break;
									case "walnut_full_life.png":
										point -= 50;
										cards[3].select();
										plant = new GiantWallNut(planX, plantY, state);
										break;
									case "cherrybomb.png":
										point -= 150;
										cards[4].select();
										plant = new CherryBomb(planX, plantY, state);
										break;
									default:
										break;
								}
								addPlant(plant);
								currentCard = null;
							}
						}
					}
					break;

				}catch (ConcurrentModificationException e){

				}
			}

			if (point >= 50 && cards[0].getActive()) {
				if (x > 150 && x < 215 && y > 35 && y < 125) {
					currentCard = "sun_flower.png";
				}
			}
			if (point >= 100 && cards[1].getActive()) {
				if (x > 250 && x < 315 && y > 35 && y < 125) {
					currentCard = "peashooter.png";
				}
			}
			if (point >= 200 && cards[5].getActive()) {
				if (x > 350 && x < 415 && y > 35 && y < 125) {
					currentCard = "repeater.png";
				}
			}
			if (point >= 175 && cards[2].getActive()) {
				if (x > 450 && x < 515 && y > 35 && y < 125) {
					currentCard = "freezepeashooter.png";
				}
			}
			if (point >= 50 && cards[3].getActive()) {
				if (x >550 && x < 615 && y > 35 && y < 125) {
					currentCard = "walnut_full_life.png";
				}
			}
			if (point >= 150 && cards[4].getActive()) {
				if (x > 650 && x < 715 && y > 35 && y < 125) {
					currentCard = "cherrybomb.png";
				}
			}

		}

	}

}