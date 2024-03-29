
package model.entities.gameentities;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.EnumSet;

import controller.input.InputChange;
import controller.input.keyboard.KeyState;
import log.Log;
import model.Action;
import model.Constants;
import model.GameState;
import model.entities.EntityAttributes;
import model.graphics.Sprite;
import model.managers.ButtonManager;
import model.managers.EntityManager;
import model.physics.DecayingForce;
import model.physics.Force;
import model.physics.Position;
import model.physics.TemporaryForce;
import model.physics.Vector;
import model.physics.collision.CollisionShape;
import model.time.GameTime;
import model.time.TimeInstant;


public class Player extends AbstractGameEntity
{
	/**
	 * All classes will have the same version so as to keep consistency within all classes.
	 */

	private static final CollisionShape	PLAYER_COLLISION_SHAPE	= new CollisionShape ( new Rectangle ( new Dimension ( 30, 48 ) ) );

	private static final long			serialVersionUID		= Constants.serialVersionUID;

	private static final float			DEFAULT_SPEED			= 200.0f;

	private static final float			CHARGE_DURATION		= 1.0f;

	private static final float			MAX_HOLD_TIME			= 0.2f;

	private boolean					charging;
	private boolean					movingRight;
	private boolean					jumping;
	private TemporaryForce				jumpForce;

	private TimeInstant					chargeStartTime;
	private TimeInstant					jumpStartTime;



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

		myPosition = new Position ( 200, 600 + Constants.BLOCK_HEIGHT / 2 );

	}



	@Override
	public void update ( GameTime gameTime, InputChange inputChange, GameState gameState )
	{
		super.update ( gameTime, inputChange, gameState );
		Log.v ( "Program State", "Player.update" );

		KeyState currentKeyState = inputChange.getCurrentState ( ).getKeyState ( );

		float elapsedSeconds = gameTime.getElapsedTime ( ).getSeconds ( );
		/*
		 * Handles player state given user input through character keys
		 */

		if ( !charging && inputChange.justPressed ( ButtonManager.get ( Action.Charge ) ) )
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
		 * Handles player's left and right movement given user input through arrow keys
		 */

		if ( !currentKeyState.anyPressed ( ButtonManager.get ( Action.MoveLeft ), ButtonManager.get ( Action.MoveRight ) ) || currentKeyState.allPressed ( ButtonManager.get ( Action.MoveLeft ), ButtonManager.get ( Action.MoveRight ) ) )
		{
			charging = false;
			boolean hadFlipped = currentAnimation.getFlip ( );
			setAnimation ( "idle" );
			currentAnimation.setFlip ( hadFlipped );

			myVelocity.setX ( 0 );
		}
		else if ( inputChange.justPressed ( ButtonManager.get ( Action.MoveRight ) ) || ( inputChange.justReleased ( ButtonManager.get ( Action.MoveLeft ) ) && currentKeyState.isDown ( ButtonManager.get ( Action.MoveRight ) ) ) )
		{
			setAnimation ( "run" );
			movingRight = true;
			currentAnimation.setFlip ( movingRight );

			updatePlayerVelocity ( );
		}
		else if ( inputChange.justPressed ( ButtonManager.get ( Action.MoveLeft ) ) || ( inputChange.justReleased ( ButtonManager.get ( Action.MoveRight ) ) && currentKeyState.isDown ( ButtonManager.get ( Action.MoveLeft ) ) ) )
		{
			setAnimation ( "run" );
			movingRight = false;
			currentAnimation.setFlip ( movingRight );
			updatePlayerVelocity ( );
		}

		/*
		 * Handles player's jumping capabilities
		 */
		if ( jumping )
		{
			float jumpSeconds = gameTime.getTimeFrom ( jumpStartTime ).getSeconds ( );
			/*
			if ( jumpSeconds < MAX_HOLD_TIME && inputChange.isHeldDown ( ButtonManager.get ( Action.Jump ) ) )
			{
			}
			else {
				jumpForce.turnOff();
			}
			
			
			 */
			jumping = false;
			/*
			if ( Math.abs(myVelocity.getY()) < application.Constants.EPSILON )
			{
				jumping = false;
			}
			*/
		}

		else if ( !jumping && inputChange.justPressed ( ButtonManager.get ( Action.Jump ) ) )
		{
			jumping = true;
			
			jumpStartTime = gameTime.getCurrentTime ( );
		
		}

	}



	private void updatePlayerVelocity ( )
	{
		myVelocity.setX ( dirSign ( ) * getCurrentMovementMagnitude ( ) );
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
		return false;
	}



	@Override
	public Position getPosition ( )
	{
		return myPosition;
	}



	@Override
	public Sprite getSprite ( )
	{
		Log.v ( "Program State", "Player.getSprite" );
		return currentAnimation.getCurrentSprite ( );
	}



	@Override
	public EnumSet < EntityAttributes > entityAttributes ( )
	{
		return EnumSet.of ( EntityAttributes.Physical, EntityAttributes.Ground_Collidable );
	}



	@Override
	public CollisionShape getCollisionShape ( )
	{
		return PLAYER_COLLISION_SHAPE;
	}



	@Override
	public int zValue ( )
	{
		return 100;
	}
}