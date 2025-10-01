package exprocesos;
import java.io.IOException;

public class ejercicio1 {

	public static void main(String[] args) throws IOException  {	   
		ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", "ping -n 2 holahastaluego.com");
		pb.redirectErrorStream(true); 
        pb.inheritIO(); 
        pb.start();
	   }
	
}
