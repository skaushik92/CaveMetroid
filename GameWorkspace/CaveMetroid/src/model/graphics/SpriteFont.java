
package model.graphics;

import java.awt.Dimension;
import java.awt.Font;
import java.util.Map;

import model.Constants;


public class SpriteFont
{

	public enum FontWeight
	{
		BOLD ( Font.BOLD ), ITALICS ( Font.ITALIC ), PLAIN ( Font.PLAIN );

		public final int	fontType;



		private FontWeight ( int type )
		{
			this.fontType = type;
		}
	}


	private Font	myFont;



	public SpriteFont ( )
	{
		init ( Constants.DEFAULT_FONT_NAME, Constants.DEFAULT_FONT_SIZE );
	}



	public SpriteFont ( String fontName )
	{
		myFont = null;
		myFont.canDisplay ( 'b' );
	}



	public SpriteFont ( String fontName, int size )
	{
		myFont = null;
	}



	@SuppressWarnings ( { "rawtypes" } )
	public SpriteFont ( Map fontAttributes )
	{

	}



	private void init ( String fontName, int size )
	{
		int type = Font.PLAIN;

		myFont = new Font ( fontName, type, size );
	}



	public Dimension measureString ( String text )
	{

		// TODO measure the string
		return null;
	}
}
