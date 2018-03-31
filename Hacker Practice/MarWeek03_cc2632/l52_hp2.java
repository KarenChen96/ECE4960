/*dates: 3/22/2018
 * authors: Chun Chen
 * Language: Java
 * Platforms: Eclipse in Windows10.
 * */

/*Construct a B-Spline curve with some anchor points*/
package hp_lecture52;

public class l52_hp2 {
	public static double[] constrains(double[] x, double[] y, double k0, double kd)
	{
		int n = x.length - 1;
		double[] k = new double[x.length];
		double c1 = 0;
		double c2 = 0;
		double c3 = 0;
		double b = 0;
		k[0] = k0;
		k[1] = (3*(y[1]-y[0]) / (x[1]-x[0])) - 2*k[0];
		
		//k[2] ... k[d-1]
		for(int i = 2; i< x.length; i++)
		{
			c1 = 1/(x[i-1] - x[i-2]);
			c2 = 2 * (1 / (x[i - 1] - x[i -2]) + 1 / (x[i] - x[i - 1]));
			c3 = 1 / (x[i] - x[i - 1]);
			b = 3 * (y[i] - y[i-1]);
			System.out.println("c1 = " + c1 + "	c2 = " + c2 + "	c3 = " + c3 + "	b = " + b);
			k[i] = (b - c1 * k[i-2] - c2 * k[i-1]) / c3; 
		}
		
		//k[d]
		c1 = 0;
		c2 = 2 * (x[n] - x[n - 1]);
		c3 = x[n] - x[n - 1];
		b =  3 * (y[n] - y[n - 1]);
		System.out.println("c1 = " + c1 + "	c2 = " + c2 + "	c3 = " + c3 + "	b = " + b);
		//k[n] = (b - c2 * k[n-1]) / c3; 	
		
		return k;
	}
	
	//The number of intervals is (x.length-1)
	public static double[] cal_a(double[] x, double[] y, double[] k) {
		double[] a = new double[x.length-1];
		for (int i = 0; i < a.length; i++) {
			a[i] = k[i] * (x[i+1] - x[i]) - (y[i+1] - y[i]);
		}
		return a;
	}

	public static double[] cal_b(double[] x, double[] y, double[] k) {
		double[] b = new double[x.length-1];
		for (int i = 0; i < b.length; i++) {
			b[i] = -k[i+1] * (x[i+1] - x[i]) + (y[i+1] - y[i]);
		}
		return b;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		double[] x = {1.0,2.0,3.0,4.0,5.0,6.0};
		double[] y = {0.0, 1.0, 0.0, -1.0,0.0, 1.0};
		double k0 = 1;
		double kd = 1;
		double[] k = constrains(x,y,k0,kd);
		
		double[] a = cal_a(x,y,k);
		double[] b = cal_b(x,y,k);
		
		double[] C0 = new double[a.length];
		double[] C1 = new double[a.length];
		double[] C2 = new double[a.length];
		double[] C3 = new double[a.length];
		for (int i = 0; i < a.length; i++) {
			C3[i] = a[i] - b[i];
			C2[i] = b[i] - 2 * a[i];
			C1[i] = a[i] + y[i + 1] - y[i];
			C0[i] = y[i];
		}
		
		for (int i=0; i<k.length; i++)
		{
			//System.out.println(C3[i] + " " + C2[i] + "  " + C2[i] + "  " + C0[i]);
			System.out.println(k[i]);
		}
		for (int i=0; i<a.length; i++)
		{
			System.out.println(C3[i] + " " + C2[i] + "  " + C2[i] + "  " + C0[i]);
		}
	}

}
