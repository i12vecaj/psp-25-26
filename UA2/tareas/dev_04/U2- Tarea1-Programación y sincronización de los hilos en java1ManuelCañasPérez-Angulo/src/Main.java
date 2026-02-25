
public class Main {
    // Aquí crearemos una variable compartida por todos los hilos
    private static int contador = 0;
    private static final Object lock = new Object(); // object para sincronizar

    public static void main(String[] args) {
        // llamamos al metodo ejecutar de la Activ1
        Activ1.ejecutar();

        // Creamos un array para guardar los 5 hilos que vamos a lanzar
        Thread[] hilos = new Thread[5];
        // Creamos un bucle para crear y lanzar los 5 hilos creádos anteriormente
        for (int i = 0; i < 5; i++) {
            // Cada posición del array contendrá un nuevo hilo
            // El hilo ejecutará la siguiente tarea
            hilos[i] = new Thread(() -> {
                // Cada hilo incrementa el contador 1000 veces
                for (int j = 0; j < 1000; j++) {
                    // Esto hace que solo un hilo entre lo que hace que los demás deban esperar
                    // solo cuando sale el otro entrará evitando que dos hilos modifiquen el contador
                    synchronized (lock){
                        contador++; // No está sincronizado
                    }
                }
            });
            hilos[i].start();
        }

        // Recorremos el array de hilos principal asegurarnos a que terminen todos los hilos
        for (Thread hilo : hilos) {
            try {
                hilo.join();
            } catch (InterruptedException e) {
                System.out.println(" Hilo sin interrumpir: " + e.getMessage());
            }
        }

        System.out.println("Resultado final del contador: " + contador);
    }


}
