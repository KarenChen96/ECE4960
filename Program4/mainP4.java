//Date:
//Created on 4/20/2018
//
//Main class 
//(1) Task4 -- simulator
//(2) Task5 -- amplifier
//
//Authors: Xiaoxing Yan & Chun Chen
//Platforms:Eclipse
//MAC OS

package project4;

import java.util.Arrays;

public class mainP4 {
	public static void main(String [] Args) {

		double t=0;
		double eR =Math.pow(10, -4);
		double eA =Math.pow(10, -7);


		//Task 4 && Task 5
		//Changing task by changing differential equation in fxSolver in class ODEModel
		//with time step =0.2ns or 1 ns

		//double[] v= new double[] {0,0}; //for task4
		double[] v= new double[] {2.5,2.5}; //for task5
		double[] newV =new double[v.length];
		double hs =0.2;//ns
		double oldhs = hs;
		t=0;
		System.out.println("when step size is "+ oldhs);
		while(t<100) {
			//choose 1 : one step method;  choose 2: forward euler;  choose 3: RK34; choose 4: RK4

			newV= ODEModel.ODESolver(3,v,t,hs*1E-9,eR,eA);
			t=t+oldhs;
			System.out.println("t is "+t+",  The calculted x is "+Arrays.toString(newV));
			v=newV;
			hs = oldhs;


		}
	}


}
