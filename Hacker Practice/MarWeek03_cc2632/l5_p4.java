/*dates: 3/20/2018
 * authors: Chun Chen
 * Language: Java
 * Platforms: Eclipse in Windows10.
 * */

package hp_lecture5;

/*Use the quasi-Newton method with line search 
to solve the same nonlinear equation
by making x(0) = 1 and the local analysis of 
the Jacobian matrix by 10-4 perturbation*/

public class l5_p4 {
	public static void Quasi_Newton(double xk) {		
		double y = fx(xk);
		int num = 1;
		while(Math.abs(y-0) > Math.pow(10,-7)) {
			double del_x =-fx(xk)/ ((fx(1.0001*xk)-fx(xk))/(0.0001*xk));
			double t = 1.0;
			double temp1 = fx(xk + t * del_x);
			double temp2 = fx(xk + 0.5 * t * del_x);
			while (temp1 > temp2) {
				temp1 = temp2;
				t = t / 2;
				temp2 = fx(xk + 0.5 * t * del_x);
			}
			y = temp1;
			
			System.out.print("x(k): " + xk + "	");
			System.out.print("delta(x(k)): " + del_x + "		");
			System.out.print("f(x(k)): " + y + "		");
			System.out.println("t = " + t);
			System.out.println(" ");
			
			num = num +1;
			xk = xk + t * del_x;
		}
		System.out.println(y);
		System.out.println("The number of iteration is: " + num);
	}

	public static double fx(double x)
	{
		return Math.exp(100*x) -1;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		double x = 1;	
		System.out.println("When x(0) is " + x);
		Quasi_Newton(x);
		System.out.println(" ");
	}

}
