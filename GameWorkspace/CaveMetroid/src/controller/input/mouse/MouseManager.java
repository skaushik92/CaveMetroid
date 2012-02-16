
package controller.input.mouse;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;

import log.Log;
import model.physics.Position;

import controller.Constants;


public class MouseManager
{

	/**
	 * All classes will have the same version so as to keep
	 * consistency within all classes.
	 */

	private static final long	serialVersionUID	= Constants.serialVersionUID;

	private static MouseManager	singleton			= null;



	public static MouseManager getInstance ( )
	{
		if ( singleton == null )
			singleton = new MouseManager ( );
		return singleton;
	}


	private MouseState			currentState;
	private final MouseAdapter	mouseListener;



	private MouseManager ( )
	{
		mouseListener = new MyMouseListener ( );
		currentState = new MouseState ( );
	}



	public MouseAdapter getMouseListener ( )
	{
		return mouseListener;
	}


	private final class MyMouseListener extends MouseAdapter implements MouseMotionListener
	{
		@Override
		public void mouseClicked ( MouseEvent e )
		{
			recordEvent ( e );
		}



		@Override
		public void mouseDragged ( MouseEvent e )
		{
			recordEvent ( e );
		}



		@Override
		public void mouseExited ( MouseEvent e )
		{
			recordEvent ( e );
		}



		@Override
		public void mouseMoved ( MouseEvent e )
		{
			recordEvent ( e );
		}



		@Override
		public void mousePressed ( MouseEvent e )
		{
			recordEvent ( e );
		}



		@Override
		public void mouseReleased ( MouseEvent e )
		{
			recordEvent ( e );
		}



		@Override
		public void mouseWheelMoved ( MouseWheelEvent e )
		{
			recordEvent ( e );
		}



		private void recordEvent ( MouseEvent e )
		{
			int button = e.getButton ( );
			Point buttonPoint = e.getPoint ( );
			int eventType = e.getID ( );

			currentState.setPosition ( new Position ( e.getX ( ), e.getY ( ) ) );

			Log.v ( "Mouse Event", "Button#: " + button + ", Point: " + buttonPoint + ", EventType: " + eventType );
		}
	}



	public synchronized MouseState getCurrentState ( )
	{
		return currentState.clone ( );
	}
}
