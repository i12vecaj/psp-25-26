package StrangerThings;

import java.util.Random;

class HilosDemogorgon extends Thread {
    private final Eleven eleven; 

    public HilosDemogorgon(String nombre, Eleven eleven) {
        super(nombre); 
        this.eleven = eleven;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Portal portal = eleven.AbrirPortal();

                System.out.println(getName() + " Está abriendo: " + portal + " portales.");
                eleven.CerrarPortal(portal);
                System.out.println(getName() + " descansa antes de abrir otro portal.");
                Thread.sleep(new Random().nextInt(1000 - 250 + 1) + 250);

            } catch (InterruptedException e) {
                System.err.println("Hilo " + getName() + " interrumpido.");
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}