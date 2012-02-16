
package controller.input.keyboard;

import java.awt.event.KeyEvent;


public enum Key
{
	// Arrow Keys
	UP ( KeyEvent.VK_UP ), DOWN ( KeyEvent.VK_DOWN ), LEFT ( KeyEvent.VK_LEFT ), RIGHT ( KeyEvent.VK_RIGHT ),

	// WASD
	W ( KeyEvent.VK_W ), A ( KeyEvent.VK_A ), S ( KeyEvent.VK_S ), D ( KeyEvent.VK_D ),

	// Function Keys
	F11 ( KeyEvent.VK_F11 ), C ( KeyEvent.VK_C );

	public final int	keyCode;



	private Key ( int keyCode )
	{
		this.keyCode = keyCode;
	}
}
