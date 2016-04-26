package entity;

import java.awt.Graphics;

/**
*  The <code>Ship</code> class is used to create game objects that can move, 
*  detect collisions with other objects, fire <code>Missile</code>'s to destroy
*  <code>AlienShip</code>'s, and be destroyed by <code>Missile</code>s fired by
*  <code>AlienShip</code>s.
*/ 
public class Ship extends Entity {
	
	/**
	 * All <code>Missile</code>s that have been fired by a <code>Ship</code>
	 * but not yet destroyed.
	 */
	protected Missile missilesFired[] = new Missile[100];
	
	/**
	 * 
	 */
	protected int missileCount = 0;
	
	/**
	 * Determines how often a <code>Ship</code> could fire a <code>Missile</code>
	 */
	protected static final int MAX_FIRE_RATE = 1;
	
	/**
	 * The speed of the <code>Ship</code> is the number of pixels
	 * it moves each time <code>move</code> is called.
	 */
	protected static final int SPEED = 1;
		
	/**
	 * Default constructor for a <code>Ship</code>
	 */
	public Ship() {
		// TODO Auto-generated constructor stub
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
	}

	/**
	 * Fires a <code>Missile</code> from the <code>Ship</code> in the upward direction
	 */
	public void fire() {
			System.out.println("Fire!");
		 missilesFired[missileCount] = new Missile(x + width/2, y - height, true);
		 missileCount++;
	}
	
	/**
	 * Sets the shape of the <code>Ship</code>.
	 */
	@Override
	public void setShape() {
		// TODO Auto-generated method stub

	}
	
	/**
	 * Moves the <code>Ship</code> left.
	 */
	public void moveLeft(){
		
	}
	
	/**
	 * Moves the <code>Ship</code> right.
	 */
	public void moveRight(){
		
	}
	
	/**
	 * Moves the <code>Ship</code> along its trajectory.
	 */
	public void move(){
	
	}
	
	/**
	 * Destroys the <code>Ship</code>
	 */
	public void destroy(){
		
	}
	
	/**
	 * Sets the <code>Ship</code>s lives to a integer
	 * @param lives 
	 */
	public void setLives(int lives){
		this.lives = lives;
	}
	
	/**
	 * Returns the number of lives a <code>Ship</code> has.
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
		pane.setColor(color.BLUE);
		pane.fillRect(x, y, width, height);
		for(int i = 0; i < missileCount; i++){
			missilesFired[i].paint(pane);
		}
	}
	
}
