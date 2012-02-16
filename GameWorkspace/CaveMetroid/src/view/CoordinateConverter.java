
package view;

import model.physics.Position;


public class CoordinateConverter
{

	public static Position toFramePosition ( Position modelPosition, float maxHeight )
	{
		return new Position ( modelPosition.getX ( ), maxHeight - modelPosition.getY ( ) );
	}

}
