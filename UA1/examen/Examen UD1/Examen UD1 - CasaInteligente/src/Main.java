 /*
  * Nombre: Daniel Santaflorentina Picchi
  * Fecha: 29/10/2025
  * Descripción: Metodo Main para ejecutar las funciones
  */

 public class Main {
    public static void main(String[] args) {

        Thread lavarRopa = new Thread(new LavarRopa());
        Thread cocinar = new Thread(new Cocinar());
        Thread limpiar = new Thread(new Limpiar());

        System.out.println("--- COMIENZO DEL PROGRAMA ---");

        lavarRopa.start();
        cocinar.start();
        limpiar.start();

        try {
            lavarRopa.join();
            cocinar.join();
            limpiar.join();

        } catch (InterruptedException e) {
            System.err.println("Error: " + e.getMessage());
        }

        System.out.println("--- FIN DEL PROGRAMA ---");
    }
}