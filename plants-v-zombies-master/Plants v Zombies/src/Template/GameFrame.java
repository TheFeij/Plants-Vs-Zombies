/*** In The Name of Allah ***/
package Template;
import Component.*;
import Component.Plants.*;
import Component.Zombies.*;
import Component.Bullets.*;
import Component.Cards.*;
import Gui.LoadPage;
import Gui.PauseMenuFrame;
import Gui.SavePage;
import Network.Client;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.logging.Handler;

/**
 * The window on which the rendering is performed.
 * This example uses the modern BufferStrategy approach for double-buffering, 
 * actually it performs triple-buffering!
 * For more information on BufferStrategy check out:
 *    http://docs.oracle.com/javase/tutorial/extra/fullscreen/bufferstrategy.html
 *    http://docs.oracle.com/javase/8/docs/api/java/awt/image/BufferStrategy.html
 * 
 * @author Seyed Mohammad Ghaffarian, Feij, Mohammad
 */
public class GameFrame extends JFrame {

	public static final int GAME_HEIGHT = 720;                  // 720p game resolution
	public static final int GAME_WIDTH = 16 * GAME_HEIGHT / 9;  // wide aspect ratio
	private Image background;									//Background image
	private BufferStrategy bufferStrategy;
	//state of the game
	private GameState state;
	//client to connect to server
	private Client client;

	/**
	 * Constructor to create a new GameFrame
	 * @param title title of the frame
	 */
	public GameFrame(String title, Client client) {
		super(title);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(GAME_WIDTH, GAME_HEIGHT);
		background = new ImageIcon("./Pics/mainBG.png").getImage();
		addKeyListener(new KeyHandler());

		this.client = client;
	}
	
	/**
	 * This must be called once after the JFrame is shown:
	 *    frame.setVisible(true);
	 * and before any rendering is started.
	 */
	public void initBufferStrategy() {
		// Triple-buffering
		createBufferStrategy(3);
		bufferStrategy = getBufferStrategy();
	}

	/**
	 * Game rendering with triple-buffering using BufferStrategy.
	 */
	public void render(GameState state) {
		// Render single frame
		do {
			// The following loop ensures that the contents of the drawing buffer
			// are consistent in case the underlying surface was recreated
			do {
				// Get a new graphics context every time through the loop
				// to make sure the strategy is validated
				Graphics2D graphics = (Graphics2D) bufferStrategy.getDrawGraphics();
				try {
					doRendering(graphics, state);
				} finally {
					// Dispose the graphics
					graphics.dispose();
				}
				// Repeat the rendering if the drawing buffer contents were restored
			} while (bufferStrategy.contentsRestored());

			// Display the buffer
			bufferStrategy.show();
			// Tell the system to do the drawing NOW;
			// otherwise it can take a few extra ms and will feel jerky!
			Toolkit.getDefaultToolkit().sync();

		// Repeat the rendering if the drawing buffer was lost
		} while (bufferStrategy.contentsLost());
	}
	
	/**
	 * Rendering all game elements based on the game state.
	 */
	private void doRendering(Graphics2D g2d, GameState state) {
		//Drawing background Image
		g2d.drawImage(background, 0, 33, null);

		//Receiving game components
		List<Plant> plants = state.getPlants();
		List<Bullet> bullets = state.getBullets();
		List<Zombie> zombies = state.getZombies();
		List<Sun> suns = state.getSuns();
		Card[] cards = state.getCards();
		LawnMower[] lawnMowers = state.getLawnMowers();

		try {
			//Drawing game components


			for (Plant plant : plants) {
				g2d.drawImage(plant.getCurrentImage(), plant.getLocX(), plant.getLocY(), null);
			}
			for (Card card : cards) {
				g2d.drawImage(card.getCurrentImage(), card.getLocX(), card.getLocY(), null);
			}
			for (Zombie zombie : zombies) {
				g2d.drawImage(zombie.getCurrentImage(), zombie.getLocX(), zombie.getLocY(), null);
			}
			for (LawnMower lawnMower : lawnMowers){
				g2d.drawImage(lawnMower.getCurrentImage(), lawnMower.getLocX(), lawnMower.getLocY(), null);
			}
			for (Sun sun : suns) {
				g2d.drawImage(sun.getCurrentImage(), sun.getLocX(), sun.getLocY(), null);
			}
			for (Bullet bullet : bullets) {
				g2d.drawImage(bullet.getCurrentImage(), bullet.getLocX(), bullet.getLocY(), null);
			}
			String str = state.getPoint() + "";
			g2d.setColor(Color.BLACK);
			g2d.setFont(g2d.getFont().deriveFont(Font.BOLD).deriveFont(28.0f));
			//int strWidth = g2d.getFontMetrics().stringWidth(str);
			g2d.drawString(str, 55, 125);

			int waveNumber = state.getWaveNum();
			if(waveNumber != 0){
				int temp = 0;
				String waveName = "";
				if(waveNumber == 1)
					waveName = "First  Wave!";
				else if(waveNumber == 2){
					waveName = "Second Wave!";
					temp = 10;
				}
				else if(waveNumber == 3)
					waveName = "Final  Wave!";
				g2d.setColor(Color.WHITE);
				g2d.setFont(g2d.getFont().deriveFont(Font.BOLD).deriveFont(24.0f));
				g2d.drawString(waveName, GAME_WIDTH- 170 - temp, 55);
			}

		}

		catch (ConcurrentModificationException e){
			//doRendering(g2d, state);
		}


	}

	/**
	 * A method to set game state
	 * @param state game state to be set
	 */
	public void setState(GameState state) {
		this.state = state;
	}


	/**
	 * A class to handle key events in game frame
	 */
	private class KeyHandler extends KeyAdapter {

		public void keyReleased(KeyEvent event){
			if(event.getKeyCode() == KeyEvent.VK_ESCAPE){
				save();
				//serverSave();
				setVisible(false);
				PauseMenuFrame pauseMenuFrame = new PauseMenuFrame(client);
			}
		}

		/**
		 * A method to save game locally
		 */
		public void save(){
			try {
				String username = state.getUsername();
				new File("./Saves/" + username).mkdirs();
				ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("./Saves/" + username + "/temp.bin"));
				state.save();
				state.setSaved(true);
				out.writeObject(state);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		/**
		 * A method to save game to server
		 */
		public void serverSave(){
			state.save();
			state.setSaved(true);
			client.setWantedSave("temp");
			client.setSave(state);
			client.connect("Save");
		}
	}
}
