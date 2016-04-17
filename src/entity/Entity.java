package entity;

import java.awt.Graphics;
import java.awt.Shape;
import shapes.AShape;

/**
 *  The <code>Entity</code> class is used to draw the Entity suite on a card.
 *  
 *  @Author Michael Twardowski
 */
public abstract class Entity extends AShape{
	
	/**
	 * The shape of AShape
	 */
	protected Shape shape;
	
	/**
	 * Is the entity alive
	 */
	protected boolean alive;
	
	/**
	 * Number of lives the entity has 
	 */
	protected int lives;
	
	/**
	 * Default Constructor
	 */
	public Entity(){
		super();
		alive = true;
		lives = 1;
	}
	
	/**
	 * Sets all of the properties
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public Entity(int x, int y ,int width, int height){
		super(x, y, width, height);
		alive = true;
		lives = 1;
	}
	
	/**
	 * Moves the entity
	 */
	public void move(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Checks to see if the entity is alive.
	 */
	public boolean isAlive(){
		return alive;
	}
	
	/**
	 * Sets the location and dimensions
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	@Override
	public void setup(int x, int y, int width, int height){
		super.setup(x, y, width, height);
		alive = true;
	}
	
	/**
	 * True if the point whose x and y are given is within the button
	 * @param pointX
	 * @param pointY
	 * @return
	 */
	public boolean isInside(int pointX, int pointY){
		return shape.contains(pointX,pointY);
	}
	
	/**
	 * Sets the Entity's shape
	 */
	public abstract void setShape();
	
	/**
	 * Destroys the entity
	 */
	public abstract void destroy();
	
	/**
	 * Draws an entity;
	 */
	@Override
	public abstract void paint(Graphics pane);
}