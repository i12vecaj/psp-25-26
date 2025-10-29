/*
 * Nombre: Eduardo Ruz López
 * Fecha: 29/10/2025
 * Descripción: Simulamos las tareas de una casa y las ejecutamos mediante hilos
 * FR implementados: FR1,FR2,FR3,FR4,FR5
 */

public class LavarRopa implements Runnable{

    public LavarRopa() {
    }

    @Override
    public void run() {
        System.out.println("[Lavar ropa] Comenzando tarea...");

        try {
            Thread.sleep((int) Math.floor(Math.random()* 3)*1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        System.out.println("[Lavar ropa] Tarea finalizada");
    }
}
