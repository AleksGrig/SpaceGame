/*	
 * 									Class loads all images and cuts parts from sprite sheet.
 */

package src.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Textures {
	
	public static BufferedImage player, missile, enemy, energy, enemyBullet, shield;
	public static BufferedImage[] explosion = new BufferedImage[3];
	public static BufferedImage background;
	private static BufferedImage spriteSheet;
	
	public static void initTextures() {
		try {
			spriteSheet = loadImage("res/Sprite_sheet.png");
			background = loadImage("res/background.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
		player = grabImage(0, 0, 32, 32);
		missile = grabImage(1, 0, 32, 32);
		enemy = grabImage(2, 0, 32, 32);
		energy = grabImage(4, 0, 32, 32);
		enemyBullet = grabImage(0, 1, 32, 32);
		shield = grabImage(1, 1, 32, 32);
		for(int i = 0; i < explosion.length; i++) {
		explosion[i] = grabImage(3, i, 32, 32);
		}
	}
	
	public static BufferedImage grabImage(int col, int row, int width, int height) {
		return spriteSheet.getSubimage(col * 32, row * 32, width, height);
	}
	
	public static BufferedImage loadImage(String path) throws IOException {
		return ImageIO.read(new FileInputStream(path));
	}
	
	public static void energyBar(Graphics g) {
		g.setColor(Color.GRAY);
		g.fillRect(5, 5, 200, 50);
		g.setColor(Color.YELLOW);
		g.fillRect(5, 5, Player.getEnergy() * 2, 50);
		g.setColor(Color.WHITE);
		g.drawRect(5, 5, 200, 50);
	}
	
	public static void temperatureBar(Graphics g) {
		g.setColor(Color.GRAY);
		g.fillRect(435, 5, 200, 50);
		g.setColor(Color.RED);
		g.fillRect(435, 5, (int)Player.getTemperature() * 2, 50);
		g.setColor(Color.WHITE);
		g.drawRect(435, 5, 200, 50);
		Font font = new Font("arial", Font.BOLD, 20);
		g.setFont(font);	
		g.drawString("TEMPERATURE", 465, 40);
		g.setColor(Color.BLACK);
		g.drawString("ENERGY", 55, 40);		
	}

	public static void drawScore(Graphics g) {
		Font font = new Font("arial", Font.BOLD, 20);
		g.setFont(font);
		g.drawString(String.valueOf("RECORD: " + Game.getRecord()),5, 80);
		g.drawString(String.valueOf("SCORE: " + Game.getScore()),5, 100);
	}
}
