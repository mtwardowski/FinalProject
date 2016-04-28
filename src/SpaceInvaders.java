import button.AButton;
import button.RegularButton;
import entity.AlienShip;
import entity.Entity;
import entity.Ship;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Toolkit;

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
public class SpaceInvaders extends Canvas implements MouseListener, KeyListener,
AlarmListener{
	
	/**
	 * Allows smooth animation
	 */
	private BufferStrategy buffer;
	
	/**
	 * The thread that all the ships.
	 */
	private Alarm timer;
	
	/**
	 * Buttons to respond to mouse clicks
	 */
	private AButton button;
	
	/**
	 * To window size of the size of the window
	 */
	private int windowHeight, windowWidth;
	
	/**
	 * Handle for the <code>SpaceInvaders</code> window.
	 */
	private CloseWindow myWindow;
	
	/**
	 * Array to keep track of everything that isn't destroyed
	 */
	private ArrayList entities = new ArrayList();
	private ArrayList missiles = new ArrayList();
	
	/**
	 * ArrayList to keep track of any <code>Entity</code> that is to be destroy
	 * when the next frame is painted.
	 */
	private ArrayList entitiesToBeDestroyed = new ArrayList();
	private ArrayList missilesToBeDestroyed = new ArrayList();
	
	/**
	 * <code>Ship</code> for the user to control
	 */
	private Ship defender;
	
	/**
	 * Number of <code>alienShips</code> that are alive
	 */
	private int alienShipCount = 0;
	
	/**
	 * Holds rectangles that are painted to indicate the number of lives a
	 *  <code>Ship</code> has
	 */
	private Rectangle[] lives;

	/**
	 * Controls animation.
	 */
	private boolean isGameRunning = false,
					nextTime = false;
	
	/**
	 * Keeps track of the game score
	 */
	private int gameScore;
	
	/**
	 * gameScore Location
	 */
	private int gameScoreX,
				gameScoreY;

	/**
	 * The default constructor for the <code>SpaceInvaders</code>. 
	 */
	public SpaceInvaders() 
	{
		final int 	BUTTON_WIDTH = 100, // defines size of buttons
					BUTTON_HEIGHT = 40,
					BUTTON_X = 50,  // locate of first button
					BUTTON_Y = 50;
		
		//Creates a Frame that will hold the game canvas
		Frame window =  new Frame("Space Invaders");

		//setting the size and location of the window based on the screen
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		windowWidth = (int)(screenSize.width * .9);
		windowHeight = (int)(screenSize.height * .9);
		window.setPreferredSize(new Dimension(windowWidth, windowHeight));
		int winX = (screenSize.width - windowWidth)/2;
		int winY = (screenSize.height - windowHeight)/2;
		window.setLocation(winX, winY);
		
		//makes sure the canvas fills the Frame
		setBounds(0,0,windowWidth, windowHeight);
		
		//makes our Canvas the component held in the Frame
		window.add(this);
		
		//starts out painting normally - painting will be taken over
		//by the rise() method when the animation is running
		setIgnoreRepaint(false);
		
		//sets everything up to be visible
		window.pack();
		window.setResizable(false);
		window.setVisible(true);
		
		/*
		 * Sets up a button to control the game
		 */
		button = new RegularButton("Start New Game", Color.red, BUTTON_X, BUTTON_Y, 
				BUTTON_WIDTH, BUTTON_HEIGHT);
		
		/*
		 * Sets up label location
		 */
		gameScoreX = windowWidth - 150;
		gameScoreY = 75;
		
		myWindow = new CloseWindow(); // allows for window closing
		window.addWindowListener(myWindow);
		
		addMouseListener(this); //detects mouse events in the frame
		addKeyListener(this);  // detects key vents in the frame;

		//To make the balloon rise (or fall)
		timer = new Alarm(this);
		timer.setPeriod(50);//20 frames per second
		timer.start();
				
		/* // the following two lines do the magic of key binding. the first line
		        // gets the dataField's InputMap and pairs the "ENTER" key to the
		        // action "doEnterAction" . . .
		        window.getInputMap().put( KeyStroke.getKeyStroke( "ENTER" ),
		                "doEnterAction" );
		        // . . . then this line pairs the AbstractAction enterAction to the
		        // action "doEnterAction"
		        window.getActionMap().put( "doEnterAction", enterAction );
*/
		
		//necessary for double-buffering
		createBufferStrategy(2);	
		buffer = getBufferStrategy();
		
	} // end default constructor
	
	/**
	 * Instantiates a <code>SpaceInvaders</code> on which to play and controls
	 */
	public static void main(String[] args) 
	{
		SpaceInvaders spaceInvaders = new SpaceInvaders();
	}
	
	/**
	 * The <code>paint</code> method will paint each button for
	 * the <code>SpaceInvaders</code> game and the game objects.
	 * Will only appear if the game animation isn't running
	 */
	@Override
	public void paint(Graphics pane){
		//paint our button
		button.paint(pane);		
	}

	/**
	 * The <code>paintFrame</code> method will paint the animation for
	 * the <code>SpaceInvaders</code> game..
	 */
	public void paintFrame(){
		//This is where the Graphics comes from
		Graphics pane = buffer.getDrawGraphics();
		
		//Redraw the background every time
		pane.setColor(Color.lightGray);
		pane.fillRect(0,0,windowWidth,windowHeight); //To cover up old paint.
		
		//paint our button
		button.paint(pane);		
		
		// paints our heroic defender
		defender.paint(pane);
		
		// paints all entities that haven't been destroyed
		for (int i = 0; i < entities.size(); i++ ){
				Entity entity = (Entity) entities.get(i);
				entity.paint(pane);
		}
		
		// paints all missiles that haven't been destroyed
		for (int i = 0; i < missiles.size(); i++ ){
				Entity missile = (Entity) missiles.get(i);
				missile.paint(pane);
		}
		
		Graphics2D pane2 = (Graphics2D)pane;
		pane2.setColor(Color.black);
		Font currentFont = pane2.getFont();
		Font newFont = currentFont.deriveFont(currentFont.getSize() * 3F);
		pane2.setFont(newFont);
		pane2.drawString("" + gameScore, gameScoreX, gameScoreY);
		
		//Necessary commands for displaying what you've painted
		pane.dispose();	//you don't need the Graphics any more
		buffer.show();	//what you've drawn is in the buffer
	}
	
	/**
	 * The animation is now controlled by the timer, 
	 * the movement occurs within takeNotice(), as
	 * long as the animation is actually running.
	 */
	public void takeNotice() {
		//Only paint animation is running and *** visible
		if(isGameRunning && !nextTime){
			//First, paint the balloon
			paintFrame();
			
			boolean directionChange = false;
			int speed = AlienShip.getSpeed();
			
			if(speed < 0){
				// moves all entities that haven't been destroyed
				for (int i = 0; i < entities.size(); i++ ){
					Entity entity = (Entity) entities.get(i);
					Rectangle rect = entity.getShape().getBounds();
					
					if((rect.x + speed <= 0 | (rect.x + rect.width + speed> windowWidth))
						& !directionChange){
						AlienShip.changeDirection();
						directionChange = true;
					}
					entity.move();
				}
			}else{
				// moves all entities that haven't been destroyed
				for (int i = entities.size() -1; i > -1; i-- ){
					Entity entity = (Entity) entities.get(i);
					Rectangle rect = entity.getShape().getBounds();
					
					if((rect.x + speed <= 0 | (rect.x + rect.width + speed> windowWidth))
						& !directionChange){
						AlienShip.changeDirection();
						directionChange = true;
					}
					entity.move();
				}
			}
			
			// moves all missiles
			for (int i = 0; i < missiles.size(); i++ ){
				Entity missile = (Entity) missiles.get(i);
				missile.move();
			}
			
			// moves all entities that haven't been destroyed
			for (int i = 0; i < missiles.size(); i++ ){
				Entity missile = (Entity) missiles.get(i);
				for(int j = 0 ; j < entities.size(); j++){
					Entity ship = (Entity) entities.get(j);
					if(ship.isInside(missile)){
						entitiesToBeDestroyed.add(ship);
						missilesToBeDestroyed.add(missile);
						gameScore = gameScore + 100;
					}
				} 
			}
			
			//removes entities to be destroyed and clears entitiesToBeDestroyed 
			entities.removeAll(entitiesToBeDestroyed);
			missiles.removeAll(missilesToBeDestroyed);
			entitiesToBeDestroyed.clear();
			missilesToBeDestroyed.clear();
		}
		//stop the animation
		else if(nextTime){
			//These reset it to show the initial screen
			nextTime = false;
			isGameRunning = false;
			setIgnoreRepaint(false);
			repaint();
		}
	}
	
	/**
	 * Starts a new game 
	 */
	private void startNewGame(){
		
		entities.clear();
		missiles.clear();
		alienShipCount = 0;
		gameScore = 0;
		
		//Our heroic defending ship
		defender = new Ship((windowWidth - 50)/2, 725, 50, 25);
		entities.add(defender);
		
		/*
		 * Adds Alien Ships
		 */
		
		int rowsOfShips = 5,
			columnsOfShips = 12,
			initialX = windowWidth/columnsOfShips,
			initialY = 150,
			spacingX = 100,
			spacingY = spacingX/2;
		
		//TODO: remove later
		int alienWidth = 25,
			alienHeight = 25;
		
		//alienShips = new AlienShip[rowsOfShips][columnsOfShips];
		for(int i = 0; i < rowsOfShips; i++){
			for(int j = 0; j < columnsOfShips; j++){
				Entity alienShip = new AlienShip(initialX + spacingX*j, 
								initialY + spacingY*i , alienWidth, alienHeight);
				entities.add(alienShip);
				alienShipCount++;
			}
		}
		isGameRunning = true;
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
	 * If the mouse click is in the Start New Game Button, the game will reset
	 * and start a new game.If it's within the quit button, the game window will
	 * close.
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();

		if(button.isInside(x, y)){			// Start New Game
			setIgnoreRepaint(true);
			startNewGame();
		}
	}

	/**
	 * Flips the button that has been pressed
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();

		if(button.isInside(x, y)){
			button.flip();
			repaint();
		}
	}

	/**
	 * Flips the button that has been released
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		
		if(button.isInside(x, y)){
			button.flip();
			repaint();
			}
	}	
	
	/**
	 * Listens for keystrokes to move the ship and to fire missiles.
	 */
	@Override
	public void keyPressed(KeyEvent key) {
		int pressed = key.getKeyCode();
		if(pressed == key.VK_SPACE){
			Entity missile = defender.fire();
			if(missile != null){
				missiles.add(missile);
			}
		}else if ((pressed == key.VK_LEFT) |(pressed == key.VK_A)){
			int speed = defender.getSpeed();
			Rectangle ship = defender.getShape().getBounds();
			if(ship.x - speed > 0 ){
				defender.moveLeft();
			}
		}else if ((pressed == key.VK_RIGHT)|(pressed == key.VK_D)){
			int speed = defender.getSpeed();
			Rectangle ship = defender.getShape().getBounds();
			if(ship.x + ship.width + speed < windowWidth ){
				defender.moveRight();
			}
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