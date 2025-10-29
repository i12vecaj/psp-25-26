
public class CasaInteligente {
    public static void main (String[] args)
    {

        System.out.println("Inicio del programa");
        LavarRopa hilo1 = new LavarRopa(1, 10);
        Cocinar hilo2 = new Cocinar(2, 4);
        Limpiar hilo3 = new Limpiar(3, 5);

        // Se crean los hilos (no extienden Thread directamente)
        Thread t1 = new Thread(hilo1);
        Thread t2 = new Thread(hilo2);
        Thread t3 = new Thread(hilo3);

        // Se lanzan los hilos
        t1.start();
        t2.start();
        t3.start();

        System.out.println("Fin del programa");
    }
}
