
package model.time;

public class GameTime
{
	private static TimeInstant	START_TIME;

	private TimeInstant			currentTime;
	private TimeSpan			elapsedTime;
	private TimeSpan			totalElapsedTime;



	/**
	 * Constructs a GameTime object that stores the current
	 * time information.
	 * 
	 * @param lastTime
	 *             The previous GameTime object from which to
	 *             update.
	 */

	public GameTime ( )
	{
		currentTime = TimeInstant.now ( );

		if ( START_TIME == null )
		{
			START_TIME = currentTime;
		}

		totalElapsedTime = new TimeSpan ( START_TIME, currentTime );
		elapsedTime = totalElapsedTime;
	}



	/**
	 * Constructs a GameTime object that updates the time
	 * information from the given GameTime object.
	 * 
	 * @param lastTime
	 *             The previous GameTime object from which to
	 *             update.
	 */

	public GameTime ( GameTime lastTime )
	{
		currentTime = TimeInstant.now ( );

		if ( START_TIME == null )
		{
			START_TIME = currentTime;
		}

		totalElapsedTime = new TimeSpan ( lastTime.totalElapsedTime.getStartTime ( ), currentTime );
		elapsedTime = new TimeSpan ( lastTime.currentTime, currentTime );
	}



	public TimeInstant getCurrentTime ( )
	{
		return currentTime;
	}



	public TimeSpan getElapsedTime ( )
	{
		return elapsedTime;
	}



	public TimeSpan getTotalElapsedTime ( )
	{
		return totalElapsedTime;
	}
	
	
	public TimeSpan getTimeFrom ( TimeInstant someInstant ) {
		return new TimeSpan ( someInstant , currentTime );
	}
}
