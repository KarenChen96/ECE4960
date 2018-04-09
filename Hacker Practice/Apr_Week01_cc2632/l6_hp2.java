/*dates: 3/29/2018
 * authors: Chun Chen
 * Language: Java
 * Platforms: Eclipse in Windows10.
 * */

package hp_lecture6;

public class l6_hp2 {
	
	public static double fx(double x, double t)
	{
		double y = 4*Math.exp(0.8*t) - 0.5*x;
		return y;
	}
	
	public static double x(double t)
	{
		double x = 4 * (Math.exp(0.8*t) - Math.exp(-0.5*t))/1.3 + 2*Math.exp(-0.5*t);
		return x;
	}
	
	public static double forward(double x, double x1, double t, double h)
	{
		double xj = x+fx(x,t) * h;
		System.out.println("Relative error of Foward Euler is " + Math.abs(100*(xj-x1)/xj) + "%");
		return xj;
	}
	
	public static double trap(double x_i, double x_i1, double t, double h) {
		double phi = (fx(x_i,t) + fx(x_i1, t+1)) / 2;
		double xj = x_i + phi * h;
		System.out.println("Relative error of one-step Huen is " + Math.abs(100*(xj-x_i1)/xj) + "%");
		return xj;
	}
	
	public static double huen_test(double x_i, double x_i1, double t, double h, double tol)
	{
		int num = 0;
		double x_j_1 = x_i1;
		double x1 = x_i + fx(x_i,t) * h;
		double phi = (fx(x_i,t) + fx(x_j_1, t+1)) / 2;
		double xj = x_i + phi * h;
		while (Math.abs((xj-x_j_1)/xj) > tol)
		{
			x_j_1 = xj;
			phi = (fx(x_i,t) + fx(x_j_1, t+1)) / 2;
			xj = x_i + phi*h;
			num ++;
		}
		System.out.println("Relative error of iterative Huen is " + Math.abs(100*(xj-x_i1)/xj) + "%");
		return xj;
	}
	
	
	public static double huen(double x, double t, double h, double tol)
	{
		int num = 0;
		double x1 = x + fx(x,t) * h;
		double phi = (fx(x,t) + fx(x1, t+1)) / 2;
		double x2 = x + phi * h;
		while (Math.abs((x2-x1)/x2) > tol)
		{
			x1 = x2;
			phi = (fx(x,t) + fx(x1, t+1)) / 2;
			x2 = x + phi*h;
			num ++;
		}
		System.out.println("Relative error is " + Math.abs((x2-x1)/x2));
		return x2;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		double[] x_true = {2, 6.1946, 14.843, 33.677, 75.339};
		double h = 1;
		double tol = Math.pow(10, -7);
		double x_pre = 0;
		double x_for = 0;
		double x_trap = 0;
		for(int t = 0; t<4; t++)
		{
			System.out.println("x_true[" + t + "] = "+ x_true[t]);
			x_for = forward(x_true[t],x_true[t+1],t,h);
			System.out.println("x_forward[" + (t+1) +"] = " + x_for );
			x_trap = trap(x_true[t],x_true[t+1],t,h);
			System.out.println("x_trap[" + (t+1) +"] = " + x_trap);
			x_pre = huen_test(x_true[t],x_true[t+1],t,h,tol);
			System.out.println("x_Huen_ite[" + (t+1) +"] = " + x_pre );
			System.out.println("");
		}
	}
}
