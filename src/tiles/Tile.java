package tiles;

import gfx.*;
import level.Level;

public abstract class Tile {

	public static final Tile[] tiles = new Tile[256];
	public static final Tile VOID = new BasicTile(0, 0, 0, Colors.get(000, -1,
			-1, -1));
	public static final Tile STONE = new BasicTile(1, 1, 0, Colors.get(-1, 333,
			-1, -1));
	public static final Tile GRASS = new BasicTile(2, 2, 0, Colors.get(-1, 131,
			141, -1));

	protected byte id;
	protected boolean solid; // for collision detection
	protected boolean emitter; // for light

	public Tile(int id, boolean isSolid, boolean isEmitter) {
		this.id = (byte) id; // parameters is int so casting is needed once

		if (tiles[id] != null)
			throw new RuntimeException("Duplicate id on " + id);
		
		this.solid = isSolid;
		this.emitter = isEmitter;
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

	public abstract void render(Screen screen, Level level, int x, int y);
}
