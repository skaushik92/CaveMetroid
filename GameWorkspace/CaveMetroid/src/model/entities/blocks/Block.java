
package model.entities.blocks;

import java.util.EnumSet;

import model.entities.ViewableEntity;
import model.physics.Position;


public abstract class Block implements ViewableEntity
{
	protected Position	myPosition;



	protected Block ( Position position )
	{
		myPosition = position;
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



	public abstract String blockType ( );



	public abstract EnumSet < BlockAttributes > getAttributes ( );
}
