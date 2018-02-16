package program1;

public class Exception1 {
	// Integer overflows, approaching overflow by multiplication (10)
	public static int excep11(int x) {
		int y = 1;
		for (int i = 0; i < x; i++) {
			y = y * 10;
		}
		return y;
	}

	// Integer overflows, approaching overflow by multiplication (2)
	public static int excep12(int x) {
		int y = 1;
		for (int i = 0; i < x; i++) {
			y = y * 2;
		}
		return y;
	}

	// Integer overflows, approaching overflow by factorials
	public static int excep13(int x) {
		int y = 1;
		for (int i = 1; i <= x; i++) {
			y = y * i;
		}
		return y;
	}

	public static boolean isOverflow1(double x, int n) {
		for (int i = n; i>0; i--)
		{
			x = x/10;
		}
		if (x == 1) {
			return false;
		} else {
			return true;
		}
	}
	
	public static boolean isOverflow2(double x, int n) {
		for (int i = n; i>0; i--)
		{
			x = x/2;
		}
		if (x == 1) {
			return false;
		} else {
			return true;
		}
	}
	
	public static boolean isOverflow3(double x, int n) {
		for (int i = n; i>0; i--)
		{
			x = x/i;
		}
		if (x == 1) {
			return false;
		} else {
			return true;
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("*****Observe Integer overflow*****");
		System.out.println("");
		System.out.println("----Use multiplication to cause integer overflow----");

		for (int i = 8; i <= 40; i++) {
			double a = Exception1.excep11(i);
			System.out.println("The result of pow(10, " + i + ") is: " + a);
			if (isOverflow1(a, i)) {
				System.out.println("pow(10, " + i + ") is overflow ");
				System.out.println(" ");
				System.out.println(
						"Conclusion: The max multiplication (of 10) can be correctly calculated in Integer is pow(10," + i +").");
				System.out
						.println("When calculate numbers which are larger than pow(10,31), the result will become 0.");
				break;
			}
		}
		
		System.out.println(" ");
		
		for (int i = 30; i <= 35; i++) {
			double a = Exception1.excep11(i);
			System.out.println("The result of pow(2, " + i + ") is: " + a);
			if (isOverflow2(a, i)) {
				System.out.println("pow(2, " + i + ") is overflow ");
				System.out.println(" ");
				System.out.println(
						"Conclusion: The max multiplication (of 2) can be correctly calculated in Integer is pow(2," + i +").");
				System.out.println("When calculate numbers which are larger than pow(2,31), the result will become 0.");
				break;
			}
		}
		
		System.out.println("");

		System.out.println("----Use factorials to cause integer overflow----");
		
		for (int i = 8; i <= 40; i++) {
			double a = Exception1.excep13(i);
			System.out.println("The result of " + i + "! is: " + a);
			if (isOverflow3(a,i)) {
				System.out.println( i + "! is overflow ");
				System.out.println("Conclusion: The max factorials can be correctly calculated in Integer is " + i + "!.");
				System.out.println("When calculate numbers which are larger than 33!, the result will become 0.");
				break;
			}
		}

		System.out.println("\r\n");
		System.out.println("");
	}

}
