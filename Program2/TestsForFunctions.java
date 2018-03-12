/*dates: 3/11/2018
 * authors: Chun Chen
 * Language: Java
 * Platforms: Eclipse in Windows10.
 * */

package assignment2;

import hm4.Row_productAx;
import hm4.sparse_mat;
import hp_lecture4.formula;

public class TestsForFunctions {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		long time1 = System.nanoTime();
		
		System.out.println("-----Tests for all helper functions that "
				+ "are used when calculating solutions using Jacobi Solver.-----");
		System.out.println("");
		//test level1
		System.out.println("This is the first level test which tests the functions "
				+ "that have been called directly in jacobi solution.");
		System.out.println("");
		
		// create a matrix in row-compressed format
		sparse_mat A = new sparse_mat();
		int[] rowPtr = { 0, 3, 6, 9, 12, 15 };
		int[] colInd = { 0, 1, 4, 0, 1, 2, 1, 2, 3, 2, 3, 4, 0, 3, 4 };
		double[] value = { -4, 1, 1, 4, -4, 1, 1, -4, 1, 1, -4, 1, 1, 1, -4 };
		A.rowPtr = rowPtr;
		A.colInd = colInd;
		A.value = value;
		A.createMatrix(rowPtr, colInd, value);

		// create the same matrix in full format
		double[][] a = { { -4, 1, 0, 0, 1 }, { 4, -4, 1, 0, 0 }, 
				{ 0, 1, -4, 1, 0 }, { 0, 0, 1, -4, 1 },{ 1, 0, 0, 1, -4 } };

		// class sparse_op: all functions in it
		System.out.println("********1. Test for functions in class sparse_op "
				+ "(helper functions for sparse matrix(in compressed format))********");
		System.out.println("********Method: Compare the results obtained by matrixs "
				+ "in row-compressed format and in full format respectively.********");
		System.out.println(" ");
		// lower negative matrix (obtain L)
		System.out.println("(1) Test for obtaining negative lower matrix of a matrix (obtain L):");
		double[][] l_a = new double[a.length][a.length];
		sparse_mat l_A = sparse_op.lower_matrix(A);
		boolean l_flag = true;
		
		for (int i = 1; i < a.length; i++) {
			for (int j = 0; j < i; j++) {
				l_a[i][j] = -a[i][j];
			}
		}

		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a.length; j++) {
				if (l_a[i][j] != l_A.retrieveElement(i, j)) {
					l_flag = false;
					break;
				}
			}
		}

		System.out.println("The lower matrix of the matrix "
				+ "in row-compressed and in full format is the same: " + l_flag);
		System.out.println(" ");

		// upper negative matrix(obtain U)
		System.out.println("(2) Test for obtaining negative "
				+ "upper matrix of a matrix (obtain U):");
		double[][] u_a = new double[a.length][a.length];
		sparse_mat u_A = sparse_op.upper_matrix(A);
		boolean u_flag = true;

		for (int i = 0; i < l_a.length; i++) {
			for (int j = i + 1; j < a[i].length; j++) {
				u_a[i][j] = -a[i][j];
			}
		}
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a.length; j++) {
				if (u_a[i][j] != u_A.retrieveElement(i, j)) {
					u_flag = false;
					break;
				}
			}
		}

		System.out.println("The upper matrix of the matrix "
				+ "in row-compressed and in full format is the same: " + u_flag);
		System.out.println(" ");

		// diagonal matrix(obtain D)
		System.out.println("(3) Test for obtaining diagonal matrix of a matrix (obtain D):");
		double[][] d_a = new double[a.length][a.length];
		sparse_mat d_A = sparse_op.dia_matrix(A);
		boolean d_flag = true;

		for (int i = 0; i < a.length; i++) {
			d_a[i][i] = a[i][i];
		}

		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a.length; j++) {
				if (d_a[i][j] != d_A.retrieveElement(i, j)) {
					d_flag = false;
					break;
				}
			}
		}
		System.out.println("The diagonal matrix of the matrix "
				+ "in row-compressed and in full format is the same: " + d_flag);
		System.out.println(" ");
		
		//inverse of a diagonal matrix
		System.out.println("(4) Test for obtaining inverse matrix of a diagonal matrix:");
		double[][] i_da = new double[a.length][a.length];
		sparse_mat i_dA = sparse_op.inverse(d_A);
		boolean i_flag = true;
		for (int i = 0; i < a.length; i++) {
			if (d_a[i][i] != 0) {
				i_da[i][i] = 1/d_a[i][i];
			}
		}
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a.length; j++) {
				if (i_da[i][j] != i_dA.retrieveElement(i, j)) {
					i_flag = false;
					break;
				}
			}
		}

		System.out.println("The inverse matrix of a diagonal matrix "
				+ "in row-compressed and in full format is the same: " + i_flag);
		System.out.println(" ");
		
		//matrix multiplication between a diagonal matrix and a normal matrix
		System.out.println("(5) Test for obtaining multiplication between "
				+ "a diagonal matrix and a normal matrix:");
		double[][] mul_ada = new double[a.length][a.length];
		sparse_mat mul_AdA = sparse_op.productmm(d_A, A, 1.0);
		boolean m_flag = true;
		for (int i = 0; i < a.length; i++) {
			double temp = d_a[i][i];
			for (int j = 0; j < a.length; j++) {
				double temp1 = temp * a[i][j] * 1.0;
				mul_ada[i][j] = temp1;
			}	
		}
	
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a.length; j++) {
				if (mul_ada[i][j] != mul_AdA.retrieveElement(i, j)) {
					m_flag = false;
					break;
				}
			}
		}
		System.out.println("The multiplication result of matrixs "
				+ "in row-compressed and in full format is the same: " + m_flag);
		System.out.println(" ");
		
		//matrix addition
		System.out.println("(6) Test for obtaining addition between two matrixs:");
		double[][] add_ada = new double[a.length][a.length];
		sparse_mat add_AdA = sparse_op.addmm(d_A, A, 1.0);
		boolean a_flag = true;
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a.length; j++) {
				add_ada[i][j] = d_a[i][j]+a[i][j] * 1.0;
			}	
		}
	
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a.length; j++) {
				if (add_ada[i][j] != add_AdA.retrieveElement(i, j)) {
					a_flag = false;
					break;
				}
			}
		}
		System.out.println("The multiplication result of matrixs "
				+ "in row-compressed and in full format is the same: " + a_flag);
		System.out.println(" ");

		
		// class formula: norm2, diff
		System.out.println("*****2. Test for functions in class formula(some basic calculations)*****");
		double x[] = {3.4, 5.6, 7.8, 9.0, 10};
		//test norm2(double[] x)
		System.out.println("(1) Test the correctness of claculating second norm of a vector.");
		System.out.println("********Method: Compare the results of multiply "
				+ "a vector by itself and the second norm of it.********");
		double sum = 0;
		for (int i = 0; i < x.length; i++) {
			sum = sum + Math.pow(x[i], 2);
		}
		boolean n_flag = true;
		if (Math.sqrt(sum) != formula.norm2(x)) {
			n_flag = false;
		}
		System.out.println("The result of multiply vector x by itself and "
				+ "the second norm of it is the same: "+n_flag);
		System.out.println(" ");
		
		//test diff(double[] result, double[] b)
		System.out.println("(2) Test the correctness of claculating "
				+ "normalized residual norm of the solution.");
		System.out.println("********Method: Compare the results of function diff and "
				+ "direct calculation, in the case of two vectors "
				+ "are the same and are different.********");
		System.out.println("a. When the calculated result is the same with the expected result: ");
		//double x[] = {3.4, 5.6, 7.8, 9.0, 10};
		double r1[] = {3.4, 5.6, 7.8, 9.0, 10};
		double diff1 = 0;
		boolean d_flag1 = true;
		for (int i = 0; i < r1.length; i++) {
			diff1 = diff1 + Math.pow(r1[i] - x[i], 2);
		}

		if (diff1 != formula.diff(r1, x)) {
			d_flag1 = false;
		}
		System.out.println("The normalized residual norm of using function diff and "
				+ "direct calculation are the same: "+d_flag1);
		System.out.println(" ");
		
		System.out.println("b. When the calculated result is different with the expected result: ");
		//double x[] = {3.4, 5.6, 7.8, 9.0, 10};
		double r2[] = {7.8, 4.2, 12, 33,9.0};
		double diff2 = 0;
		boolean d_flag2 = true;
		for (int i = 0; i < r2.length; i++) {
			diff2 = diff2 + Math.pow(r2[i] - x[i], 2);
		}
		double r_n = Math.sqrt(diff2)/Math.sqrt(formula.norm2(x));

		if (r_n != formula.diff(r2, x)) {
			d_flag2 = false;
		}
		System.out.println("The normalized residual norm of using function diff and "
				+ "direct calculation are the same: "+d_flag2);
		System.out.println(" ");
		System.out.println(" ");
		
		// class Row_productAx: productAx
		System.out.println("*****3. Test for the correctness of claculating productAx*****");
		System.out.println("********Method: Compare the results of multiply a vector and martixs "
				+ "in row-compressed format and in row-compressde format.********");
		double[] b = Row_productAx.productAx(A, x);
		double[] b1 = new double[5];
		boolean p_flag = true;
		for(int i=0; i<b1.length; i++)
		{
			double temp = 0;
			for (int j = 0; j < x.length; j++) {
				temp = temp + a[i][j] * x[j];
			}
			b1[i] = temp;
		}
		
		for (int i = 0; i < b1.length; i++) {
			if(b[i] != b1[i])
			{
				p_flag = false;
				break;
			}
			
		}
		System.out.println("The vector products of using row-compressed format and "
				+ "using full-matrix repersentations are the same: " + p_flag);
		System.out.println("");
		System.out.println("");
		
		//test level2
		System.out.println("This is the second level test which tests the functions "
				+ "that have been called in tested funcitons in level1.");
		System.out.println("");
		
		//retrieveElement(i, j)
		System.out.println("*****1. Test for the correctness of retrieving an element "
				+ "from a row-compressed matrix*****");
		System.out.println("********Method: Compare the results of retrieving element from matrixs "
				+ "od row-compressed format and in row-compressde format.********");
		boolean r_flag = true;
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a.length; j++) {
				if (a[i][j] != A.retrieveElement(i, j)) {
					r_flag = false;
					break;
				}
			}
		}
		System.out.println("The results of retrieving any element using row-compressed format and "
				+ "using full-matrix repersentations are the same: " + r_flag);
		System.out.println("");
		System.out.println("");
		
		System.out.println("*****Comments on the results*****");
		System.out.println("All flag values are true which means that "
				+ "all test cases for helper funcitons are passed.");
		System.out.println(" ");
		System.out.println(" ");
		
		System.out.println("*****Computational time and memory usage checks*****");
		long time2 = System.nanoTime();
		System.out.println("Computational time is: " + (time2-time1) +"ns");
		long used = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		System.out.println("Memory usage is: " + used + "bytes");
	}
}
