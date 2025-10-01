import java.io.*;

// no sé si hacía faltar hacerlo de tal forma que detecte si es linux o windows
// pero lo he hecho estilo windows
// debajo he añadido la reflexión y un snippet con versión multiplataforma
public class EjecutaComando4 {
    public static void main(String[] args) throws IOException {

        // ===== PRIMER COMANDO: Listar archivos =====
        Process p1 = new ProcessBuilder("CMD", "/C", "DIR").start();

        try {
            InputStream is = p1.getInputStream();

            // mostramos en pantalla caracter a caracter
            int c;
            while ((c = is.read()) != -1)
                System.out.print((char) c);
            is.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        // COMPROBACION DE ERROR - 0 bien - 1 mal
        int exitVal1;
        try {
            exitVal1 = p1.waitFor();
            System.out.println("\n===========================================");
            System.out.println("Valor de Salida del primer comando: " + exitVal1);
            System.out.println("===========================================\n");

            // ===== SEGUNDO COMANDO: Solo se ejecuta si el primero tuvo éxito (exit code 0) =====
            if (exitVal1 == 0) {
                System.out.println("El primer comando se ejecutó correctamente.");
                System.out.println("Ejecutando segundo comando...\n");

                // Es mucho más fácil de realizar usando comandos de CMD

                // Redirigimos la salida del primer comando a un archivo
                Process p1b = new ProcessBuilder("CMD", "/C", "DIR > salida2.txt").start();
                p1b.waitFor();

                // Segundo comando: mostrar el contenido del archivo
                Process p2 = new ProcessBuilder("CMD", "/C", "TYPE salida2.txt").start();

                InputStream is2 = p2.getInputStream();
                int c2;
                while ((c2 = is2.read()) != -1)
                    System.out.print((char) c2);
                is2.close();

                int exitVal2 = p2.waitFor();
                System.out.println("\n===========================================");
                System.out.println("Valor de Salida del segundo comando: " + exitVal2);
                System.out.println("===========================================");

            } else {
                System.out.println("El primer comando falló con código: " + exitVal1);
                System.out.println("No se ejecutará el segundo comando.");
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

/*
REFLEXIÓN:
    Para controlar dicha ejecución lo importa es la estructura y el uso de checks
    como se puede ver en mi código he añadido dicha estructura para controlar
    que el segundo comando solo se ejecute si hay exit code 0
 */

/*
VERSIÓN MULTIPLATAFORMA (he tenido que buscar cosas)

se podría hacer de esta manera, comprobando que exista windows
y si no es el caso pues se tira de comandos bash (linux)
String os = System.getProperty("os.name").toLowerCase();
boolean isWindows = os.contains("win");

Process p1;
if (isWindows) {
    p1 = new ProcessBuilder("CMD", "/C", "DIR").start();
} else {
    p1 = new ProcessBuilder("ls", "-l").start();
}
*/