package hastaratillo;
//este es el FR1, no lo pusé al crear el archivo.
public class ua2act1 {

    static int contador = 0;

    public static void main(String[] args) {

        MiHilo h1 = new MiHilo("Hilo 1");
        MiHilo h2 = new MiHilo("Hilo 2");
        MiHilo h3 = new MiHilo("Hilo 3");
        MiHilo h4 = new MiHilo("Hilo 4");
        MiHilo h5 = new MiHilo("Hilo 5");

        h1.start();
        h2.start();
        h3.start();
        h4.start();
        h5.start();

        try {
            h1.join();
            h2.join();
            h3.join();
            h4.join();
            h5.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Valor contador: " + contador);
    }

    static class MiHilo extends Thread {
        public MiHilo(String nombre) {
            super(nombre);
        }

        @Override
        public void run() {
            contador = contador+1000;
        }
    }
}
//al menos al ejecutar yo mi codigo, si da 5000 todas las veces
