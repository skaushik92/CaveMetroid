
package controller.input.keyboard;

import java.awt.event.KeyEvent;
import java.util.BitSet;


public class KeyState
{

	private BitSet		keyState;



	public KeyState ( )
	{
		keyState = new BitSet ( 256 );
	}



	protected void setKey ( int keyCode )
	{
		keyState.set ( keyCode );
	}



	public KeyState clone ( )
	{
		KeyState clean = new KeyState ( );

		// Copying values
		clean.keyState = (BitSet) keyState.clone ( );

		return clean;
	}



	protected void unsetKey ( int keyCode )
	{
		keyState.clear ( keyCode );
	}


	public boolean isUp ( Key key )
	{
		return !keyState.get ( key.keyCode );
	}



	public boolean isDown ( Key key )
	{
		return keyState.get ( key.keyCode );
	}
	

	public boolean allPressed ( Key ... keys ) {
		for (Key k : keys)
			if (isUp ( k ))
				return false;
		return true;
	}
	
	public boolean anyPressed ( Key ... keys ) {
		for (Key k : keys)
			if (isDown ( k ))
				return true;
		return false;
	}
	
	public String toString() {
		String s = "{";
		
		for (int i = keyState.nextSetBit ( 0 ); i >= 0; i = keyState.nextSetBit ( i + 1 ))
		{
			s += KeyEvent.getKeyText ( i ) + ", ";
		}
		if (s.length() > 1)
			s = s.substring ( 0, s.length() - 2 );
		s += "}";
		
		return s;
	}
}
