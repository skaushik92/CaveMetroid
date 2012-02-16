
package view;

import java.awt.Dimension;
import java.awt.Toolkit;


public class Constants
{
	public static final long	serialVersionUID	= application.Constants.serialVersionUID;
	public static final int	WINDOW_POSITION_X	= 100;
	public static final int	WINDOW_POSITION_Y	= 50;
	public static int		WINDOW_HEIGHT;
	public static int		WINDOW_WIDTH;
	public static int		MONITOR_HEIGHT;
	public static int		MONITOR_WIDTH;
	public static String	MAIN_WINDOW_TITLE;
	static
	{
		WINDOW_HEIGHT = 800;
		WINDOW_WIDTH = 800;
		MAIN_WINDOW_TITLE = "Cave Metroid";
		Toolkit toolkit = Toolkit.getDefaultToolkit ( );
		Dimension dim = toolkit.getScreenSize ( );
		MONITOR_HEIGHT = dim.height;
		MONITOR_WIDTH = dim.width;
	}
}