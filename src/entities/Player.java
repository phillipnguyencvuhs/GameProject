package entities;

import gfx.Colors;
import gfx.Font;
import gfx.Screen;
import level.Level;
import Main.InputHandler;

public class Player extends Mob {

	//input handler for moving player
	private InputHandler input;
	//colors the player (look in spritesheet)
	private int color = Colors.get(-1, 111, 300, 543);
	//health variable
	public static int health = 13;
	//for rendering hearts (look in font class)
	private String life = ("###");
	//to center hearts
	private int adjustment = 5;
	
	////////////////////////////////////////////////////////////
	// Constructor assigns player to level, location for spawn,
	// and gives the player an input handler
	///////////////////////////////////////////////////////////
	public Player(Level level, int x, int y, InputHandler input) {
		// creates the player
		super(level, "Player", x, y, 1);
		this.input = input;
	}
	
	//tick updates player and moves
	public void tick() {
		int xa = 0;
		int ya = 0;
		
		// the following should move the entire screen...
		if (input.up.isPressed()) {
			ya--;
		}
		if (input.down.isPressed()) {
			ya++;
		}
		if (input.left.isPressed()) {
			xa--;
		}
		if (input.right.isPressed()) {
			xa++;
		}
		
		if(xa != 0 || ya != 0) {
			move(xa, ya);
			isMoving = true;
		} else {
			isMoving = false;
		}
		this.scale = 1;
	}

	//renders player with spritesheet info
	public void render(Screen screen) {
		//location of player on sprite sheet (first tile)
		//player is made out of 4 tiles
		int xTile = 0; 
		int yTile = 28;
		
		int walkingSpeed = 3;
		int flipTop = (numSteps >> walkingSpeed) & 1;
		int flipBottom = (numSteps >> walkingSpeed) & 1;
		
		if (movingDir == 1){
			xTile += 2;
		}
		
		else if (movingDir > 1){
			xTile += 4 + ((numSteps >> walkingSpeed) & 1) * 2;
			flipTop = (movingDir - 1) % 2;
		}
		
		//modifier variable
		int modifier = 8 * scale;
		int xOffset = x - modifier/2; 
		int yOffset = y - modifier/2 - 4;
		
		/* multiply by 32 to get actual tile in sprite sheet*/
		//upper body of player
		screen.render(xOffset + (modifier * flipTop), yOffset, xTile + yTile * 32, color, flipTop, scale);
		screen.render(xOffset + modifier - (modifier * flipTop), yOffset, (xTile + 1) + yTile * 32, color, flipTop, scale);
		
		//lower body of player
		screen.render(xOffset + (modifier * flipBottom), yOffset + modifier, xTile + (yTile + 1) * 32, color, flipBottom, scale);
		screen.render(xOffset + modifier - (modifier * flipBottom), yOffset + modifier, (xTile + 1) + (yTile + 1) * 32, color, flipBottom, scale);
		
		//changes hearts above player based on life total
		if(health == 13){
			life = "###";
			adjustment = 5;
		} else if(health == 9){
			life = "##";
			adjustment = 0;
		} else if(health == 5){
			life = "#";
			adjustment = -3;
		}
		
		Font.render(life, screen, xOffset - adjustment, yOffset - 10, Colors.get(-1, -1, -1, 257), 1);
	}
	
	// For Solid Detection 
	public boolean hasCollided(int xa, int ya) {
		int xMin = 0;
		int xMax = 7;
		int yMin = 3;
		int yMax = 7;
		
		
		for(int x = xMin; x < xMax; x++ ){
			if(isSolidTile(xa, ya, x, yMin))
				return true;
		}
		for(int x = xMin; x < xMax; x++ ){
			if(isSolidTile(xa, ya, x, yMax))
				return true;
		}
		for(int y = yMin; y < yMax; y++ ){
			if(isSolidTile(xa, ya, xMin, y))
				return true;
		}
		for(int y = yMin; y < yMax; y++ ){
			if(isSolidTile(xa, ya, xMax, y))
				return true;
		}
		return false;
	}
	
	// For Spike Detection
	public boolean isDamaged(int xa, int ya) {
		int xMin = 0;
		int xMax = 7;
		int yMin = 3;
		int yMax = 7;
		
		for(int x = xMin; x < xMax; x++ ){
			if(isSpikeTile(xa, ya, x, yMin))
				return true;
		}
		for(int x = xMin; x < xMax; x++ ){
			if(isSpikeTile(xa, ya, x, yMax))
				return true;
		}
		for(int y = yMin; y < yMax; y++ ){
			if(isSpikeTile(xa, ya, xMin, y))
				return true;
		}
		for(int y = yMin; y < yMax; y++ ){
			if(isSpikeTile(xa, ya, xMax, y))
				return true;
		}
		return false;
	}
	
	//returns health
	public static int getHealth(){
		return health;
		// We made this static so it is accessible from the Mob class
	}
	
	//sets health
	public static void setHealth(int Health){
		health = Health;
		// It's static for the same reason as getHealth
	}

}
