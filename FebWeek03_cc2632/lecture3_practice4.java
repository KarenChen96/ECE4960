package hacker_practice;

public class lecture3_practice4 {
	public static double fx(double x)
	{
		double y = Math.pow(Math.E, x);
		return y;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//h is fixed now, but it should be adaptive
		//calculated 
		double df = Math.E - 1/ Math.E; // ground truth value
		System.out.println("The grounf truth value is: " + df);
		System.out.println("");
		
		double h = 0.1; //set the value of interval
		
		//rectangle
		double approx1 = 0;
		double err1 = 0;
		for (double i = -1; i <= 1; i = i+h)
		{
			approx1 = approx1 + h * fx(i);		
		}		
		err1 = Math.abs(approx1 - df);	
		System.out.println("The approximation of Rectangle scheme is:" + approx1);
		System.out.println("The error of Rectangle scheme is:"+ err1);
		System.out.println("");
		
		//trapezoid
		double approx2 = 0;
		double err2 = 0;
		for (double i = -1; i <= 1; i = i+h)
		{
			approx2 = approx2 + h * (0.5*fx(i) + 0.5*fx(i+h));			
		}			
		err2 = Math.abs(approx2 - df);
		System.out.println("The approximation of Trapzoid scheme is:" + approx2);
		System.out.println("The error of Trapzoid scheme is:" + err2);
		System.out.println("");
		
		//mid-point
		double approx3 = 0;
		double err3 = 0;
		for (double i = -1; i <= 1; i = i+h)
		{
			approx3 = approx3 + h * (fx((i+(i+h))/2));
		}			
		err3 = Math.abs(approx3 - df);		
		System.out.println("The approximation of mid-point scheme is:" + approx3);
		System.out.println("The error of mid-point scheme is:" + err3);
		System.out.println("");
		
		//Simpson
		double approx4 = 0;
		double err4 = 0;
		double a1 = 0;
		double a2 = 0; 
		double a3 = 0;
		for (double i = -1; i <= 1; i = i+h)
		{
			double part = h * (1*fx(i) + 4*fx((i+(i+h))/2) + 1*fx(i+h)) ;
//			approx4 = approx4 + h * ((1/6)*fx(i) + (4/6)*fx((i+(i+h))/2) + (1/6)*fx(i+h));
			double part1 = part/6;
			approx4 = approx4 + part1;
		}		
		err4 = Math.abs(approx4 - df);	
		System.out.println("The approximation of Simpson scheme is:" + approx4);
		System.out.println("The error of Simpson scheme is:" + err4);
		System.out.println("");
		
		//2-point Guassian
		double approx5 = 0;
		double err5 = 0;
		double temp = 2*(Math.sqrt(3));
		for (double i = -1; i <= 1; i = i+h)
		{
			double part = h * ( 1*fx((i+(i+h))/2 - h/temp) + 1*fx((i+(i+h))/2 + h/temp));
			double part1 = part/2;
			approx5 = approx5 + part1;
//			approx5 = approx5 + h * ( (1/2) * fx((i+(i+h))/2 - h/temp) + (1/2) * fx((i+(i+h))/2 + h/temp));	
		}			
		err5 = Math.abs(approx5 - df);
		System.out.println("The approximation of 2-point Guassian scheme is:" + approx5);
		System.out.println("The error of 2-point Guassian scheme is:" + err5);
		System.out.println("");
	}

}
