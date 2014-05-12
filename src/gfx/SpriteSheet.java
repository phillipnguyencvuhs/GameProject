package gfx;

import java.awt.image.*;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet {

	private String path;
	private int width;
	private int height;
	private int[] pixels;

	public SpriteSheet(String path) {
		BufferedImage image = null;
		try {
			image = ImageIO.read(SpriteSheet.class.getResource(path));
		} catch (IOException e) {
			e.printStackTrace();
		}

		//just in case the try/catch doesn't work:
		if (image == null) { 
			return;
		}

		this.path = path;
		this.width = image.getWidth();
		this.height = image.getHeight();

		pixels = image.getRGB(0, 0, width, height, null, 0, width);

		/***********************************************************
		 * Information on colors:
		 * All color values have two values for alpha channel, red, 
		 * green, and blue (AARRGGBB). White is 0xffffff (which is a
		 * hex value, which would be 255, 255, 255 in decimal) because 
		 * no transparency is needed.
		 * Important colors to know:
		 * black:      000000 (255/3*0 = 0, all RGB values are 0)
		 * gray:       555555 (255/3*1 = 85, all RGB values are 85)
		 * light gray: aaaaaa (255/3*2 = 170, all RGB values are 170)
		 * white: 	   ffffff (255/3*3 = 255, all RGB values are 255)
		 ***********************************************************/
		// loop sets a color value into either a 0,1,2,3,or 4
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = (pixels[i] & 0xff) / 64;
		//removed alpha channel and divided by 255/4 (which is about 64)
		}

		for (int i = 0; i < 8; i++) {
			System.out.println(pixels[i]);
		}
	}
	
	public int[] getPixels(){
		return pixels;
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
	
	public String path(){
		return path;
	}
}
