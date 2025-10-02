import java.io.*;
import java.util.Scanner;


public class EjecutaComando {
    public static void main(String[] args) throws IOException , InterruptedException {

        ProcessBuilder pb = new ProcessBuilder("PING",  "google.com");
        pb.redirectErrorStream(true); // El error standar se fusiona con la salida y se envia al mismo destino si es true
        // Para poder ejecutar pb.start debemos tener implementado IOException
        //Al tener el proceso debemos crear otra variable para atrabajar con ella
        Process p1 = pb.start();

        try(
                BufferedReader br = new BufferedReader(new InputStreamReader(p1.getInputStream()))) {
            String line;

            while ((line = br.readLine()) != null) {
                System.out.printf("%s\n", line);
            }
        }
            // Ejercicio 2 Ping a web que no existe

            ProcessBuilder pb2 = new ProcessBuilder("PING", "nose.com");
            pb2.redirectErrorStream(true); //Volvemos a capturar el error
            Process p2 = pb2.start(); //Creamos un segudo proceso

        try(
                BufferedReader br2 = new BufferedReader(new InputStreamReader(p2.getInputStream()))) {
            String line2;
            while ((line2 = br2.readLine()) != null) {
                System.out.printf("%s\n", line2);
            }

        }
    // Añadimos a un fichero

        String fichero = "error.txt"; //Creamos nuestro fichero para almacenar el error

        //Creamos un nuevo proceso
        ProcessBuilder pb3 = new ProcessBuilder("PING", "google.com");
        pb3.redirectErrorStream(true); // Volvemos a rederigir el error

        Process p3 = pb3.start(); // Ejecutamos el proceso y lo almacenamos en una variable

        try (
                BufferedReader br3 = new BufferedReader(new InputStreamReader(p3.getInputStream()));
                BufferedWriter bw = new BufferedWriter(new FileWriter(fichero))
                ){
                    String line3;
                    while ((line3 = br3.readLine()) != null) {
                        System.out.printf("%s\n", line3);
                        bw.write(line3); //Escribimos la linea que se vaya creando
                        bw.newLine(); // Creamos una nueva linea en el

                    }
        }

    //Ejercicio 4
        // Creamos una segund fichero
        String fichero2 = "lista.txt";
        //Creamos nuestro primer proceso
        ProcessBuilder pb4 = new ProcessBuilder("CMD", "/c", "DIR");
        pb4.redirectOutput(new File(fichero2)); // Redirigiemos la salida al fichero que hemos creado
        Process p4 = pb4.start(); // Ejecutamos el proceso
        // Para usar .waitFor debemos añadir la excepcion InterrptedException
        int exit = p4.waitFor(); // Esperamos que nos devuelva 0 para que termine el primer proceso
        //controlamos que si es igual a 0 entonces entramos a ejecutar el segundo comando
        if (exit == 0) {
            ProcessBuilder pb5 = new ProcessBuilder("CMD", "/c", "type", fichero2);
            pb5.inheritIO();
            Process p5 = pb5.start();
            p5.waitFor();
        }else {
            System.out.println("Da error" + exit);
        }
    // Ejercicio 5
        // Solicitamos por teclado el comando que vamos a ejecutar
        Scanner teclado = new Scanner(System.in);
        System.out.printf("Introduce que comando quieres ejecutar: ");
        String comando = teclado.nextLine();
        // Creamos el proceso para ejecutarlo
        ProcessBuilder pb6 = new ProcessBuilder("CMD", "/c",comando);
        pb6.redirectErrorStream(true); //Capturamos el error
        Process p6 = pb6.start(); //ejecutamos
        //Creamos
        BufferedReader br = new BufferedReader(new InputStreamReader(p6.getInputStream()));
        String line6;
        while ((line6 = br.readLine()) != null) {
            System.out.printf("%s\n", line6);
        }
    }
}

