import button.AButton;
import button.RegularButton;
import entity.AlienShip;
import entity.Ship;

import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;

/**
*							  	SpaceInvaders Class
*						=============================
*	The <code>SpaceInvaders</code> Class extends the <code>Frame</code> Class.
*	<p>
*	It creates a game of Space Invaders where multiple <code>AlienShip</code>s
*	will descend down towards earth where a sole defending <code>Ships</code>
*	awaits. They can destroy each other by firing <code>Missile</code>s. 
*	The game ends when either:
*		1. All <code>AlienShip</code>s have been destroyed.
*		2. The <code>Ship</code> has been destroyed.
*		3. Any <code>AlienShip</code> lands on the earth.
*
*	@Author Michael Twardowski
*/
public class SpaceInvaders extends Frame implements MouseListener, KeyListener {
	
	/**
	 * Buttons to respond to mouse clicks
	 */
	private AButton[] buttons = new AButton[2];
	
	/**
	 * To window size of the size of the window
	 */
	private int windowHeight, windowWidth;
	
	/**
	 * Keeps track of the insets of the game to use for collision detection
	 */
	private Insets insets;
	
	/**
	 * Handle for the <code>SpaceInvaders</code> window.
	 */
	private CloseWindow myWindow;
	
	/**
	 * <code>Ship</code> for the user to control
	 */
	private Ship defender;
	
	/**
	 * Holds rectangles that are painted to indicate the number of lives a
	 *  <code>Ship</code> has
	 */
	private Rectangle[] lives;
	
	/**
	 * <code>AlienShip</code>s controlled by <code>SpaceInvaders</code>
	 */
	private AlienShip[][] alienShips = new AlienShip[5][12];

	/**
	 * Prevents objects from being painted until the game setup is complete.
	 */
	private boolean isInitializationComplete = false,
					isGameStarted = false;
	
	/**
	 * Keeps track of the game score
	 */
	private int gameScore = 0;

	/**
	 * The default constructor for the <code>SpaceInvaders</code>. 
	 */
	public SpaceInvaders() 
	{
		final int 	BUTTON_WIDTH = 100, // defines size of buttons
					BUTTON_HEIGHT = 40,
					BUTTON_X = 50,  // locate of first button
					BUTTON_Y = 50,
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
				SPACE), BUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT);
		
		myWindow = new CloseWindow(); // allows for window closing
		addWindowListener(myWindow);
		
		addMouseListener(this); //detects mouse events in the frame
		addKeyListener(this);  // detects key vents in the frame;
		
		//Our heroic defending ship
		defender = new Ship(375, 725, 50, 25);
		
		isInitializationComplete = true; // the game setup is complete
		
		setVisible(true);
		
		insets = getInsets();
		
	} // end default constructor
	
	/**
	 * Instantiates a <code>SpaceInvaders</code> on which to play and controls
	 */
	public static void main(String[] args) 
	{
		SpaceInvaders spaceInvaders = new SpaceInvaders();
	}
	
	/**
	 * Starts a new game 
	 */
	private void startNewGame(){
		/*
		 * Adds Alien Ships
		 */
		
		int rowsOfShips = 5,
			columnsOfShips = 12,
			initialX = 100,
			initialY = 150,
			spacing = 50;
		
		//TODO: remove later
		int alienWidth = 25,
			alienHeight = 25;
		
		//alienShips = new AlienShip[rowsOfShips][columnsOfShips];
		for(int i = 0; i < rowsOfShips; i++){
			for(int j = 0; j < columnsOfShips; j++){
				alienShips[i][j] = new AlienShip(initialX + spacing*j, 
								initialY + spacing*i , alienWidth, alienHeight);
			}
		}
		
		//Our heroic defending ship
		defender = new Ship(375, 725, 50, 25);
		
		isGameStarted = true;
	}
	
	/**
	 * Updates the game score
	 */
	private void updateScore(){
		
	}
	
	/**
	 * Moves all of the <code>AlienShips</code> that are alive.
	 */
	private void moveAlienShips(){
		
	}

	/**
	 * The <code>paint</code> method will paint each button for
	 * the <code>SpaceInvaders</code> game and the game objects.
	 */
	@Override
	public void paint(Graphics pane){
		if(isInitializationComplete){
			
			//paint our buttons
			for(AButton button: buttons){
				button.paint(pane);		
			}
			
			// paints our heroic defender
			defender.paint(pane);
			
			if(isGameStarted){
				// paints alien ships
				for (AlienShip[] aliens: alienShips){
					for(AlienShip alien: aliens){
						alien.paint(pane);
					}
				}
			}
		}
	}

	/**
	 * If the mouse click is in the Start New Game Button, the game will reset
	 * and start a new game.If it's within the quit button, the game window will
	 * close.
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();

		if(buttons[0].isInside(x, y)){			// Start New Game
			startNewGame();
			repaint();
		}else if(buttons[1].isInside(x, y)){	// Quit
			System.exit(0);
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
	
	/**
	 * Listens for keystrokes to move the ship and to fire missiles.
	 */
	@Override
	public void keyPressed(KeyEvent key) {
		int pressed = key.getKeyCode();
		if(pressed == key.VK_SPACE){
			defender.fire();
			repaint();
		}else if ((pressed == key.VK_LEFT) |(pressed == key.VK_A)){
			System.out.println("Left");
		}else if ((pressed == key.VK_RIGHT)|(pressed == key.VK_D)){
			System.out.println("Right");
		}
	}
	
	/**
	 * Not used
	 */
	@Override
	public void mouseEntered(MouseEvent e) {}

	/**
	 * Not used
	 */
	@Override
	public void mouseExited(MouseEvent e) {}
	
	/**
	 * Not used
	 */
	@Override
	public void keyTyped(KeyEvent arg0) {}
	
	/**
	 * Not used
	 */
	@Override
	public void keyReleased(KeyEvent arg0) {}

}