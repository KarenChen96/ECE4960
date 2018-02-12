//import java.math.*;;

public class Week1 {	
	//generate +0 from inf if no such semantics in your language
	//generate inf by max_value ^ 2 or 10 ^ 1000
	
	//+0 == -0, while INF != NINF
	/*Function to test +0*/
	static boolean isPositiveZero(double x)
	{
		if (1.0/x == 1.0/0.0)
		{
			return true;
		}
		else return false;
	}
	
	/*Function to test -0*/
	static boolean isNegativeZero(double x)
	{
		if (1.0/x == 1.0/(-0.0))
		{
			return true;
		}
		else return false;
	}
	
	/*Function to test INF*/
	static boolean isPositiveInf(double x)
	{
		if (x == 1.0/0.0)
		{
			return true;
		}
		else return false;
	}
	
	/*Function to test NINF*/
	static boolean isNegativeInf(double x)
	{
		if (x == -1.0/0.0) 
		{
			return true;
		}
		else return false;
	}
	
	/*Function1 to test NaN*/
	static boolean is_NaN(double x)
	{
		if (x != x)
		{
			return true;
		}
		else return false;
	}
	
	/*Function2 to test NaN*/
	static boolean NaN(double x)
	{
		if (Double.isNaN(x))
		{
			return true;
		}
		else return false;
	}
	
	/*Solve the quadratic equation*/
	static double[] equation(double a, double b, double c)
	{
		double x1 = (-b+Math.sqrt(b*b - 4*a*c)) / (2*a);
		double x2 = (-b-Math.sqrt(b*b - 4*a*c)) / (2*a);
		
		double x3 = (-2*c) / (b+Math.sqrt(b*b - 4*a*c));
		double x4 = (-2*c) /(b-Math.sqrt(b*b - 4*a*c)) ;
		
		double result[] = {x1,x2,x3,x4};
		
		return result;
	}
	
	
	public static void main(String[] arg){

		/*Hacker Practice: Solve the quadratic equation*/
		//Potential problem: UNDERFLOW???
		System.out.println("*****Solve the quadratic equation*****");
		double a = Math.pow(10, -20);
		double b = Math.pow(10, 3);
		double c = Math.pow(10, 3);
		double roots[] = equation(a, b, c);
		for (int i = 0; i < roots.length; i++)
		{
			System.out.println("root"+i+" = "+roots[i]);	
		}
		System.out.println("");
		
		/*Hacker Practice (1) */
		System.out.println("*****Observe NaN and Inf handling in double*****");
		//Observe NaN and Inf handling in double
		double x = 0.0; 
		double y = 0.0;		
		try
		{
			double in1 = 1/x;			
			System.out.println(in1);				
		}catch (ArithmeticException e)
		{
			System.out.println("Double can not handle NaN.");
		}
		try
		{						
			double in2 = y/x;		
			System.out.println(in2);	
		}catch (ArithmeticException e)
		{
			System.out.println("Double can not handle Inf.");
		}
		System.out.println("");
		
		//Observe NaN and Inf handling in integers
		System.out.println("*****Observe NaN and Inf handling in integers*****");
		long m = 0;
		long n = 0;
		try
		{
			int re1 = (int) (1/m);			
			System.out.println(re1);		
		}catch (ArithmeticException e)
		{
			System.out.println("Integers can not handle NaN.");
		}
		try
		{			
			int re2 = (int) (m/n);			
			System.out.println(re2);
		}catch (ArithmeticException e)
		{
			System.out.println("Integers can not handle Inf.");
		}
		System.out.println("");
		

		/*Hacker Practice (2): Observe overflow handling in integers*/
		System.out.println("*****Observe overflow handling in integers*****");
		long i = 0; 
		int fac = 1;
		for (i = 2; i < 30; i++)
		{
			fac*=(int) i;
			System.out.println(i);
			System.out.println(fac);
		}
		
		for (i = 20; i > 1; i--)
		{
			fac /= i;
			System.out.println(fac);
		}	
		System.out.println("");

		//generate infinity
//		float x = Double.POSITIVE_INFINITY;
//		System.out.println(1/(1/x));
//		float y = Double.NEGATIVE_INFINITY;
//		System.out.println(1/(1/y));		
		
		//generate NaN
		double NaN1 = Double.POSITIVE_INFINITY - Double.POSITIVE_INFINITY;
		double NaN2 = Double.POSITIVE_INFINITY * Double.POSITIVE_INFINITY;
				
		double test[] =  {+1.0, -1.0, Double.MAX_VALUE, -1.0 * Double.MAX_VALUE, 
		          1.0/Double.POSITIVE_INFINITY, 1/Double.NEGATIVE_INFINITY, 
		          Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY, NaN1};
		
		/*Hacker Practice: Test +0, -0, INF, NINF, NaN*/
		System.out.println("*****Test +0, -0, INF, NINF, NaN*****");
		for (int j = 0; j < test.length; j++)
		{
			System.out.println("Results when input is " + test[j]);
			System.out.println(isPositiveZero(test[j]));
			System.out.println(isNegativeZero(test[j]));
			System.out.println(isPositiveInf(test[j]));
			System.out.println(isNegativeInf(test[j]));
			System.out.println(is_NaN(test[j]));
			System.out.println("");
		}
		System.out.println("");
				
		/*Hacker Practice: Observe the exception handling on your platform*/
		System.out.println("*****Observe the exception handling on your platform*****");
		//make x with easily observable precision
		double z = 1.234567890123456;
		int k =1;
		//The normalized number is above 4.9406*10^(-324)
		z *= Math.pow(10, -307);		
		//Decrease the normalized number to the range of denormals	
		for ( k = 1; k <20 ; k++)
		{
			z /= 10.0;		
			System.out.println(z);
		}
		
		/*Other tests*/
//		System.out.println((NaN1-NaN2 == 0));
//		System.out.println((NaN1 == NaN2));
//		System.out.println((+0 == -0));
//		System.out.println(Double.MIN_VALUE);
//		System.out.println(Double.MAX_VALUE);
//		System.out.println(1.0/0.0 == 2.0/0.0);
//		System.out.println(null == null);
//		System.out.println(NaN1 == NaN2);
//		System.out.println(Double.isNaN(Double.NaN));
//		System.out.println(NaN(Double.NaN));
//		System.out.println(Double.MAX_VALUE * Double.MAX_VALUE);
//		System.out.println(isPositiveZero(-0.0));
//		System.out.println(isPositiveInf(-0.0));
	}

}
