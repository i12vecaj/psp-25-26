package exprocesos;
import java.io.IOException;
import java.io.File;
public class ejercicio1 {

	public static void main(String[] args) throws IOException  {	   
		ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", "ping -n 2 google.com");
        
        pb.redirectOutput(new File("salida.txt"));
        pb.redirectErrorStream(true);
        
        pb.start();
        System.out.println("Ping ejecutado. Revisa el archivo salida.txt");
	   }
	
}
