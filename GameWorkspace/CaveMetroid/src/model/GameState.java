
package model;

import java.util.ArrayList;
import java.util.List;

import controller.input.InputChange;

import view.Constants;

import log.Log;
import model.entities.CameraEntity;
import model.entities.Entity;
import model.entities.ViewableEntity;
import model.entities.blocks.BackgroundBlock;
import model.entities.blocks.BlockManager;
import model.entities.gameentities.Player;
import model.managers.EntityManager;
import model.managers.LevelManager;
import model.physics.Position;
import model.time.GameTime;


public class GameState
{
	/**
	 * All classes will have the same version so as to keep
	 * consistency within all classes.
	 */
	private static final long	serialVersionUID	= Constants.serialVersionUID;

	LevelManager				lvlManager;


	public GameState ( )
	{
		Log.v ( "Program State", "GameState.Constructor(1)" );
	}



	public GameState ( int levelWidth, int levelHeight )
	{
		Log.v ( "Program State", "GameState.Constructor(2)" );
	}



	public GameState update ( GameTime gameTime, InputChange inputChange )
	{
		Log.v ( "Program State", "GameState.update" );

		GameState newState = new GameState ( );

		EntityManager.update ( gameTime, inputChange, this );

		return newState;
	}



	public List < Entity > entities ( )
	{
		return new ArrayList < Entity > ( EntityManager.allOfType ( Entity.class ) );
	}



	public Player getPlayer ( )
	{
		return EntityManager.getSingleton ( Player.class );
	}



	/**
	 * Returns the entities in order of drawing. The background
	 * elements are in the beginning of the list, while the
	 * foreground elements are at the end. The elements are
	 * immutable.
	 */

	public List < ViewableEntity > viewableEntities ( )
	{
		ArrayList < ViewableEntity > entities = new ArrayList < ViewableEntity > ( );

		CameraEntity ce = EntityManager.getSingleton ( CameraEntity.class );
		Position cameraPos = ce.getPosition ( );

		entities.addAll ( BlockManager.getViewableBlocks ( cameraPos ) );
		entities.addAll ( EntityManager.allOfTypeExcept ( ViewableEntity.class, BackgroundBlock.class ) );

		return entities;
	}



	public CameraEntity getCamera ( )
	{
		return EntityManager.getSingleton ( CameraEntity.class );
	}
}