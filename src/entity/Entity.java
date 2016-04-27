package entity;

import java.awt.Graphics;
import java.awt.Shape;
import shapes.AShape;

/**
 *  The <code>Entity</code> class is used to create objects with different
 *  shapes that can move, detect collisions with other objects, and be destroyed.
 *  
 *  @Author Michael Twardowski
 */
public abstract class Entity extends AShape{
	
	/**
	 * The shape of <code>Entity</code>.
	 */
	protected Shape shape;
	
	/**
	 * Is the <code>Entity</code> alive
	 */
	protected boolean alive;
	
	/**
	 * Number of lives an <code>Entity</code>.
	 */
	protected int lives;
	
	/**
	 * Default Constructor for <code>Entity</code>.
	 */
	public Entity(){
		super();
		alive = true;
		lives = 1;
	}
	
	/**
	 * Sets all of the properties of <code>Entity</code>.
	 * @param x the x location
	 * @param y the y location
	 * @param width of the <code>Entity</code>
	 * @param height of the <code>Entity</code>
	 */
	public Entity(int x, int y ,int width, int height){
		super(x, y, width, height);
		alive = true;
		lives = 1;
	}
	
	/**
	 * Checks to see if the <code>Entity</code> is alive.
	 */
	public boolean isAlive(){
		return alive;
	}
	
	/**
	 * Sets all of the properties of <code>Entity</code>.
	 * @param x the x location
	 * @param y the y location
	 * @param width of the <code>Entity</code>
	 * @param height of the <code>Entity</code>
	 */
	@Override
	public void setup(int x, int y, int width, int height){
		super.setup(x, y, width, height);
		alive = true;
	}
	
	/**
	 * True if the point whose x and y are given is within the button
	 * @param x point to check
	 * @param y point to check
	 * @return
	 */
	public boolean isInside(int x, int y){
		return shape.contains(x, y);
	}
	
	/**
	 * The entity fires an object
	 */
	public abstract Entity fire();
	
	/**
	 * Sets the <code>Entity</code>'s shape
	 */
	public abstract void setShape();
	
	/**
	 * Moves the <code>Entity</code>.
	 */
	public abstract void move();
	
	/**
	 * Destroys the <code>Entity</code>.
	 */
	public abstract void destroy();
	
	/**
	 * Draws an <code>Entity</code>.
	 */
	@Override
	public abstract void paint(Graphics pane);
}