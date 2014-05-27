package gfx;

//This class contains some basic information about the screen
// and also some level information as well (at the moment)

public class Screen {

	public static final int MAP_WIDTH = 64;
	public static final int MAP_WIDTH_MASK = MAP_WIDTH - 1;

	public static final byte BIT_MIRROR_X = 0x01;
	public static final byte BIT_MIRROR_Y = 0x02;

	public int[] pixels;

	public int xOffset = 0; // for the camera point
	public int yOffset = 0; // for the camera point

	public int width;
	public int height;

	public SpriteSheet sheet;

	public Screen(int width, int height, SpriteSheet sheet) {

		this.width = width;
		this.height = height;
		this.sheet = sheet;

		pixels = new int[width * height];
	}

	/*****************************************************************
	 * Information on bitwise AND (&) operator: ints take up 32 bits of space.
	 * The & operator compares each binary digit of two integers and returns a
	 * new integer. It does this by comparing every bit of each int and returns
	 * 1 if they are both 1, otherwise the result is 0.
	 ***************************************************************** 
	 * Example: 37 & 23
	 ***************************************************************** 
	 * 37 in binary: 0 0 1 0 0 1 0 1 23 in binary: 0 0 0 1 0 1 1 1 result in
	 * bin: 0 0 0 0 0 1 0 1 = 5 (in decimal)
	 *****************************************************************/

	public void render(int xPos, int yPos, int tile, int color, int mirrorDir, int scale) {

		/*******************************************************
		 * Using bits to determine location (using two bits): This is so we
		 * don't have to use two booleans
		 ******************************************************** 
		 * 00 = normal 01 = flipped along x 02 = flipped along y
		 ********************************************************/

		xPos -= xOffset;
		yPos -= yOffset;

		boolean mirrorX = (mirrorDir & BIT_MIRROR_X) > 0;
		/*
		 * becomes true if mirrorDir matches BIT_MIRROR_X in a bit, which means
		 * that it will move x
		 */
		boolean mirrorY = (mirrorDir & BIT_MIRROR_Y) > 0;
		
		int scaleMap = scale -1;
		int xTile = tile % 32;
		int yTile = tile / 32;
		int tileOffset = (xTile << 3) + (yTile << 3) * sheet.width;

		for (int y = 0; y < 8; y++) {
			int ySheet = y;
			if (mirrorY)
				ySheet = 7 - y;
			
			int yPixel = y + yPos + (y * scaleMap) - ((scaleMap << 3)/2);
			

			for (int x = 0; x < 8; x++) {
				int xSheet = x;
				if (mirrorX)
					xSheet = 7 - x;
				
				int xPixel = x + xPos + (x * scaleMap) - ((scaleMap << 3)/2);

				// verify it is between 0 and 255
				int col = (color >> (sheet.pixels[xSheet + ySheet
						* sheet.width + tileOffset] * 8)) & 255;

				if (col < 255) {
					for (int yScale = 0; yScale < scale; yScale++){
					if (yPixel + yScale < 0 || yPixel + yScale >= height)
						continue;
						for (int xScale = 0; xScale < scale; xScale++) {
							if (xPixel + xScale < 0 || xPixel + xScale >= width) 
								continue;
							pixels[(xPixel + xScale) + (yPixel + yScale) * width] = col;
						}
					}	
				}
			}
		}
	}

	public void setOffset(int xOffset, int yOffset) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}
}
