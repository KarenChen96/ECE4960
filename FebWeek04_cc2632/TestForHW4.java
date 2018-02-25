package hacker_practice;

public class TestForHW4 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// (1) Test for row permute£ºPermutation of (1, 3) and then (1, 5).
		int[] rowPtr = { 0, 3, 6, 9, 10, 12 };
		int[] colInd = { 0, 1, 4, 0, 1, 2, 1, 2, 4, 3, 0, 4 };
		double[] value = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 };
		double[] x = { 5, 4, 3, 2, 1 };

		System.out.println("Permutation of (1, 3)");
		sparse_mat A11 = new sparse_mat();
		A11.createMatrix(rowPtr, colInd, value);
		A11 = Row_permute.rowPermute(A11, x, 0, 2);
		System.out.println("The result of row permute (1, 3) of a sparse matrix is: ");
		for (int i = 0; i < A11.a.size(); i++) {
			for (int j = 0; j < A11.a.size(); j++) {
				System.out.print(A11.retrieveElement(i, j) + " ");
			}
			System.out.println("");
		}
		
		System.out.println("");
		System.out.println("Permutation of (1, 5)");
		System.out.println("The result of row permute (1, 3) of a full matrix is: ");
		sparse_mat A21 = new sparse_mat();
		A21 = Row_permute.full_rowPermute(A11, x, 0, 2);
		for (int i = 0; i < A11.a.size(); i++) {
			for (int j = 0; j < A11.a.size(); j++) {
				System.out.print(A21.retrieveElement(i, j) + " ");
			}
			System.out.println("");
		}
		
		sparse_mat A12 = new sparse_mat();
		A12.createMatrix(rowPtr, colInd, value);
		A12 = Row_permute.rowPermute(A11, x, 0, 4);
		System.out.println("The result of row permute (1, 5) of a sparse matrix is: ");
		for (int i = 0; i < A11.a.size(); i++) {
			for (int j = 0; j < A11.a.size(); j++) {
				System.out.print(A11.retrieveElement(i, j) + " ");
			}
			System.out.println("");
		}
		
		System.out.println("");
		System.out.println("The result of row permute (1, 5) of a full matrix is: ");
		sparse_mat A22 = new sparse_mat();
		A22 = Row_permute.full_rowPermute(A11, x, 0, 4);
		for (int i = 0; i < A12.a.size(); i++) {
			for (int j = 0; j < A12.a.size(); j++) {
				System.out.print(A22.retrieveElement(i, j) + " ");
			}
			System.out.println("");
		}

		//(2) Test for row scaling: Test 3.0*row[1] + row [4] and -4.4*row[5] + row[2].
		System.out.println("");
		System.out.println("Test 3.0*row[1] + row [4]");
		sparse_mat A3 = new sparse_mat();
		A3.createMatrix(rowPtr, colInd, value);
		A3 = Row_scaling.rowScaling(A3, x, 3, 0, 3.0);
		System.out.println("The result of row scaling of a sparse matrix is: ");
		for (int i = 0; i < A3.a.size(); i++) {
			for (int j = 0; j < A3.a.size(); j++) {
				System.out.print(A3.retrieveElement(i, j) + " ");
			}
			System.out.println("");
		}

		System.out.println("");
		System.out.println("The result of row sacling of a full matrix is: ");
		sparse_mat A4 = new sparse_mat();
		A4 = Row_scaling.full_rowScaling(A3, x, 3, 0, 3.0);
		for (int i = 0; i < A3.a.size(); i++) {
			for (int j = 0; j < A3.a.size(); j++) {
				System.out.print(A4.retrieveElement(i, j) + " ");
			}
			System.out.println("");
		}
		
		System.out.println("");
		System.out.println("Test -4.4*row[5] + row[2]");
		sparse_mat A32 = new sparse_mat();
		A32.createMatrix(rowPtr, colInd, value);
		A32 = Row_scaling.rowScaling(A32, x, 1, 4, -4.4);
		System.out.println("The result of row scaling of a sparse matrix is: ");
		for (int i = 0; i < A32.a.size(); i++) {
			for (int j = 0; j < A32.a.size(); j++) {
				System.out.print(A32.retrieveElement(i, j) + " ");
			}
			System.out.println("");
		}

		System.out.println("");
		System.out.println("The result of row sacling of a full matrix is: ");
		sparse_mat A42 = new sparse_mat();
		A42 = Row_scaling.full_rowScaling(A32, x, 1, 4, -4.4);
		for (int i = 0; i < A32.a.size(); i++) {
			for (int j = 0; j < A32.a.size(); j++) {
				System.out.print(A42.retrieveElement(i, j) + " ");
			}
			System.out.println("");
		}
		System.out.println("");

		// (3) Test for productAx: Ax=b: Test Ax = b
		sparse_mat A5 = new sparse_mat();
		A5.createMatrix(rowPtr, colInd, value);
		double[] b = new double[5];
		Row_productAx.productAx(A5, x, b);
		System.out.println("The vector product of using row-compressed storage is: ");
		for (int i = 0; i < b.length; i++) {
			System.out.print(b[i] + " ");
		}
		System.out.println("");

		double[] b1 = new double[5];
		Row_productAx.full_productAx(A5, x, b1);
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
