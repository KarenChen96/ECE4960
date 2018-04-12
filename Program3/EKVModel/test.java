/*
//  
//  test method for matrix
//
//  Created by Xiaoixng Yan on 03/07/2018.
//  Copyright © 2018 Xiaoxing Yan. All rights reserved.
*/


package project3;
import java.util.Arrays;

import homework4Matrix.rowFunction;

public class test {
	//test if the X is the right answer
	//second term
	
	public static double secondNorm(double[] calResult, double[] trueResult) {
		
		assert calResult.length == trueResult.length;
		
		double[] termResult = new double[trueResult.length];
		for(int i = 0;i<trueResult.length;i++) {
			termResult[i] = calResult[i] - trueResult[i];
		}
		double sum = 0.0;
		//System.out.println(Arrays.toString(termResult));
		for(int i = 0;i<trueResult.length;i++) {
			sum = sum+ Math.pow(termResult[i],2);
			//System.out.println(sum);
		}
		sum = Math.pow(sum, 0.5);
		return sum;
		//System.out.println("Test, the second norm of calculate X is "+ sum);
		//System.out.println();
	}
	//for Double
	public static Double secondNorm(Double[] calResult, Double[] trueResult) {
		
		assert calResult.length == trueResult.length:"these two vectors do not have same length";
		
		double[] termResult = new double[trueResult.length];
		for(int i = 0;i<trueResult.length;i++) {
			termResult[i] = calResult[i] - trueResult[i];
		}
		double sum = 0.0;
		//System.out.println(Arrays.toString(termResult));
		for(int i = 0;i<trueResult.length;i++) {
			sum = sum+ Math.pow(termResult[i],2);
			//System.out.println(sum);
		}
		sum = Math.pow(sum, 0.5);
		return sum;
		//System.out.println("Test, the second norm of calculate X is "+ sum);
		//System.out.println();
	}
	
	//for double-Double
     public static double secondNorm(double[] calResult, Double[] trueResult) {
		
		assert calResult.length == trueResult.length;
		
		double[] termResult = new double[trueResult.length];
		for(int i = 0;i<trueResult.length;i++) {
			termResult[i] = calResult[i] - trueResult[i];
		}
		double sum = 0.0;
		//System.out.println(Arrays.toString(termResult));
		for(int i = 0;i<trueResult.length;i++) {
			sum = sum+ Math.pow(termResult[i],2);
			//System.out.println(sum);
		}
		sum = Math.pow(sum, 0.5);
		return sum;
		//System.out.println("Test, the second norm of calculate X is "+ sum);
		//System.out.println();
	}
	//for Double
	public static Double secondNorm(Double[] calResult) {
		
		assert calResult != null;
		
		Double sum = 0.0;
		for(int i = 0;i<calResult.length;i++) {
			sum = sum+ Math.pow(calResult[i],2);
			//System.out.println(sum);
		}
		sum = Math.pow(sum, 0.5);
		return sum;
		//System.out.println("Test, the second norm of calculate X is "+ sum);
		//System.out.println();
	}
	
	//for double
public static double secondNorm(double[] calResult) {
		
		assert calResult != null;
		
		double sum = 0.0;
		for(int i = 0;i<calResult.length;i++) {
			sum = sum+ Math.pow(calResult[i],2);
			//System.out.println(sum);
		}
		sum = Math.pow(sum, 0.5);
		return sum;
		//System.out.println("Test, the second norm of calculate X is "+ sum);
		//System.out.println();
	}
	
	

}

