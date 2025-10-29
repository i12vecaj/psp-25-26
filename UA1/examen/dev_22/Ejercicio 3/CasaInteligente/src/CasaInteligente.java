/*
 * Nombre: Jose Antonio Roda Donoso
 * Fecha: 29/10/2025
 * Descripción: Simulador de la casa inteligente donde se ejecutan las otras tareas
 * FR implementados: FR4, FR5
 */
public class CasaInteligente {
    public static void main(String[] args) {
        System.out.println("Casa inteligente iniciando tareas...");

        Thread hiloLavar = new Thread(new LavarRopa());
        Thread hiloCocinar = new Thread(new Cocinar());
        Thread hiloLimpiar = new Thread(new Limpiar());

        hiloLavar.start();
        hiloCocinar.start();
        hiloLimpiar.start();

        try {
            hiloLavar.join();
            hiloCocinar.join();
            hiloLimpiar.join();
        } catch (InterruptedException e) {
            System.out.println("El hilo principal fue interrumpido.");
        }

        System.out.println("Casa inteligente: todas las tareas finalizadas.");
    }
}

