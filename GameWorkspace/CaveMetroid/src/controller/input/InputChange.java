package controller.input;

import controller.input.keyboard.Key;

public class InputChange
{
	private InputState last;
	private InputState current;
	
	public InputChange () {
		last = new InputState();
		current = new InputState();
	}
	
	public InputChange ( InputState lastState, InputState currentState) {
		last = lastState;
		current = currentState;
	}
	
	public InputChange ( InputChange lastChange, InputState currentState) {
		last = lastChange.current;
		current = currentState;
	}

	public boolean justPressed (Key key) {
		return last.getKeyState ( ).isUp(key) && current.getKeyState ( ).isDown(key);
	}
	
	public boolean justReleased ( Key key )
	{
		return last.getKeyState ( ).isDown(key) && current.getKeyState ( ).isUp(key);
	}
	
	public InputState getCurrentState() {
		return current;
	}
	
	public String toString () {
		return "" + last + " -> " + current;
	}

	public boolean isHeldDown ( Key key )
	{
		return last.getKeyState ( ).isDown ( key ) && current.getKeyState ( ).isDown ( key );
	}
}
