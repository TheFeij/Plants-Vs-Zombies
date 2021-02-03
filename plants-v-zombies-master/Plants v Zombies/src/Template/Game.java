/*** In The Name of Allah ***/
package Template;
import Template.GameFrame;
import Template.GameLoop;
import Template.GameState;
import Template.ThreadPool;
import java.awt.EventQueue;
import javax.swing.JFrame;

/**
 * Program start.
 * 
 * @author Feij
 */
public class Game {

	
    public void start(GameState state, Client client){
		// Initialize the global thread-pool
		ThreadPool.init();
		
		// Show the game menu ...
		
		// After the player clicks 'PLAY' ...
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				GameFrame frame = new GameFrame("Simple Plant Zombie Practice", client);
				frame.setLocationRelativeTo(null); // put frame at center of screen
				frame.setVisible(true);
				frame.initBufferStrategy();
				// Create and execute the game-loop
				GameLoop game = new GameLoop(frame, state, client);
				game.init();
				ThreadPool.execute(game);
				// and the game starts ...
			}
		});
    }
}
