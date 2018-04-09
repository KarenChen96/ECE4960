package hp_lecture6;

public class l6_hp1 {
	public static double ground(double x) {
		double y = Math.exp(-x);
		return y;
	}

	public static double backward(double t, double d_t) {
		double y = ground(t - d_t) / (1 + d_t);
		return y;
	}

	public static double trap(double t, double d_t) {
		double y = ground(t - d_t) * (2 - d_t) / (2 + d_t);
		return y;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		double[] d_t = {0.5, 0.1};
		for (int t = 0; t <= 2; t++) {
			System.out.println("t is: " + t);
			for (int i = 0; i < d_t.length; i++) {
				System.out.println("delta(t) is: " + d_t[i]);
				System.out.println("ground truth is " + ground(t));
				double err_back = Math.abs(backward(t, d_t[i]) - ground(t));
				double err_trap = Math.abs(trap(t, d_t[i]) - ground(t));
				System.out.println("The error in Backward Euler is: " + err_back);
				System.out.println("The error in Trapezoidal Euler is: " + err_trap);
				//same delta t, t increases, error in backward euler is getting smaller
				//same t,delta t increases, error in back is getting larger
			}
		}
	}
}
