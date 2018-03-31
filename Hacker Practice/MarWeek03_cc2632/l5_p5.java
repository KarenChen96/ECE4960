/*dates: 3/22/2018
 * authors: Chun Chen
 * Language: Java
 * Platforms: Eclipse in Windows10.
 * */

package hp_lecture5;

public class l5_p5 {
	public static void quasi_multi(double x1, double x2) {
		int num = 1;
		Functions f = new Functions();
		double f1 = f.quadractic(3, x1, 1, x2, -4);
		double f2 = f.quadractic(1, x1, -3, x2, 2);
		double v = f1 * f1 + f2 * f2;
		System.out.println(v);
		double[] d_x = delta_x(x1, x2);
		System.out.println(d_x[0]);
		double temp = Math.pow(f.quadractic(3, x1 + d_x[0], 1, x2 + d_x[1], -4), 2)
				+ Math.pow(f.quadractic(3, x1 + d_x[0], 1, x2 + d_x[1], -4), 2);
		System.out.println(temp);
		while (Math.abs(v) > Math.pow(10, -7)) {
			while (v > temp) {
				x1 = x1 + d_x[0];
				x2 = x2 + d_x[1];
				d_x = delta_x(x1, x2);
				v = temp;
				temp = Math.pow(f.quadractic(3, x1 + d_x[0], 1, x2 + d_x[1], -4), 2)
						+ Math.pow(f.quadractic(3, x1 + d_x[0], 1, x2 + d_x[1], -4), 2);
				num++;
			}
			
		}
		System.out.println(num);

	}

	// calculate delta(x) by quasi-newton method
	public static double[] delta_x(double x1, double x2) {
		Functions f = new Functions();
		double[] delta_x = new double[2];
		double[] j = inv_J(x1, x2);
		double f1 = f.quadractic(3, x1, 1, x2, -4);
		double f2 = f.quadractic(1, x1, -3, x2, 2);
		delta_x[0] = j[0] * f1 + j[1] * f2;
		delta_x[1] = j[2] * f1 + j[3] * f2;
		return delta_x;
	}

	// calculate inverse matrix of Jacobina matrix
	public static double[] inv_J(double x1, double x2) {
		double[] J = new double[4];
		Functions f = new Functions();
		J[0] = (f.quadractic(3, 1.0001 * x1, 1, x2, -4) - f.quadractic(3, x1, 1, x2, -4)) / (0.0001 * x1);
		//System.out.println(f.quadractic(3, 1.0001 * x1, 1, x2, -4));
		//System.out.println(f.quadractic(3, x1, 1, x2, -4));
		J[1] = (f.quadractic(3, x1, 1, 1.0001 * x2, -4) - f.quadractic(3, x1, 1, x2, -4)) / (0.0001 * x2);
		J[2] = (f.quadractic(1, 1.0001 * x1, -3, x2, 2) - f.quadractic(1, x1, -3, x2, 2)) / (0.0001 * x1);
		J[3] = (f.quadractic(1, x1, -3, 1.0001 * x2, 2) - f.quadractic(1, x1, -3, x2, 2)) / (0.0001 * x2);
		double temp = 1 / (J[0] * J[3] - J[1] * J[2]);
		double[] inv_J = new double[4];
		inv_J[0] = temp * J[3];
		inv_J[1] = -temp * J[1];
		inv_J[2] = -temp * J[2];
		inv_J[3] = temp * J[0];
		return inv_J;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		quasi_multi(0, 0);
	}

}
