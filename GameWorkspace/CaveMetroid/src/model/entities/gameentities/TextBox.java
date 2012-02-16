
package model.entities.gameentities;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import controller.input.InputChange;

import log.Log;

import model.Constants;
import model.GameState;

import model.entities.ViewableEntity;
import model.graphics.Sprite;
import model.physics.Position;
import model.time.GameTime;


public class TextBox implements ViewableEntity
{

	private Position			myPosition;
	private String				myText;
	private int				myMaxWidth;
	private Font				myFont;
	private Sprite				mySprite;

	private static final int		BOX_HEIGHT_BUFFER	= 5;
	private static final int		BOX_WIDTH_BUFFER	= 5;

	private static final Color	BACKGROUND_COLOR	= Color.white;
	private static final Color	TEXT_COLOR		= Color.black;


	public TextBox ( Position position, String containedValue )
	{
		Log.v ( "Program State", "TextBox.Constructor(1)" );
		myPosition = position;
		myMaxWidth = Integer.MAX_VALUE;
		myText = containedValue.trim ( );
		myFont = Constants.DEFAULT_TEXT_FONT;
		generateSprite ( );
	}



	public TextBox ( Position position, String containedValue, Integer maxWidth )
	{
		Log.v ( "Program State", "TextBox.Constructor(2)" );
		myPosition = position;
		myMaxWidth = maxWidth;
		myText = containedValue.trim ( );
		myFont = Constants.DEFAULT_TEXT_FONT;
		generateSprite ( );
	}



	public TextBox ( Position position, String containedValue, Integer maxWidth, Font font )
	{
		Log.v ( "Program State", "TextBox.Constructor(2)" );
		myPosition = position;
		myMaxWidth = maxWidth;
		myText = containedValue.trim ( );
		myFont = font;
		generateSprite ( );
	}



	@Override
	public Position getPosition ( )
	{
		return myPosition;
	}



	private void generateSprite ( )
	{
		Log.v ( "Program State", "TextBox.generateSprite" );

		/*
		 * Finds the best bounding region for the text box.
		 */

		// Creates a temporary image from which to get the
		// font metrics object.
		FontMetrics metrics = new BufferedImage ( 1, 1, BufferedImage.TYPE_4BYTE_ABGR ).getGraphics ( ).getFontMetrics ( myFont );

		// Gets the height of one line of text.
		int lineHeight = (int) Math.round ( metrics.getHeight ( ) * 1.2 );

		/*
		 * Scans through the text, word by word, to build
		 * lines, one line at a time.
		 */

		ArrayList < String > lines = new ArrayList < String > ( );

		String [] words = myText.split ( "\\s+" );

		String nextLine = "";
		for (String word : words)
		{
			if ( metrics.stringWidth ( nextLine + word ) < myMaxWidth )
				nextLine += " " + word;
			else
			{
				if ( !nextLine.equals ( "" ) )
					lines.add ( nextLine );
				nextLine = word;
				if ( metrics.stringWidth ( word ) > myMaxWidth )
				{
					Log.e ( "Text Box Error", "Cannot have a word that is longer than the max width: " + word );
				}
			}
		}

		if ( !nextLine.equals ( "" ) )
			lines.add ( nextLine );

		int boxHeight = lines.size ( ) * ( lineHeight );
		int boxWidth = 0;

		for (String line : lines)
			if ( boxWidth < metrics.stringWidth ( line ) )
				boxWidth = metrics.stringWidth ( line );

		String actualText = "";

		for (String line : lines)
			actualText += line + "\n";

		int actualWidth = boxWidth + 2 * BOX_WIDTH_BUFFER;
		int actualHeight = boxHeight + 2 * BOX_HEIGHT_BUFFER;

		BufferedImage textImage = new BufferedImage ( actualWidth, actualHeight, BufferedImage.TYPE_4BYTE_ABGR );

		Graphics gOrig = textImage.getGraphics ( );
		Graphics2D g = (Graphics2D) gOrig;

		g.setRenderingHint ( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );
		g.setRenderingHint ( RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY );

		FontRenderContext frc = g.getFontRenderContext ( );

		g.setColor ( BACKGROUND_COLOR );

		g.fillRoundRect ( 0, 0, actualWidth, actualHeight, 15, 15 );

		g.setColor ( TEXT_COLOR );

		int nextHeight = BOX_HEIGHT_BUFFER - 4;

		for (String line : lines)
		{
			TextLayout tl = new TextLayout ( line, myFont, frc );
			tl.draw ( g, BOX_WIDTH_BUFFER, nextHeight += lineHeight );
		}

		mySprite = new Sprite ( textImage );
	}



	@Override
	public void update ( GameTime gameTime , InputChange inputChange , GameState gameState )
	{
	}



	@Override
	public Sprite getSprite ( )
	{
		return mySprite;
	}



	@Override
	public TextBox clone ( )
	{
		return this;
	}



	@Override
	public boolean shouldDestroy ( )
	{
		return false;
	}

	@Override
	public int zValue ( )
	{
		return 150;
	}
}