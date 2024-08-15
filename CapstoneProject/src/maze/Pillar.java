package maze;

import processing.core.PApplet;

/**
 * A Pillar is a wall that connects two fixed walls.
 * 
 * @author Yujun Lee
 * @version 05262024
 */
public class Pillar extends Wall{
	
	private int fillColor;
	
	/**
	 * Creates a new instance of a Pillar
	 * 
	 * @param x the x-coordinate of the Pillar
	 * @param y the y-coordinate of the Pillar
	 * @param z the z-coordinate of the Pillar
	 * @param orientation the orientation of the Pillar; 1 is perpendicular to x, 2 is perpendicular to y, 3 is perpendicular to z
	 * @param sideLength the length of the Pillar
	 */
	public Pillar(float x, float y, float z, int orientation, float sideLength) {
		super(x, y, z, orientation, sideLength);
		fillColor = 100;
	}
	
	@Override
	public void display(PApplet g) {
		g.pushMatrix();
		g.translate(x, y, z);
		g.stroke(0);
		g.fill(fillColor);
		
		if (orientation == 1) {
			g.box(sideLength, sideLength/10, sideLength/10);
			
		} else if (orientation == 2) {
			g.box(sideLength/10, sideLength, sideLength/10);
			
		} else if (orientation == 3) {
			g.box(sideLength/10, sideLength/10, sideLength);
			
		}
		
		g.popMatrix();
	}
}
