package entities;

import tiles.Tile;
import level.Level;
import entities.*;

//framework for all moving characters/objects

public abstract class Mob extends Entity {

	// protected is like private, but subclasses can see
	// name of mob
	protected String name;
	// speed of mob
	protected int speed;
	// number of steps taken
	protected int numSteps = 0;
	// checks to see if the mob is currently in motion
	protected boolean isMoving;
	// movingDir info: 1 is up, 0 is down, 2 is left, 3 is right
	protected int movingDir = 1;
	// size of mob
	protected int scale = 1;

	// //////////////////////////////////////////////////////////////////
	// constructor assigns mob to level, assigns name to mob, assigns
	//location for spawn, and assigns speed to mob
	// //////////////////////////////////////////////////////////////////
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

		// spike detection
		if (isDamaged(xa, ya)) {
			Player.setHealth(Player.getHealth() - 1);
		}
	}

	public abstract boolean hasCollided(int xa, int ya);

	public abstract boolean isDamaged(int xa, int ya);

	//checks to see if the tile is solid, for collision detection
	protected boolean isSolidTile(int xa, int ya, int x, int y) {
		//automatically return false if no level
		if (level == null)
			return false;
		
		Tile lastTile = level.getTile((this.x + x) >> 3, (this.y + y) >> 3);
		Tile newTile = level.getTile((this.x + x + xa) >> 3,
				(this.y + y + ya) >> 3);
		
		//returns true if the next tile is solid and the last one is not
		if (!lastTile.equals(newTile) && newTile.isSolid())
			return true;

		return false;
	}

	//checks to see if the tile is a spike, for spike detection
	protected boolean isSpikeTile(int xa, int ya, int x, int y) {
		//automatically return false if no level
		if (level == null)
			return false;

		Tile lastTile = level.getTile((this.x + x) >> 3, (this.y + y) >> 3);
		Tile newTile = level.getTile((this.x + x + xa) >> 3,
				(this.y + y + ya) >> 3);
		
		//returns true if player hit a spike
		if (!lastTile.isSpike() && (newTile.isSpike())) {
			return true;
		}

		return false;
	}

	//returns name of mob
	public String getName() {
		return name;
	}
}
