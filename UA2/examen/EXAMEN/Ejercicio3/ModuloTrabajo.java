/*
Nombre: Jose Antonio Roda Donoso
Fecha: 10/12/2025
Curso: 2 DAM
Representa un módulo de producción que trabaja en paralelo
 */

import java.util.Random;

public class ModuloTrabajo extends Thread {

    private String nombre;
    private Random rand = new Random();

    public ModuloTrabajo(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public void run() {
        System.out.println("Módulo " + nombre + " iniciado. ID: " + getId());

        for (int i = 1; i <= 5; i++) {
            System.out.println("Módulo " + nombre + " – iteración " + i);

            // En la tercera iteración cede la CPU
            if (i == 3) {
                System.out.println("Módulo " + nombre + " cede la CPU");
                Thread.yield();
            }

            try {
                // Sleep aleatorio
                Thread.sleep(300 + rand.nextInt(501));
            } catch (InterruptedException e) {
                System.out.println("Módulo " + nombre + " interrumpido durante la ejecución");
                return;
            }
        }
    }

    @Override
    public String toString() {
        return "ModuloTrabajo= nombre:'" + nombre + "', id:" + getId() +
                ", prioridad:" + getPriority() + "}";
    }
}