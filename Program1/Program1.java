package program1;

import java.io.FileWriter;
import java.io.IOException;

public class Program1 {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub	
		FileWriter out1 = new FileWriter("report1.txt");
		FileWriter out2 = new FileWriter("report2.txt");
		System.out.println("Tests for different exceptions.");
		System.out.println("");
		Exception1.main(args);
		Exception2.main(args);
		Exception3.main(args);
		Exception4.main(args);
		Exception5.main(args);
		Exception6.main(args);
		Exception7.main(args);
		Question4.main(args);
	}

}
