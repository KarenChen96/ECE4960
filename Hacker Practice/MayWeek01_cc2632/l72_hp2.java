/*dates: 5/4/2018
 * authors: Chun Chen
 * Language: Java
 * Platforms: Eclipse in Windows10.
 * */

package hp_lecture7;

import java.util.Arrays;

import assignment2.jacobi;
import hm4.sparse_mat;

//Use finite element method with the rooftop function to solver the elliptical PDE
public class l72_hp2 {
	public static double[] Finite_Element(int N, double h) {
		
		//boundary condition
		double Phi0 = 0;
		double PhiN = 0;
		double term = h*h;
		//construct f
		double[] f = new double[N-1];
		for (int i = 0; i < f.length; i++) {
			f[i] = -1 * term;
		}			
		
		sparse_mat M = matrix();
		double[] PHI = jacobi.solu(M,f);
		System.out.println("Phi is: "+Arrays.toString(PHI));
		return PHI;
	}
	
	public static sparse_mat matrix() {

		int[] rowPtr = { 0, 2, 5, 8, 11, 14, 17, 20, 23, 25 };
		int[] colInd = { 0,1, 0,1,2, 1,2,3, 2,3,4, 3,4,5, 4,5,6, 5,6,7, 6,7,8, 7,8};
		double[] value = { -2,1, 1,-2,1, 1,-2,1, 1,-2,1, 1,-2,1, 1,-2,1, 1,-2,1, 1,-2,1, 1,-2 };

		sparse_mat A = new sparse_mat();
		A.rowPtr = rowPtr; A.colInd = colInd; A.value = value;
		A.createMatrix(rowPtr, colInd, value);
		//sparse_mat.display(A);
		return A;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int N = 10;
		double h = (double) 1/N;
		Finite_Element(N, h);//seems like the same with finite difference

	}

}
