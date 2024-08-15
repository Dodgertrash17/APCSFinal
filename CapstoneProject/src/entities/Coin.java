package entities;

import player.Player;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PShape;

/**
 * Representation of a coin that can be found in the maze
 * 
 * @author Dhruv Solanki, Yujun Lee
 * @version 05262024
 */
public class Coin extends Entity {
	
	private PShape coinShape;
	
	/**
	 * Creates a new instance of a Coin
	 * 
	 * @param x the x-coordinates of the coin
	 * @param y the y-coordinates of the coin
	 * @param z the z-coordinates of the coin
	 * @param coinImage the image for the coin
	 * @param p the PApplet in which the coin is drawn
	 */
	public Coin(double x, double y, double z, PImage coinImage, PApplet p) {
		super(x, y, z, coinImage);
		coinShape = createCoinShape(p);
	}

	@Override
	public void display(PApplet p) {
		p.pushMatrix();
		p.translate((float)getX(), (float)getY(), (float)getZ());
		
		p.shape(coinShape);
		p.popMatrix();

	}
	
	/**
	 * Checks if the player is touching the coin.
	 * 
	 * @param p the player of the game
	 * @return true if the player and the coin is touching.
	 */
	public boolean isTouching(Player p) {
		double dist = Math.sqrt(Math.pow(getX() - p.getPosition().x, 2) + Math.pow(getY() - p.getPosition().y, 2) + Math.pow(getZ() - p.getPosition().z,2));
		if(dist <= 1.5)
			return true;
		return false;
	}
	
	private PShape createCoinShape(PApplet p) {
		PShape shape = p.createShape(PShape.SPHERE, 1.3f);
		shape.setTexture(getImage());
		
		return shape;

	}
	
	public PShape getCoinShape() {
		return coinShape;
	}

}
