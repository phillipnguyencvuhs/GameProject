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
	public static final Tile SPIKE = new SpikeTile(3, 4, 0, Colors.get(-1, 181, 171, 333), 0xFF9a9a9a);
	public static final Tile CHEST = new ChestTile(4, 5, 0, Colors.get(-1, 170, 000, 446
			), 0xFF00ff00);

	protected byte id; // telling what is what
	protected boolean solid; // for collision detection
	protected boolean emitter; // for light
	protected boolean spike; // for damage
	protected boolean chest;
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
	
	public boolean isSpike(){
		return spike;
	}
	
	public boolean isChest(){
		return chest;
	}

	public boolean isEmitter() {
		return emitter;
	}
	public int getLevelColor(){
		return levelColor;
	}

	public abstract void render(Screen screen, Level level, int x, int y);
}
