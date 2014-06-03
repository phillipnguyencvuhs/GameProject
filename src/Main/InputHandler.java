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

	///////////////////////////////////////////////////////////
	// Nested Class used only in input handler
	///////////////////////////////////////////////////////////
	public class Key {
		
		private int numTimesPressed = 0;
		private boolean pressed = false;

		//getter method
		public int getNumTimesPressed() {
			return numTimesPressed;
		}

		//getter method
		public boolean isPressed() {
			return pressed;
		}

		//sets pressed to either true or false
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

	//not used
	public void keyTyped(KeyEvent e) {
		
	}

	//checks if a key is pressed
	public void keyPressed(KeyEvent e) {
		toggleKey(e.getKeyCode(), true);
	}

	//checks if a key is released
	public void keyReleased(KeyEvent e) {
		toggleKey(e.getKeyCode(), false);
	}

	// WSAD and Arrow-key controls
	public void toggleKey(int keyCode, boolean isPressed) {
		if (keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_UP ) {
			up.toggle(isPressed);
		}
		if (keyCode == KeyEvent.VK_S || keyCode == KeyEvent.VK_DOWN) {
			down.toggle(isPressed);
		}
		if (keyCode == KeyEvent.VK_A || keyCode == KeyEvent.VK_LEFT) {
			left.toggle(isPressed);
		}
		if (keyCode == KeyEvent.VK_D || keyCode == KeyEvent.VK_RIGHT) {
			right.toggle(isPressed);
		}
	}
}
