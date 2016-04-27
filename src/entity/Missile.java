package entity;

import java.awt.Graphics;


/**
 *  The <code>Missile</code> class is fired from a <code>Ship</code> or 
 *  <code>AlienShipe</code> with a set speed and direction. A <code>Missle</code>
 *  destroys any <code>Ship</code> or <code>AlienShip</code> it collides with.
 *  A <code>Missile</code> will also destruct if it collides with a game window
 *  border.
 *  
 *  @Author Michael Twardowski
 */
public class Missile extends Entity {
	
	/**
	 * Tells the direction in which the missile is fired.
	 * If true, the missile is fired up.
	 */
	private boolean isMissileRising;
	
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
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Sets all of the properties of <code>Missile</code>.
	 * @param x the x location
	 * @param y the y location
	 * @param width of the <code>Missile</code>
	 * @param height of the <code>Missile</code>
	 * @param isUp the direction of the missile. (Up if true)
	 */
	public Missile(int x, int y, int speed) {
		super(x, y, 5, 15);
		width = 5;
		height = 15;
		this.speed = speed;
	}

	/**
	 * Sets the shape of the Missile
	 */
	@Override
	public void setShape() {
		// TODO Auto-generated method stub
	}

	/**
	 * Moves the <code>Missle</code> along its trajectory until a collision occurs.
	 */
	public void move(){
		y = y + speed;
	}
	
	/**
	 * Destroys the entity
	 */
	public void destroy(){
		
	}
	
	/**
	 * Paints the Missile
	 */
	@Override
	public void paint(Graphics pane) {
		pane.setColor(color.blue);
		pane.fillRect(x, y, width, height);
	}
	
	/**
	 * Fire does nothing, returns null
	 */
	public Entity fire(){
		return null;
	}
	
}
