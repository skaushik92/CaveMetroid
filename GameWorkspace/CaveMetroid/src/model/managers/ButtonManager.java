
package model.managers;

import java.util.EnumMap;

import model.Action;

import controller.input.keyboard.Key;


public class ButtonManager
{

	public static EnumMap < Action , Key >	actionToButtonMap;

	static
	{
		actionToButtonMap = new EnumMap < Action , Key > ( Action.class );

		map ( Action.MoveLeft, Key.LEFT );
		map ( Action.MoveRight, Key.RIGHT );
		map ( Action.Inspect, Key.DOWN );
		map ( Action.LookDown, Key.DOWN );
		map ( Action.Jump, Key.Z );
		map ( Action.LocalMap, Key.Q );
		map ( Action.WorldMap, Key.W );
		map ( Action.WeaponSelectLeft, Key.A );
		map ( Action.WeaponSelectRight, Key.S );
		map ( Action.Charge, Key.C );
		map ( Action.Exit, Key.ESC );
		map ( Action.FullScreen, Key.F11 );
	}



	public static Key get ( Action a )
	{
		Key k = actionToButtonMap.get ( a );
		if ( k == null )
			k = Key.NONE;
		return k;
	}



	public static void map ( Action a, Key k )
	{
		actionToButtonMap.put ( a, k );
	}
}
