/*
 *								Class responsible for enemy's behavior. 
 */

package src.main;

import java.awt.Graphics;
import java.util.Random;

import src.interfaces.EntityEnemy;

public class Enemy extends GameObject implements EntityEnemy{
	
	Random r = new Random();
	private int speed = r.nextInt(3) + 1;
	
	public Enemy(double x, double y) {
		super(x, y);
	}
	
	public void tick() {
		y += speed;
	
		Controller.enemyCollides(this);				// All collisions are processed from enemy point of view 
		
		if(y > Game.HEIGHT * Game.SCALE) {			
			Controller.removeEntity(this);			// If enemy reaches bottom of screen we delete it and
			Controller.createEnemy(1);				// create new one above
			Player.decreaseEnergy(10);
		}		
	}
	
	public void render(Graphics g) {
		g.drawImage(Textures.enemy, (int)x, (int)y, null);
	}
}
