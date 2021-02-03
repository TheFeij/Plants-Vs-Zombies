/*** In The Name of Allah ***/
package Template;

import Network.Client;
import java.util.ConcurrentModificationException;

/**
 * A very simple structure for the main game loop.
 * THIS IS NOT PERFECT, but works for most situations.
 * Note that to make this work, none of the 2 methods 
 * in the while loop (update() and render()) should be 
 * long running! Both must execute very quickly, without 
 * any waiting and blocking!
 * 
 * Detailed discussion on different game loop design
 * patterns is available in the following link:
 *    http://gameprogrammingpatterns.com/game-loop.html
 * 
 * @author Seyed Mohammad Ghaffarian, Feij, Mohammad
 */
public class GameLoop implements Runnable {
	
	/**
	 * Frame Per Second.
	 * Higher is better, but any value above 24 is fine.
	 */
	public static final int FPS = 30;
	//game frame
	private GameFrame canvas;
	//state of the game
	private GameState state;
	//client to connect to server
	private Client client;


	/**
	 * A constructor to create a new game loop
	 * @param frame game frame
	 * @param state state of the game
	 * @param client client to connect to server
	 */
	public GameLoop(GameFrame frame, GameState state, Client client) {
		if(state.isSaved()){
			state.setSaved(false);
			state.load();
		}
		this.state = state;
		canvas = frame;
		canvas.setState(state);
		canvas.addMouseListener(state.getMouseListener());
		canvas.addMouseMotionListener(state.getMouseMotionListener());
		this.client = client;
	}
	
	/**
	 * This must be called before the game loop starts.
	 */
	public void init() {
	}

	@Override
	public void run() {
		while (!state.getEndGame()) {
			try {
				long start = System.currentTimeMillis();
				//
				state.update();
				canvas.render(state);
				//
				long delay = (1000 / FPS) - (System.currentTimeMillis() - start);
				if (delay > 0)
					Thread.sleep(delay);
			} catch (InterruptedException | NullPointerException | ConcurrentModificationException ex) {

			}catch (IllegalStateException e){
				break;
			}
		}
		try{
			canvas.render(state);
		}catch (IllegalStateException ignored){

		}
		canvas.setVisible(false);
		if(!state.isSaved()){
			int score;
			if(state.getType().equals("Normal")){
				if(state.isGameOver())
					score = -1;
				else
					score = 3;
			}
			else {
				if(state.isGameOver())
					score = -3;
				else
					score = 10;
			}
			client.setScore(score);
			client.connect("Give Score");
		}

	}
}
