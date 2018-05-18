/*Date:
Created on 5/17/2018

 PDE Model
(1) PDE Solver  -- 1D & 2D PDE
(2) NewT  --  obtain new temperature
(3) ParameterMatrix_1D  --  Using Forward/Backward/Trapezoidal Euler to do the first order time discretization
(4) ParameterMatrix_2D  --  Using Forward/Backward/Trapezoidal Euler to do the first order time discretization

Authors: Xiaoxing Yan & Chun Chen
Platforms:Eclipse
MAC OS*/

package project5;
import java.util.Arrays;

public class PDEModel {
	
	// Global variables that can be used in the whole program and can be changed in the main.java
	public double[] period;
	public double d_t;
	public double h;
	public double D;
	public double[] init_val;

	// constructor
	public PDEModel() {
	}

	public void setPeriod(double[] period) {
		this.period = period;
	}

	// delta(t) < 0.5*(dx^2)/2*k
	public void setDeltT(double delt_t) {
		this.d_t = delt_t;
	}

	// delta(x)
	public void setH(double h) {
		this.h = h;
	}

	// diffusion
	public void setD(double D) {
		this.D = D;
	}

	public void setInit(double[] n) {
		this.init_val = n;
	}

	
	/**
	 * PDESolver: Receive choice of dimension and time discretization methods
	 *            to obtain the new temperature distribution
	 * @param dimension
	 * @param choice
	 * @return (for validation) 
	 * new Temperature distribution T_new = 
	 * t = 1 [temp_x1, temp_x2, ..., temp_xn]
	 * t = 2 [temp_x1, temp_x2, ..., temp_xn]
	 * ......
	 * t = m [temp_x1, temp_x2, ..., temp_xn]
	 */
	public double[][] PDESolver(int dimension, int choice) {
		int rank = this.init_val.length;
		double[][][] param = new double[2][rank][rank];
		
		if (dimension == 1) {
			System.out.println("**********Temperature distribution in one space dimension**********");
			System.out.println("[t11, t12, ... t1n]");
			param = ParameterMatrix_1D(choice);
		} else if (dimension == 2) {
			System.out.println("**********Temperature distribution in two space dimension**********");
			System.out.println("[t11, t12, ... t1n ...... tn1, tn2, ... tnn]");
			param = ParameterMatrix_2D(choice);
		}

		double[][] A = param[0];
		double[][] B = param[1];
		
		// Perform temperature distribution in different locations
		double[][] T = NewT(A, B, choice);
		return T;
	}
		
	
	/**NewT: Obtain new Temperature Distribution by calculating T_new
	 * Backward/Trapezoidal: A * T_new = B * T_old
	 * Forward: B * T_new = A * T_old
	 * Different methods can be chose through choice
	 * @param choice ( 0: Forward 1: Backward 2:Trapezoidal)
	 * @return new Temperature distribution T_new = 
	 * t = 1 [temp_x1, temp_x2, ..., temp_xn]
	 * t = 2 [temp_x1, temp_x2, ..., temp_xn]
	 * ......
	 * t = m [temp_x1, temp_x2, ..., temp_xn]
	 */
	public double[][] NewT(double[][] A, double[][] B, int choice) {
		double start_time = this.period[0];
		double end_time = this.period[1];
		int cycles = (int) ((int) (end_time - start_time) / d_t);// number of cycles
		double[][] T = new double[cycles][init_val.length];

		
		for (int i = 1; i <= cycles; i = i + 1) {
			double[] newT = new double[init_val.length]; 
			//forward
			if(choice == 1) {
				// obtain T_new: B * T_new = A*T_old	
				double[] result = rowFunction.fullmatrixProduct(A, init_val);
				newT = matrixFunction.SLUSolver(B, result);
			}
			else {
				// obtain T_new: A*T_new = B * T_old
				double[] result = rowFunction.fullmatrixProduct(B, init_val);
				newT = matrixFunction.jacobiSolver(A, result, 1.0E-5, init_val);
				// double[] newN = matrixFunction.SLUSolver(A, this.init_val);

			}		
			

			T[i - 1] = newT;
			//System.out.println("when t = " + i + ", current Tempreture in different locations are " + Arrays.toString(newT));
			this.init_val = newT;	
		}

		
		return T;
	}
	

	/**ParameterMatrix_1D:
	 * Using Forward/Backward/Trapezoidal Euler to do the first order time discretization
	 * @param choice
	 * @return Parameters matrix A and B
	 * A and B are for obtaining T_new: 
	 * 		B * T_new = A*T_old	(Forward) 
	 * 		A*T_new = B * T_old (Backward or Trapezoidal)
	 */
	public double[][][] ParameterMatrix_1D(int choice){
		int rank = this.init_val.length;
		
		double A_item1 = 0; double A_item2 = 0;
		double B_item1 = 0; double B_item2 = 0;
		
		switch(choice) {
		//Forward
		case 1:
			System.out.println("**********Forward Euler**********");
			A_item1 = 1 / d_t - 2 * D / (h * h); A_item2 =  D / (h * h);
			B_item1 = 1 / d_t; B_item2 = 0;		
			break;
		
		//Backward
		case 2:
			System.out.println("**********Backward Euler**********");
			A_item1 = 1 / d_t + 2 * D / (h * h); A_item2 = -D / (h * h);
			B_item1 = 1 / d_t; B_item2 = 0;		
			break;
		//Trapezoidal
		case 3:
			System.out.println("**********Trapezoidal Euler**********");
			A_item1 = 1 / d_t + D / (h * h); A_item2 = -D / (2 * h * h);
			B_item1 = 1 / d_t - D / (h * h); B_item2 = -A_item2;
		}
		
		// Construct the parameter matrix of T_new and T_old
		double[][] A = new double[rank][rank];
		double[][] B = new double[rank][rank];
		A[0][0] = A_item1; A[0][1] = A_item2; // first rows of A
		B[0][0] = B_item1; B[0][1] = B_item2; // first rows of B

		// middle rows of A and B
		for (int i = 1; i < rank - 1; i++) {
			A[i][i - 1] = A_item2; A[i][i] = A_item1; A[i][i + 1] = A_item2;
			B[i][i - 1] = B_item2; B[i][i] = B_item1; B[i][i + 1] = B_item2;
		}
		
		A[rank - 1][rank - 2] = A_item2; A[rank - 1][rank - 1] = A_item1; // last row of A
		B[rank - 1][rank - 2] = B_item2; B[rank - 1][rank - 1] = B_item1; // last row of B
	
		//return the combination of two parameter matrixes
		double[][][] param = {A,B};
		return param;
	}
	
	
	
	/**ParameterMatrix_2D:
	 * Using Forward/Backward/Trapezoidal Euler to do the first order time discretization
	 * @param choice
	 * @return parameters matrix A and B
	 * A and B are for obtaining T_new: 
	 * 		B * T_new = A*T_old	(Forward) 
	 * 		A*T_new = B * T_old (Backward or Trapezoidal)
	 */
	public double[][][] ParameterMatrix_2D(int choice){
		int rank = this.init_val.length;
		
		double A_item1 = 0; double A_item2 = 0;
		double B_item1 = 0; double B_item2 = 0;
		
		switch(choice) {
		//Forward
		case 1:
			System.out.println("**********Forward Euler**********");
			A_item1 = (-4*D)/Math.pow(h, 2)+ 1.0/d_t; A_item2 = D/Math.pow(h, 2);
			B_item1 = 1 / d_t; B_item2 = 0;	
			break;
		//Backward
		case 2:
			System.out.println("**********Backward Euler**********");
			A_item1 = 1/d_t + 4 * D / (h * h); A_item2 = -D / (h * h);
			B_item1 = 1 / d_t; B_item2 = 0;	
			break;
		//Trapezoidal
		case 3:
		    System.out.println("**********Trapezoidal Euler**********");
			A_item1 = 1 / d_t + 2 * D / (h * h); A_item2 = -D / (2 * h * h);
			B_item1 = 1 / d_t - 2 * D / (h * h); B_item2 = D / (2 * h * h);	
			break;
		}
		
		// Construct the parameter matrix of T_new and T_old
		double[][] A = new double[rank][rank];
		double[][] B = new double[rank][rank];

		// construct matrix A
		for (int i = 0; i < rank; i++) {
			A[i][i] = A_item1;
			B[i][i] = B_item1;
			
			// sqrt(rank) = 3
			if (i + 3 <= rank-1) {
				A[i][i + 3] = A_item2;
				B[i][i + 3] = B_item2;
			}
			if (i - 3 >= 0) {
				A[i][i - 3] = A_item2;
				B[i][i - 3] = B_item2;
			}
			if (i == 1 || i == 4 || i == 7) {
				A[i - 1][i] = A[i + 1][i] = A[i][i - 1] = A[i][i + 1] = A_item2;
				B[i - 1][i] = B[i + 1][i] = B[i][i - 1] = B[i][i + 1] = B_item2;
			}
		}
		
		//return the combination of two parameter matrixes
		double[][][] param = {A,B};
		return param;
	}

}
