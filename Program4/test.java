/*
//  
//  Calculate second norm
//
//  Created by Xiaoixng Yan & Chun Chen on 03/07/2018.
 */


package project4;

public class test {


	public static double secondNorm(double[] calResult, double[] trueResult) {

		assert calResult.length == trueResult.length;

		double[] termResult = new double[trueResult.length];
		for(int i = 0;i<trueResult.length;i++) {
			termResult[i] = calResult[i] - trueResult[i];
		}
		double sum = 0.0;
		for(int i = 0;i<trueResult.length;i++) {
			sum = sum+ Math.pow(termResult[i],2);

		}
		sum = Math.pow(sum, 0.5);
		return sum;

	}
	//for Double
	public static Double secondNorm(Double[] calResult, Double[] trueResult) {

		assert calResult.length == trueResult.length:"these two vectors do not have same length";

		double[] termResult = new double[trueResult.length];
		for(int i = 0;i<trueResult.length;i++) {
			termResult[i] = calResult[i] - trueResult[i];
		}
		double sum = 0.0;

		for(int i = 0;i<trueResult.length;i++) {
			sum = sum+ Math.pow(termResult[i],2);

		}
		sum = Math.pow(sum, 0.5);
		return sum;

	}

	//for double-Double
	public static double secondNorm(double[] calResult, Double[] trueResult) {

		assert calResult.length == trueResult.length;

		double[] termResult = new double[trueResult.length];
		for(int i = 0;i<trueResult.length;i++) {
			termResult[i] = calResult[i] - trueResult[i];
		}
		double sum = 0.0;

		for(int i = 0;i<trueResult.length;i++) {
			sum = sum+ Math.pow(termResult[i],2);

		}
		sum = Math.pow(sum, 0.5);
		return sum;

	}
	//for Double
	public static Double secondNorm(Double[] calResult) {

		assert calResult != null;

		Double sum = 0.0;
		for(int i = 0;i<calResult.length;i++) {
			sum = sum+ Math.pow(calResult[i],2);

		}
		sum = Math.pow(sum, 0.5);
		return sum;

	}

	//for double
	public static double secondNorm(double[] calResult) {

		assert calResult != null;

		double sum = 0.0;
		for(int i = 0;i<calResult.length;i++) {
			sum = sum+ Math.pow(calResult[i],2);

		}
		sum = Math.pow(sum, 0.5);
		return sum;

	}



}

