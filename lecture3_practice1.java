/*dates: 2/10/2018
 * authors: Chun Chen
 * Language: Java
 * Platforms: Eclipse in Windows10.
 * */

package hacker_practice;

public class lecture3_practice1 {
	public static double fx11(double x) {
		double y = x * x;
		return y;
	}
	
	public static double fx12(double x) {
		double y = x * x + Math.pow(10, 8);
		return y;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub	
		 double x = 1;
		 double re1 = 0;
		 double re2 = 0;	
		 double re11 = 0;
		 double re22 = 0;	
		 double h0 = 0.1;
		 for (int i = 0; i<18; i++)
		 {
			 System.out.println("h is: " + h0);
			 re1 = Math.abs((fx11(x+h0) - fx11(x))/h0 - 2 ) / 2;
			 re2 = Math.abs((fx11(x+h0) - fx11(x-h0))/(2*h0) - 2) / 2;
			 System.out.println("********Relative error in calculating f'(x) (first order)********");
			 System.out.println("Relative error in calculating f'(x) (first order) is: " + re1);
			 System.out.println("Relative error in calculating f'(x) (second order) is: "  + re2);	
			 System.out.println("");
			 h0=h0/10;	 
		 }
		 
		 h0 = 0.1;
		 for (int i = 0; i<18; i++)
		 {
			 System.out.println("h is: " + h0);
			 //re11 = Math.abs((fx12(x+h0) - fx12(x))/h0 - 2 );
			// re22 = Math.abs((fx12(x+h0) - fx12(x-h0))/(2*h0) - 2);
			 re11 = Math.abs(((Math.pow(x+h0, 2) + Math.pow(10, 8)) - (Math.pow(x, 2) + Math.pow(10, 8)))/h0 - 2 ) / 2;
			 re22 = Math.abs(((Math.pow(x+h0, 2) + Math.pow(10, 8)) - (Math.pow(x-h0, 2) + Math.pow(10, 8)))/(2*h0) - 2) / 2;			 
			 System.out.println("Relative error in calculating f'(x) (first order) is: " + re11);
			 System.out.println("Relative error in calculating f'(x) (second order) is: " + re22);
			 System.out.println("");
			 h0=h0/10;	
		 }

	}

}
