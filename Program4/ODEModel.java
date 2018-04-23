//Date:
//Created on 4/20/2018
//
//functions:
//(1) ODESolver -- function to solve the generic ODE
//(2) increFunc -- return increment function * h
//(3) fxSolver --  choose different fx according to different x[i]
//(4) RK34Mode -- solution for RK34
//(5) RK34_changeH -- adaptive h
//(6) normalizedErr -- calculated normalized error
//(7) RK3
//(8) RK4
//(9) it(time) -- calculate current 
//(10) groundTruth
//(11) Id_ekv 
//
//Authors: Xiaoxing Yan & Chun Chen
//Platforms:Eclipse
//MAC OS


package project4;

import java.util.Arrays;

public class ODEModel {

	//function to solve the generic ODE 
	//input: initX, initT, step size h and choose(is to choose different increment function)
	//output: newX
	//choose 1 : one step method
	//choose 2: forward euler
	//choose 3: RK34
	//choose 4: RK4
	public static double[] ODESolver(int choose,double[] initX, double t, double h,double eR,double eA) {

		//increment function * h
		double[] temp = increFunc(choose, initX, t, h,eR,eA);
		//update X
		double[] result = rowFunction.addVector(initX,temp);
		return result;

	}

	//return increment function * h
	//choose 1 : one step method
	//choose 2: forward euler
	//choose 3: RK34
	//choose 4: RK4
	public static double[] increFunc(int choose,double[] initX, double t, double h,double eR,double eA) {

		int rank = initX.length;

		switch(choose) {

		case 1:// one step method
			//predictor
			double[] temp = new double[rank];
			for(int i =0;i< rank;i++) {
				temp[i] = fxSolver(i,initX, t,h);
			}
			temp = rowFunction.kMultiVector(temp,h);
			double[] newX = rowFunction.addVector(initX, temp);
			//increment coefficient
			double[] coe = rowFunction.addVector(initX,newX);
			coe = rowFunction.kMultiVector(coe,0.5*h);
			return coe;

		case 2://forward euler	
			double[] temp1 = new double[rank];
			for(int i =0;i< rank;i++) {
				temp1[i] = fxSolver(i,initX, t,h);
			}
			temp1 = rowFunction.kMultiVector(temp1,h);
			return temp1;

		case 3://RK34
			return RK34Model(initX, t,h,eR,eA);

		case 4://RK4
			double[] tempX = new double[initX.length];
			tempX =  RK4(initX, t, h);
			tempX = rowFunction.subtractionVector(tempX,initX);
			return tempX;
		}
		double[] result = new double[] {-1};
		return result;
	}

	//Choose different fx -- for different x[i]
	//output: fx for x[i]
	public static double fxSolver(int choose, double[] x, double t, double h) {

		//parameters for task4
		//V1 = x[0];  V2 = x[1];
		//double[] result = new double[length];
		int length = x.length;

		double R1 = 10000; //ohm
		double R2 =10000;
		double R3 = 10000;
		double C1 = 1E-12;//F
		double C2 = 1E-12;

		//parameters used in task5
		double vdd = 5;
		double rg = 1.0E4;
		double rl = 1.0E4;
		double c1 = 1.0E-12;
		double c2 = 1.0E-12;

		switch(choose) {
		case 0:
			//for validation
			//
			//double term1 = 4*Math.exp(0.8*t);
			//return term1 - 0.5*x[0];

			//for task4
			//double term1 = -(1.0/(C1*R1)+ 1.0/(C1*R2));
			//double term2 = 1.0/(C1*R2);
			//return  term1*x[0]+term2*x[1] +it(t)/C1;

			//for task5
			//reminder: Vin(t)/rg = Iin(t)
			double v1t = -x[0]/(rg*c1) + it(t) / c1; 
			return v1t;


		case 1:
			//for task4
			//term1 =1.0/(C2*R2);
			//term2 = -(1.0/(C2*R2)+ 1.0/(C2*R3));
			//return term1*x[0]+term2*x[1]; 

			//for task5
			double v2t = -Id_ekv(x)/c2 - x[1]/(rl*c2) + vdd/(rl*c2);
			return v2t;
		}
		return 0;
	}
	

	//Solution for RK34
	//return an array of (newX - initX)  
	public static double[] RK34Model(double[] initX, double t, double h,double eR, double eA) {

		double[] result = new double[initX.length];
		double[] newX = RK34_changeH(initX, t, h, eR, eA);
		result = rowFunction.subtractionVector(newX,initX);
		return result;
	}


	//Adaptive h
	//return a double array =[newx];
	public static double[] RK34_changeH( double[] initX, double ti, double h, double eR, double eA) {
		double[] x = new double[initX.length];
		x = Arrays.copyOf(initX, initX.length);
		double r=0;
		double t=0; // t is a deviation
		double h1 = 0;
		double oldH = h * 1.0E9;
		double[] x_rk4 = new double[initX.length];
		while(t<oldH) {
			double[] x_rk3 = RK3(x,  ti, h);
			x_rk4 = RK4(x,  ti, h);
			r =normalizedErr(x_rk3, x_rk4, eR, eA);
			while(Math.abs(r-1)>0.01 || Math.abs(r-1)<Math.pow(10, -6)) {
				//update h
				double k = Math.pow(r, 1.0/2.0);
				h = h/k;
				h1 = h * 1.0E9;
				//overshoot -- which must happen, every loop breaks in through this step
				if(h1+t>oldH) {
					//discard this h, keep h in the last step
					break;
				}
				x_rk3 = RK3(x, ti, h);
				x_rk4 = RK4(x, ti, h);
				r = normalizedErr(x_rk3, x_rk4, eR, eA);
			}

			ti = ti+h1; //update the start point 
			t = t+h1;//for overshoot
			x = x_rk4;
			h1 = 1-t;//wants to predict in full step next time
			h = h1 * 1.0E-9;
		}

		return x;
	}

	//Calculate normalized error
	public static double normalizedErr(double[] rk3, double[] rk4,double eR, double eA) {	
		double term1 = test.secondNorm(rk3, rk4);
		double term2 = test.secondNorm(rk4);
		double estimatedErr = Math.abs(term1);
		double temp = (eR * Math.abs(term2) + eA);
		double result= estimatedErr/temp;
		return result;
	}

	//return X(i+1) using RK3 method
	public static double[] RK3(double[] x, double t, double h) {
		int rank = x.length;
		double[] tempx = new double[x.length];
		//k1
		double[] k1  = new double[rank];
		for(int i=0;i<rank;i++) {
			k1[i] = fxSolver(i, x, t, h);
		}

		double h1 = h * 1.0E9;// using the same unit
		//k2
		for(int i=0;i<rank;i++) {
			tempx[i] =x[i]+ k1[i]*h/2.0;
		}
		double[] k2  = new double[rank];
		for(int i=0;i<rank;i++) {
			k2[i] = fxSolver(i, tempx, t+h1/2.0, h);	
		}

		//k3
		for(int i=0;i<rank;i++) {
			tempx[i] =x[i]+ k2[i]*h*3/4.0;
		}
		double[] k3  = new double[rank];
		for(int i=0;i<rank;i++) {
			k3[i] = fxSolver(i, tempx, t+3*h1/4.0, h);	
		}

		for(int i=0;i<rank;i++) {
			tempx[i] = x[i]+(1.0/9.0)*(2.0*k1[i]+3.0*k2[i]+4.0*k3[i])*h;
		}
		return tempx;


	}

	//return newX using RK4 method for element x[i]
	public static double[] RK4(double[] x, double t, double h) {
		int rank = x.length;
		double[] tempx = new double[x.length];
		
		//k1
		double[] k1  = new double[rank];
		for(int i=0;i<rank;i++) {
			k1[i] = fxSolver(i, x, t,  h);
		}	

		double h1 = h * 1.0E9;
		//k2
		for(int i=0;i<rank;i++) {
			tempx[i] =x[i]+ k1[i]*h/2.0;
		}
		double[] k2  = new double[rank];
		for(int i=0;i<rank;i++) {
			k2[i] = fxSolver(i, tempx, t+h1/2.0, h);	
		}

		//k3
		for(int i=0;i<rank;i++) {
			tempx[i] =x[i]+ k2[i]*h*3/4.0;
		}

		double[] k3  = new double[rank];
		for(int i=0;i<rank;i++) {
			k3[i] = fxSolver(i, tempx, t+3*h1/4.0, h);	
		}

		//k4
		for(int i=0;i<rank;i++) {
			tempx[i] =x[i]+ k3[i]*h;
		}

		double[] k4  = new double[rank];
		for(int i=0;i<rank;i++) {
			k4[i] = fxSolver(i, tempx, t+h1, h);	
		}

		for(int i=0;i<rank;i++) {
			tempx[i] = x[i]+(1.0/24.0)*(7.0*k1[i]+6.0*k2[i]+8.0*k3[i]+3.0*k4[i])*h;
		}
		return tempx;	
	}


	//current source 
	//input: time (ns)
	//output: current (A)
	public static double it(double t) {

		double k = t%20;
		if(k>=0&&k<=1) {
			return k*1E-4;
		}else if(k>1&&k<=10) {
			return 1E-4;
		}else if(k>10&&k<=11) {
			return (11-k)*1E-4;
		}else {
			return 0;
		}

	}

	public static double groundTruth(double t) {
		double term1= Math.exp(0.8*t)-Math.exp(-0.5*t);
		double term2= Math.exp(-0.5*t);
		double result= term1*4/1.3+2*term2;
		return result;
	}



	//V1:Vgs V2: Vds
	public static double Id_ekv(double[] x) {
		double is = 5 * 1.0E-6;
		double k = 0.7;
		double vth = 1.0;
		double vt = 0.026;

		double vgs = x[0];
		double vds = x[1];

		double a1 = k * (vgs - vth) / (2*vt);
		double a2 = 1 + Math.exp(a1);
		double a3 = Math.pow(Math.log(a2), 2);

		double b1 = (k*(vgs-vth) - vds) / (2*vt);
		double b2 = 1 + Math.exp(b1);
		double b3 = Math.pow(Math.log(b2), 2);

		double Id_ekv = is * (a3 - b3); 

		return Id_ekv;
	}


}
