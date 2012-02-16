package model.entities;

import model.physics.collision.CollisionShape;

public interface CollidableEntity extends ViewableEntity
{
	public CollisionShape getCollisionShape ();
}