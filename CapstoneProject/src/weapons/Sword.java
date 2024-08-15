package weapons;

import entities.Monster;
import processing.core.*;

/**
 * Represents a sword which is a hand held weapon 
 * 
 * @author Ohm Bhakta
 * @version 05262024
 */
public class Sword extends Weapon{

	private PShape p;

	/**
	 * Sets up the damage and location of the sword
	 * @param damage The amount of damage that the sword can do
	 * @param xOffset the x-distance between the player and the Sword
	 * @param yOffset the y-distance between the player and the Sword
	 * @param zOffset the z-distance between the player and the Sword
	 * @param g the PApplet in which the Blaster is drawn
	 */
	public Sword(int damage, double xOffset, double yOffset, double zOffset, PApplet g) {
		super(damage, 0.2f, 1.2f, 0.2f, xOffset, yOffset, zOffset, g.loadImage("img/sword.png"));
		p = createShape(g);
	}

	
	/**
	 * Detects whether the sword makes any interaction with a monster in the maze
	 * @param dhruv The monster that is found in the maze
	 * @return true if there is an interaction made, false if there is not interaction made
	 */
	public boolean isTouching(Monster dhruv) {
		
		if(getX() >= dhruv.getX() - dhruv.getWidth()&& getX() <= dhruv.getX() + dhruv.getWidth() && 
				getY() >= dhruv.getY() - dhruv.getHeight() && getY() <= dhruv.getY() + dhruv.getHeight() && 
				getZ() >= dhruv.getZ() - dhruv.getDepth() && getZ() <= dhruv.getZ() + dhruv.getDepth())
			return true;
		
		return false;
	}
	
	

	@Override
	public void draw(PApplet surface) {
		surface.pushMatrix();
		surface.translate((float)getX(), (float)getY(), (float)getZ());
		surface.shape(p);
		surface.popMatrix();
	}
	
	private PShape createShape(PApplet p) {
		PShape shape = p.createShape(PShape.BOX, (float)getWidth(), (float)getHeight(), (float)getDepth());
		shape.setTexture(getImage());
		return shape;
	}

}
