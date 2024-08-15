package player;

import java.awt.*;

import processing.core.*;
import com.jogamp.newt.opengl.GLWindow;

/**
 * This class represents the camera on the screen that you can use to move/look around
 *
 * @author Dhruv Solanki
 * @version 05262024
 */
public class Camera {
	protected Robot robot;
	protected PVector center, right, forward, position, velocity;
	protected float speed, xSensitivity, ySensitivity, pan, tilt, friction, fov, viewDistance;
	protected Point mouse, pMouse;

	/**
	 * Creates a new instance of a camera
	 */
	public Camera() {
		this(3, 1, 1, .75f, PConstants.PI / 3f, 1000f);
	}

	/**
	 * Creates a new instance of a camera 
	 * @param speed The speed of teh camera
	 * @param xSensitivity Mouse sensitivity on the x-axis
	 * @param ySensitivity Mouse sensitivity on the y-axis
	 * @param friction The amount of friction the player experiences while moving
	 * @param fov The player's field of view
	 * @param viewDistance How far the player can look in the distance
	 */
	public Camera(float speed, float xSensitivity, float ySensitivity, float friction, float fov, float viewDistance) {
		try {
			robot = new Robot();
		} catch (AWTException e) {
		}

		this.speed = speed;
		this.xSensitivity = xSensitivity;
		this.ySensitivity = ySensitivity;
		this.friction = friction;
		this.fov = fov;
		this.viewDistance = viewDistance;

		position = new PVector(0f, 0f, 0f);
		right = new PVector(1f, 0f, 0f);
		forward = new PVector(0f, 0f, 1f);
		velocity = new PVector(0f, 0f, 0f);

		pan = 0;
		tilt = 0;
	}

	/**
	 * Sets up the camera when it is created
	 * @param g The PApplet in which the camera is located at
	 */
	public void setup(PApplet g) {
		g.perspective(fov, (float) g.width / (float) g.height, 0.01f, viewDistance);
		// Moves the mouse to the center of the screen at the start of the game
		//robot.mouseMove(g.displayWidth/2, g.displayHeight/2);
	}

	public PVector getForward() {
		return forward;
	}

	public PVector getPosition() {
		return position;
	}

	public PVector getVelocity() {
		return velocity;
	}

	public PVector getCenter() {
		return center;
	}

	public PVector getRight() {
		return right;
	}

	public float getPan() {
		return pan;
	}

	public float getTilt() {
		return tilt;
	}

	/**
	 * moves the camera in the x direction
	 * @param dir the direction of the camera
	 */
	public void moveX(float dir) {
		velocity.add(PVector.mult(right, speed * dir));
	}

	/**
	 * moves the camera in the z direction
	 * @param dir the direction of the camera
	 */
	public void moveZ(float dir) {
		velocity.add(PVector.mult(forward, speed * dir));
	}
	
	/**
	 * moves the camera in the y direction
	 * @param isUp true if the camera moves up false if the camera moves down
	 */
	public void moveY(boolean isUp) {
		if(isUp == true) {
			getPosition().y -= 1.2*getSpeed();
		} else if(isUp == false){
			getPosition().y += 1.2*getSpeed();
		}
		
	}

	public float getXSensitivity() {
		return xSensitivity;
	}

	public float getYSensitivity() {
		return ySensitivity;
	}

	public void setXSensitivity(float f) {
		xSensitivity = f;
	}

	public void setYSensitivity(float f) {
		ySensitivity = f;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float f) {
		speed = f;
	}

	public float getFriction() {
		return friction;
	}

	public void setFriction(float f) {
		friction = f;
	}

	public float getFOV() {
		return fov;
	}

	public void setFOV(float f) {
		fov = f;
	}

	public float getViewDistance() {
		return viewDistance;
	}

	public void setViewDistance(float f) {
		viewDistance = f;
	}

	public Point getMouse() {
		return mouse;
	}

	public void setMouse(Point mouse) {
		robot.mouseMove(mouse.x, mouse.y);
	}

	public void setMouse(int x, int y) {
		robot.mouseMove(x, y);
	}

	public void setPan(double angle) {
		pan = (float) angle;
	}
}

