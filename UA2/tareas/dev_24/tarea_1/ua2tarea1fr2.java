/* Breve explicación para entenderlo yo
- Con sincronización usando Thread
- También se crea hilos extendiendo Thread
- Pero ahora la función de sumar está con synchronized -
*/

//siempre debe dar 5000//

package UA2.tareas.dev_0.tarea_1;

public class ua2tarea1fr2 {

    static int contador = 0;

    public static synchronized void sumar() {
        contador++;
    }

    static class MiHilo extends Thread {
        public void run() {
            for (int i = 0; i < 1000; i++) {
                sumar();
            }
        }
    }

    public static void main(String[] args) {

        Thread h1 = new MiHilo();
        Thread h2 = new MiHilo();
        Thread h3 = new MiHilo();
        Thread h4 = new MiHilo();
        Thread h5 = new MiHilo();

        h1.start(); h2.start(); h3.start(); h4.start(); h5.start();

        try {
            h1.join(); h2.join(); h3.join(); h4.join(); h5.join();
        } catch (Exception e) {
            System.out.println("Error al esperar hilos");
        }

        System.out.println("Resultado sincronizado con Thread: " + contador);
        System.out.println("Debe ser 5000 porque ahora no hay errores de concurrencia.");
    }
}
