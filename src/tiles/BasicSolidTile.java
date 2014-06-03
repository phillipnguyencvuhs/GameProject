package tiles;

public class BasicSolidTile extends BasicTile {
	
	//Basic solid tile constructor is like the others, but sets solid to true
	public BasicSolidTile(int id, int x, int y, int tileColor, int levelColor) {
		super(id, x, y, tileColor, levelColor);
		this.solid = true;
	}

}
