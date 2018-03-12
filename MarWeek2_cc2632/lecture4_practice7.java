/*dates: 3/11/2018
 * authors: Chun Chen
 * Language: Java
 * Platforms: Eclipse in Windows10.
 * */

package hp_lecture4;

import hp_lecture4.formula;
import hp_lecture4.lecture4_practice5;
import hm4.sparse_mat;
import assignment2.sparse_op;
import hm4.Row_productAx;

//Use the SOR iterations to solve the problem with w = 0.5
public class lecture4_practice7 {	
		//calculate xk
		public static void solu(sparse_mat A, double[] b )
		{
			sparse_mat D = sparse_op.dia_matrix(A);
			sparse_mat L = sparse_op.lower_matrix(A);
			sparse_mat U = sparse_op.upper_matrix(A);
			double[] xk = new double[b.length];
			double[] xk1 = new double[b.length];
			
			// obtain T and C
			sparse_mat inv_D = sparse_op.inverse(D);	
			sparse_mat T = sparse_op.productmm(inv_D, sparse_op.addmm(L,U,1.0), 0.5);
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
			//||delta(x)||/||x||
			while (formula.norm2(err)/formula.norm2(xk) > Math.pow(10, -7)) {
				System.out.println("This is the " + num + "-th iteration.");
				System.out.print("x1(k): " + xk[0] + "		");
				System.out.print("x1(k+1): " + xk1[0]+ "		");
				System.out.print("||delta(x)||: " + formula.norm2(err) + "		");
				
				double[] result = Row_productAx.productAx(A, xk);
				System.out.println("||r(k)||: " + formula.diff(result, b));
				System.out.println(" ");
				
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
			//check precision by checking the evolution of the residual vector: ||b ¨C Ax(k)||2.
			double[] result = Row_productAx.productAx(A, xk);
			double diff = formula.diff(result, b);
			System.out.println("The normalized residual norm of this iterative solver is: " + diff);
		}
		
		public static void main(String[] args) {
			// TODO Auto-generated method stub
			sparse_mat A = new sparse_mat();
			int[] rowPtr = {0,3,6,9,12,15};
			int[] colInd = {0,1,4,0,1,2,1,2,3,2,3,4,0,3,4};
			double[] value = {-4,1,1,4,-4,1,1,-4,1,1,-4,1,1,1,-4};
			A.rowPtr = rowPtr;
			A.colInd = colInd;
			A.value = value;
			A.createMatrix(rowPtr,colInd,value);
		
			double[] b = {1,0,0,0,0};
			solu(A,b);
		}
}
