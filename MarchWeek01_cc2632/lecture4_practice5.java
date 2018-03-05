/*dates: 2/18/2018
 * authors: Chun Chen
 * Language: Java
 * Platforms: Eclipse in Windows10.
 * */

/*Calculate the upper bounds of two norms 
 * in the full-matrix and sparse-matrix formats.  
*/
package hacker_practice;

public class lecture4_practice5 {

	public static double norm1_full(double[][] a) {
		double maxSum = 0;
		double curSum = 0;
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[i].length; j++) {
				curSum = curSum + a[j][i];
			}
			if (curSum > maxSum) {
				maxSum = curSum;
			}
			curSum = 0;
		}
		double norm = maxSum;
		return norm;
	}

	public static double norm1_sparse(sparse_mat A) {
		double maxSum = 0;
		double curSum = 0;
		for (int i = 0; i < A.a.size(); i++) {
			for (int j = 0; j < A.a.size(); j++) {
				curSum = curSum + A.retrieveElement(j, i);
			}
			if (curSum > maxSum) {
				maxSum = curSum;
			}
			curSum = 0;
		}
		double norm = maxSum;
		return norm;
	}

	public static double normInf_full(double[][] a) {
		double maxSum = 0;
		double curSum = 0;
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[i].length; j++) {
				curSum = curSum + a[i][j];
			}
			if (curSum > maxSum) {
				maxSum = curSum;
			}
			curSum = 0;
		}
		double norm = maxSum;
		return norm;
	}

	public static double normInf_sparse(sparse_mat A) {
		double maxSum = 0;
		double curSum = 0;
		for (int i = 0; i < A.a.size(); i++) {
			for (int j = 0; j < A.a.size(); j++) {
				curSum = curSum + A.retrieveElement(j, i);
			}
			if (curSum > maxSum) {
				maxSum = curSum;
			}
			curSum = 0;
		}
		double norm = maxSum;
		return norm;
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
		double[][] a = { { 1, 2, 0, 0, 3 }, { 4, 5, 6, 0, 0 }, { 0, 7, 8, 0, 9 }, { 0, 0, 0, 10, 0 },
				{ 11, 0, 0, 0, 12 } };

		double norm1 = norm1_full(a);
		double normInf = normInf_full(a);
		System.out.println(norm1);
		System.out.println(normInf);

		double norm1_sp = norm1_sparse(A);
		double normInf_sp = norm1_sparse(A);
		System.out.println(norm1_sp);
		System.out.println(normInf_sp);
	}

}
