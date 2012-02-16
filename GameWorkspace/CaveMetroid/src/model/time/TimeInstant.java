
package model.time;

import java.util.Date;

public class TimeInstant
{

	public static final TimeInstant	ZERO	= new TimeInstant ( 0 );

	private long					t;



	public TimeInstant ( long time )
	{
		this.t = time;
	}



	public TimeInstant plus ( TimeSpan elapsed )
	{
		return new TimeInstant ( t + elapsed.getMilliseconds ( ) );
	}



	public long getTimeInMilliseconds ( )
	{
		return t;
	}



	public static TimeInstant now ( )
	{
		return new TimeInstant ( System.currentTimeMillis ( ) );
	}



	public String toString ( )
	{
		return new Date ( t ).toString ( );
	}
}
