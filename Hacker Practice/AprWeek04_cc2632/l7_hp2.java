/*dates: 4/21/2018
 * authors: Chun Chen
 * Language: Java
 * Platforms: Eclipse in Windows10.
 * */

package hp_lecture7;

import java.util.Arrays;
import assignment2.jacobi;
import hp_lecture4.formula;
import hp_lecture4.lecture4_practice4;
import hm4.sparse_mat;

//hacker practice(1) & (2)
public class l7_hp2 {
	// (1) Solve the homogeneous Poisson equation
	public static double[] direct(double h, double NA, double ND, double BC) {

		int[] rowPtr = { 0, 3, 7, 10, 14, 19, 23, 26, 30, 33 };
		int[] colInd = { 0, 1, 3, 0, 1, 2, 4, 1, 2, 5, 0, 3, 4, 6, 1, 3, 4, 5, 7, 2, 4, 5, 8, 3, 6, 7, 4, 6, 7, 8, 5, 7,
				8 };
		double[] value = { -4, 1, 1, 1, -4, 1, 1, 1, -4, 1, 1, -4, 1, 1, 1, 1, -4, 1, 1, 1, 1, -4, 1, 1, -4, 1, 1, 1,
				-4, 1, 1, 1, -4 };
		sparse_mat A = new sparse_mat();
		A.rowPtr = rowPtr;
		A.colInd = colInd;
		A.value = value;
		A.createMatrix(rowPtr, colInd, value);
		sparse_mat.display(A);

		double temp1 = h * h * NA - 2 * BC;
		double temp2 = -h * h * ND;
		double[] x = { temp1, temp1, temp2, temp2, temp2, temp2, temp2, temp2, temp2 };

		double[] solution = jacobi.solu(A, x);

		return solution;
	}

	// (2) Implement Newton for the nonlinear solution

	// construct sparse(diagonal dominant) matrix
	public static sparse_mat matrix(double[] phi, double BC, double h) {

		double[] temp = new double[phi.length];
		for (int i = 0; i < temp.length; i++) {
			temp[i] = -4 - h * h * (Math.exp(-phi[i]) + Math.exp(phi[i]));
		}

		int[] rowPtr = { 0, 3, 7, 10, 14, 19, 23, 26, 30, 33 };
		int[] colInd = { 0, 1, 3, 0, 1, 2, 4, 1, 2, 5, 0, 3, 4, 6, 1, 3, 4, 5, 7, 2, 4, 5, 8, 3, 6, 7, 4, 6, 7, 8, 5, 7,
				8 };
		double[] value = { temp[0], 1, 1, 1, temp[1], 1, 1, 1, temp[2], 1, 1, temp[3], 1, 1, 1, 1, temp[4], 1, 1, 1, 1,
				temp[5], 1, 1, temp[6], 1, 1, 1, temp[7], 1, 1, 1, temp[8] };

		sparse_mat A = new sparse_mat();
		A.rowPtr = rowPtr;
		A.colInd = colInd;
		A.value = value;
		A.createMatrix(rowPtr, colInd, value);
		sparse_mat.display(A);
		return A;
	}

	// construct derivative equations F1-F9
	// f_v[0] - f_v[8] represents F1-F9
	// phi[0] - phi[8] represents PHI1-PHI9
	public static double[] fi(double[] phi, double BC, double h, double NA, double ND) {

		double[] f_v = new double[phi.length];
		f_v[0] = (phi[1] + phi[0] * (-4) + phi[3] + h * h * (Math.exp(-phi[0]) - Math.exp(phi[0]) - NA) + 2 * BC);
		f_v[1] = (phi[0] + phi[1] * (-4) + phi[2] + phi[4] + h * h * (Math.exp(-phi[1]) - Math.exp(phi[1]) - NA) + BC);
		f_v[2] = (phi[1] + phi[2] * (-4) + phi[5] + h * h * (Math.exp(-phi[2]) - Math.exp(phi[2]) + ND));
		f_v[3] = (phi[0] + phi[3] * (-4) + phi[4] + phi[6] + h * h * (Math.exp(-phi[3]) - Math.exp(phi[3]) - NA));
		f_v[4] = (phi[1] + phi[3] + phi[4] * (-4) + phi[5] + phi[7]
				+ h * h * (Math.exp(-phi[4]) - Math.exp(phi[4]) + ND));
		f_v[5] = (phi[2] + phi[5] * (-4) + phi[4] + phi[8] + h * h * (Math.exp(-phi[5]) - Math.exp(phi[5]) + ND));
		f_v[6] = (phi[3] + phi[6] * (-4) + phi[7] + h * h * (Math.exp(-phi[6]) - Math.exp(phi[6]) + ND));
		f_v[7] = (phi[4] + phi[6] + phi[7] * (-4) + phi[8] + h * h * (Math.exp(-phi[7]) - Math.exp(phi[7]) + ND));
		f_v[8] = (phi[5] + phi[7] + phi[8] * (-4) + h * h * (Math.exp(-phi[8]) - Math.exp(phi[8]) + ND));

		// System.out.println(Arrays.toString(f_v));
		return f_v;
	}

	// Using Newton method to do: delta(x) = inverse(J) * F (Or J*delta(x) = F)
	public static double[] Deltaphi(double[] phi, double BC, double h, double NA, double ND) {
		double[] F = fi(phi, BC, h, NA, ND);
		sparse_mat A = matrix(phi, BC, h);
		double[] solution = jacobi.solu(A, F);
		System.out.println("delta_phi are " + Arrays.toString(solution));
		return solution;
	}

	public static double[] Updatephi(double[] phi, double Deltaphi[]) {
		double[] new_phi = new double[phi.length];
		if (phi.length == Deltaphi.length) {
			for (int i = 0; i < phi.length; i++) {
				new_phi[i] = phi[i] + Deltaphi[i];
			}
		}
		return new_phi;

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		double BC = 1.0;// boundary condition
		double[] phi = { 0, 0, 0, 0, 0, 0, 0, 0, 0 };// initial value

		// parameters
		double ni = 1.5E10;
		double ND = 1E15 / ni;
		double NA = 1E17 / ni;

		double epsilon = 1.03 * Math.pow(10, -12);
		double LD = 2.4E-3;
		double Vt = 2.6 * Math.pow(10, -2);
		double h0 = 1E-5;
		double h = 1E-5 / LD;// normalized h

		// ********Solve homogeneous Poisson equation directly********
		// double[] direct_solu = direct(h0, NA, ND, BC);
		// System.out.println(Arrays.toString(direct_solu));

		// ********Implement Newton for the nonlinear solution********
		double[] F = fi(phi, BC, h, NA, ND);

		double norm = formula.norm2(F);
		double[] deltaphi = new double[phi.length];
		double norm_deltaphi = 1;

		do {
			deltaphi = Deltaphi(phi, BC, h, ND, NA);
			norm_deltaphi = formula.norm2(deltaphi);
			for (int i = 0; i < phi.length; i++) {
				System.out.print("phi" + (i + 1) + "= " + phi[i] + "\t");
			}
			System.out.print("\n");
			phi = Updatephi(phi, deltaphi);
			norm = formula.norm2(phi);
		} while (norm > Math.pow(10, -7) && norm_deltaphi > Math.pow(10, -7));

	}

}
