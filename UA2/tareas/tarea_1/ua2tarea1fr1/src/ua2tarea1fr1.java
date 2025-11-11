
/*

Autor Bernardo Cubero
Fecha 08/11/2025
Version 1.0
FR1: Crea un programa en Java que lance 5 hilos. Cada hilo incrementará
una variable contador de tipo entero en 1000 unidades. Esta variable
estará compartida por todos los hilos.
Comprueba el resultado final de la variable y reflexiona sobre el resultado.
 ¿Se obtiene el resultado esperado? - 3 puntos
 */
public class ua2tarea1fr1 {
    static int contador = 0;

    public static void main(String[] args) {

        Thread th1 = new hilo();
        Thread th2 = new hilo();
        Thread th3 = new hilo();
        Thread th4 = new hilo();
        Thread th5 = new hilo();

        //Hilos
        th1.start();
        th2.start();
        th3.start();
        th4.start();
        th5.start();


    }

    public static class hilo extends Thread {

        public void run() {
            // Incremento 1000 veces (una a una)


                try {
                    for (int i = 0; i < 1000; i++) {
                        contador++; // operación NO segura
                    }
                } catch (Exception e) {
                    System.out.println("Error: " + getId());
                }

                System.out.println("Hilo: " + getId() + " Contador: " + contador);
            }

    }
}
/*
   Cada vez que ejecuto el programa, el contador muestra un número diferente
porque los hilos se están ejecutando al mismo tiempo. Cuando el for de cada
hilo llega a lo establecido, ese hilo se detiene, pero como ninguno espera
a los demás, todos avanzan en paralelo. Al hacerlo, cada hilo toma valores
distintos de la variable compartida y por eso el resultado final del contador
cambia en cada ejecución.
 */