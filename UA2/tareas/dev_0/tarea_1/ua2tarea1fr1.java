
public class ContadorNoSincronizado {

    // Variable compartida entre todos los hilos
    static int contador = 0;

    public static void main(String[] args) {
        // Creamos un array para guardar los hilos
        Thread[] hilos = new Thread[5];

        // Creamos y lanzamos los 5 hilos
        for (int i = 0; i < 5; i++) {
            hilos[i] = new Thread(() -> {
                // Cada hilo incrementa la variable 1000 veces
                for (int j = 0; j < 1000; j++) {
                    contador++; // acceso no sincronizado
                }
            });

            hilos[i].start(); // iniciamos el hilo
        }

        // Esperamos a que todos los hilos terminen
        for (int i = 0; i < 5; i++) {
            try {
                hilos[i].join();
            } catch (InterruptedException e) {
                System.err.println("Error: el hilo fue interrumpido.");
            }
        }

        // Mostramos el resultado final
        System.out.println("Valor final del contador (sin sincronizar): " + contador);
    }
}
//Cada hilo incrementa el contador 1000 veces.


En total, deberían ser 5 × 1000 = 5000.


Pero al ejecutar varias veces, verás que el valor final varía (por ejemplo, 4723 o 4890...).


Esto ocurre porque varios hilos acceden a la variable contador al mismo tiempo → condición de carrera.



🧩 FR2 – Programa con sincronización
Objetivo: sincronizar el acceso al contador para obtener el resultado correcto (5000).
Se pide hacerlo con la clase Thread y luego con Runnable.

🔹 Versión usando Thread
/**
 * FR2: Programa que sincroniza el acceso a la variable compartida "contador"
 * usando la clase Thread. Cada hilo incrementa 1000 veces el contador, pero
 * ahora se usa una sección crítica sincronizada.
 * 
 * Autor: [Tu nombre]
 * Fecha: [Fecha actual]
 */

public class ContadorSincronizadoThread {

    static int contador = 0; // variable compartida

    // Objeto usado como candado para sincronizar
    static final Object lock = new Object();

    public static void main(String[] args) {
        Thread[] hilos = new Thread[5];

        // Crear hilos
        for (int i = 0; i < 5; i++) {
            hilos[i] = new Thread() {
                public void run() {
                    for (int j = 0; j < 1000; j++) {
                        // Bloque sincronizado
                        synchronized (lock) {
                            contador++;
                        }
                    }
                }
            };
            hilos[i].start();
        }

        // Esperar a que terminen
        for (Thread hilo : hilos) {
            try {
                hilo.join();
            } catch (InterruptedException e) {
                System.err.println("Error: hilo interrumpido.");
            }
        }

        System.out.println("Valor final del contador (sincronizado con Thread): " + contador);
    }
}


🔹 Versión usando Runnable
/**
 * FR2: Versión sincronizada del contador usando el interfaz Runnable.
 * Se demuestra que el resultado es el mismo que con Thread, pero el código
 * es más flexible y reutilizable.
 * 
 * Autor: [Tu nombre]
 * Fecha: [Fecha actual]
 */

public class ContadorSincronizadoRunnable implements Runnable {

    static int contador = 0;
    static final Object lock = new Object();

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            synchronized (lock) {
                contador++;
            }
        }
    }

    public static void main(String[] args) {
        Thread[] hilos = new Thread[5];
        ContadorSincronizadoRunnable tarea = new ContadorSincronizadoRunnable();

        // Lanzamos 5 hilos con la misma tarea Runnable
        for (int i = 0; i < 5; i++) {
            hilos[i] = new Thread(tarea);
            hilos[i].start();
        }

        for (Thread hilo : hilos) {
            try {
                hilo.join();
            } catch (InterruptedException e) {
                System.err.println("Error: hilo interrumpido.");
            }
        }

        System.out.println("Valor final del contador (sincronizado con Runnable): " + contador);
    }
}


/**
Cada hilo incrementa el contador 1000 veces.

En total, deberían ser 5 × 1000 = 5000.

Pero al ejecutar varias veces, verás que el valor final varía (por ejemplo, 4723 o 4890...).

Esto ocurre porque varios hilos acceden a la variable contador al mismo tiempo → condición de carrera. **/
