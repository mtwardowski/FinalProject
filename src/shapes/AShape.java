package shapes;

import java.awt.Color;
import java.awt.Graphics;

/**
 *  The <code>AShape</code> abstract class can be used to create shapes.
 *  
 *  @Author Michael Twardowski
 */
public abstract class AShape {
	
		/**
		 * The location, dimensions of <code>AShape</code>
		 */
		protected int x, y, width, height;
		
		/**
		 * Its the fill color of  <code>AShape</code>.
		 */
		protected Color color;
		
		/**
		 * Default Constructor of <code>AShape</code>.
		 */
		public AShape(){
		}
		
		/**
		 * Sets all of the properties of <code>AShape</code>.
		 * @param x the x location
		 * @param y the y location
		 * @param width of the <code>AShape</code>
		 * @param height of the <code>AShape</code>
		 */
		public AShape(int x, int y, int width, int height){
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
		}

		/**
		 * Sets the location and dimensions of <code>AShape</code>.
		 * @param x the x location
		 * @param y the y location
		 * @param width the width of the shape
		 * @param height the height of the shape
		 */
		public void setup(int x, int y, int width, int height){
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
		}
		
		/**
		 * Sets the fill color
		 * @param color
		 */
		public void setColor(Color color){
			this.color = color;
		}
		
		/**
		 *  Paints <code>AShape</code>.
		 */
		public abstract void paint(Graphics pane);
		
}