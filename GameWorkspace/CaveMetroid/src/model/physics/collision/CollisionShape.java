
package model.physics.collision;


import java.awt.Rectangle;
import java.awt.Shape;

import model.physics.Position;

public class CollisionShape
{
	private Position	centerPosition;
	private Shape		shape;
	
	public CollisionShape () {
		centerPosition = Position.ZERO;
		shape = new Rectangle ( );
	}

	
	public Shape getShape () {
		return shape;
	}
	
	public Position getPosition () {
		return centerPosition;
	}
}