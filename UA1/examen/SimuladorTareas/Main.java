/*
 * Nombre: Angel Amil Cobacho
 * Fecha: 29/10/2025
 * Descripción: Simulacion de tareas
 * FR implementados: [FR1, FR2...]
 */

public class Main {
    public static void main(String[] args) {

        Tarea tarea1 = new Tarea("Tarea 1");
        Tarea tarea2 = new Tarea("Tarea 2");
        Tarea tarea3 = new Tarea("Tarea 3");

        Thread hilo1 = new Thread(tarea1);
        Thread hilo2 = new Thread(tarea2);
        Thread hilo3 = new Thread(tarea3);

        hilo1.start();
        hilo2.start();
        hilo3.start();

        }
    }