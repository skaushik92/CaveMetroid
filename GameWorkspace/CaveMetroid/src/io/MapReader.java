
package io;

import java.util.ArrayList;

import model.entities.Entity;

import view.Constants;


public class MapReader
{
	/**
	 * All classes will have the same version so as to keep
	 * consistency within all classes.
	 */
	private static final long	serialVersionUID	= Constants.serialVersionUID;



	public static ArrayList < Entity > readMap ( String mapFileName )
	{
		ArrayList < Entity > map = new ArrayList < Entity > ( );
		map.add ( EntityReader.readEntity ( "BLAH" ) );
		return map;
	}
}
