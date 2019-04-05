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
import src.interfaces.EntityEnemyBullet;
import src.interfaces.EntityBonus;
import src.interfaces.EntityExplosion;

public class Controller {

	private static LinkedList<Entity> entities;
	private static LinkedList<EntityExplosion> explosions;
	private static LinkedList<EntityBonus> bonuses;
	private static LinkedList<EntityEnemyBullet> enemyBullets;
	private static Random r = new Random();
	
	public static void createEnemy(int enemy_count) {
		for(int i = 0; i < enemy_count; i++) {
			addEntity(findPlace(new Enemy(0,0)));
		}
	}
	
	public static void createBonus(EntityExplosion explosion) {
		if(r.nextInt(5) == 3) {
		bonuses.add(new EnergyBonus(explosion.getX(), explosion.getY(), r.nextInt(100)));
		} else if(r.nextInt(30) == 15) {
			bonuses.add(new ShieldBonus(explosion.getX(), explosion.getY()));
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
		
		for(int i = 0; i < bonuses.size(); i++) {
			bonuses.get(i).tick();
		}
		
		for(int i = 0; i < enemyBullets.size(); i++) {
			enemyBullets.get(i).tick();
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
		
		for(int i = 0; i < bonuses.size(); i++) {
			bonuses.get(i).render(g);
		}
		
		for(int i = 0; i < enemyBullets.size(); i++) {
			enemyBullets.get(i).render(g);
		}
	}
	
	// All collisions are processed from enemy point of view 
	public static void enemyCollides(EntityEnemy enemy) {
		if(enemy.getBounds().intersects(Player.getPlayer().getBounds())) {
			Controller.addEntity(new Explosion(enemy.getX(), enemy.getY()));
			Controller.removeEntity(enemy);
			Game.setScore(Game.getScore() + 1);
			if(!Player.isShielded()) {
				Player.decreaseEnergy(25);
			}
		} else 
			for(int i = 0; i < entities.size(); i++) {
			if((enemy != entities.get(i)) && (enemy.getBounds().intersects(entities.get(i).getBounds()))) {
				if(entities.get(i) instanceof EntityPlayer) {
					Game.setScore(Game.getScore() + 1);
					}
				Controller.removeEntity(entities.get(i));
				Controller.addEntity(new Explosion(enemy.getX(), enemy.getY()));
				Controller.removeEntity(enemy);
				break;				
			}
		}
	}
	
	public static void bonusCollides(EntityBonus bonus) {
		if(bonus.getBounds().intersects(Player.getPlayer().getBounds())) {
			if(bonus instanceof EnergyBonus) {
				if(!Player.isShielded()) {
					Player.increaseEnergy(((EnergyBonus) bonus).getEnergy());
				}
			} else if(bonus instanceof ShieldBonus) {
				if(!Player.isShielded()) Player.setShield();
			}
			Controller.removeEntity(bonus);
		}		
	}
	
	public static void enemyBulletCollides(EntityEnemyBullet enemyBullet) {
		if(enemyBullet.getBounds().intersects(Player.getPlayer().getBounds())) {
			Controller.removeEntity(enemyBullet);
			if(!Player.isShielded()) {
				Player.decreaseEnergy(10);
			}
		}
		for(int i = 0; i < entities.size(); i++) {
			if(enemyBullet.getBounds().intersects(entities.get(i).getBounds())) {
				Controller.removeEntity(enemyBullet);
				if(entities.get(i) instanceof EntityPlayer) {
				Controller.removeEntity(entities.get(i));
				}
			}
		}		
	}
	
	public static void addEntity(Entity block) {
		if(block instanceof EntityExplosion) {
			explosions.add((EntityExplosion) block);
		} else if(block instanceof EntityBonus) {
			bonuses.add((EntityBonus) block);
		} else if(block instanceof EntityEnemyBullet){
			enemyBullets.add((EntityEnemyBullet) block);
		} else {
			entities.add(block);
		}
	}
	
	public static void removeEntity(Entity block) {
		if(block instanceof EntityExplosion) {
			explosions.remove(block);
		} else if(block instanceof EntityBonus) {
			bonuses.remove(block);
		} else if(block instanceof EntityEnemyBullet){
			enemyBullets.remove(block);
		} else {
			entities.remove(block);
		}
	}
	
	public static void initController() {
		entities = new LinkedList<Entity>();
		explosions = new LinkedList<EntityExplosion>();
		bonuses = new LinkedList<EntityBonus>();
		enemyBullets = new LinkedList<EntityEnemyBullet>();
	}
	
	public static int getEnemyNumber() {
		if(entities  != null) {
			int number = 0;
			for(int i = 0; i < entities.size(); i++) {
				if(entities.get(i) instanceof EntityEnemy) {
					number++;
				}				
			}	
			return number;
		} else {
			return 0;
		}
	}
}
	
	
