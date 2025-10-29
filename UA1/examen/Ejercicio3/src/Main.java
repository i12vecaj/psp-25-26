
public class Main {
    public static void main(String[] args) {
        System.out.println("Inicio de programa\n");
        Thread hilo1 = new Thread(new LavarRopa());
        Thread hilo2 = new Thread(new Cocinar());
        Thread hilo3 = new Thread(new Limpiar());

        hilo1.start();
        hilo2.start();
        hilo3.start();

        try{
            hilo1.join();
            hilo2.join();
            hilo3.join();

        }catch (InterruptedException e){
            System.out.println("Error a la hora de esperar a los hilos");
            e.printStackTrace();
        }

        System.out.println("\nFinal de programa");
    }
}