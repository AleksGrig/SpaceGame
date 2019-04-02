/* 	
 * 					Class responsible for capture and process of keyboard  input.									
 */
 																										
package src.main;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {
	
	private boolean is_shooting = false;
	
	// Key strokes are processed only when in game mode
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		if(Game.getState() == STATE.GAME) {
			if(key == KeyEvent.VK_RIGHT) {
				Player.setVelX(3);
			} else if (key == KeyEvent.VK_LEFT) {
				Player.setVelX(-3);
			} else if (key == KeyEvent.VK_UP) {
				Player.setVelY(-3);
			} else if (key == KeyEvent.VK_DOWN) {
				Player.setVelY(3);
			} else if (key == KeyEvent.VK_SPACE && !is_shooting && Player.getTemperature() < 95) {
				is_shooting = true;
				Controller.addEntity(new Bullet(Player.getPlayer().getX(), Player.getPlayer().getY() - 32));
				Player.decreaseEnergy(1);
				Player.increaseTemperature(5);
			} else if (key == KeyEvent.VK_ESCAPE) {
				Player.die();				
			}
		}
		if(key == KeyEvent.VK_ESCAPE) {
			if(Game.getState() == STATE.HELP) 
			Game.setState(STATE.MENU);
		}
	}
	
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_RIGHT) {
			Player.setVelX(0);
		} else if (key == KeyEvent.VK_LEFT) {
			Player.setVelX(0);
		} else if (key == KeyEvent.VK_UP) {
			Player.setVelY(0);
		} else if (key == KeyEvent.VK_DOWN) {
			Player.setVelY(0);
		} else if (key == KeyEvent.VK_SPACE) {
			is_shooting = false;
		}	
	}
}
