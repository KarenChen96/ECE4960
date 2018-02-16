package program1;

public class Exception5 {
	// NaN: generation, detection, propagation, interaction with other exception cases
	public static void main(String[] args) {
		// TODO Auto-generated method stub	
		double x1 = 1.0 / +0.0; //INF	
		double x2 = 1/ -0.0; //NINF
		double x3 = 2/0.0;
		double x4 = -2/0.0;
		
		System.out.println("*****Observe floating-point operations of NaN*****");
		System.out.println("");
		double a = x1 / x2;
		
		System.out.println("----Detect NaN----");
		System.out.println("Create NaN by dividing two NINF: x1 / x3 = " +x1+"/"+x3+" = " + a);
		System.out.println("");

		// detection??
		System.out.println("----Detect NaN----");
		System.out.println("The result of NaN == NaN is: " + (a==a));
		System.out.println("Conclusion: If a number is not equal to itself, then it is not a nunmber.");		
		System.out.println("");

		// propagation
		System.out.println("----Observe NaN's propagation----");
		System.out.println("log(sin(NaN) is£º " + Math.log(Math.sin(a)));
		System.out.println("Log(exp(NaN) is: " + Math.log(Math.exp(a)));
		System.out.println("Conclusion: The result of NaN's propagation is NaN.");
		System.out.println("");

		// interaction with other exception
		System.out.println("----Observe NaN's interaction with other exception----");
		System.out.println(" ");
		try {
			System.out.println("Add NaN with the result of int/0 is: " + (a + Exception2.excep2(0)));

		} catch (Exception e) {
			// throw new Exception("ArithmeticException");
			System.out.println("NaN interacts with integer divided by 0 is Arithmetic Exception. ");
		}
		System.out.println("");
		
		System.out.println("----NaN interacts with INF----");
		System.out.println("The result of NaN + INF is: " + (a + 1/0.0));
		System.out.println("The result of NaN - INF is: " + (a - 1/0.0));
		System.out.println("The result of NaN * INF is: " + (a * (1/0.0)));
		System.out.println("The result of NaN / INF is: " + (a / (1/0.0)));
		System.out.println(" ");
		
		System.out.println("----NaN interacts with NINF----");
		System.out.println("The result of NaN + NINF is: " + (a + (-1/0.0)));
		System.out.println("The result of NaN - NINF is: " + (a - 1/0.0));
		System.out.println("The result of NaN * NINF is: " + (a * (-1/0.0)));
		System.out.println("The result of NaN / NINF is: " + (a / (-1/0.0)));	
		
		System.out.println("Conclusion: The result of NaN's interaction with others are NaN ");
		System.out.println("\r\n");
		System.out.println("");
	}

}
