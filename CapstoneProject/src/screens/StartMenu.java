package screens;


import com.jogamp.newt.event.KeyEvent;

import core.DrawingSurface;
import processing.core.*;

/**
 * Representation of a start menu which is the first screen the user is presented 
 * with and allows them to start the game
 * 
 * @author Ohm Bhakta
 * @version 05262024
 */
public class StartMenu extends Screen{

	private DrawingSurface surface;
	private PImage img;
	
	/**
	 * Sets up all the components of the start screen
	 * @param surface The surface in which all the components are drawn to
	 */
	public StartMenu(DrawingSurface surface) {
		super(800, 600);
		
		this.surface = surface;
		
	}
	
	public void setup() {
		PFont titleFont = surface.createFont("TitleFont.ttf", 60);
		surface.textFont(titleFont);
		img = surface.loadImage("img/dhruvmogging.png");
	}
	
	@Override
	public void setPerspective() {
		surface.ortho();
	}
	
	@Override
	public void draw() {
		
		surface.image(img, 0, 0, 2000, 1000);
		surface.textSize(150);
		surface.fill(255, 15, 20);
		surface.text("Escape Dhruv's Maze",  DrawingSurface.CENTER +400, 130);
		surface.fill(0, 200, 200);
		surface.textSize(50);
		surface.text("PRESS 'S' TO START", 1200, surface.height - 200, 800, 200);
		surface.text("PRESS 'M' FOR MENU", 1200, surface.height - 100, 800, 200);
		surface.text("PRESS 'L' FOR LEADERBOARD", 1130, surface.height, 800, 200);

	}
	
	@Override
	public void keyPressed() {
		if(surface.keyCode == KeyEvent.VK_S) 
			surface.switchScreen(2);
		else if(surface.keyCode == KeyEvent.VK_M) 
			surface.switchScreen(1);
		else if(surface.keyCode == KeyEvent.VK_L)
			surface.switchScreen(5);
		
	}


}
