/*dates: 3/11/2018
 * authors: Chun Chen
 * Language: Java
 * Platforms: Eclipse in Windows10.
 * */

package assignment2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import hm4.Row_productAx;
import hm4.sparse_mat;
import hp_lecture4.formula;
import assignment2.jacobi;

public class mainTest {
	//read file for rowPtr and colInd
	public static int[] readFile(String filePath) {
		ArrayList<Integer> a = new ArrayList<Integer>();
		try {
			File file = new File(filePath);
			if (file.isFile() && file.exists()) {
				InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "utf-8");
				BufferedReader br = new BufferedReader(isr);
				String line = null;
				while ((line = br.readLine()) != null) {
					int ind = Integer.parseInt(line)-1;
					a.add(ind);
				}
				br.close();
			} else {
				System.out.println("The file doesn't exist!");
			}
		} catch (Exception e) {
			System.out.println("Error occurs when reading the file!");
		}
		
		int[] b = new int[a.size()];
		for (int i = 0; i < a.size(); i++) {
			b[i] = a.get(i);
		}
		return b;
	}
	
	//read file for value in double format
	public static double[] readFile_double(String filePath) {
		ArrayList<Double> v = new ArrayList<Double>();
		try {
			File file = new File(filePath);
			if (file.isFile() && file.exists()) {
				InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "utf-8");
				BufferedReader br = new BufferedReader(isr);
				String line = null;
				while ((line = br.readLine()) != null) {
					double ind = Double.parseDouble(line);
					v.add(ind);
				}
				br.close();
			} else {
				System.out.println("The file doesn't exist!");
			}
		} catch (Exception e) {
			System.out.println("Error occurs when reading the file!");
			//System.out.println(e.getMessage());
		}
		
		double[] b = new double[v.size()];
		for (int i = 0; i < v.size(); i++) {
			b[i] = v.get(i);
		}
		return b;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//read files	
		String filepath1 = "E:\\JAVA 安装及内容\\workspace\\hacker_practice\\src\\assignment2\\rowPtr.csv";
		int[] rp = readFile(filepath1);
		String filepath2 = "E:\\JAVA 安装及内容\\workspace\\hacker_practice\\src\\assignment2\\colInd.csv";
		int[] ci = readFile(filepath2);
		String filepath3 = "E:\\JAVA 安装及内容\\workspace\\hacker_practice\\src\\assignment2\\value.csv";
		double[] vl = readFile_double(filepath3);

		//create sparse matrix and do test
		sparse_mat A = new sparse_mat();
		A.rowPtr = rp;
		A.colInd = ci;
		A.value = vl;
		A.createMatrix(rp, ci, vl);
		
		double[] b1 = new double[A.a.size()];// all 0
		b1[0] = 1.0;
		double[] b2 = new double[A.a.size()];
		b2[4] = 1.0;
		double[] b3 = new double[A.a.size()];// all 1
		for (int i = 0; i < b3.length; i++) {
			b3[i] = 1.0;
		}
		
		System.out.println("-----The main test for calculating the solution for an equation "
				+ "using iterative solver(Jacobi Solver)----- ");
		System.out.println("*****Calculate the resolution using Jacobi Iterative solver*****");
		long time1 = System.nanoTime();
		//check precision by checking the evolution of the residual vector: ||b – Ax(k)||2.
		System.out.print("When b = b1, ");
		double[] x1 = jacobi.solu(A,b1);
		double[] result1 = Row_productAx.productAx(A, x1);
		double diff1 = formula.diff(result1, b1);
		System.out.println("The normalized residual norm of iterative solver to b1 is: "+diff1);
		System.out.println(" ");
		
		System.out.print("When b = b2, ");
		double[] x2 = jacobi.solu(A,b2);
		double[] result2 = Row_productAx.productAx(A, x2);
		double diff2 = formula.diff(result2, b2);
		System.out.println("The normalized residual norm of iterative solver to b2 is: "+diff2);
		System.out.println(" ");
		
		System.out.print("When b = b3, ");
		double[] x3 = jacobi.solu(A,b3);
		double[] result3 = Row_productAx.productAx(A, x3);
		double diff3 = formula.diff(result3, b3);
		System.out.println("The normalized residual norm of iterative solver to b3 is: "+diff3);
		System.out.println(" ");
		System.out.println(" ");
		
		long time2 = System.nanoTime();
		
		System.out.println("*****Comments on the results*****");
		System.out.println("1. Observation:"
				+ "(1) Under the three cases, the normalized residual norm "
				+ "in the solution decrease with the number of iterations, "
				+ "which means the convergence of the solver is achieved.");
		System.out.println("(2) The monotonic convergence and relatively small "
				+ "normalized residual norm reflects that this problem is a well-conditioned one.");
		System.out.println("(3) Iterative solver's edge is that it can change the convergence condition");
		System.out.println(" ");
		System.out.println(" ");
		
		System.out.println("*****Computational time and memory usage checks*****");
		System.out.println("Time complexity is O(n^2) (n=5000)");
		System.out.println("Computational time is: " + (time2-time1) +"ns");
		long used = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		System.out.println("Memory usage is: " + used + "bytes");
		System.out.println(" ");
		System.out.println(" ");
	}

}
