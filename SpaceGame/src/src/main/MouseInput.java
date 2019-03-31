/*
 * 						Class responsible for capture and process of mouse  input.
 */

package src.main;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseInput extends MouseAdapter{

	// Process mouse input only when in menu mode
	public void mousePressed(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();
		
		if(Game.getState() == STATE.MENU) {				
			if(mx >= Game.WIDTH / 2 + 120 && mx <=  Game.WIDTH / 2 + 220) {
				if(my >= 150 && my <= 200) {
					// Play Button Pressed
					Game.setState(STATE.GAME);					
				}
				if(my >= 350 && my <= 400) {
					// Quit Button Pressed
					System.exit(1);
				}
				if(my >= 250 && my <= 300) {
					// Help Button Pressed
					Game.setState(STATE.HELP);
				}
			}
		} else if(Game.getState() == STATE.HELP) {
			if((mx >= Game.WIDTH / 2 + 120) && (mx <=  Game.WIDTH / 2 + 220) && (my >= 350 && my <= 400)) {
				Game.setState(STATE.MENU);
			}
		} else if(Game.getState() == STATE.LOSE) {
			if((mx >= Game.WIDTH / 2 + 120) && (mx <=  Game.WIDTH / 2 + 220) && (my >= 350 && my <= 400)) {
				Game.setState(STATE.MENU);
				Game.isGameBegining = true;
			}	
		}
	}
}
