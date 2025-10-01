package exprocesos;
import java.io.IOException;
import java.io.File;
public class ejercicio1 {

	public static void main(String[] args) throws IOException, InterruptedException  {	   
	        File salida = new File("salida.txt");

	        
	        ProcessBuilder listar = new ProcessBuilder("cmd.exe", "/c", "dir");
	        listar.redirectOutput(salida); 
	        listar.redirectErrorStream(true); 
	        Process p1 = listar.start();
	        p1.waitFor(); 

	        System.out.println("Listado de archivos guardado en salida.txt");
	     
	        ProcessBuilder mostrar = new ProcessBuilder("cmd.exe", "/c", "type salida.txt");
	        mostrar.inheritIO(); 
	        Process p2 = mostrar.start();
	        p2.waitFor();
	   }
	
}
