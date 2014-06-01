package gfx;

public class Font {

	// # is for heart
	private static String chars = "" + "ABCDEFGHIJKLMNOPQRSTUVWXYZ#     "
			+ "0123456789.,:;'\"!?$%-=+/      ";
	//Renders text in the game with a specific location, color, and size
	public static void render(String msg, Screen screen, int x, int y, int color, int scale){
		msg = msg.toUpperCase();
		
		for (int i = 0; i < msg.length(); i++){
			//following method finds  location of character in spritesheet
			int charIndex = chars.indexOf(msg.charAt(i)); 
			if(charIndex >=0)
				screen.render(x + (i*8), y, charIndex + 30 * 32, color, 0x00, scale);
		}
	}
}
