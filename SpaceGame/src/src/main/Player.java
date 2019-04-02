/*
 *								Class responsible for player's behavior.
 */

package src.main;

import java.awt.Graphics;

import src.interfaces.EntityPlayer;

public class Player extends GameObject implements EntityPlayer {
	
	private static double velX = 0;
	private static double velY = 0;
	private static int energy;
	private static Player player;
	private static double temperature;
	
	private Player(double x, double y) {
		super(x, y);
		energy = 100;
		temperature = 0;
		}
	
	public static void die() {
		Game.setState(STATE.LOSE);
	}
	
	public void tick() {
		x += velX;
		y += velY;
		
		temperature -= 0.1;

		// Processing of boundaries
		if(x <= 0) x = 0;
		if(x >= (Game.WIDTH * Game.SCALE) - 32) x = (Game.WIDTH * Game.SCALE) - 32;
		if(y <= 0) y = 0;
		if(y >= (Game.HEIGHT * Game.SCALE) - 32) y = (Game.HEIGHT * Game.SCALE) - 32;
	}
	
	public void render(Graphics g) {
		g.drawImage(Textures.player, (int)x, (int)y, null);
	}
	
	public static void setVelX(double value) {
		velX = value;
	}
	
	public static void setVelY(double value) {
		velY = value;
	}
	
	public static Player getPlayer() {
		if(player == null) {
			player = new Player(Game.WIDTH * Game.SCALE / 2, Game.HEIGHT * Game.SCALE - 32);
		}
		return player;
	}
	
	public static void resetPlayer() {
		player = null;
	}
	
	public static void setEnergy(int value) {
		energy = value;
		if(energy > 100) energy = 100;
	}
	
	public static int getEnergy() {
		return energy;
	}
	
	public static void increaseEnergy(int value) {
		energy += value;
		if(energy > 100) energy = 100;
	}
	
	public static void decreaseEnergy(int value) {
		energy -= value;
		if(energy <= 0) Player.die();
	}
	
	public static void increaseTemperature(int value) {
		temperature += value;
		if(temperature > 100) temperature = 100;
	}
	
	public static void decreaseTemperature(double value) {
		temperature -= value;
		if(temperature < 0) temperature = 0;
	}
	
	public static double getTemperature() {
		return temperature;
	}
}

