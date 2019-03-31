/*	Base class for all moving objects.										*/ 

package src.main;

import java.awt.Rectangle;

public class GameObject {
	
	public double x;
	public double y;
	
	public GameObject(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, 32, 32);
	}
	
	public double getY() {
		return this.y;
	}

	public double getX() {
		return this.x;
	}
	
	public void setY(double y) {
		this.y = y;
	}

	public void setX(double x) {
		this.x = x;
	}
}
