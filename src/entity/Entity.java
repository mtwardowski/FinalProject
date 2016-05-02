package entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Shape;

/**
 *  The <code>Entity</code> class is used to create objects with different
 *  shapes that can move, detect collisions with other objects.
 *  
 *  @author Michael Twardowski
 */
public abstract class Entity{
	
	/**
	 * The location, dimensions of <code>Entity</code>
	 */
	protected int x, y, width, height;
	
	/**
	 * The shape of <code>Entity</code>.
	 */
	protected Shape shape;
	
	/**
	 * Its the fill color of <code>Entity</code>.
	 */
	protected Color color;
	
	/**
	 * Number of lives an <code>Entity</code>.
	 */
	protected int lives;
	
	/**
	 * Keeps track of last fired missile
	 */
	protected long lastShotTime = 0;
	
	/**
	 * Controls fire rate of the entity, in mS
	 */
	protected int fireRate;
	
	/**
	 * Default Constructor for <code>Entity</code>.
	 */
	public Entity(){
		super();
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
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		lives = 1;
		setShape();
	}
	
	
	/**
	 * Sets all of the properties of <code>Entity</code>.
	 * @param x the x location
	 * @param y the y location
	 * @param width of the <code>Entity</code>
	 * @param height of the <code>Entity</code>
	 */
	public void setup(int x, int y, int width, int height){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		lives = 1;
		setShape();
	}
	
	/**
	 * Sets the fill color
	 * @param color
	 */
	public void setColor(Color color){
		this.color = color;
	}
	
	/**
	 * True if the point whose x and y are given is within the entity
	 * @param entity to be checked
	 * @return boolean true if the given entity is inside this one
	 */
	public boolean isInside(Entity entity){
		return shape.contains(entity.x, entity.y);
	}
	
	/**
	 * Returns the <code>Entity</code>'s shape
	 * @return shape of the Entity
	 */
	public Shape getShape(){
		return shape;
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
	 * Draws an <code>Entity</code>.
	 * @param pane a graphics object
	 */
	public abstract void paint(Graphics pane);
}