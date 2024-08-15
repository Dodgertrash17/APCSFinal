package screens;

import java.util.ArrayList;
import java.util.Collections;

import com.jogamp.newt.event.KeyEvent;

import core.DrawingSurface;

/**
 * Represents the screen that is shown when the game ends. It gives a list of all the users top times in the maze
 * 
 * @author Yujun Lee
 * @version 05262024
 */
public class GameEndScreen extends Screen {
	
	private DrawingSurface surface;
	
	private boolean win, dead;
	
	private String timerText;
	
	private String[] times;
	
	/**
	 * Creates a new GameEndScreen
	 * @param surface The surface to which the components are drawn to
	 */
	public GameEndScreen(DrawingSurface surface) {
		super(800, 600);
		this.surface = surface;
		times = new String[5];
		times[0] = "00:00";
		times[1] = "00:00";
		times[2] = "00:00";
		times[3] = "00:00";
		times[4] = "00:00";
	}
	
	@Override
	public void setPerspective() {
		surface.ortho();
	}
	
	public void setup() {
		
	}
	
	@Override
	public void draw() {
		surface.camera();
		surface.fill(0);
		
		if(win) {
			surface.background(150, 255, 255);
			surface.textSize(100);
			surface.text("Congratulations! You beat the Maze!", 380, 230);
			surface.text("Your time was " + timerText, 600, 360);
			surface.textSize(70);
			surface.text("Press 'L' to see the leaderboard", 600, 600);
		} else if(dead) {
			surface.background(180, 90, 30);
			surface.textSize(65);
			surface.fill(100, 0, 100);
			surface.text("Dhruv has caught you! Buy some upgrades to help "
					+ "escape the maze and try again.", 105, 230);
			surface.fill(0);
			surface.text("Press 'M' to go back to the menu.", 635, 650);
			surface.text("Press 'S' to start a new game.", 655, 750);
		} else {
			surface.background(255,200,150);
			surface.textSize(140);
			surface.text("Leaderboard", 660, 100);
			drawTimesList();
			surface.textSize(60);
			surface.text("Press 'M' to go back to the menu.", 200, 840);
			surface.text("Press 'S' to start a new game.", 1100, 840);
		}
	}
	
	@Override
	public void keyPressed() {
		if(surface.keyCode == KeyEvent.VK_L && win) {
			reset();
		} else if(surface.keyCode == KeyEvent.VK_M && !win) {
			reset();
			surface.switchScreen(0);
			surface.newGame();
		} else if(surface.keyCode == KeyEvent.VK_S && !win) {
			reset();
			timerText = "";
			surface.newGame();
			surface.switchScreen(2);
		}

	}
	
	/**
	 * Calculates the time the user took to complete the maze 
	 */
	public void setTime() {
		long elapsedTime = surface.millis() - GameScreen.subTime;
		int minutes = (int) (elapsedTime / 60000);
		int seconds = (int) ((elapsedTime % 60000) / 1000);
		timerText = String.format("%02d:%02d", minutes, seconds);
	}
	
	/**
	 * Sets the win status of the game
	 * 
	 * @param win true if win
	 */
	public void setWin(boolean win) {
		this.win = win;
	}
	
	/**
	 * Sets the dead status of the Player
	 * 
	 * @param dead true if dead
	 */
	public void setDead(boolean dead) {
		this.dead = dead;
	}
	
	/**
	 * Resets the game conditions that check id the user wins or the user dies
	 */
	public void reset() {
		win = false;
		dead = false;
	}
	
	/**
	 * Adds all the users top times in a list
	 */
	public void addTime() {
		ArrayList<Integer> temp = new ArrayList<Integer>();
		for(String t : times)
			temp.add(stringTimeToInt(t));
		temp.add(stringTimeToInt(timerText));
		Collections.sort(temp);
		boolean isFrontZero = false;
		do {
			if(temp.get(0) == 0) {
				temp.add(temp.remove(0));
			}
			if(temp.get(0) == 0)
				isFrontZero = true;
			else
				isFrontZero = false;
		} while(isFrontZero);
		for(int i = 0; i < 5; i++) {
			times[i] = intToStringTime(temp.get(i));
		}
	}
	
	private int stringTimeToInt(String time) {
//		System.out.println(time);
//		System.out.println(Integer.parseInt(time.substring(0, 2)));
		
		if(time == null) {
			return 0;
		}
		
		return Integer.parseInt(time.substring(0, 2)) * 100 + Integer.parseInt(time.substring(3));
//		return 0;

	}
	
	private String intToStringTime(int val) {
		if(val < 10)
			return "00:0" + val;
		if(val <100)
			return "00:" + val;
		String front;
		if(val/100 < 10)
			front = "0" + val/100 + ":";
		else
			front = val/100 + ":";
		String end;
		if(val%100 < 10)
			end = "0" + val%100;
		else
			end = "" + val%100;
		return front + end;
	}
	
	private void drawTimesList() {
		for (int i = 0; i < times.length; i++) {
			surface.textSize(100);
			if(i == 0) {
				surface.fill(255, 255, 0);
			} else if(i == 1) {
				surface.fill(224, 224, 224);
			} else if(i == 2) {
				surface.fill(200, 150, 50);
			} else {
				surface.fill(0);
			}
			if(times[i] != null)
				surface.text(i+1 + ". " + times[i], 790, 220 + i * 120);
			else
				surface.text(i+1 + ". 00:00", 790, 220 + i * 120);
			
		}
	}

}
