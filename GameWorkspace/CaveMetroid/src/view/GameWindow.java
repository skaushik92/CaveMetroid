
package view;

import java.awt.Color;

import javax.swing.JFrame;

public class GameWindow extends JFrame
{
	/**
	 * All classes will have the same version so as to keep
	 * consistency within all classes.
	 */
	private static final long	serialVersionUID	= Constants.serialVersionUID;

	public static GameWindow currentWindow;

	public GameWindow ( GameView view )
	{
		/*
		 * Constructs the window with the title being the
		 * title defined in the Constants class.
		 */
		super ( Constants.MAIN_WINDOW_TITLE );
		init ( view );
	}



	/*
	 * Initializes the window.
	 */
	private void init ( GameView view )
	{
		setBackground ( Color.BLACK );
		/*
		 * The KeyboardManager keeps track of the key inputs,
		 * so it must be listening to the events that are
		 * coming from this main window.
		 */
		setSize ( Constants.WINDOW_HEIGHT, Constants.WINDOW_WIDTH );
		setResizable ( false );
		setUndecorated ( true );
		setIgnoreRepaint ( true );
		setLocation ( Constants.WINDOW_POSITION_X, Constants.WINDOW_POSITION_Y );


		add (view);
		addKeyListener ( view.getController().getKeyboardListener() );
		
		
		pack();
		currentWindow = this;
	}
}
