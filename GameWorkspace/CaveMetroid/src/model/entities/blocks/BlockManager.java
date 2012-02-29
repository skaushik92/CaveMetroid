
package model.entities.blocks;

import java.util.ArrayList;
import java.util.List;

import log.Log;
import model.Constants;
import model.World;
import model.managers.EntityManager;
import model.physics.Position;


public class BlockManager
{

	// / Blocks that don't need updates:
	// BackgroundBlocks
	// GroundBlocks

	// / Blocks that do need updates:
	// DisappearingBlocks
	// DestructableBlocks

	private static World currentWorld;
	

	public static void initialize ( int gridWidth, int gridHeight )
	{
		currentWorld = new World ( gridWidth, gridHeight );
	}



	public static boolean validCoordinate ( int col, int row )
	{
		return currentWorld.validCoordinate ( col, row );
	}



	public static void setBlock ( int col, int row, Class < Block > blockType )
	{
		currentWorld.setBlock ( col, row, blockType );
	}



	/**
	 * bottomLeftx and bottomLefty are inclusive. topRightx and
	 * topRighty are exclusive;
	 */

	public static void setBlocksInRegion ( int bottomLeftCol, int bottomLeftRow, int topRightCol, int topRightRow, Class < ? extends Block > blockType, Object... blockParameters )
	{
		currentWorld.setBlocksInRegion ( bottomLeftCol, bottomLeftRow, topRightCol, topRightRow, blockType, blockParameters );
	}



	public static void setBlock ( int col, int row, Class < GroundBlock > blockType, Object... blockParameters )
	{
		currentWorld.setBlock ( col, row, blockType, blockParameters );
	}



	public static List < Block > getViewableBlocks ( Position cameraPos )
	{
		return currentWorld.getViewableBlocks ( cameraPos );
	}



	public static BlockCoordinate coordinateAtWorldPosition ( Position worldPos )
	{
		return currentWorld.coordinateAtWorldPosition ( worldPos );
	}



	public static boolean containsBlockOfTypeAt ( int col, int row, BlockAttributes attribute )
	{
		return currentWorld.containsBlockOfTypeAt ( col, row, attribute );
	}

}