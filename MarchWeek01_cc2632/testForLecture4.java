/*dates: 3/4/2018
 * authors: Chun Chen
 * Language: Java
 * Platforms: Eclipse in Windows10.
 * */

/*test for practice3, practice4, practice5 in lecture4 */

package hacker_practice;

public class testForLecture4 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//test for practice3
		double a1 = 100;
		double b1 = 99;
		double a2 = 99;
		double b2 = 98.01;
		double c1 = 199;
		double c2 = 197;
		double[] vx = new double[8];
		double[] vy = new double[8];
		double e = Math.pow(10, -2);
		//obtain solutions set
		for (int i = 0; i < 8; i++) {
			b2 = 98.01 - e;
			vx[i] = lecture4_practice3.cal_y(a1, b1, c1, a2, b2, c2);
			vy[i] = lecture4_practice3.cal_x(a1, b1, c1, a2, b2, c2);
			System.out.print(vx[i] + " ");
			System.out.println(vy[i]);
			System.out.println(" ");
			e = e * (0.1);
		}
		
		e = Math.pow(10, -2);
		int[] rowPtr0 = {0,2,4};
		int[] colInd0 = {0,1,0,1};
		double[] r = {199,197};
		
		for (int i = 0; i < 8; i++) {
			double[] value0 = { 100, 99, 99, 98.01-e};
			sparse_mat A = new sparse_mat();
			A.createMatrix(rowPtr0, colInd0, value0);
			double[] b = new double[2];
			double[] x = { vx[i], vy[i] };
			Row_productAx.productAx(A, x, b);
			double diff = 0.0;
			for (int j = 0; j < A.a.size(); j++) {
				//System.out.println(b[j]);
				diff = diff + TestForHW4.sec_norm(b[j], r[j]);
			}
			System.out.println(diff);
		}
		e = e * (0.1);
		
		
		//test for practice4
		// initialization
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
		double[] bb = { 5, 4, 3, 2, 1 };
		int[] a = new int[M.length];

		double[] solu = lecture4_practice4.solution(M, a, b);
		double[] result = lecture4_practice4.result(M1, solu);
		double diff = lecture4_practice4.diff(result, bb);
		System.out.println(diff);
		
		//test for practice5: 
		sparse_mat A1 = new sparse_mat();
		A1.rowPtr = rowPtr;
		A1.colInd = colInd;
		A1.value = value;
		A1.createMatrix(rowPtr, colInd, value);
		double[][] m = { { 1, 2, 0, 0, 3 }, { 4, 5, 6, 0, 0 }, { 0, 7, 8, 0, 9 }, { 0, 0, 0, 10, 0 },
				{ 11, 0, 0, 0, 12 } };

		double norm1 = lecture4_practice5.norm1_full(m);
		double normInf = lecture4_practice5.normInf_full(m);
		System.out.println(norm1);
		System.out.println(normInf);

		double norm1_sp = lecture4_practice5.norm1_sparse(A1);
		double normInf_sp = lecture4_practice5.norm1_sparse(A1);
		System.out.println(norm1_sp);
		System.out.println(normInf_sp);
	}

}
