package src.main;

import java.awt.Graphics;
import src.interfaces.EntityExplosion;

public class Explosion extends GameObject implements EntityExplosion{
	
	private long timer;

	public Explosion(double x, double y) {
		super(x, y);	
		timer = System.currentTimeMillis();
	}

	public void tick() {
		if(System.currentTimeMillis() - timer > 1000) {
			Controller.createEnergyBonus(this);
			Controller.removeEntity(this);
		}
		
	}

	public void render(Graphics g) {
		if(System.currentTimeMillis() - timer < 300) {
			g.drawImage(Textures.explosion[0], (int)x, (int)y, null);		
		} else if(System.currentTimeMillis() - timer >= 300 && System.currentTimeMillis() - timer < 670) {
			g.drawImage(Textures.explosion[1], (int)x, (int)y, null);
		} else if(System.currentTimeMillis() - timer>= 670) {
			g.drawImage(Textures.explosion[2], (int)x, (int)y, null);
		}
		
	}
}
