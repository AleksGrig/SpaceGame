package src.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Screens {
	private static Rectangle playButton = new Rectangle(Game.WIDTH / 2 + 120, 150, 100, 50);
	private static Rectangle helpButton = new Rectangle(Game.WIDTH / 2 + 120, 250, 100, 50);
	private static Rectangle quitButton = new Rectangle(Game.WIDTH / 2 + 120, 350, 100, 50);
	private static Rectangle okButton = new Rectangle(Game.WIDTH / 2 + 120, 350, 100, 50);
	
	public static void drawMenu(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		Font fnt0 = new Font("arial", Font.BOLD, 50);
		g.setFont(fnt0);
		g.setColor(Color.WHITE);
		g.drawString("SPACE GAME", Game.WIDTH / 2, 100);
		
		Font fnt1 = new Font("arial", Font.BOLD, 30);
		g.setFont(fnt1);
		
		g.drawString("PLAY", playButton.x + 10, playButton.y + 35);
		g.drawString("HELP", helpButton.x + 10, helpButton.y + 35);
		g.drawString("QUIT", quitButton.x + 10, quitButton.y + 35);
		
		g2d.draw(playButton);
		g2d.draw(helpButton);
		g2d.draw(quitButton);
	}
	
	public static void drawHelp(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		Font fnt0 = new Font("arial", Font.BOLD, 50);
		g.setFont(fnt0);
		g.setColor(Color.WHITE);
		g.drawString("HELP", Game.WIDTH - 50, 100);
		
		Font font1 = new Font("arial", Font.BOLD, 20);
		g.setFont(font1);
				
		g.drawString("Game goal is to kill as many enemies as possible.", 50, 150);
		g.drawString("You die when you loose all energie.", 50, 200);
		g.drawString("You have 100 energy, every shot costs you 2,", 50, 250);
		g.drawString("every enemy reached bottom 10, every collision 10.", 50, 300);
		
		Font fnt2 = new Font("arial", Font.BOLD, 30);
		g.setFont(fnt2);
		
		g.drawString("OK", okButton.x + 20, okButton.y + 35);
		g2d.draw(okButton);
	}
	
	@SuppressWarnings("resource")
	public static void drawLose(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		Font fnt0 = new Font("arial", Font.BOLD, 40);
		g.setFont(fnt0);
		g.setColor(Color.RED);
		g.drawString("YOU ARE KILLED, LOOSER!!!", 30, 100);
		g.setColor(Color.WHITE);
		g.drawString("YOU HIT " + Game.getScore() + " SHIPS.", 150, 220);
		if(Game.getScore() > Game.getRecord()) {
			g.setColor(Color.RED);
			g.drawString("NEW RECORD!!!", 180, 320);
			try {
				new DataOutputStream(new FileOutputStream("res/data")).writeInt(Game.getScore());
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			g.setColor(Color.WHITE);
		}
		g.drawString("OK", okButton.x + 20, okButton.y + 35);
		g2d.draw(okButton);		
	}
}
