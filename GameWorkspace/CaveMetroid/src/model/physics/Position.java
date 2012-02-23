
package model.physics;

import view.Constants;


/**
 * Immutable Position stores x and y values as integers.
 */
public class Position
{
	/**
	 * All classes will have the same version so as to keep consistency within all classes.
	 */
	private static final long	serialVersionUID	= Constants.serialVersionUID;

	public static final Position	ZERO				= new Position ( 0, 0 );

	private float				x;
	private float				y;



	public Position ( float x, float y )
	{
		this.x = x;
		this.y = y;
	}



	public Position ( double x, double y )
	{
		this.x = (float) x;
		this.y = (float) y;
	}



	public Position ( Position oldPosition, float dx, float dy )
	{
		this.x = oldPosition.x + dx;
		this.y = oldPosition.y + dy;
	}



	public Position ( Position oldPosition, double dx, double dy )
	{
		this.x = oldPosition.x + (float) dx;
		this.y = oldPosition.y + (float) dy;
	}



	public Position ( Position oldPosition, Vector delta )
	{
		this.x = oldPosition.x + (float) delta.getX ( );
		this.y = oldPosition.y + (float) delta.getY ( );
	}



	public Position ( Position oldPosition, Position moveBy )
	{
		this.x = oldPosition.x + (float) moveBy.getX ( );
		this.y = oldPosition.y + (float) moveBy.getY ( );
	}



	public Position plus ( Vector delta )
	{
		return new Position ( this, delta );
	}



	public float getX ( )
	{
		return x;
	}



	public float getY ( )
	{
		return y;
	}



	public void setX ( float newX )
	{
		x = newX;
	}



	public void setY ( float newY )
	{
		y = newY;
	}



	public void set ( float newX, float newY )
	{
		setX ( newX );
		setY ( newY );
	}



	public void setPosition ( Position p )
	{
		set ( p.getX ( ), p.getY ( ) );
	}



	// FUNCTIONS
	public Position minus ( Position anotherPosition )
	{
		// TODO Auto-generated method stub
		return new Position ( x - anotherPosition.x, y - anotherPosition.y );
	}



	public String toString ( )
	{
		return "(" + String.format ( "%.3f", x ) + ", " + String.format ( "%.3f", y ) + ")";
	}



	public boolean isPositive ( )
	{
		return x >= 0 && y >= 0;
	}



	public Position negate ( )
	{
		return new Position ( -x, -y );
	}
}
