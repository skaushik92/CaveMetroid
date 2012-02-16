
package model.entities;

import java.util.Comparator;

import model.graphics.Sprite;
import model.physics.Position;


public interface ViewableEntity extends Entity
{
	/**
	 * @return center position of the entity
	 */
	public Position getPosition ( );
	
	

	public Sprite getSprite ( );
	
	/*
	 * Higher in front of lower.
	 */
	public int zValue();
	
	
	public static final Comparator <ViewableEntity> viewableEntityComparator = new Comparator <ViewableEntity> () {
		public int compare ( ViewableEntity e1, ViewableEntity e2) {
			int tempResult = e1.zValue ( ) - e2.zValue ( );
			return tempResult == 0 ? -1 : tempResult;
			
		}
	};
}
