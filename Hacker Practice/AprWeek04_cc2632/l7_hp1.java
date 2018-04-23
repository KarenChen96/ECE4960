/*dates: 4/21/2018
 * authors: Chun Chen
 * Language: Java
 * Platforms: Eclipse in Windows10.
 * */

package hp_lecture7;

import java.util.Arrays;

import hp_lecture4.formula;
import hp_lecture4.lecture4_practice4;


public class l7_hp1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Discretized Poisson equation
		double[][] M1 = {{-2,1,0,0}, {1,-2,1,0}, {0,1,-2,1}, {0,0,1,-2}};
		double[] b1 = { 1,0,0,0};
		for (int i = 0; i<b1.length; i++) {
			b1[i] = b1[i] * 0.2 * 0.2;
		}
		int[] solution1 = new int[M1.length];

		double[] solu1 = lecture4_practice4.solution(M1, solution1, b1);
		System.out.println(Arrays.toString(solu1));
		
		double[][] M2 = {{-2,1,0,0}, {1,-2,1,0}, {0,1,-2,1}, {0,0,1,-3}};
		double[] b2 = { 1,0,0,0};
		for (int i = 0; i<b2.length; i++) {
			b2[i] = b2[i] * 0.2 * 0.2;
		}
		int[] solution2 = new int[M2.length];

		double[] solu2 = lecture4_practice4.solution(M2, solution2, b2);
		System.out.println(Arrays.toString(solu2));
	}

}
