package Main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

//Every time a key is pressed this class is called
public class InputHandler implements KeyListener {
	// The listener interface for receiving keyboard events (keystrokes).

	// adds the input handler to the main game
	public InputHandler(GameRunner game) {
		game.addKeyListener(this);
	}

	public class Key {
		private int numTimesPressed = 0;
		private boolean pressed = false;

		public int getNumTimesPressed() {
			return numTimesPressed;
		}

		public boolean isPressed() {
			return pressed;
		}

		public void toggle(boolean isPressed) {
			pressed = isPressed;
			if (isPressed) {
				numTimesPressed++;
			}
		}
	}

	public Key up = new Key();
	public Key down = new Key();
	public Key left = new Key();
	public Key right = new Key();

	@Override
	public void keyTyped(KeyEvent e) {
		toggleKey(e.getKeyCode(), true);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		toggleKey(e.getKeyCode(), false);
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	public void toggleKey(int keyCode, boolean isPressed) {
		// WSAD controls
		if (keyCode == KeyEvent.VK_W) {
			up.toggle(isPressed);
		}
		if (keyCode == KeyEvent.VK_S) {
			down.toggle(isPressed);
		}
		if (keyCode == KeyEvent.VK_A) {
			left.toggle(isPressed);
		}
		if (keyCode == KeyEvent.VK_D) {
			right.toggle(isPressed);
		}
	}
}
