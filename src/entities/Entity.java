package entities;

import gfx.Screen;
import level.Level;

//framework for mobs
public abstract class Entity {

	//location in level
	public int x, y;
	//level which entity is in
	protected Level level;
	
	//constructor runs init
	public Entity(Level level){
		init(level);
	}
	
	//assigns entity to a level
	private final void init(Level level){
		this.level = level;
	}
	
	///////////////////////////////////////////////////
	//prototype methods to be implemented 
	public abstract void tick();
	public abstract void render(Screen screen);
	//////////////////////////////////////////////////
}
