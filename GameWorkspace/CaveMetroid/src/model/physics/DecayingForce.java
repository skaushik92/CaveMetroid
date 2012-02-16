
package model.physics;

import controller.input.InputChange;
import model.GameState;
import model.entities.Entity;
import model.time.GameTime;


public class DecayingForce extends Force implements Entity
{

	/**
	 * Degradation means the force is reduced by (1 - factor)%
	 * every second.
	 */
	public static final float	DEFAULT_DEGRADATION			= 0.2f;
	public static final float	NEGLIGIBLE_FORCE_MAGNITUDE	= 0.001f;

	private float				factor;



	public DecayingForce ( Vector appliedForce )
	{
		super ( appliedForce );
		factor = DEFAULT_DEGRADATION;
	}



	public DecayingForce ( float x, float y )
	{
		super ( x, y );
		factor = DEFAULT_DEGRADATION;
	}



	public DecayingForce ( Vector appliedForce, float degradationFactor )
	{
		super ( appliedForce );
		this.factor = degradationFactor;
	}



	@Override
	public void update ( GameTime gameTime, InputChange inputChange , GameState gameState )
	{
		float secs = gameTime.getElapsedTime ( ).getSeconds ( );
		this.getForceVector ( ).scale ( secs * ( 1 - factor ) );
	}
	
	

	@Override
	public boolean shouldDestroy ( )
	{
		return getForceVector ( ).magnitude ( ) < NEGLIGIBLE_FORCE_MAGNITUDE;
	}

}
