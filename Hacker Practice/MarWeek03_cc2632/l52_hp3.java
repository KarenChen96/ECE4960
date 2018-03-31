/*dates: 3/23/2018
 * authors: Chun Chen
 * Language: Java
 * Platforms: Eclipse in Windows10.
 * */
package hp_lecture52;

import java.util.Arrays;

public class l52_hp3 {
	public static double cal_pi(int N) {
		double[] x = new double[N];
		double[] y = new double[N];
		for (int i = 0; i < N; i++) {
			x[i] = Math.random();
			y[i] = Math.random();
		}
		double count = 0;
		for (int i = 0; i < N; i++) {
			if (x[i] * x[i] + y[i] * y[i] < 1)
				count++;
		}
		double pi = 4 * count / N;
		return pi;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		double[] N = new double[6];
		double[] PI = new double[6];
		double[] err = new double[6]; 
		int n = 10;
		for (int i = 0; i < 6; i++) {
			N[i] = n;
			PI[i] = cal_pi(n);
			err[i] = Math.abs(PI[i] - Math.PI);
			System.out.println("N = " + N[i] + "  " + cal_pi(n) + "  Error is " + err[i]);
			n = n * 10;
		}
		
		System.out.println(Arrays.toString(err));
		System.out.println(Arrays.toString(N));
	}
}
