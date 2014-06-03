package Main;

import entities.Player;
import gfx.*;
import gfx.Font;
import tiles.*;
import level.*;
import java.awt.*;
import java.awt.image.*;
import java.util.Random;
import javax.swing.*;

// 6/1/2014: exceeded 1000 lines of code

public class GameRunner extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;
	// random number generator
	private Random rn = new Random();

	// project setup (size and name)
	public static final int WIDTH = 160;
	public static final int HEIGHT = WIDTH / 12 * 9;
	public static int SCALE = 3;
	public static final String NAME = "Final Project";

	private JFrame frame;
	public boolean running = true;
	public int tickcount = 0;

	//death screen image
	Image result;

	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT,
			BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer())
			.getData(); // represents how many pixels are inside images
	private int[] colors = new int[6 * 6 * 6]; 
	// 216 is from amount of colors we want, (36) times 6 for RGB
	
	/////////////////////////////////////////
	//make basic things required for game
	public Screen screen;
	public InputHandler input;
	public Level level;
	public Player player;
	////////////////////////////////////////
	
	// a list of the names of the level files
	public String[] list = { "/large_level.png", "/large_maze_level.png",
			"/medium_level.png", "/small_level.png", "/small_maze_level.png", };

	// randomly chose a level
	public String select = list[(rn.nextInt(4))];

	public GameRunner() {
		// the size is set to the values initialized above
		setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));

		//////////////////////////////////////////////////////
		//setting up JFrame
		frame = new JFrame(NAME);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(this, BorderLayout.CENTER);
		frame.pack();
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		/////////////////////////////////////////////////////
	}

	//init method sets up the game
	public void init() {
		int index = 0;
		
		//makes all the colors
		for (int r = 0; r < 6; r++) {
			for (int g = 0; g < 6; g++) {
				for (int b = 0; b < 6; b++) {
					int rr = (r * 255 / 5);
					int gg = (g * 255 / 5);
					int bb = (b * 255 / 5);
					colors[index++] = rr << 16 | gg << 8 | bb;
				}
			}
		}
		
		//set the screen (to render from) 
		screen = new Screen(WIDTH, HEIGHT, new SpriteSheet("/sprite_sheet.png"));
		input = new InputHandler(this);

		// print out the name of the selected level in console for debugging
		// purposes
		System.out.println(select);
		// constructs the level
		level = new Level(select);
		//level = new Level("/large_level.png");
		// makes a player with coordinates 0,0 and basic controls
		player = new Player(level, 10, 10, input);
		// puts player into level
		level.addEntity(player);
	}

	//sets running to true and makes a new thread
	public synchronized void start() {
		running = true;
		new Thread(this).start();
	}

	//makes the game stop updating
	public synchronized void stop() {
		running = false;
	}

	//the method that calls all the other methods
	public void run() {
		long lastTime = System.nanoTime();
		double nsPerTick = 1000000000.0 / 60.0; // how many nano secs per tick

		int ticks = 0; // how many updates
		int frames = 0; // current fps

		long lastTimer = System.currentTimeMillis(); // time to reset data
		double delta = 0; // how many unprocessed nano seconds so far

		//initializes game variables and settings
		init();

		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / nsPerTick;
			lastTime = now;
			boolean shouldRender = true;

			while (delta >= 1) {
				ticks++;
				tick();
				delta -= 1;
				shouldRender = true;
			}
			
			//limits the fps to something reasonable
			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if (shouldRender) {
				frames++;
				render();
			}

			if (System.currentTimeMillis() - lastTimer >= 1000) {
				lastTimer += 1000; // update another second
				System.out.println(frames + " frames " + ticks + " ticks");
				frames = 0;
				ticks = 0;
			}
		}
	}

	// tick method updates the entire game
	public void tick() {
		tickcount++;
		level.tick();
	}

	// print out the updates
	public void render() {
		BufferStrategy bs = getBufferStrategy(); // organizer

		if (bs == null) {
			createBufferStrategy(3); // enables triple buffering
			return;
		}

		// renders game if player is alive
		if (player.getHealth() > 0 && Player.getHealth() < 1000) {

			int xOffset = player.x - (screen.width / 2);
			int yOffset = player.y - (screen.height / 2);
			
			//renders the tiles of the level
			level.renderTiles(screen, xOffset, yOffset);
			//renders the entities on the level
			level.renderEntities(screen);

			//colors in the tiles
			for (int y = 0; y < screen.height; y++) {
				for (int x = 0; x < screen.width; x++) {
					int colorCode = screen.pixels[x + y * screen.width];
					if (colorCode < 255)
						pixels[x + y * WIDTH] = colors[colorCode];
				}
			}

			//final drawing stuff:
			Graphics g = bs.getDrawGraphics();
			g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
			g.dispose(); // free up memory
			bs.show(); // show contents of the buffer
		}
		// renders death screen when player is dead
		else if(Player.getHealth() > 1000){
			result = new ImageIcon("res/win.png").getImage();
			repaint();
			//stops the game
			stop();
		}
		else {
			// assign death image
			result = new ImageIcon("res/dead.png").getImage();
			repaint();
			//stops the game
			stop();
		}
	}

	// draws death image under the game
	public void paint(Graphics g) {

		/************************************************************
		 * This was the only method of adding an image I could find that
		 * actually works with this project. The death image is blank until the
		 * players health reaches 0, otherwise it would show the image when the
		 * game starts up.
		 ************************************************************/

		BufferedImage thumbImage = new BufferedImage(WIDTH * SCALE + 10, HEIGHT
				* SCALE + 10, BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics2D = thumbImage.createGraphics();
		graphics2D.drawImage(result, 0, 0, WIDTH * SCALE + 10, HEIGHT * SCALE
				+ 10, null);
		g.drawImage(thumbImage, 0, 0, this);
	}

	//starts the game
	public static void main(String[] args) {
		new GameRunner().start();
	}
}
