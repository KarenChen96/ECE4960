/*dates: 3/4/2018
 * authors: Chun Chen
 * Language: Java
 * Platforms: Eclipse in Windows10.
 * */

/*Use your choice of the full matrix solver for Ax=b, for e = 10-2, 10-3, бн, 10-9.  
 * Print out the value of (x, y) and the second norm of the residual vector.
 */
package hacker_practice;

public class lecture4_practice3 {
	
	public static double cal_y(double a1, double b1, double c1, double a2, double b2, double c2)
	{
		double y = (c1-c2*a1/a2)/(b1-a1*b2/a2);
		return y;
	}
	
	public static double cal_x(double a1, double b1, double c1, double a2, double b2, double c2)
	{
		double x = (c1-c2*b1/b2)/(a1-a2*b1/b2);
		return x;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	}


}
