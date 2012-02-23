
package model.entities.blocks;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeSet;


public class BlockCollection implements Iterable < Block >
{
	private ArrayList < Block >	blocks;

	private TreeSet < Block >	blockViews;



	public BlockCollection ( )
	{
		blocks = new ArrayList < Block > ( 1 );

		blockViews = new TreeSet < Block > ( new Comparator < Block > ( )
		{
			@Override
			public int compare ( Block b1, Block b2 )
			{
				// We want b1 to be earlier if b1's zValue
				// is less than b2's zValue
				int bz1 = b1.zValue ( );
				int bz2 = b2.zValue ( );

				return bz1 < bz2 ? -1 : 1;
			}
		} );
	}



	public int size ( )
	{
		return blockViews.size ( );
	}



	@Override
	public Iterator < Block > iterator ( )
	{
		return blockViews.iterator ( );
	}



	public boolean contains ( Class < Block > blockType )
	{
		for (Block b : blocks)
			if ( blockType.isAssignableFrom ( b.getClass ( ) ) )
				return true;
		return false;
	}



	public void addBlock ( Block block )
	{
		blocks.add ( block );
		blockViews.add ( block );
	}



	public ArrayList < Block > getBlocks ( )
	{
		return blocks;
	}



	public ArrayList < Block > getSortedBlocks ( )
	{
		return new ArrayList < Block > ( blockViews );
	}



	public boolean containsBlockWithCharacteristic ( BlockAttributes attribute )
	{
		for (Block b : blocks)
			if ( b.getAttributes ( ).contains ( attribute ) )
				return true;
		return false;
	}



	public void clear ( )
	{
		blocks.clear ( );
		blockViews.clear ( );
	}
}
