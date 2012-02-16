
package controller.input.keyboard;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import log.Log;

import controller.Constants;


/**
 * Singleton KeyboardManager.
 * 
 * @author Kaushik
 * 
 */

public class KeyboardManager
{
	
	
	/**
	 * All classes will have the same version so as to keep
	 * consistency within all classes.
	 */
	private static final long		serialVersionUID	= Constants.serialVersionUID;

	private KeyState				currentState;

	private final KeyAdapter			keyboardListener;

	private static KeyboardManager	singleton			= null;

	public KeyAdapter getKeyListener() {
		return keyboardListener;
	}


	private KeyboardManager ( )
	{
		keyboardListener = new MyKeyListener ( );
		currentState = new KeyState ( );
		
	}



	public static KeyboardManager getInstance ( )
	{
		if ( singleton == null )
			singleton = new KeyboardManager ( );
		return singleton;
	}

	public KeyState getCurrentState ( )
	{
		return currentState.clone();
	}

	public String toString() {
		return currentState.toString ( );
	}
	
	private final class MyKeyListener extends KeyAdapter
	{
		@Override
		public void keyPressed ( KeyEvent e )
		{
			Log.v ( "User Input", "Pressed " + KeyEvent.getKeyText ( e.getKeyCode ( ) ) );
			currentState.setKey ( e.getKeyCode ( ) );
		}



		@Override
		public void keyReleased ( KeyEvent e )
		{
			Log.v ( "User Input", "Released " + KeyEvent.getKeyText ( e.getKeyCode ( ) ) );
			currentState.unsetKey ( e.getKeyCode () );
		}

	}


}