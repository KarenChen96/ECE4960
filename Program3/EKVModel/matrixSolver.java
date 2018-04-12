//Date:
//Created on 2/20/2018
//Edited on 4/11/2018
//
//includes:
//(1) Task1 -- Testing and validation of matrix solver
//
//Authors: Xiaoxing Yan
//Platforms:Eclipse
//MAC OS

package project3;

import java.util.Arrays;

import HP5Mtrix.test;
import homework4Matrix.rowFunction;

public class matrixSolver {
	public static void main(String[] Args){

		//Task 1
		//Validation of matrix solver
		//By using ill-conditioned matrix
		System.out.println("this is validation");
		double[] delt = new double[20];
		delt[0]=0.1;
		for(int i=1;i<20;i++) {
			delt[i] = delt[i-1]/10.0;
		}

		for(int i=0;i<delt.length;i++) {


			double[][] fullM = {
					{1,2,0,0,1},
					{1,2+delt[i],0,0,1},
					{0,7,8,0,9},
					{0,0,0,10,0},
					{11,0,0,0,12}
			};

			double[][] fullM1 = {
					{1,2,0,0,1},
					{1,2+delt[i],0,0,1},
					{0,7,8,0,9},
					{0,0,0,10,0},
					{11,0,0,0,12}
			};

			double[] result = new double[] {5,4,3,2,1};
			double[] valueX = new double[result.length];
			valueX = SLUSolver(fullM, result);
			System.out.println("the value of X is :"+Arrays.toString(valueX));
			double[] backResult = rowFunction.fullmatrixProduct(fullM1, valueX);
			double[] result1 = new double[] {5,4,3,2,1};
			System.out.println("the second norm is "+ test.secondNorm(backResult, result1) );

		}

		//testing
		System.out.println();
		System.out.println("this is testing");
		//Using backward substitution and calculate the norm between A*X and B	

		double[][] fullM = {
				{1,2,0,0,1},
				{1,3,0,0,1},
				{0,7,8,0,9},
				{0,0,0,10,0},
				{11,0,0,0,12}
		};

		double[][] fullM1 = {
				{1,2,0,0,1},
				{1,3,0,0,1},
				{0,7,8,0,9},
				{0,0,0,10,0},
				{11,0,0,0,12}
		};

		double[] result = new double[] {5,4,3,2,1};
		double[] valueX = new double[result.length];
		valueX = matrixSolver.SLUSolver(fullM, result);
		System.out.println("the value of X is :"+Arrays.toString(valueX));
		double[] backResult = rowFunction.fullmatrixProduct(fullM1, valueX);
		double[] resultC = new double[] {5,4,3,2,1};
		System.out.println("the second norm is "+ test.secondNorm(backResult, resultC) );
		if(test.secondNorm(backResult, resultC)<Math.pow(10, -7)) {
			System.out.println("the test for matrix solver is passed");
		}
	}

	//matrix solver for full matrix
	//result is the b of ax =b
	//return x
	public static double[] SLUSolver(double[][] fullM, double[] result) {

		int rowSize = fullM.length;
		int columnSize = fullM[0].length;
		int[] pivotRecord = new int[rowSize];


		//for matrix
		for (int i = 0;i<rowSize;i++) {
			//loop rowi.1: row permutation-- find the most sparse row in a loop
			//calcute the number of nonzero element in this row
			int maxCount = 0;
			for(int column =0;column<columnSize;column++) {
				if(fullM[i][column]==0) maxCount++;
			}

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

			//change the row and vector 
			if(rowP != i) {
				fullM= rowFunction.fullrowPermute(fullM, i, rowP);
				//change the number in result vector
				rowFunction.changeVector(result, i,rowP);

			}

			//loop rowi.2
			//after changing the row, go through every element in this row
			//record the pivot with minimum fillins

			int pivotC =0;
			int minFill = Integer.MAX_VALUE;

			//for changing different pivot
			for(int c=0;c<columnSize;c++) {
				if(fullM[i][c]==0) continue;
				//calculate fill in
				int tempFillin = 0;
				for(int loopC=0;loopC<columnSize;loopC++) {

					if(loopC == c) continue;
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


			//scaling:add row2 to row1
			for(int changeR=i+1;changeR<rowSize;changeR++) {
				double a = -fullM[changeR][pivotC]/fullM[i][pivotC];
				fullM=rowFunction.fullrowscaling(fullM, changeR, i,a);
				result[changeR]+=result[i]*a;
			}

			pivotRecord[i] = pivotC;

		}

		//change the matrix to an upper triangular matrix
		for(int rowX=0;rowX<rowSize;rowX++) {
			if(fullM[rowX][rowX]!=0) continue;
			for(int searchRow = rowX+1;searchRow<rowSize;searchRow++) {
				if(fullM[searchRow][rowX]!=0) {
					fullM= rowFunction.fullrowPermute(fullM, rowX,searchRow );
					//change the number in result vector
					rowFunction.changeVector(result, rowX,searchRow);
					break;

				}
			}
		}

		//get X for AX=b -- backward substitution
		double[] valueX= new double[result.length];
		for(int nowX = valueX.length-1;nowX >=0;nowX--) {
			double sum =0;
			for(int nowC = columnSize-1; nowC>nowX;nowC--) {
				sum += fullM[nowX][nowC]*valueX[nowC];
			}
			valueX[nowX] = (result[nowX]-sum)/fullM[nowX][nowX];
		}

		return valueX;


	}
}
