package weapons;

import processing.core.*;

/**
 * Represents a weapon which can be used by the user in the maze
 * 
 * @author Ohm Bhakta
 * @version 05262024
 */
public abstract class Weapon {
	
	private int damage;
	private double w, h, d;
	private double x, y, z;
	private PImage img;
	
	private double xOffset,  yOffset,  zOffset;
	private double pan, tilt;

	/**
	 * Sets the location, dimensions, and the amount of damage the weapon can do
	 * 
	 * @param damage The damage of the weapon
	 * @param w The width of the weapon
	 * @param h The height of the weapon
	 * @param d The depth of the weapon
	 * @param xOffset the x-distance between the player and the Weapon
	 * @param yOffset the y-distance between the player and the Weapon
	 * @param zOffset the z-distance between the player and the Weapon
	 * @param p the PApplet in which the Blaster is drawn
	 */
	public Weapon(int damage, double w, double h, double d, double xOffset, double yOffset, double zOffset, PImage p) {
		this.damage = damage;
		this.w = w;
		this.h = h;
		this.d = d;
		this.x = 0;
		this.y = 0;
		this.z = 0;
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		this.zOffset = zOffset;
		
		pan = 0;
		tilt = 0;
		img = p;
	}
	
	/**
	 * Draws the weapon to the game screen
	 * @param surface the place where the weapon is drawn to
	 */
	public abstract void draw(PApplet surface);
	
	public int getDamage() {
		return damage;
	}
	
	public double getWidth() {
		return w;
	}
	
	public double getHeight() {
		return h;
	}
	
	public double getDepth() {
		return d;
	}
	
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
		this.x =x;
	}
	
	public void setY(double y) {
		this.y = y;
	}
	
	public void setZ(double z) {
		this.z = z;
	}
	
	public double getXOffset() {
		return xOffset;
	}
	
	public double getYOffset() {
		return yOffset;
	}
	
	public double getZOffset() {
		return zOffset;
	}
	
	public double getPan() {
		return pan;
	}
	
	public double getTilt() {
		return tilt;
	}
	
	public void setPan(double pan) {
		this.pan = pan;
	}
	
	public void setTilt(double tilt) {
		this.tilt = tilt;
	}
	
	public PImage getImage() {
		return img;
	}
	
	

}
