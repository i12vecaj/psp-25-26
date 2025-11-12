<<<<<<< HEAD
import java.util.Random;
public class ua2tarea1fr1 {
    static int contador = 0;
    public static void main(String[] args) {
        Thread[] hilos = new Thread[5];

        for (int i = 0; i < 5; i++) {
            hilos[i] = new MiHilo("Hilo-" + (i + 1));
            hilos[i].start();
        }

        System.out.println("Valor final del contador: " + contador);
        System.out.println("Valor esperado: 5000");
    }

    static class MiHilo extends Thread {
        static final Random random = new Random();
        public MiHilo(String nombre) {
            super(nombre);
        }

        @Override
        public void run() {
            for (int i = 0; i <= 1000; i++) {
                contador++;
            }
        }
    }
=======
import java.util.Random;
public class ua2tarea1fr1 {
    static int contador = 0;
    public static void main(String[] args) {
        Thread[] hilos = new Thread[5];

        for (int i = 0; i < 5; i++) {
            hilos[i] = new MiHilo("Hilo-" + (i + 1));
            hilos[i].start();
        }

        System.out.println("Valor final del contador: " + contador);
        System.out.println("Valor esperado: 5000");
    }

    static class MiHilo extends Thread {
        static final Random random = new Random();
        public MiHilo(String nombre) {
            super(nombre);
        }

        @Override
        public void run() {
            for (int i = 0; i <= 1000; i++) {
                contador++;
            }
        }
    }
>>>>>>> 9bb36804bd953f7be089a1850adc30824a6e43a1
}