import button.AButton;
import button.RegularButton;
import entity.AlienShip;
import entity.Entity;
import entity.Missile;
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
	 * AlienShips
	 */
	private Entity[][] alienShips;
	private int[] alienShipIndex;
	
	private ArrayList<Entity> defenderMissiles = new ArrayList<Entity>();
	private ArrayList<Entity> alienMissiles = new ArrayList<Entity>();
	
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
	 * Did you win the game?
	 */
	private boolean winCondition = false;

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
		timer.setPeriod(16);//60 frames per second
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
		
		// paints all alienShips that haven't been destroyed
		for(int i = 0; i < alienShips.length; i++){
			for(int j = 0; j < alienShipIndex[i]; j++){
				alienShips[i][j].paint(pane);
			}
		}
		
		
		// paints all defender missiles that haven't been destroyed
		for (int i = 0; i < defenderMissiles.size(); i++ ){
				Entity missile = (Entity) defenderMissiles.get(i);
				missile.paint(pane);
		}
		
		// paints all alien missiles that haven't been destroyed
		for (int i = 0; i < alienMissiles.size(); i++ ){
				Entity missile = (Entity) alienMissiles.get(i);
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
			AlienShip.setVerticalMovement(false);
			int speed = AlienShip.getSpeed();
			
			if(speed < 0){
				// moves all alienShips to the right
				for(int i = 0; i < alienShips.length; i++){
					for(int j = 0; j < alienShipIndex[i]; j++){
						
						// checks to see if the direction has already changed
						if(!directionChange){
							Rectangle rect = alienShips[i][j].getShape().getBounds();
	
							//changes direction of ships and tells them to move down
							if(rect.x + speed <= 0){
								AlienShip.changeDirection();
								directionChange = true;
								AlienShip.setVerticalMovement(true);
							}
						}
						alienShips[i][j].move();
						
						//fire random missile
						if( Math.random() > 0.9998){
							Entity missile = alienShips[i][j].fire();
							alienMissiles.add(missile);
						}
					}
				}
			}else{
				// moves all alienShips to the left
				for(int i = alienShips.length - 1; i > -1 ; i--){
					for(int j = 0; j < alienShipIndex[i]; j++){
						
						// checks to see if the direction has already changed
						if(!directionChange){
							Rectangle rect = alienShips[i][j].getShape().getBounds();
	
							//changes direction of ships and tells them to move down
							if(rect.x + rect.width + speed >= windowWidth){
								AlienShip.changeDirection();
								directionChange = true;
								AlienShip.setVerticalMovement(true);
							}
						}
						alienShips[i][j].move();
						
						//fire random missile
						if( Math.random() > 0.9998){
							Entity missile = alienShips[i][j].fire();
							alienMissiles.add(missile);
						}
					}
				}
			}
			
			// moves all defenderMissiles
			for (int i = 0; i < defenderMissiles.size(); i++ ){
				Entity missile = defenderMissiles.get(i);
				missile.move();
			}
			
			/*
			 * ArrayList to keep track of any <code>Entity</code> that is to be destroy
			 * when the next frame is painted.
			 */
			ArrayList<Entity> defenderMissilesToBeDestroyed = new ArrayList<Entity>(),
							  alienMissilesToBeDestroyed = new ArrayList<Entity>();
			
			/*
			 * Keeps track of the indexes for what alienShip needs to be destroyed
			 */
			boolean[][] alienShipsToBeDestroyed = new boolean[12][5];
			int[] alienShipsToBeDestroyedCount = new int[12];
			
			// Checks for defenderMissiles position
			for (int i = 0; i < defenderMissiles.size(); i++ ){
				Entity missile = defenderMissiles.get(i);
				int y = missile.getShape().getBounds().x;
				
				// If missile is out of the draw able area, destroy
				if( y < 0){
					defenderMissilesToBeDestroyed.add(missile);
				}else{ // check for collisions
					for(int j = 0; j < alienShips.length; j++){
						for(int k = 0; k < alienShipIndex[j]; k++){
							if(alienShips[j][k].isInside(missile)){
								alienShipsToBeDestroyed[j][k] = true;
								alienShipsToBeDestroyedCount[j]++;
								alienShipCount--;
								if(alienShipCount < 1){
									winCondition = true;
									gameOver();
								}
								defenderMissilesToBeDestroyed.add(missile);
								
								// increment score
								gameScore = gameScore + 100;
							}
						} 
					}	
				}
			}
			
			
			//removes alienShips to be destroyed an
			for(int i = 0; i < alienShipsToBeDestroyedCount.length; i++){
					if(alienShipsToBeDestroyedCount[i] > 0){
						Entity[] tempShips = new AlienShip[alienShips[i].length];
						int count = 0;
						for(int j = 0; j < alienShipIndex[i]; j++){
							if(!alienShipsToBeDestroyed[i][j]){
								tempShips[count++] = alienShips[i][j];
							}
						}
						alienShips[i] = tempShips;
						alienShipIndex[i] = count;
				}
			}
			
			//removes alienShips and missiles to be destroyed and clears objects that have been destroyed.
			defenderMissiles.removeAll(defenderMissilesToBeDestroyed);
			defenderMissilesToBeDestroyed.clear();
			
			if(alienShipCount <= 50 && alienShipCount > 40){
				AlienShip.setSpeedMultiplier(2);
			}else if(alienShipCount <= 30 && alienShipCount > 20){
				AlienShip.setSpeedMultiplier(3);
			}else if(alienShipCount <= 20 && alienShipCount > 10){
				AlienShip.setSpeedMultiplier(4);
			}else if(alienShipCount <= 10 && alienShipCount > 5){
				AlienShip.setSpeedMultiplier(6);
			}else if (alienShipCount <= 5){
				AlienShip.setSpeedMultiplier(8);
			}
			
			
		}
		//stop the animation
		else if(!isGameRunning){
			//These reset it to show the initial screen
			nextTime = false;
			isGameRunning = false;
			setIgnoreRepaint(false);
			repaint();
		}
	}
	
	//
	public boolean containsInt(int[] array, int target, int max) {
      for (int i = 0; i < max; i++) {
         if (target == array[i]) {
            return true;
         }
      }
      return false;
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
			alienWidth = 40,
			alienHeight = 40,
			initialX = windowWidth/columnsOfShips,
			initialY = 150,
			spacingX = 50,
			spacingY = 20;
		
		alienShips = new AlienShip[columnsOfShips][rowsOfShips];
		alienShipIndex = new int[columnsOfShips];
		alienShipCount = 0;
		
		defenderMissiles.clear();
		alienMissiles.clear();
		gameScore = 0;
		
		winCondition = false;
		
		AlienShip.setSpeedMultiplier(1);
		
		//Our heroic defending ship
		defender = new Ship((windowWidth - 50)/2, 850, 50, 25);
		
		for(int i = 0; i < alienShips.length; i++){
			for(int j = 0; j < alienShips[0].length; j++){
				alienShips[i][j] = new AlienShip(initialX + alienWidth*i + spacingX*i, 
								initialY + alienWidth*j + spacingY*j , alienWidth, alienHeight);
				alienShipIndex[i]++;
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
				defenderMissiles.add(missile);
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
	 * Game Over
	 */
	private void gameOver(){
		isGameRunning = false;
		System.out.println("Game Over");
		repaint();
		if(winCondition){
			System.out.println("You Win!");
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