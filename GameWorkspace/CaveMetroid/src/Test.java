

public class Test
{
	public static void main ( String [] args )
	{
		Thread t1 = new Thread ( new T1() );
		Thread t2 = new Thread ( new T2() );
		
		t1.start ( );
		t2.start ( );
	}
	
	static class T1 implements Runnable {

		@Override
		public void run ( )
		{
		}
		
	}
	
	static class T2 implements Runnable {

		@Override
		public void run ( )
		{
		}
	}
}
