package entity;

import java.awt.Graphics;

public class Ship extends Entity {
	
	
	public Ship() {
		// TODO Auto-generated constructor stub
	}

	public Ship(int x, int y, int width, int height) {
		super(x, y, width, height);
	}

	/**
	 * Fires a missile
	 */
	public void fire() {
		// TODO Auto-generated method stub

	}
	
	/**
	 * Sets the shape of the ship
	 */
	@Override
	public void setShape() {
		// TODO Auto-generated method stub

	}

	/**
	 * Destroys the entity
	 */
	public void destroy(){
		
	}
	
	/**
	 * 
	 */
	public void setLives(int lives){
		this.lives = lives;
	}
	
	/**
	 * 
	 */
	public void loseALife(){
		int life = 1;
		setLives(lives - life);
	}
	
	/**
	 * 
	 */
	public void gainALife(){
		int life = 1;
		setLives(lives + life);
	}
	
	/**
	 * Paints the ship
	 */
	@Override
	public void paint(Graphics pane) {
		// TODO Auto-generated method stub

	}
	
}
