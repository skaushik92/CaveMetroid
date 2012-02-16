
package io;

import view.Constants;

import model.entities.*;
import model.entities.gameentities.TextBox;
import model.physics.Position;


public class EntityReader
{
	/**
	 * All classes will have the same version so as to keep
	 * consistency within all classes.
	 */
	private static final long	serialVersionUID	= Constants.serialVersionUID;

	public static Entity readEntity ( String line )
	{
		return new TextBox ( new Position ( 50, 50 ), "Hello World!" );
	}
}
