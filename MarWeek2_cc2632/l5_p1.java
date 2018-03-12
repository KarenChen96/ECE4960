/*dates: 3/11/2018
 * authors: Chun Chen
 * Language: Java
 * Platforms: Eclipse in Windows10.
 * */

package hp_lecture5;

public class l5_p1 {
	//a and b are bounds in which a<=b(x -- [a,b])
	public static double bisection(double a, double b, double tol) {
		double r = fx((a + b)/2);
		while (Math.abs(r-0) > tol) {
			System.out.print("a is: "+ a + " ");
			System.out.print("b is: " + b + " ");
			System.out.print("mid-point is: " + (a+b)/2 + " ");
			System.out.println("The value of fx is:" + fx((a+b)/2) + " ");
			System.out.println(" ");
			if (r*fx(b) > 0) {
				b = (a + b) / 2;
			} else if (r*fx(b) < 0) {
				a = (a + b)/2;
			}
			r = fx((a + b)/2);
		}
		return fx((a + b) / 2);
	}
	
	public static double[] quadtree(double x1, double x2, double y1, double y2, double tol)
	{
		double[] result = new double[2];
		double r1 = fx1((x1+x2)/2,(y1+y2)/2);
		double r2 = fx2((x1+x2)/2,(y1+y2)/2);
		while(Math.abs(r1-0) > tol || Math.abs(r2-2) > tol)
		{
			double temp1 = fx1(x1,y1)*fx1(x1,y2)*fx1(x2,y1)*fx1(x2,y2);
			double temp2 = fx2(x1,y1)*fx2(x1,y2)*fx2(x2,y1)*fx2(x2,y2);
			boolean flag1 = false;
			boolean flag2 = false;
			if ((fx1(x1, y1) > 0 && fx1(x1, y2) > 0 && fx1(x2, y1) > 0 && fx1(x2, y2) > 0)
					|| (fx1(x1, y1) > 0 && fx1(x1, y2) > 0 && fx1(x2, y1) > 0 && fx1(x2, y2) > 0)) {
				flag1 = true;
			}
			if ((fx2(x1, y1)>0 && fx2(x1, y2)>0 && fx2(x2, y1)>0 && fx2(x2, y2)>0)
					|| (fx2(x1, y1)>0 && fx2(x1, y2)>0 && fx2(x2, y1)>0 && fx2(x2, y2)>0)) {
				flag2 = true;
			}
			//If either function has same sign at the four vertices, stop processing that region.
			if (flag1 || flag2) {
				break;
			}
			//If both the functions have a sign change at the vertices, evaluate at the center point. 
			else
			{
				
			}
		}
		return result;
	}
	
	public static double fx(double x)
	{
		return Math.exp(x)-1;
	}
	
	public static double fx1(double x, double y)
	{
		return Math.exp(x) - Math.exp(y);
	}
	public static double fx2(double x, double y)
	{
		return Math.exp(x) + Math.exp(y);
	}
	
	public double residual(double y, double r)
	{
		return Math.abs(y-r);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		double a = -5; 
		double b =10;
		double t = Math.pow(10, -7);
		bisection(a,b,t);
	}

}
