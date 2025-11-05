/*
 * Nombre del archivo:ua2tarea1fr1.java
 * Ejercicio: FR1: Crea un programa en Java que lance 5 hilos. Cada hilo incrementará una variable contador de tipo entero en 1000 unidades.
 * Esta variable estará compartida por todos los hilos. Comprueba el resultado final de la variable y reflexiona sobre el resultado.
 * ¿Se obtiene el resultado esperado? - 3 puntos
 * Fecha: 05/11/2025
 * Alumna: María Luisa Ortega LUcena
 */
/*
Respuesta de la pregunta:
-¿Se obtiene el resultado esperado?
No, porque no está controlada la entrada de los hilos, es decir no está sincronizada, si entran dos hilos a la vez lo que pasa es que
cada uno lee que hay 200 y hace un contador++, que pasa con esto que uno de los dos no se actualiza entonces se queda en 201 y no en 202, que es lo
que se debería hacer

 */

public class ua2tarea1fr1 {
    //La variable para todos los hilos
    protected static int contador = 0;

    public static void main(String[] args) {
        //Array para los 5 hilos
        Thread[] hilos = new Thread[5];

        for (int i = 0; i < 5; i++) {
            hilos[i] = new Thread(new Incrementador()); //El hilo se realiza con ña clase Incrementador (implementa la interfaz Runnable)
            hilos[i].start();
        }

        for (int i = 0; i < 5; i++) {
            try {
                hilos[i].join(); //Espera a que todos los hilos terminen
            } catch (InterruptedException e) {
                System.out.println("Error al esperar el hilo " + i);
                e.printStackTrace();
            }
        }

        System.out.println("Valor final del contador: " + contador);
    }
}

class Incrementador implements Runnable {
    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            ua2tarea1fr1.contador++; //Aqui cada hilo llama a esta clase, pero que pasa que todos utilizan la misma variable, entonces todos lo hacen sobre elmismo espacio de memoria
        }
    }
}
