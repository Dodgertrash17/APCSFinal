package maze;

import processing.core.*;

/**
 * A FixedWall is a Wall that is fixed, thus players and entities cannot go through.
 * 
 * @author Yujun Lee
 * @version 05262024
 */
public class FixedWall extends Wall{
	
	private int[] fillColor;
	
	/**
	 * Creates a new instance of a FixedWall.
	 * @param x the x-coordinate of the wall
	 * @param y the y-coordinate of the wall
	 * @param z the z-coordinate of the wall
	 * @param orientation the orientation of the wall; 1 faces x, 2 faces y, 3 faces z
	 * @param sideLength the side length of the wider side of the wall
	 */
	public FixedWall(float x, float y, float z, int orientation, float sideLength) {
		super(x, y, z, orientation, sideLength);
		double dist = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));
		fillColor = assignColor(dist);
	}
	
	private int[] assignColor(double dist) {
		int[] rgb = new int[3];
		int floor = 150;
		if(dist < 17 * Math.sqrt(3)) {
			rgb[0] = 255;
			rgb[2] = floor;
			rgb[1] = floor + (int)((255-floor)*(dist/17/Math.sqrt(3)));
		} else if(dist < 34 * Math.sqrt(3)) {
			rgb[1] = 255;
			rgb[2] = floor;
			rgb[0] = 255 - (int)((255-floor)*((dist - 17*Math.sqrt(3))/17/Math.sqrt(3)));
		} else if(dist < 51 * Math.sqrt(3)) {
			rgb[1] = 255;
			rgb[0] = floor;
			rgb[2] = floor + (int)((255-floor)*((dist - 34*Math.sqrt(3))/17/Math.sqrt(3)));
		} else if(dist < 68 * Math.sqrt(3)) {
			rgb[2] = 255;
			rgb[0] = floor;
			rgb[1] = 255 - (int)((255-floor)*((dist - 51*Math.sqrt(3))/17/Math.sqrt(3)));
		} else if(dist < 85 * Math.sqrt(3)) {
			rgb[2] = 255;
			rgb[1] = floor;
			rgb[0] = floor + (int)((255-floor)*((dist - 68*Math.sqrt(3))/17/Math.sqrt(3)));
		} else {
			rgb[0] = 255;
			rgb[1] = floor;
			rgb[2] = 255 - (int)((255-floor)*((dist - 85*Math.sqrt(3))/17/Math.sqrt(3)));
		}
		return rgb;
	}
	
	@Override
	public void display(PApplet g) {
		g.pushMatrix();
		g.translate(x, y, z);
		
		g.stroke(0);
		
		g.fill(fillColor[0], fillColor[1], fillColor[2]);
		if (orientation == 1) {
			g.box(sideLength/10, sideLength, sideLength);
			
		} else if (orientation == 2) {
			g.box(sideLength, sideLength/10, sideLength);
			
		} else if (orientation == 3) {
			g.box(sideLength, sideLength, sideLength/10);
			
		}

		g.popMatrix();
	}
	
	
	
	
}
