
package view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import log.Log;

import controller.GameController;


public class GameView extends JPanel
{
	/**
	 * All classes will have the same version so as to keep
	 * consistency within all classes.
	 */
	private static final long	serialVersionUID	= Constants.serialVersionUID;

	private GameController		controller;
	private Frame				currFrame;



	public GameView ( )
	{
		Log.v ( "Program State", "GameView.Constructor" );

		controller = null;
		currFrame = new Frame ( );

		init ( );
	}



	private void init ( )
	{
		Log.v ( "Program State", "GameView.init" );
		setPreferredSize ( new Dimension ( Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT ) );
	}



	@Override
	public synchronized void paintComponent ( Graphics g1 )
	{
		Log.v ( "Program State", "GameView.paintComponent" );
		super.paintComponent ( g1 );
		Graphics2D g = (Graphics2D) g1;

		g.drawImage ( currFrame.getFrameImage ( ), null, 0, 0 );
	}



	public int width ( )
	{
		return getWidth ( );
	}



	public int height ( )
	{
		return getHeight ( );
	}



	public void setController ( GameController gameController )
	{
		controller = gameController;
	}



	public synchronized void setFrame ( Frame frame )
	{
		Log.v ( "Program State", "GameView.setFrame" );
		currFrame = frame;
	}



	public GameController getController ( )
	{
		return controller;
	}



	public void directInputToController ( )
	{
		//addMouseListener ( controller.getMouseListener ( ) );
		//addMouseMotionListener ( controller.getMouseMotionListener ( ) );
	}
}
