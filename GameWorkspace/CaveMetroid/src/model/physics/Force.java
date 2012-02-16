
package model.physics;

import java.util.Collection;
import controller.input.InputChange;
import model.GameState;
import model.entities.Entity;
import model.time.GameTime;


/**
 * 
 * @author Kaushik
 * 
 */
public class Force implements Entity
{
	private Vector	force;



	public Force ( Vector appliedForce )
	{
		force = appliedForce;
	}



	public Force ( float x, float y )
	{
		force = new Vector (x, y);
	}



	public Vector getForceVector ( )
	{
		return force;
	}
	
	
	

	public static Force sum ( Collection < Force > appliedForces )
	{
		float finalX = 0, finalY = 0;

		for (Force f : appliedForces)
		{
			finalX += f.force.getX ( );
			finalY += f.force.getY ( );
		}

		return new Force ( new Vector ( finalX, finalY ) );
	}



	@Override
	public void update ( GameTime gameTime, InputChange inputChange , GameState gameState )
	{
		//Forces do not change by themselves.
	}



	@Override
	public boolean shouldDestroy ( )
	{
		return false;
	}
}
