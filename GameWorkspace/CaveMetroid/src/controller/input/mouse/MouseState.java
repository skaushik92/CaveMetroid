
package controller.input.mouse;

import model.physics.Position;


/**
 * 
 * @author Kaushik
 * 
 *         Will be an immutable object.
 */
public class MouseState
{
	private Position	mousePosition;



	public MouseState ( )
	{
		mousePosition = Position.ZERO;
	}



	public MouseState clone ( )
	{
		MouseState copyState = new MouseState ( );

		// TODO Copy values

		return copyState;
	}



	public String toString ( )
	{
		String s = "{";

		// TODO List information

		s += "}";

		return s;
	}



	protected void setPosition ( Position position )
	{
		mousePosition = position;
	}



	public Position getMousePosition ( )
	{
		return mousePosition;
	}
}
