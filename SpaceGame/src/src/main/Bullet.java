/*
 *								Class responsible for bullet's behavior.
 */

package src.main;

import java.awt.Graphics;

import src.interfaces.EntityPlayer;

public class Bullet extends GameObject implements EntityPlayer{
	
	public Bullet(double x, double y) {
		super(x, y);
	}
	
	public void tick() {
		y -= 10;
		
		if(y < 0) {
			Controller.removeEntity(this);  			// Remove bullet when leaving the screen
		}
	}
	
	public void render(Graphics g) {
		g.drawImage(Textures.missile, (int)x, (int)y, null);
	}
}
