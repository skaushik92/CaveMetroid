
package model.entities.gameentities;

import io.ContentManager;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import model.GameState;
import model.entities.CollidableEntity;
import model.entities.EntityAttributes;
import model.entities.ViewableEntity;
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

	private static final Force			GRAVITY			= new Force ( 0, -100.0f );

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
}