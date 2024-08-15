package screens;


import com.jogamp.newt.event.KeyEvent;

import core.DrawingSurface;

import maze.*;
import player.Player;
import weapons.*;

/**
 * Represents the screen of the game. It is a 3D screen centered at the Player.
 * 
 * @author Dhruv Solanki, Ohm Bhakta, Yujun Lee
 * @version 05262024
 */
public class GameScreen extends Screen {

	private DrawingSurface surface;
	private Player player;
	private Maze maze;
	private long pauseTime;
	public static long subTime;
	private static int speedVar;
	private boolean canAccessStore, isTouchingFinish;
	private int knockCount;
	
		
	/**
	 * Creates a new instance of a GameScreen
	 * 
	 * @param surface the DrawinSurface in which the GameScreen is drawn to
	 */
	public GameScreen(DrawingSurface surface) {
		super(800, 600);
		this.surface = surface;
		maze = new Maze(8);
		player = new Player(maze);
		subTime = 0;
		canAccessStore = false;
		isTouchingFinish = false;
		knockCount = 0;
	}
	
	@Override
	public void setPerspective() {
		
		player.setup(surface);
		
	}
	
	public void setup() {
		maze.setPlayerAtStart(player);
		surface.fill(255);
		surface.strokeWeight(0);
		maze.addEntities(surface);
		
		surface.strokeWeight(1);	
	}
	
	@Override
	public void draw() {
		surface.background(126);
		surface.noCursor();
		surface.background(51);
		surface.stroke(0);
		
		player.setup(surface);
		player.draw(surface);
		player.heal(surface);
		
		maze.display(surface);
		maze.update(player);
		maze.moveMonsters(player);
		maze.checkCoinCollect(player);
		maze.attackPlayer(surface, player);
		incKnock(maze.removeMonsters());

		Weapon w = player.getWeapon();
		if(w != null) {
			player.attack(surface, maze.getMonsters());
			if(w instanceof Blaster) {
				Blaster b = (Blaster)w;
				for(Ammo a: b.getAmmos()) {
					a.move();
					a.draw(surface);
				}
			}
		}
		
		if(surface.getCanUpgradeBlaster() && !(player.getWeapon() instanceof Blaster))
			player.givePlayerWeapon(true, surface);
		else if(surface.getCanUpgradeSword() && !(player.getWeapon() instanceof Sword)) {
			player.givePlayerWeapon(false, surface);
		}
		
		int interact = maze.checkInteractableInteraction(player);
		if(interact == 1) 
			canAccessStore = true;
		else
			canAccessStore = false;
		if(interact == 2) {
			isTouchingFinish = true;
			if(knockCount >= 10)
				surface.endGame(true);
		} else {
			isTouchingFinish = false;
		}
		if(player.getHealth() <= 0)
			surface.endGame(false);
				
		surface.push();
		surface.camera();
		surface.ortho();		
		drawUI();		
		surface.pop();
		
		if(surface.getCanUpgradeSpeed())
			speedVar = 2;
		else 
			speedVar = 1;
		
		if (surface.checkKey(KeyEvent.VK_W)) {
			player.moveZ(0.5f * speedVar);
		} else if (surface.checkKey(KeyEvent.VK_S)) {
			player.moveZ(-0.5f * speedVar);
		}
		if (surface.checkKey(KeyEvent.VK_A)) {
			player.moveX(0.5f * speedVar);
		} else if (surface.checkKey(KeyEvent.VK_D)) {
			player.moveX(-0.5f * speedVar);
		}
		if(surface.checkKey(KeyEvent.VK_SPACE)) {
			player.moveY(true);
		}else if (surface.checkKey(KeyEvent.VK_ALT)) {
			player.moveY(false);
		}

	}
	
	public Player getPlayer() {
		return player;
	}
	
	@Override
	public void mousePressed() {
		Weapon w = player.getWeapon();
		if(w != null && w instanceof Blaster) {
			long currentTime = surface.millis();
			if(currentTime - player.getLastAttackTime() >= Player.BLASTER_ATTACK_COOLDOWN) {
				Blaster b = (Blaster)w;
				b.use(player.getPosition(), player.getForward());
				player.setLastAttackTime(surface.millis());
			}
		}
			
	}
	
	/**
	 * Sets the amount of time that should be subtracted in order for the game to measure the time accurately
	 * 
	 * @param t the time that is considered
	 */
	public void setSubTime(long t) {
		if(subTime == 0) {
			subTime = t;
		} else {
			subTime += (t - pauseTime);
			pauseTime = 0;
		}
	}
	
	private void drawUI() {
		drawTimer();
		drawHealthBar();
		drawNumCoins();
		drawAttackUI();
		drawStoreEnter();
		drawKnockCount();
		drawUnfinish();
	}
	
	private void drawKnockCount() {
		surface.push();
		surface.translate(1620, 270);
		surface.textSize(100);
		surface.fill(0);
		if(knockCount <10)
			surface.text(knockCount + "/10", 0, 0);
		else
			surface.text(knockCount + "/10", -30, 0);
		surface.textSize(40);
		surface.text("Monsters Knocked", -50, -90);
		surface.pop();
	}
	
	private void drawUnfinish() {
		if(isTouchingFinish) {
			surface.push();
			surface.translate(655, 300);
			surface.textSize(60);
			surface.fill(255, 0, 0);
			surface.text("Not enough monsters knocked", 0, 0);
			surface.text("You need to knock " + (10 - knockCount) + " more monsters", -50, 100);
			surface.pop();
		}
	}
	
	private void drawStoreEnter() {
		if(canAccessStore) {
			surface.push();
			surface.translate(590, 300);
			surface.textSize(80);
			surface.fill(0);
			surface.text("Press \'T\' to Enter the Store", 0, 0);
			surface.pop();
		}
	}
	
	private void drawAttackUI() {
		surface.push();
		surface.stroke(0);
		surface.strokeWeight(2);
		surface.line(surface.width/2, surface.height/2 - 10, surface.width/2, surface.height/2 + 10);
		surface.line(surface.width/2 - 10, surface.height/2, surface.width/2 + 10, surface.height/2);
		Weapon w = player.getWeapon();
		if(w != null) {
			if(w instanceof Sword) {
				if(surface.millis() - player.getLastAttackTime() < Player.SWORD_ATTACK_COOLDOWN) {
					surface.stroke(0);
					surface.line(surface.width/2 - 15, surface.height/2 + 20, surface.width/2 + 15, surface.height/2 + 20);
					float coolTimeLength = (float)(surface.millis() - player.getLastAttackTime())/(float)Player.SWORD_ATTACK_COOLDOWN * 30;
					surface.stroke(255);
					surface.line(surface.width/2 - 15, surface.height/2 + 20, surface.width/2 - 15 + coolTimeLength, surface.height/2 + 20);
				}
			} else if(w instanceof Blaster) {
				if(surface.millis() - player.getLastAttackTime() < Player.BLASTER_ATTACK_COOLDOWN) {
					surface.stroke(0);
					surface.line(surface.width/2 - 15, surface.height/2 + 20, surface.width/2 + 15, surface.height/2 + 20);
					float coolTimeLength = (float)(surface.millis() - player.getLastAttackTime())/(float)Player.BLASTER_ATTACK_COOLDOWN * 30;
					surface.stroke(255);
					surface.line(surface.width/2 - 15, surface.height/2 + 20, surface.width/2 - 15 + coolTimeLength, surface.height/2 + 20);
				}
			}
		}
		surface.pop();
	}
	
	private void drawNumCoins() {
		surface.push();
		surface.translate(1600, 80);
		surface.textSize(70);
		surface.fill(0);
		surface.text("Coins: " + Player.coins, 0, 0);
		surface.pop();
	}
	
	private void drawTimer() {
		long elapsedTime = surface.millis() - subTime;
		int minutes = (int) (elapsedTime / 60000);
		int seconds = (int) ((elapsedTime % 60000) / 1000);
		
		String timerText = String.format("%02d:%02d", minutes, seconds);
		
		surface.push();
		surface.translate(10, 50);
		surface.textSize(70);
		surface.fill(0);
		surface.text("Time: " + timerText, 20, 30);	
		surface.pop();
	}
	
	private void drawHealthBar() {
		float healthPercentage;
		if(surface.getCanUpgradeHealth())
			healthPercentage = (float) player.getHealth() / 150f;
		else 
			healthPercentage = (float) player.getHealth() / 100f;
		
		float healthBarWidth = 600;
		float healthBarHeight = 50;
		float healthBarX = surface.width - 950;
		float healthBarY = 40;
		
		float filledWidth = healthPercentage * healthBarWidth;
		
		surface.push();
	//	surface.translate(0, 15);
		surface.noFill();
		surface.stroke(0);
		surface.rect(healthBarX, healthBarY, healthBarWidth, healthBarHeight);
		
		if (player.getHealth() > 0) {
			surface.fill(0, 255, 0);
			if(player.getHealth() < 25)
				surface.fill(255, 0, 0);
			else if(player.getHealth() < 50)
				surface.fill(255, 124, 0);
			surface.noStroke();
			surface.rect(healthBarX-healthBarWidth/2+filledWidth/2, healthBarY, filledWidth, healthBarHeight);
		}
		
		surface.pop();
	}
	
	@Override
	public void keyPressed() {
		
		if(surface.checkKey(KeyEvent.VK_T) && canAccessStore) {
			
			surface.moveToStore();	
			pauseTime = surface.millis();
		}
		
	}
	
	private void incKnock(int inc) {
		knockCount += inc;
	}
}
