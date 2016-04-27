package entity;

import java.awt.Graphics;

/**
*  The <code>AlienShip</code> class is used to create objects that move,
*  can increase in speed detect collisions with other objects, fire <code>Missile</code>'s to destroy
*  <code>Ship</code>'s, and be destroyed by <code>Missile</code>s fired by
*  <code>Ship</code>s.
*/ 
public class AlienShip extends Ship {
	
	/**
	 * Multiply's the speed of the <code>AlienShip</code>s as more are destroyed
	 */
	private static int speedMultiplier,
					   speed = 0;
	
	/**
	 * Default constructor for a <code>AlienShip</code>
	 */
	public AlienShip() {
		// TODO Auto-generated constructor stub
		speedMultiplier = 1;
	}
	
	/**
	 * Sets all of the properties of <code>AlienShip</code>.
	 * @param x the x location
	 * @param y the y location
	 * @param width of the <code>Entity</code>
	 * @param height of the <code>Entity</code>
	 */
	public AlienShip(int x, int y, int width, int height) {
		super(x, y, width, height);
		speedMultiplier = 1;
	}
	
	/**
	 * Increases speed of all <code>AlienShip</code>s.
	 */
	public static void increaseSpeed() {
		// TODO Auto-generated method stub

	}
	
	/**
	 * Fires a <code>Missile</code> from the <code>AlienShip</code> in the
	 * downward direction
	 */
	@Override
	public Missile fire() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Destroys the <code>AlienShip</code>.
	 */
	public void destroy(){
		
	}

	/**
	 * Moves the <code>AlienShip</code> along its trajectory
	 */
	@Override
	public void move(){
		int rowHeight = 1;
		x =  x + speed*speedMultiplier;
		//this.y = this.y - rowHeight;
	}
	
	/**
	 * Paints the <code>AlienShip</code>
	 */
	@Override
	public void paint(Graphics pane) {
		pane.setColor(color.black);
		pane.fillRect(x, y, width, height);
	}
	
}
