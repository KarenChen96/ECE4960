package project5;
/*
// function for matrix
//
//(1)copyArrays(double[][] from)  -- copy full matrix
//(2)retriveMethod1  -- retrive matrix in sparse format
//(3)retriveEleandInd
//(4)SLUSolver(double[][] initM, double[] initresult)    -- full matrix solver
//		matrix and vector will not be changed after SLUSolver
//		result is the b of ax =b
//(5)upperNorm  -- fullmatrix & sparsematrix
//(6)jacobiSolver(double[][] matrix, double[] vector, double criteria,double[] initGuess) 
//		iterative solver
//		return x
//(7)jacobiSolverDLU(double[][] matrix, double[] vector, double criteria,double[] initGuess) 
//		fullmatrix - jocobi solver 
//		getD(-1)*(L+U)

//(8)decomposeCompressesMatrix(ArrayList<Double> value,ArrayList<Integer> rowPtr, ArrayList<Integer> colInd,ArrayList<Double> constC,ArrayList<Double> vector){
//		change the matrix to D(-1)*(L+U) and get the constant c	
//(9)CompressesMatrixSolver(ArrayList<Double> initvalue,ArrayList<Integer> initrowPtr, ArrayList<Integer> initcolInd,ArrayList<Double> vector,ArrayList<Double> x,double criteria){
//			iterative solver for sparse matrix
//			AX = vector
//			return x directly	 		
//(10)methodSOR(double[][] matrix, double[] vector,double[] initGuessX, double w) {

//  Created by Xiaoixng Yan on 03/07/2018.
//  Copyright © 2018 Xiaoxing Yan. All rights reserved.
*/


import java.util.ArrayList;
import java.util.Arrays;


public class matrixFunction {
	
	
	
	public static double[][] copyArrays(double[][] from){

		int row = from.length;
		int col = from[0].length;
		double[][] to = new double[row][col];

		for(int i = 0 ; i < row ; i++) {
			for(int j = 0 ; j < col; j++){
				to[i][j] = from[i][j];
			}
		}
		return to;
	}
	
	//retrive the whole matrix --for Double
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
	

		//retrive an element and its index(when this element is nonzero) in value for a matrix --for Double
		//for this method,element needs to initialize to zero
		public static void retriveEleandInd(ArrayList<Double> value,ArrayList<Integer> rowPtr, ArrayList<Integer> colInd, int row, int col, Integer[] index, Double[] element) {
			
		assert row>=0&&row<=(rowPtr.size()-1):"the row index is wrong";
		assert col>=0&&col<=(rowPtr.size()-1):"the column index is wrong";
		
			
			int numCol = rowPtr.get(row);//the index of value
			int nextCol = rowPtr.get(row+1);
		    for(int i = numCol;i< nextCol;i++) {
				   if(colInd.get(i) == col){
					
						element[0] = value.get(i);
						index[0]=i;
						break;
					}else {
						continue;
					}
				   //continue means enter next loop
				   //as y could be in different position
				}
		    //after the loop, return 0
		  
			
		}
		
	//matrix and vector will not be changed after SLUSolver
	//result is the b of ax =b
	public static double[] SLUSolver(double[][] initM, double[] initresult) {

		
	//the number of rows/columns in this matrix
	int rowSize = initM.length;
	int columnSize = initM[0].length;
	int[] pivotRecord = new int[rowSize];
	
	double[][] fullM = new double[rowSize][columnSize];
	fullM = copyArrays(initM);
	
	double[] result = new double[initresult.length];
	System.arraycopy(initresult, 0, result, 0, initresult.length);
	
	
	//the first loop is for this matrix
	//test part,only for the first row -- 
	//for (int i = 0;i<2;i++) {
	for (int i = 0;i<rowSize;i++) {
		//loop rowi.1: row permutation-- find the most sparse row in a loop
		//calcute the number of nonzero element in this row
		int maxCount = 0;
		for(int column =0;column<columnSize;column++) {
			if(fullM[i][column]==0) maxCount++;
		}
		//start from the next row after i
		//rowP is a row with maximum sparsity
		int rowP = i;
		for(int j=i+1;j<rowSize;j++) {
			int count =0;
			for(int column =0;column<columnSize;column++) {
				if(fullM[j][column]==0) count++;
			}
			if(count>maxCount){
				maxCount = count;
				rowP = j;
			}	
		}
		//test: pass -- System.out.println(rowP);
		//change the row and vector 
		if(rowP != i) {
			fullM= rowFunction.fullrowPermute(fullM, i, rowP);
			//change the number in result vector
			rowFunction.changeVector(result, i,rowP);
			//test:pass -- System.out.println(Arrays.toString(result));
		}
		
		//loop rowi.2
		//after changing the row, go through every element in this row
		//there is a loop for every element
		//using minFill to record the pivot with minimum fillins

		int pivotC =0;
		int minFill = Integer.MAX_VALUE;
		//set the max value to initialize the minimum fillin
		//and it can be replaced when meets smaller integer
		
		//this loop is for changing different pivot
		for(int c=0;c<columnSize;c++) {
			if(fullM[i][c]==0) continue;
			//this loop is to calculate fill in
			int tempFillin = 0;
			for(int loopC=0;loopC<columnSize;loopC++) {
				
				if(loopC == c) continue;//loops in other column
				//find which column will be polluted -- full[i][~] is not zero
				if(fullM[i][loopC]==0) continue;
				//find which row will be polluted--fullM[~][c] is  zero
				for(int temR = i+1;temR<rowSize;temR++) {
					if(fullM[temR][loopC]==0) tempFillin++;
				}
			}
			
			
			//if the tempFillin less than minfillin, change pivot
			if(tempFillin<minFill) {
				pivotC = c;
				minFill=tempFillin;
			}
			
		}
		//test:pass -- System.out.println(i+"haha"+pivotC);
		//System.out.println(minFill);
		
		//after determining the pivot
		//start to change the matrix and vector
		
		//for full matrix
		//scaling:add row2 to row1
		for(int changeR=i+1;changeR<rowSize;changeR++) {
			double a = -fullM[changeR][pivotC]/fullM[i][pivotC];
			fullM=rowFunction.fullrowscaling(fullM, changeR, i,a);
			result[changeR]+=result[i]*a;
		}
		
		
		
		
		pivotRecord[i] = pivotC;
		
		
		
	}//this is the outer loop for pivot in every row
	//test:pass--System.out.println(Arrays.toString(pivotRecord));
	//test:pass --System.out.println(Arrays.toString(result));

	
	//gtest:pass--rowFunction.printFullMatrix(fullM);
	
	//System.out.println();
	//change the matrix to an upper triangular matrix
	
	for(int rowX=0;rowX<rowSize;rowX++) {
		if(fullM[rowX][rowX]!=0) continue;
		for(int searchRow = rowX+1;searchRow<rowSize;searchRow++) {
			if(fullM[searchRow][rowX]!=0) {
				//row permutation
				fullM= rowFunction.fullrowPermute(fullM, rowX,searchRow );
				//change the number in result vector
				rowFunction.changeVector(result, rowX,searchRow);
				//rowFunction.printFullMatrix(fullM);
				break;
				
			}
		}
	}//END FOR
	
	//test:pass--rowFunction.printFullMatrix(fullM);
	//System.out.println(Arrays.toString(result));
	
	//get X for AX=b -- backward substitution
	double[] valueX= new double[result.length];
	//start from the last row
	for(int nowX = valueX.length-1;nowX >=0;nowX--) {
		double sum =0;
		for(int nowC = columnSize-1; nowC>nowX;nowC--) {
		sum += fullM[nowX][nowC]*valueX[nowC];
		}
		valueX[nowX] = (result[nowX]-sum)/fullM[nowX][nowX];
	}
	//test:pass --System.out.println(Arrays.toString(valueX));
	return valueX;


	}//end of a method
	
	//for full matrix
	public static void upperNorm(double[][] fullM, double[] result) {
		int rowSize = fullM.length;
		int colSize = fullM[0].length;
		//for Aone
		double max = 0;
		for(int column=0;column<colSize;column++) {
			double temp = 0;
			for(int row=0;row<rowSize;row++) {
			temp += fullM[row][column];
			}
			if(temp>max) max = temp;
		}
		//Aone = max;
		result[0] = max;
		//test:pass -- 
		//System.out.println(Aone);
		
		//for AInfinite
		double max1 = 0;
		for(int row=0;row<rowSize;row++) {
			double temp = 0;
			for(int column=0;column<colSize;column++) {
			temp += fullM[row][column];
			}
			if(temp>max1) max1 = temp;
		}
		//AInfinite = max1;
		result[1] = max1;
		//test:pass -- 
		//System.out.println(AInfinite);
		
	}//end of full-upper
	
	//for sparse matrix
		public static void upperNorm(ArrayList<Double> value, ArrayList<Integer> rowPtr, ArrayList<Integer> colInd, int colSize, double[] result) {
			int rowSize = rowPtr.size()-1;
			//for Aone
			//retrive elements and then calculate
			double max =0;
			for(int column=0;column<colSize;column++) {
				double temp = 0;
				for(int row=0;row<rowSize;row++) {
				temp += Math.abs(rowFunction.retriveMethodHelp1(value, rowPtr, colInd, row, column));
				}
				if(temp>max) max = temp;
			}
			result[0] = max;
			

			//for AInfinite
			double max1 = 0;
			for(int row=0;row<rowSize;row++) {
				double temp = 0;
				for(int column=0;column<colSize;column++) {
				temp += Math.abs(rowFunction.retriveMethodHelp1(value, rowPtr, colInd, row, column));
				}
				if(temp>max1) max1 = temp;
			}
			//AInfinite = max1;
			result[1] = max1;
			

	
		}//end of a method
		
		//matrix_product: full matrix - for double
		public static double[] fullmatrixProduct(double[][] matrix, double[] X, double[] result) {



			//Double[] result= new Double[X.length];
			for(int i=0;i<X.length;i++) {
				for(int j=0;j<matrix.length;j++) {
					result[i]+=matrix[i][j]*X[j];
				}
			}

			return result;

		}//end of a method
		
		//matrix-product: matrix times k
		public static double[][] fullmatrixProduct(double[][] matrix, double k, double[][] result) {

			int rowSize = matrix.length;
			int colSize = matrix[0].length;
			for(int i=0;i<rowSize;i++) {
				for(int j=0;j<colSize;j++) {
					result[i][j] = matrix[i][j]*k;
				}
			}

			return result;

		}//end of a method
		
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
		
		public static double[] jacobiSolver(double[][] matrix, double[] vector, double criteria,double[] initGuess) {
			//calculate constanct C
			//recreate matrix L+U
			//recreate matrix D(-1)*(L+U)
			//using a loop for iteration
			//record the count of iterations and update X (product of a matrix and a vector)
			
			
			//init
			int rowSize = matrix.length;
			int columnSize = matrix[0].length;
			//get inverse d and constant c
			double[] constC = new double[vector.length];
			double[] dInverse = new double[rowSize];
			for(int i=0;i<rowSize;i++) {
				dInverse[i] = 1/matrix[i][i];
				constC[i]=dInverse[i]*vector[i];
			}
			//test:pass--System.out.println(Arrays.toString(dInverse));
			//test:pass--System.out.println(Arrays.toString(constC));
			
			
			//L+U
			double[][] matrixLU = new double[rowSize][columnSize];
			matrixFunction.fullmatrixProduct(matrix, -1, matrixLU);
			for(int i=0; i<rowSize;i++) {
				matrixLU[i][i]=0;
			}
			//test:pass--rowFunction.printFullMatrix(matrixLU);
			
			//D(-1)*(L+U)
			for(int i=0;i<matrixLU.length;i++) {
					for(int j=0;j<matrixLU[0].length;j++) {
						if(matrixLU[i][j]!=0) {
							matrixLU[i][j]=dInverse[i]*matrixLU[i][j];
						}
					}
			}
			//test:pass--System.out.println();
			//test:pass--rowFunction.printFullMatrix(matrixLU);
			
			double[] X = new double[initGuess.length];
			System.arraycopy(initGuess, 0, X, 0, initGuess.length);
			
			double[] oldX = new double[initGuess.length];
			int counter=0;
			//rowFunction.printFullMatrix(matrixLU);
			//System.out.println(Arrays.toString(X));
			//System.out.println(Arrays.toString(constC));
			double[] resultCri = new double[] {0};
			do{
				//initilize in the loop!!!!!!!
				double[] result  = new double[X.length];//all zero
				matrixFunction.fullmatrixProduct(matrixLU, X, result);
				for(int n=0;n<X.length;n++) {
					result[n] = result[n] +constC[n];
				}
				counter++;
				//cope x to oldx
				System.arraycopy(X, 0, oldX, 0, X.length);
				//copy result to x
				System.arraycopy(result, 0, X, 0, result.length);

	
				
				double[] truer= new double[X.length];//all zeros
				double base =test.secondNorm(oldX,truer);
				double dltx = test.secondNorm(X, oldX);
				resultCri[0] = dltx/base;

			}while(resultCri[0]>criteria);
			
			//System.out.println("the number of iteration is " + counter);
//			System.out.println("the X from full-matrix is: "+Arrays.toString(X));
			return X;
			
			
		}
		
		//fullmatrix - jocobi solver 
		//getD(-1)*(L+U)
		public static double[][] jacobiSolverDLU(double[][] matrix, double[] vector, double criteria,double[] initGuess) {
			//calculate constanct C
			//recreate matrix L+U
			//recreate matrix D(-1)*(L+U)
			//using a loop for iteration
			//record the count of iterations and update X (product of a matrix and a vector)
			
			
			//init
			int rowSize = matrix.length;
			int columnSize = matrix[0].length;
			//get inverse d and constant c
			double[] constC = new double[vector.length];
			double[] dInverse = new double[rowSize];
			for(int i=0;i<rowSize;i++) {
				dInverse[i] = 1/matrix[i][i];
				constC[i]=dInverse[i]*vector[i];
			}
			//test:pass--System.out.println(Arrays.toString(dInverse));
			//test:pass--System.out.println(Arrays.toString(constC));
			
			
			//L+U
			double[][] matrixLU = new double[rowSize][columnSize];
			matrixFunction.fullmatrixProduct(matrix, -1, matrixLU);
			for(int i=0; i<rowSize;i++) {
				matrixLU[i][i]=0;
			}
			//test:pass--rowFunction.printFullMatrix(matrixLU);
			
			//D(-1)*(L+U)
			for(int i=0;i<matrixLU.length;i++) {
					for(int j=0;j<matrixLU[0].length;j++) {
						if(matrixLU[i][j]!=0) {
							matrixLU[i][j]=dInverse[i]*matrixLU[i][j];
						}
					}
			}
			return matrixLU;
		}
		
		//change the matrix to D(-1)*(L+U) and get the constant c
		public static void decomposeCompressesMatrix(ArrayList<Double> value,ArrayList<Integer> rowPtr, ArrayList<Integer> colInd,ArrayList<Double> constC,ArrayList<Double> vector){
			
			int rowSize = rowPtr.size()-1;
			int colSize = rowSize;
			//loop one--change the matrix to (L+U) and get D(-1)
			ArrayList<Double> dInverse = new ArrayList<Double>();
			Integer[] index = new Integer[1];//init:null
			Double[] element = new Double[1];//init:0.0
			for(int row=0;row<rowSize;row++) {
				for(int column=0;column<colSize;column++) {
					
					//loop all the elements, get D(-1) and change it to zero
					element[0] = 0.0;
					retriveEleandInd(value,rowPtr,colInd,row,column,index,element);
					//test:pass -- retrive elements correctly--System.out.println(row+"and"+column+"element"+element[0]);
					//System.out.println(row+"and"+column+"element"+index[0]);
					
					//add diagonal element to ArrayList
					if(row == column) {
						dInverse.add(1/element[0]);
						//change it to zero
						//delete it from value and colInd, change rowPtr
						int index1 = index[0];
						value.remove(index1);
						colInd.remove(index1);
						for(int i =row+1;i<=rowSize;i++) {
							rowPtr.set(i, rowPtr.get(i)-1);
						}
					}else if(element[0]!=0 && (row != column)) {
						//multiply minus one to it
						value.set(index[0], -value.get(index[0]));
					}
			}//end of column loop
			
			}//end of row loop
			//test:pass
//			Double[] a =new Double[3];
//			dInverse.toArray(a);
//			System.out.println("the D(-1) and the L+U matrix is: ");
//			System.out.println(Arrays.toString(a));
//			rowFunction.retriveMethod1(value,rowPtr, colInd, 3, 3);
//			System.out.println();
			
			//loop two
			//change the matrix to D(-1)*(L+U) and get the vector D(-1)*b	
			index[0] = 0;//reset index
			
			for(int row=0;row<rowSize;row++) {
				//get constant c
				constC.add(dInverse.get(row)*vector.get(row));
				//change the matrix to D(-1)*(L+U)
				for(int column=0;column<colSize;column++) {
					element[0] = 0.0;
					retriveEleandInd(value,rowPtr,colInd,row,column,index,element);
					if(element[0]!=0 ) {
						value.set(index[0], value.get(index[0])*dInverse.get(row));
					}
					
				}
				}//end of loop row
		
			
	}
		
		
		 //get x 
		 //the matrix now is D(-1)*(L+U)
		//x is the init x
		//after editing, the old arraylist will not be changed after this function
		 public static Double[] jacobiSolverCompressdeM(ArrayList<Double> initvalue,ArrayList<Integer> initrowPtr, ArrayList<Integer> initcolInd,ArrayList<Double> constC,ArrayList<Double> x,double criteria) {
			 assert x.size()==initrowPtr.size()-1;
			 
			 ArrayList<Double> value = new ArrayList<Double>(initvalue);
			 ArrayList<Integer> rowPtr = new ArrayList<Integer>(initrowPtr);
			 ArrayList<Integer> colInd = new ArrayList<Integer>(initcolInd);
			 
			 
			 //Double[] X =new Double[constC.size()];
			 //x.toArray(X); edited in 4/29
			 
			Double[] X = x.toArray(new Double[x.size()]);
			 //System.out.println("the init x is "+Arrays.toString(X));

			 Double[] oldX =new Double[constC.size()];
			 Double[] resultCri = new Double[1];
			 int counter = 0;
			 do{
				 //initilize in the loop!!!!!!!
				 Double[] result  = new Double[X.length];//now all element are null	
				 //test:pass--System.out.println("the init result is "+Arrays.toString(result));
				 rowFunction.resultMatrix( value,rowPtr, colInd, X,result); 
				// System.out.println("the result after loop before add consc"+counter+" is "+Arrays.toString(result));
				 for(int n=0;n<X.length;n++) {
					 result[n] = result[n] +constC.get(n);
				 }
				 //System.out.println("the result after loop"+counter+" is "+Arrays.toString(result));
				 counter++;
				 //cope x to oldx
				 System.arraycopy(X, 0, oldX, 0, X.length);
				 //copy result to x
				 System.arraycopy(result, 0, X, 0, result.length);


				 double base =test.secondNorm(oldX);
				 double dltx = test.secondNorm(X, oldX);
				 resultCri[0] = dltx/base;
				System.out.println("when the iteration is: "+counter+", the ||oldX-X||2 is " + dltx);

			 }while(resultCri[0]>criteria);
			



			 //System.out.println("the X is: "+Arrays.toString(X));
			 //System.out.println("the number of iteration is " + counter);
			 return X;
		 }
		 
		 //for full matrix--norm 1
		 public static double calNorm1(double[][] matrix) {
			 double maxSum=0;
			 
			 for(int i=0;i<matrix[0].length;i++) {
				 double sum1 =0;
					for(int j=0;j<matrix.length;j++) {
						sum1 += Math.abs(matrix[j][i]);
					}
					if(sum1>maxSum) {
						maxSum=sum1;
					}
			 }
			 return maxSum;
			 
		 }
		 
		//for full matrix--norm inf
		 public static double calNormInf(double[][] matrix) {
			 double maxSum=0;
			 
			 for(int i=0;i<matrix.length;i++) {
				 double sum1 =0;
					for(int j=0;j<matrix[0].length;j++) {
						sum1 += Math.abs(matrix[i][j]);
					}
					if(sum1>maxSum) {
						maxSum=sum1;
					}
			 }
			 return maxSum;
		 }
		 
		 //getD(-1)*b --fullmatrix 
		 public static double[] obtainDb(double[][] matrix, double[] vector) {

			 //init
			 int rowSize = matrix.length;
			 
			 //get inverse d and constant c
			 double[] constC = new double[vector.length];
			 double[] dInverse = new double[rowSize];
			 for(int i=0;i<rowSize;i++) {
				 dInverse[i] = 1/matrix[i][i];
				 constC[i]=dInverse[i]*vector[i];
			 }
	
			 return constC;
		 }
	
		 public static double[] methodSOR(double[][] matrix, double[] vector,double[] initGuessX, double w) {

				int rowSize = matrix.length;
				int colSize = matrix[0].length;

				//d(-1)
				double[] dInverse= new double[rowSize];
				for(int i=0;i<rowSize;i++) {
					for(int j=0;j<colSize;j++) {
						if(i==j) {
							dInverse[i] = 1/matrix[i][j];
						}
					}
				}
				System.out.println("the d-1 is "+ Arrays.toString(dInverse));

				double[] newX = new double[vector.length];
				double prove =0;
				int counter=0;
				do {
					//r = b-ax
					double[] valueP = new double[vector.length];
					rowFunction.fullmatrixProduct(matrix, initGuessX, valueP);
					double[] valueR = new double[vector.length];
					rowFunction.subtractionVector(vector,valueP, valueR);
					System.out.println("the r is "+Arrays.toString(valueR));
					
					//wD(-1)r
					for(int i=0;i<rowSize;i++) {
						newX[i] = w*dInverse[i]*valueR[i];
					}
					System.out.println("the second norm of deltx is "+test.secondNorm(newX));
					
					//wD(-1)r+x
					for(int i=0;i<rowSize;i++) {
						newX[i] = newX[i]+initGuessX[i];
					}
					prove = test.secondNorm(newX,initGuessX);
					System.out.println("the x before updating is "+ Arrays.toString(initGuessX));
					System.arraycopy(newX, 0, initGuessX, 0, newX.length);
					System.out.println("the x after updating is "+ Arrays.toString(initGuessX));

					
					counter++;
					System.out.println("the iter. is "+ counter);

				}while(prove > Math.pow(10, -7));
		
				return newX;

				
			}
		 
		
		 //iterative solver for sparse matrix
		 //AX = vector
		 //return x directly
		 	public static Double[] CompressesMatrixSolver(ArrayList<Double> initvalue,ArrayList<Integer> initrowPtr, ArrayList<Integer> initcolInd,ArrayList<Double> vector,ArrayList<Double> x,double criteria){
		 		
		 		 ArrayList<Double> value = new ArrayList<Double>(initvalue);
		 		 ArrayList<Integer> rowPtr = new ArrayList<Integer>(initrowPtr);
		 		 ArrayList<Integer> colInd = new ArrayList<Integer>(initcolInd);
		 		 
		 		 
		 		int rowSize = rowPtr.size()-1;
		 		int colSize = rowSize;
		 		//loop one--change the matrix to (L+U) and get D(-1)
		 		ArrayList<Double> dInverse = new ArrayList<Double>();
		 		Integer[] index = new Integer[1];//init:null
		 		Double[] element = new Double[1];//init:0.0
		 		for(int row=0;row<rowSize;row++) {
		 			for(int column=0;column<colSize;column++) {
		 				
		 				//loop all the elements, get D(-1) and change it to zero
		 				element[0] = 0.0;
		 				retriveEleandInd(value,rowPtr,colInd,row,column,index,element);
		 				//test:pass -- retrive elements correctly--System.out.println(row+"and"+column+"element"+element[0]);
		 				//System.out.println(row+"and"+column+"element"+index[0]);
		 				
		 				//add diagonal element to ArrayList
		 				if(row == column) {
		 					dInverse.add(1/element[0]);
		 					//change it to zero
		 					//delete it from value and colInd, change rowPtr
		 					int index1 = index[0];
		 					value.remove(index1);
		 					colInd.remove(index1);
		 					for(int i =row+1;i<=rowSize;i++) {
		 						rowPtr.set(i, rowPtr.get(i)-1);
		 					}
		 				}else if(element[0]!=0 && (row != column)) {
		 					//multiply minus one to it
		 					value.set(index[0], -value.get(index[0]));
		 				}
		 		}//end of column loop
		 		
		 		}//end of row loop
		 		//test:pass
//		 		Double[] a =new Double[3];
//		 		dInverse.toArray(a);
//		 		System.out.println("the D(-1) and the L+U matrix is: ");
//		 		System.out.println(Arrays.toString(a));
//		 		rowFunction.retriveMethod1(value,rowPtr, colInd, 3, 3);
//		 		System.out.println();
		 		
		 		//loop two
		 		//change the matrix to D(-1)*(L+U) and get the vector D(-1)*b	
		 		index[0] = 0;//reset index
		 		ArrayList<Double> constC = new ArrayList<Double> ();
		 		for(int row=0;row<rowSize;row++) {
		 			//get constant c
		 			constC.add(dInverse.get(row)*vector.get(row));
		 			//change the matrix to D(-1)*(L+U)
		 			for(int column=0;column<colSize;column++) {
		 				element[0] = 0.0;
		 				retriveEleandInd(value,rowPtr,colInd,row,column,index,element);
		 				if(element[0]!=0 ) {
		 					value.set(index[0], value.get(index[0])*dInverse.get(row));
		 				}
		 				
		 			}
		 			}//end of loop row
		 	
		 	 //the matrix now is D(-1)*(L+U)
		 		 
		 		Double[] X = x.toArray(new Double[x.size()]);
		 		 //System.out.println("the init x is "+Arrays.toString(X));

		 		 Double[] oldX =new Double[constC.size()];
		 		 Double[] resultCri = new Double[1];
		 		 int counter = 0;
		 		 do{
		 			 //initilize in the loop!!!!!!!
		 			 Double[] result  = new Double[X.length];//now all element are null	
		 			 //test:pass--System.out.println("the init result is "+Arrays.toString(result));
		 			 rowFunction.resultMatrix( value,rowPtr, colInd, X,result); 
		 			// System.out.println("the result after loop before add consc"+counter+" is "+Arrays.toString(result));
		 			 for(int n=0;n<X.length;n++) {
		 				 result[n] = result[n] +constC.get(n);
		 			 }
		 			 //System.out.println("the result after loop"+counter+" is "+Arrays.toString(result));
		 			 counter++;
		 			 //cope x to oldx
		 			 System.arraycopy(X, 0, oldX, 0, X.length);
		 			 //copy result to x
		 			 System.arraycopy(result, 0, X, 0, result.length);


		 			 double base =test.secondNorm(oldX);
		 			 double dltx = test.secondNorm(X, oldX);
		 			 resultCri[0] = dltx/base;
		 			//System.out.println("when the iteration is: "+counter+", the ||oldX-X||2 is " + dltx);

		 		 }while(resultCri[0]>criteria);
		 		



		 		 //System.out.println("the X is: "+Arrays.toString(X));
		 		 //System.out.println("the number of iteration is " + counter);
		 		 return X;
		 	 }
		 	 
		 

			
			
}//end of this class


