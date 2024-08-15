package maze;

import processing.core.*;

/**
 * Representation of walls that is the building components of the maze
 * 
 * @author Yujun Lee
 * @version 05262024
 */
public abstract class Wall {
	
	protected float x, y, z, sideLength;
	protected int orientation; //1 faces x, 2 faces y, 3 faces z
	
	/**
	 * Creates a new instance of a wall. However, this constructor is not directly called by the client classes.
	 * @param x the x-coordinate of the wall
	 * @param y the y-coordinate of the wall
	 * @param z the z-coordinate of the wall
	 * @param orientation the orientation of the wall; 1 faces x, 2 faces y, 3 faces z
	 * @param sideLength the side length of the wider side of the wall
	 */
	public Wall(float x, float y, float z, int orientation, float sideLength) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.orientation = orientation;
		this.sideLength = sideLength;
	}
	
	/**
	 * Displays the current Wall
	 * @param g the PApplet in which the Wall will be drawn
	 */
	public void display(PApplet g) {};
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public float getZ() {
		return z;
	}
	
	public float getSideLength() {
		return sideLength;
	}
	
	public int getOrientation() {
		return orientation;
	}
}
