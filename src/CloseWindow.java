import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
*	The <code>CloseWindow</code> Class extends the <code>WindowAdapter</code> Class to be 
*	able to close a window when its exit button is pressed.
*	<p>
*	The <code>windowClosing</code> method closes a window when its exit button is pressed. 
*	
*	@Author Michael Twardowski
*/
public class CloseWindow extends WindowAdapter {
	
	/**
	 * The <code>windowClosing</code> method closes a window when its exit button is pressed. 
	 */
	public void windowClosing(WindowEvent event)
	{
		System.exit(0);
	}
}
