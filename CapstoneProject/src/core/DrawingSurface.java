package core;

import java.util.ArrayList;

import player.Player;
import screens.*;
import processing.core.*;

/**
 * DrawingSurface is a surface where the game is performed and drawn.
 * 
 * @author Ohm Bhakta, Yujun Lee
 * @version 05262024
 */

public class DrawingSurface extends PApplet{
	
	private ArrayList<Integer> keys = new ArrayList<Integer>();
	
	private boolean canUpgradeBlaster, canUpgradeSword, canUpgradeHealth, canUpgradeSpeed;
	
	private Screen[] screens = {new StartMenu(this), new Menu(this), new GameScreen(this), new Store(this), new UpgradeInfo(this), new GameEndScreen(this)};
	private Screen activeScreen = screens[0];
	
	public void settings() {
		super.size(super.displayWidth-50, super.displayHeight-150, P3D);
	}

	public void setup() {

		super.windowMove(0, 0);
		rectMode(CENTER);
		noStroke();
		fill(255);
		this.frameRate(60);
        for(Screen s : screens)
        	s.setup();
        
        canUpgradeBlaster = false;
        canUpgradeSword = false;
        canUpgradeHealth = false;
        canUpgradeSpeed = false;
		
		keys = new ArrayList<Integer>();
		
	}

	/**
	 * Draws the surface.
	 */
	public void draw() {
		
		activeScreen.draw();
		
	}
	
	

	public void keyPressed() {
		if (!checkKey(keyCode))
			keys.add(keyCode);

		activeScreen.keyPressed();
	}

	public void keyReleased() {
		while (checkKey(keyCode))
			keys.remove(Integer.valueOf(keyCode));
		activeScreen.keyReleased();
	}
	
	// Removes key from array list

	public boolean checkKey(int i) {
		return keys.contains(i);
	}
	
	public boolean isPressed(Integer code) {
		return keys.contains(code);
	}
	
	/**
	 * Shifts the screen to Store
	 */
	public void moveToStore() {
		activeScreen = screens[3];
		activeScreen.setPerspective();
	}
	
	/**
	 * Ends the game, which moves the games to the GameEndScreen
	 * 
	 * @param hasWon true if the player won, false if the player died
	 */
	public void endGame(boolean hasWon) {
		ortho();
		activeScreen = screens[5];
		activeScreen.setPerspective();
		GameEndScreen end = (GameEndScreen)activeScreen;
		if(hasWon){
			end.setWin(true);
			end.setTime();
			end.addTime();
		} else {
			end.setDead(true);
		}
	}

	/**
	 * Switches the screen
	 * 
	 * @param i the value of each screen
	 */
	public void switchScreen(int i) {
		activeScreen = screens[i];
		activeScreen.setPerspective();
		if(i == 2) {
			((GameScreen)activeScreen).setSubTime(this.millis());
		}
			
		
	}
	
	public void setUpgradeBlaster(boolean t) {
		canUpgradeBlaster = t;
	}
	
	public void setUpgradeSword(boolean t) {
		canUpgradeSword = t;
	}
	
	public void setUpgradeHealth(boolean t) {
		canUpgradeHealth = t;
	}
	
	public void setUpgradeSpeed(boolean t) {
		canUpgradeSpeed = t;
	}

	public boolean getCanUpgradeBlaster() {
		return canUpgradeBlaster;
	}
	
	public boolean getCanUpgradeSword() {
		return canUpgradeSword;
	}
	
	public boolean getCanUpgradeHealth() {
		return canUpgradeHealth;
	}
	
	public boolean getCanUpgradeSpeed() {
		return canUpgradeSpeed;
	}
	
	/**
	 * Starts a new game
	 */
	public void newGame() {
		screens[2] = new GameScreen(this);
		screens[2].setup();	
		canUpgradeBlaster = false;
		canUpgradeSword = false;
		canUpgradeHealth = false;
		canUpgradeSpeed = false;
		Player.coins = 0;
		Player.health = 100;
	}
	
	public void mousePressed() {
		if(activeScreen.equals(screens[2]))
			((GameScreen)activeScreen).mousePressed();
		else
			activeScreen.mousePressed();
	}
	

}
