package tiles;

import gfx.*;
import level.Level;

public abstract class Tile {
	
	public static final Tile[] tiles = new Tile[256];
	public static final Tile VOID = null;
	public static final Tile STONE = null;
	public static final Tile GRASS = null;

	protected byte id; 
	protected boolean solid;
	protected boolean emitter;
	
	public Tile(int id, boolean isSolid, boolean isEmitter){
		this.id = (byte)id;
		if(tiles[id] != null)
			throw new RuntimeException("Duplicate id on " + id);
		tiles[id] = this;
	}
	
	public abstract void render(Screen screen, Level level, int x, int y){
		
	}
}
