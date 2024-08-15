package player;

import java.awt.MouseInfo;
import java.awt.Point;
import java.util.ArrayList;

import core.DrawingSurface;
import entities.Monster;
import maze.*;
import processing.core.*;
import weapons.*;

/**
 * This class represents the player, a type of camera that moves along the maze, collides with Walls.
 *
 * @author Dhruv Solanki, Ohm Bhakta, Yujun Lee
 * @version 05262024
 */
public class Player extends Camera {
	private float w, h, d;
	public static int health;
	private Weapon weapon;
	public static int coins;
	private Maze maze;
	
	private long lastAttackTime;
	public static final long SWORD_ATTACK_COOLDOWN = 700;
	public static final long BLASTER_ATTACK_COOLDOWN = 1400;
	
	private long lastHealTime;
	public static final long HEAL_COOLDOWN = 5000;

	/**
	 * Creates a new instance of a player.
	 */
	public Player() {
		// speed is at .12f max
		this(1, 3, 1, .1f, .5f, .5f, .75f, PConstants.PI / 3f, 120f, null, null);
		health = 100;
	}
	
	/**
	 * Creates a new instance of a player
	 * 
	 * @param maze the maze the Player interacts
	 */
	public Player(Maze maze) {
		this();
		this.maze = maze;
	}

	/**
	 * Creates a new instance of a player 
	 * 
	 * @param w Width of the player
	 * @param h Height of the player
	 * @param d Depth of the player
	 * @param speed How fast the player moves
	 * @param xSensitivity Mouse sensitivity on the x-axis
	 * @param ySensitivity Mouse sensitivity on the y-axis
	 * @param friction The amount of friction the player experiences while moving
	 * @param fov The player's field of view
	 * @param viewDistance How far the player can look in the distance
	 * @param weapon the weapon the player holds
	 * @param maze the Maze the player interacts
	 */
	public Player(float w, float h, float d, float speed, float xSensitivity, float ySensitivity, float friction,
			float fov, float viewDistance, Weapon weapon, Maze maze) {
		super(speed, xSensitivity, ySensitivity, friction, fov, viewDistance);
		this.w = w;
		this.h = h;
		this.d = d;
		health = 100;
		this.weapon = weapon;
	}
	
	/**
	 * Gives a weapon to the Player
	 * 
	 * @param weap true if the weapon is a Blaster, false if the weapon is a Sword
	 * @param g the PApplet in which the Player interacts
	 */
	public void givePlayerWeapon(boolean weap, PApplet g) {
		if(weap)
			weapon = new Blaster(80, 2.5, -0.2, -1.5, g);
		else 
			weapon = new Sword(40,  2.5,  -0.2, 0, g);
		
	}
	
	/**
	 * Represents the Player's interaction with the maze
	 *
	 * @param maze the walls that are part of the maze 
	 */
	public void act(ArrayList<Wall> maze) {
		
	}
	
	public Weapon getWeapon() {
		return weapon;
	}
	
	/**
	 * Decreases the health of the player
	 * @param h the amount the player's health to decrease
	 */
	public void decreaseHealth(int h) {
		health -= h;
	}
	
	public int getHealth() {
		return health;
	}
	
	public long getLastAttackTime() {
		return lastAttackTime;
	}
	
	public void setLastAttackTime(long t) {
		lastAttackTime = t;
	}
	
	/**
	 * Draws the Player
	 * 
	 * @param g the PApplet in which the Player interacts
	 */
	public void draw(PApplet g) {
		
		int top = g.displayHeight/4;
		int left = g.displayWidth/4;
		int windowRight = 3*g.displayWidth/4;
		int bottom = 3*g.displayHeight/4;

		mouse = MouseInfo.getPointerInfo().getLocation();

		if (pMouse == null)
			pMouse = new Point(mouse.x, mouse.y);

		// means that the mouse went off the screen to the left so move it to the right
		if (mouse.x < left + 2 && (mouse.x - pMouse.x) < 0) {
			robot.mouseMove(windowRight - 2, mouse.y);
			mouse.x = windowRight - 2;
			pMouse.x = windowRight - 2;
		}

		// means that the mouse went off the screen to the right so move it to the left
		if (mouse.x > windowRight - 2 && (mouse.x - pMouse.x) > 0) {
			robot.mouseMove(left + 2, mouse.y);
			mouse.x = left + 2;
			pMouse.x = left + 2;
		}

		// means that the mouse went up off the screen so move it to the bottom
		if (mouse.y < top + 10 && (mouse.y - pMouse.y) < 0) {
			robot.mouseMove(mouse.x, bottom - 5);
			mouse.y = bottom - 5;
			pMouse.y = bottom - 5;
		}

		// means that the mouse went down off the screen so move it to the top
		if (mouse.y > bottom - 5 && (mouse.y - pMouse.y) > 0) {
			robot.mouseMove(mouse.x, top + 10);
			mouse.y = top + 10;
			pMouse.y = top + 10;
		}

		// map the mouse value to the corresponding angle between 0 and 2PI for x
		// rotation(pan) because you have 360º rotation
		pan += PApplet.map(mouse.x - pMouse.x, 0, g.width, 0, PConstants.TWO_PI) * xSensitivity;
		tilt += PApplet.map(mouse.y - pMouse.y, 0, g.height, 0, PConstants.PI) * ySensitivity;
		tilt = clamp(tilt, -PConstants.PI / 2.01f, PConstants.PI / 2.01f);

		// tan of pi/2 or -pi/2 is undefined so if it happens to be exactly that
		// increase it so the code works
		if (tilt == PConstants.PI / 2)
			tilt += 0.001f;
		if (tilt == -PConstants.PI / 2)
			tilt -= 0.001f;

		//Vector representing what forward is relative to the camera right now
		forward = new PVector(PApplet.cos(pan), PApplet.tan(tilt), PApplet.sin(pan));

		// make it a unit vector because the direction is all that matters
		forward.normalize();

		// subtract pi/2 from pan to get the vector perpendicular to forward to show
		// which way is right
		right = new PVector(PApplet.cos(pan - PConstants.PI / 2), 0, PApplet.sin(pan - PConstants.PI / 2));

		//have the previous mouse set to the current mouse to use it for the next call to draw()
		pMouse = new Point(mouse.x, mouse.y);

		// account for friction
		velocity.mult(friction);
		// use velocity to find out location of new position
		

		if(maze != null) {
			int[] coords = maze.pointToGrid(position.x + velocity.x, position.y + velocity.y, position.z + velocity.z);
			if(!maze.checkWall(coords[0], coords[1], coords[2])) {
				position.add(velocity);
			} 
		}
		
		if(weapon != null) {
			PVector up = getRight().cross(getForward());
			weapon.setX(getPosition().x + weapon.getXOffset() * getForward().x + weapon.getZOffset() * getRight().x + weapon.getYOffset() * up.x);
			weapon.setY(getPosition().y + weapon.getXOffset()* getForward().y + weapon.getZOffset() * getRight().y + weapon.getYOffset() * up.y);
			weapon.setZ(getPosition().z + weapon.getXOffset() * getForward().z + weapon.getZOffset() * getRight().z + weapon.getYOffset() * up.z);
			
			if(weapon instanceof Blaster) {
				weapon.setTilt(tilt);
				weapon.setPan(pan);
			} else if(weapon instanceof Sword) {
				weapon.setPan(pan);
			}
		}
	
		
		center = PVector.add(position, forward);
		g.camera(position.x, position.y, position.z, center.x, center.y, center.z, 0, 1, 0);
		
		
		if(weapon instanceof Blaster) {
			Blaster b = (Blaster)weapon;
			b.draw(g);
		} else if(weapon instanceof Sword) {
			Sword s = (Sword)weapon;
			s.draw(g);
		}
		
	}
	
	@Override
	public void moveY(boolean isUp) {
		if(isUp == true) {
			if(maze != null) {
				int[] coords = maze.pointToGrid(position.x, position.y - 1.5f * getSpeed(), position.z);
				if(!maze.checkWall(coords[0], coords[1], coords[2])) {
					super.moveY(isUp);
				}
			}
		} else if(isUp == false){
			if(maze != null) {
				int[] coords = maze.pointToGrid(position.x, position.y + 1.5f * getSpeed(), position.z);
				if(!maze.checkWall(coords[0], coords[1], coords[2])) {
					super.moveY(isUp);
				}
			}
		}
	}

	// "Clamp" the x value to within the range of min-max
	private float clamp(float x, float min, float max) {
		if (x > max)
			return max;
		if (x < min)
			return min;
		return x;
	}
	
	/**
	 * Represents the natural healing process of the Player
	 * 
	 * @param surface the PApplet in which the Player ineracts
	 */
	public void heal(DrawingSurface surface) {
		long currentTime = surface.millis();
		if(currentTime - lastHealTime >= HEAL_COOLDOWN) {
			if(surface.getCanUpgradeHealth() && health < 150) {
				health += 2;
				if (health > 150)
					health = 150;
				lastHealTime = surface.millis();
			} else if(health < 100){
				health += 2;
				if(health > 100)
					health = 100;
				lastHealTime = surface.millis();
			}
		}
	}
	
	/**
	 * Returns the width 
	 * @return width
	 */
	public float getWidth() {
		return w;
	}

	/**
	 * Returns the height 
	 * @return height
	 */
	public float getHeight() {
		return h;
	}

	/**
	 * Returns the depth 
	 * @return depth
	 */
	public float getDepth() {
		return d;
	}
	
	/**
	 * Sets the position of the player to the given coordinates
	 * 
	 * @param x x-coordinate of where to move the player
	 * @param y y-coordinate of where to move the player
	 * @param z z-coordinate of where to move the player
	 */
	public void moveTo(float x, float y, float z) {
		this.getPosition().x = x;
		this.getPosition().y = y;
		this.getPosition().z = z;
	}
	
	/**
	 * Attacks the monster
	 * 
	 * @param p the PApplet in which the Player is drawn
	 * @param monsters the list of all monsters 
	 */
	public void attack(PApplet p, ArrayList<Monster> monsters) {
		if (weapon != null) {
			for (Monster monster : monsters) {
				if (weapon instanceof Sword) {
					long currentTime = p.millis();
					if(currentTime - lastAttackTime >= SWORD_ATTACK_COOLDOWN) {
						Sword sword = (Sword) weapon;
						
						if (sword.isTouching(monster)) {
							monster.decreaseHealth(weapon.getDamage());
							lastAttackTime = p.millis();
						}
					}
				} else if (weapon instanceof Blaster) {
					Blaster blaster = (Blaster) weapon;
					ArrayList<Ammo> a = blaster.getAmmos();
					for(int i = 0; i < a.size(); i++) {
						if(a.get(i).isTouching(monster)) {
							monster.decreaseHealth(weapon.getDamage());
							blaster.removeAmmo(i);
							i--;
						} else if(a.get(i).isTouching(maze)) {
							blaster.removeAmmo(i);
							i--;
						}
						
					}
					
				}
			}
		}
	}
	
	
	
	
}
