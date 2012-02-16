
package controller;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import controller.input.keyboard.KeyboardManager;
import controller.input.mouse.MouseManager;
import log.Log;
import model.GameModel;
import model.GameState;
import model.entities.ViewableEntity;
import model.physics.Position;
import view.Frame;
import view.GameView;


public class GameController implements Runnable
{
	private GameModel	model;
	private GameView	view;



	public GameController ( GameModel model, GameView view )
	{
		Log.v ( "Program State", "GameController.Constructor" );
		this.model = model;
		this.view = view;
		init ( );
	}



	private void init ( )
	{
		Log.v ( "Program State", "GameController.init" );
		model.setController ( this );
		view.setController ( this );
	}



	@Override
	public void run ( )
	{
		Log.d ( "Program State", "GameController.run" );
		while ( true )
		{
			/*
			 * The controller takes the model information
			 * from the current state and draws the entities
			 * into the view using the sprite within each
			 * entity.
			 */
			GameState currState = model.getState ( );
			Frame currFrame = generateFrame ( currState );
			view.setFrame ( currFrame );
			view.repaint ( );
		}
	}



	private Frame generateFrame ( GameState state )
	{
		Log.v ( "Program State", "GameController.generateFrame" );
		Position topLeftPos = state.getCamera ( ).getPosition ( );
		Frame f = new Frame ( topLeftPos );
		for (ViewableEntity e : state.viewableEntities ( ))
		{
			f.addSprite ( e.getSprite ( ), e.getPosition ( ) );
		}
		return f;
	}



	public MouseListener getMouseListener ( )
	{
		return MouseManager.getInstance ( ).getMouseListener ( );
	}



	public MouseMotionListener getMouseMotionListener ( )
	{
		return MouseManager.getInstance ( ).getMouseListener ( );
	}



	public KeyListener getKeyboardListener ( )
	{
		return KeyboardManager.getInstance ( ).getKeyListener ( );
	}
}