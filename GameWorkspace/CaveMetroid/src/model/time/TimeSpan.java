
package model.time;

import java.util.Date;

public class TimeSpan
{
	TimeInstant	startTime;

	TimeInstant	endTime;



	public TimeSpan ( )
	{
		this.startTime = TimeInstant.ZERO;
		this.endTime = TimeInstant.ZERO;
	}



	public TimeSpan ( TimeInstant startTime, TimeInstant endTime )
	{
		this.startTime = startTime;
		this.endTime = endTime;
	}



	public TimeSpan ( TimeInstant startTime, long elapsedTimeMillis )
	{
		this.startTime = startTime;
		this.endTime = startTime.plus ( new TimeSpan ( TimeInstant.ZERO, new TimeInstant ( elapsedTimeMillis ) ) );
	}



	protected TimeInstant getStartTime ( )
	{
		return startTime;
	}



	protected TimeInstant getEndTime ( )
	{
		return endTime;
	}



	public long getMilliseconds ( )
	{
		return endTime.getTimeInMilliseconds ( ) - startTime.getTimeInMilliseconds ( );
	}

	public float getSeconds ( )
	{
		return getMilliseconds() / 1000.0f;
	}



	public boolean equals ( Object o )
	{
		return ( o instanceof TimeSpan ) && ( ( (TimeSpan) o ).getMilliseconds ( ) == getMilliseconds ( ) );
	}
	
	public String toString () {
		return new Date( getMilliseconds() ).toString();
	}
}
