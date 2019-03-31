/*
 * 								Base interface for all game objects.
 */

package src.interfaces;

import java.awt.Graphics;
import java.awt.Rectangle;

public interface Entity {
	public void tick();
	public void render(Graphics g);
	
	public double getX();
	public double getY();
	public void setX(double x);
	public void setY(double y);
	
	public Rectangle getBounds();
}
