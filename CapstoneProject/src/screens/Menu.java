package screens;

import com.jogamp.newt.event.KeyEvent;

import core.DrawingSurface;

/**
 * Representation of a menu which gives the user important information about the game
 * such as the story line and the controls
 * 
 * @author Ohm Bhakta
 * @version 05262024
 */
public class Menu extends Screen{

	private DrawingSurface surface;
	
	/**
	 * Sets up all the components for the menu
	 * @param surface The surface in which all the components are drawn out to
	 */
	public Menu(DrawingSurface surface) {
		super(800, 600);
		this.surface = surface;
	}
	
	@Override
	public void setPerspective() {
		surface.ortho();
	}
	
	@Override
	public void draw() {
		surface.background(100, 43, 110);
		
		surface.fill(255, 189, 44);
		String start = "Start Screen: B \n"
				+ "Upgrade Info: U";
		surface.textSize(40);
		surface.text(start, 1650, 40);
		
		surface.fill(253, 185, 39);
		surface.textSize(110);
		surface.text("Menu", 1250, 120, 800, 200);
		
		surface.textSize(80);
		surface.text("Controls: ", 1700, 230, 800, 200);
		surface.textSize(50);
		surface.text("W - Move Forward \n"
				+ "A - Move Left \n"
				+ "S - Move Backwards \n"
				+ "D - Move Right \n"
				+ "Space - Move Up \n"
				+ "Alt - Move Down \n"
				+ "Left Click - Attack \n"
				+ "T - Open Store(Only in game)" , 1300, 330);
		
		surface.textSize(80);
		surface.text("Introduction:", 500, 230, 800, 200);
		surface.textSize(30);
		surface.text("Welcome to our Capstone Project \"Escape Dhruv's Maze.\"\n"
				+ "This is a 3-dimensional game where you have to find your way\n"
				+ "through as fast as your can. You also have to kill at least 10\n"
				+ "monsteers before you can escape. Monsters(Dhruvs)will be present\n"
				+ "while you are trying to find your way through the maze. They\n"
				+ "will try to attack you. You will have 100 health and once the\n"
				+ "monsters take away all your health, you will be knocked out of\n"
				+ "the game. There will also be coins all around the map to collect.\n"
				+ "All around the maze there will be stores where you can interact\n"
				+ "with them to take you to the store to buy upgrades with the coins\n"
				+ "you collected. To learn more about the upgrade avaiable, press \n"
				+ "\"U\". Upgrade only last for one game and go away after that. When\n"
				+ "you get knocked out of the game, you will be taken to the leaderboard \n"
				+ "screen where you can see how long you look and your top times. We \n"
				+ "hope you enjoy and don't let the monsters(Dhruvs) catch you!", 100, 260);
		
	}
	
	@Override
	public void keyPressed() {
		if(surface.keyCode == KeyEvent.VK_B) 
			surface.switchScreen(0);
		else if(surface.keyCode == KeyEvent.VK_U)
			surface.switchScreen(4);
	}
	
}
