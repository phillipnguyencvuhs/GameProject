package tiles;

import gfx.Screen;
import level.Level;

public class ChestTile extends Tile{
	
	private int tileId;
	private int tileColor;

	//ChestTile constructor is like the others but sets chest to true
	public ChestTile(int id, int x, int y, int tileColor, int levelColor) {
		super(id, false, false, levelColor);
		this.tileId = x + y;
		this.tileColor = tileColor;
		this.chest = true;
	}

	//calls screen's render method
	public void render(Screen screen, Level level, int x, int y) {
		screen.render(x, y, tileId, tileColor, 0x00, 1);		
	}
}
