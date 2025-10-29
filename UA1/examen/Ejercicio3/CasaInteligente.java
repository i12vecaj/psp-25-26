/*
 * Nombre: Pablo Rodríguez Casado
 * Fecha: 29/10/2025
 * Descripción: En este ejercicio usamos hilos para ejecutar una simulación de varias tareas de forma concurrente
 * FR implementados: [FR1, FR2, FR3]
 */
public class CasaInteligente{
    static void main(String[] args) {
        System.out.println("Comienza el programa");
        Thread t1 = new Thread(new LavarRopa());
        Thread t2 = new Thread(new Cocinar());
        Thread t3 = new Thread(new Limpiar());
        t1.start();
        t2.start();
        t3.start();
        do{
            if(!t1.isAlive()){
                if(!t2.isAlive()){
                    if(!t3.isAlive()){
                        System.out.println("Programa finalizado");
                        break;
                    }
                }
            }

        }while (true);

    }
}