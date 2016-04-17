package shapes;

import java.awt.Color;
import java.awt.Graphics;

/**
 *  The <code>Rectangle</code> class is used to draw rectangles
 *  
 *  @Author Michael Twardowski
 */
public class Rectangle extends AShape{
	
	/**
	 * Default Constructor.
	 */
	public Rectangle(){
		super();
	}//end default constructor
	
	/**
	 * Sets all of the properties
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public Rectangle(int x, int y, int width, int height){
		super(x, y, width, height);
	}
	
	/**
	 * draws a rectangle, filled with the given color
	 * @param pane
	 */
	@Override
	public void paint(Graphics pane) {
		pane.setColor(color);
		pane.fillRect(x, y, width, height);
		pane.setColor(Color.BLACK);
		pane.drawRect(x, y, width, height);
	}
}