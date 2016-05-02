package entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
*  The <code>AlienShip</code> class is used to create objects that move,
*  can increase in speed detect collisions with other objects, fire <code>Missile</code>'s to destroy
*  <code>Ship</code>'s, and be destroyed by <code>Missile</code>s fired by
*  <code>Ship</code>s.
*  
*   @author Michael Twardowski
*/ 
public class AlienShip extends Ship {
	
	/**
	 * Multiply's the speed of the <code>AlienShip</code>s as more are destroyed
	 */
	private static int speedMultiplier = 1,
					   velocityX = 1,
					   velocityY = 5;
	
	/**
	 * Is alien moving down?
	 */
	private static boolean verticalMovement = false;
	
	/**
	 * Default constructor for a <code>AlienShip</code>
	 */
	public AlienShip() {
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
		setColor(Color.GREEN);
	}
	
	/**
	 * Sets speed of all <code>AlienShip</code>s.
	 */
	public static void setSpeedMultiplier(int speedMultiplier) {
		AlienShip.speedMultiplier = speedMultiplier;
	}
	
	/**
	 * Returns the speed of all <code>AlienShip</code>s.
	 */
	public static int getSpeed() {
		return velocityX*speedMultiplier;
	}
	
	/**
	 * Change Direction of all <code>AlienShip</code>s.
	 */
	public static void changeDirection() {
		velocityX = - velocityX;
	}
	
	/**
	 * Enables or Disables vertical movement of all <code>AlienShip</code>s.
	 * @param verticalMovement true if there is movement in the Y dimenion
	 */
	public static void setVerticalMovement(boolean verticalMovement) {
		AlienShip.verticalMovement = verticalMovement;
	}
	
	/**
	 * Fires a <code>Missile</code> from a <code>AlienShip</code> in the
	 * downward direction
	 */
	@Override
	public Missile fire() {
		int speed = 10; 
		Missile missile = new Missile(x + width/2, y, speed);
		missile.setColor(Color.GREEN);
		return missile;
	}

	/**
	 * Moves the <code>AlienShip</code> along its trajectory
	 */
	@Override
	public void move(){
		x =  x + velocityX*speedMultiplier;
		if(verticalMovement){
			y = y + velocityY*speedMultiplier;
		}
		setShape();
	}
	
	/**
	 * Paints the <code>AlienShip</code>
	 */
	@Override
	public void paint(Graphics pane) {
		pane.setColor(color);
		pane.fillRect(x, y, width, height);
	}
	
	/**
	 * Sets the shape of the <code>AlienShip</code>.
	 */
	@Override
	public void setShape() {
		shape = new Rectangle(x, y, width, height);
	}
}
