package src.main.bonuses;

import java.awt.Graphics;
import java.util.Random;

import src.interfaces.EntityBonus;
import src.main.Controller;
import src.main.Game;
import src.main.GameObject;
import src.main.Textures;

public class ShieldBonus extends GameObject implements EntityBonus{
	
	private Random r = new Random();
	private int speed;

	public ShieldBonus(double x, double y) {
		super(x, y);	
		speed = r.nextInt(3) + 1;
	}
	
	public void tick() {
		y += speed;
		
		Controller.bonusCollides(this);
		
		if(y > Game.HEIGHT * Game.SCALE) {
			Controller.removeEntity(this);
		}	
		
	}

	public void render(Graphics g) {
		g.drawImage(Textures.shield, (int)x, (int)y, null);
	}	
}
