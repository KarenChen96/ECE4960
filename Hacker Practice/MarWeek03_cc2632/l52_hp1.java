/*dates: 3/22/2018
 * authors: Chun Chen
 * Language: Java
 * Platforms: Eclipse in Windows10.
 * */

/* Use the Quasi Newton method to perform the parameter 
 * extraction for the power-law function y = a*math.exp(x,m)
 * with the known measurements*/

package hp_lecture52;

public class l52_hp1 {
	public static double fx(double a, double m, double x) {
		double y = a * Math.pow(x, m);
		return y;
	}

	public static double V(double a, double m, double[] x, double[] y) {
		double v = 0;
		for (int i = 0; i < x.length; i++) {
			v = v + Math.pow(fx(a, m, x[i]) - y[i], 2);
		}
		return v;
	}

	// partial derivative w.r.t a
	public static double v_a(double a, double m, double[] x, double[] y) {
		double v_a = 0;
		for (int i = 0; i < x.length; i++) {
			v_a = v_a + a * Math.pow(x[i], 2 * m) - y[i] * Math.pow(x[i], m);
		}
		return v_a;
	}

	// partial derivative w.r.t m
	public static double v_m(double a, double m, double[] x, double[] y) {
		double v_b = 0;
		for (int i = 0; i < x.length; i++) {
			v_b = v_b + a * Math.log(x[i]) * v_a(a, m, x, y);
		}
		return v_b;
	}

	// f1's derivative with respect to a
	public static double f1_a(double a, double m, double[] x, double[] y) {
		double f1_a = 0;
		for (int i = 0; i < x.length; i++) {
			f1_a = f1_a + Math.pow(x[i], 2 * m);
		}
		return f1_a;
	}

	// f1's derivative with respect to a
	public static double f1_m(double a, double m, double[] x, double[] y) {
		double f1_m = 0;
		double part1 = 0;
		double part2 = 0;
		for (int i = 0; i < x.length; i++) {
			part1 = part1 + Math.log(x[i]) * Math.pow(x[i], 2 * m);
			part2 = part2 + Math.log(x[i]) * Math.pow(x[i], m) * y[i];
		}
		f1_m = 2 * a * part1 - part2;
		return f1_m;
	}

	// f2's derivative with respect to a
	public static double f2_a(double a, double m, double[] x, double[] y) {
		double f2_a = 0;
		double part1 = 0;
		double part2 = 0;
		for (int i = 0; i < x.length; i++) {
			part1 = part1 + Math.log(x[i]) * Math.pow(x[i], 2 * m);
			part2 = part2 + Math.log(x[i]) * Math.pow(x[i], m) * y[i];
		}
		f2_a = 2 * a * part1 - part2;
		return f2_a;
	}

	// f2's derivative with respect to m
	public static double f2_m(double a, double m, double[] x, double[] y) {
		double f2_m = 0;
		double part1 = 0;
		double part2 = 0;
		for (int i = 0; i < x.length; i++) {
			part1 = part1 + Math.log(x[i]) * Math.log(x[i]) * Math.pow(x[i], 2 * m);
			part2 = part2 + Math.log(x[i]) * Math.log(x[i]) * Math.pow(x[i], m) * y[i];
		}
		f2_m = 2 * a * a * part1 - a * part2;
		return f2_m;
	}

	// calculate inverse matrix of Jacobian matrix
	public static double[] inv_J(double a, double m, double[] x, double[] y) {
		double[] J = new double[4];
		J[0] = f1_a(a, m, x, y);
		J[1] = f1_m(a, m, x, y);
		J[2] = f2_a(a, m, x, y);
		J[3] = f2_m(a, m, x, y);
		double temp = 1 / (J[0] * J[3] - J[1] * J[2]);
		double[] inv_J = new double[4];
		inv_J[0] = temp * J[3];
		inv_J[1] = -temp * J[1];
		inv_J[2] = -temp * J[2];
		inv_J[3] = temp * J[0];
		return inv_J;
	}

	// calculate delta(x) by quasi-newton method
	public static double[] delta_x(double a, double m, double[] x, double[] y) {
		double[] delta_x = new double[2];
		double[] j = inv_J(a, m, x, y);
		double v_a = v_a(a, m, x, y);
		double v_m = v_m(a, m, x, y);
		delta_x[0] = -(j[0] * v_a + j[1] * v_m);
		delta_x[1] = -(j[2] * v_a + j[3] * v_m);
		return delta_x;
	}

	public static void extraction(double a, double m, double[] x, double[] y) {
		int num = 0;
		double v = V(a, m, x, y);
		double[] d_x = delta_x(a, m, x, y);
		while (num <= 100) {
			System.out.print("V(a,m) =  " + v + "	 ");
			System.out.print("a is: " + a + "	m is: " + m + " ");
			System.out.println("||delta(x(k))|| = " + Math.sqrt((d_x[0] * d_x[0] + d_x[1] * d_x[1])) + " ");
			a = a + d_x[0];
			m = m + d_x[1];
			d_x = delta_x(a, m, x, y);
			num++;
			if ((d_x[0] * d_x[0] + d_x[1] * d_x[1]) < Math.pow(10, -7)) {
				break;
			}
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		double[] x = { 1.0, 4.5, 9.0, 20, 74, 181 };
		double[] y = { 3.0, 49.4, 245, 1808, 2.2 * Math.pow(10, 4), 7.3 * Math.pow(10, 4) };
		double a0 = 2;
		double m0 = 1;
		extraction(a0, m0, x, y);
	}
}