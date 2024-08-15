package maze;

import java.util.ArrayList;

import entities.*;
import player.Player;
import processing.core.PApplet;
import processing.core.PImage;

/**
 * A Maze represents a three dimensional maze of given dimensions. It consists of Walls which are its building components and Entities throughout the Maze.
 * @author Dhruv Solanki, Ohm Bhakta, Yujun Lee
 * @version 05262024
 */
public class Maze {

	private boolean[][][] path;
	private boolean[][][] checkGrid;
	private float startX, startY, startZ;
	private ArrayList<Wall> maze;
	private ArrayList<Coin> coins;
	private ArrayList<Monster> monsters;
	private ArrayList<Interactable> storeMarks;
	private Interactable endMark;
	private int dimension;
	
	/**
	 * Creates a new instance of a 3D maze of given dimensions.
	 * @param dim the dimension of one edge of the maze
	 */
	public Maze(int dim) {
		path = new boolean[2*dim+1][2*dim+1][2*dim+1];
		checkGrid = new boolean[2*dim+1][2*dim+1][2*dim+1];
		startX = 5;
		startY = 5;
		startZ = 5;
		maze = new ArrayList<Wall>();
		coins = new ArrayList<Coin>();
		monsters = new ArrayList<Monster>();
		storeMarks = new ArrayList<Interactable>();
		dimension = dim;
		createPath();
		fillMaze();
	}
	
	/**
	 * Displays the Maze
	 * @param g the PApplet in which the maze will be drawn in.
	 */
	public void display(PApplet g) {
		
		for (int i = 0; i < maze.size(); i++) {
			maze.get(i).display(g);
		}	
		
		drawEntities(g);
	}
	
	/**
	 * Checks the collection of coins by the player
	 * 
	 * @param p the Player that interacts in the maze
	 */
	public void checkCoinCollect(Player p) {
		if(coins.size() != 0) {
			for(int c = 0; c < coins.size(); c++) {
				if(coins.get(c).isTouching(p)) {
					coins.remove(c);
					c--;
					Player.coins++;
					break;
				}
			}
		}
	}

	/**
	 * Removes monsters
	 * 
	 * @return the number of monsters to be removed
	 */
	public int removeMonsters() {
		int remCount = 0;
		for (int i = 0; i < monsters.size(); i++) {
			if (monsters.get(i).getHealth() <= 0) {
				monsters.remove(i);
				remCount ++;
				i--;
			}
		}
		return remCount;
	}
	
	/**
	 * Checks if the player is interacting with an interactable
	 * 
	 * @param p the player that interacts in the maze
	 * @return 1 if the player is interacting with a store, 2 if the player is interacting with a finish, -1 if not
	 */
	public int checkInteractableInteraction(Player p) {
		if(storeMarks.size() != 0) {
			for(int i = 0; i < storeMarks.size(); i++) {
				if(storeMarks.get(i).isVectorInside(p.getPosition()))
					return 1;
			}
		}
		
		if(endMark.isVectorInside(p.getPosition()))
			return 2;
		
		return -1;
	}

	/**
	 * Represents the monsters attacking the player
	 * 
	 * @param p the PApplet in which the maze is drawn
	 * @param player the Player that interacts in the maze
	 */
	public void attackPlayer(PApplet p, Player player) {
		for (Monster monster : monsters) {
			if(monster.isPlayerIntersecting(player)) {
				monster.attack(p, player);
			}
		}
	}
	
	/**
	 * Moves the monsters based on the player's position
	 * 
	 * @param player the Player that interacts in the maze
	 */
	public void moveMonsters(Player player) {
		for (Monster monster : monsters) {
			float[] nextPosition = monster.calculateNextPosition(player);
			float nextX = nextPosition[0];
			float nextY = nextPosition[1];
			float nextZ = nextPosition[2];
			        
			int[] coords = this.pointToGrid(nextX, nextY, nextZ);
			
			if (!this.checkWall(coords[0], coords[1], coords[2])) {
				monster.move(player);
			}
		}
	}
	
	/**
	 * Adds entities to the maze
	 * 
	 * @param p the PApplet in which the entities are to be drawn
	 */
	public void addEntities(PApplet p) {
		PImage coinImage = p.loadImage("img/coin.png");	
		PImage monsterImage1 = p.loadImage("img/starringdhruv.png");
		PImage monsterImage2 = p.loadImage("img/sworddhruv.png");
		
		for (int i = 1; i < path.length; i += 2) {
			for (int j = 1; j < path[0].length; j += 2) {
				for (int k = 1; k < path[0][0].length; k += 2) {
					if(!(i == 1 && j == 1 && k == 1) && !(i == 15 && j == 15 && k == 15)) {
						float[] point = gridToPoint(i, j, k);
						double random = Math.random();
						if (random < 0.35) {

							coins.add(new Coin(point[0], point[1], point[2], coinImage, p));
							
						} else if (random < 0.55) {
							
							if(Math.random() < 0.7)
								monsters.add(new Monster(point[0], point[1], point[2], 75, 1.5f, 1.5f, 1.5f, 0.1f, 3, monsterImage1, p));
							else {
								monsters.add(new Monster(point[0], point[1], point[2], 100, 1.9f, 1.9f, 1.9f, 0.08f, 5, monsterImage2, p));
							}
							
						} else if(random < 0.55 + 1d/16) {
							storeMarks.add(new Interactable(point[0], point[1], point[2], Interactable.STORE_INTERACTABLE, p));
						}
						
					}
				}
			}
		}
		
		float[] end = gridToPoint(15, 15, 15);
		endMark = new Interactable(end[0], end[1], end[2], Interactable.FINISH_INTERACTABLE, p);
	}
	
	/**
	 * Draws the entities
	 * 
	 * @param p the PApplet in which the entities are to be drawn
	 */
	public void drawEntities(PApplet p) {
		for (Coin coin : coins) {
			coin.display(p);
		}
		
		for (Monster monster : monsters) {
			monster.display(p);
		}
		
		for (Interactable storeMark : storeMarks) {
			storeMark.display(p);
		}
		
		endMark.display(p);
	}

	public void update(Player p) {
		p.act(maze);
	}
	
	/**
	 * Places the player at the given starting location.
	 * @param player the Player of the maze
	 */
	public void setPlayerAtStart(Player player) {
		player.moveTo(startX, startY, startZ);
	}
	
	public ArrayList<Wall> getWalls(){
		return maze;
	}
	
	/**
	 * Checks if the given grid is a wall
	 * 
	 * @param x the x-coordinate in the grid
	 * @param y the y-coordinate in the grid
	 * @param z the z-coordinate in the grid
	 * @return true if the location is a wall
	 */
	public boolean checkWall(int x, int y, int z) {
		if(x >= 0 && x < path.length && y >= 0 && y < path.length && z >= 0 && z < path.length)
			return path[x][y][z];
		return false;
	}
	
	/**
	 * Converts the given grid location to PApplet coordinate points
	 * 
	 * @param x the x-coordinate in the grid
	 * @param y the y-coordinate in the grid
	 * @param z the z-coordinate in the grid
	 * @return the size 3 float grid containing the x, y, and z coordinate points
	 */
	public float[] gridToPoint(int x, int y, int z) {
		float[] result = new float[3];
		
		if(x % 2 == 1)
			result[0] = x/2 * 11 + 5;
		else
			result[0] = x/2 - 0.5f;
		
		if(y % 2 == 1)
			result[1] = y/2 * 11 + 5;
		else
			result[1] = y/2 - 0.5f;
		
		if(z % 2 == 1)
			result[2] = z/2 * 11 + 5;
		else
			result[2] = z/2 - 0.5f;
		
		return result;
	}
	
	/**
	 * Converts the given PApplet coordinate points to grid location
	 * 
	 * @param x the x-coordinate in PApplet
	 * @param y the y-coordinate in PApplet
	 * @param z the z-coordinate in PApplet
	 * @return the size 3 integer grid containing the x, y, and z grid locations
	 */
	public int[] pointToGrid(float x, float y, float z) {
		int[] result = new int[3];
		
		int tempX = (int)x;
		if(x < 0)
			tempX = (int)(x-1);
		if(tempX % 11 == 10)
			result[0] = 2 * (tempX/11) +2;
		else if (tempX == -1)
			result[0] = 0;
		else
			result[0] = 2 * (tempX/11) +1;
		
		int tempY = (int)y;
		if(y < 0)
			tempY = (int)(y-1);
		if(tempY % 11 == 10) 
			result[1] = 2 * (tempY/11) +2;
		else if (tempY == -1)
			result[1] = 0;
		else
			result[1] = 2 * (tempY/11) +1;
		
		int tempZ = (int)z;
		if(z < 0)
			tempZ = (int)(z-1);
		if(tempZ % 11 == 10 || tempZ % 11 == -1) 
			result[2] = 2 * (tempZ/11) +2;
		else if (tempZ == -1)
			result[2] = 0;
		else
			result[2] = 2 * (tempZ/11) +1;
		
		return result;
	}
	
	
	private void fillMaze() {
		for(int i = 0; i < path.length; i++) {
			for(int j = 0; j < path[0].length; j++) {
				for(int k = 0; k < path[0][0].length; k++) {
					if(path[i][j][k] == true) {
						if(i%2==0 && j%2==1 && k%2 == 1) {
							maze.add(new FixedWall(11*i/2-0.5f, 11*j/2, 11*k/2, 1, 10));
						} else if(i%2==1 && j%2==0 && k%2 == 1) {
							maze.add(new FixedWall(11*i/2, 11*j/2-0.5f, 11*k/2, 2, 10));
						} else if(i%2==1 && j%2==1 && k%2 == 0) {
							maze.add(new FixedWall(11*i/2, 11*j/2, 11*k/2-0.5f, 3, 10));
						} else if(i%2==1 && j%2==0 && k%2 == 0){
							maze.add(new Pillar(11*i/2, 11*j/2 - 0.5f, 11*k/2 - 0.5f, 1, 10));
						} else if(i%2==0 && j%2==1 && k%2 == 0) {
							maze.add(new Pillar(11*i/2 - 0.5f, 11*j/2, 11*k/2 - 0.5f, 2, 10));
						} else if(i%2==0 && j%2==0 && k%2 == 1) {
							maze.add(new Pillar(11*i/2 - 0.5f, 11*j/2 - 0.5f, 11*k/2, 3, 10));
						}
//						maze.get(maze.size()-1).
					}
				}
			}
		}
	}
	
	private void createPath() {
		do {
			clearCheck();
			clearPath();
			fillPath();
		}while(!checkValidPath(1,1,1));
	}
	
	private boolean checkValidPath(int x, int y, int z) {
		if(x >= 0 && x < path.length && y >= 0 && y < path[0].length && z >= 0 && z < path[0][0].length) {
			if(checkGrid[x][y][z] == false && path[x][y][z] == false) {
				checkGrid[x][y][z] = true;
				
				if(checkValidPath(x +1, y, z)) {
					return true;
				}
				if(checkValidPath(x - 1, y, z)) {
					return true;
				}
				if(checkValidPath(x, y + 1, z)) {
					return true;
				}
				if(checkValidPath(x, y - 1, z)) {
					return true;
				}
				if(checkValidPath(x, y, z + 1)) {
					return true;
				}
				if(checkValidPath(x, y, z - 1)) {
					return true;
				}
						
			} else if(x == path.length - 2 && y == path[0].length - 2 && z == path[0][0].length - 2) {
				return true;
			}
		}
		
		return false;
	}
	
	private void fillPath() {
		for(int i = 0; i < path.length; i ++) {
			for(int j = 0; j < path[0].length; j ++) {
				for(int k = 0; k < path[0][0].length; k ++) {
					
					if(i == 0 || i == path.length - 1 || j == 0 || j == path[0].length - 1 || k == 0 || k == path[0][0].length - 1) {
						
						path[i][j][k] = true;
						
					} else if((i%2==0 && j%2==0 && k%2 == 1)||(i%2==0 && j%2==1 && k%2 == 0)||(i%2==1 && j%2==0 && k%2 == 0)) {
						
						path[i][j][k] = true;
						
					} else if((i%2==0 && j%2==1 && k%2 == 1)||(i%2==1 && j%2==0 && k%2 == 1)||(i%2==1 && j%2==1 && k%2 == 0)) {
						
						if(Math.random() < 0.6) {
							path[i][j][k] = true;
						}
						
					}
				}
			}
		}
	}
	
	private void clearCheck() {
		for(int i = 0; i < checkGrid.length; i ++) {
			for(int j = 0; j < checkGrid[0].length; j ++) {
				for(int k = 0; k < checkGrid[0][0].length; k ++) {
					checkGrid[i][j][k] = false;
				}
			}
		}
	}
	
	private void clearPath() {
		for(int i = 0; i < path.length; i ++) {
			for(int j = 0; j < path[0].length; j ++) {
				for(int k = 0; k < path[0][0].length; k ++) {
					path[i][j][k] = false;
				}
			}
		}
	}
	
	public ArrayList<Monster> getMonsters(){
		return this.monsters;
	}
	
}
