package exprocesos;
import java.io.IOException;

public class ejercicio1 {

	public static void main(String[] args) throws IOException  {	   
		ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", "ping -n 2 google.com");
		   pb.start();
	 
	   }
	
}
