
package io;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import log.Log;
import model.graphics.Animation;
import model.graphics.Sprite;


public class ContentManager
{
	private static final Map < File , BufferedImage >	imageMap;

	private static final File					CONTENT;
	private static final File					IMAGES;

	static
	{
		imageMap = new HashMap < File , BufferedImage > ( );
		CONTENT = new File ( "Content" );
		IMAGES = new File ( CONTENT, "Images" );
	}



	public static Animation loadAnimation ( String animationName )
	{

		File animationDir = new File ( IMAGES, animationName );

		if ( !animationDir.isDirectory ( ) )
		{
			Log.e ( "Directory IO Error", "directory \"" + animationName + "\" does not exist!" );
			return null;
		}

		int nextFrameIndex = 1;

		ArrayList < Sprite > frames = new ArrayList < Sprite > ( );

		File directory, nextFile;

		directory = new File ( IMAGES, animationName );
		while ( ( nextFile = new File ( directory, animationName + nextFrameIndex + Constants.IMAGE_FILE_TYPE ) ).exists ( ) )
		{
			frames.add ( loadSpriteFile ( nextFile ) );
			nextFrameIndex++ ;
		}

		Log.v ( "Animation Loaded", "Loaded the " + animationName + " animation (" + frames.size ( ) + " frames)." );
		return new Animation ( animationName, frames.toArray ( new Sprite [ 1 ] ) );
	}



	public static Sprite loadSprite ( String filename )
	{
		Sprite result = loadSpriteFile ( new File ( IMAGES, filename + Constants.IMAGE_FILE_TYPE ) );
		Log.v ( "Sprite Loaded", "Loaded the " + filename + " sprite." );
		return result;
	}



	private static Sprite loadSpriteFile ( File spriteFile )
	{
		return new Sprite ( loadImageFile ( spriteFile ) );
	}



	private static BufferedImage loadImageFile ( File imageFile )
	{
		if ( !imageFile.isFile ( ) )
		{
			Log.e ( "File IO Error", "file \"" + imageFile.getPath ( ) + "\" does not exist!" );
			return null;
		}

		if ( imageMap.containsKey ( imageFile ) )
			return imageMap.get ( imageFile );

		try
		{
			BufferedImage img = ImageIO.read ( imageFile );

			imageMap.put ( imageFile, img );
			return img;
		}
		catch ( Exception e )
		{
			Log.e ( "Image IO", "\"" + imageFile.getPath ( ) + "\" could not be read! Error = " + e.getMessage ( ) );
		}
		return null;
	}
}
