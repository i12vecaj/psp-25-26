
public class Activ1 {

    //  Variable compartida por todos los hilos
    private static int contador = 0;

    public static void ejecutar() {

        // Creamos un array para guardar los 5 hilos que vamos a lanzar
        Thread[] hilos = new Thread[5];

        // Creamos y lanzamos los 5 hilos
        for (int i = 0; i < 5; i++) {
            // Cada hilo incrementa el contador 1000 veces
            hilos[i] = new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    contador++; //  No está sincronizado, al no estar sincronizado
                    // los hilos leen el mismo valor de contador antes que el otro se actualice
                    // haciendo que se puedan perder incrementos (no se interrumen solo con estos y puede que no de fallos como me ha pasado)
                    try{
                        //ejecutamos un try catch en el que usaremos el metodo thread.sleep para interrumpir temporalmente la ejecucion del ilo
                        Thread.sleep(1);
                    } catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            });
            hilos[i].start(); // Iniciamos el hilo
        }

        // Esperamos a que todos los hilos terminen antes de mostrar el resultado final
        for (Thread hilo : hilos) {
            try {
                hilo.join();
            } catch (InterruptedException e) {
                System.out.println(" Hilo interrumpido: " + e.getMessage());
            }
        }

        // Mostramos el resultado final
        System.out.println("Resultado final del contador (sin sincronizar): " + contador);
    }
}
