package entities;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PShape;
import processing.core.PVector;

/**
 * Representation of an interactable box that can be found in the maze
 * 
 * @author Yujun Lee
 * @version 052622024
 */
public class Interactable extends Entity {
	
	private PShape interactableShape;
	
	public static final int STORE_INTERACTABLE = 1, FINISH_INTERACTABLE = 2;
	
	/**
	 * Creates a new instance of an Interactable, which is an entity that the user interacts with.
	 * 
	 * @param x the x-coordinate of the Interactable
	 * @param y the y-coordinate of the Interactable
	 * @param z the z-coordinate of the Interactable
	 * @param type the type of the Interactable; can be set using the static fields.
	 * @param p the PApplet in which the Interactable is located at
	 */
	public Interactable(double x, double y, double z, int type, PApplet p) {
		super(x, y, z, Interactable.loadInteractableImage(p, type));
		interactableShape = createNewShape(p);
	}
	
	/**
	 * Loads the corresponding image based on the type.
	 * 
	 * @param p the PApplet in which the Interactable is located at
	 * @param type the type of the Interactable
	 * @return the corresponding image given the type; null if the type does not exist
	 */
	public static PImage loadInteractableImage(PApplet p, int type) {
		if(type == STORE_INTERACTABLE) 
			return p.loadImage("img/store.png");
		else if(type == FINISH_INTERACTABLE)
			return p.loadImage("img/finish.png");
		return null;
	}
	
	@Override
	public void display(PApplet p) {
		p.pushMatrix();
		p.translate((float)getX(), (float)getY(), (float)getZ());
		p.shape(interactableShape);
		p.popMatrix();
	}
	
	/**
	 * Checks if a given vector is inside the Interactable.
	 * 
	 * @param pPos The PVector to be checked
	 * @return true if the given PVector is inside the Interactable
	 */
	public boolean isVectorInside(PVector pPos) {
		if(pPos.x > getX() - 2.5 && pPos.x < getX() + 2.5 && pPos.y > getY() - 2.5 && pPos.y < getY() + 2.5 && pPos.z > getZ() - 2.5 && pPos.z < getZ() + 2.5)
			return true;
		return false;
	}
	
	private PShape createNewShape(PApplet p) {
		PShape shape = p.createShape(PShape.BOX, 5, 5, 5);
		shape.setTexture(getImage());
		return shape;
	}
	
}
