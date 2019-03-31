package src.main;

import java.awt.Graphics;
import java.util.Random;

import src.interfaces.EntityEnergyBonus;

public class EnergyBonus extends GameObject implements EntityEnergyBonus {
	
	private Random r = new Random();
	private int speed;
	private int energy;

	public EnergyBonus(double x, double y, int energy) {
		super(x, y);
		this.energy = energy;
		speed = r.nextInt(3) + 1;
	}

	public void tick() {
		y += speed;
		
		Controller.energyBonusCollides(this);
		
		if(y > Game.HEIGHT * Game.SCALE) {
			Controller.removeEntity(this);
		}		
	}

	public void render(Graphics g) {
		g.drawImage(Textures.energy, (int)x, (int)y, null);
		
	}
	
	public int getEnergy() {
		return this.energy;
	}

}
