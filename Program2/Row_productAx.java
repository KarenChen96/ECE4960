/*dates: 2/18/2018
 * authors: Chun Chen
 * Language: Java
 * Platforms: Eclipse in Windows10.
 * */
package hm4;

import java.util.ArrayList;

/*Use the row-compressed storage and the full-matrix representations 
 * to implement the vector product 
 */
public class Row_productAx {
	// Compute the product of Ax = b
	public static double[] productAx(sparse_mat A, double[] x) {
		double[] b = new double[x.length];
		for (int i = 0; i < x.length; i++) {
			double sum = 0;
			for (int j = 0; j < x.length; j++) {
				sum = sum + A.retrieveElement(i, j) * x[j];
			}
			b[i] = sum;
		}
		return b;
	}

	// Compute the product of Ax = b when A is full_matrix
	public static void full_productAx(double[][] m, double[] x, double[] b) {
		for (int i = 0; i < x.length; i++) {
			double sum = 0;
			for (int j = 0; j < x.length; j++) {
				sum = sum + m[i][j] * x[j];
			}
			b[i] = sum;
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		sparse_mat A = new sparse_mat();
		int[] rowPtr = {0,3,6,9,10,12};
		int[] colInd = {0,1,4,0,1,2,1,2,4,3,0,4};
		double[] value = {1,2,3,4,5,6,7,8,9,10,11,12};
		A.rowPtr = rowPtr;
		A.colInd = colInd;
		A.value = value;
		A.createMatrix(rowPtr,colInd,value);
		double[][] full_m = A.full_m(rowPtr, colInd, value);
		double[] x = { 5, 4, 3, 2, 1 };
		
		double[] b = productAx(A, x);
		System.out.println("The vector product of using row-compressed storage is: ");
		for (int i = 0; i < b.length; i++) {
			System.out.print(b[i] + " ");
		}
		System.out.println("");

		double[] b1 = new double[5];
		full_productAx(full_m, x, b1);
		System.out.println("The vector product of using full-matrix repersentations is: ");
		for (int i = 0; i < b1.length; i++) {
			System.out.print(b1[i] + " ");
		}

		// compare the result of row-compressed storage and full_matrix
		// element-by-element
		System.out.println(" ");
		System.out.println(" ");
		int flag = 1;
		for (int i = 0; i < b.length; i++) {
			if (b[i] != b1[i]) {
				flag = -1;
			}
		}
		if (flag > 0) {
			System.out.println("The two resulting vectors are the same.");
		} else {
			System.out.println("The two resulting vectors are different.");
		}		
	}
}
