import button.AButton;
import button.RegularButton;
import java.awt.Frame;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Color;
import java.awt.Graphics;

/**
*							  	SpaceInvaders Class
*						=============================
*	The <code>SpaceInvaders</code> Class extends the <code>Frame</code> Class.
*	<p>

*
*	@Author Michael Twardowski
*/
public class SpaceInvaders extends Frame implements MouseListener {
	
	/**
	 * Buttons to respond to mouse clicks
	 */
	private AButton[] buttons = new AButton[2];
	

	/**
	 * To window size of the size of the window
	 */
	private int windowHeight, windowWidth;
	
	/**
	 * Handle for the <code>SpaceInvaders</code> window.
	 */
	private CloseWindow myWindow;

	/**
	 * 
	 */
	private boolean initializationComplete = false;

	/**
	 * Instantiates a <code>SpaceInvaders</code> on which to play
	 */
	public static void main(String[] args) 
	{
		SpaceInvaders spaceInvaders = new SpaceInvaders();
		
		/*
		//useful for testing
		for(int i=0; i<50; i++){
			// delays playing turn
			myGameTable.playTurn();
			try {
			    Thread.sleep(1000);  //1000 milliseconds is one second.
			} catch(InterruptedException ex) {
			    //Thread.currentThread().interrupt();
			}
		}
		*/
	
	}

	/**
	 * The default constructor for the <code>SpaceInvaders</code>. 
	 */
	public SpaceInvaders() 
	{
		final int 	BUTTON_WIDTH = 100, // defines size of buttons
					BUTTON_HEIGHT = 40,
					BUTTON_X = 240,  // locate of first button
					BUTTON_Y = 600,
					SPACE = 10;  // space between buttons

		setTitle("Space Invaders");		//name, location and size of our frame
		setLocation(150,150);	

		windowWidth = 800;	//can be resized, but starts out reasonably large
		windowHeight = 800;
		setSize(windowWidth,windowHeight);

		/*
		 * Sets up two buttons to control the game
		 */
		buttons[0] = new RegularButton("Start New Game", Color.red, BUTTON_X, BUTTON_Y, 
				BUTTON_WIDTH, BUTTON_HEIGHT);

		buttons[1] = new RegularButton("Quit", Color.cyan, BUTTON_X + (BUTTON_WIDTH +
				SPACE)*2, BUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT);
		
		myWindow = new CloseWindow(); // allows for window closing
		addWindowListener(myWindow);
		
		addMouseListener(this); //detects mouse events in the frame
		
		initializationComplete = true; // the game setup is complete
		
		setVisible(true);
	} // end default constructor

	/**
	 * The <code>paint</code> method will paint each <code>Card</code> on <code>SpaceInvaders</code>
	 * after then have been initialized.
	 */
	@Override
	public void paint(Graphics pane){
		if(initializationComplete){
			
			//paint our buttons
			for(AButton button: buttons){
				button.paint(pane);		
			}
		}
	}

	/**
	 * If the mouse click is in the Start Game Button, a turn will be played.  If it's 
	 * within the reset button, the game resets to the beginning. If it's within
	 * the quit button, the game window will close.
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();

		if(buttons[0].isInside(x, y)){			// Start New Game
			repaint();
		}else if(buttons[1].isInside(x, y)){	// Quit
			repaint();
		}
	}

	/**
	 * Flips the button that has been pressed
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();

		for(AButton b:buttons){
			if(b.isInside(x, y)){
				b.flip();
				repaint();
			}
		}

	}

	/**
	 * Flips the button that has been released
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();

		for(AButton b:buttons){
			if(b.isInside(x, y)){
				b.flip();
				repaint();
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}
}
