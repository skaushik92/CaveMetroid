
package model.entities.blocks;

public class BlockCoordinate
{
	int	row;
	int	col;



	public BlockCoordinate ( int column, int row )
	{
		this.row = row;
		this.col = column;
	}



	public int getCol ( )
	{
		return col;
	}



	public int getRow ( )
	{
		return row;
	}



	public String toString ( )
	{
		return "BlockCoordinate: (" + col + ", " + row + ")";
	}



	public void setCol ( int newCol )
	{
		col = newCol;
	}



	public void setRow ( int newRow )
	{
		row = newRow;
	}
}