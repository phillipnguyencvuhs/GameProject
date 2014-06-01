package tiles;

import gfx.*;
import level.Level;

public abstract class Tile {

	public static final Tile[] tiles = new Tile[256];
	public static final Tile VOID = new BasicSolidTile(0, 0, 0, Colors.get(000, -1,
			-1, -1), 0xff000000);
	public static final Tile STONE = new BasicSolidTile(1, 1, 0, Colors.get(-1, 333,
			-1, -1), 0xFF555555);
	public static final Tile GRASS = new BasicTile(2, 2, 0, Colors.get(-1, 131,
			141, -1), 0xFF00FF00);
	public static final Tile SPIKE = new SpikeTIle(3, 3, 0, Colors.get(-1, 333, -1, -1), );

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
