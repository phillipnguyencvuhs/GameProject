package tiles;

import gfx.Screen;
import level.Level;

public class BasicTile extends Tile{

	protected int tileid;
	protected int tileColor;
	
	
	public BasicTile(int id, int x, int y, int tileColor) {
		super(id, false, false);
		this.tileid = x + y;
		this.tileColor = tileColor;
	}


	public void render(Screen screen, Level level, int x, int y) {
		screen.render(x,  y,  tileid, tileColor)
		
	}
	
}
