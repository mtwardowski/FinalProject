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
*	@author Michael Twardowski
*/
public class SpaceInvaders extends Canvas implements MouseListener, KeyListener,
AlarmListener{

	/**
	 * Allows smooth animation
	 */
	private BufferStrategy buffer;
	
	/**
	 * The timer that will call the takeAction method to repaint our canvas
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
	 * <code>Ship</code> for the user to control
	 */
	private Ship defender;
	
	/**
	 * Holds the AlienShips
	 */
	private Entity[][] alienShips;
	
	/**
	 * Keeps track of the partial filled AlienShips array
	 */
	private int[] alienShipIndex;
	
	/**
	 * Number of <code>alienShips</code> that are alive
	 */
	private int alienShipCount;
	
	/**
	 * Holds the missiles the defender has fired
	 */
	private ArrayList<Entity> defenderMissiles = new ArrayList<Entity>();
	
	/**
	 * Holds the missiles the <code>AlienShip</code>'s have fired
	 */
	private ArrayList<Entity> alienMissiles = new ArrayList<Entity>();
	
	/**
	 * The possibility that an <code>AlienShip</code> may fire every time it moves
	 */
	private double firePossibility;
	
	/**
	 * Holds rectangles that are painted to indicate the number of lives a
	 *  <code>Ship</code> has
	 */
	private Rectangle[] lives;
	
	/**
	 * Lives Location
	 */
	private int livesX,
				livesY;

	/**
	 * Tells us if the game animation should be painted or not.
	 */
	private boolean isGameRunning = false;
	
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
	 * Is the game over?
	 */
	private boolean isGameOver = false;

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
		 * Sets up game score label location
		 */
		gameScoreX = windowWidth - 150;
		gameScoreY = 75;
		
		/*
		 * set up lives label location
		 */
		livesX = windowWidth - 500; 
		livesY = 50;
		
		// allows for window closing
		myWindow = new CloseWindow(); 
		window.addWindowListener(myWindow);
		
		//detects mouse events in the frame
		addMouseListener(this);
		// detects key vents in the frame;
		addKeyListener(this);  

		timer = new Alarm(this);
		timer.setPeriod(16); //60 frames per second

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
		
		// if the Game has been played and is over this will draw the appropriate message
		if(isGameOver){
			String gameOverMessage;
			int gameOverMessageX = windowWidth/2,
				gameOverMessageY = windowHeight/2;
			if(winCondition){
				gameOverMessage = "You Won!";
			}else{
				gameOverMessage = "You lost :(";
			}
			Graphics2D pane2 = (Graphics2D)pane;
			pane2.setColor(Color.black);
			Font currentFont = pane2.getFont();
			Font newFont = currentFont.deriveFont(currentFont.getSize() * 3F);
			pane2.setFont(newFont);
			pane2.drawString(gameOverMessage, gameOverMessageX, gameOverMessageY);
		}
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
		
		//paint the button
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
		
		// paints game score
		Graphics2D pane2 = (Graphics2D)pane;
		pane2.setColor(Color.black);
		Font currentFont = pane2.getFont();
		Font newFont = currentFont.deriveFont(currentFont.getSize() * 3F);
		pane2.setFont(newFont);
		pane2.drawString("" + gameScore, gameScoreX, gameScoreY);
		
		// paints lives
		for(int i = 0; i < defender.getLives(); i++){
			pane2.setColor(Color.BLUE);
			pane2.fillRect(lives[i].x, lives[i].y, lives[i].width, lives[i].height);
		}
		
		//Necessary commands for displaying what you've painted
		pane.dispose();	//you don't need the Graphics any more
		buffer.show();	//what you've drawn is in the buffer
	}
	
	/**
	 * takeNotice will move all objects in the game and perform collision detection.
	 */
	public void takeNotice() {
		//Only paint animation is running
		if(isGameRunning){
			//First, paint the game
			paintFrame();	
			
			moveAlienShips();
			
			moveMissiles();
			
			checkForCollisions();
			
			checkRemainingAliens();
		}
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
		
		firePossibility = 0.9998;
		 
		gameScore = 0;
		
		winCondition = false;
		isGameOver = false;
		
		AlienShip.setSpeedMultiplier(1);
		
		//Our heroic defending ship
		int defenderX = (windowWidth - 50)/2,
			defenderY = windowHeight - 100,
			defenderWidth= 50,
			defenderHeight = 25;
		defender = new Ship(defenderX, defenderY, defenderWidth, defenderHeight);
		
		// set lives to 3
		int lives = 3;
		defender.setLives(lives);
		
		// creates lives
		this.lives = new Rectangle[3];
		for(int i = 0; i < lives; i++){
			this.lives[i] = new Rectangle(livesX + defenderWidth*i + spacingX*i, livesY, defenderWidth, defenderHeight);
		}
		
		for(int i = 0; i < alienShips.length; i++){
			for(int j = 0; j < alienShips[0].length; j++){
				alienShips[i][j] = new AlienShip(initialX + alienWidth*i + spacingX*i, 
								initialY + alienWidth*j + spacingY*j , alienWidth, alienHeight);
				alienShipIndex[i]++;
				alienShipCount++;
			}
		}
		isGameRunning = true;
		timer.start();
	}
	
	/**
	 * Increases the game score
	 */
	private void increaseScore(){
		gameScore = gameScore + 100;
	}
	
	/**
	 * Moves all of the <code>AlienShips</code> that are alive.
	 */
	private void moveAlienShips(){
		
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
					
						//if alienShip is now at the bottom
						if(rect.y >= windowHeight - 100){
							gameOver();
						}
					}
					
					alienShips[i][j].move();
					
					//fire random missile
					if( Math.random() > firePossibility){
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
						
						//if alienShip is now at the bottom
						if(rect.y >= windowHeight - 100){
							gameOver();
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
	}
	
	/**
	 * Moves all of the <code>Missile</code>s that haven't been destroyed
	 */
	private void moveMissiles(){
		// moves all defenderMissiles
		for (int i = 0; i < defenderMissiles.size(); i++ ){
			Entity missile = defenderMissiles.get(i);
			missile.move();
		}
		
		// moves all alienMissiles
		for (int i = 0; i < alienMissiles.size(); i++ ){
			Entity missile = alienMissiles.get(i);
			missile.move();
		}
	}
	
	/**
	 * Checks to see if any <code>Ship</code>, <code>Missile</code>s or
	 * <code>AlienShip</code>s has collided. Destroys those that have.
	 */
	private void checkForCollisions(){
		
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
							increaseScore();
						}
					} 
				}	
			}
		}
		
		// Checks for alienMissiles position
		for (int i = 0; i < alienMissiles.size(); i++ ){
			Entity missile = alienMissiles.get(i);
			int y = missile.getShape().getBounds().x;
	
			// If missile is out of the draw able area, destroy
			if( y > windowHeight){
				alienMissilesToBeDestroyed.add(missile);
			}else{ // check for collisions
				if(defender.isInside(missile)){
					alienMissilesToBeDestroyed.add(missile);
					defender.loseALife();
					
					if(defender.getLives() < 1){
						winCondition = false;
						gameOver();
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
		
		//removes alienShips alienMisilles and missiles to be destroyed and clears objects that have been destroyed.
		defenderMissiles.removeAll(defenderMissilesToBeDestroyed);
		alienMissiles.removeAll(alienMissilesToBeDestroyed);
		defenderMissilesToBeDestroyed.clear();
		alienMissilesToBeDestroyed.clear();
	}
	
	/**
	 * Updates <code>AlienShip</code> speed and firing rating depending
	 * on the number of them remaining
	 */
	private void checkRemainingAliens(){
		if(alienShipCount <= 50 && alienShipCount > 40){
			AlienShip.setSpeedMultiplier(2);
			firePossibility = 0.9995;
		}else if(alienShipCount <= 30 && alienShipCount > 20){
			AlienShip.setSpeedMultiplier(3);
			firePossibility = 0.999;
		}else if(alienShipCount <= 20 && alienShipCount > 10){
			AlienShip.setSpeedMultiplier(4);
			firePossibility = 0.995;
		}else if(alienShipCount <= 10 && alienShipCount > 5){
			AlienShip.setSpeedMultiplier(6);
			firePossibility = 0.99;
		}else if (alienShipCount <= 5){
			AlienShip.setSpeedMultiplier(8);
			firePossibility = 0.985;
		}
	}

	/**
	 * If the mouse click is in the Start New Game Button, the game will reset
	 * and start a new game.
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
		if(pressed == KeyEvent.VK_SPACE){
			Entity missile = defender.fire();
			// returns null if the time between shots is too short.
			if(missile != null){
				defenderMissiles.add(missile);
			}
		}else if ((pressed == KeyEvent.VK_LEFT) |(pressed == KeyEvent.VK_A)){
			int speed = Ship.getSpeed();
			Rectangle ship = defender.getShape().getBounds();
			if(ship.x - speed > 0 ){
				defender.moveLeft();
			}
		}else if ((pressed == KeyEvent.VK_RIGHT)|(pressed == KeyEvent.VK_D)){
			int speed = Ship.getSpeed();
			Rectangle ship = defender.getShape().getBounds();
			if(ship.x + ship.width + speed < windowWidth ){
				defender.moveRight();
			}
		}
	}
	
	/**
	 * This method will end the game.
	 */
	private void gameOver(){
		isGameRunning = false;
		isGameOver = true;
		repaint();
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