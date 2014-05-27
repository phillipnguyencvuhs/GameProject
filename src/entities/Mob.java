package entities;

import level.Level;

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

	public String getName() {
		return name;
	}
}
