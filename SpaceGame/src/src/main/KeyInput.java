/* 	
 * 					Class responsible for capture and process of keyboard  input.									
 */
 																										
package src.main;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {
	
	private boolean is_shooting = false;
	private boolean isLeftPressed = false;
	private boolean isRightPressed = false;
	private boolean isUpPressed = false;
	private boolean isDownPressed = false;
	
	// Key strokes are processed only when in game mode
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		if(Game.getState() == STATE.GAME) {
			if(key == KeyEvent.VK_RIGHT) {
				isRightPressed = true;
				Player.setVelX(3);
			} else if (key == KeyEvent.VK_LEFT) {
				isLeftPressed = true;
				Player.setVelX(-3);
			} else if (key == KeyEvent.VK_UP) {
				isUpPressed = true;
				Player.setVelY(-3);
			} else if (key == KeyEvent.VK_DOWN) {
				isDownPressed = true;
				Player.setVelY(3);
			} else if (key == KeyEvent.VK_SPACE && !is_shooting && Player.getTemperature() < 95) {
				is_shooting = true;
				Controller.addEntity(new Bullet(Player.getPlayer().getX(), Player.getPlayer().getY() - 32));
				Player.decreaseEnergy(1);
				Player.increaseTemperature(5);
			} else if (key == KeyEvent.VK_ESCAPE) {
				Player.die();				
			} else if (key == KeyEvent.VK_X) {
				if(!Game.isPaused) {
					Game.isPaused = true;
				} else {
					Game.isPaused = false;
				}
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
			if(isLeftPressed) {
				Player.setVelX(-3);				
			} else {
				Player.setVelX(0);
			}
			isRightPressed = false;
		} else if (key == KeyEvent.VK_LEFT) {
			if(isRightPressed) {
				Player.setVelX(3);
			} else {
				Player.setVelX(0);
			}
			isLeftPressed = false;
		} else if (key == KeyEvent.VK_UP) {
			if(isDownPressed) {
				Player.setVelY(3);
			} else	{
				Player.setVelY(0);
			}
			isUpPressed = false;
		} else if (key == KeyEvent.VK_DOWN) {
			if(isUpPressed) {
				Player.setVelY(-3);
			} else {
				Player.setVelY(0);
			}
			isDownPressed = false;
		} else if (key == KeyEvent.VK_SPACE) {
			is_shooting = false;
		}	
	}
}
