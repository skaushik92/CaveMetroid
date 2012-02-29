
package model.physics;

import controller.input.InputChange;
import model.GameState;
import model.entities.Entity;
import model.time.GameTime;
import model.time.TimeInstant;


public class DecayingForce extends Force implements Entity
{

	/**
	 * Degradation means the force is reduced by (1 - factor)% every second.
	 */
	public static final float	DEFAULT_DEGRADATION			= 0.2f;
	public static final float	NEGLIGIBLE_FORCE_MAGNITUDE	= 1.0f;

	private float				factor;

	private Vector				originalForce;
	private TimeInstant			startTime;



	public DecayingForce ( Vector appliedForce )
	{
		super ( appliedForce );
		originalForce = appliedForce;
		factor = DEFAULT_DEGRADATION;
		startTime = null;
	}



	public DecayingForce ( Float x, Float y )
	{
		super ( x, y );
		originalForce = new Vector ( x, y );
		factor = DEFAULT_DEGRADATION;
		startTime = null;
	}



	public DecayingForce ( Vector appliedForce, Float degradationFactor )
	{
		super ( appliedForce );
		originalForce = appliedForce;
		this.factor = degradationFactor;
		startTime = null;
	}



	@Override
	public void update ( GameTime gameTime, InputChange inputChange, GameState gameState )
	{
		if ( startTime == null )
			startTime = gameTime.getCurrentTime ( );
		float secs = gameTime.getTimeFrom ( startTime ).getSeconds ( );

		this.getForceVector ( ).setVector ( originalForce.scale ( 1 - secs * factor ) );
	}



	@Override
	public boolean shouldDestroy ( )
	{
		return getForceVector ( ).magnitude ( ) < NEGLIGIBLE_FORCE_MAGNITUDE;
	}

}
