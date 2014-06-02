package entities;

import tiles.Tile;
import level.Level;
import entities.*;

//framework for all moving characters/objects

public abstract class Mob extends Entity {

	// protected is like private, but subclasses can see
	protected String name;

	protected int speed;
	protected int numSteps = 0;
	protected boolean isMoving;
	protected int movingDir = 1;

	// movingDir info: 1 is up, 0 is down, 2 is left, 3 is right
	protected int scale = 1;

	public Mob(Level level, String name, int x, int y, int speed) {
		super(level);
		this.name = name;
		this.speed = speed;
		this.y = y;
		this.x = x;
	}

	// xa and ya is how much we are moving in that direction
	public void move(int xa, int ya) {
		if (xa != 0 && ya != 0) {
			move(xa, 0);
			move(0, ya);
			numSteps--;
			return;
		}
		numSteps++;
		// collision detection
		if (!hasCollided(xa, ya)) {
			// sets direction
			if (ya < 0)
				movingDir = 0;
			if (ya > 0)
				movingDir = 1;
			if (xa < 0)
				movingDir = 2;
			if (xa > 0)
				movingDir = 3;
			x += xa * speed;
			y += ya * speed;
		}
	}

	public abstract boolean hasCollided(int xa, int ya);
	
	protected boolean isSolidTile(int xa, int ya, int x, int y){
		if(level == null) 
			return false;
		Tile lastTile = level.getTile((this.x + x) >> 3, (this.y + y) >> 3);
		Tile newTile = level.getTile((this.x + x + xa) >> 3, (this.y + y + ya) >> 3);
		
		if(!lastTile.equals(newTile) && newTile.isSolid())
			return true;
		
		return false;
	}
	
	protected boolean isSpikeTile(int xa, int ya, int x, int y){
		if(level == null)
			return false;
		
		Tile lastTile = level.getTile((this.x + x) >> 3, (this.y + y) >> 3);
		Tile newTile = level.getTile((this.x + x + xa) >> 3, (this.y + y + ya) >> 3);
		
		if(!lastTile.equals(newTile) && newTile.isSpike()){
			Player.setHealth(Player.getHealth() - 1);
			return false;
		}
		
		return false;
	}

	public String getName() {
		return name;
	}
}
