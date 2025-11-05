/* Breve explicación para entenderlo yo
- Con sincronización usando Runnable
- En vez de extender de Thread, usamos Runnable
- También usamos synchronized -
*/

//siempre debe dar 5000//

package UA2.tareas.dev_0.tarea_1;

public class ua2tarea1fr2runnable {

    static int contador = 0;

    public static synchronized void sumar() {
        contador++;
    }

    static class MiRunnable implements Runnable {
        public void run() {
            for (int i = 0; i < 1000; i++) {
                sumar();
            }
        }
    }

    public static void main(String[] args) {

        Runnable tarea = new MiRunnable();

        Thread h1 = new Thread(tarea);
        Thread h2 = new Thread(tarea);
        Thread h3 = new Thread(tarea);
        Thread h4 = new Thread(tarea);
        Thread h5 = new Thread(tarea);

        h1.start(); h2.start(); h3.start(); h4.start(); h5.start();

        try {
            h1.join(); h2.join(); h3.join(); h4.join(); h5.join();
        } catch (Exception e) {
            System.out.println("Error al esperar hilos");
        }

        System.out.println("Resultado sincronizado con Runnable: " + contador);
        System.out.println("Debe ser 5000 igual que antes.");
    }
}
