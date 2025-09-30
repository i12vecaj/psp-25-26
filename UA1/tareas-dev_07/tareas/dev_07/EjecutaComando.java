import java.io.*;
import java.util.List;

public class EjecutaComando {
     public static void main(String[] args) throws IOException  {	   

        //ejercicio 1
	   Process pb = new ProcessBuilder("ping", "www.google.es").start(); //ping funcional
       InputStream is = pb.getInputStream();

         int c;
			 while ((c = is.read()) != -1)
				System.out.print((char) c);
			 is.close();

             //ejercicio 2

             Process pt = new ProcessBuilder("ping", "www.goo.estere")  //ping no funcional
             .redirectErrorStream(true)
             .start();

       InputStream are = pt.getInputStream();

         int b;
			 while ((b = are.read()) != -1)
				System.out.print((char) b);
			 are.close();

  //ejercicio 3

try {
    File salida = new File("salida.txt");
    ProcessBuilder pb3 = new ProcessBuilder("ping", "www.google.es");
    pb3.redirectOutput(salida);                         // redirige la salida estándar al archivo
    pb3.redirectErrorStream(true); // también redirige errores al mismo fichero

     // Ejecutamos el proceso
    Process p = pb3.start();
    int exitCode = p.waitFor();                        // esperamos a que termine

    System.out.println("Proceso terminado con código: " + exitCode);
    System.out.println("Revisa el archivo salida.txt");
} catch (IOException | InterruptedException e) {
    e.printStackTrace();
}
try {
            File listado = new File("listado.txt");  // Creamos un objeto File que representará el archivo donde se guardará la salida
            ProcessBuilder pb1;

            pb1 = new ProcessBuilder("cmd", "/c", "dir"); // Si el SO es Windows usamos "cmd /c dir"
            

            pb1.redirectOutput(listado); // Indicamos que la salida del comando se redirija al archivo
            pb1.redirectErrorStream(true);  // Mezclamos la salida de error

            Process p1 = pb1.start();// Iniciamos el proceso
            int exitCode1 = p1.waitFor(); // Esperamos a que el proceso termine y recogemos su código de salida

            if (exitCode1 == 0) {  // Si el primer proceso terminó correctamente
                ProcessBuilder pb2;

                    pb2 = new ProcessBuilder("cmd", "/c", "type listado.txt");
               

                pb2.inheritIO(); // Redirigimos la salida de este proceso directamente a la consola
                Process p2 = pb2.start(); //Hacemos lo mismo de antes
                int exitCode2 = p2.waitFor();

                System.out.println("Segundo proceso terminado con código: " + exitCode2);
            } else {
                System.out.println("El primer proceso falló, no se ejecuta el segundo."); // Si el primer proceso falló, no se ejecuta el segundo
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace(); // Capturamos y mostramos cualquier error de entrada/salida o interrupción
       }
   }
}
