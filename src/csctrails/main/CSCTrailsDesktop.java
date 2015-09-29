package csctrails.main;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

/**
 * CSCTailsDesktop contains the main() method to be ran
 * if the game is to be ran on a desktop. It will first
 * create the application configuration and listener. 
 * It then makes a new LwjglApplication and passes it 
 * the cfg and listener. From this point, the 
 * LwjglApplication will take over and run the program.
 * 
 * Change Log:
 * 15.9.21: First Edition
 *
 */

public class CSCTrailsDesktop {
	
	public static void main(String[] args) {
		//Create Logger
		
		//Create Configuration object - gha 15.9.21
		LwjglApplicationConfiguration cfg =
			new LwjglApplicationConfiguration();
		
		cfg.title = Game.TITLE;
		cfg.width = Game.V_WIDTH * Game.SCALE;
		cfg.height = Game.V_HEIGHT * Game.SCALE;
		
		Game game = new Game();
		Game.logger.log("Main: Constructing application threads");
		//Create a new LwjglApplication, passing it the cfg and Game listener - gha 15.9.21
		new LwjglApplication(game, cfg);

		
	}
	
}
