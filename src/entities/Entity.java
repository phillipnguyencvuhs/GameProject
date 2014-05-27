package entities;

import gfx.Screen;
import level.Level;

public abstract class Entity {

	public int x, y;
	private Level level;
	
	public Entity(Level level){
		init(level);
	}
	
	private final void init(Level level){
		this.level = level;
	}
	
	public abstract void tick();
	
	public abstract void render(Screen screen);
	
}