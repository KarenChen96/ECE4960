/*dates: 2/18/2018
 * authors: Chun Chen
 * Language: Java
 * Platforms: Eclipse in Windows10.
 * */
package hacker_practice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Lecture4_practice1{
	//three vectors
	public static int[] rowPtr = {0,3,6,9,10,12};
	public static int[] colInd = {0,1,4,0,1,2,1,2,4,3,0,4};
	public static int[] value = {1,2,3,4,5,6,7,8,9,10,11,12};
	public static ArrayList<Map> a = new ArrayList<Map>();
	
	public static int[][] full_m = {{1,2,0,0,3},{4,5,6,0,0},
			{0,7,8,0,9},{0,0,0,10,0},{11,0,0,0,12}};
	
	/*Create the sparse matrix A in the row compressed format of Table 2 with three vectors: 
	 * value[12]; rowPtr[6]; colInd[12].  
	 */
	
	//	public static void createMatrix(int[] rowPtr,int[] colInd, int[] value) 
	public static void createMatrix() {
		// using row pointer to find column index
		for (int i = 0; i < rowPtr.length - 1; i++)// i stands for the row number
		{
			Map<Integer, Integer> m = new HashMap<>();
			// create hashmap<col, val> for each row
			for (int j = rowPtr[i]; j < rowPtr[i + 1]; j++) {
				m.put(colInd[j], value[j]);
			}
			a.add(m);
		}
	}
	
	/*Implement the function retrieveElement( ).*/
	public static int retrieveElement(int x, int y) {
		Map<Integer, Integer> m = new HashMap<>();
		try {
			m = a.get(x);// obtain the map for x-th row
			if (m.containsKey(y)) {
				return m.get(y); // return responding value
			} else // else the value is 0
			{
				return 0;
			}
		} catch (Exception e) {
			return -1;//out of bound
		}
	}
		
	public static void main(String[] args) {
//		Lecture4_practice1 A = new Lecture4_practice1();
//		int[] rowPtr = {0,3,6,9,10,12};
//		int[] colInd = {0,1,4,0,1,2,1,2,4,3,0,4};
//		int[] value = {1,2,3,4,5,6,7,8,9,10,11,12};
//		createMatrix(rowPtr,colInd,value);
		createMatrix();
		
		//test retrieveElement fucntion
		Scanner s = new Scanner(System.in);
		
		System.out.print("Please input the index: ");
		String index = s.nextLine();
		int x = Integer.parseInt(index.substring(0, 1));
		int y = Integer.parseInt(index.substring(2,3));
		int result = retrieveElement(x,y);
		System.out.println("The value of this point is: " + result);
		
		//output the matrix using  row-compressed storage 
		System.out.println("");
		System.out.println("The matrix using row-compressed storage is£º ");
		for (int i = 0; i < Lecture4_practice1.a.size(); i++) {
			for (int j = 0; j < Lecture4_practice1.a.size(); j++) {
				System.out.print(retrieveElement(i, j) + " ");
			}
			System.out.println("");
		}
		
		//compare the output and full_matrix element-by-element
		System.out.println("");
		int flag = 1;
		for (int i = 0; i < Lecture4_practice1.a.size(); i++) {
			for (int j = 0; j < Lecture4_practice1.a.size(); j++) {
				if (full_m[i][j] != retrieveElement(i, j)) {
					flag = -1;
				}
			}
		}
		if (flag > 0) {
			System.out.println("The matrix derived by the row-compressed storage is the same with full_matrix.");
		} else {
			System.out.println("The matrix derived by the row-compressed storage is not the same with full_matrix.");
		}

	}

}
