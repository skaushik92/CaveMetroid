
package model.entities.blocks;

import java.util.EnumSet;

import io.ContentManager;

import controller.input.InputChange;

import log.Log;
import model.GameState;
import model.graphics.Animation;
import model.graphics.Sprite;
import model.physics.Position;
import model.time.GameTime;


public class GroundBlock extends Block
{
	String	blockType;
	Animation	myAnimation;



	public GroundBlock ( Position position, Object... params )
	{
		super ( position );
		Log.v ( "Program State", "GroundBlock Constructor" );
		blockType = (String) params[ 0 ];

		myAnimation = ContentManager.loadAnimation ( blockType );
		myAnimation.setRepeat ( false );
		myAnimation.setFrameTimes ( 1 );
	}



	@Override
	public int zValue ( )
	{
		return 0;
	}



	@Override
	public Sprite getSprite ( )
	{
		return myAnimation.getCurrentSprite ( );
	}



	@Override
	public void update ( GameTime gameTime, InputChange inputChange, GameState gameState )
	{
		myAnimation.moveForward ( gameTime.getElapsedTime ( ).getSeconds ( ) );
	}



	@Override
	public String blockType ( )
	{
		return blockType;
	}



	@Override
	public EnumSet < BlockAttributes > getAttributes ( )
	{
		return EnumSet.of ( BlockAttributes.Full_Collidable );
	}

}
