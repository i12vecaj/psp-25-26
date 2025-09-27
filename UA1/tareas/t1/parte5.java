package t1;
import java.io.InputStream;
import java.util.Scanner;

public class parte5 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Introduce el comnado que quieres Ejecutar en cmd");
        String comnado  = sc.nextLine();
        sc.close();
        try {
            
            ProcessBuilder p = new ProcessBuilder("cmd.exe", "/c", comnado);
        Process proceso = p.start();

        InputStream is = proceso.getInputStream();


         int c;
			 while ((c = is.read()) != -1)
				System.out.print((char) c);
			 is.close();

             InputStream er = proceso.getErrorStream();


         int c2;
			 while ((c2 = er.read()) != -1)
				System.out.print((char) c2);
			 er.close();


        } catch (Exception e) {
            e.printStackTrace();
        }

        
    }
}

/* ¿qué riesgos tiene permitir ejecutar cualquier comando del sistema? */

/* Permitir la ejecución de cualquier comando del sistema conlleva riesgos graves, ya que un atacante puede obtener un control total sobre el 
servidor afectado, lo que permite robar información sensible como credenciales, registros financieros o datos personales de usuarios. */
