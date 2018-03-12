package hacker_practice;

public class formula {
	/*
	 * Calculate the result of multiplying two vectors can be used to test the
	 * solution's correctness by calculating the result of Ax
	 */
	public static double[] vecMul(double[][] m, double[] x) {
		double[] result = new double[x.length];
		for (int i = 0; i < x.length; i++) {
			double temp = 0;
			for (int j = 0; j < x.length; j++) {
				temp = temp + m[i][j] * x[j];
			}
			result[i] = temp;
		}
		return result;
	}

	/*
	 * Calculate the difference between the result obtained by Ax and the real value
	 * of b
	 */
	public static double diff(double[] result, double[] b) {
		double diff = 0;
		for (int i = 0; i < result.length; i++) {
			diff = diff + Math.pow(result[i] - b[i], 2);
		}
		return Math.sqrt(diff);
	}

	/* Calculate second norm of a vector (one dimension) */
	public static double norm2(double[] x) {
		double sum = 0;
		for (int i = 0; i < x.length; i++) {
			sum = sum + Math.pow(x[i], 2);
		}
		return Math.sqrt(sum);
	}

}
