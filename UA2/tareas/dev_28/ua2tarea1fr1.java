public class ua2tarea1fr1 {
    static int contador = 0;

    public static void main(String[] args) throws InterruptedException {

        Thread hilo1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                contador++;
            }
        });

        Thread hilo2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                contador++;
            }
        });

        Thread hilo3 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                contador++;
            }
        });

        Thread hilo4 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                contador++;
            }
        });

        Thread hilo5 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                contador++;
            }
        });

        // Iniciamos los 5 hilos
        hilo1.start();
        hilo2.start();
        hilo3.start();
        hilo4.start();
        hilo5.start();

        // Esperamos a que todos terminen
        hilo1.join();
        hilo2.join();
        hilo3.join();
        hilo4.join();
        hilo5.join();

        System.out.println("Valor final del contador: " + contador);
    }
}

/*Al no haber sincronizacion ninguna, el valor nunca va a ser
 el deseado ya que deberia salir 5000 pero al no haber dicha sincronizacion
 dara siempre un valor por debajo de esos 5000.
 */