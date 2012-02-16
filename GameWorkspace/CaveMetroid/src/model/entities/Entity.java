
package model.entities;

import controller.input.InputChange;
import model.GameState;
import model.time.GameTime;


public interface Entity
{
	public void update ( GameTime gameTime, InputChange inputChange, GameState gameState );
	
	public boolean shouldDestroy ( );
}