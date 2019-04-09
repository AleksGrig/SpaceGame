/*
 *								Class responsible for player's behavior.
 */

package src.main.gameobjects;

import java.awt.Graphics;

import src.interfaces.EntityPlayer;
import src.main.Game;
import src.main.GameObject;
import src.main.STATE;
import src.main.Textures;

public class Player extends GameObject implements EntityPlayer {
	
	private static double velX = 0;
	private static double velY = 0;
	private static int energy;
	private static Player player;
	private static double temperature;
	private static boolean isShielded;
	private static long shieldTimer;
	private static boolean isCooling;
	private static long coolingTimer;
	
	private Player(double x, double y) {
		super(x, y);
		energy = 100;
		temperature = 0;
		isShielded = false;
		isCooling = false;
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
		
		if(Player.getShieldTimer() > 10000) isShielded = false;
		if(Player.getCoolingTimer() > 10000) isCooling = false;
	}
	
	public void render(Graphics g) {
		if(!isShielded) g.drawImage(Textures.player, (int)x, (int)y, null);
		else g.drawImage(Textures.playerShielded, (int)x, (int)y, null);
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
	
	public static void setShield() {
		isShielded = true;
		shieldTimer = System.currentTimeMillis();
	}
	
	public static void removeShield() {
		isShielded = false;
	}
	
	public static boolean isShielded() {
		return isShielded;
	}
	
	public static long getShieldTimer() {
		return (System.currentTimeMillis() - shieldTimer);
	}
	
	public static boolean isCooling() {
		return isCooling;
	}
	
	public static void setCooling() {
		isCooling = true;
		coolingTimer = System.currentTimeMillis();
	}
	
	public static void stopCooling() {
		isCooling = false;
	}
	
	public static long getCoolingTimer() {
		return (System.currentTimeMillis() - coolingTimer);
	}
}

