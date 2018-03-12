/*dates: 3/11/2018
 * authors: Chun Chen
 * Language: Java
 * Platforms: Eclipse in Windows10.
 * */

package assignment2;

import hp_lecture4.formula;
import hm4.sparse_mat;
import hm4.Row_productAx;

public class jacobi {	
	//Function solu: calculate xk, solution of Ax=b
	/*Ideas:
	 * (1)Obtain D,L,U from A and then obtain T and C.
	 * (2)Initialize x0 and calculate x1 before goes into iteration; let "num" to record the cycles 
	 * (3)Using ||delta(x)||/||x|| to represent error, as while condition
	 * (4) Re-calculate xk+1 by xk; reassign and re-calculate error until error < 10^(-7)
	 * */
	public static double[] solu(sparse_mat A, double[] b )
	{
		sparse_mat D = sparse_op.dia_matrix(A);
		sparse_mat L = sparse_op.lower_matrix(A);
		sparse_mat U = sparse_op.upper_matrix(A);
		double[] xk = new double[b.length];
		double[] xk1 = new double[b.length];
		
		// obtain T and C
		sparse_mat inv_D = sparse_op.inverse(D);	
		sparse_mat T = sparse_op.productmm(inv_D, sparse_op.addmm(L,U,1.0), 1.0);
		double[] C = Row_productAx.productAx(inv_D, b);

		//initial guess x0 and then calculate x1
		for (int i = 0; i < xk.length; i++) {
			xk[i] = C[i];
		}
		
		double[] temp = Row_productAx.productAx(T, xk);
		for (int i = 0; i < xk1.length; i++) {
			xk1[i] = temp[i] + C[i];
		}
		int num = 1;// record the number of iterations
		
		double[] err = new double[xk.length];
		for (int i = 0; i<xk.length; i++)
		{
			err[i] = (xk1[i] - xk[i]);
		}
		
		while (formula.norm2(err)/formula.norm2(xk) > Math.pow(10, -7)) {
			//reset xk as xk1 and re-calculate xk1
			for (int i = 0; i < xk.length; i++) {
				xk[i] = xk1[i];
			}
			double[] temp1 = Row_productAx.productAx(T, xk);;
			for (int i = 0; i < xk1.length; i++) {
				xk1[i] = temp1[i] + C[i];
			}
			// reset error set
			for (int i = 0; i < xk.length; i++) {
				err[i] = xk1[i] - xk[i];
			}
			num = num + 1;
		}

		System.out.println("Iteration that are needed for convergence is: " + num);
		return xk1;
	}
}

