
package model.entities.gameentities;

import java.util.EnumSet;

import controller.input.InputChange;
import controller.input.keyboard.Key;
import controller.input.keyboard.KeyState;
import log.Log;
import model.GameState;
import model.entities.EntityAttributes;
import model.graphics.Sprite;
import model.physics.Position;
import model.physics.Vector;
import model.physics.collision.CollisionShape;
import model.time.GameTime;
import model.time.TimeInstant;
import view.Constants;


public class Player extends AbstractGameEntity
{
	/**
	 * All classes will have the same version so as to keep
	 * consistency within all classes.
	 */
	private static final long	serialVersionUID	= Constants.serialVersionUID;

	private static final float	DEFAULT_SPEED		= 100.0f;

	private static final float	CHARGE_DURATION	= 1.0f;
	private static final float	JUMP_TIME			= 1.0f;

	private boolean			charging;
	private boolean			movingRight;
	private boolean			jumping;

	private TimeInstant			chargeStartTime;

	private TimeInstant			jumpStartTime;



	public Player ( )
	{
		super ( );
		Log.d ( "Program State", "Player.Constructor" );

		loadAnimation ( "run" );
		loadAnimation ( "idle" );

		/*
		 * Initializing the default animation variables.
		 */

		setAnimation ( "run" );
		currentAnimation.setFrameTimes ( 0.1f );
		currentAnimation.setRepeat ( true );

		/*
		 * Initializing charge variables.
		 */

		charging = false;
		movingRight = false;
		chargeStartTime = TimeInstant.ZERO;

		/*
		 * Initalizing player characteristics.
		 */

		myPosition = new Position ( 600, 600 );

	}



	@Override
	public void update ( GameTime gameTime, InputChange inputChange, GameState gameState )
	{
		super.update ( gameTime, inputChange, gameState );
		Log.v ( "Program State", "Player.update" );

		KeyState currentKeyState = inputChange.getCurrentState ( ).getKeyState ( );

		float elapsedSeconds = gameTime.getElapsedTime ( ).getSeconds ( );
		/*
		 * Handles player state given user input through
		 * character keys
		 */

		if ( !charging && inputChange.justPressed ( Key.C ) )
		{
			charging = true;
			chargeStartTime = gameTime.getCurrentTime ( );
			updatePlayerVelocity ( );
		}

		if ( charging && CHARGE_DURATION < gameTime.getTimeFrom ( chargeStartTime ).getSeconds ( ) )
		{
			charging = false;
			updatePlayerVelocity ( );
		}

		if ( charging )
		{
			currentAnimation.moveForward ( elapsedSeconds * ( getPlayerSpeedScaleFactor ( ) - 1 ) );
		}

		/*
		 * Handles player's left and right movement given user
		 * input through arrow keys
		 */

		if ( !currentKeyState.anyPressed ( Key.LEFT, Key.RIGHT ) || currentKeyState.allPressed ( Key.LEFT, Key.RIGHT ) )
		{
			charging = false;
			boolean hadFlipped = currentAnimation.getFlip ( );
			setAnimation ( "idle" );
			currentAnimation.setFlip ( hadFlipped );

			myVelocity = new Vector ( 0, myVelocity.getY ( ) );
		}
		else if ( inputChange.justPressed ( Key.RIGHT ) || ( inputChange.justReleased ( Key.LEFT ) && currentKeyState.isDown ( Key.RIGHT ) ) )
		{
			setAnimation ( "run" );
			movingRight = true;
			currentAnimation.setFlip ( movingRight );

			updatePlayerVelocity ( );
		}
		else if ( inputChange.justPressed ( Key.LEFT ) || ( inputChange.justReleased ( Key.RIGHT ) && currentKeyState.isDown ( Key.LEFT ) ) )
		{
			setAnimation ( "run" );
			movingRight = false;
			currentAnimation.setFlip ( movingRight );
			updatePlayerVelocity ( );
		}

		/*
		 * Handles player's jumping capabilities
		 */

		if ( !jumping && inputChange.justPressed ( Key.UP ) )
		{
			jumping = true;
			jumpStartTime = gameTime.getCurrentTime ( );
		}

		if ( jumping )
		{
			float jumpSeconds = gameTime.getTimeFrom ( jumpStartTime ).getSeconds ( );
			if ( jumpSeconds > JUMP_TIME )
			{
				jumping = false;
			}
			else
			{

			}
		}
	}



	private void updatePlayerVelocity ( )
	{
		myVelocity = new Vector ( dirSign ( ) * getCurrentMovementMagnitude ( ), myVelocity.getY ( ) );
	}



	private int dirSign ( )
	{
		return ( movingRight ? 1 : -1 );
	}



	private float getCurrentMovementMagnitude ( )
	{
		return DEFAULT_SPEED * getPlayerSpeedScaleFactor ( );
	}



	private float getPlayerSpeedScaleFactor ( )
	{
		return charging ? 4.0f : 1.0f;
	}



	@Override
	public boolean shouldDestroy ( )
	{
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public Position getPosition ( )
	{
		// TODO Auto-generated method stub
		return myPosition;
	}



	@Override
	public Sprite getSprite ( )
	{
		Log.v ( "Program State", "Player.getSprite" );
		// TODO Auto-generated method stub
		return currentAnimation.getCurrentSprite ( );
	}



	@Override
	public EnumSet < EntityAttributes > entityAttributes ( )
	{
		return EnumSet.of ( EntityAttributes.Physical );
	}



	@Override
	public CollisionShape getCollisionShape ( )
	{
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public int zValue ( )
	{
		return 100;
	}
}