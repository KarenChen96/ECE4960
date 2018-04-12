//Date:
//Created on 3/29/2018
//
//test for functions:
//(1) secondNorm(double[] calResult, double[] trueResult)
//(2) SLUSolver(double[][] fullM, double[] result)
//(3) transDtod    --change Double array to double array
//(4) checkQuaConvergence
//(5) f1,f2,f3
//(6) f1xDri,f2xDri,f3xDri
//(7) calculate    --Id model
//(8) calculate    --loss function V
//
//Authors: Xiaoxing Yan
//Platforms:Eclipse
//MAC OS

package project3;

import java.util.ArrayList;
import java.util.Arrays;

import HP5Mtrix.test;
import homework4Matrix.rowFunction;

public class testClassP3 {

	public static void main(String[] Args) {
		//(1) for method:
		//public static double secondNorm(double[] calResult, double[] trueResult) {}
		//create two vectors and in this case we have already known the ground truth
		double[] calVector = new double[] {0.0,1.0,2.0,3.0};
		double[] trueVector = new double[] {3.0,4.0,5.0,6.0};
		System.out.println("1");
		//when they have the same length
		double[] trueResult  = new double[4];
		for(int i = 0;i<trueVector.length;i++) {
			trueResult[i] = calVector[i] - trueVector[i];
		}
		double sum = 0.0;
		for(int i = 0;i<trueVector.length;i++) {
			sum = sum+ Math.pow(trueResult[i],2);
		}
		double result1 = Math.pow(sum, 0.5);
		double result2 = test.secondNorm(calVector,trueVector);
		if(result1==result2) {
			System.out.println("the test for second norm is passed");
		}

		System.out.println();


		//(2) for method
		//public static double[] SLUSolver(double[][] fullM, double[] result) {}
		//Using backward substitution and calculate the norm between A*X and B	
		
		double[][] fullM = {
				{1,2,0,0,1},
				{1,3,0,0,1},
				{0,7,8,0,9},
				{0,0,0,10,0},
				{11,0,0,0,12}
		};

		double[][] fullM1 = {
				{1,2,0,0,1},
				{1,3,0,0,1},
				{0,7,8,0,9},
				{0,0,0,10,0},
				{11,0,0,0,12}
		};

		double[] result = new double[] {5,4,3,2,1};
		double[] valueX = new double[result.length];
		valueX = matrixSolver.SLUSolver(fullM, result);
		System.out.println("2");
		System.out.println("the value of X is :"+Arrays.toString(valueX));
		double[] backResult = rowFunction.fullmatrixProduct(fullM1, valueX);
		double[] resultC = new double[] {5,4,3,2,1};
		System.out.println("the second norm is "+ test.secondNorm(backResult, resultC) );
		if(test.secondNorm(backResult, resultC)<Math.pow(10, -7)) {
			System.out.println("the test for matrix solver is passed");
		}

		//(3) for method
		//transDtod(Double[] array1) {}
		double[] Vgs = new double[3];
		double[] trueR = new double[] {1,1,1};
		Double[] Vgs_temp = new Double[] {Double.valueOf(1),Double.valueOf(1),Double.valueOf(1)};
		Vgs=EKVModel.transDtod(Vgs_temp);
		System.out.println();
		System.out.println("3");
		System.out.println("the second norm is "+ test.secondNorm(Vgs,trueR));
		if(test.secondNorm(Vgs,trueR)<Math.pow(10, -7)) {
			System.out.println("the test for transDtod is passed");
		}


		//(4) for method
		//automated check of the quadratic convergence
		//public static void checkQuaConvergence(ArrayList<Double> normV, ArrayList<Double> normDelt, double trueV, double trueDelt) 


		ArrayList<Double> normV = new ArrayList<Double>();
		ArrayList<Double> normDelt = new ArrayList<Double>();
		normV.add(Math.pow(10, -2));
		normV.add(Math.pow(10, -4));
		normV.add(Math.pow(10, -8));

		normDelt.add(Math.pow(10, -2));
		normDelt.add(Math.pow(10, -4));
		normDelt.add(Math.pow(10, -8));
		System.out.println();
		System.out.println("4");
		EKVModel.checkQuaConvergence( normV, normDelt, 0, 0);

		//(5) for method f1, f2, f3
		//public static double f1(double[] Vgs, double[] Vds, double[] Ids, double k, double Vt, double Is, double Vth) 
		//public static double f2(double[] Vgs, double[] Vds, double[] Ids, double k, double Vt, double Is, double Vth) {
		//public static double f3(double[] Vgs, double[] Vds, double[] Ids, double k, double Vt, double Is, double Vth) {
		//known ground truth f1=f2=f3=0.43997

		double[] vgs = {3};
		double[] vds = {1};
		double[] ids = {1};
		double Vt =1;
		double k=1;
		double Is = 1;
		double Vth = 0;
		double f1 = EKVModel.f2( vgs, vds,  ids,  k,  Vt,  Is,  Vth);
		double f2 = EKVModel.f2( vgs, vds,  ids,  k,  Vt,  Is,  Vth);
		double f3 = EKVModel.f2( vgs, vds,  ids,  k,  Vt,  Is,  Vth);
		System.out.println();
		System.out.println("5");
		System.out.println("the norm for f1 is "+ Math.abs(f1-0.43997));
		System.out.println("the norm for f2 is "+ Math.abs(f2-0.43997));
		System.out.println("the norm for f3 is "+ Math.abs(f3-0.43997));

		//(6) for method
		//public static double f1xDri(int choose,double perturbation, double[] Vgs, double[] Vds, double[] Ids, double k, double Vt, double Is, double Vth) 
		//public static double f2xDri(int choose,double perturbation, double[] Vgs, double[] Vds, double[] Ids, double k, double Vt, double Is, double Vth) {
		//public static double f3xDri(int choose,double perturbation, double[] Vgs, double[] Vds, double[] Ids, double k, double Vt, double Is, double Vth) {
		//known ground truth
		//f1 = 2.739
		//f2 = 3.466
		//f3 = -1.155
		double perturbation =Math.pow(10, -4);
		f1 = EKVModel.f1xDri(1,perturbation, vgs,  vds,  ids,  k,  Vt,  Is, Vth);	
		f2 = EKVModel.f2xDri(1,perturbation, vgs,  vds,  ids,  k,  Vt,  Is, Vth);	
		f3 = EKVModel.f3xDri(1,perturbation, vgs,  vds,  ids,  k,  Vt,  Is, Vth);	
		System.out.println();
		System.out.println("6");
		System.out.println("the norm for the partial derivative of f1 with respect to Is is "+ Math.abs(f1-2.739));
		System.out.println("the norm for the partial derivative of f2 with respect to Is is "+ Math.abs(f2-3.466));
		System.out.println("the norm for the partial derivative of f3 with respect to Is is "+ Math.abs(f3+1.155));

		//(7) for method
		//public static double Id(double Vgs, double Vds, double k, double Vt, double Is, double Vth) 
		//known ground truth Id = 0.0


		double Id = EKVModel.Id(0.5, 0, k, Vt, Is, Vth);
		System.out.println();
		System.out.println("7");
		System.out.println("the norm of Id is "+Math.abs(Id-0.0));


		//(8) for method
		//public static double V(int choose, double[] Vgs, double[] Vds, double[] Ids, double k, double Vt, double Is, double Vth) 
		//known ground truth V = 0.0289

		double V = EKVModel.V(1,vgs, vds,ids, k, Vt, Is, Vth);
		System.out.println();
		System.out.println("8");
		System.out.println("the norm of V is "+Math.abs(V-0.0289));
	}
}
