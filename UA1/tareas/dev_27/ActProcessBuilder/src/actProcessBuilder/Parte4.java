package actProcessBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Parte4 {

    public static void main(String[] args) throws IOException {
        /*
         * Este programa ejecuta el comando DIR de Windows mediante un ProcessBuilder.
         * El flujo de ejecución es:
         * 1. Se crea un ProcessBuilder con el comando "CMD /C DIR".
         * 2. Se inicia el proceso con start().
         * 3. Se espera a que el proceso termine usando waitFor(), obteniendo su exitVal.
         * 4. Si exitVal == 0 (ejecución correcta), se abre un Scanner sobre la salida del proceso
         *    (getInputStream()), y cada línea se muestra por consola y se escribe en el archivo
         *    "salidaDIR.txt".
         * 5. Si exitVal != 0, no se hace nada con la salida y solo se muestra el valor de salida.
         */
        
        ProcessBuilder pb = new ProcessBuilder("CMD", "/C", "DIR");
        Process p = pb.start();

        try {
            int exitVal = p.waitFor();
            System.out.println("Valor de salida: " + exitVal);

            if (exitVal == 0) { // Solo si el proceso terminó bien
                File salidaDIR = new File("salidaDIR.txt");
                try (FileWriter writer = new FileWriter(salidaDIR);
                     Scanner scanner = new Scanner(p.getInputStream())) {

                    while (scanner.hasNextLine()) {
                        String linea = scanner.nextLine();
                        System.out.println(linea);  // Muestra en consola
                        writer.write(linea + System.lineSeparator()); // Guarda en archivo
                    }
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
