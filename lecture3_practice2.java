/*dates: 2/10/2018
 * authors: Chun Chen
 * Language: Java
 * Platforms: Eclipse in Windows10.
 * */

package hacker_practice;

public class lecture3_practice2 {
	public static double y(double a, double t) {
		double y = Math.pow(Math.E, a*t);
		return y;
	}
	
	public static double fw(double dt, double a, double t)
	{
		double ft1 = (1 + a*dt) * y(a,t-dt);
		return ft1;
	}
	
	public static double bw(double dt, double a, double t)
	{
		double ft2 =( 1 / (1 - a*dt) ) * y(a,t-dt);
		return ft2;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		double t = 0;
		double dt = 0;
		double a = -1;
		double y = 0;
		double re_for = 0;
		double re_back = 0;	

		System.out.println("**********ground truth of evaluating f(t)**********");
		for (t = 0; t <= 20; t = t + 1) {
			// ground truth
			System.out.println(t);
			y = y(a, t);
			System.out.println("Ground Truth value is: " + y);		
			System.out.println("");
		}
		
		System.out.println("**********when delta(t) = 0.5, " + "forward and backward methods of evaluating f(t)**********");
		for (t = 0; t <= 20; t = t + 1) {
			System.out.println(t);
			dt = 0.5;
			// forward
			//re_for = (1 - dt) * y(a, t - dt);
			re_for = fw(dt, a, t);
			System.out.println("Forward Euler value is: " + re_for);
			// backward
			//re_back = (1 / (1 + dt)) * y(a, t - dt);
			re_back = bw(dt, a, t);
			System.out.println("Backward Euler value is: " + re_back);
			System.out.println("");
		}
		
		
		System.out.println("**********when delta(t) = 1.0, " + "forward and backward methods of evaluating f(t)**********");
		for (t = 0; t <= 20; t = t + 1) {
			dt = 1.0;
			System.out.println(t);
			// forward
			//re_for = (1 - dt) * y(a, t - dt);
			re_for = fw(dt, a, t);
			System.out.println("Forward Euler value is: " + re_for);
			// backward
			//re_back = (1 / (1 + dt)) * y(a, t - dt);
			re_back = bw(dt, a, t);
			System.out.println("Backward Euler value is: " + re_back);
			System.out.println("");
		}
		
		System.out.println("**********when delta(t) = 2.0, " + "forward and backward methods of evaluating f(t)**********");
		for (t = 0; t <= 20; t = t + 1) {
			dt = 2.0;
			System.out.println(t);
			// forward
			//re_for = (1 - dt) * y(a, t - dt);
			re_for = fw(dt, a, t);
			System.out.println("Forward Euler value is: " + re_for);
			// backward
			//re_back = (1 / (1 + dt)) * y(a, t - dt);
			re_back = bw(dt, a, t);
			System.out.println("Backward Euler value is: " + re_back);
			System.out.println("");
		}
		

	}

}
