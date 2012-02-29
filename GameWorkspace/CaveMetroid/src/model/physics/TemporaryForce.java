
package model.physics;

import model.entities.Entity;


public class TemporaryForce extends Force implements Entity
{
	private boolean turnOff;
	
	public TemporaryForce ( Vector appliedForce )
	{
		super ( appliedForce );
		turnOff = false;
	}

	public TemporaryForce ( Float x, Float y )
	{
		super ( x, y );
		turnOff = false;
	}
	
	public void turnOff ( ) {
		turnOff = true;
	}
	
	@Override
	public boolean shouldDestroy ( )
	{
		return turnOff;
	}
}
