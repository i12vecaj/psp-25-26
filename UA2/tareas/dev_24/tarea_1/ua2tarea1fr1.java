/* Breve explicación para entenderlo yo
- Sin sincronización
- Creamos los hilos extendiendo Thread
- Cada hilo suma 1000 veces
 - NO usas synchronized -
*/

//No siempre 5000//

package UA2.tareas.dev_0.tarea_1;

public class ua2tarea1fr1 {

    static int contador = 0;


    static class MiHilo extends Thread {
        public void run() {
            for (int i = 0; i < 1000; i++) {
                contador++;
            }
        }
    }

    public static void main(String[] args) {

        //creamos los 5 hilos h
        Thread h1 = new MiHilo();
        Thread h2 = new MiHilo();
        Thread h3 = new MiHilo();
        Thread h4 = new MiHilo();
        Thread h5 = new MiHilo();

        //los iniciamos
        h1.start(); h2.start(); h3.start(); h4.start(); h5.start();

        try {
            h1.join(); h2.join(); h3.join(); h4.join(); h5.join();
        } catch (Exception e) {
            System.out.println("Error al esperar hilos");
        }

        System.out.println("Resultado sin sincronización: " + contador);
        System.out.println("Debería ser 5000 pero puede salir menos por errores de concurrencia.");
    }
}
