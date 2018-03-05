/*dates: 2/18/2018
 * authors: Chun Chen
 * Language: Java
 * Platforms: Eclipse in Windows10.
 * */

/*Use the full matrix format and only row permutation 
 * perform the minimal fill-in algorithm for choosing the sequence of pivoting 
 * to solve Ax=b.
*/

package hacker_practice;

public class lecture4_practice4 {
	// using a-th row to do row scaling to the whole matrix A
	public static int numOfFillin(int a, int b, double[][] A) {
		int num = 0;
		double[] slave = A[a];
		for (int i = a; i < A.length; i++) {
			if (slave[b] != 0) {
				double weight = A[i][b] / slave[b];// how much to scale
				double[] temp = new double[slave.length];
				for (int j = 0; j < A.length; j++) {
					temp[j] = A[i][j] + slave[j] * weight;// scaling
					if (A[i][j] == 0 && temp[j] != 0) {
						num = num + 1;
					}
				}
			} else {
				num = -1;// if this element is 0, then won't use it as pivot
			}
		}
		return num;
	}

	// find the pivot of a-th row of matrix A
	public static int pivot(int a, double[][] A) {
		int index = 0;
		double[] slave = A[a];
		for (int i = 1; i < slave.length; i++) {
			if (slave[i] != 0) {
				int num1 = numOfFillin(a, index, A);
				int num2 = numOfFillin(a, i, A);
				if ((num2 < num1) || (num1 < 0 && num2 >= 0)) { // when i-th row has smaller fill-ins or the element in
																// index-th row is 0
					index = i;
				}
			}
		}
		return index;
	}

	/* Calculate sparsity of row r */
	public static int sparsity(double[] r) {
		int s = 0;
		for (int i = 0; i < r.length; i++)
			if (r[i] == 0) {
				s = s + 1;
			}
		return s;
	}

	/* find the maximal sparisty from row a (v[]) to the end */
	public static int max(int[] v, int a) {
		double max = 0;
		int index = -1;
		for (int i = a; i < v.length; i++) {
			if (v[i] > max) {
				max = v[i];
				index = i;
			}
		}
		return index;
	}

	public static void changeVec(double[] x, int a, int b) {
		double temp1 = x[a];
		double temp2 = x[b];
		x[a] = temp2;
		x[b] = temp1;
	}

	public static void scaleVec(double[] x, int a, int b, double weight) {
		double temp2 = x[b];
		x[a] = x[a] + weight * temp2;
	}

	/*
	 * change this matrix to upper triangle matrix, and do according change to the
	 * vector
	 */
	public static void tri_matrix(double[][] m, double[] b) {
		for (int i = 0; i < m.length; i++) {
			if (m[i][i] == 0) {
				for (int j = i + 1; j < m.length; j++) {
					if (m[j][i] != 0) {
						m = Row_permute.full_rowPermute(m, i, j);
						changeVec(b, i, j);
						break;
					}
				}
			}
		}
	}

	/* When m is a upper triangle matrix, 
	 * using the backward substitution to obtain the solution of mx=b */
	public static double[] calSolu(double[][] m, double[] b) {
		double[] x = new double[m.length];
		x[m.length - 1] = b[m.length - 1] / m[m.length - 1][m.length - 1];
		for (int i = m.length - 2; i >= 0; i--) {
			double temp = 0;
			for (int j = i + 1; j < m.length; j++) {
				temp = temp + x[j] * m[i][j];
			}
			x[i] = (b[i] - temp) / m[i][i];
		}
		return x;
	}
	
	/*Integrate all functions to obtain the solution of Ax=b
	 *Algo: From k-th row to the end, calculate sparsity of each row,and find the maximal one. 
	 *Change the row having the maximal sparsity with k-th row (do the same row permutation for vector b)
	 *Then begin to find the pivot of this row(current k-th row).
	 *Finally, do triangle to the matrix and calculate the solution.
	 * */
	public static double[] solution(double[][] M, int[] a, double[] b)
	{
		for (int k = 0; k < M.length; k++)// optimize p-th row
		{
			//find the maximal sparsity
			for (int i = k; i < M.length; i++) {
				int s = sparsity(M[i]);
				a[i] = s;
			}
			int maxSparRow = max(a, k);
			if (k != maxSparRow) // rows that below k-th have larger sparsity
			{
				M = Row_permute.full_rowPermute(M, k, maxSparRow);
				changeVec(b, k, maxSparRow);
			}

			// begin to find the pivot of this row, weight is how much to scale
			double[] slave = M[k];
			int p = pivot(k, M);// index of pivot in this row(k-th row)
			for (int j = k + 1; j < M.length; j++) {
				if (slave[p] != 0) {
					double weight = -(M[j][p] / slave[p]);
					if (weight != 0) {
						M = Row_scaling.full_rowScaling(M, j, k, weight);
						scaleVec(b, j, k, weight);
					}
				}
			}
		}

		tri_matrix(M, b);
		double[] solu = calSolu(M, b);
		return solu;
	}
	
	/*test the solution's correctness*/
	public static double[] result(double[][] m, double[] x)
	{
		double[] result = new double [m.length];
		for (int i = 0; i<m.length; i++)
		{
			for (int j = 0; j<m.length; j++)
			{
				result[i] = result[i] + m[i][j] * x[j];
			}
		}
		return result;
	}
	
	/*Calculate the difference between the result obtained by Ax and the real value of b*/
	public static double diff(double[] result, double[] b)
	{
		double diff = 0;
		for (int i = 0; i < result.length; i++) {
				diff = diff + Math.pow(result[i] - b[i],2);
			}
		return Math.sqrt(diff);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//initialization
		int[] rowPtr = { 0, 3, 6, 9, 10, 12 };
		int[] colInd = { 0, 1, 4, 0, 1, 2, 1, 2, 4, 3, 0, 4 };
		double[] value = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 };
		sparse_mat A = new sparse_mat();
		A.createMatrix(rowPtr, colInd, value);
		A.rowPtr = rowPtr;
		A.colInd = colInd;
		A.value = value;
		double[][] M = A.full_m(rowPtr, colInd, value);
		double[][] M1 = A.full_m(rowPtr, colInd, value);
		double[] b = { 5, 4, 3, 2, 1 };
		int[] a = new int[M.length];

		double[] solu = solution(M,a,b);
		double[] result = result(M1, solu);
		double diff = diff(result, b);

		/*// print out M
		for (int i = 0; i < M.length; i++) {
			for (int j = 0; j < M.length; j++) {
				System.out.print(M[i][j] + " ");
			}
			System.out.println("");
		}
		System.out.println(" ");
		
		//result
		for (int i = 0; i < result.length; i++) {
				System.out.print(result[i] + " ");
			System.out.println("");
		}
		System.out.println(" ");

		// print out solution
		for (int i = 0; i < b.length; i++) {
			System.out.println(solu[i] + "");
		}*/
		
	}

}
