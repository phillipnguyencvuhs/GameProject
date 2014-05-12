package gfx;

//This class contains some basic information about the screen
// and also some level information as well (at the moment)
public class Screen {

	public static final int MAP_WIDTH = 64;
	public static final int MAP_WIDTH_MASK = MAP_WIDTH - 1;

	// contain data about the color and their locations
	public int[] tiles = new int[MAP_WIDTH * MAP_WIDTH];
	public int[] colors = new int[MAP_WIDTH * MAP_WIDTH * 4];

	public int xOffset = 0; // for the camera point
	public int yOffset = 0; // for the camera point

	public int width;
	public int height;

	public SpriteSheet sheet;

	public Screen(int width, int height, SpriteSheet sheet) {

		this.height = height;
		this.width = width;
		this.sheet = sheet;

		for (int i = 0; i < MAP_WIDTH * MAP_WIDTH; i++) {
			// go through all the colors and set them
			colors[i * 4 + 0] = 0xff00ff;
			colors[i * 4 + 1] = 0x00ffff;
			colors[i * 4 + 2] = 0xffff00;
			colors[i * 4 + 3] = 0xffffff;
		}
	}

	public void render(int[] pixels, int offset, int row) {

		// loops through the yTiles, and allows for side scrolling and such
		for (int yTile = yOffset >> 3; yTile <= (yOffset + height) >> 3; yTile++) {
			// bitshift 3 (>> 3) is the same as divide by 8
			int yMin = yTile * 8 - yOffset; // min tile to start
			int yMax = yMin + 8;
			// make sure the variables are within bounds:
			if (yMin < 0)
				yMin = 0;
			if (yMax > height)
				yMax = height;

			// same thing as above but for x
			for (int xTile = xOffset >> 3; xTile <= (xOffset + width) >> 3; xTile++) {
				int xMin = xTile * 8 - xOffset;
				int xMax = xMin + 8;
				if (xMin < 0)
					xMin = 0;
				if (xMax > width)
					xMax = width;

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
				// gives us the index of whatever tile we are on
				int tileIndex = (xTile & (MAP_WIDTH_MASK))
						+ (yTile & (MAP_WIDTH_MASK)) * MAP_WIDTH;

				// this loop actually draws everything
				for (int y = yMin; y < yMax; y++) {
					// get the location of where we are in the sheet
					int sheetPixel = ((y + yOffset) & 7) * sheet.getWidth()
							+ ((xMin + xOffset) & 7);
					int tilePixel = offset + xMin + y * row;
					for (int x = xMin; x < xMax; x++) {
						int color = tileIndex * 4
								+ sheet.getPixels()[sheetPixel++];
						pixels[tilePixel++] = colors[color];
					}
				}
			}
		}
	}
}
