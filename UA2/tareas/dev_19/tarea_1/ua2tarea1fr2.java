/*
 * Nombre del archivo:ua2tarea1fr1.java
 * Ejercicio: FR2: Modifica el programa anterior para sincronizar el acceso a dicha varaible.
 *  Lanza primero los hilos mediante la clase Thread y después mediante el interfaz Runnable.
 * Comprueba los resultados e indica las variaciones - 3 puntos
 * Fecha: 05/11/2025
 * Alumna: María Luisa Ortega LUcena
 */


public class ua2tarea1fr2 {
    //Variable compartida entre todos los hilos
    static int contador = 0;

    //Este metodo sincroniza para implementar el contador
    public static synchronized void incrementar() {
        contador++;
    }

    public static void main(String[] args) {
        //array para almacenar los hilos
        HiloIncrementador[] hilos = new HiloIncrementador[5];

        //Creamos y lanzo cada hilo
        for (int i = 0; i < 5; i++) {
            hilos[i] = new HiloIncrementador();
            hilos[i].start();
        }

        //Se espera a que todos los hilos terminen
        for (int i = 0; i < 5; i++) {
            try {
                hilos[i].join();
            } catch (InterruptedException e) {
                System.out.println("Error al esperar el hilo " + i);
                e.printStackTrace();
            }
        }

        //resultado final
        System.out.println("Valor final del contador: " + contador);
    }
}

//Clase que extiende Thread y ejecuta la tarea de incrementar
class HiloIncrementador extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            ua2tarea1fr2.incrementar(); // Acceso sincronizado
        }
    }
}
