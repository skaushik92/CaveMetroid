import view.Constants;
import log.Log;
import application.Game;


public class Application
{
	/**
	 * All classes will have the same version so as to keep
	 * consistency within all classes.
	 */
	private static final long	serialVersionUID	= Constants.serialVersionUID;

	public static void main ( String [] args )
	{
		Log.v ( "Program State", "Application.main" );
		try {
			Game.play ( args );
		}
		catch (Exception e) {
			Log.e ( "Exception", e.getMessage ( ) );
			e.printStackTrace ( );
		}
	}
}
