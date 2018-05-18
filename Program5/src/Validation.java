/*Date:
Created on 5/17/2018

Validation for our PDESolver (in 1D):
(1) Obtain result  by Backward Euler
(2) Obtain result  by Trapezoidal Euler
(3) Obtain difference of these two methods

Authors: Chun Chen
Platforms:Eclipse Windows10
*/


package project5;

import java.util.Arrays;

public class Validation {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//set parameters
		double[] period = new double[] { 0, 10, 1 }; // perform temperature distribution from t = 1 to t = 10
		double d_t = 1; // delta(t)
		double h = 1; // delta(x) or delta(y)
		double D = 0.05;
		// ********Solve 1D parabolic heat equation********

		// Validate the strategy by comparing results calculated by two methods
		PDEModel pm = new PDEModel();
		pm.setH(1.0);
		pm.setDeltT(1.0);
		pm.setD(0.05);
		pm.setPeriod(new double[] { 0, 10, 1 });
		pm.setInit(new double[] { 0, 10, 0 });
		double[][] T1 = pm.PDESolver(1,2);// Backward Euler

		pm.setH(1.0);
		pm.setDeltT(1.0);
		pm.setD(0.1);
		pm.setPeriod(new double[] { 0, 10, 1 });
		pm.setInit(new double[] { 0, 10, 0 });
		double[][] T2 = pm.PDESolver(1,3);// Trapezoidal

		double start_time = pm.period[0];
		double end_time = pm.period[1];
		int cycles = (int) ((int) (end_time - start_time) / pm.d_t);// number of cycles

		System.out.println(" ");
		System.out.println("-------------------PDESolver Validation-------------------");
		System.out.println("Compare the results obtainde by Backward Euler and Trapezoidal Euler in 1D.");
		double[][] difference = new double[cycles][pm.init_val.length];

		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 3; j++) {
				difference[i][j] = Math.abs((T1[i][j] - T2[i][j]) / T1[i][j]);
			}
			System.out.println(" ");
			System.out.println("when t = " + (i + 1) + ", relative difference is " + Arrays.toString(difference[i]));
		}

	}

}
