
package io;

import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import log.Log;
import model.World;
import model.entities.Entity;

import view.Constants;


public class MapReader
{
	/**
	 * All classes will have the same version so as to keep consistency within all classes.
	 */
	private static final long	serialVersionUID	= Constants.serialVersionUID;



	public static ArrayList < Entity > readMap ( String mapFileName )
	{
		ArrayList < Entity > map = new ArrayList < Entity > ( );

		return map;
	}



	public static void setWorld ( File mapFile )
	{
		try
		{
			FileInputStream input = new FileInputStream ( mapFile );
			BufferedReader in = new BufferedReader ( new InputStreamReader ( input ) );

			String [] worldSizeStrs = in.readLine ( ).split ( "\\s+" );

			int numRows = Integer.parseInt ( worldSizeStrs[ 0 ] );
			int numCols = Integer.parseInt ( worldSizeStrs[ 1 ] );

			World world = new World ( numCols, numRows );

			String nextLine = "";

			while ( ( nextLine = in.readLine ( ) ) != null )
			{
				nextLine = nextLine.trim ( );
				if ( nextLine.equals ("") )
					continue;
				
				
			}

		}
		catch ( Exception e )
		{
			e.printStackTrace ( );
			Log.e ( "Map Read Error", "Could not read map " + mapFile.getName ( ) );
		}
	}
}
