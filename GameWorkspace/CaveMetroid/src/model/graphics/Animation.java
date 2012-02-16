
package model.graphics;

import java.util.Arrays;

import view.Constants;

import log.Log;


public class Animation
{
	/**
	 * All classes will have the same version so as to keep
	 * consistency within all classes.
	 */
	private static final long	serialVersionUID	= Constants.serialVersionUID;
	
	private String		name;
	
	private Sprite []	frames;
	private float []	frameTimes;

	private boolean	repeat;

	private float		currentTime;
	private int		currentFrame;

	private boolean	flip;


	
	public Animation ( String animationName, Sprite [] animationFrames )
	{
		name = animationName;
		
		frames = animationFrames;
		frameTimes = new float [ frames.length ];

		currentFrame = 0;
		currentTime = 0;

		setFrameTimes ( 1 );
		setRepeat ( true );
		setFlip ( false );
	}


	public void reset () {
		currentTime = 0;
		currentFrame = 0;
		repeat = true;
		flip = false;
	}
	
	public void setFrameTimes ( float secondsPerFrame )
	{
		Arrays.fill ( frameTimes, secondsPerFrame );
	}



	public void setFrameTimes ( float [] secondsForEachFrame )
	{
		if ( frames.length != secondsForEachFrame.length )
			Log.e ( "Animation Frame Error", "Frames and frameTimes do not have same length! Frames: " + frames.length + " FrameTimes: " + secondsForEachFrame );

		for (int i = 0; i < Math.min ( secondsForEachFrame.length, frames.length ); i++ )
			frameTimes[ i ] = secondsForEachFrame[ i ];
	}



	public void setRepeat ( boolean shouldRepeat )
	{
		repeat = shouldRepeat;
	}



	public void setFlip ( boolean shouldFlip )
	{
		flip = shouldFlip;
	}



	public void moveForward ( float timeElapsedInSecs )
	{
		if ( currentFrame >= frames.length - 1 && !repeat )
			return;
		currentTime += timeElapsedInSecs;
		while ( currentTime > frameTimes[ currentFrame ] )
		{
			currentTime -= frameTimes[ currentFrame ];
			currentFrame += 1;

			if ( currentFrame >= frames.length )
			{
				currentFrame = repeat ? 0 : frames.length - 1;
				if ( currentFrame == frames.length - 1 && !repeat )
					return;
			}
		}
	}



	public Sprite getCurrentSprite ( )
	{
		return flip ? frames[ currentFrame ].flipped() : frames[ currentFrame ];
	}


	public boolean getFlip ( )
	{
		return flip;
	}


	public String getAnimationName ( )
	{
		return name;
	}
}
