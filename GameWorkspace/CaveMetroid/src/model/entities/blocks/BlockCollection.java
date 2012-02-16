
package model.entities.blocks;

import java.util.ArrayList;
import java.util.Iterator;


public class BlockCollection implements Iterable < Block >
{
	private ArrayList < Block >	blocks;



	public BlockCollection ( )
	{
		blocks = new ArrayList < Block > ( 1 );
	}



	public int size ( )
	{
		return blocks.size ( );
	}



	@Override
	public Iterator < Block > iterator ( )
	{
		return blocks.iterator ( );
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
	}



	public ArrayList < Block > getBlocks ( )
	{
		return blocks;
	}
}
