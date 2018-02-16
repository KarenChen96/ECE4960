package program1;

public class Exception2 {
	// Integer divided by 0
	public static int excep2(int x) {
		int y = 1 / x;
		return y;
	}

	public static void main(String[] args) {

		System.out.println("*****Observe Integer divided by 0*****");
		System.out.println("----Use a variable to create such exception----");
		try {
			System.out.println("The result of integer/0 is: " + Exception2.excep2(0));
		} catch (Exception e) {
			// throw new Exception("ArithmeticException");
			System.out.println("Integer/0 is an Arithmetic Exception. ");
			System.out.println("\r\n");
			System.out.println("");
		}
	}

}
