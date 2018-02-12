/*dates: 2/10/2018
 * authors: Chun Chen
 * Language: Java
 * Platforms: Eclipse in Windows10.
 * */

package hacker_practice;

public class lecture3_practice3 {

	public static double fx2(double x) {
		double y = x * x * x + Math.pow(10, 10);
		return y;
	}
	
	public static double fx3(double x) {
		double y = x * x * x ;
		return y;
	}
	
	public static double fx3_1(double h, double x) {
		double y_1 = (fx3(x+h) - fx3(x)) / h;
		return y_1;
	}
	

	public static double fx3_2(double h, double x) {
		double y_2 = -fx3(x+2*h)/(2*h) - 3*fx3(x)/(2*h) + 2*fx3(x+h)/h;
		return y_2;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 double x = 1;		 
		 double h = Math.pow(2, -4);
				 
		 double re3 = 0; 
		 double re4 = 0;
		 double re5 = 0;
		 double re6 = 0;
		 double eta1 = 0;
		 double eta2= 0;
		 double eta3 = 0;
		 double eta4 = 0;
		
		 for (int i = 0; i < 37; i++)
		 {
		 re3 = Math.abs(fx3_1(h,x) - 3 );
		 re4 = Math.abs(fx3_1(2*h,x) - 3 );
		 re5 = Math.abs(fx3_2(h,x) - 3 );
		 re6 = Math.abs(fx3_2(2*h,x) - 3 );
			 
		 eta1 = re4/re3;
		 eta2 = Math.abs( (fx3_1(4*h,x) - fx3_1(2*h,x)) / (fx3_1(2*h,x) - fx3_1(h,x)));
		 eta3 = re6/re5;
		 eta4 = Math.abs( (fx3_2(4*h,x) - fx3_2(2*h,x)) / (fx3_2(2*h,x) - fx3_2(h,x)));
		
		 System.out.println("**********The relative error in calculating f'(x) when h = " + h +"**********");
		 System.out.println("Relative error of first order with h is: " + re3);
		 System.out.println("Relative error of first order with 2h is: " + re4);
		 System.out.println("Relative error of second order with h is: " + re5);
		 System.out.println("----------Estimate eta for differnet choice of h (first order)");
		 System.out.println("Estimate Eta by E(2h)/E(h) is: " + eta1);
		 System.out.println("Estimate Eta by another alternative is: " + eta2);	
		 System.out.println("----------Estimate eta for differnet choice of h (second order)");
		 System.out.println("Estimate Eta by E(2h)/E(h) is: " + eta3);
		 System.out.println("Estimate Eta by another alternative is: " + eta4);
		 System.out.println(""); 
		 //System.out.println(eta2);
		 
		 h = h / 2;
		 }
	}

}
