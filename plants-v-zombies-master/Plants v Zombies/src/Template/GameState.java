/*** In The Name of Allah ***/
package Template;

import Component.Plants.*;
import Component.Zombies.*;
import Component.*;
import Component.Cards.*;
import Component.Bullets.*;
import Coordinates.Coordinates;
import Network.User;
import Producers.*;
import MusicPlayer.AudioPlayer;
import javax.swing.*;
import java.awt.event.*;
import java.io.Serializable;
import java.util.*;
import java.util.Timer;

/**
 * This class holds the state of game and all of its elements.
 * 
 * @author Feij, Mohammd
 */
public class GameState implements Serializable {

	private ArrayList<Bullet> bullets;
	private ArrayList<Plant> plants;
	private ArrayList<Zombie> zombies;
	private Card[] cards;
	private LawnMower[] lawnMowers;
	private ArrayList<Sun> suns;
	private String currentCard;
	private GameState state;
	private transient MouseHandler mouseHandler;
	private int point;
	private String type;
	private int waveNum;
	private ZombieFactory factory;
	private Sky sky;
	private transient AudioPlayer backgroundSound;
	private boolean endGame;
	private boolean gameOver;
	private boolean saved;
	private String username;

	
	public GameState(String type,String username) {

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
		this.username = username;
		init();
	}


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

		backgroundSound = new AudioPlayer("./Sounds/background.wav", -1);
	}


	/**
	 * The method which updates the game state.
	 */
	public void update() throws InterruptedException, ConcurrentModificationException {
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
					AudioPlayer gameOverSound = new AudioPlayer("./Sounds/atebrains.wav", 0);
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

	public void checkEndGame(){
		if(saved){
			endGame = true;
		}
		else
			endGame = getWaveNum() == 4 && zombiesDestroyed();
	}

	public void addBullet(Bullet bullet){ bullets.add(bullet); }

	public void removeBullet(Bullet bullet){ bullets.remove(bullet); }

	public void addPlant(Plant plant){
		plants.add(plant);
	}

	public void removePlant(Plant plant){ plants.remove(plant); }

	public boolean isSaved() {
		return saved;
	}

	public void addZombie(Zombie zombie){
		zombies.add(zombie);
	}

	public List<Bullet> getBullets() { return bullets; }

	public List<Plant> getPlants() { return plants; }

	public String getUsername() {
		return username;
	}

	public List<Zombie> getZombies() { return zombies; }

	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}

	public boolean isGameOver() {
		return gameOver;
	}

	public boolean zombiesDestroyed(){
		return zombies.size() == 0;
	}

	public Card[] getCards() { return cards; }

	//public void addCard(Card card) { cards.add(card); }

	public List<Sun> getSuns() { return suns; }

	public boolean getEndGame(){
		return endGame;
	}

	public void setEndGame(boolean endGame) {
		this.endGame = endGame;
	}

	public void addSun(Sun sun) { suns.add(sun); }

	public LawnMower[] getLawnMowers() { return lawnMowers; }

	public MouseListener getMouseListener() { return mouseHandler; }

	public MouseMotionListener getMouseMotionListener() { return mouseHandler; }

	public int getPoint() { return point; }

	public String getType() { return type; }

	public void setSaved(boolean saved) {
		this.saved = saved;
	}

	public void setWaveNum(int waveNum) {
        this.waveNum = waveNum;
    }

    public int getWaveNum() {
        return waveNum;
    }

    public boolean isZombieInWay(int locY, int locX){
		for(Zombie zombie : zombies){
			if(Coordinates.checkRowEquality(zombie.getLocY(), locY) && zombie.getLocX() > locX)
				return true;
		}
		return false;
	}


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
								AudioPlayer ting = new AudioPlayer("./Sounds/ting.wav", 0);
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



	public boolean isHouseFree(int x, int y){
		for (Plant plant : plants){
			if (plant.getLocX() == x && plant.getLocY() == y){
				return false;
			}
		}
		return true;
	}


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
		backgroundSound.stop();
	}

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
		backgroundSound = new AudioPlayer("./Sounds/background.wav", -1);
	}
}