 /*
 * Nombre: David Peláez Pérez
 * Fecha: 29/10/2025
 * Descripción: Crear 3 hilos los cuales tiene distintos tiempos de finalización y mostrar cuando se inicia y cuando termina el hilo 
 * y ver tambíen como el programa es concurrente el orden de inicializado cambia
 * FR implementados: [FR1, FR2, FR3, FR4, FR5]
 */

public class CasaInteligente {
    public static void main(String[] args) {
        System.out.println("........................Iniciando Casa...........................");
        LavarRopa lr1 = new LavarRopa();
        Cocinar c1 = new Cocinar();
        Limpiar l1 = new Limpiar();

        Thread t1 = new Thread(lr1);
        Thread t2 = new Thread(c1);
        Thread t3 = new Thread(l1);

        t1.start();
        t2.start();
        t3.start();

    }
}
