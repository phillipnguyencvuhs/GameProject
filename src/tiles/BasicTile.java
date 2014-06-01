package tiles;

import gfx.Screen;
import level.Level;

public class BasicTile extends Tile{

	protected int tileId;
	protected int tileColor;
	
	/* id is for determining which block is which
	 x and y are for creating the id
	 tileColor is what the color is on the actual image
	 levelColor is what we want it to display as */

	public BasicTile(int id, int x, int y, int tileColor, int levelColor) {
		super(id, false, false, levelColor);
		this.tileId = x + y;
		this.tileColor = tileColor;
	}

	public void render(Screen screen, Level level, int x, int y) {
		screen.render(x, y, tileId, tileColor, 0x00, 1);
		
	}
	
}
