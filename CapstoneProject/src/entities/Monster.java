package entities;

import player.Player;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PShape;
import processing.core.PVector;


/**
 * Representation of a monster that can be found in the maze
 * 
 * @author Dhruv Solanki
 * @version 05262024
 */
public class Monster extends Entity {
	
	private int health;
	private long lastAttackTime;
	private static final long ATTACK_COOLDOWN = 3000;
	
	private PShape monsterShape;
	private float width;
	private float height;
	private float depth; 
	private float speed;
	private int damage;
	

	/**
	 * Creates a new instance of a Monster
	 * 
	 * @param x the x-coordinate of the monster
	 * @param y the y-coordinate of the monster
	 * @param z the z-coordinate of the monster
	 * @param health the health of the monster
	 * @param width the width of the monster
	 * @param height the height of the monster
	 * @param depth the depth of the monster
	 * @param speed the speed of the monster
	 * @param damage the damage the monster deals
	 * @param monsterImage the image for the monster
	 * @param p the PApplet in which the monster is drawn
	 */
	public Monster(double x, double y, double z, int health, float width, float height, float depth, float speed, int damage, PImage monsterImage, PApplet p) {
		super(x, y, z, monsterImage);
		this.health = health;
		this.width = width;
		this.height = height;
		this.depth = depth;
		this.speed = speed;
		this.damage = damage;
		monsterShape = createMonsterShape(p);
		
	}
	
	/**
	 * Moves the monster
	 * 
	 * @param player the player in which the monster interacts with
	 */
	public void move(Player player) {
		float[] nextPosition = calculateNextPosition(player);
		setX(nextPosition[0]);
		setY(nextPosition[1]);
		setZ(nextPosition[2]);
	}
	
	/**
	 * Calculates the next position the monster will move to
	 * 
	 * @param player the Player of the game
	 * @return the float array containing the x, y, and z coordinates, in order
	 */
	public float[] calculateNextPosition(Player player) {
		PVector playerPosition = player.getPosition();
		double diffX = playerPosition.x - getX();
		double diffY = playerPosition.y - getY();
		double diffZ = playerPosition.z - getZ();
		
		double magnitude = Math.sqrt(diffX * diffX + diffY * diffY + diffZ * diffZ);
		
		if(magnitude > 30)
			return new float[] {(float)getX(), (float)getY(), (float)getZ()};
		
		if (magnitude != 0) {
			diffX /= magnitude;
			diffY /= magnitude;
			diffZ /= magnitude;
		}
		float nextX = (float)(getX() + diffX * speed);
		float nextY = (float)(getY() + diffY * speed);
		float nextZ = (float)(getZ() + diffZ * speed);
		
		return new float[] {nextX, nextY, nextZ};
	}

	@Override
	public void display(PApplet p) {
		p.pushMatrix();
		p.translate((float)getX(),(float)getY(), (float)getZ());
		p.shape(monsterShape);
		p.popMatrix();
	}

	private PShape createMonsterShape(PApplet p) {
		PShape shape = p.createShape(PShape.BOX, width, height, depth);
		shape.setTexture(getImage());
		return shape;
	}
	
	/**
	 * Checks if the player is intersecting the Monster
	 * 
	 * @param p the player of the game
	 * @return true if the player intersects the monster
	 */
	public boolean isPlayerIntersecting(Player p) {
		if(p.getPosition().x > getX() - 2.5 && p.getPosition().x < getX() + 2.5 && p.getPosition().y > getY() - 2.5 && p.getPosition().y < getY() + 2.5 && p.getPosition().z > getZ() - 2.5 && p.getPosition().z < getZ() + 2.5)
			return true;
		return false;
	}
	
	
	/**
	 * Attacks the player
	 * 
	 * @param p the PApplet in which the monster is located in
	 * @param player the player the monster will attack
	 */
	public void attack(PApplet p, Player player) {
		long currentTime = p.millis();
		
		if (currentTime - lastAttackTime >= ATTACK_COOLDOWN) {

			player.decreaseHealth(damage);
			
			lastAttackTime = p.millis();
		}
	}
	
	/**
	 * Decreases the monster's health
	 * 
	 * @param h the amount the monster loses health 
	 */
	public void decreaseHealth(int h) {
		health -= h;
	}
	
	public PShape getMonsterShape() {
		return monsterShape;
	}
	
	public float getWidth() {
		return width;
	}
	
	public float getHeight() {
		return height;
	}
	
	public float getDepth() {
		return depth;
	}
	
	public int getHealth() {
		return health;
	}
	
}
