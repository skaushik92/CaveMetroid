
package application;

import controller.GameController;
import log.Log;
import model.GameModel;
import view.Constants;
import view.GameView;
import view.GameWindow;


public class Game
{
	/**
	 * All classes will have the same version so as to keep
	 * consistency within all classes.
	 */
	private static final long	serialVersionUID	= Constants.serialVersionUID;

	private GameModel			model;
	private GameView			view;
	private GameController		controller;
	private GameWindow			window;
	private Thread				modelThread;
	private Thread				controllerThread;



	public static void play ( String [] args )
	{
		Log.init ( );
		Log.v ( "Program State", "Game.play" );
		Game g = new Game ( );
		g.start ( );
	}



	public Game ( )
	{
		Log.v ( "Program State", "Game.Constructor" );

		/*
		 * The game consists of a Model of the game consisting
		 * of game entities (e.g. Player, Enemies etc)
		 */

		model = new GameModel ( );

		/*
		 * The game also consists of the visual representation
		 * of the game, (i.e. an drawing of the game world on
		 * the screen)
		 */

		view = new GameView ( );

		/*
		 * The game's view and model are updated using the
		 * controller which accesses the model at a routine
		 * time and updates the view based on information
		 * taken from the model
		 */

		controller = new GameController ( model, view );

		// The window will contain the view of the game

		view.directInputToController();
		
		window = new GameWindow ( view );
		window.setVisible ( true );

		/*
		 * The model runs on a separate thread since its
		 * processing is not dependent on the existence of a
		 * view
		 */

		modelThread = new Thread ( model );
		modelThread.setName ( "Model Thread" );
		modelThread.setPriority ( 10 );
		
		/*
		 * The controller runs on a separate thread accessing
		 * the model and modifying the view
		 */

		controllerThread = new Thread ( controller );
		controllerThread.setName ( "Controller Thread" );
		controllerThread.setPriority ( 1 );
		
		/*
		 * The view does not run on a separate thread because
		 * it should only be updated when the controller wants
		 * it to be updated.
		 */
	}



	/**
	 * Starts the game.
	 */
	public void start ( )
	{
		Log.v ( "Program State", "Game.start" );
		Log.v ( "Thread Info", "modelThread.start" );
		
		modelThread.start ( );
		Log.v ( "Thread Info", "controllerThread.start" );
		controllerThread.start ( );
	}
	/*
	 * public void pause () { modelThread.suspend();
	 * controllerThread.suspend(); } public void resume () {
	 * modelThread.resume(); controllerThread.resume(); }
	 * public void end () { modelThread.destroy();
	 * controllerThread.destroy(); }
	 */
}