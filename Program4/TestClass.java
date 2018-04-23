//Date:
//Created on 4/20/2018
//
//test for functions:
//(1) ODESolver -- using different method
//(2) secondNorm(double[] calResult, double[] trueResult)
//(3) current source it(time)
//
//Authors: Xiaoxing Yan
//Platforms:Eclipse
//MAC OS
package project4;

import java.util.Arrays;

import HP5Mtrix.test;

public class TestClass {
	public static void main(String [] Args) {

		//Task 3
		//validation of ODE Solver 
		//Forward Euler
		
		double[] x= new double[] {2.0};
		double t=0;
		double h= 1;
		double eR =Math.pow(10, -4);
		double eA =Math.pow(10, -7);
		double[] xTrue = new double[9];
		for(int i =0;i<9;i++) {
			t=t+h;
			xTrue[i] = ODEModel.groundTruth(t);
		}

		//RK34 with time adaption 
		t=0;
		double[] calculateX =new double[1];
		double oldh = h;
		int i =0;
		System.out.println("Testing using Forward Euler");
		System.out.println("t is 0,  The calculted x is 2,  The relative error is 0" );
		while(t<9) {
			//choose 1 : one step method;  choose 2: forward euler;  choose 3: RK34; choose 4: RK4
			calculateX= ODEModel.ODESolver(3,x,t,h,eR,eA);
			t=t+oldh;
			System.out.println("t is "+t+",  The calculted x is "+Arrays.toString(calculateX)+ ",  The relative error is " + Math.abs(calculateX[0]-xTrue[i])/xTrue[i]);
			x=calculateX;
			h = oldh;
			i++;
		}
		
		//RK34 without time adaption
		i=0;
		t=0;
		System.out.println("Testing using RK34 with time adaptation");
		System.out.println("t is 0,  The calculted x is 2,  The relative error is 0" );
		while(t<9) {
			//choose 1 : one step method;  choose 2: forward euler;  choose 3: RK34; choose 4: RK4
			calculateX= ODEModel.ODESolver(3,x,t,h,eR,eA);
			t=t+oldh;
			System.out.println("t is "+t+",  The calculted x is "+Arrays.toString(calculateX)+ ",  The relative error is " + Math.abs(calculateX[0]-xTrue[i])/xTrue[i]);
			x=calculateX;
			h = oldh;
			i++;
		}
		
		i=0;
		t=0;
		System.out.println("Testing using RK34 without time adaptation");
		System.out.println("t is 0,  The calculted x is 2,  The relative error is 0" );
		while(t<9) {
			//choose 1 : one step method;  choose 2: forward euler;  choose 3: RK34; choose 4: RK4
			calculateX= ODEModel.ODESolver(4,x,t,h,eR,eA);
			t=t+oldh;
			System.out.println("t is "+t+",  The calculted x is "+Arrays.toString(calculateX)+ ",  The relative error is " + Math.abs(calculateX[0]-xTrue[i])/xTrue[i]);
			x=calculateX;
			h = oldh;
			i++;
		}
		
		
		
		//Testing helper functions
		//(1)
		//public static double secondNorm(double[] calResult, double[] trueResult) {}
		//create two vectors and in this case we have already known the ground truth
		double[] calVector = new double[] {0.0,1.0,2.0,3.0};
		double[] trueVector = new double[] {3.0,4.0,5.0,6.0};
		System.out.println();
		System.out.println("Testing for second norm:");
		//when they have the same length
		double[] trueResult  = new double[4];
		for( i = 0;i<trueVector.length;i++) {
			trueResult[i] = calVector[i] - trueVector[i];
		}
		double sum = 0.0;
		for(i = 0;i<trueVector.length;i++) {
			sum = sum+ Math.pow(trueResult[i],2);
		}
		double result1 = Math.pow(sum, 0.5);
		double result2 = test.secondNorm(calVector,trueVector);
		if(result1==result2) {
			System.out.println("the test for second norm is passed");
		}

		System.out.println();
		
		
	    //(2)
		//public static double it(double t) 
		//knowing the ground truth and calculate error
		double[] current = new double[] {5.0E-5, 1.0E-4, 1.0E-4, 1.0E-4, 1.0E-4, 1.0E-4, 1.0E-4, 1.0E-4, 1.0E-4, 1.0E-4, 0.0, 0.0};
		double[] currentC = new double[12];
		double[]  time = new double[] {0.5,2,3,4,5,6,7,8,9,10,11,12};
		for(i=0;i<12;i++) {
			currentC[i] = ODEModel.it(time[i]);
			
		}
		System.out.println(Arrays.toString(currentC));
		result2 = test.secondNorm(currentC,current);
		System.out.println("the error is "+ result2);
		

	}
}
