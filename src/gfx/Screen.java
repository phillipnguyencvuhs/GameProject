package gfx;

//This class contains some basic information about the screen
// and also some level information as well (at the moment)
public class Screen {

	public static final int MAP_WIDTH = 64; 
	public static final int MAP_WIDTH_MASK = MAP_WIDTH - 1; 
	public int[] pixels;
	public int xOffset = 0; // for the camera point
	public int yOffset = 0; // for the camera point
	public int width;
	public int height;
	public SpriteSheet sheet;

	public Screen(int width, int height, SpriteSheet sheet) {

		this.height = height;
		this.width = width;
		this.sheet = sheet;
		
		pixels = new int[width * height];
	}	
				 /*****************************************************************
				 * Information on bitwise AND (&) operator: ints take up 32 bits
				 * of space. The & operator compares each binary digit of two
				 * integers and returns a new integer. It does this by comparing
				 * every bit of each int and returns 1 if they are both 1,
				 * otherwise the result is 0.
				 ***************************************************************** 
				 * Example: 37 & 23
				 ***************************************************************** 
				 * 37 in binary:  0 0 1 0 0 1 0 1 
				 * 23 in binary:  0 0 0 1 0 1 1 1 
				 * result in bin: 0 0 0 0 0 1 0 1 = 5 (in decimal)
				 *****************************************************************/
	
	public void render(int xPos, int yPos, int tile, int color){
		xPos -= xOffset;
		yPos -= yOffset;
		
		int xTile = tile % 32;
		int yTile = tile / 32;
		int tileOffset = (xTile << 3) + (yTile << 3) * sheet.getWidth();
		
		for(int y = 0; y < 8; y++){
			if(y + yPos < 0 || y + yPos >= height) continue;
			int ySheet = y;
			
			for(int x = 0; x < 8; x++){
				if(x + yPos < 0 || x+ xPos >= height) continue;
				int xSheet = x;
				int col = (color >> (sheet.getPixels()[xSheet + ySheet * sheet.getWidth() + tileOffset] * 8)) & 255;
				if(col < 255){
					pixels[(x+xPos) + (y + yPos) * width] = col;
				}
			}
		}
	}
}
