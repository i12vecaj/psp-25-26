/*
 * Nombre: Pablo Herrador Castillo
 * Fecha: 29/10/2025
 * Descripción: Casa Inteligente
 * FR implementados: FR1, FR2 , FR3, FR4 , FR5
 */

public class CasaInteligente {

    public static class LavarRopa implements Runnable {
        @Override
        public void run() {
            System.out.println("[Lavar ropa] Comenzando tarea...");
            try {
                Thread.sleep(1000); 
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("[Lavar ropa] Interrumpida.");
                return;
            }
            System.out.println("[Lavar ropa] Tarea finalizada.");
        }
    }

    public static class Cocinar implements Runnable {
        @Override
        public void run() {
            System.out.println("[Cocinar] Comenzando tarea...");
            try {
                Thread.sleep(2000); 
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("[Cocinar] Interrumpida.");
                return;
            }
            System.out.println("[Cocinar] Tarea finalizada.");
        }
    }

    public static class Limpiar implements Runnable {
        @Override
        public void run() {
            System.out.println("[Limpiar] Comenzando tarea...");
            try {
                Thread.sleep(3000); 
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("[Limpiar] Interrumpida.");
                return;
            }
            System.out.println("[Limpiar] Tarea finalizada.");
        }
    }

    public static void main(String[] args) {
        System.out.println("Programa iniciado - Casa inteligente empezando tareas");

        Thread tLavar = new Thread(new LavarRopa(), "LavarRopa-Thread");
        Thread tCocinar = new Thread(new Cocinar(), "Cocinar-Thread");
        Thread tLimpiar = new Thread(new Limpiar(), "Limpiar-Thread");

        
        tLavar.start();
        tCocinar.start();
        tLimpiar.start();

        
        try {
            tLavar.join();
            tCocinar.join();
            tLimpiar.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Hilo principal interrumpido mientras esperaba a las tareas.");
        }

        System.out.println("Programa finalizado - Todas las tareas completadas");
    }
}
