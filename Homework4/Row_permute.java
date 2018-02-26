/*dates: 2/25/2018
 * authors: Chun Chen
 * Language: Java
 * Platforms: Eclipse in Windows10.
 * */
package hm4;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Row_permute {
	public static sparse_mat rowPermute(sparse_mat A, double[] v, int i, int j ) {
		// create hashmap<col, val> for each row
		Map<Integer, Double> m1 = new HashMap<>();
		Map<Integer, Double> m2 = new HashMap<>();

		m1 = A.a.get(i);
		m2 = A.a.get(j);
		A.a.add(i, m2);
		A.a.remove(i+1);
		A.a.add(j, m1);
		A.a.remove(j+1);
		return A;
	}
	
	// Compute the product of Ax = b when A is full_matrix
	public static sparse_mat full_rowPermute(sparse_mat A, double[] v, int x, int y) {
		double[] m1 = A.full_m[x];
		double[] m2 = A.full_m[y];
		A.full_m[x] = m2;
		A.full_m[y] = m1;
		return A;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		sparse_mat A = new sparse_mat();
		int[] rowPtr = {0,3,6,9,10,12};
		int[] colInd = {0,1,4,0,1,2,1,2,4,3,0,4};
		double[] value = {1,2,3,4,5,6,7,8,9,10,11,12};
		A.createMatrix(rowPtr,colInd,value);
		double[] x = { 5, 4, 3, 2, 1 };
		A = rowPermute(A, x, 1,3);
		
		System.out.println("The result of row permute of a sparse matrix is: ");
		for (int i = 0; i < A.a.size(); i++) {
			for (int j = 0; j < A.a.size(); j++) {
				System.out.print(A.retrieveElement(i, j) + " ");
			}
			System.out.println("");
		}
		
		System.out.println("");
		System.out.println("The result of row permute of a full matrix is: ");
		sparse_mat A1 = new sparse_mat();
		A1 = full_rowPermute(A, x, 1,3);
		for (int i = 0; i < A.a.size(); i++) {
			for (int j = 0; j < A.a.size(); j++) {
				System.out.print(A1.retrieveElement(i, j) + " ");
			}
			System.out.println("");
		}

	}

}
