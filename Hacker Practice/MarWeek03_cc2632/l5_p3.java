/*dates: 3/20/2018
 * authors: Chun Chen
 * Language: Java
 * Platforms: Eclipse in Windows10.
 * */
package hp_lecture5;

/*Use the Newton method with line search 
to solve the same nonlinear equation 
by making x(0) = 10. choose a t
*/
public class l5_p3 {
	public static void Newton(double xk) {
		double y = fx3(xk);
		int num = 1;
		while (Math.abs(y - 0) > Math.pow(10, -7)) {
			double del_x = -fx3(xk) / fx31(xk);
			// System.out.println("Quadratic convergence is: "+ Math.abs(del_x)/xk);
			double t = 1.0;
			double temp1 = fx3(xk + t * del_x);
			double temp2 = fx3(xk + 0.5 * t * del_x);
			while (temp1 > temp2) {
				temp1 = temp2;
				t = t / 2;
				temp2 = fx3(xk + 0.5 * t * del_x);
			}
			y = temp1;

			System.out.print("x(k): " + xk + " ");
			System.out.print("delta(x(k)): " + del_x + " ");
			System.out.print("f(x(k)): " + y + " ");
			System.out.println("t is " + t);
			num = num + 1;
			System.out.println("The number of iteration is: " + num);

			xk = xk + t * del_x;
		}
	}

	public static double fx3(double x) {
		return Math.exp(50 * x) - 1;
	}

	public static double fx31(double x) {
		return 50 * Math.exp(50 * x);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		double[] x = { 1 };
		for (int i = 0; i < x.length; i++) {
			double x0 = x[i];
			System.out.println("When x(0) is " + x0);
			Newton(x0);
			System.out.println(" ");
		}
	}
}
