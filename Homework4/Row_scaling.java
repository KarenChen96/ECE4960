/*dates: 2/25/2018
 * authors: Chun Chen
 * Language: Java
 * Platforms: Eclipse in Windows10.
 * */
package hm4;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Row_scaling {
	public static sparse_mat rowScaling(sparse_mat A, double[] v, int i, int j, double a) {
		// create hashmap<col, val> for each row
		Map<Integer, Double> m1 = new HashMap<>();
		Map<Integer, Double> m2 = new HashMap<>();
		m1 = A.a.get(i);
		m2 = A.a.get(j);
		for (int k = 0; k < A.full_m[i].length; k++) {
			// k-th column has non-zero value for both two rows
			if (m1.containsKey(k) && m2.containsKey(k)) {
				double t = m1.get(k) + m2.get(k) * a;
				m1.put(k, t);
			}
			// row(i) doesn't has k-th column but row(j) has this column
			else if (!m1.containsKey(k) && m2.containsKey(k)) {
				m1.put(k, m2.get(k)*a);
			}
		}
		A.a.add(i, m1);
		A.a.remove(i + 1);
		return A;
	}

	// Compute the product of Ax = b when A is full_matrix
	public static sparse_mat full_rowScaling(sparse_mat A, double[] v, int i, int j, double a) {
		double[] m1 = A.full_m[i];
		double[] m2 = A.full_m[j];
		for (int k = 0; k < A.full_m[i].length; k++) {
			m2[k] = A.full_m[i][k] * a;
			A.full_m[j][k] = A.full_m[j][k] + m2[k];
		}
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
		A = rowScaling(A, x, 0,2, 2.0);

		System.out.println("The result of row scaling of a sparse matrix is: ");
		for (int i = 0; i < A.a.size(); i++) {
			for (int j = 0; j < A.a.size(); j++) {
				System.out.print(A.retrieveElement(i, j) + " ");
			}
			System.out.println("");
		}

		System.out.println("");
		System.out.println("The result of row sacling of a full matrix is: ");
		sparse_mat A1 = new sparse_mat();
		A1 = full_rowScaling(A, x, 0,2, 2.0);
		for (int i = 0; i < A.a.size(); i++) {
			for (int j = 0; j < A.a.size(); j++) {
				System.out.print(A1.retrieveElement(i, j) + " ");
			}
			System.out.println("");
		}

	}

}
