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
	public static int scale = 3;
	
	private JFrame frame;
	public static final String NAME = "placeholder";
	
	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT,
			BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer())
			.getData();
	
	private Screen screen;
	
	public boolean running = true;
	public int tickcount = 0;

	public GameRunner() {
		setMinimumSize(new Dimension(WIDTH * scale, HEIGHT * scale));
		setMaximumSize(new Dimension(WIDTH * scale, HEIGHT * scale));
		setPreferredSize(new Dimension(WIDTH * scale, HEIGHT * scale));

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
				delta--;
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

	public void tick() {
		tickcount++;
	}

	public void render() {

		BufferStrategy bs = getBufferStrategy();

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
