package core;

import processing.core.PApplet;

/**
 * Main is the class where the program is executed.
 * 
 * @author Ohm Bhakta
 * @version 05262024
 */
public class Main {

	public static void main(String[] args) {
		DrawingSurface drawing = new DrawingSurface();
		PApplet.runSketch(new String[]{""}, drawing);
	}

}
