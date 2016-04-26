package button;

import java.awt.*;

/**
 *  The <code>RegularButton</code> class can be used to create interactive buttons
 *  of a rectangular shape.
 */
public class RegularButton extends AButton {
	
	/**
	 * Default constructor automatically calls default constructor of parent
	 */
	public RegularButton(){}
	
	/**
	 * This constructor lets the parent class set the attributes, except the shape
	 * @param someLabel the button label
	 * @param someColor the button color
	 * @param someX the x location
	 * @param someY the y location
	 * @param someWidth the width of a button
	 * @param someHeight the height of a button
	 */
	public RegularButton(String someLabel,
				   Color someColor,
				   int someX, int someY,
				   int someWidth, int someHeight)
	{										//	We create a button
		super(someLabel,					//		with a given label,
			  someColor,					//		in a given color
			  someX, someY,					//		at a given location,
			  someWidth, someHeight);		//		and with a given size,
		setShape();
	}
	
	/**
	 * A regular button is rectangle
	 */
	public void setShape(){
		shape = new Rectangle(x, y, width, height);
	}
}