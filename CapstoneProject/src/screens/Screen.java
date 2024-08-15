package screens;

/**
 * Representation of the different screens in the program
 * 
 * @author Ohm Bhakta
 * @version 05262024
 */
public abstract class Screen {

	/**
	 * The dimensions of the screen 
	 */
	public final int DRAWING_WIDTH, DRAWING_HEIGHT;
	
	/**
	 * Sets the dimensions of the screen that are permanent
	 * 
	 * @param width The x-axis length of the screen
	 * @param height The y-axis width of the screen
	 */
	public Screen(int width, int height) {
		this.DRAWING_WIDTH = width;
		this.DRAWING_HEIGHT = height;
	}
	
	public void setup() {
		
	}
	
	/**
	 * Draws the screen and all its components to the window
	 */
	public void draw() {
		
	}
	
	/**
	 * Detects if the mouse has been clicked 
	 */
	public void mousePressed() {
		
	}
	
	/**
	 * Detects if a key on the keyboard has been pressed
	 */
	public void keyPressed(){
		
	}
	
	/**
	 * Sets up the perspective of the screen either 2d or 3d screen
	 */
	public void setPerspective() {
		
	}

	/**
	 * Detects when the user lets go of a key on the keyboard
	 */
	public void keyReleased() {
		
		
	}
	
}
