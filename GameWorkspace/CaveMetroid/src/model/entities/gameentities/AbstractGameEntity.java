
package model.entities.gameentities;

import io.ContentManager;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import log.Log;
import model.GameState;
import model.entities.CollidableEntity;
import model.entities.EntityAttributes;
import model.entities.ViewableEntity;
import model.entities.blocks.BlockAttributes;
import model.entities.blocks.BlockCoordinate;
import model.entities.blocks.BlockManager;
import model.graphics.Animation;
import model.graphics.Sprite;
import model.physics.Force;
import model.physics.Position;
import model.physics.Vector;
import model.physics.collision.CollisionShape;
import model.time.GameTime;
import controller.input.InputChange;


/**
 * All game entities have the following characteristics: - Have
 * an Animation (but can be just one frame) - Have a Position
 * (but can be unchanging) -
 */

public abstract class AbstractGameEntity implements ViewableEntity, CollidableEntity
{

	/**
	 * Physics variables
	 */

	private static final Force			GRAVITY			= new Force ( 0, 0.0f );

	protected Position					myPosition;
	protected Vector					myVelocity;
	protected Vector					myAcceleration;
	protected List < Force >				appliedForces;
	protected float					myMass;

	/**
	 * Animation variables
	 */

	private static final String			DEFAULT_ANIMATION	= "error";
	protected Animation					currentAnimation;
	protected Map < String , Animation >	myAnimations;



	protected AbstractGameEntity ( )
	{

		/*
		 * Initializing Physics instance variables
		 */

		myPosition = Position.ZERO;
		myVelocity = Vector.ZERO;
		myAcceleration = Vector.ZERO;

		appliedForces = new LinkedList < Force > ( );

		if ( entityAttributes ( ).contains ( EntityAttributes.Physical ) )
			appliedForces.add ( GRAVITY );

		myMass = 1;

		/*
		 * Initializing Animation instance variables
		 */

		currentAnimation = ContentManager.loadAnimation ( DEFAULT_ANIMATION );
		myAnimations = new HashMap < String , Animation > ( );
		myAnimations.put ( DEFAULT_ANIMATION, ContentManager.loadAnimation ( DEFAULT_ANIMATION ) );
	}



	@Override
	public void update ( GameTime gameTime, InputChange inputChange, GameState gameState )
	{
		EnumSet < EntityAttributes > attrs = entityAttributes ( );
		float seconds = gameTime.getElapsedTime ( ).getSeconds ( );

		/*
		 * Updating physics
		 */

		if ( attrs.contains ( EntityAttributes.Physical ) )
		{
			myPosition = myPosition.plus ( myVelocity.scale ( seconds ) );
			myVelocity = myVelocity.plus ( myAcceleration.scale ( seconds ) );

			for (Force f : appliedForces)
				f.update ( gameTime, inputChange, gameState );

			Force resultantForce = Force.sum ( appliedForces );

			myAcceleration = resultantForce.getForceVector ( );

			if ( attrs.contains ( ( EntityAttributes.Ground_Collidable ) ) )
			{
				/*
				 * Checking for collision with ground
				 * blocks.
				 */

				BlockManager.coordinateAtWorldPosition ( myPosition );

				/*
				 * Checking each direction!
				 */

				// UP
				if ( myVelocity.getY ( ) > 0 )
				{

				}
				// DOWN
				if ( myVelocity.getY ( ) < 0 )
				{

				}
				// RIGHT
				if ( myVelocity.getX ( ) > 0 )
				{

				}
				// LEFT
				if ( myVelocity.getX ( ) < 0 )
				{
					BlockCoordinate currentClosestLeft = BlockManager.coordinateAtWorldPosition ( new Position ( leftMostX ( ), myPosition.getY ( ) ) );
					int currCol = currentClosestLeft.getCol ( );
					int currRow = currentClosestLeft.getRow ();
					
					
					if ( BlockManager.validCoordinate ( currCol, currRow ) && BlockManager.containsBlockOfTypeAt ( currCol, currRow , BlockAttributes.Full_Collidable ) )
					{
						Log.d ( "Collision Information", "Collided with a block at (" + currCol + ", " + currRow + ")" );
						myVelocity = new Vector ( 0, myVelocity.getY() );
						myPosition = new Position ( (currCol + 1)* 50 + ( myPosition.getX() - leftMostX () ) , myPosition.getY() );
					}
					/*
					BlockCoordinate currentClosestLeft = BlockManager.coordinateAtWorldPosition ( new Position ( leftMostX ( ), myPosition.getY ( ) ) );
					BlockCoordinate currentFarthestLeft = BlockManager.coordinateAtWorldPosition ( new Position ( leftMostX ( ) + myVelocity.getX ( ), myPosition.getY ( ) ) );

					BlockCoordinate currentClosestTopLeft = BlockManager.coordinateAtWorldPosition ( new Position ( leftMostX (), topMostY() ) );
					BlockCoordinate currentClosestBottomLeft = BlockManager.coordinateAtWorldPosition ( new Position ( leftMostX (), bottomMostY() ) );
					
					int closestLeftCol = currentClosestLeft.getCol ( );
					//int farthestLeftCol = currentFarthestLeft.getCol ( );
					int farthestLeftCol = currentClosestLeft.getCol ( );
					
					int closestTopLeftRow = currentClosestTopLeft.getRow ();
					int closestTopBottomRow = currentClosestTopLeft.getRow ();
					
					Log.d ( "Collision Range Check Information", "Current Closest Left: " + closestLeftCol );
					Log.d ( "Collision Range Check Information", "Current FarthestLeft: " + farthestLeftCol );
					
					for ( int currCol = closestLeftCol - 1; currCol >= farthestLeftCol; currCol-- )
					{
						for ( int currRow = closestTopBottomRow; currRow <= closestTopLeftRow; currRow++ ) {
							if ( BlockManager.validCoordinate ( currCol, currRow )) {
								if ( BlockManager.containsBlockOfTypeAt ( currCol, currRow , BlockAttributes.Full_Collidable ) )
								{
									Log.d ( "Collision Information", "Collided with a block at (" + currCol + ", " + currRow + ")" );
								}
							}
						}
					}
					*/
				}

			}
		}

		/*
		 * Updating animation
		 */

		currentAnimation.moveForward ( seconds );
	}



	public abstract boolean shouldDestroy ( );



	public abstract EnumSet < EntityAttributes > entityAttributes ( );



	protected void loadAnimation ( String animationName )
	{
		if ( !myAnimations.containsKey ( animationName ) )
			myAnimations.put ( animationName, ContentManager.loadAnimation ( animationName ) );
	}



	protected void setAnimation ( String animationName )
	{
		if ( currentAnimation.getAnimationName ( ).equals ( animationName ) )
			return;

		if ( !myAnimations.containsKey ( animationName ) )
			loadAnimation ( animationName );

		currentAnimation = myAnimations.get ( animationName );
		currentAnimation.reset ( );
	}



	@Override
	public Position getPosition ( )
	{
		return myPosition;
	}



	@Override
	public Sprite getSprite ( )
	{
		return currentAnimation.getCurrentSprite ( );
	}



	@Override
	public abstract CollisionShape getCollisionShape ( );



	public float leftMostX ( )
	{
		return myPosition.getX ( ) - getCollisionShape ( ).getWidth ( ) / 2;
	}



	public float rightMostX ( )
	{
		return myPosition.getX ( ) + getCollisionShape ( ).getWidth ( ) / 2;
	}



	public float topMostY ( )
	{
		return myPosition.getY ( ) + getCollisionShape ( ).getHeight ( ) / 2;
	}



	public float bottomMostY ( )
	{
		return myPosition.getY ( ) - getCollisionShape ( ).getHeight ( ) / 2;
	}
}