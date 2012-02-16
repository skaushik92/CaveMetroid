
package model.physics.collision;

import java.awt.Rectangle;
import java.awt.Shape;


public class CollisionShape
{
	private Shape	shape;



	public CollisionShape ( )
	{
		shape = new Rectangle ( );
	}



	public CollisionShape ( Rectangle rectangle )
	{
		shape = rectangle;
	}



	public Shape getShape ( )
	{
		return shape;
	}



	public float getHeight ( )
	{
		return ((Rectangle) shape).height;
	}



	public float getWidth ( )
	{
		return ((Rectangle) shape).width;
	}
}