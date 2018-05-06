/*dates: 5/4/2018
 * authors: Chun Chen
 * Language: Java
 * Platforms: Eclipse in Windows10.
 * */

package hp_lecture7;

import java.util.Arrays;

public class l72_hp1 {
	public static double fx(double x) {
		return Math.sin(x);
	}

	public static double vk_x(double xk0, double xk, double xk1, double x) {
		if (x < xk && x >= xk0)
			return (x - xk0) / (xk - xk0);
		else if (x >= xk && x < xk1)
			return (xk1 - x) / (xk1 - xk);
		else
			return 0;
	}
	
	//another version of calculating vk_x
	//i represents the i-th line we use to approximate, i>=1
	public static double vk_x(double[] basepoint, int i, double x) {
		// i-th rooftop line. Eg, in this practice, 1<=i<=7
		if (i > 0 && i < basepoint.length - 1) {
			double xk = basepoint[i];
			double xk1 = basepoint[i + 1];
			double xk0 = basepoint[i - 1];
			if (x < xk && x >= xk0)
				return (x - xk0) / (xk - xk0);
			else if (x >= xk && x < xk1)
				return (xk1 - x) / (xk1 - xk);
			else
				return 0;
		}
		else return -1;//input a wrong format parameter	
	}

	public static double cal_ak(double xk0, double xk, double xk1) {
		double temp = vk_x(xk0, xk, xk1, xk);
		double ak = fx(xk) / temp;
		return ak;
	}
	
	//another version of calculating ak
	public static double[] cal_ak(double[] basepoint) {
		double[] ak = new double[basepoint.length-1];
		for(int i = 1; i<basepoint.length-1; i++) {
			double temp = vk_x(basepoint,i,basepoint[i]);
			ak[i] = fx(basepoint[i]) / temp;
		} 
		return ak;
	}
	
	//x0(=0) x1 x2 ..... x7, x8=pi/2
	//a0(=0) a1 a2 ..... a7	
	public static double rooftop(double[] xk, double[] ak, double x) {
		int l = xk.length-1;//x0 ... xN 
		//l=8 in test example
		double result = 0;
		//over the range we do the approximation
		if(x>Math.PI/2 || x<0) {
			return -1; 
		}
		
		//first line
		if (x>=xk[0] && x<=xk[1]) {
			result = vk_x(xk[0], xk[1],xk[2], x) * ak[1];
			return result;
		}
		//last line
		else if (x>=xk[l-1] && x<=xk[l]) {
			result = vk_x(xk[l-2], xk[l-1],xk[l], x) * ak[l-1];
			return result;
		}
		//middle lines
		else {
			for (int i = 1; i<l-1;i++) {
				if (x>=xk[i] && x<=xk[i+1]) {
					double temp1 = vk_x(xk[i-1], xk[i],xk[i+1], x) * ak[i];
					double temp2 = vk_x(xk[i], xk[i+1],xk[i+2], x) * ak[i+1];
					result = temp1 + temp2;
					return result;
				}
			}	
		}		
		return result;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int N = 8;
		double delta = Math.PI / (2 * N);

		double[] basepoint = new double[N + 1];
		basepoint[0] = 0;

		for (int i = 1; i < N + 1; i++) {
			basepoint[i] = basepoint[i - 1] + delta;
		}		
		
		double[] ak = new double[N];
//		for (int i = 1; i<N; i++) {
//			ak[i] = cal_ak(x[i-1],x[i],x[i+1]);
//		}
		ak = cal_ak(basepoint);
		
		//Use 100 points by the ground truth and approximated roof top function 
		//to find the percentage error in the area estimate
		double[] x = new double[100];
		double[] error = new double[100];
		x[0] = 0;
		double dx = Math.PI/200;
		for (int i = 1; i < 90; i++) {
			x[i] = x[i-1] + dx;
			double gt = fx(x[i]);
			double appro = rooftop(basepoint, ak, x[i]);
			//System.out.print(i+"    ");System.out.print(x[i]+"    ");System.out.print(appro+"    "); System.out.println(gt);
			error[i] = Math.abs((gt-appro)/gt);
		}
		
		//how to simulate the last line? using x8??
		System.out.println(Arrays.toString(error));
		
	}

}
