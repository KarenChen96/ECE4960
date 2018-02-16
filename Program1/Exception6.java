package program1;

public class Exception6 {
	// Singed zero
	public static void main(String[] args)  {
		// TODO Auto-generated method stub	
//		FileWriter out1 = new FileWriter("report1.txt");
//		FileWriter out2 = new FileWriter("report2.txt");
		
		System.out.println("*****Observe signed zero handling by performing different functions*****");
		System.out.println("");
		double x1 = 1.0 / +0.0; //INF	
		double x2 = 1/ -0.0; //NINF
		double x3 = 2/0.0;
		double x4 = -2/0.0;	
		
		double y1 = 1 / x1; // create positive zero
		double y2 = 1 / x2; // create negative zero
		//System.out.println(y1);
		//System.out.println(y2);
		double r1 = Math.log(y1);
		double r2 = Math.log(y2);
		double r3 = Math.sin(y1) / y1;
		double r4 = Math.sin(y2) / y2;
		double r5 = Math.sin(y2) / Math.abs(y2);
		System.out.println("The result of log(+0) is: " + r1);
		System.out.println("");
		System.out.println("The result of log(-0) is: " + r2);
		System.out.println("");
		System.out.println("The result of sin(+0)/+0 is: " + r3);
		System.out.println("");
		System.out.println("The result of sin(-0)/-0 is: " + r4);
		System.out.println("");
		System.out.println("The result of sin(-0)/|-0| is: " + r5);
		System.out.println("\r\n");
		System.out.println("");
		
//		out1.flush();
//		out2.flush();
//		out1.close();
//		out2.close();
		
//		if ( (r1== Double.POSITIVE_INFINITY)  )
//		{
//			if (r2 == Double.POSITIVE_INFINITY)
//			{
//				if (r3 == 1)
//				{
//					if (r4 == 1)
//					{
//						if (r5 == )
//					}
//				}
//			}
//		}

	}

}
