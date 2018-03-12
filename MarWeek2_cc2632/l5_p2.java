/*dates: 3/11/2018
 * authors: Chun Chen
 * Language: Java
 * Platforms: Eclipse in Windows10.
 * */

package hp_lecture5;

public class l5_p2 {
	public static void Newton(double xk) {
		double xk1 = xk;
		double y = fx3(xk);
		int num = 1;
		while(Math.abs(y-0) > Math.pow(10,-7)) {
			xk = xk1;
			double del_x =-fx3(xk)/fx31(xk);
//			System.out.println("Quadratic convergence is: "+ Math.abs(del_x)/xk);
			System.out.print("x(k): " + xk1 + "		");
			System.out.print("delta(x(k)): " + del_x + "		");
			System.out.println("f(x(k)): " + y + "		");
			System.out.println(" ");
			xk1 = xk+del_x;
			y=fx3(xk1);
			num = num +1;
		}
		System.out.println("The number of iteration is: " + num);
	}

	public static double fx3(double x)
	{
		return Math.exp(50*x) -1;
	}
	
	public static double fx31(double x)
	{
		return 50*Math.exp(50*x);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		double[] x = {1, 10};
		for (int i = 0; i<x.length; i++)
		{
			double x0 = x[i];
			System.out.println("When x(0) is " + x0);
			Newton(x0);
			System.out.println(" ");
		}
	}

}
