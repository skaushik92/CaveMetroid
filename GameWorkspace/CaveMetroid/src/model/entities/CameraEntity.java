package model.entities;

import log.Log;
import model.GameState;
import model.physics.Position;
import model.time.GameTime;
import controller.input.InputChange;

public class CameraEntity implements Entity
{

	private Position currentCameraPosition;
	
	
	public CameraEntity () {
		currentCameraPosition = new Position (0, 0);
	}
	
	public CameraEntity ( Position initialPosition ) {
		currentCameraPosition = initialPosition;
	}
	
	@Override
	public void update ( GameTime gameTime, InputChange inputChange, GameState gameState )
	{
		Position playerPosition = gameState.getPlayer ( ).getPosition ( );
		Log.v ( "Camera Position", playerPosition );
	}



	@Override
	public boolean shouldDestroy ( )
	{
		return false;
	}



	public void addCameraYPosition ( float dy )
	{
		currentCameraPosition = new Position ( currentCameraPosition , 0, dy );
	}



	public Position getPosition ( )
	{
		return currentCameraPosition;
	}

}
