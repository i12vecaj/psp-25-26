import java.io.FileReader;

public class ContadorConcurrente {

    public static void main(String[] args) throws InterruptedException {
        String fichero1 = "el_quijote.txt";
        String fichero2 = "Harry_Potter.txt";

        // Variables para almacenar los totales
        final int[] total1 = {0};
        final int[] total2 = {0};

        // Hilo para el primer fichero
        Thread hilo1 = new Thread(() -> {
            try (FileReader fr = new FileReader(fichero1)) {
                int caract;
                while ((caract = fr.read()) != -1) {
                    System.out.print((char) caract); // Mostrar el carácter
                    total1[0]++;
                }
            } catch (Exception e) {
                System.out.println("Error al leer el fichero " + fichero1 + ": " + e.getMessage());
            }
        });

        // Hilo para el segundo fichero
        Thread hilo2 = new Thread(() -> {
            try (FileReader fr = new FileReader(fichero2)) {
                int caract;
                while ((caract = fr.read()) != -1) {
                    System.out.print((char) caract); // Mostrar el carácter
                    total2[0]++;
                }
            } catch (Exception e) {
                System.out.println("Error al leer el fichero " + fichero2 + ": " + e.getMessage());
            }
        });

        // Iniciar ambos hilos
        hilo1.start();
        hilo2.start();

        // Esperar a que ambos hilos terminen
        hilo1.join();
        hilo2.join();

        // Mostrar los totales al final
        System.out.println("\nTotal de caracteres en " + fichero1 + ": " + total1[0]);
        System.out.println("Total de caracteres en " + fichero2 + ": " + total2[0]);
    }
}
