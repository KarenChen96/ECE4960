/*dates: 3/23/2018
 * authors: Chun Chen
 * Language: Java
 * Platforms: Eclipse in Windows10.
 * */

package hp_lecture52;

import java.util.Arrays;

public class l52_hp4 {
	public static void change_p() {
		int N = 1000;
		double lamda = 0.2;
		double[] v = new double[N];
		double[] u = new double[N];
		double[] pv = new double[N];
		double[] Fv = new double[N];
		for (int i = 0; i < N; i++) {
			u[i] = Math.random();
			v[i] = v[i] = -Math.log(1 - u[i]) / lamda;
		}

		// sort v, calculate p(v) and F(v)
		Arrays.sort(v);
		for (int i = 0; i < N; i++) {
			if (v[i] >= 0) {
				pv[i] = lamda * Math.exp(-lamda * v[i]);
				Fv[i] = 1 - Math.exp(-lamda * v[i]);// obtain u for this v
			}
		}

		// visualization verification......plot bar figure
		// process data to obtain data for plotting
		int num = 20;
		double[] new_p = new double[num];
		double[] new_F = new double[num];
		for (int i = 0; i < new_p.length; i++) {
			for (int j = i * 50; j < (i + 1) * 50; j++) {
				new_p[i] = new_p[i] + pv[j];
				new_F[i] = new_F[i] + Fv[j];
			}
			new_p[i] = new_p[i] / 50;
			new_F[i] = new_F[i] / 50;
		}

		// another method for verification
		boolean flag = true;
		for (int i = 0; i < N - 1; i++) {
			if (v[i + 1] > v[i]) {
				if (pv[i + 1] > pv[i]) {
					flag = false;
				}
			}
		}
		
		for (int i = 0; i< 10; i++)
		{
			System.out.print("v[i] is: " +v[i] + " ");
			System.out.print("pv[i] is: " + pv[i] + " ");
			System.out.print("Fv[i] is: " + Fv[i] + " ");
			System.out.println("");
		}
		System.out.println("The result follows the new distribution: " + flag);
		//System.out.println(Arrays.toString(new_p));
		//System.out.println(Arrays.toString(new_F));
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		change_p();
	}
}
