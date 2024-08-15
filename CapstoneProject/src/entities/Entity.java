package entities;

import processing.core.PApplet;
import processing.core.PImage;

/**
 * Representation of entities that can be found throughout the maze
 * 
 * @author Dhruv Solanki
 * @version 05262024
 */
public abstract class Entity {
	
	/**
	 * The x-coordinate location of the entity on the 3-D plane
	 */
	private double x;
	
	/**
	 * The y-coordinate location of the entity on the 3-D plane
	 */
	private double y;
	
	/**
	 * The z-coordinate location of the entity on the 3-D plane
	 */
	private double z;
	
	private PImage image;
		
	/**
	 * Creates a new instance of an entity
	 * 
	 * @param x the x-coordinate of the entity
	 * @param y the y-coordinate of the entity
	 * @param z the z-coordinate of the entity
	 * @param image the image for the entity
	 */
	public Entity(double x, double y, double z, PImage image) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.image = image;
	}
	
	/**
	 * Draws the entity
	 * 
	 * @param p the PApplet in which the entity will be drawn
	 */
	public abstract void display(PApplet p);
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public double getZ() {
		return z;
	}
	
	public void setX(double x) {
		this.x = x;
	}
	
	public void setY(double y) {
		this.y = y;
	}
	
	public void setZ(double z) {
		this.z = z;
	}
	
	public PImage getImage() {
		return image;
	}
	
}
