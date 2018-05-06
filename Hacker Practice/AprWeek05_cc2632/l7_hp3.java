/*dates: 4/29/2018
 * authors: Chun Chen
 * Language: Java
 * Platforms: Eclipse in Windows10.
 * */

/*This is the third hacker practice of lecture 7 */
package hp_lecture7;

import hm4.Row_productAx;
import assignment2.jacobi;
import hm4.sparse_mat;
import java.util.Arrays;
import assignment2.jacobi;

/* Solve 1-D Parabolic PDE with zero Dirichlet boundary condition 
and zero-slope Neumann boundary condition*/

public class l7_hp3 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		double h = 1;
		double delta_t = 1;
		double D = 1;
		
		System.out.println("**********Zero Dirichelet boundary condition**********");
		
		// define matrix [A] * n[i][j+1] = [B] * n[i][j]	
		double term1 = 1.0 / delta_t + 2 * D / (h * h);
		double term2 = -D / (h * h);
		double term3 = 1.0 / delta_t;
			
		int[] rP1 = {0, 2, 5, 7};
		int[] cI1 = {0, 1, 0, 1, 2, 1, 2};
		double[] v1 = {term1, term2, term2, term1, term2, term2, term1};
		sparse_mat A = new sparse_mat();
		A.rowPtr = rP1; A.colInd = cI1; A.value = v1;
		A.createMatrix(rP1, cI1, v1);
		
		int[] rP2 = { 0, 1, 2, 3 };
		int[] cI2 = { 0, 1, 2 };
		double[] v2 = { term3, term3, term3 };
		sparse_mat B = new sparse_mat();
		B.rowPtr = rP2; B.colInd = cI2; B.value = v2;
		B.createMatrix(rP2, cI2, v2);
			
		//Initial condition
		double[] n = {0, 10, 0};
		
		for(int t = 2; t <=5; t++) {
			// [B] * n[i][j]
			double[] b = Row_productAx.productAx(B, n);
			// calculate n[i][j+1]
			double[] n_New = jacobi.solu(A, b);		
			System.out.print("t =  "+ t + "		");
			System.out.println("n(x) is "+Arrays.toString(n_New));
			//update n(x)
			n = n_New;
		}	
		
		//Zero-slope Neumann boundary condition
		System.out.println("**********Zero-slope Neumann boundary condition**********");
		int[] rP3 = { 0, 2, 5, 7 };
		int[] cI3 = { 0, 1, 0, 1, 2, 1, 2 };
		double[] v3 = { term1 - 1, term2, term2, term1, term2, term2, term1 - 1 };
		
		sparse_mat C = new sparse_mat();
		C.rowPtr = rP3; C.colInd = cI3; C.value = v3;
		C.createMatrix(rP3, cI3, v3);		
		
		double[] n2 = { 0, 10, 0 };
		for (int t = 2; t <= 5; t++) {
			// [B] * n[i][j]
			double[] b = Row_productAx.productAx(B, n2);
			// calculate n[i][j+1]
			double[] n_New = jacobi.solu(A, b);
			System.out.print("t =  "+ t + "		");
			System.out.println("n(x) is " + Arrays.toString(n_New));
			//update n(x)
			n2 = n_New;
		}
		
	
	}

}
