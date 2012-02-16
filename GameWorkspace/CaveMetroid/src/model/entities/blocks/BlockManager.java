
package model.entities.blocks;

import java.util.ArrayList;
import java.util.List;

import log.Log;
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

	BlockCollection [][]	grid;

	int					width;
	int					height;

	EntityManager			entityManager;



	public BlockManager ( int width, int height, EntityManager entityManager )
	{
		grid = new BlockCollection [ height ] [ width ];

		for (int row = 0; row < height; row++ )
		{
			grid[ row ] = new BlockCollection [ width ];
			for (int col = 0; col < width; col++ )
			{
				grid[ row ][ col ] = new BlockCollection ( );
			}
		}

		this.width = width;
		this.height = height;
		this.entityManager = entityManager;
	}



	private boolean validCoordinate ( int x, int y )
	{
		return x >= 0 && x < width && y >= 0 && y < height;
	}



	public void setBlock ( int x, int y, Class < Block > blockType )
	{
		if ( validCoordinate ( x, y ) )
			grid[ y ][ x ].addBlock ( entityManager.createEntity ( blockType ) );
		else
		{
			Log.e ( "Block Setting Error", "Block of type " + blockType.getSimpleName ( ) + " cannot be created at (" + x + ", " + y + ")!" );
		}
	}



	/**
	 * bottomLeftx and bottomLefty are inclusive. topRightx and
	 * topRighty are exclusive;
	 */

	public void setBlocksInRegion ( int bottomLeftx, int bottomLefty, int topRightx, int topRighty, Class < ? extends Block > blockType, Object... blockParameters )
	{
		if ( validCoordinate ( bottomLeftx, bottomLefty ) && validCoordinate ( topRightx - 1, topRighty - 1 ) )
		{
			for (int row = bottomLefty; row < topRighty; row++ )
				for (int col = bottomLeftx; col < topRightx; col++ )
					grid[ row ][ col ].addBlock ( entityManager.createEntity ( blockType, new Position ( 50 * col + 25, 50 * row + 25 ), blockParameters ) );
		}
	}



	public List < Block > getViewableBlocks ( Position cameraPos )
	{
		float posX = cameraPos.getX ( );
		float upperX = ( posX + view.Constants.WINDOW_WIDTH ) - 1;

		float posY = cameraPos.getY ( ) - 1;
		float lowerY = ( posY - view.Constants.WINDOW_HEIGHT );

		Coordinate bottomLeft = coordinateAtWorldPosition ( new Position ( posX, lowerY ) );
		Coordinate topRight = coordinateAtWorldPosition ( new Position ( upperX, posY ) );

		List < Block > blocksToDraw = new ArrayList < Block > ( );

		for (int row = bottomLeft.row; row <= topRight.row; row++ )
		{
			for (int col = bottomLeft.col; col <= topRight.col; col++ )
			{
				if ( validCoordinate ( col, row ) )
					blocksToDraw.addAll ( grid[ row ][ col ].getBlocks ( ) );
			}
		}

		return blocksToDraw;
	}



	public static Coordinate coordinateAtWorldPosition ( Position worldPos )
	{
		int row = 0, col = 0;

		col = ( (int) worldPos.getX ( ) ) / 50;
		row = ( (int) worldPos.getY ( ) ) / 50;

		return new Coordinate ( row, col );
	}


	private static final class Coordinate
	{
		int	row;
		int	col;



		public Coordinate ( int row, int column )
		{
			this.row = row;
			this.col = column;
		}
	}

}