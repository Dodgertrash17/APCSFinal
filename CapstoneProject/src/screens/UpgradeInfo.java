package screens;

import com.jogamp.newt.event.KeyEvent;

import core.DrawingSurface;

/**
 * Representation of a upgrade info screen which gives the user information about
 * all the upgrades they could buy and play with
 * 
 * @author Yujun Lee
 * @version 05262024
 */
public class UpgradeInfo extends Screen{

	private DrawingSurface surface;
	
	/**
	 * Sets up all the components of the upgrade screen
	 * @param surface The surface in which all the components are drawn to
	 */
	public UpgradeInfo(DrawingSurface surface) {
		super(800, 600);
		
		this.surface = surface;
		
	}
	
	@Override
	public void draw() {
		surface.background(0, 0, 150);
		
		surface.textSize(65);
		surface.fill(255, 185, 20);
		surface.text("Upgrade Information", 700, 50);
		
		surface.textSize(37);
		surface.fill(0, 50, 255);
		surface.fill(255, 210, 44);
		surface.text("Back: 'B'", 1740, 40);
		
				
		surface.textSize(40);
		surface.text("Blaster: ", 550, 200);
		surface.text("Sword: ", 1000, 200);
		surface.text("Extra Health:", 1000, 580);
		surface.text("Boosted Speed:", 550, 580);
		surface.textSize(30);
		surface.text("- infinite shots \n"
				+ "- infinite range\n"
				+ "- deals 80 damage\n"
				+ "- 1.4 seconds attack cool time", 550, 240);
		surface.text("- changes the max health to 150\n"
				+ "- heals by 50 \n"
				+ "- last the entire game", 1000, 620);
		surface.text("- half a wall range\n"
				+ "- infinite uses in the game\n"
				+ "- deals 40 damage\n"
				+ "- 0.7 seconds attack cool time", 1000, 240);
		surface.text("- double the normal speed\n"
				+ "- last the entire game\n"
				+ "- turning/vertical speed remains the same", 550, 620);
		
	}
	
	@Override
	public void keyPressed() {
		if(surface.keyCode == KeyEvent.VK_B)
			surface.switchScreen(1);
	}

}
