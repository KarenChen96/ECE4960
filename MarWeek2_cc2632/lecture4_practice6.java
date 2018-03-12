/*dates: 3/11/2018
 * authors: Chun Chen
 * Language: Java
 * Platforms: Eclipse in Windows10.
 * */

package hp_lecture4;

//Use the Jacobi iterations to solve the problem
public class lecture4_practice6 {
	//OBTAIN D
	public static double[][] dia_matrix(double[][] m) {
		double[][] d = new double[m.length][m.length];
		for (int i = 0; i < d.length; i++) {
			d[i][i] = m[i][i];
		}
		return d;
	}	
		
	//obtain L
	public static double[][] lower_matrix(double[][] m) {
		double[][] d = new double[m.length][m.length];
		for (int i = 0; i < d.length; i++) {
			for(int j = i+1; j<m[i].length; j++)
			{
				d[i][j] = -m[i][j];
			}
		}
		return d;
	}
	
	//obtain U
	public static double[][] upper_matrix(double[][] m) {
		double[][] d = new double[m.length][m.length];
		for (int i = 1; i < d.length; i++) {
			for (int j =0; j<i; j++)
			{
				d[i][j] = -m[i][j];
			}
		}
		return d;
	}
	
	//calculate inverse matrix of a diagonal matrix
	public static double[][] inverse(double[][] d) {
		double[][] inv_d = new double[d.length][d.length];
		for (int i = 0; i < d.length; i++) {
			if (d[i][i] != 0) {
				inv_d[i][i] = 1/d[i][i];
			}
		}
		return inv_d;
	}
	
	//calculate xk
	public static void xk(double[][] m, double[][] D, double[][] L, double[][] U, double[] b) {
		double[] xk = new double[b.length];
		double[] xk1 = new double[b.length];

		// obtain T and C
		double[][] inv_D = inverse(D);
		
		double[][] T = new double[b.length][b.length];
		double[] C = new double[b.length];// 沒有考慮非方陣的情況,應該不需要考慮
		for (int i = 0; i < T.length; i++) {
			for(int j = 0; j<T.length; j++)
			{
				T[i][j] = inv_D[i][i] * (L[i][j] + U[i][j]);
			}
			C[i] = inv_D[i][i] * b[i];
		}
		
		double[] result1 = formula.vecMul(m, xk);
		
		double[] temp = formula.vecMul(T,xk);
		for (int i = 0; i < xk1.length; i++) {
			xk1[i] = temp[i] + C[i];
		}
		
		double[] result2 = formula.vecMul(m, xk1);
		
		double diff1[] = new double[xk.length];
		double diff2[] = new double[xk.length];
		for (int i = 0; i < T.length; i++) {
			diff1[i] = result1[i] - b[i];
			diff2[i] = result2[i] - b[i];
		}
		
		int num = 1;// record the number of iterations
		
		double[] err = new double[xk.length];
		for (int i = 0; i<xk.length; i++)
		{
			err[i] = xk1[i] - xk[i];
		}
		//while (norm2(diff2)/norm2(diff1) > Math.pow(10, -7)) {
		while (formula.norm2(err)/formula.norm2(xk) > Math.pow(10, -7)) {
			for (int i = 0; i < xk.length; i++) {
				xk[i] = xk1[i];
			}
			double[] temp1 = formula.vecMul(T,xk1);
			for (int i = 0; i < xk1.length; i++) {
				xk1[i] = temp1[i] + C[i];
			}
			result1 = formula.vecMul(m, xk);
			result2 = formula.vecMul(m, xk1);
			for (int i = 0; i < T.length; i++) {
				diff1[i] = result1[i] - b[i];
				diff2[i] = result2[i] - b[i];
			}
			//reset error set
			for (int i = 0; i<xk.length; i++)
			{
				err[i] = xk1[i] - xk[i];
			}
			num = num + 1;
		}
		System.out.println("Iteration that are needed for convergence is: " + num);
	
		//check precision by checking the evolution of the residual vector: ||b – Ax(k)||2.
		double[] result = formula.vecMul(m, xk1);
		double diff = formula.diff(result, b);
		System.out.println("The precison of iterative solver is: " + diff);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		double[][] a = { {-4,1,0,0,1}, {4,-4,1,0,0}, 
				{0,1,-4,1,0}, {0,0,1,-4,1}, {1,0,0,1,-4} };
		double[] b = {1,0,0,0,0};
		double[][] D = dia_matrix(a);
		double[][] L = lower_matrix(a);
		double[][] U = upper_matrix(a);
		xk(a,D,L,U,b);
		long used = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		System.out.println("Memory usage is: " + used + "bytes");
	}

}
