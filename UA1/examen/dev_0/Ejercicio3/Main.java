/*
 * Nombre: Miguel Castilla Gallego
 * Fecha: 29/10/2025
 * Descripción: Ejercicio sobre hilos simulando tareas del hogar
 * FR implementados: [FR1, FR2, FR3, FR4, FR5]
 */
public class Main {
    public static void main(String[] args) {

        Cocinar c = new Cocinar();
        LavarRopa lr = new LavarRopa();
        Limpiar l = new Limpiar();

        Thread t1 = new Thread(lr);
        Thread t2 = new Thread(c);
        Thread t3 = new Thread(l);

        System.out.println("Iniciando concurrencia: ");
        t1.start();
        t2.start();
        t3.start();

        
    }
}