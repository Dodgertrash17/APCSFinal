package weapons;


import entities.Monster;
import maze.Maze;
import processing.core.*;

/**
 * Representation of a Ammo that gets shot from a blaster. Drawn to the screen using a line
 * 
 * @author Ohm Bhakta, Yujun Lee
 * @version 05262024
 */
public class Ammo {

	private double x, y, z;
	private PVector v;
	
	/**
	 * Sets the initial location on the screen for the ammo
	 * 
	 * @param x The x coordinate location
	 * @param y The y coordinate location
	 * @param z The z coordinate location
	 * @param v The velocity of the ammo
	 */
	public Ammo(double x, double y, double z, PVector v) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.v = v;
	}
	
	/**
	 * Detects whether the ammo that was shot has any part of it intersecting with a monster
	 * @param dhruv The monster that can be found in the maze
	 * @return true if the ammo shot intersects with the monster, false if there is no intersection detected
	 */
	public boolean isTouching(Monster dhruv) {
		
		double monX = dhruv.getX();
		double monY = dhruv.getY();
		double monZ = dhruv.getZ();
		double monSide = dhruv.getWidth();
		double closeX;
		double closeY;
		double closeZ;
		
		if(x >= monX - monSide/2 && x <= monX + monSide/2) {
			closeX = x;
		} else if(x < monX - monSide/2) {
			closeX = monX - monSide/2;
		} else {
			closeX = monX + monSide/2;
		}
		
		if(y >= monY - monSide/2 && y <= monY + monSide/2) {
			closeY = y;
		} else if(y < monY - monSide/2) {
			closeY = monY - monSide/2;
		} else {
			closeY = monY + monSide/2;
		}
		
		if(z >= monZ - monSide/2 && z <= monZ + monSide/2) {
			closeZ = z;
		} else if(z < monZ - monSide/2) {
			closeZ = monZ - monSide/2;
		} else {
			closeZ = monZ + monSide/2;
		}
		
		if(Math.sqrt(Math.pow(closeX - x, 2) + Math.pow(closeY - y, 2) + Math.pow(closeZ - z, 2)) < 0.1) 
			return true;
		
		return false;
	}
	
	/**
	 * Detects whether the ammo that was shot has any part of it intersecting with a wall
	 * @param m The Maze of the game
	 * @return True if the ammo intersects with a wall, false if there is no intersection
	 */
	public boolean isTouching(Maze m) {

		int[] coords = m.pointToGrid((float)x, (float)y, (float)z);
		if(m.checkWall(coords[0], coords[1], coords[2])) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * Draws the Ammo
	 * 
	 * @param g the PApplet in which the Ammo is drawn.
	 */
	public void draw(PApplet g) {
		g.pushMatrix();
		g.translate((float)x, (float)y, (float)z);
		g.fill(255, 0, 0);
		g.sphere(0.1f);
		g.popMatrix();
	}
	
	/**
	 * Moves the ammo
	 */
	public void move() {
		x += v.x;
		y += v.y;
		z += v.z;
	}
	
	
	
}
