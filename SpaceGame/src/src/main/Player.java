/*
 *								Class responsible for player's behavior.
 */

package src.main;

import java.awt.Graphics;

import src.interfaces.EntityPlayer;

public class Player extends GameObject implements EntityPlayer {
	
	private double velX = 0;
	private double velY = 0;
	private static int energy;
	private static Player player;
	
	private Player(double x, double y) {
		super(x, y);
		energy = 100;
		}
	
	public static void die() {
		Game.setState(STATE.LOSE);
	}
	
	public void tick() {
		x += velX;
		y += velY;

		// Processing of boundaries
		if(x <= 0) x = 0;
		if(x >= (Game.WIDTH * Game.SCALE) - 32) x = (Game.WIDTH * Game.SCALE) - 32;
		if(y <= 0) y = 0;
		if(y >= (Game.HEIGHT * Game.SCALE) - 32) y = (Game.HEIGHT * Game.SCALE) - 32;
	}
	
	public void render(Graphics g) {
		g.drawImage(Textures.player, (int)x, (int)y, null);
	}
	
	public void setVelX(double velX) {
		this.velX = velX;
	}
	
	public void setVelY(double velY) {
		this.velY = velY;
	}
	
	public static Player getPlayer() {
		if(player == null) {
			player = new Player(Game.WIDTH * Game.SCALE / 2, Game.HEIGHT * Game.SCALE - 32);
		}
		return player;
	}
	
	public static void setPlayer(Player value) {
		player = value;
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
}

