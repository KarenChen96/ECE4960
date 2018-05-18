package project5;
/*
//basic function for matrix 
//
//Created on 3/07/2018
//Edited on 5/13/2018
//
//Functions:
//Sparse -matrix:
//(1) createSMtrix -- for type int / double
//(2) rowPermuteSparse
//(3) retriveMethod && retriveMethodHelp -- for Integer / Double
//(4) rowScalingSparse
//(5) resultMatrix -- product of sparse matrix and vector x
//
//Full matrix:
//(1) fullrowPermute -- for type int / double
//(2) fullrowscaling
//(3) fullmatrixProduct  -- product of full matrix -- Double/double(safe one, return result)
//(3-1)matrix-product: matrix times k --- Safer one
//(4) subtractionMatrix  -- full matrix / double / Double
//(5) printFullMatrix -- retrive full matrix
//(6) changeVector
//(7) addVector
//(8) kMultiVector
//(9) addConst(double[] vector,double k) -- every element in vector: vector + k		 
//
//Authors: Xiaoxing Yan
//Platforms:Eclipse
//MAC OS
//  Copyright 漏 2018 Xiaoxing Yan. All rights reserved.
 */



import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class rowFunction {


	//銆恗ethods for sparse matrix銆�

	//for value = integer
	public static void createSMtrix(ArrayList<Integer> value,ArrayList<Integer> rowPtr, ArrayList<Integer> colInd, int[] nvalue, int[] nrow, int[] ncol) {
		for(int i=0;i<nvalue.length;i++) {
			value.add(nvalue[i]);	
		}
		for(int i=0;i<nrow.length;i++) {
			rowPtr.add(nrow[i]);	
		}
		for(int i=0;i<ncol.length;i++) {
			colInd.add(ncol[i]);	
		}
	}


	//for value = double
	public static void createSMtrix(ArrayList<Double> value,ArrayList<Integer> rowPtr, ArrayList<Integer> colInd, Double[] nvalue, int[] nrow, int[] ncol) {
		for(int i=0;i<nvalue.length;i++) {
			value.add(nvalue[i]);	
		}
		for(int i=0;i<nrow.length;i++) {
			rowPtr.add(nrow[i]);	
		}
		for(int i=0;i<ncol.length;i++) {
			colInd.add(ncol[i]);	
		}
	}



	//for compressed
	public static <E> void rowPermuteSparse(ArrayList<Integer> value,ArrayList<Integer> rowPtr, ArrayList<Integer> colInd, int row1, int row2) {

		//make sure row1 < row2
		if(row1>row2) {
			int number = row2;
			row2 =row1;
			row1 = number;
		}

		//using arraylist to change values and colInd;
		//append values and the delete unnecessary elements

		int row1length = rowPtr.get(row1+1)-rowPtr.get(row1);//the number of elements in two rows
		int row2length = rowPtr.get(row2+1) - rowPtr.get(row2);
		int row1start = rowPtr.get(row1);//the index of value
		int row2start = rowPtr.get(row2);
		int row1end = rowPtr.get(row1+1) -1;
		int row2end = rowPtr.get(row2+1) -1;


		//get elements for row1 and row2
		int[] row1element = new int[row1length];
		int[] row2element = new int[row2length];
		int[] col1element = new int[row1length];
		int[] col2element = new int[row2length];
		int start1 = row1start;
		int start2 = row2start;

		for(int i=0;i<row1length;i++) {
			row1element[i] = value.get(start1);
			col1element[i] = colInd.get(start1);
			start1++;

		}


		for(int i=0;i<row2length;i++) {
			row2element[i] = value.get(start2);
			col2element[i] = colInd.get(start2);
			start2++;


		}

		//append row2 after row1		
		int end1 = row1end+1;
		for(int i = 0; i < row2length; i++) {
			value.add(end1,row2element[i]);
			colInd.add(end1,col2element[i]);//(index,element)
			end1++;

		}

		//append row1 after row2	
		int end2 = row2end+1+row2length;
		for(int i = 0; i < row1length; i++) {
			value.add(end2,row1element[i]);//(index,element)
			colInd.add(end2,col1element[i]);
			end2++;

		}

		//remove elements
		//remove row1 from value
		for(int i = 0;i<row1length;i++) {
			value.remove(row1start);
			colInd.remove(row1start);
		}

		//remove row2 from value
		int index = row2start+row2length-row1length;
		for(int i =0 ;i<row2length;i++) {
			value.remove(index);
			colInd.remove(index);
		}


		//change rowpointer
		int difference=row2length-row1length;
		for(int i = row1+1;i<=row2;i++) {
			rowPtr.set(i, rowPtr.get(i)+difference);
		}



	}

	//retrive the matrix -- for integer
	public static void retriveMethod(ArrayList<Integer> value,ArrayList<Integer> rowPtr, ArrayList<Integer> colInd, int x, int y){

		for(int i=0;i<x;i++) {
			for(int j=0;j<y;j++) {
				int element =retriveMethodHelp(value,rowPtr,colInd, i, j);
				System.out.print(" "+ element);
			}
			System.out.println();

		}
	}


	public static int retriveMethodHelp(ArrayList<Integer> value,ArrayList<Integer> rowPtr, ArrayList<Integer> colInd, int x, int y) {

		int numCol = rowPtr.get(x);//the index of value
		int nextCol = rowPtr.get(x+1);
		for(int i = numCol;i< nextCol;i++) {
			if(colInd.get(i) == y){

				return value.get(i);

			}else {
				continue;
			}
			//continue means enter next loop
			//as y could be in different position
		}
		//after the loop, return 0
		return 0;

	}


	//retrive --for Double
	public static void retriveMethod1(ArrayList<Double> value,ArrayList<Integer> rowPtr, ArrayList<Integer> colInd, int x, int y){

		for(int i=0;i<x;i++) {
			for(int j=0;j<y;j++) {
				Double element =retriveMethodHelp1(value,rowPtr,colInd, i, j);
				System.out.print(" "+ element);
			}
			System.out.println();

		}
	}


	public static Double retriveMethodHelp1(ArrayList<Double> value,ArrayList<Integer> rowPtr, ArrayList<Integer> colInd, int x, int y) {

		int numCol = rowPtr.get(x);//the index of value
		int nextCol = rowPtr.get(x+1);
		for(int i = numCol;i< nextCol;i++) {
			if(colInd.get(i) == y){

				return value.get(i);

			}else {
				continue;
			}
			//continue means enter next loop
			//as y could be in different position
		}
		//after the loop, return 0
		return 0.0;
	}


	//add a*row2 to row1
	public static <E> void rowScalingSparse(ArrayList<Double> value,ArrayList<Integer> rowPtr, ArrayList<Integer> colInd, int row1, int row2, double a) {


		HashMap<Integer,Integer> standard = new HashMap<Integer,Integer>();
		HashMap<Integer,Double> row2set = new HashMap<Integer,Double>();

		int row1start = rowPtr.get(row1);//the index of the value
		int row2start = rowPtr.get(row2);
		int row1end = rowPtr.get(row1+1)-1;
		int row2end = rowPtr.get(row2+1)-1;

		//create the hashmap

		//key - row1 column number, value = the index of value
		for(int i = row1start;i<=row1end;i++) {
			standard.put(colInd.get(i), i);
		}

		//for row2 the hashmap is different
		for(int i = row2start;i<=row2end;i++) {
			row2set.put(colInd.get(i),value.get(i));
		}



		//check every element in row2set
		int end1 = row1end;
		for(Integer key : row2set.keySet()) {


			//if there is already column existing
			if(standard.containsKey(key)) {
				double previousnumberrow1 = value.get(standard.get(key));
				double previousnumberrow2 = row2set.get(key);
				double newvalue = a*previousnumberrow2+previousnumberrow1;
				if(newvalue == 0) {
					//if the calculation cause 0 in the matrix
					//needs to remove the element in value and column
					//decrease the rowpointer

					value.remove(standard.get(key));//int index = standard.get(key)
					colInd.remove(standard.get(key));
					standard.remove(key);

					for(int z = row1+1;z < rowPtr.size();z++) {
						int number =rowPtr.get(z)-1;
						rowPtr.set(z, number);
					}	

				}else {
					value.set(standard.get(key), newvalue);//add a*row2 to row1
				}
			}else {//row 2 does not have this column
				//do not have to find the right position in value
				//just add it in the range of row1
				value.add(end1+1,a*row2set.get(key));
				colInd.add(end1+1,key);


				//change the rowptr
				for(int z = row1+1;z < rowPtr.size();z++) {
					int number =rowPtr.get(z)+1;
					rowPtr.set(z, number);
				}
				//change the end1 of row1

				end1++;

			}

		}




	}

	//Product of sparse matrix and vector x
	public static  void resultMatrix(ArrayList<Integer> value,ArrayList<Integer> rowPtr, ArrayList<Integer> colInd, int[] X,int[] result) {


		//j is the number of rows
		for(int j=0;j<(rowPtr.size()-1);j++) {
			int number = 0;
			int numCol = rowPtr.get(j);
			int nextCol = rowPtr.get(j+1);
			for(int i = numCol;i< nextCol;i++) {
				number+=value.get(i) * X[colInd.get(i)];
			} 
			result[j] = number;

		}

	}

	//matrix product for Double
	public static  void resultMatrix(ArrayList<Double> value,ArrayList<Integer> rowPtr, ArrayList<Integer> colInd, Double[] X,Double[] result) {

		assert rowPtr.size() -1 == X.length:"they are not in the same size";

		//j is the number of rows
		for(int j=0;j<X.length;j++) {
			Double number = 0.0;
			int numCol = rowPtr.get(j);
			int nextCol = rowPtr.get(j+1);
			for(int i = numCol;i< nextCol;i++) {
				number+=value.get(i) * X[colInd.get(i)];
			} 
			result[j] = number;

		}

	}


	// 銆恗ethods for full matrix銆�

	//change row
	public static int[][] fullrowPermute(int[][] matrix, int row1, int row2){
		int[] temp = new int[matrix[0].length];
		temp = matrix[row1];
		matrix[row1]=matrix[row2];
		matrix[row2] = temp;

		return matrix;
	}

	//for full--double
	public static double[][] fullrowPermute(double[][] matrix, int row1, int row2){
		double[] temp = new double[matrix[0].length];
		temp = matrix[row1];
		matrix[row1]=matrix[row2];
		matrix[row2] = temp;

		return matrix;
	}

	//for full matrix
	//scaling:add row2 to row1
	public static double[][] fullrowscaling(double[][] matrix, int row1, int row2,double a){
		for(int i = 0;i< matrix[row1].length;i++) {//length means the number of rows in [][] and in this case means the columns
			matrix[row1][i]+= matrix[row2][i]*a;

		}
		return matrix;
	}

	//product of full matrix
	public static int[] fullmatrixProduct(int[][] matrix, int[] X) {

		int[] result= new int[X.length];
		for(int i=0;i<X.length;i++) {
			for(int j=0;j<X.length;j++) {
				result[i]+=matrix[i][j]*X[j];
			}
		}

		return result;

	}
	//full matrix for Double
	public static Double[] fullmatrixProduct(Double[][] matrix, Double[] X, Double[] result) {



		//Double[] result= new Double[X.length];
		for(int i=0;i<X.length;i++) {
			for(int j=0;j<matrix.length;j++) {
				result[i]+=matrix[i][j]*X[j];
			}
		}

		return result;

	}

	//optimization -- the other one is not safe
	//full matrix - for double
	public static double[] fullmatrixProduct(double[][] matrix, double[] X) {

		double[] result = new double[X.length];
		for(int i=0;i<X.length;i++) {
			for(int j=0;j<matrix.length;j++) {
				result[i]+=matrix[i][j]*X[j];
			}
		}

		return result;

	}

	//full matrix - for double
	public static double[] fullmatrixProduct(double[][] matrix, double[] X, double[] result) {

		//Double[] result= new Double[X.length];
		for(int i=0;i<X.length;i++) {
			for(int j=0;j<matrix.length;j++) {
				result[i]+=matrix[i][j]*X[j];
			}
		}

		return result;

	}
	
	
	//matrix-product: matrix times k --- Safer one
	public static double[][] fullmatrixProduct(double[][] matrix, double k) {

		int rowSize = matrix.length;
		int colSize = matrix[0].length;
		double[][] result = new double[rowSize][colSize];
		for(int i=0;i<rowSize;i++) {
			for(int j=0;j<colSize;j++) {
				result[i][j] = matrix[i][j]*k;
			}
		}

		return result;

	}//end of a method

	//for value is double: the subtraction --fullmatrix
	public static double[][] subtractionMatrix(double[][] matrix1,double[][] matrix2) {
		double[][] result = new double[matrix1.length][matrix1[0].length];

		for(int i=0;i<matrix1.length;i++) {
			for(int j=0;j<matrix1[0].length;j++) {
				result[i][j] = matrix1[i][j] -matrix2[i][j];
			}
		}
		return result;
	}

	//for value is double: the subtraction 
	public static void subtractionVector(Double[] v1,Double[] v2, Double[] result) {
		for(int i = 0;i<v1.length;i++) {
			result[i] = v1[i] - v2[i];
		}

	}
	//for double
	public static void subtractionVector(double[] v1,double[] v2, double[] result) {
		for(int i = 0;i<v2.length;i++) {
			result[i] = v1[i] - v2[i];
		}

	}
	
	//for double
		public static double[] subtractionVector(double[] v1,double[] v2) {
			double[] result = new double[v1.length];
			for(int i = 0;i<v2.length;i++) {
				result[i] = v1[i] - v2[i];
			}
			return result;

		}


	//retrive full matrix
	public static void printFullMatrix(double[][] matrix) {
		for(int i=0;i<matrix.length;i++) {
			for(int j=0;j<matrix[0].length;j++) {
				System.out.print(matrix[i][j]+", ");

			}
			System.out.println();
		}

	}

	//change the vector
	public static void changeVector(double[] vector, int row1,int row2) {
		double tempN = vector[row1];
		vector[row1] = vector[row2];
		vector[row2] = tempN;

	}

	//add two vectors--double
	public static double[] addVector(double[] vector1,double[] vector2) {
		double[] sum = new double[vector1.length];
		for(int i=0;i<vector1.length;i++) {
			sum[i] = vector1[i] + vector2[i];

		}
		return sum;
	}

	//vector*k

	public static double[] kMultiVector(double[] vector,double k) {
		double[] result = new double[vector.length];
		for(int i=0;i<vector.length;i++) {
			result[i] = vector[i]*k;
		}
		return result;
	}

	//every element in vector: vector + k
	public static double[] addConst(double[] vector,double k) {
		double[] result = new double[vector.length];
		for(int i=0;i<vector.length;i++) {
			result[i] = vector[i]+k;
		}
		return result;
	}





}
