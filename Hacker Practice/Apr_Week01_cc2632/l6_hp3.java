/*dates: 3/29/2018
 * authors: Chun Chen
 * Language: Java
 * Platforms: Eclipse in Windows10.
 * */

package hp_lecture6;

public class l6_hp3 {
	//derivative (dx/dt)
	public static double fx(double x, double t)
	{
		double y = 4*Math.exp(0.8*t) - 0.5*x;
		return y;
	}
	//function x(t)
	public static double x(double t)
	{
		double x = 4 * (Math.exp(0.8*t) - Math.exp(-0.5*t))/1.3 + 2*Math.exp(-0.5*t);
		return x;
	}
	
	//functions for calculating k1, k2, k3, k4
	public static double K1(double x, double t)
	{
		double k1 = fx(x,t);
		return k1;
	}
	
	public static double K2(double x, double t, double h)
	{
		double k1 = K1(x,t);
		double k2 = fx(x + k1*h/2, t+h/2);
		return k2;
	}
	
	public static double K3(double x, double t, double h)
	{
		double k2 = K2(x,t,h);
		double k3 = fx(x + k2*h/2, t+h/2);
		return k3;
	}
	
	public static double K4(double x, double t, double h)
	{
		double k3 = K3(x,t,h);
		double k4 = fx(x + k3*h, t+h);
		return k4;
	}
	
	//function for calculating predicted value of x(i+1) by using k1, k2, k3, k4
	//x: x t:time h:step, set as 1
	public static double X_RK4(double x, double t, double h)
	{
		double k1 = K1(x,t);
		double k2 = K2(x,t,h);
		double k3 = K3(x,t,h);
		double k4 = K4(x,t,h);
		double x_rk4 = x + (k1+2*k2+2*k3+k4)*h/6;
		System.out.println("k1 = " + k1 + "	k2 = " + k2 + "	  k3 = " + k3 + "	k4 = " + k4);
		System.out.println("x_rk4 = " + x_rk4);
		return x_rk4;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		double[] x_true = {2, 6.1946, 14.843, 33.677, 75.339};
		double h = 1;
		for(int t = 0; t<4; t++)
		{
			System.out.println("x_true = " + x_true[t]);
			double x_rk4 = X_RK4(x_true[t],t,h);
			System.out.println("Realtive Error is " + 100 * (x_rk4 - x_true[t+1]) / x_true[t+1] + "%" );
			System.out.println(" ");
		}
		
		System.out.println("x_true = " + x_true[4]);
	    X_RK4(x_true[4],4,h);
	}
}
