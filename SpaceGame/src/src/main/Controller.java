/* 
 * 			Class keeps and manages all game objects, like player, bullets and enemies, processes collisions 
 * 			and finds place for enemies.  
 */

package src.main;

import java.awt.Graphics;
import java.util.LinkedList;
import java.util.Random;

import src.interfaces.Entity;
import src.interfaces.EntityPlayer;
import src.interfaces.EntityEnemy;
import src.interfaces.EntityEnergyBonus;
import src.interfaces.EntityExplosion;

public class Controller {

	private static LinkedList<Entity> entities;
	private static LinkedList<EntityExplosion> explosions;
	private static LinkedList<EntityEnergyBonus> energyBonus;
	private static Random r = new Random();
	
	public static void createEnemy(int enemy_count) {
		for(int i = 0; i < enemy_count; i++) {
			addEntity(findPlace(new Enemy(0,0)));
		}
	}
	
	public static void createEnergyBonus(EntityExplosion explosion) {
		if(r.nextInt(5) == 3) {
		energyBonus.add(new EnergyBonus(explosion.getX(), explosion.getY(), r.nextInt(100)));
		}
	}
	
	// Finding place for new enemy to not intersect with other existing enemies
	public static Entity findPlace(Entity enemy) {
		boolean isIntersected;
		int x = 0;
		do {
			isIntersected = false;
			x = r.nextInt(Game.WIDTH * Game.SCALE);
			if(x < 34) x = 34; 
			else if(x > Game.WIDTH * Game.SCALE - 34) x = Game.WIDTH * Game.SCALE - 34;
			enemy.setX((double)x);
			enemy.setY((double)r.nextInt(2) * 32 - 10);
			for(int i = 0; i < entities.size(); i++) {
				if(enemy != entities.get(i) && entities.get(i) instanceof EntityEnemy && 
						enemy.getBounds().intersects(entities.get(i).getBounds())) {
					isIntersected = true;
				}
			}
		} while(isIntersected);
		
		return enemy;
	}
		
	public static void tick() {
		Player.getPlayer().tick();
		
		for(int i = 0; i < entities.size(); i++) {
			entities.get(i).tick();
		}
		
		for(int i = 0; i < explosions.size(); i++) {
			explosions.get(i).tick();
		}
		
		for(int i = 0; i < energyBonus.size(); i++) {
			energyBonus.get(i).tick();
		}
	}
	
	public static void render(Graphics g) {
		Player.getPlayer().render(g);

		for(int i = 0; i < entities.size(); i++) {
			entities.get(i).render(g);
		}
		
		for(int i = 0; i < explosions.size(); i++) {
			explosions.get(i).render(g);
		}
		
		for(int i = 0; i < energyBonus.size(); i++) {
			energyBonus.get(i).render(g);
		}
	}
	
	// All collisions are processed from enemy point of view 
	public static void enemyCollides(EntityEnemy enemy) {
		double x = enemy.getX();
		double y = enemy.getY();
		if(enemy.getBounds().intersects(Player.getPlayer().getBounds())) {
			Controller.removeEntity(enemy);
			Controller.addEntity(new Explosion(x, y));
			Game.setEnemyKilled(Game.getEnemyKilled() + 1);
			Game.setScore(Game.getScore() + 1);
			Player.decreaseEnergy(10);
		} else 
			for(int i = 0; i < entities.size(); i++) {
			if((enemy != entities.get(i)) && (enemy.getBounds().intersects(entities.get(i).getBounds()))) {
				if(entities.get(i) instanceof EntityPlayer) {
					Game.setEnemyKilled(Game.getEnemyKilled() + 1);
					Game.setScore(Game.getScore() + 1);
					} else {
					Game.setEnemyKilled(Game.getEnemyKilled() + 2);
				}
					Controller.removeEntity(entities.get(i));
					Controller.removeEntity(enemy);
					Controller.addEntity(new Explosion(x, y));
					break;
				
			}
		}
	}
	
	public static void energyBonusCollides(EntityEnergyBonus energy) {
		if(energy.getBounds().intersects(Player.getPlayer().getBounds())) {
			Player.increaseEnergy(((EnergyBonus) energy).getEnergy());
			Controller.energyBonus.remove(energy);
		}
		
	}
	
	public static void addEntity(Entity block) {
		if(block instanceof EntityExplosion) {
			explosions.add((EntityExplosion) block);
		} else if(block instanceof EntityEnergyBonus) {
			energyBonus.add((EntityEnergyBonus) block);
		} else {
			entities.add(block);
		}
	}
	
	public static void removeEntity(Entity block) {
		if(block instanceof EntityExplosion) {
			explosions.remove(block);
		} else if(block instanceof EntityEnergyBonus) {
			energyBonus.remove(block);
		} else {
			entities.remove(block);
		}
	}
	
	public static void initController() {
		entities = new LinkedList<Entity>();
		explosions = new LinkedList<EntityExplosion>();
		energyBonus = new LinkedList<EntityEnergyBonus>();
	}
}
	
	