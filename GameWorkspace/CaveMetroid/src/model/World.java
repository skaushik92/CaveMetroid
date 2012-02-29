
package model;

import java.util.ArrayList;
import java.util.List;

import log.Log;
import model.entities.Entity;
import model.entities.blocks.Block;
import model.entities.blocks.BlockAttributes;
import model.entities.blocks.BlockCollection;
import model.entities.blocks.BlockCoordinate;
import model.entities.blocks.GroundBlock;
import model.managers.EntityManager;
import model.physics.Position;


public class World
{
	private static BlockCollection [][]	grid;

	private static int					width;
	private static int					height;



	public World ( int gridWidth, int gridHeight )
	{
		width = gridWidth;
		height = gridHeight;

		grid = new BlockCollection [ height ] [ width ];

		for (int row = 0; row < height; row++ )
		{
			grid[ row ] = new BlockCollection [ width ];
			for (int col = 0; col < width; col++ )
			{
				grid[ row ][ col ] = new BlockCollection ( );
			}
		}
	}



	public boolean validCoordinate ( int col, int row )
	{
		return col >= 0 && col < width && row >= 0 && row < height;
	}



	public void setBlock ( int col, int row, Class < Block > blockType )
	{
		if ( validCoordinate ( col, row ) )
			grid[ row ][ col ].addBlock ( EntityManager.createEntity ( blockType ) );
		else
		{
			Log.e ( "Block Setting Error", "Block of type " + blockType.getSimpleName ( ) + " cannot be created at (" + col + ", " + row + ")!" );
		}
	}



	/**
	 * bottomLeftx and bottomLefty are inclusive. topRightx and topRighty are exclusive;
	 */

	public void setBlocksInRegion ( int bottomLeftCol, int bottomLeftRow, int topRightCol, int topRightRow, Class < ? extends Block > blockType, Object... blockParameters )
	{
		if ( validCoordinate ( bottomLeftCol, bottomLeftRow ) && validCoordinate ( topRightCol - 1, topRightRow - 1 ) )
		{
			for (int row = bottomLeftRow; row < topRightRow; row++ )
				for (int col = bottomLeftCol; col < topRightCol; col++ )
					grid[ row ][ col ].addBlock ( EntityManager.createEntity ( blockType, new Position ( Constants.BLOCK_WIDTH * col + Constants.BLOCK_WIDTH / 2, Constants.BLOCK_HEIGHT * row + Constants.BLOCK_HEIGHT / 2 ), blockParameters ) );
		}
	}



	public void setBlock ( int col, int row, Class < GroundBlock > blockType, Object... blockParameters )
	{
		if ( validCoordinate ( col, row ) )
		{
			grid[ row ][ col ].clear ( );
			if ( blockParameters == null || blockParameters.length == 0 )
				grid[ row ][ col ].addBlock ( EntityManager.createEntity ( blockType, new Position ( Constants.BLOCK_WIDTH * col + Constants.BLOCK_WIDTH / 2, Constants.BLOCK_HEIGHT * row + Constants.BLOCK_HEIGHT / 2 ) ) );
			else
				grid[ row ][ col ].addBlock ( EntityManager.createEntity ( blockType, new Position ( Constants.BLOCK_WIDTH * col + Constants.BLOCK_WIDTH / 2, Constants.BLOCK_HEIGHT * row + Constants.BLOCK_HEIGHT / 2 ), blockParameters ) );
		}
	}



	public List < Block > getViewableBlocks ( Position cameraPos )
	{
		float posX = cameraPos.getX ( );
		float upperX = ( posX + view.Constants.WINDOW_WIDTH ) - 1;

		float posY = cameraPos.getY ( ) - 1;
		float lowerY = ( posY - view.Constants.WINDOW_HEIGHT );

		BlockCoordinate bottomLeft = coordinateAtWorldPosition ( new Position ( posX, lowerY ) );
		BlockCoordinate topRight = coordinateAtWorldPosition ( new Position ( upperX, posY ) );

		List < Block > blocksToDraw = new ArrayList < Block > ( );

		for (int row = bottomLeft.getRow ( ); row <= topRight.getRow ( ); row++ )
		{
			for (int col = bottomLeft.getCol ( ); col <= topRight.getCol ( ); col++ )
			{
				if ( validCoordinate ( col, row ) )
					blocksToDraw.addAll ( grid[ row ][ col ].getBlocks ( ) );
			}
		}

		return blocksToDraw;
	}



	public BlockCoordinate coordinateAtWorldPosition ( Position worldPos )
	{
		int row = 0, col = 0;

		col = ( (int) worldPos.getX ( ) ) / Constants.BLOCK_WIDTH;
		row = ( (int) worldPos.getY ( ) ) / Constants.BLOCK_HEIGHT;

		return new BlockCoordinate ( col, row );
	}



	public boolean containsBlockOfTypeAt ( int col, int row, BlockAttributes attribute )
	{
		return grid[ row ][ col ].containsBlockWithCharacteristic ( attribute );
	}

}
