
package model.save;

import java.util.HashMap;


public class SaveState
{
	private HashMap < SaveKey , Saveable >	data;

	private static SaveState				singleton;



	private SaveState ( )
	{
		data = new HashMap < SaveKey , Saveable > ( );
	}



	public static SaveState getInstance ( )
	{
		if ( singleton == null )
			singleton = new SaveState ( );
		return singleton;
	}



	public void save ( SaveKey key, Saveable value )
	{
		data.put ( key , value );
	}



	public void read ( )
	{
		
	}



	public void write ( )
	{
		
	}
}