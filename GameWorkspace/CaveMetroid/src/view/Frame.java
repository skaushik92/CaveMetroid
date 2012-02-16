
package view;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import log.Log;
import model.graphics.Sprite;
import model.physics.Position;


public class Frame
{
	/**
	 * All classes will have the same version so as to keep
	 * consistency within all classes.
	 */
	private static final long	serialVersionUID	= Constants.serialVersionUID;

	private BufferedImage		screen;
	private Position			topLeftPos;
	private Graphics2D			g;

	private float				maxHeight;



	public Frame ( )
	{
		Log.v ( "Program State", "Frame.Constructor(1)" );
		init ( new Position ( 0, Constants.WINDOW_HEIGHT ), Constants.WINDOW_HEIGHT );
	}



	public Frame ( Position topLeft )
	{
		Log.v ( "Program State", "Frame.Constructor(2)" );
		init ( topLeft, Constants.WINDOW_HEIGHT );
	}



	public Frame ( float maxHeight )
	{
		Log.v ( "Program State", "Frame.Constructor(3)" );
		init ( new Position ( 0, Constants.WINDOW_HEIGHT ), maxHeight );
	}



	public Frame ( Position topLeft, float maxHeight )
	{
		Log.v ( "Program State", "Frame.Constructor(4)" );
		init ( topLeft, maxHeight );
	}



	private void init ( Position topLeft, float maxHeight )
	{
		Log.v ( "Program State", "Frame.init" );
		screen = new BufferedImage ( Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT, BufferedImage.TYPE_INT_ARGB );
		topLeftPos = topLeft;
		this.maxHeight = maxHeight;
		g = (Graphics2D) screen.getGraphics ( );
	}



	private Position convert ( Position position )
	{
		return CoordinateConverter.toFramePosition ( position, maxHeight );
	}



	public void addSprite ( Sprite sprite, Position position )
	{
		Log.v ( "Program State", "Frame.addSprite" );
		Position actualPosition = convert ( position );
		Position onScreenPos = actualPosition.minus ( convert ( topLeftPos ) );

		Log.v ( "Program Info", "Positions: [" + actualPosition + "], [" + topLeftPos + "], [" + onScreenPos + "]" );
		if ( shouldDraw ( sprite , onScreenPos ) )
		{
			Log.v ( "Sprite can be drawn", onScreenPos);

			/*
			 * Allows for anti-aliasing between pixels so
			 * that positions are perfectly drawn rather than
			 * rounded down during type-conversion.
			 */

			g.setRenderingHint ( RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC );
			g.setRenderingHint ( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );
	
			BufferedImage srcImage = sprite.getImage ( );
			Position centerOfImage = new Position ( srcImage.getWidth ( ) / 2.0, srcImage.getHeight ( ) / 2.0 );
			Position topLeftOfImage = new Position ( onScreenPos, centerOfImage.negate ( ) );

			AffineTransform t = new AffineTransform ( );
			t.translate ( topLeftOfImage.getX ( ), topLeftOfImage.getY ( ) );
			g.drawImage ( srcImage, t, null );
		}
	}



	private boolean shouldDraw ( Sprite sprite, Position onScreenPos )
	{
		int halfWidth = sprite.getImage ( ).getWidth ( )/2;
		int halfHeight= sprite.getImage ( ).getHeight( )/2;
		
		/*
		 * Check if off the left side
		 */
		if ( onScreenPos.getX() + halfWidth < 0 )
			return false;

		/*
		 * Check if off the right side
		 */
		if ( onScreenPos.getX() - halfWidth > Constants.WINDOW_WIDTH )
			return false;
		

		/*
		 * Check if off the top side
		 */
		if ( onScreenPos.getY() + halfHeight < 0)
			return false;
		

		/*
		 * Check if off the down side
		 */
		if ( onScreenPos.getY() - halfHeight > Constants.WINDOW_HEIGHT )
			return false;
		
		return true;
	}



	public BufferedImage getFrameImage ( )
	{
		Log.v ( "Program State", "Frame.getFrameImage" );
		return screen;
	}
}
