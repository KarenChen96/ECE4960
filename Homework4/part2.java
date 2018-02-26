/*dates: 2/25/2018
 * authors: Chun Chen
 * Language: Java
 * Platforms: Eclipse in Windows10.
 * */

package hm4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class part2 {
	ArrayList<Integer> r = new ArrayList<Integer>();
	ArrayList<Integer> c = new ArrayList<Integer>();
	ArrayList<Double> v = new ArrayList<Double>();
	public void readFile(String filePath) {
		try {
			File file = new File(filePath);
			if (file.isFile() && file.exists()) {
				InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "utf-8");
				BufferedReader br = new BufferedReader(isr);
				String line = null;
				int i = 1;
				while ((line = br.readLine()) != null) {
					if (i > 2) {
						parse(line);
					}
					i++;
				}
				br.close();
			} else {
				System.out.println("The file doesn't exist!");
			}
		} catch (Exception e) {
			System.out.println("Error occurs when reading the file!");
		}
	}
	
	public void parse(String s) {
		String[] args = s.split(" ");
			int rowRank = Integer.parseInt(args[0]);
			int colRank = Integer.parseInt(args[1]);

			double value = 0;
			if (args.length > 3) {
				value = Double.parseDouble(args[3]);
			} else {
				value = Double.parseDouble(args[2]);
			}
			r.add(rowRank);
			c.add(colRank);
			v.add(value);
	}
	
	public static boolean isNumeric(String str) {
		for (int i = str.length(); --i >= 0;) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}
	
	public static double[] getRowi(sparse_mat A, int i)
	{
		double[] a = new double[A.a.size()];
		for (int j = 0; j < A.a.size(); j++) {
			a[i] = A.retrieveElement(i, j);
		}
		return a;			
	}
	
	public static boolean test_permute(double[] a1, double[] a2) {
		for (int i = 0; i<a1.length; i++)
		{
			if (a1[i] != a2[1])
				return false;
		}
		return true;
	}
	
	public static double fir_norm(double a, double b)
	{
		double diff = Math.abs(a-b);
		return diff;
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		part2 p = new part2();
		String filepath = "E:\\JAVA 安装及内容\\workspace\\hacker_practice\\src\\hm4\\memplus.mtx";
		p.readFile(filepath);
		double[] x = { 5, 4, 3, 2, 1 };
		
	    //(1) Perform permutation of (1, 3), (1, 5), (10, 3000), (5000, 10000), and (6, 15000).
		sparse_mat A1 = new sparse_mat();
		A1.traverse(p.r, p.c, p.v);
		System.out.println(" ");

		double[] row1 = getRowi(A1,0);
		double[] row3 = getRowi(A1,2);	
		double[] row5 = getRowi(A1,4);	
		double[] row6 = getRowi(A1,5);
		double[] row10 = getRowi(A1,9);	
		double[] row3000 = getRowi(A1,2999);	
		double[] row5000 = getRowi(A1,4999);
		double[] row10000 = getRowi(A1,9999);
		double[] row15000 = getRowi(A1,14999);
		
	    Row_permute.rowPermute(A1, x, 0, 2);
	    Row_permute.rowPermute(A1, x, 0, 4);
	    Row_permute.rowPermute(A1, x, 9, 2999);
	    Row_permute.rowPermute(A1, x, 4999, 9999);
	    Row_permute.rowPermute(A1, x, 5, 14999);
	    
	    double[] row1_p = getRowi(A1,0);
		double[] row3_p = getRowi(A1,2);
		double[] row5_p = getRowi(A1,4);	
		double[] row6_p = getRowi(A1,5);
		double[] row10_p = getRowi(A1,9);	
		double[] row3000_p = getRowi(A1,2999);	
		double[] row5000_p = getRowi(A1,4999);
		double[] row10000_p = getRowi(A1,9999);
		double[] row15000_p = getRowi(A1,14999);
		
		System.out.println("The result of comparing the matrixs is: ");
		boolean t = test_permute(row1, row3_p) && test_permute(row3, row5_p) && test_permute(row3, row5_p) 
				&& test_permute(row6, row15000_p) && test_permute(row10, row3000_p) && test_permute(row5000, row10000_p);
		System.out.println(t);
	    
		//(2) Perform 3.0*row[2] + row[4], permute (2, 5), and then perform -3.0*row[5] + row[4].
		sparse_mat B1 = new sparse_mat();
		B1.traverse(p.r, p.c, p.v);
		System.out.println(" ");
		System.out.println("Perform 3.0*row[2] + row[4], permute (2, 5), and then perform -3.0*row[5] + row[4]");
		Row_scaling.rowScaling(B1, x, 3, 1, 3.0);
		Row_permute.rowPermute(B1, x, 1, 4);
		Row_scaling.rowScaling(B1, x, 3, 4, -3.0);
		
		//(3)Perform Ax = b, where x is a column vector with all its elements equal to 1.0.  
		//You can readily see that the signed sum of the elements in b is the signed sum of all elements of A.
		sparse_mat C1 = new sparse_mat();
		C1.traverse(p.r, p.c, p.v);
		System.out.println(" ");
		double[] b = new double[C1.a.size()];
		for (int i=0; i<C1.a.size(); i++)
		{
			b[i] = 1.0;
		}
		Row_productAx.productAx(C1, x, b);
		
		//(4) Compute first norm, which should be smaller than a set tolerance (say 10-7).
		sparse_mat D1 = new sparse_mat();
		D1.traverse(p.r, p.c, p.v);
		double diff = 0;
		for (int i = 0; i < D1.a.size(); i++) {
			for (int j = 0; j < D1.a.size(); j++) {
				if (D1.retrieveElement(i, j) != 0) {
					diff = diff + fir_norm(D1.retrieveElement(i, j), b[j]);
				}
			}
		}
		System.out.println(diff);
		
		long used  = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();//calculate memory usage
		System.out.println(used);
	}

}
