package entity;

import java.awt.Graphics;
import java.awt.Rectangle;


/**
 *  The <code>Missile</code> class is fired from a <code>Ship</code> or 
 *  <code>AlienShipe</code> with a set speed and direction. A <code>Missle</code>
 *  destroys any <code>Ship</code> or <code>AlienShip</code> it collides with.
 *  A <code>Missile</code> will also destruct if it collides with a game window
 *  border.
 *  
 *  @author Michael Twardowski
 */
public class Missile extends Entity {
	
	/**
	 * Missile Dimensions
	 */
	private int width,
				height;
	
	/**
	 * Missile Speed, negative speed is traveling up, positive is down
	 */
	private int speed;
	
	/**
	 * Default Constructor for <code>Missile</code>.
	 */
	public Missile() {
	}
	
	/**
	 * Sets all of the properties of <code>Missile</code>.
	 * @param x the x location
	 * @param y the y location
	 * @param speed of the missile, a negative speed would fly up.
	 */
	public Missile(int x, int y, int speed) {
		super(x, y, 5, 15);
		width = 5;
		height = 25;
		this.speed = speed;
	}

	/**
	 * Sets the shape of the Missile
	 */
	@Override
	public void setShape() {
		shape = new Rectangle(x, y, width, height);
	}

	/**
	 * Moves the <code>Missile</code> along its trajectory.
	 */
	public void move(){
		y = y + speed;
	}
	
	/**
	 * Paints the Missile
	 */
	@Override
	public void paint(Graphics pane) {
		pane.setColor(color);
		pane.fillRect(x, y, width, height);
	}
	
	/**
	 * Fire does nothing, returns null
	 */
	@Override
	public Entity fire(){
		return null;
	}
}