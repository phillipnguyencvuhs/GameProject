package tiles;

import gfx.*;
import level.Level;

public abstract class Tile {

	public static final Tile[] tiles = new Tile[256];
	
	/**************************************************************
	 * How these tiles work:
	 * arguments: (id, x position on spritesheet, y position on 
	 * spritesheet, Color(color to replace black, color to replace 
	 * dark gray, color to replace light gray, color to replace 
	 * white), color to assign to (on level sheet).
	 **************************************************************/
	
	public static final Tile VOID = new BasicSolidTile(0, 0, 0, Colors.get(000, -1,
			-1, -1), 0xff000000);
	public static final Tile STONE = new BasicSolidTile(1, 3, 0, Colors.get(-1, 333,
			0, -1), 0xFF555555);
	public static final Tile FLOOR = new BasicTile(2, 2, 0, Colors.get(-1, 181,
			171, -1), 0xFFFFFFFF);
	//public static final Tile SPIKE = new SpikeTIle(3, 3, 0, Colors.get(-1, 333, -1, -1), );

	protected byte id; // telling what is what
	protected boolean solid; // for collision detection
	protected boolean emitter; // for light
	
	private int levelColor;

	public Tile(int id, boolean isSolid, boolean isEmitter, int levelColor) {
		this.id = (byte) id; // parameters is int so casting is needed once

		if (tiles[id] != null)
			throw new RuntimeException("Duplicate id on " + id);
		
		this.solid = isSolid;
		this.emitter = isEmitter;
		this.levelColor = levelColor;
		tiles[id] = this;
	}

	public byte getId() {
		return id;
	}

	public boolean isSolid() {
		return solid;
	}

	public boolean isEmitter() {
		return emitter;
	}
	public int getLevelColor(){
		return levelColor;
	}

	public abstract void render(Screen screen, Level level, int x, int y);
}
