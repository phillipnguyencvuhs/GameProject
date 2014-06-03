package tiles;

import gfx.Screen;
import level.Level;

public class SpikeTile extends Tile{
	
	protected int tileId;
	protected int tileColor;

	//SpikeTile constructor is like the others but spike is set to true
	public SpikeTile(int id, int x, int y, int tileColor, int levelColor) {
		super(id, false, false, levelColor);
		this.tileId = x;
		this.tileColor = tileColor;
		this.spike = true;
	}
	
	//calls screen's render method
	public void render(Screen screen, Level level, int x, int y) {
		screen.render(x, y, tileId, tileColor, 0x00, 1);
		
	}

}