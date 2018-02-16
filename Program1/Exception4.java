package program1;

public class Exception4 {
	// Floating operations of INF and NINF
	public static boolean isINF(double x) {
		if (x == 1 / 0.0) {
			return true;
		} else
			return true;
	}

	public static boolean isNINF(double x) {
		if (x == 1 / (-0.0)) {
			return true;
		} else
			return true;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		System.out.println("*****Observe floating-point operations of INF and NINF*****");
		double x1 = 1.0 / +0.0; // INF
		double x2 = 1 / -0.0; // NINF

		System.out.println("----Observe INF behaviors in following functions----");
		System.out.println("The result of 1/INF is: " + 1 / x1);
		System.out.println("The result of sin(INF) is: " + Math.sin(x1));
		System.out.println("The result of exp(INF) is: " + Math.pow(Math.E, x1));
		System.out.println("");
		System.out.println("----Observe NINF behaviors in following functions----");
		System.out.println("The result of 1/NINF is: " + 1 / x2);
		System.out.println("The result of sin(NINF) is: " + Math.sin(x2));
		System.out.println("The result of exp(NINF) is: " + Math.pow(Math.E, x2));
		System.out.println("");

		// propagation
		// INF
		System.out.println("----Observe INF's propagation----");
		System.out.println("log(sin(INF) is: " + Math.log(Math.sin(x1)));
		System.out.println("Log(exp(INF) is: " + Math.log(Math.exp(x1)));
		System.out.println("");

		// NINF
		System.out.println("----Observe NINF's propagation----");
		System.out.println("log(sin(NINF) is: " + Math.log(Math.sin(x2)));
		System.out.println("Log(exp(NINF) is: " + Math.log(Math.exp(x2)));
		System.out.println("");

		// interaction
		double x3 = 2 / 0.0;
		double x4 = -2 / 0.0;
		System.out.println("----Observe INF's interaction with INF----");
		System.out.println("INF + INF is£º " + (x1 + x3));
		System.out.println("INF - INF is£º " + (x1 - x3));
		System.out.println("INF * INF is£º " + (x1 * x3));
		System.out.println("INF / INF is£º " + (x1 / x3));
		System.out.println("");

		System.out.println("----Observe NINF's interaction with NINF----");
		System.out.println("NINF + NINF is£º " + (x2 + x4));
		System.out.println("NINF - NINF is£º " + (x2 - x4));
		System.out.println("NINF * NINF is£º " + (x2 * x4));
		System.out.println("NINF / NINF is£º " + (x2 / x4));
		System.out.println("");

		System.out.println("----Observe INF's interaction with NINF----");
		System.out.println("INF + NINF is£º " + (x1 + x2));
		System.out.println("INF - NINF is£º " + (x1 - x2));
		System.out.println("INF * NINF is£º " + (x1 * x2));
		System.out.println("INF / NINF is£º " + (x1 / x2));
		System.out.println("");

		System.out.println("Conclusion: The propagation and interaction of INF and NINF are unpredictable, "
				+ "the result will be NaN or INF or NINF.");
		System.out.println("\r\n");
		System.out.println("");

	}

}
