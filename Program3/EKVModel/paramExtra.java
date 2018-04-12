//Date:
//Created on 3/29/2018
//
//functions:
//(1) paramExtraNoLog(double[] x,double[] y, double a, double m, double perturbation )
//(2) paraExtraLinearPowerLaw(double[] x, double[] y)
//(3) vx1  --loss function
//(4) f1x & f1xDri
//(5) f2x & f2xDri
//
//Authors: Xiaoxing Yan
//Platforms:Eclipse
//MAC OS



package project3;
public class paramExtra {

	//Parameter extraction of power law
	//Input: array x and array y
	//Output: array--array[0] =m, array[1] =a
	public static double[] paramExtraNoLog(double[] x,double[] y, double a, double m, double perturbation ) {

		double delta=0.0;
		double deltm=0.0;
		double[][] H  = new double[2][2];
		double[] f = new double[2];
		double f1 = 0.0;
		double f2 = 0.0;
		int counter=0;
		do {
			//calculate H
			H[0][0] = f1xDri(1, x, y, m, a, perturbation);
			H[0][1] = f1xDri(2, x, y, m, a, perturbation);
			H[1][0] = f2xDri(1, x, y, m, a, perturbation);
			H[1][1] = f2xDri(2, x, y, m, a, perturbation);

			//(-)H[X]-1
			//only for 2*2 matrix
			double coefficient=1/( H[0][0]* H[1][1] - H[0][1]* H[1][0]);
			//System.out.println("the coefficient is: "+coefficient);
			double temp = H[0][0];
			H[0][0] = H[1][1] *(-coefficient);
			H[1][1] = temp *(-coefficient);
			H[0][1] = H[0][1] *(coefficient);
			H[1][0] = H[1][0] *(coefficient);


			//calculate f1,f2
			f1 = f1x(x,  y, m, a);
			f2 = f2x(x,  y, m, a);
			f[0] =f1;
			f[1] =f2;
			//
			double[] result = rowFunction.fullmatrixProduct(H, f);
			delta = result[0];
			deltm = result[1];

			//update
			double t=1;
			double a1=a+(t/2)*delta;
			double m1=m+(t/2)*deltm;
			//line search
			double result1 = vx1(x,  y, m+deltm,  a+delta);
			double result2 = vx1(x,  y, m1,  a1);
			while(result1>result2) {
				result1=result2;
				t=t/2;
				a1=a+(t/2)*delta;
				m1=m+(t/2)*deltm;
				result2 = vx1(x,  y, m1,  a1);
			}
			delta=t*delta;
			deltm=t*deltm;
			a=a+delta;
			m=m+deltm;
			counter++;

		}while(Math.abs(delta)>Math.pow(10, -7)&&Math.abs(deltm)>Math.pow(10, -7));
		double[] result = new double[2];
		result[0] = a;
		result[1] = m;
		return result;


	}

	//loss function
	public static double vx1(double[] x, double[] y, double m, double a) {
		double sum=0.0;
		int length =x.length;
		for(int i=0;i<length;i++) {
			double term1 =a*Math.pow(x[i],m)-y[i];
			sum+=Math.pow(term1, 2);
		}
		return sum;
	}

    // f1x= d(V)/d(a)
	public static double f1x(double[] x, double[] y, double m, double a) {
		int length =x.length;
		double sum1=0.0;
		double sum2=0.0;
		for(int i=0;i<length;i++) {
			sum1+=Math.pow(x[i],2*m);
			sum2+=Math.pow(x[i],m)*y[i];
		}
		sum1=sum1*a;
		return sum1-sum2;
	}
	// f2x =f1x= d(V)/d(m)
	public static double f2x(double[] x, double[] y, double m, double a) {
		int length =x.length;
		double sum1=0.0;
		double sum2=0.0;
		for(int i=0;i<length;i++) {
			sum1+=Math.log(x[i])*Math.pow(x[i],2*m);
			sum2+=Math.log(x[i])*Math.pow(x[i],m)*y[i];
		}
		sum1=sum1*a*a;
		sum2=sum2*a;
		return sum1-sum2;
	}

	//choose =1  for a;
	//choose =2  for m;
	public static double f1xDri(int choose,double[] x,double[] y, double m, double a, double perturbation) {
		double term1=0.0;
		double term2=0.0;
		switch(choose) {
		case 1:
			term1 = f1x(x,y,m,a*(1+perturbation));
			term2 = f1x(x,y,m,a);
			return (term1-term2)/(a*perturbation);

		case 2:
			term1 = f1x(x,y,m*(1+perturbation),a);
			term2 = f1x(x,y,m,a);
			return (term1-term2)/(m*perturbation);

		}
		return 0;
	}

	//choose =1  for a;
	//choose =2  for m;
	public static double f2xDri(int choose,double[] x,double[] y, double m, double a, double perturbation) {
		double term1=0.0;
		double term2=0.0;
		switch(choose) {
		case 1:
			term1 = f2x(x,y,m,a*(1+perturbation));
			term2 = f2x(x,y,m,a);
			return (term1-term2)/(a*perturbation);

		case 2:
			term1 = f2x(x,y,m*(1+perturbation),a);
			term2 = f2x(x,y,m,a);
			return (term1-term2)/(m*perturbation);

		}
		return 0;


	}

	//Parameter extraction of power law through linear regression
	//Input: array x and array y
	//Output: array--array[0] =m, array[1] =a
	public static double[] paraExtraLinearPowerLaw(double[] x, double[] y) {
		//obtain H matrix
		double[][] H = new double[2][2];
		for(int i=0;i<10;i++) {
			double logX = Math.log(x[i]);
			H[0][0] +=Math.pow(logX, 2);
			H[0][1] += logX;
			H[1][0] += logX;
		}
		H[1][1] =x.length;
		//obtain RHS
		double[] RHS = new double[2];
		for(int i=0;i<10;i++) {
			double logX = Math.log(x[i]);
			double logY = Math.log(y[i]);
			RHS[0]+= logX*logY;
			RHS[1]+= logY;
		}
		//y=ax+b ----- logY = mlogX+loga
		//then, a= m, b = loga
		double[] am = new double[2];
		am = matrixSolver.SLUSolver(H, RHS);
		am[1] = Math.exp(am[1]);//this is exp
		return am;

	}




}
