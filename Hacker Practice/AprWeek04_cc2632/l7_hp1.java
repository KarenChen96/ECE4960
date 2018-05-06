/*dates: 4/21/2018
 * authors: Chun Chen
 * Language: Java
 * Platforms: Eclipse in Windows10.
 * */

package hp_lecture7;

import java.util.Arrays;

import assignment2.jacobi;
import hm4.sparse_mat;
import hp_lecture4.formula;
import hp_lecture4.lecture4_practice4;

/*Use either a direct solver or Jacobi iterative solver 
to solve discretized Poisson Equation*/

public class l7_hp1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// Discretized Poisson equation -- Use Dirichlet boundary condition
		//direct solver... seems wrong
//		double[][] M1 = { { -2, 1, 0, 0 }, { 1, -2, 1, 0 }, { 0, 1, -2, 1 }, { 0, 0, 1, -2 } };
//		double[] b1 = { -1, 0, 0, 0 };
//		for (int i = 0; i < b1.length; i++) {
//			b1[i] = b1[i] * 0.2 * 0.2;
//		}
//		int[] solution1 = new int[M1.length];
//		double[] solu1 = lecture4_practice4.solution(M1, solution1, b1);
//		System.out.println(Arrays.toString(solu1));
		
		//Jocabi solver
		int[] rowPtr = { 0, 2, 5, 8, 10};
		int[] colInd = { 0, 1, 0, 1, 2, 1, 2, 3, 2, 3 };
		double[] value = { -2, 1, 1, -2, 1, 1, -2, 1, 1, -2};
		double[] x = { -1, 0, 0, 0};
		sparse_mat A = new sparse_mat();
		A.rowPtr = rowPtr; A.colInd = colInd; A.value = value;
		A.createMatrix(rowPtr, colInd, value);
		
		double[] solu1_jacobi = jacobi.solu(A,x);
		System.out.println(Arrays.toString(solu1_jacobi));

		//Use Neumann boundary condition
		//direct solver
//		double[][] M2 = { { -2, 1, 0, 0 }, { 1, -2, 1, 0 }, { 0, 1, -2, 1 }, { 0, 0, 1, -1} };
//		double[] b2 = { 1, 0, 0, 0 };
//		for (int i = 0; i < b2.length; i++) {
//			b2[i] = b2[i] * 0.2 * 0.2;
//		}
//		int[] solution2 = new int[M2.length];
//		double[] solu2 = lecture4_practice4.solution(M2, solution2, b2);
//		System.out.println(Arrays.toString(solu2));
//		
		//Jacobi solver
		double[] value2 = { -2, 1, 1, -2, 1, 1, -2, 1, 1, -1};
		sparse_mat A2 = new sparse_mat();
		A2.rowPtr = rowPtr; A2.colInd = colInd; A2.value = value2;
		A2.createMatrix(rowPtr, colInd, value2);
		double[] x2 = { -1, 0, 0, 0};
		
		double[] solu2_jacobi = jacobi.solu(A2,x2);
		System.out.println(Arrays.toString(solu2_jacobi));
	}

}
