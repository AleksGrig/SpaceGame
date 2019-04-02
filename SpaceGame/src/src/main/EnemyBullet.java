package src.main;

import java.awt.Graphics;

import src.interfaces.EntityEnemyBullet;

public class EnemyBullet extends GameObject implements EntityEnemyBullet{

	public EnemyBullet(double x, double y) {
		super(x, y);		
	}

	public void tick() {
		y += 10;
		
		Controller.enemyBulletCollides(this);
		
		if(y > Game.HEIGHT * Game.SCALE ) {
			Controller.removeEntity(this);
		}		
	}

	public void render(Graphics g) {
		g.drawImage(Textures.enemyBullet, (int)x, (int)y, null);		
	}

}
