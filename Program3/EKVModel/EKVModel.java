//Date:
//Created on 3/29/2018
//Edited on 4/8/2018
//
//function includes:
//(1) quaNewSolver --obtain best fit of Is, k, Vth
//(2) f1 & f1xDri  --partial derivative with respect to Is
//(3) f2 & f2xDri  --partial derivative with respect to k 
//(4) f2 & f2xDri  --partial derivative with respect to Vth
//(5) calculate    --Id model
//(6) calculate    --loss function V
//(7) transDtod    --change Double array to double array
//(8)SecantSolver
//(9)GetSmallestV_Secant
//(10) f1xDri_Secant &&  f2xDri_Secant && f3xDri_Secant
//
//Authors: Xiaoxing Yan && Chun Chen
//Platforms:Eclipse
//MAC OS

package project3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class EKVModel {

	//find the best fit of Is, k, Vth and check quadratic convergence automatically
	//return Norm of V
	//choose 1: for unnormalized data  ---  choose 2:  for normalized data
	public static double quaNewSolver(int choose, double[] Vgs, double[] Vds, double[] Ids, double Vt, double k, double Is, double Vth, double perturbation) {
		int counter=0;
		double NormV=0.0;
		double delt=0.0;
		double deltc = 0.0;
		double[][] H = new double[3][3];
		ArrayList<Double> normV = new ArrayList<Double>();
		ArrayList<Double> normDelt = new ArrayList<Double>();
		
		do {

			//choose =1  for Is;  //choose =2  for k; //choose =3  for Vth;
			H[0][0] = f1xDri(1,perturbation, Vgs,  Vds,  Ids,  k,  Vt,  Is, Vth);
			H[0][1] = f1xDri(2,perturbation, Vgs,  Vds,  Ids,  k,  Vt,  Is, Vth);
			H[0][2] = f1xDri(3,perturbation, Vgs,  Vds,  Ids,  k,  Vt,  Is, Vth);
			H[1][0] = f2xDri(1,perturbation, Vgs,  Vds,  Ids,  k,  Vt,  Is, Vth);
			H[1][1] = f2xDri(2,perturbation, Vgs,  Vds,  Ids,  k,  Vt,  Is, Vth);
			H[1][2] = f2xDri(3,perturbation, Vgs,  Vds,  Ids,  k,  Vt,  Is, Vth);
			H[2][0] = f3xDri(1,perturbation, Vgs,  Vds,  Ids,  k,  Vt,  Is, Vth);
			H[2][1] = f3xDri(2,perturbation, Vgs,  Vds,  Ids,  k,  Vt,  Is, Vth);
			H[2][2] = f3xDri(3,perturbation, Vgs,  Vds,  Ids,  k,  Vt,  Is, Vth);

			double[] param = new double[3];
			double[] result = new double[3];
			result[0] = -f1(Vgs, Vds, Ids,  k,  Vt,  Is,  Vth);
			result[1] = -f2(Vgs, Vds, Ids,  k,  Vt,  Is,  Vth);
			result[2] = -f3(Vgs, Vds, Ids,  k,  Vt,  Is,  Vth);
			param = matrixSolver.SLUSolver(H, result);


			//line search
			double t=1;
			double newIs = Is + param[0];
			double newk  =  k+param[1];
			double newVth = Vth + param[2];
			double newIs2 = Is + (t/2)*param[0];
			double newk2  =  k+(t/2)*param[1];
			double newVth2 = Vth + (t/2)*param[2];
			double v1 = V(choose, Vgs,  Vds, Ids,  newk,  Vt, newIs,  newVth);
			double v2 = V(choose, Vgs,  Vds, Ids,  newk2,  Vt, newIs2,  newVth2);
			while(v1>v2) {
				t=t/2;
				v1=v2;
				newIs2 = Is + (t/2)*param[0];
				newk2  =  k+(t/2)*param[1];
				newVth2 = Vth + (t/2)*param[2];
				v2 = V(choose,Vgs,  Vds, Ids,  newk2,  Vt, newIs2,  newVth2);
			}
			newIs = Is + t*param[0];
			newk  =  k+t*param[1];
			newVth = Vth + t*param[2];

			counter++;

			switch(choose) {
			//for unnormalized data
			case 1:
				NormV = V( 1, Vgs,  Vds, Ids, newk,  Vt,  newIs,  newVth);
				break;

			//for normalized data
			case 2:
				NormV = V( 2, Vgs,  Vds, Ids, newk,  Vt,  newIs,  newVth);
				break;

			}

			double temp1 = Math.pow(param[0],2)/Math.pow(Is, 2);
			double temp2 = Math.pow(param[1],2)/Math.pow(k, 2);
			double temp3 = Math.pow(param[2],2)/Math.pow(Vth, 2);
			delt= temp1+temp2+temp3;
			double temp = Math.pow(t*param[0],2)+Math.pow(t*param[1],2)+Math.pow(t*param[2],2);
			deltc = Math.pow(temp, 0.5);
		

			//update
			Is = newIs;
			k = newk;
			Vth = newVth;
            normV.add(NormV);
            normDelt.add(delt);

			if(counter>=100) {

				System.out.println("this does not converge when counter = 100");
				System.out.println("the Is is "+Is+", the k is "+k+", the Vth is "+Vth+ ", the second norm of v is "+NormV+", the delt is " +delt);

				break;
			}
		}while(NormV>Math.pow(10, -7)&&deltc>Math.pow(10, -7));

		//check quadratic convergence
		if(counter>3) {
			checkQuaConvergence( normV, normDelt, 0, 0);
		}
		System.out.println("the counter is "+ counter);
		System.out.println("the Is is "+Is+", the k is "+k+", the Vth is "+Vth+ ", the second norm of v is "+NormV+", the delt is " +delt);
		return NormV;

	}
	
	
	public static void checkQuaConvergence(ArrayList<Double> normV, ArrayList<Double> normDelt, double trueV, double trueDelt) {

		int index= normV.size()-1;
		//check the last three elements

		double val1 = normV.get(index)-trueV;
		double val2 =normV.get(index-1)-trueV;
		double val3 =normV.get(index-2)-trueV;

		double term1 = val1/Math.pow(val2, 2);
		double term2 = val2/Math.pow(val3, 2);
		//If it has quadratic convergence, the difference will close to a constant
		if((term1-term2)<Math.pow(10, -7)) {

			System.out.println("V has quadratic convergence");
		}else {
			System.out.println("V does not have quadratic convergence");
		}

		val1 = normDelt.get(index)-trueDelt;
		val2 =normDelt.get(index-1)-trueDelt;
		val3 =normDelt.get(index-2)-trueDelt;

		term1 = val1/Math.pow(val2, 2);
		term2 = val2/Math.pow(val3, 2);

		if((term1-term2)<Math.pow(10, -7)) {
			System.out.println("Delt has quadratic convergence");
		}else {
			System.out.println("Delt does not have quadratic convergence");
		}


	}
	

	//Get Smallest V according to different Is,k,Vth
	public static double GetSmallestV(int choose, double[] Vgs, double[] Vds, double[] Ids, double Vt,double[] IsArray, double[] kArray, double[] VthArray,double perturbation) {
		int size = IsArray.length*kArray.length*VthArray.length;
		double[] Varray = new double[size];
		double[] isTemp = new double[size];
		double[] kTemp = new double[size];
		double[] VthTemp = new double[size];
		Map<Double, Integer> relation = new HashMap<Double, Integer>();//to retrieve three parameters according to v
		int counter=0;
		for(int i=0;i< IsArray.length;i++) {
			for(int j=0;j<kArray.length;j++) {
				for(int z=0;z<VthArray.length;z++) {
					double nowIs=IsArray[i];
					double nowk =kArray[j];
					double nowVth = VthArray[z];
					double value =quaNewSolver(choose,Vgs,  Vds,  Ids,  Vt, nowk, nowIs,  nowVth,  perturbation);
					Varray[counter] =value;
					relation.put(value, counter);
					isTemp[counter] = nowIs;
					kTemp[counter] = nowk;
					VthTemp[counter] = nowVth;
					counter++;
				}
			}
		}

		Arrays.sort(Varray);//sort array, get the smallest element
		System.out.println("the smallest V is "+Varray[0]);
		counter = relation.get(Varray[0]);
		System.out.println("the Is is "+isTemp[counter]+", k is "+kTemp[counter]+", Vth is "+VthTemp[counter] );
		return  Varray[0];

	}


	//f1= d(V/Is) -- for all points
	public static double f1(double[] Vgs, double[] Vds, double[] Ids, double k, double Vt, double Is, double Vth) {
		int size=Vgs.length;
		double result = 0.0;
		for(int i=0;i<size;i++) {
			//d(Id)/d(Is)
			double a=((Vgs[i]-Vth)*k)/(2*Vt);
			double a1 = 1+Math.exp(a);
			double a2 = Math.pow(Math.log(a1),2);

			double b1 = 1+Math.exp(a-Vds[i]/(2*Vt));
			double b2 = Math.pow(Math.log(b1),2);
			//f1
			double IdIs = a2-b2;
			double Id = Id(Vgs[i], Vds[i], k, Vt, Is, Vth);
			double temp = 2*Id* IdIs-2*Ids[i]*IdIs;

			result = result+temp;

		}
		return result;
	}

	//the partial derivative of f1 with respect to Is or k or Vth
	//choose =1  for Is;
	//choose =2  for k;
	//choose =3  for Vth;
	public static double f1xDri(int choose,double perturbation, double[] Vgs, double[] Vds, double[] Ids, double k, double Vt, double Is, double Vth) {
		double term1=0.0;
		double term2=0.0;
		switch(choose) {
		case 1:
			term1 = f1( Vgs,Vds, Ids,  k,  Vt,  Is*(1+perturbation),  Vth);
			term2 = f1( Vgs,Vds, Ids,  k,  Vt,  Is,  Vth);
			if(Is!=0) {//in case
				return (term1-term2)/(Is*perturbation);
			}else {
				return (term1-term2)/(perturbation);
			}

		case 2:
			term1 = f1( Vgs,Vds, Ids,  k*(1+perturbation),  Vt,  Is,  Vth);
			term2 = f1( Vgs,Vds, Ids,  k,  Vt,  Is,  Vth);
			if(k!=0) {
				return (term1-term2)/(k*perturbation);
			}else {
				return (term1-term2)/(perturbation);
			}
		case 3:
			term1 = f1( Vgs,Vds, Ids,  k,  Vt,  Is,  Vth*(1+perturbation));
			term2 = f1( Vgs,Vds, Ids,  k,  Vt,  Is,  Vth);
			if(Vth!=0) {
				return (term1-term2)/(Vth*perturbation);
			}else {
				return (term1-term2)/(perturbation);
			}

		}
		return 0;

	}

	//f2=d(V/k) -- for all points 
	public static double f2(double[] Vgs, double[] Vds, double[] Ids, double k, double Vt, double Is, double Vth) {
		int size=Vgs.length;
		double result = 0.0;
		for(int i=0;i<size;i++) {
			//d(Id)/d(k)
			double a=((Vgs[i]-Vth)*k)/(2*Vt);
			double a1 = 1+Math.exp(a);
			double a2 = Math.log(a1);
			double a3 = Is*a2;

			double b=a-(Vds[i]/(2*Vt));
			double b1 = 1+Math.exp(b);
			double b2 = Math.log(b1);
			double b3 = Is*b2;

			double term1 = 2*a3*(1/a1)*(a1-1)*(a/k);
			double term2 = 2*b3*(1/b1)*(b1-1)*(a/k);//this is also a

			double IdK = term1 - term2;

			//f2
			double Id = Id(Vgs[i], Vds[i], k, Vt, Is, Vth);
			double temp = 2*Id* IdK-2*Ids[i]*IdK;

			result = result+temp;

		}
		return result;
	}
	
	//the partial derivative of f2 with respect to Is or k or Vth
	//choose =1  for Is;
	//choose =2  for k;
	//choose =3  for Vth;
	public static double f2xDri(int choose,double perturbation, double[] Vgs, double[] Vds, double[] Ids, double k, double Vt, double Is, double Vth) {
		double term1=0.0;
		double term2=0.0;
		switch(choose) {
		case 1:

			if(Is!=0) {
				term1 = f2( Vgs,Vds, Ids,  k,  Vt,  Is*(1+perturbation),  Vth);
				term2 = f2( Vgs,Vds, Ids,  k,  Vt,  Is,  Vth);
				return (term1-term2)/(Is*perturbation);
			}else {
				term1 = f2( Vgs,Vds, Ids,  k,  Vt,  perturbation,  Vth);
				term2 = f2( Vgs,Vds, Ids,  k,  Vt,  Is,  Vth);
				return (term1-term2)/(perturbation);
			}

		case 2:

			if(k!=0) {
				term1 = f2( Vgs,Vds, Ids,  k*(1+perturbation),  Vt,  Is,  Vth);
				term2 = f2( Vgs,Vds, Ids,  k,  Vt,  Is,  Vth);
				return (term1-term2)/(k*perturbation);
			}else {
				term1 = f2( Vgs,Vds, Ids, perturbation,  Vt,  Is,  Vth);
				term2 = f2( Vgs,Vds, Ids,  k,  Vt,  Is,  Vth);
				return (term1-term2)/(perturbation);
			}
		case 3:

			if(Vth!=0) {
				term1 = f2( Vgs,Vds, Ids,  k,  Vt,  Is,  Vth*(1+perturbation));
				term2 = f2( Vgs,Vds, Ids,  k,  Vt,  Is,  Vth);
				return (term1-term2)/(Vth*perturbation);
			}else {
				term1 = f2( Vgs,Vds, Ids,  k,  Vt,  Is,  perturbation);
				term2 = f2( Vgs,Vds, Ids,  k,  Vt,  Is,  Vth);
				return (term1-term2)/(perturbation);
			}

		}
		return 0;

	}

	//f3=d(V/Vth) --for all points
	public static double f3(double[] Vgs, double[] Vds, double[] Ids, double k, double Vt, double Is, double Vth) {
		int size=Vgs.length;
		double result = 0.0;
		for(int i=0;i<size;i++) {
			//d(Id)/d(Vth)
			double a=((Vgs[i]-Vth)*k)/(2*Vt);
			double a1 = 1+Math.exp(a);
			double a2 = Math.log(a1);
			double a3 = Is*a2;

			double b=a-Vds[i]/(2*Vt);
			double b1 = 1+Math.exp(b);
			double b2 = Math.log(b1);
			double b3 = Is*b2;

			double coe = -k/(2*Vt);

			double term1 = 2*a3*(1/a1)*(a1-1)*coe;
			double term2 = 2*b3*(1/b1)*(b1-1)*coe;//this is also a

			double IdVth = term1 - term2;

			//f3
			double Id = Id(Vgs[i], Vds[i], k, Vt, Is, Vth);
			double temp = 2*Id* IdVth-2*Ids[i]*IdVth;

			result = result+temp;

		}
		return result;
	}
	
	//the partial derivative of f3 with respect to Is or k or Vth
	//choose =1  for Is;
	//choose =2  for k;
	//choose =3  for Vth;
	public static double f3xDri(int choose,double perturbation, double[] Vgs, double[] Vds, double[] Ids, double k, double Vt, double Is, double Vth) {
		double term1=0.0;
		double term2=0.0;
		switch(choose) {
		case 1:

			if(Is!=0) {
				term1 = f3( Vgs,Vds, Ids,  k,  Vt,  Is*(1+perturbation),  Vth);
				term2 = f3( Vgs,Vds, Ids,  k,  Vt,  Is,  Vth);
				return (term1-term2)/(Is*perturbation);
			}else {
				term1 = f3( Vgs,Vds, Ids,  k,  Vt,  perturbation,  Vth);
				term2 = f3( Vgs,Vds, Ids,  k,  Vt,  Is,  Vth);
				return (term1-term2)/(perturbation);
			}


		case 2:

			if(k!=0) {
				term1 = f3( Vgs,Vds, Ids,  k*(1+perturbation),  Vt,  Is,  Vth);
				term2 = f3( Vgs,Vds, Ids,  k,  Vt,  Is,  Vth);
				return (term1-term2)/(k*perturbation);
			}else {
				term1 = f2( Vgs,Vds, Ids, perturbation,  Vt,  Is,  Vth);
				term2 = f2( Vgs,Vds, Ids,  k,  Vt,  Is,  Vth);
				return (term1-term2)/(perturbation);
			}

		case 3:

			if(Vth!=0) {
				term1 = f3( Vgs,Vds, Ids,  k,  Vt,  Is,  Vth*(1+perturbation));
				term2 = f3( Vgs,Vds, Ids,  k,  Vt,  Is,  Vth);
				return (term1-term2)/(Vth*perturbation);
			}else {
				term1 = f3( Vgs,Vds, Ids,  k,  Vt,  Is,  perturbation);
				term2 = f3( Vgs,Vds, Ids,  k,  Vt,  Is,  Vth);
				return (term1-term2)/(perturbation);
			}


		}
		return 0;

	}


	//Id model--for one point
	//return calculated Id
	public static double Id(double Vgs, double Vds, double k, double Vt, double Is, double Vth) {

		double a=((Vgs-Vth)*k)/(2*Vt);
		double a1 = 1+Math.exp(a);
		double a2 = Math.pow(Math.log(a1),2);
		double a3 = Is*a2;

		double b1 = 1+Math.exp(a-Vds/(2*Vt));
		double b2 = Math.pow(Math.log(b1),2);
		double b3 = Is*b2;

		double Id = a3-b3;

		return Id;
	}

	//objective function V--for all points
	//choose 1: for unnormalized data  ---  choose 2:  for normalized data
	public static double V(int choose, double[] Vgs, double[] Vds, double[] Ids, double k, double Vt, double Is, double Vth) {
		int size=Vgs.length;
		double V = 0.0;
		for(int i=0;i<size;i++) {
			switch(choose) {
			//for unnormalized data
			case 1:
				double Id = Id(Vgs[i], Vds[i], k, Vt, Is, Vth);
				double temp = Math.pow(Id-Ids[i], 2);
				V= V+ temp;
				break;
				//for normalized data
			case 2:
				Id = Id(Vgs[i], Vds[i], k, Vt, Is, Vth);
				Id = Id/Ids[i];
				temp = Math.pow(Id-1, 2);
				V= V+ temp;
				break;
			}
		}
		return V; 
	}

	public static double[] transDtod(Double[] array1) {
		int length = array1.length;
		double[] array2 = new double[length];
		for(int i =0;i<length;i++) {
			array2[i] = array1[i];
		}
		return array2;
	}
	

	//functions for Secant method
	public static double SecantSolver(int if_N, double[] Vgs, double[] Vds, double[] Ids, double Vt, double k,
			double Is, double Vth, double k_1, double Is_1, double Vth_1) {
		// Here, if_N is for choosing normalize or non-normalize
		int counter = 0;
		double NormV = 0.0;
		double delt = 0.0;
		double deltc = 0.0;
		// create matrix H
		double[][] H = new double[3][3];
		ArrayList<Double> normV = new ArrayList<Double>();
		ArrayList<Double> normDelt = new ArrayList<Double>();
		
		do {		
			// choose =1 for Is; //choose =2 for k; //choose =3 for Vth;
			H[0][0] = f1xDri_Secant(1, Vgs, Vds, Ids, k, Vt, Is, Vth, Is_1, k_1, Vth_1);
			H[1][1] = f2xDri_Secant(2, Vgs, Vds, Ids, k, Vt, Is, Vth, Is_1, k_1, Vth_1);
			H[2][2] = f3xDri_Secant(3, Vgs, Vds, Ids, k, Vt, Is, Vth, Is_1, k_1, Vth_1);

			double[] param = new double[3];
			double[] result = new double[3];

			result[0] = -f1(Vgs, Vds, Ids, k, Vt, Is, Vth);
			result[1] = -f2(Vgs, Vds, Ids, k, Vt, Is, Vth);
			result[2] = -f3(Vgs, Vds, Ids, k, Vt, Is, Vth);
			
			param = matrixSolver.SLUSolver(H, result);

			// line search
			double t = 1;
			double newIs = Is + param[0];
			double newk = k + param[1];
			double newVth = Vth + param[2];
			double newIs2 = Is + (t / 2) * param[0];
			double newk2 = k + (t / 2) * param[1];
			double newVth2 = Vth + (t / 2) * param[2];
			double v1 = V(if_N, Vgs, Vds, Ids, newk, Vt, newIs, newVth);
			double v2 = V(if_N, Vgs, Vds, Ids, newk2, Vt, newIs2, newVth2);
			while (v1 > v2) {
				t = t / 2;
				v1 = v2;
				newIs2 = Is + (t / 2) * param[0];
				newk2 = k + (t / 2) * param[1];
				newVth2 = Vth + (t / 2) * param[2];
				v2 = V(if_N, Vgs, Vds, Ids, newk2, Vt, newIs2, newVth2);
			}

			// update
			Is = Is_1;
			k = k_1;
			Vth = Vth_1;

			Is_1 = Is_1 + t * param[0];
			k_1 = k_1 + t * param[1];
			Vth_1 = Vth_1 + t * param[2];

			switch (if_N) {
			// for unnormalized data
			case 1:
				NormV = V(1, Vgs, Vds, Ids, k_1, Vt, Is_1, Vth_1);
				break;

			// for normalized data
			case 2:
				NormV = V(2, Vgs, Vds, Ids, k_1, Vt, Is_1, Vth_1);
				break;
			}

			// System.out.println("the second norm of v is " + NormV);
			double temp1 = Math.pow(param[0], 2) / Math.pow(Is_1, 2);
			double temp2 = Math.pow(param[1], 2) / Math.pow(k_1, 2);
			double temp3 = Math.pow(param[2], 2) / Math.pow(Vth_1, 2);
			delt = temp1 + temp2 + temp3;

			double temp = Math.pow(t * param[0], 2) + Math.pow(t * param[1], 2) + Math.pow(t * param[2], 2);
			deltc = Math.pow(temp, 0.5);
			
			counter++;
			normV.add(NormV);
	        normDelt.add(delt);


			if (counter >= 100) {
				System.out.println("this does not converge when counter = 100");
				System.out.println("the Is is " + Is + ", the k is " + k + ", the Vth is " + Vth
						+ ", the second norm of v is " + NormV + ", the delt is " + delt);
				break;
			}

		} while (NormV > Math.pow(10, -7) && deltc > Math.pow(10, -7));
		
		// check quadratic convergence
		if (counter > 3) {
			checkQuaConvergence(normV, normDelt, 0, 0);
		}
		System.out.println("the counter is " + counter);
		System.out.println("the Is is "+Is+", the k is "+k+", the Vth is "+Vth+ ", the second norm of v is "+NormV+", the delt is " +delt);

		return NormV;
	}
	
	// Get Smallest V according to different initial guess
	public static double GetSmallestV_Secant(int if_N, double[] Vgs, double[] Vds, double[] Ids, double Vt,
			double[] IsArray, double[] kArray, double[] VthArray, double[] kArray_1, double[] IsArray_1,
			double[] VthArray_1) {
		int size = IsArray.length * kArray.length * VthArray.length;
		double[] Varray = new double[size];
		double[] isTemp = new double[size];
		double[] kTemp = new double[size];
		double[] VthTemp = new double[size];
		Map<Double, Integer> relation = new HashMap<Double, Integer>();// to retrieve three parameters according to v
		int counter = 0;
		for (int i = 0; i < IsArray.length; i++) {
			for (int j = 0; j < kArray.length; j++) {
				for (int z = 0; z < VthArray.length; z++) {
					double nowIs = IsArray[i];
					double nowk = kArray[j];
					double nowVth = VthArray[z];
					double nowIs_1 = IsArray_1[i];
					double nowk_1 = kArray_1[j];
					double nowVth_1 = VthArray_1[z];
					double value = SecantSolver(if_N, Vgs, Vds, Ids, Vt, nowk, nowIs, nowVth, nowk_1, nowIs_1,
							nowVth_1);
					if (value == value) {
						Varray[counter] = value;
						relation.put(value, counter);
					} else {
						Varray[counter] = Math.pow(10, 10);
						relation.put(Math.pow(10, 10), counter);
					}

					isTemp[counter] = nowIs;
					kTemp[counter] = nowk;
					VthTemp[counter] = nowVth;
					counter++;
				}
			}
		}

		Arrays.sort(Varray);// sort array, get the smallest element
		System.out.println("the smallest V is " + Varray[0]);
		counter = relation.get(Varray[0]);
		System.out
				.println("the Is is " + isTemp[counter] + ", k is " + kTemp[counter] + ", Vth is " + VthTemp[counter]);
		return Varray[0];
	}

	// choose =1 for Is; // choose =2 for k; // choose =3 for Vth;
	public static double f1xDri_Secant(int choose, double[] Vgs, double[] Vds, double[] Ids, double k, double Vt,
			double Is, double Vth, double Is_1, double k_1, double Vth_1) {
		double term1 = 0.0;
		double term2 = 0.0;
		switch (choose) {
		case 1:
			term1 = f1(Vgs, Vds, Ids, k, Vt, Is_1, Vth);
			term2 = f1(Vgs, Vds, Ids, k, Vt, Is, Vth);
			// if (Is_1 != Is)
			try {
				return (term1 - term2) / (Is_1 - Is);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		case 2:
			term1 = f1(Vgs, Vds, Ids, k_1, Vt, Is, Vth);
			term2 = f1(Vgs, Vds, Ids, k, Vt, Is, Vth);
			// if (Is_1 != Is)
			try {
				return (term1 - term2) / (k_1 - k);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		case 3:
			term1 = f1(Vgs, Vds, Ids, k, Vt, Is, Vth_1);
			term2 = f1(Vgs, Vds, Ids, k, Vt, Is, Vth);
			// if (Is_1 != Is)
			try {
				return (term1 - term2) / (Vth_1 - Vth);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		return 0;
	}
	
	// choose =1 for Is; // choose =2 for k; // choose =3 for Vth;
	public static double f2xDri_Secant(int choose, double[] Vgs, double[] Vds, double[] Ids, double k, double Vt,
			double Is, double Vth, double Is_1, double k_1, double Vth_1) {
		double term1 = 0.0;
		double term2 = 0.0;
		switch (choose) {
		case 1:
			term1 = f2(Vgs, Vds, Ids, k, Vt, Is_1, Vth);
			term2 = f2(Vgs, Vds, Ids, k, Vt, Is, Vth);
			// if (Is_1 != Is)
			try {
				return (term1 - term2) / (Is_1 - Is);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		case 2:
			term1 = f2(Vgs, Vds, Ids, k_1, Vt, Is, Vth);
			term2 = f2(Vgs, Vds, Ids, k, Vt, Is, Vth);
			// if (Is_1 != Is)
			try {
				return (term1 - term2) / (k_1 - k);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		case 3:
			term1 = f2(Vgs, Vds, Ids, k, Vt, Is, Vth_1);
			term2 = f2(Vgs, Vds, Ids, k, Vt, Is, Vth);
			// if (Is_1 != Is)
			try {
				return (term1 - term2) / (Vth_1 - Vth);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		return 0;
	}
	
	// choose =1 for Is; // choose =2 for k; // choose =3 for Vth;
	public static double f3xDri_Secant(int choose, double[] Vgs, double[] Vds, double[] Ids, double k, double Vt,
			double Is, double Vth, double Is_1, double k_1, double Vth_1) {
		double term1 = 0.0;
		double term2 = 0.0;
		switch (choose) {
		case 1:
			term1 = f3(Vgs, Vds, Ids, k, Vt, Is_1, Vth);
			term2 = f3(Vgs, Vds, Ids, k, Vt, Is, Vth);
			// if (Is_1 != Is)
			try {
				return (term1 - term2) / (Is_1 - Is);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		case 2:
			term1 = f3(Vgs, Vds, Ids, k_1, Vt, Is, Vth);
			term2 = f3(Vgs, Vds, Ids, k, Vt, Is, Vth);
			// if (Is_1 != Is)
			try {
				return (term1 - term2) / (k_1 - k);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		case 3:
			term1 = f3(Vgs, Vds, Ids, k, Vt, Is, Vth_1);
			term2 = f3(Vgs, Vds, Ids, k, Vt, Is, Vth);
			// if (Is_1 != Is)
			try {
				return (term1 - term2) / (Vth_1 - Vth);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		return 0;
	}

}
