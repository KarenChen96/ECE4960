/*Date:
Created on 5/16/2018

(1) Using PDE to solve 1D Heat Equation
Forward & Backward Euler  && Trapezoidal Euler
(2) Using PDE to solve 2D Heat Equation
Forward & Backward Euler  && Trapezoidal Euler

Authors: Xiaoxing Yan & Chun Chen
Platforms:Eclipse
MAC OS*/

package project5;

import java.util.Arrays;

public class Main {

	public static void main(String[] args) {
		
		//set parameters
		double[] period = new double[] { 0, 10, 1 }; //perform temperature distribution from t = 1 to t = 10
		double d_t = 1; //delta(t)
		double h = 1; //delta(x) or delta(y)
		double D = 0.2;

		System.out.println("delta(t) = " + d_t + ", delta(x)/delta(y) = " + h + ", Diffusivity = " + D + "\n");
		// Solve 1D parabolic heat equation
		System.out.println("--------------------Test for 1D heat equation.--------------------");
		double[] T_1 = {0,10,0}; //initial value
		System.out.println("Initial values are: " + Arrays.toString(T_1) + "\n");
		// Backward Euler
		PDEModel pm = new PDEModel();
		pm.setH(h);
		pm.setDeltT(d_t);
		pm.setD(D);
		pm.setPeriod(period);
		
		pm.setInit(T_1);
		pm.PDESolver(1,1);
		pm.setInit(T_1);
		pm.PDESolver(1,2);
		pm.setInit(T_1);
		//the first param is to choose dimension[ 1:1D &  2:2D]
		//the second param is to choose method in FDM [ 0: Forward & 1: Backward & 2:Trapezoidal]
		pm.PDESolver(1,3);
		
		System.out.println(" ");
		
		
		// Solve 2D parabolic heat equation
		System.out.println("--------------------Test for 2D heat equation.--------------------");
		double[] T_2 = {68,80,50,75,100,70,65,85,60};
		
		System.out.println("Initial values are: " + Arrays.toString(T_2) + "\n");
		PDEModel pm2 = new PDEModel();
		pm2.setH(h);
		pm2.setDeltT(d_t);
		pm2.setD(D);
		pm2.setPeriod(period);

		pm2.setInit(T_2);
		pm2.PDESolver(2,1);
		pm2.setInit(T_2);
		pm2.PDESolver(2,2);
		pm2.setInit(T_2);
		pm2.PDESolver(2,3);
		
	}

}
