/*Date:
Created on 5/17/2018

test for functions:
(1) Validation and Testing for matrix solver -- Jacob matrix solver
(2)ParameterMatrix_1D
(3)ParameterMatrix_2D

Authors: Xiaoxing Yan
Platforms:Eclipse
MAC OS
*/


package project5;

import java.util.Arrays;
import project3.matrixSolver;

public class TestClass {
	public static void main(String [] Args) {

		//Testing helper functions
		//(1)Validation of matrix solver
		//By using ill-conditioned matrix
		System.out.println("Validation for jacob matrix solver");
		double[] delt = new double[20];
		delt[0]=0.1;
		for(int i=1;i<20;i++) {
			delt[i] = delt[i-1]/10.0;
		}

		for(int i=0;i<delt.length;i++) {


			double[][] fullM = {
					{3,2,0,0,1},
					{1,2+delt[i],0,0,1},
					{0,7,9,0,7},
					{0,0,0,10,0},
					{11,0,0,0,12}
			};


			double[] result = new double[] {5,4,3,2,1};
			double[] valueX = new double[result.length];
			valueX = matrixFunction.jacobiSolver(fullM, result, 1.0E-5, new double[] {0,0,0,0,0});
			System.out.println("the value of X is :"+Arrays.toString(valueX));
	


		}

		//(2)testing for matrix solver
		System.out.println();
		System.out.println("this is testing");
		//Using backward substitution and calculate the norm between A*X and B	

		double[][] fullM = {
				{3,2,0,0,1},
				{1,3,0,0,1},
				{0,7,10,0,9},
				{0,0,0,10,0},
				{11,0,0,0,12}
		};

		double[] result = new double[] {5,4,3,2,1};
		double[] valueX = new double[result.length];
		valueX = matrixFunction.jacobiSolver(fullM, result, 1.0E-5, new double[] {0,0,0,0,0});
		System.out.println("the value of X is :"+Arrays.toString(valueX));
		double[] backResult = rowFunction.fullmatrixProduct(fullM, valueX);
		System.out.println("the second norm is "+ test.secondNorm(backResult, result) );
		if(test.secondNorm(backResult, result)<Math.pow(10, -2)) {
			System.out.println("the test for matrix solver is passed");
		}
		
		//(3)testing for ParameterMatrix_1D
		System.out.println();
		System.out.println();
		PDEModel test = new PDEModel();
		test.setH(1.0);
		test.setDeltT(1.0);
		test.setD(0.05);
		test.setInit(new double[] {0,10,0});
		rowFunction.printFullMatrix(test.ParameterMatrix_1D(2)[1]);
		
		
			
		//(4)testing for ParameterMatrix_2D
		System.out.println();
		System.out.println();
		test.setInit(new double[] {10,10,10,10,10,10});
		rowFunction.printFullMatrix(test.ParameterMatrix_2D(2)[1]);
		
	}
}
