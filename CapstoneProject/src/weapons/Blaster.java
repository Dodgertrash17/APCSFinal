package weapons;

import java.util.ArrayList;

import processing.core.*;

/**
 * Represents a blaster which shoots out ammo 
 * 
 * @author Ohm Bhakta, Yujun Lee
 * @version 05262024
 */
public class Blaster extends Weapon{

	private ArrayList<Ammo> a;
	
	private PShape p;

	/**
	 * Sets up the location of the blaster and what direction the blaster is pointed to
	 * 
	 * @param damage The amount of damage the blaster does
	 * @param xOffset the x-distance between the player and the Blaster
	 * @param yOffset the y-distance between the player and the Blaster
	 * @param zOffset the z-distance between the player and the Blaster
	 * @param g the PApplet in which the Blaster is drawn
	 */
	public Blaster(int damage, double xOffset, double yOffset, double zOffset, PApplet g) {
		super(damage, 1.5, 0.2, 0.2, xOffset, yOffset, zOffset, g.loadImage("img/banannablaster.png"));
		

		a = new ArrayList<Ammo>();
		this.p = createBlaster(g);
	}
	
	/**
	 * Shoots an ammo at the direction the blaster is currently pointed at
	 * 
	 * @param p the position of the Player
	 * @param f the direction Player is facing
	 */
	public void use(PVector p, PVector f) {
		PVector aim = new PVector(p.x + 15 * f.x, p.y + 15 * f.y, p.z + 15 * f.z);
		
		double dist = Math.sqrt(Math.pow(aim.x - getX(), 2)+ Math.pow(aim.y - getY(), 2) + Math.pow(aim.z - getX(), 2));
		
		PVector v = new PVector((float)(aim.x - getX())/(float)dist, (float)(aim.y - getY())/(float)dist, (float)(aim.z - getZ())/(float)dist);
		
		a.add(new Ammo(getX(), getY(), getZ(), v));
			
	}
	
	@Override
	public void draw(PApplet surface) {
		
		surface.pushMatrix();
		surface.translate((float)getX(), (float)getY(), (float)getZ());
		surface.rotateY(-(float)getPan());
		surface.rotateZ((float)getTilt());
		
		surface.shape(p);
		surface.popMatrix();
		
	}
	
	public ArrayList<Ammo> getAmmos() {
		if(a != null)
			return a;
		return null;
	}
	
	/**
	 * Removes the Ammo from the maze
	 * @param i the index of the ammo in the Blaster
	 */
	public void removeAmmo(int i) {
		a.remove(i);

	}
	
	private PShape createBlaster(PApplet g) {
		PShape blaster = g.createShape(PApplet.BOX, (float)getWidth(), (float)getHeight(), (float)getDepth());
		blaster.setTexture(getImage());
		return blaster;
	}
	

}
