
package model.graphics;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import log.Log;

import view.Constants;


public class Sprite
{
	/**
	 * All classes will have the same version so as to keep
	 * consistency within all classes.
	 */
	private static final long	serialVersionUID	= Constants.serialVersionUID;

	private BufferedImage		image;

	private Sprite				flippedSprite;



	public Sprite ( BufferedImage spriteImage )
	{
		image = spriteImage;
		flippedSprite = null;
	}



	public BufferedImage getImage ( )
	{
		return image;
	}



	private void generateFlippedSprite ( )
	{
		BufferedImage flippedImage = null;
		
		if ( flippedSprite == null )
		{
			AffineTransform tx = AffineTransform.getScaleInstance ( -1, 1 );
			tx.translate ( -image.getWidth ( null ), 0 );
			AffineTransformOp op = new AffineTransformOp ( tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR );
			flippedImage = op.filter ( image, null );
		}

		if ( flippedImage == null)
			Log.wtf ( "NULL POINTER",  "Image is still null!");
		
		flippedSprite = new Sprite( flippedImage );
		flippedSprite.flippedSprite = this;
	}



	public Sprite flipped ( )
	{
		if (flippedSprite == null)
			generateFlippedSprite ( );
		return flippedSprite;
	}
}