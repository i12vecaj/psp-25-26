package hastaratillo;
//En el último archivo te dejo mi reflexión sobre los 3 archivos y lo que yo he entendido
public class ua2act1FR2 {

    static int contador = 0;

    public static void main(String[] args) {

    	MiHiloSync h1 = new MiHiloSync("Hilo 1");
        MiHiloSync h2 = new MiHiloSync("Hilo 2");
        MiHiloSync h3 = new MiHiloSync("Hilo 3");
        MiHiloSync h4 = new MiHiloSync("Hilo 4");
        MiHiloSync h5 = new MiHiloSync("Hilo 5");


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
            System.err.println("Error al esperar los hilos (Thread): " + e.getMessage());
        }

        System.out.println("Valor contador (Thread sincronizado): " + contador);
    }

    static class MiHiloSync extends Thread {
        public MiHiloSync(String nombre) {
            super(nombre);
        }

        @Override
        public void run() {
            synchronized (ua2act1FR2.class) {
                try {
                    contador = contador + 1000;
                } catch (Exception e) {
                    System.err.println("Error en " + getName() + ": " + e.getMessage());
                }
        }
    }
}
}