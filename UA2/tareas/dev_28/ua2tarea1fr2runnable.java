public class ua2tarea1fr2runnable implements Runnable {
    // Variable contador que sera compartida
    private static int contador = 0;

    // Objeto para la sincronizacion de los hilos
    private static final Object lock = new Object();

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            synchronized (lock) {
                contador++;
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        // Crear 5 objetos Runnable
        ua2tarea1fr2runnable tarea1 = new ua2tarea1fr2runnable();
        ua2tarea1fr2runnable tarea2 = new ua2tarea1fr2runnable();
        ua2tarea1fr2runnable tarea3 = new ua2tarea1fr2runnable();
        ua2tarea1fr2runnable tarea4 = new ua2tarea1fr2runnable();
        ua2tarea1fr2runnable tarea5 = new ua2tarea1fr2runnable();

        // Crear los hilos que ejecutarán las tareas
        Thread hilo1 = new Thread(tarea1);
        Thread hilo2 = new Thread(tarea2);
        Thread hilo3 = new Thread(tarea3);
        Thread hilo4 = new Thread(tarea4);
        Thread hilo5 = new Thread(tarea5);

        // Iniciar los hilos
        hilo1.start();
        hilo2.start();
        hilo3.start();
        hilo4.start();
        hilo5.start();

        // Esperar a que terminen
        hilo1.join();
        hilo2.join();
        hilo3.join();
        hilo4.join();
        hilo5.join();

        // Mostrar el resultado final
        System.out.println("Valor final del contador : " + contador);
    }
}

/*
    El resultado seria el mismo que sin la interfaz Runnable pero
    se consigue con menos lineas porque no tienes que repetir el mismo ciclo
    que aumentan el contador tantas veces como hilos sino que con solo una vez
    en la funcion Run ya es suficiente para que funcione.
 */