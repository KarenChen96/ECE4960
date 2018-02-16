package program1;

public class Exception3 {
	// Floating-point overflows.

//	// Method1: factorials
//	public static float excep31(float x1) {
//		float y1 = 1;
//		for (int i = 1; i <= x1; i++) {
//			y1 = y1 * i;
//		}
//		return y1;
//	}
	
	// Method1: multiplications of 10
		public static float excep31(float x1) {
			float y1 = 1;
			for (int i = 1; i <= x1; i++) {
				y1 = y1 * 10;
			}
			return y1;
		}

	// Method2: multiplications of 2
	public static float excep32(float x2) {
		float y2 = 1;
		for (int i = 1; i <= x2; i++) {
			y2 = y2 * 2;
		}
		return y2;
	}

	public static boolean isOverflow1(float x, float n) {
		if (x > Float.MAX_VALUE)
			return true;
		else
			return false;
	}

	public static boolean isOverflow2(float x, float n) {
		if (x > Float.MAX_VALUE)
			return true;
		else
			return false;
	}


	public static void main(String[] args) {
		System.out.println("*****Observe Floating-point overflows: *****");
//		System.out.println("----Use factorials to cause Floating-point overflow----");
//		for (float i = 10; i <= 30; i++) {
//			float a = Exception3.excep31(i);
//			System.out.println("The result of " + i + "! is: " + Exception3.excep31(i));
//			if (isOverflow(a, i)) {
//				System.out.println(i + "! is overflow ");
////				System.out.println("pow(2, " + i + ") is overflow ");
//			}
//		}
		
		System.out.println("----Use multiplication of 10 to cause Floating-point overflow----");
		for (float i = 20; i <= 40; i++) {
			float a = Exception3.excep31(i);
			System.out.println("The result of pow(10, " + i + ") is: " + a);
			if (isOverflow1(a, i)) {
				System.out.println("pow(10, " + i + ") is overflow ");
				System.out.println(" ");
				System.out.println(
						"Conclusion: The max multiplication (of 10) can be correctly calculated in Float is pow(10," + i +").");
				System.out.println("The calculated numbers won't become 0 when become larger than the maximum, "
				+ "instead it will always be infinity.");
				System.out.println(" ");
				break;
			}
		}		
		System.out.println(" ");		

		System.out.println("----Use multiplication of 2 to cause Floating-point overflow----");
		for (float i = 120; i <= 140; i++) {
			float a = Exception3.excep32(i);
			System.out.println("The result of pow(2, " + i + ") is: " + a);
			if (isOverflow2(a, i)) {
				System.out.println("pow(2, " + i + ") is overflow ");
				System.out.println(" ");
				System.out.println(
						"Conclusion: The max multiplication (of 2) can be correctly calculated in Float is pow(2," + i +").");
				System.out.println("The calculated numbers won't become 0 when become larger than the maximum, "
				+ "instead it will always be infinity.");
				break;
			}
		}		
			
		System.out.println("\r\n");
		System.out.println("");
	}

}
