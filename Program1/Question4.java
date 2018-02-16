package program1;

import java.math.BigDecimal;

public class Question4 {
	public static final int N = 1000000; // # of terms

	public static void main(String[] args) {
		//1. Calculate and report PI with 30 digits of precision
		System.out.println("*****Calculate PI with 30 digits*****");
		System.out.println("");
		BigDecimal sum = new BigDecimal(1);
		BigDecimal one = new BigDecimal(1);
		BigDecimal two = new BigDecimal(2);
		BigDecimal temp = new BigDecimal(-1);
		BigDecimal sign = new BigDecimal(1);

		for (int i = 1; i < N; i = i + 1) {
			// term = 1.0/(2.0*k + 1.0);
			BigDecimal temp1 = new BigDecimal(i);// i
			BigDecimal temp2 = temp.pow(i);// (-1) ^ i
			BigDecimal temp3 = two.multiply(temp1).add(one);// 2*i+1
			// round_floor: rounding mode to round towards negative infinity
			BigDecimal temp4 = one.divide(temp3, 30, BigDecimal.ROUND_FLOOR);// 1/(2*i+1)
			BigDecimal temp5 = temp4.multiply(temp2);
			sum = sum.add(temp5);// sum = sum + sign*term;
		}

		BigDecimal four = new BigDecimal(4);
		BigDecimal re = sum.multiply(four);
		System.out.println("Calculated pi (approx., " + N + " terms and 30 Decimal Places): " + re);
		System.out.println("Actual pi: " + Math.PI);
		// System.out.println(re);
		System.out.println("Conclusion: With N increasing, the calculated result will be closer to PI. ");

		/* not so accurate, obtain it by 355/113 */
//		BigDecimal a1 = new BigDecimal(355);
//		BigDecimal a2 = new BigDecimal(113);
//		BigDecimal result = a1.divide(a2, 30, BigDecimal.ROUND_FLOOR);
//		System.out.println(result);

		/* calculate the n-th digit of pi */
//		double a = 0;
//		double c = 0;
//		for (double k = 0; k <= 1; k++) {
//			a = a + 4 / (8 * k + 1) - 2 / (8 * k + 4) - 1 / (8 * k + 5) - 1 / (8 * k + 6);
//			double b = Math.pow(16, -k);
//			c = c + b * a;
//			double d = c * Math.pow(16, 2);
//			System.out.println(d);
//		}

	}

}
