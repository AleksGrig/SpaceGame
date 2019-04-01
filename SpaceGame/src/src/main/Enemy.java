/*
 *								Class responsible for enemy's behavior. 
 */

package src.main;

import java.awt.Graphics;
import java.util.Random;

import src.interfaces.EntityEnemy;

public class Enemy extends GameObject implements EntityEnemy{
	
	Random r = new Random();
	private int speed = r.nextInt(3) + 2;
	private int velX;
	
	public Enemy(double x, double y) {
		super(x, y);
		if(r.nextBoolean()) {
			velX = r.nextInt(2) + 1;
			} else {
				velX = -1 * (r.nextInt(2) + 1);
			}
		
	}
	
	public void tick() {
		if(r.nextInt(50) == 4) {
			Controller.addEntity(new EnemyBullet(this.x, this.y + 32));
		}
		y += speed;
		
		if(r.nextInt(50) == 7) {
			if(r.nextBoolean()) {
				this.velX += (r.nextInt(3) + 1);
			} else {
				this.velX -= (r.nextInt(3) + 1);
			}			
		}
		x += velX;
		
		if(x <= 0) x = 0;
		if(x >= (Game.WIDTH * Game.SCALE) - 32) x = (Game.WIDTH * Game.SCALE) - 32;
	
		Controller.enemyCollides(this);				// All collisions are processed from enemy point of view 
		
		if(y > Game.HEIGHT * Game.SCALE) {			
			Controller.removeEntity(this);			// If enemy reaches bottom of screen we delete it and
			Controller.createEnemy(1);				// create new one above
		}		
	}
	
	public void render(Graphics g) {
		g.drawImage(Textures.enemy, (int)x, (int)y, null);
	}
}
