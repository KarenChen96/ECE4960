
//Date:
//Created on 3/29/2018
//Edited on 4/8/2018
//
//includes:
//(1) Task2 -- a validation of parameter extraction program for y=a*math.pow(x,m)-- linear and log scale
//(2) Task4 -- obtain best fit of Is, k, Vth
//(3) Task 6 -- find the smallest V
//(4) Task 7 -- three checks
//
//Authors: Xiaoxing Yan && Chun Chen
//Platforms:Eclipse
//MAC OS

package project3;

import java.util.Random;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.IntStream;

public class mainP3 {
	public static void main(String[] Args){
		
	

		//Task 2
		//a validation of parameter extraction program for y=a*math.pow(x,m)-- linear and log scale
		//ground truth is a=10, m=-0.5
		//Smeasured with random noise 10%-20%

		//generate Smeasured
		
		double[] x = new double[] {1,10,50,100,150,180,200,220,300,500};
		double[] y = new double[10];
		double a=20;
		Random r = new Random();
		for(int i=0;i<10;i++) {
			double term =a* Math.pow(x[i], -0.5);
			double rand = r.nextInt(11)+10;
			rand=rand*0.01;
			y[i] = term*(1+rand);
		}
		double[] am = new double[2];
		//(1) Parameter extraction of power law through linear regression
		am=paramExtra.paraExtraLinearPowerLaw(x,  y);
		System.out.println("From linear regression");
		System.out.println("the power m is "+am[0]+", and the coefficient c is "+ am[1]);
		//check error
		double term1 =Math.abs(am[0]+1);
		double term2 =Math.abs(am[1]-20);
		System.out.println("the norm of m is "+ term1+", the norm of a is "+term2);

		//(2)/Parameter extraction of power law 
		double perturbation =Math.pow(10, -4);
		a =20;
		double m = -1;
		double[] result = new double[2];
		result =paramExtra.paramExtraNoLog(x,y,a,m, perturbation);
		System.out.println("From Quasi Newton method ");
		System.out.println("the power m is "+result[1]+", and the coefficient c is "+ result[0]);
		//check error
		term1 =Math.abs(result[1]+1);
		term2 =Math.abs(result[0]-20);
		System.out.println("the norm of m is "+ term1+", the norm of a is "+term2);

		//Task 4
		//Using quasi-newton method and secant method to find the best fit of Is, k and Vth

		//read the file
		ArrayList<Double> Vgs_v= new ArrayList<Double>();
		ArrayList<Double> Vds_v= new ArrayList<Double>();
		ArrayList<Double> Ids_v= new ArrayList<Double>();
		try {
			File measure= new File("src/project3/outputNMOS.txt");
			BufferedReader br_mea= new BufferedReader(new FileReader(measure));
			String st= br_mea.readLine();
			st= br_mea.readLine();
			while(st!=null) {
				String[] s= st.split("\\t");
				if(s.length==3) {
					Vgs_v.add(Double.valueOf(s[0]));
					Vds_v.add(Double.valueOf(s[1]));
					Ids_v.add(Double.valueOf(s[2]));
				}
				st= br_mea.readLine();

			}
			br_mea.close();

		}catch (Exception e) {
			System.out.println(e.getMessage());
		}

		//arraylist to array
		int size1=Vgs_v.size();
		Double[] Vgs_temp = (Double[])Vgs_v.toArray(new Double[size1]); 
		double[] Vgs = new double[size1];
		Vgs=EKVModel.transDtod(Vgs_temp);
		int size2=Vds_v.size();
		Double[] Vds_temp = (Double[])Vds_v.toArray(new Double[size2]); 
		double[] Vds = new double[size2];
		Vds=EKVModel.transDtod(Vds_temp);
		int size3=Ids_v.size();
		Double[] Ids_temp = (Double[])Ids_v.toArray(new Double[size3]); 
		double[] Ids = new double[size3];
		Ids=EKVModel.transDtod(Ids_temp);
		
		//quasi-newton method
		//init guess
		double Vt =0.026;
		double k=1;
		double Is = Math.pow(10, -7);
		double Vth = 1;
		System.out.println();
		EKVModel.quaNewSolver(1, Vgs,  Vds,  Ids,  Vt, k, Is,  Vth,  perturbation);
		
		// secant method
		// two initial guess
		double k_s = 0.9;
		double Is_s = 3 * Math.pow(10, -5);
		double Vth_s = 2.0;

		double k_s1 = 0.5;
		double Is_s1 = Math.pow(10, -8);
		double Vth_s1 = 0.8;
		EKVModel.SecantSolver(0, Vgs, Vds, Ids, Vt, k_s, Is_s, Vth_s, k_s1, Is_s1, Vth_s1);
				
		
		//Task 6
		//Unnormalized data -- Quasi Newton
		double[] IsArray = new double[]{Math.pow(10, -8),3*Math.pow(10, -8),Math.pow(10, -7),3*Math.pow(10, -7),Math.pow(10, -6),
				3*Math.pow(10, -6),Math.pow(10, -5),3*Math.pow(10, -5)};
		double[] kArray = new double[]{0.5,0.6,0.7,0.8,0.9};
		double[] VthArray = new double[] {0.8,0.9,1.0,1.1,1.2,1.3,1.4,1.5,1.6,1.7,1.8,1.9,2.0};
		System.out.println("For unnormalized data:");
		EKVModel.GetSmallestV(1, Vgs, Vds, Ids, Vt,IsArray, kArray, VthArray,perturbation);
		
		//Normalized data -- Quasi Newton
		System.out.println("For normalized data:");
		EKVModel.GetSmallestV(2, Vgs, Vds, Ids, Vt,IsArray, kArray, VthArray,perturbation);
		
		
		//create another initial guess array
		double[] IsArray_1 = new double[IsArray.length];
		double[] kArray_1 = new double[kArray.length];
		double[] VthArray_1 = new double[VthArray.length];
		for(int i =0; i< IsArray.length; i++)
		{
			for (int j = 0; j< kArray.length; j++)
			{
				for (int l = 0; l < VthArray.length; l++)
				{
					if (i == 0)
						IsArray_1[i] = IsArray[7];
					else
						IsArray_1[i] = IsArray[i - 1];
					if (j == 0)
						kArray_1[j] = kArray[4];
					else
						kArray_1[j] = kArray[j - 1];
					if (l == 0)
						VthArray_1[l] = VthArray[12];
					else
						VthArray_1[l] = VthArray[l - 1];
				}
			}
		}
		
		//Unnormalized data -- Secant Method
		EKVModel.GetSmallestV_Secant(1, Vgs, Vds, Ids, Vth, IsArray, kArray, VthArray, kArray_1, IsArray_1, VthArray_1);
		//Normalized data -- Secant Method
		EKVModel.GetSmallestV_Secant(2, Vgs, Vds, Ids, Vth, IsArray, kArray, VthArray, kArray_1, IsArray_1, VthArray_1);
		

		//Task 7
		//choose the three parameters from task 6
		k=0.9;
		Is = Math.pow(10, -6);
		Vth = 0.9;

		boolean flag1 = true;
		boolean flag2 = true;
		boolean flag3 = true;
		for(int i=0;i< Vgs.length;i++) {
		
			//check Id is an exponential function of Vgs when k<1 and Vgs < Vth
			if(Vgs[i]<Vth) {
				double IdCal =EKVModel.Id(Vgs[i],  Vds[i], Vt, k, Is,  Vth);
				double termX = Is*Math.exp(((Vgs[i]-Vth)*k)/(Vt));
				double termY = Is*Math.exp(((Vgs[i]-Vth)*k-(Vds[i]+0))/(Vt));
				double IdApprox = termX -termY;
				double norm = Math.abs(IdCal-IdApprox);
				
				if(norm>Math.pow(10, -6)) {
					System.out.println("when Vgs is "+Vgs[i]+" and Vth is 1.1, the norm is "+norm);
					flag1 =false;
				}	
			}
			
			//check Id is quadratic to Vgs when Vds > (Vgs-Vth)*k and Vgs > Vth
			double temp = (Vgs[i]-Vth)*k;
			if(Vgs[i]>Vth&&(temp/Vt)>1 && Vds[i]>temp) {
				double IdCal =EKVModel.Id(Vgs[i],  Vds[i], Vt, k, Is,  Vth);
				double termX = Is*Math.pow(((Vgs[i]-Vth)*k)/(2*Vt),2);
				double termY = Is*Math.exp(((Vgs[i]-Vth)*k-(Vds[i]+0))/(Vt));
				double IdApprox = termX -termY;
				double norm = Math.abs(IdCal-IdApprox);
				//System.out.println("when Vgs is "+Vgs[i]+" and Vth is 1.1, the norm is "+norm);
				if(norm>Math.pow(10, -2)) {
					System.out.println("when Vgs is "+Vgs[i]+" and Vds is "+Vds[i]+" and Vth is 1.1, the norm is "+norm);
					flag2 =false;
				}	
			}
			
			
			//check Id is quadratic to Vgs when Vds < (Vgs-Vth)*k and Vgs > Vth
			if(Vgs[i]>Vth && Vds[i]<temp&&(temp/Vt)>1&&((temp-Vds[i])/Vt)>1) {
				double IdCal =EKVModel.Id(Vgs[i],  Vds[i], Vt, k, Is,  Vth);
				double termX = Is*Math.pow(((Vgs[i]-Vth)*k)/(2*Vt),2);
				double termY = Is*Math.pow(((Vgs[i]-Vth)*k-(Vds[i]))/(2*Vt),2);
				double IdApprox = termX -termY;
				double norm = Math.abs(IdCal-IdApprox);
				//System.out.println("when Vgs is "+Vgs[i]+" and Vth is 1.1, the norm is "+norm);
				if(norm>Math.pow(10, -2)) {
					System.out.println("when Vgs is "+Vgs[i]+" and Vds is "+Vds[i]+" and Vth is 1.1, the norm is "+norm);
					flag3 =false;
				}	
			}
			
		}
		if(flag1 ==true) {
			System.out.println("Id is an exponential function of Vgs when k<1 and Vgs < Vth");
		}
		if(flag2 ==true) {
			System.out.println("Id is quadratic to Vgs when Vds > (Vgs-Vth)*k and Vgs > Vth");
		}
		if(flag3 ==true) {
			System.out.println("Id is quadratic to Vgs when Vds < (Vgs-Vth)*k and Vgs > Vth");
		}
			
	}
	
}
