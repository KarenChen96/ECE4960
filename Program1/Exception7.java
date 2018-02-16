package program1;

public class Exception7 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// Floating point gradual underflow
		System.out.println("*****Observe floating point gradual underflow with different methods*****");
		float a = Float.MIN_NORMAL;
		float a1 = a;
		// 2.1101 * Math.pow(10, -315);
		float b1 = a / 2;
		// 7.2345 * Math.pow(10, -321);
		System.out.println("----Observing floating gradual underflow by performing a1-b1 and a1/b1 with "
				+ "a starting with " + a1 + " and b starting with " + b1 + "----");
		while ((a1 - b1 != 0) && (a1 / b1 != 1)) {
			System.out.print("a1 is: " + a1 + "; ");
			System.out.println("b1 is: " + b1);
			System.out.println("a1-b1 is: " + (a1 - b1));
			System.out.println("a1/b1 is: " + (a1 / b1));
			System.out.println("");
			a1 = a1 / 10;
			b1 = b1 / 10;		
		}
		System.out.println("Conclusion: When a1 and b1 are less than the float normal minimum value,"
				+ " we can still obtain a1-b1 != 0 or a1/b1 != 1 (2a = b),"
				+ " which means that a1 != b1, showing that gradual underflow exists.");
		System.out.println("");

		float a2 = a;
		System.out.println("----Observing floating gradual underflow by performing "
				+ "sin(1.23456789012345 * a3)/a3 with " + "a3 starting with " + a2 + "----");
		for (int i = 0; i <= 15; i++) {
			System.out.print("When a3 is " + a2 + ", " + "the result of sin(1.23456789012345 * a3)/a3 is: ");
			System.out.println(Math.sin(1.23456789012345 * a2) / a2);
			a2 = a2 / 10;
		}		
		
		System.out.println("");	
		System.out.println("Conclusion: When a2 are less than the float normal minimum value,"
				+ " we can still calculate the result of the function until a2 > 1.4*E-45,"
				+ " which means that gradual underflow exists.");
		
		System.out.println("\r\n");
		System.out.println("");

	}

}
