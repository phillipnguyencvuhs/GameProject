package Main;
import gfx.Screen;
import gfx.SpriteSheet;
import java.awt.*;
import java.awt.image.*;
import javax.swing.JFrame;

public class GameRunner extends Canvas implements Runnable {
	
	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 160;
	public static final int HEIGHT = WIDTH / 12 * 9;
	public static int SCALE = 3;
	public static final String NAME = "placeholder";
	
	private JFrame frame;
	public boolean running = true;
	public int tickcount = 0;
	
	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT,
			BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer())
			.getData(); //represents how many pixels are inside image
	
	private Screen screen;
	//private SpriteSheet spritesheet = new SpriteSheet("/sprite_sheet.png");
	
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

	public void init(){
		screen = new Screen(WIDTH, HEIGHT, new SpriteSheet("/sprite_sheet.png"));
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
	
	//tick method updates the entire game
	public void tick() {
		tickcount++;
		//screen.xOffset++;
		//screen.yOffset++;
	}
	
	//print out the updates
	public void render() {
		BufferStrategy bs = getBufferStrategy(); //organizer

		if (bs == null) {
			createBufferStrategy(3); // enables triple buffering
			return;
		}
		
		screen.render(pixels, 0, WIDTH);
		
		Graphics g = bs.getDrawGraphics();
		g.drawRect(0, 0, getWidth(), getHeight());
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		g.dispose(); // free up memory
		bs.show(); // show contents of the buffer
	}

	public static void main(String[] args) {
		new GameRunner().start();
	}
}
