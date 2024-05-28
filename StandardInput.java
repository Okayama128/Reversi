package reversi;
import java.util.Scanner;

public class StandardInput {
	
	public static int readInt() {
		Scanner sc = new Scanner(System.in);
		int result = sc.nextInt();
		sc.close();
		return result;
	}
//	public String readString(String str) {
//		return str;
//	}
	
}
