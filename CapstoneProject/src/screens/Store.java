package screens;


import com.jogamp.newt.event.KeyEvent;

import core.DrawingSurface;
import player.Player;
import processing.core.*;


/**
 * Representation of a store which gives and allow the user to buy upgrades for the game
 * 
 * @author Ohm Bhakta
 * @version 05262024
 */
public class Store extends Screen{

	private DrawingSurface surface;
	
	private PImage img;
	
	private static final int blasterCost = 18;
	private static final int swordCost = 12;
	private static final int upgradeHealthCost = 10;
	private static final int boostedSpeedCost = 10;
	
	private boolean bought;
	
	/**
	 * Sets up all the components of the store screen
	 * @param surface The surface in which the store is drawn to
	 */
	public Store(DrawingSurface surface) {
		super(800, 600);
		this.surface= surface;
		bought = false;
	}
	
	@Override
	public void setPerspective() {
		surface.ortho();
	}
	
	public void setup() {
		img = surface.loadImage("img/starringdhruv.png");
	}

	@Override
	public void draw() {
		surface.camera();
		
		
		surface.image(img, 0, 0, 2000, 1000);
		
		surface.textSize(60);
		
		surface.text("Buy some upgrades to help improve your time.", 530, 230);
		surface.fill(255);
		surface.textSize(80);
		surface.text("Welcome to the Store", 700, 60);
		
		surface.textSize(50);
		surface.fill(255);
		surface.text("Blaster: " + blasterCost + " coins \n"
				+ "        Press 1", 140, 500);
		surface.text("Sword: " + swordCost + " coins \n"
				+ "       Press 2", 510, 500);
		surface.text("Increase health: " + upgradeHealthCost + " coins \n"
				+ "                 Press 3", 840, 500);
		surface.text("Boosted Speed: " + boostedSpeedCost + " coins \n"
				+ "          Press 4", 1360, 500);
		
		surface.text("Start Game \n"
				+ "   Press 'S'", 890, 760);
		surface.text("Coins: " + Player.coins, 20, 40);
		
		if(bought)
			surface.text("upgrade applied", 810, 360);
		
	}
	
	@Override
	public void keyPressed() {
		if(surface.keyCode == KeyEvent.VK_1 && Player.coins >= blasterCost) {
			surface.setUpgradeSword(false);
			surface.setUpgradeBlaster(true); 
			Player.coins -= blasterCost;
			bought = true;
		}
		if(surface.keyCode == KeyEvent.VK_2 && Player.coins >= swordCost) {
			surface.setUpgradeBlaster(false);
			surface.setUpgradeSword(true); 
			Player.coins -= swordCost;
			bought = true;
		}
		if(surface.keyCode == KeyEvent.VK_3 && Player.coins >= upgradeHealthCost) {
			surface.setUpgradeHealth(true); 
			Player.coins -= upgradeHealthCost;
			bought = true;
			if(surface.getCanUpgradeHealth()) {
				Player.health += 50;
			}
		}
		if(surface.keyCode == KeyEvent.VK_4 && Player.coins >= boostedSpeedCost) {
			surface.setUpgradeSpeed(true); 
			Player.coins -= boostedSpeedCost;
			bought = true;
		}
		if(surface.keyCode == KeyEvent.VK_S) {
			surface.switchScreen(2);
			bought = false;
		}
		
		
		
	}
	
}
