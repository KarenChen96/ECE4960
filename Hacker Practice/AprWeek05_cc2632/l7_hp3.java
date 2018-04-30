/*dates: 4/29/2018
 * authors: Chun Chen
 * Language: Java
 * Platforms: Eclipse in Windows10.
 * */

/*This is the third hacker practice of lecture 7 */
package hp_lecture7;

import hm4.Row_productAx;
import hm4.sparse_mat;
import java.util.Arrays;

import assignment2.jacobi;

public class l7_hp3 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// 1-D Parabolic PDE
		double h = 1;
		double delta_t = 1;
		double D = 1;
		double term1 = 1.0 / delta_t + 2 * D / (h * h);
		double term2 = -D / (h * h);
		double term3 = 1.0 / delta_t;
		
		
		int[] rP1 = {0, 2, 5, 7};
		int[] cI1 = {0, 1, 0, 1, 2, 1, 2};
		double[] v1 = {term1, term2, term2, term1, term2, term2, term1};

		sparse_mat A = new sparse_mat();
		A.rowPtr = rP1;
		A.colInd = cI1;
		A.value = v1;
		A.createMatrix(rP1, cI1, v1);
		
		int[] rP2 = { 0, 1, 2, 3 };
		int[] cI2 = { 0, 1, 2 };
		double[] v2 = { term3, term3, term3 };
		
		sparse_mat B = new sparse_mat();
		B.rowPtr = rP2;
		B.colInd = cI2;
		B.value = v2;
		B.createMatrix(rP2, cI2, v2);
			
		double[] n_v = {0, 10, 0};
		double[] b = Row_productAx.productAx(B, n_v);		
		
		
		//Zero-slope Neumann boundary condition
		int[] rP3 = { 0, 2, 5, 7 };
		int[] cI3 = { 0, 1, 0, 1, 2, 1, 2 };
		double[] v3 = { term1 - 1, term2, term2, term1, term2, term2, term1 - 1 };
		
		sparse_mat C = new sparse_mat();
		C.rowPtr = rP3;
		C.colInd = cI3;
		C.value = v3;
		C.createMatrix(rP3, cI3, v3);
		
		b = Row_productAx.productAx(C, n_v);
	
	}

}
