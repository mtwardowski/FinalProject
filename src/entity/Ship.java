package entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
*  The <code>Ship</code> class is used to create game objects that can move, 
*  detect collisions with other objects, fire <code>Missile</code>'s to destroy
*  <code>AlienShip</code>'s, and be destroyed by <code>Missile</code>s fired by
*  <code>AlienShip</code>s.
*  
*   @author Michael Twardowski
*/ 
public class Ship extends Entity {
	
	/**
	 * The speed of the <code>Ship</code> is the number of pixels
	 * it moves each time <code>move</code> is called.
	 */
	protected static final int SPEED = 25;
		
	/**
	 * Default constructor for a <code>Ship</code>
	 */
	public Ship() {
	}
	
	/**
	 * Returns the speed of all Ships
	 * @return SPEED the speed of all Ships
	 */
	public static int getSpeed() {
		return SPEED;
	}

	/**
	 * Sets all of the properties of <code>Ship</code>.
	 * @param x the x location
	 * @param y the y location
	 * @param width of the <code>Entity</code>
	 * @param height of the <code>Entity</code>
	 */
	public Ship(int x, int y, int width, int height) {
		super(x, y, width, height);
		fireRate = 300;
		setColor(Color.blue);
	}

	/**
	 * Fires a <code>Missile</code> from the <code>Ship</code> in the upward direction
	 * @return a missile (returns null if it has been too short of time between
	 *  firing attempts)
	 */
	public Missile fire() {
	
		// check that we have waiting long enough to fire
		if (System.currentTimeMillis() - lastShotTime > fireRate) {
			lastShotTime = System.currentTimeMillis();
			int speed = -15; 
			Missile missile = new Missile(x + width/2, y, speed);
			missile.setColor(Color.BLUE);
			return missile;
		}else{
			return null;
		}
	}
	
	/**
	 * Sets the shape of the <code>Ship</code>.
	 */
	@Override
	public void setShape() {
		shape = new Rectangle(x, y, width, height);
	}
	
	/**
	 * Moves the <code>Ship</code> left.
	 */
	public void moveLeft(){
		x = x - SPEED;
		setShape();
	}
	
	/**
	 * Moves the <code>Ship</code> right.
	 */
	public void moveRight(){
		x = x + SPEED;
		setShape();
	}

	/**
	 * Move the ship randomly
	 */
	public void move(){
		// Nothing done here.
	}
	
	/**
	 * Sets the <code>Ship</code>s lives to a integer
	 * @param lives number of lives to set too
	 */
	public void setLives(int lives){
		this.lives = lives;
	}
	
	/**
	 * Returns the number of lives a <code>Ship</code> has.
	 * @return lives the number of lives remaining
	 */
	public int getLives(){
		return lives;
	}
	
	/**
	 * Reduces the <code>Ship</code>s lives by one. 
	 */
	public void loseALife(){
		int life = 1;
		setLives(lives - life);
	}
	
	/**
	 * Increase the <code>Ship</code>s lives by one.
	 */
	public void gainALife(){
		int life = 1;
		setLives(lives + life);
	}
	
	/**
	 * Paints the <code>Ship</code>
	 */
	@Override
	public void paint(Graphics pane) {
		pane.setColor(color);
		pane.fillRect(x, y, width, height);
	}
}
