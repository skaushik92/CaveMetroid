
package model;

import view.Constants;
import log.Log;
import model.entities.CameraEntity;
import model.entities.Entity;
import model.entities.blocks.BlockManager;
import model.entities.blocks.GroundBlock;
import model.entities.gameentities.FullScreenEntity;
import model.entities.gameentities.Player;
import model.entities.gameentities.TextBox;
import model.managers.EntityManager;
import model.physics.Position;
import model.time.GameTime;
import controller.GameController;
import controller.input.InputChange;
import controller.input.InputState;


/**
 * The game model continually updates the game by constructing a
 * new state that is an updated version of the last state.
 */
public class GameModel implements Runnable
{
	/**
	 * All classes will have the same version so as to keep
	 * consistency within all classes.
	 */
	private static final long	serialVersionUID	= Constants.serialVersionUID;
	GameController				controller;
	boolean					running;
	long						lastTime;
	GameState					lastState;
	GameState					currState;
	GameTime					lastGameTime;
	GameTime					gameTime;
	InputChange				inputChange;



	public GameModel ( )
	{
		Log.d ( "Program State", "GameModel.Constructor" );
		lastState = initialState ( );
		currState = null;
		running = true;
		lastTime = System.currentTimeMillis ( );
		lastGameTime = new GameTime ( );
		gameTime = new GameTime ( lastGameTime );
		inputChange = new InputChange ( new InputState ( ), InputState.currentInputState ( ) );
	}



	public GameState initialState ( )
	{
		Log.v ( "Program State", "GameModel.initialState" );
		int numBlockCols = 10;
		int numBlockRows = 10;
		BlockManager.initialize ( numBlockCols, numBlockRows );

		GameState newState = new GameState ( numBlockCols, numBlockRows );
		TextBox longBox = EntityManager.createEntity ( TextBox.class, new Position ( 400, 400 ), "Hello World! Will this work?" );
		TextBox rectBox = EntityManager.createEntity ( TextBox.class, new Position ( 300, 300 ), "This is just another test, but I don't really know how well it'll work.", 100 );
		Player player = EntityManager.createEntity ( Player.class );
		FullScreenEntity fullScreen = EntityManager.createEntity ( FullScreenEntity.class );
		CameraEntity camera = EntityManager.createEntity ( CameraEntity.class, new Position ( 0, Constants.WINDOW_HEIGHT ) );

		BlockManager.setBlocksInRegion ( 0, 0, numBlockCols, numBlockRows, GroundBlock.class, "castle-ground" );
		// BlockManager.setBlock ( 10, 0, GroundBlock.class,
		// "castle-ground");

		Entity [] entities = new Entity [ ] { longBox, rectBox, player, fullScreen, camera };

		for (Entity e : entities)
			Log.v ( "Initial Entity Information", e.getClass ( ).getName ( ) );

		return newState;
	}



	@Override
	public void run ( )
	{
		Log.d ( "Program State", "GameModel.run" );
		while ( running )
		{
			update ( );
		}
	}



	private void update ( )
	{
		gameTime = new GameTime ( lastGameTime );
		inputChange = new InputChange ( inputChange, InputState.currentInputState ( ) );
		currState = lastState.update ( gameTime, inputChange );
		/*
		 * During this stage, the previous state needs to be
		 * updated, only after which, access to the state can
		 * occur.
		 */
		lastGameTime = gameTime;
		updateState ( );
		rest ( );
	}



	private synchronized void updateState ( )
	{
		Log.v ( "Program State", "GameModel.updateState" );
		lastState = currState;
	}



	public void pause ( )
	{
		Log.d ( "Program State", "GameModel.pause" );
		running = false;
	}



	public void resume ( )
	{
		Log.d ( "Program State", "GameModel.resume" );
		running = true;
	}



	public void setController ( GameController gameController )
	{
		controller = gameController;
	}



	public synchronized GameState getState ( )
	{
		Log.v ( "Program State", "GameModel.getState" );
		/*
		 * GameController calls this method requesting the
		 * state, but has to wait until the updating is
		 * finished.
		 */
		return lastState;
	}



	private void rest ( )
	{
		try
		{
			Thread.sleep ( 1 );
		}
		catch ( Exception e )
		{
		}
	}
}