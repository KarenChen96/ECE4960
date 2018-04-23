/*dates: 4/21/2018
 * authors: Chun Chen
 * Language: Java
 * Platforms: Eclipse in Windows10.
 * */

package hp_lecture7;

import hm4.sparse_mat;
import assignment2.jacobi;
import hp_lecture4.formula;

public class l7_hp2 {
	//hacker practice(1) & (2)
	
	//construct sparse(diagonal dominant) matrix		
	public static sparse_mat matrix(double[] phi, double phi0, double h) {
//		int[] rowPtr = { 0, 3, 6, 9, 10, 12 };
//		int[] colInd = { 0, 1, 4, 0, 1, 2, 1, 2, 4, 3, 0, 4 };
//		double[] value = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 };
//		double[] x = { 5, 4, 3, 2, 1 };
		
		double[] temp = new double[phi.length];
		for (int i = 0; i<temp.length; i++) {
			temp[i] = -4-h*h*(Math.exp(-phi[i])+Math.exp(phi[i]));
		}
		
		int[] rowPtr = {0, 3, 7, 10, 14, 19, 23, 26, 30, 33};
		int[] colInd = {0,1,3, 0,1,2,4, 1,2,5, 0,3,4,6, 1,3,4,5,7, 2,4,5,8, 3,6,7, 4,6,7,8, 5,7,8};
		double[] value = {temp[0],1,1, 1,temp[1],1,1, 1,temp[2],1, 1,temp[3],1,1, 1,1,temp[4],1,1, 1,1,temp[5],1, 1,temp[6],1, 1,1,temp[7],1, 1,1,temp[8]};

		sparse_mat A = new sparse_mat();
		A.rowPtr = rowPtr;
		A.colInd = colInd;
		A.value = value;
		A.createMatrix(rowPtr, colInd, value);
		return A;
	}
	

	//construct derivative equations F1-F9
	//f_v[0] - f_v[8] represents F1-F9
	//phi[0] - phi[8] represents PHI1-PHI9
	public static double[] fi(double[] phi, double phi0, double h, double ND, double NA) {

//		ArrayList<Double> f_v= new ArrayList<Double>();
		double[] f_v = new double[phi.length];
		f_v[0] = (phi[1]+phi[0]*(-4)+phi[3]+h*h*(Math.exp(-phi[0])-Math.exp(phi[0])+ND)+2*phi0); 
		f_v[1] = (phi[0]+phi[1]*(-4)+phi[2]+phi[4]+h*h*(Math.exp(-phi[1])-Math.exp(phi[1])+ND)+phi0);
		f_v[2] = (phi[1]+phi[2]*(-4)+phi[5]+h*h*(Math.exp(-phi[2])-Math.exp(phi[2])-NA));
		f_v[3] = (phi[0]+phi[3]*(-4)+phi[4]+phi[6]+h*h*(Math.exp(-phi[3])-Math.exp(phi[3])+ND));
		f_v[4] = (phi[1]+phi[3]+phi[4]*(-4)+phi[5]+phi[7]+h*h*(Math.exp(-phi[4])-Math.exp(phi[4])-NA));
		f_v[5] = (phi[2]+phi[5]*(-4)+phi[4]+phi[8]+h*h*(Math.exp(-phi[5])-Math.exp(phi[5])-NA));
		f_v[6] = (phi[3]+phi[6]*(-4)+phi[7]+h*h*(Math.exp(-phi[6])-Math.exp(phi[6])-NA));
		f_v[7] = (phi[4]+phi[6]+phi[7]*(-4)+phi[8]+h*h*(Math.exp(-phi[7])-Math.exp(phi[7])-NA));
		f_v[8] = (phi[5]+phi[7]+phi[8]*(-4)+h*h*(Math.exp(-phi[8])-Math.exp(phi[8])-NA));

		return f_v;
	} 
	
	
	public static double[] Deltaphi(double[] phi, double phi0, double h, double ND, double NA) {
		double[] F = fi(phi, phi0, h, ND, NA);
		sparse_mat A = matrix(phi, phi0, h);
		double[] solution = jacobi.solu(A, F);
		return solution;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
//		double Nd = Math.pow(10, 15);
//		double Na = Math.pow(10, 17);
		double phi0 = 1.0;
		double[] phi= {0,0,0,0,0,0,0,0,0};

		double ni= 1.5E10;
		double ND= 1E15/ni;
		double NA= 1E17/ni;

		double ephilon = 1.03 * Math.pow(10, -12);
		double LD = 2.4E-3;
		double Vt = 2.6 * Math.pow(10, -2);
		double h= 1E-5/LD;

		double[] deltaphi= new double[phi.length];
		double norm_deltaphi= 1;

//		do {
//
//		}while(norm>Math.pow(10, -7)&&norm_deltaphi>Math.pow(10, -7));
	}

}
