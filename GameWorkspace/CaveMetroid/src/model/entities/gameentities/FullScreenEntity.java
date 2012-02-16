
package model.entities.gameentities;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import view.Constants;
import view.GameWindow;

import log.Log;
import model.GameState;
import model.entities.Entity;
import model.time.GameTime;
import controller.input.InputChange;
import controller.input.keyboard.Key;


public class FullScreenEntity implements Entity
{

	boolean					isFullScreen	= false;
	boolean					applySwitch	= false;

	private GraphicsEnvironment	ge;
	private GraphicsDevice		gd;

	private int				windowedWindowHeight;
	private int				fullScreenWindowHeight;



	public FullScreenEntity ( )
	{
		ge = GraphicsEnvironment.getLocalGraphicsEnvironment ( );
		gd = ge.getDefaultScreenDevice ( );

		windowedWindowHeight = Constants.WINDOW_HEIGHT;
		fullScreenWindowHeight = Constants.MONITOR_HEIGHT;

		isFullScreen = false;
		applySwitch = false;
	}



	@Override
	public void update ( GameTime gameTime, InputChange inputChange, GameState gameState )
	{
		if ( applySwitch )
		{
			switchScreen ( );
			applySwitch = false;
		}
		if ( inputChange.justPressed ( Key.F11 ) )
		{
			applySwitch = true;
			gameState.getCamera ( ).addCameraYPosition ( ( isFullScreen ? -1 : 1 ) * ( fullScreenWindowHeight - windowedWindowHeight ) );
		}
	}



	public void switchScreen ( )
	{

		if ( isFullScreen )
		{
			if ( changeFromFullScreen ( ) )
			{
				isFullScreen = false;
			}
		}
		else
		{
			if ( changeToFullScreen ( ) )
			{
				isFullScreen = true;
			}
		}
	}



	private boolean changeFromFullScreen ( )
	{
		gd.setFullScreenWindow ( null );

		fullScreenWindowHeight = view.Constants.WINDOW_HEIGHT;
		Constants.WINDOW_HEIGHT = GameWindow.currentWindow.getBounds ( ).height;
		Constants.WINDOW_WIDTH = GameWindow.currentWindow.getBounds ( ).width;
		return true;
	}



	private boolean changeToFullScreen ( )
	{

		GameWindow window = GameWindow.currentWindow;

		if ( !gd.isFullScreenSupported ( ) )
		{
			Log.e ( "Full Screen Error", "Full-screen exclusive mode not supported" );
			return false;
		}
		else
		{

			gd.setFullScreenWindow ( window );

			windowedWindowHeight = Constants.WINDOW_HEIGHT;
			Constants.WINDOW_HEIGHT = window.getBounds ( ).height;
			Constants.WINDOW_WIDTH = window.getBounds ( ).width;

			return true;
		}

	}



	@Override
	public boolean shouldDestroy ( )
	{
		return false;
	}
}
