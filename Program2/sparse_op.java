/*dates: 3/11/2018
 * authors: Chun Chen
 * Language: Java
 * Platforms: Eclipse in Windows10.
 * */

package assignment2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import hm4.sparse_mat;

/*define some functions will be used by sparse matrix
 * Including obtain lower/upper/diagonal/inverse(of a diagonal matrix) matrix, 
 * matrix multiplication(between a diagonal matrix and a normal matrix, 
 * and matrix addition
 * */
public class sparse_op {
	// OBTAIN D (diagonal matrix), only contains m values for a m*m matrix
	public static sparse_mat dia_matrix(sparse_mat A) {
		sparse_mat A1 = new sparse_mat();
		int[] rp = new int[A.rowPtr.length];
		int[] ci = new int[A.rowPtr.length - 1];
		double[] v = new double[A.value.length - 1];
		int num = 0;

		for (int i = 0; i < A.rowPtr.length - 1; i++) {
			for (int j = A.rowPtr[i]; j < A.rowPtr[i + 1]; j++) {
				if (A.colInd[j] == i) {
					ci[num] = i;// ci[j] = A.colInd[j]
					v[num] = A.value[j];
					num = num + 1;
					break;// this row has been traversed
				}
			}
			rp[i] = i;
		}
		rp[A.rowPtr.length - 1] = rp[A.rowPtr.length - 2] + 1;

		A1.rowPtr = rp;
		A1.colInd = ci;
		A1.value = v;
		A1.createMatrix(rp, ci, v);
		return A1;
	}

	// obtain L(lower matrix)
	public static sparse_mat lower_matrix(sparse_mat A) {
		sparse_mat A1 = new sparse_mat();
		int[] rp = new int[A.rowPtr.length];
		int[] ci = new int[A.colInd.length/2];
		double[] v = new double[A.colInd.length/2];
		int num = 0;
		rp[0] = 0;
		rp[1] = 0;

		for (int i = 1; i < A.rowPtr.length - 1; i++) {
			for (int j = A.rowPtr[i]; j < A.rowPtr[i + 1]; j++) {
				if (A.colInd[j] < i) {
					ci[num] = A.colInd[j];
					v[num] = -A.value[j];
					num = num + 1;
				}
			}
			rp[i + 1] = num;
		}

		A1.rowPtr = rp;
		A1.colInd = ci;
		A1.value = v;
		A1.createMatrix(rp, ci, v);
		return A1;
	}

	// obtain U (upper matrix)
	public static sparse_mat upper_matrix(sparse_mat A) {
		sparse_mat A1 = new sparse_mat();
		int[] rp = new int[A.rowPtr.length];
		int[] ci = new int[A.colInd.length/2];
		double[] v = new double[A.value.length/2];
		int num = 0;

		rp[0] = 0;
		for (int i = 0; i < A.rowPtr.length - 2; i++) {
			for (int j = A.rowPtr[i]; j < A.rowPtr[i + 1]; j++) {
				if (A.colInd[j] > i) {
					ci[num] = A.colInd[j];
					v[num] = -A.value[j];
					num = num + 1;
				}
			}
			rp[i + 1] = num;
		}
		rp[A.rowPtr.length - 1] = rp[A.rowPtr.length - 2];
		A1.rowPtr = rp;
		A1.colInd = ci;
		A1.value = v;
		A1.createMatrix(rp, ci, v);
		return A1;
	}

	// calculate inverse matrix of a diagonal matrix
	public static sparse_mat inverse(sparse_mat A) {
		sparse_mat A1 = new sparse_mat();
		int[] rp = new int[A.rowPtr.length];
		int[] ci = new int[A.rowPtr.length - 1];
		double[] v = new double[A.rowPtr.length - 1];
		int num = 0;

		for (int i = 0; i < A.rowPtr.length - 1; i++) {
			for (int j = A.rowPtr[i]; j < A.rowPtr[i + 1]; j++) {
				if (A.colInd[j] == i) {
					if (A.value[j] != 0) {
						ci[num] = i;// ci[j] = A.colInd[j]
						v[num] = 1 / A.value[j];
						num = num + 1;
					}
					break;// this row has been traversed
				}
			}
			rp[i] = i;
		}
		rp[A.rowPtr.length - 1] = rp[A.rowPtr.length - 2] + 1;

		A1.rowPtr = rp;
		A1.colInd = ci;
		A1.value = v;
		A1.createMatrix(rp, ci, v);
		return A1;
	}
	
	// product of a diagonal matrix and a normal matrix
	public static sparse_mat productmm(sparse_mat A, sparse_mat B, double w) {
		sparse_mat C = new sparse_mat();
		ArrayList<Integer> r = new ArrayList<Integer>();
		ArrayList<Integer> c = new ArrayList<Integer>();
		ArrayList<Double> v = new ArrayList<Double>();
		int num = 0;

		r.add(0);
		for (int i = 0; i < A.a.size(); i++) {
			for (int j = 0; j < A.a.size(); j++) {
				double temp = A.retrieveElement(i, i);
				if (temp != 0) {
					double temp1 = temp * B.retrieveElement(i, j) * w;
					if (temp1 != 0) {
						// save this value and its index in C
						v.add(temp1);
						c.add(j);
						num = num + 1;
					}
				}
			}
			r.add(num);
		}

		int[] rp = new int[r.size()];
		int[] ci = new int[c.size()];
		double[] v1 = new double[v.size()];

		for (int i = 0; i < r.size(); i++) {
			rp[i] = r.get(i);
		}
		for (int i = 0; i < c.size(); i++) {
			ci[i] = c.get(i);
		}
		for (int i = 0; i < v.size(); i++) {
			v1[i] = v.get(i);
		}

		C.rowPtr = rp;
		C.colInd = ci;
		C.value = v1;
		C.createMatrix(rp, ci, v1);
		return C;
	}
	
	//ADD two matrixs
	public static sparse_mat addmm(sparse_mat A, sparse_mat B, double a) {
		// create hashmap<col, val> for each row
		Map<Integer, Double> m1 = new HashMap<>();
		Map<Integer, Double> m2 = new HashMap<>();
		for (int i = 0; i < A.a.size(); i++) {
			m1 = A.a.get(i);
			m2 = B.a.get(i);
			for (int k = 0; k < A.a.size(); k++) {
				// k-th column has non-zero value for both two rows
				if (m1.containsKey(k) && m2.containsKey(k)) {
					double t = m1.get(k) + m2.get(k) * a;
					m1.put(k, t);
				}
				// row(i) doesn't has k-th column but row(j) has this column
				else if (!m1.containsKey(k) && m2.containsKey(k)) {
					m1.put(k, m2.get(k) * a);
				}
			}
			A.a.add(i, m1);
			A.a.remove(i + 1);
		}
		return A;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		sparse_mat A = new sparse_mat();
		int[] rowPtr = { 0, 3, 6, 9, 10, 12 };
		int[] colInd = { 0, 1, 4, 0, 1, 2, 1, 2, 4, 3, 0, 4 };
		double[] value = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 };
		A.rowPtr = rowPtr;
		A.colInd = colInd;
		A.value = value;
		A.createMatrix(rowPtr, colInd, value);

		sparse_mat A1 = dia_matrix(A);
		System.out.println("");
		for (int i = 0; i < A1.a.size(); i++) {
			for (int j = 0; j < A1.a.size(); j++) {
				System.out.print(A1.retrieveElement(i, j) + " ");
			}
			System.out.println("");
		}
		
		sparse_mat A2= inverse(A1);
		for (int i = 0; i < A2.a.size(); i++) {
			for (int j = 0; j < A2.a.size(); j++) {
				System.out.print(A2.retrieveElement(i, j) + " ");
			}
			System.out.println("");
		}
		
		sparse_mat A3 = productmm(A1, A2,2.0);
		for (int i = 0; i < A3.a.size(); i++) {
			for (int j = 0; j < A3.a.size(); j++) {
				System.out.print(A3.retrieveElement(i, j) + " ");
			}
			System.out.println("");
		}
	}

}
