package controller.input;

import controller.input.keyboard.KeyState;
import controller.input.keyboard.KeyboardManager;
import controller.input.mouse.MouseManager;
import controller.input.mouse.MouseState;

public class InputState
{
	private KeyState keyState;
	private MouseState mouseState;
	
	public InputState() {
		keyState = new KeyState();
		mouseState = new MouseState();
	}
	
	public InputState ( InputState stateToCopy ) {
		keyState = stateToCopy.keyState.clone ( );
		mouseState = stateToCopy.mouseState.clone ();
	}
	
	public KeyState getKeyState ( )
	{
		return keyState;
	}
	
	public MouseState getMouseState ( )
	{
		return mouseState;
	}
	
	public static InputState currentInputState () {
		InputState curr = new InputState();
		curr.keyState = KeyboardManager.getInstance ( ).getCurrentState ( );
		curr.mouseState = MouseManager.getInstance ( ).getCurrentState ( );
		return curr;
	}
	
	public String toString () {
		return "K: " + keyState + ", M: " + mouseState;
	}
}
