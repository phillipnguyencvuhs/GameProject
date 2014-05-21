package Main;

import gfx.*;
import gfx.Font;
import tiles.*;
import level.*;
import java.awt.*;
import java.awt.image.*;

import javax.swing.*;

public class GameRunner extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 160;
	public static final int HEIGHT = WIDTH / 12 * 9;
	public static int SCALE = 3;
	public static final String NAME = "placeholder name";

	private JFrame frame;
	public boolean running = true;
	public int tickcount = 0;

	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT,
			BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer())
			.getData(); // represents how many pixels are inside image
	private int[] colors = new int[216];   //216 is from amount of colors we want (36) times 6 for RGB

	public Screen screen;
	public InputHandler input;
	public Level level;

	public GameRunner() {
		setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));

		frame = new JFrame(NAME);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(this, BorderLayout.CENTER);
		frame.pack();
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	public void init() {
		int index = 0;
		for(int r = 0; r < 6; r++){
			for(int g = 0; g < 6; g++){
				for(int b = 0; b < 6; b++){
					int rr = (r*255/5);
					int gg = (g*255/5);
					int bb = (b*255/5);
					
					colors[index++] = rr << 16 | gg << 8 | bb;
			}
		}
	}
		
		screen = new Screen(WIDTH, HEIGHT, new SpriteSheet("/sprite_sheet.png"));
		input = new InputHandler(this);
		level = new Level(64, 64);
	}

	public synchronized void start() {
		running = true;
		new Thread(this).start();
	}

	public synchronized void stop() {
		running = false;
	}

	public void run() {
		long lastTime = System.nanoTime();
		double nsPerTick = 1000000000.0 / 60.0; // how many nano secs per tick

		int ticks = 0; // how many updates
		int frames = 0; // current fps

		long lastTimer = System.currentTimeMillis(); // time to reset data
		double delta = 0; // how many unprocessed nano seconds so far

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
	
	private int x = 0;
	private int y = 0;

	// tick method updates the entire game
	public void tick() {
		tickcount++;
		//the following should move the entire screen...
		if (input.up.isPressed()) {
			y--;
		}
		if (input.down.isPressed()) {
			y++;
		}
		if (input.left.isPressed()) {
			x--;
		}
		if (input.right.isPressed()) {
			x++;
		}
		level.tick();
	}

	// print out the updates
	public void render() {
		BufferStrategy bs = getBufferStrategy(); // organizer

		if (bs == null) {
			createBufferStrategy(3); // enables triple buffering
			return;
		}
		
		int xOffset = x - (screen.xOffset / 2);
		int yOffset = y - (screen.yOffset / 2);
		
		level.renderTiles(screen, xOffset, yOffset);
		
		for(int x = 0; x < level.width; x++){
			int color = Colors.get(-1, -1, -1, 000);
			if(x % 10 == 0 && x != 0)
				color = Colors.get(-1,-1,-1, 500);
			Font.render((x % 10) + "", screen, 0 + (x*8), 0, color);
		}
		
		String msg = "This is our game!";
		Font.render(msg, screen,screen.xOffset +screen.width/2, screen.yOffset+screen.height/2 - (msg.length()*8/2), Colors.get(-1, -1, -1, 000));
		
		for(int y = 0; y < screen.height; y++){
			for(int x = 0; x < screen.width; x++){
				int colorCode = screen.pixels[x + y * screen.width];
				if(colorCode < 255) pixels[x + y * WIDTH] = colors[colorCode];
			}
		}
		Graphics g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		g.dispose(); // free up memory
		bs.show(); // show contents of the buffer
	}

	public static void main(String[] args) {
		new GameRunner().start();
	}
}
