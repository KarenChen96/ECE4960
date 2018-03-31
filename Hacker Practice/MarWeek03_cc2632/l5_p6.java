/*dates: 3/22/2018
 * authors: Chun Chen
 * Language: Java
 * Platforms: Eclipse in Windows10.
 * */

package hp_lecture5;

public class l5_p6 {
	/*
	 * Use the steepest descent method with line search to solve the nonlinear
	 * optimization function V
	 */

	public static void stp_des(double x1, double x2) {
		int num = 1;
		double v = f1(x1, x2) + f2(x1, x2);
		double[] d_x = delta_x(x1, x2);
		while (Math.abs(v) > Math.pow(10, -7)) {
			System.out.print("V(x(k)) =  " + v + "	 ");
			System.out.print("||x(k)|| = " + Math.sqrt(x1 * x1 + x2 * x2) + "	");
			System.out.print("x1 is: " + x1 + "x2 is: " + x2 + "   ");
			System.out.println("||delta(x(k))|| = " + Math.sqrt((d_x[0] * d_x[0] + d_x[1] * d_x[1])) + " ");

			x1 = x1 + d_x[0];
			x2 = x2 + d_x[1];
			v = f1(x1, x2) + f2(x1, x2);
			// v = f1(x1 + d_x[0], x2 + d_x[1]) + f2(x1 + d_x[0], x2 + d_x[1]);
			d_x = delta_x(x1, x2);
			num++;
		}
		System.out.println("v is " + v);
		System.out.println(num);
	}

	// calculate delta(x) by quasi-newton method
	public static double[] delta_x(double x1, double x2) {
		double[] delta_x = new double[2];
		double d_x1 = 0;
		double d_x2 = 0;
		if (x1 != 0) {
			d_x1 = 0.0001 * x1;
		} else
			d_x1 = 0.0001;
		if (x2 != 0) {
			d_x2 = 0.0001 * x2;
		} else
			d_x2 = 0.0001;

		delta_x[0] = -((f1(x1 + d_x1, x2) + f2(x1 + d_x1, x2)) - (f1(x1, x2) + f2(x1, x2))) / d_x1;
		delta_x[1] = -((f1(x1, x2 + d_x2) + f2(x1, x2 + d_x2)) - (f1(x1, x2) + f2(x1, x2))) / d_x2;

		double[] t = new double[2];
		double t1 = 1.0;
		double v_x1 = ((f1(x1 + d_x1, x2) + f2(x1 + d_x1, x2)) - (f1(x1, x2) + f2(x1, x2))) / d_x1;
		double temp1 = -t1 * v_x1;
		double temp2 = -0.5 * t1 * v_x1;
		// System.out.println("temp1 = "+temp1); System.out.println("temp2 = "+temp2);
		while (temp1 > temp2) {
			temp1 = temp2;
			t1 = t1 / 2;
			temp2 = -0.5 * t1 * v_x1;
		}
		System.out.print("t1 = " + t1 + " ");
		delta_x[0] = -t1 * v_x1;

		x1 = x1 + delta_x[0];
		double v_x2 = ((f1(x1, x2 + d_x2) + f2(x1, x2 + d_x2)) - (f1(x1, x2) + f2(x1, x2))) / d_x2;
		double t2 = 1.0;
		double temp3 = -t2 * v_x2;
		double temp4 = -0.5 * t2 * v_x2;
		// System.out.println("temp3 = "+temp3); System.out.println("temp4 = "+temp4);
		while (temp3 > temp4) {
			temp3 = temp4;
			t2 = t2 / 2;
			temp4 = -0.5 * t2 * v_x2;
		}
		System.out.println("t2 = " + t2 + " ");

		delta_x[1] = -t2 * v_x2;

		System.out.println("delta_x1 = " + delta_x[0]);
		System.out.println("delta_x2 = " + delta_x[1]);
		return delta_x;
	}

	public static double[] opt(double x1, double x2, double d_x1, double d_x2) {
		double[] t = new double[2];
		double t1 = 1.0;
		double temp1 = f1(x1 + t1 * d_x1, x2) + f2(x1 + t1 * d_x1, x2);
		double temp2 = f1(x1 + 0.5 * t1 * d_x1, x2) + f2(x1 + 0.5 * t1 * d_x1, x2);
		// System.out.println("temp1 = "+temp1); System.out.println("temp2 = "+temp2);
		while (temp1 > temp2) {
			temp1 = temp2;
			t1 = t1 / 2;
			temp2 = f1(x1 + 0.5 * t1 * d_x1, x2) + f2(x1 + 0.5 * t1 * d_x1, x2);
		}
		System.out.print("t1 = " + t1 + "	 ");
		t[0] = t1;

		double t2 = 1.0;
		double temp3 = f1(x1 + t1 * d_x1, x2 + t2 * d_x2) + f2(x1 + t1 * d_x1, x2 + t2 * d_x2);
		double temp4 = f1(x1 + t1 * d_x1, x2 + 0.5 * t2 * d_x2) + f2(x1 + t1 * d_x1, x2 + 0.5 * t2 * d_x2);
		// System.out.println("temp3 = "+temp3); System.out.println("temp4 = "+temp4);
		while (temp3 > temp4) {
			temp3 = temp4;
			t2 = t2 / 2;
			temp4 = f1(x1 + t1 * d_x1, x2 + 0.5 * t2 * d_x2) + f2(x1 + 0.5 * t1 * d_x1, x2 + 0.5 * t2 * d_x2);
		}
		System.out.println("t2 = " + t2 + "		");
		t[1] = t2;
		return t;
	}

	public static double f1(double x1, double x2) {
		double y = 3 * x1 * x1 + x2 - 4;
		return y * y;
	}

	public static double f2(double x1, double x2) {
		double y = x1 * x1 - 3 * x2 + 2;
		return y * y;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		stp_des(0.8, 0.8);
		// stp_des(-1,1.2);
		// stp_des(1,1.2);
	}
}