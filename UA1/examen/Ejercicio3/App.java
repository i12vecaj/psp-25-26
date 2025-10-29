public class App {


    public static void main(String[] args) throws Exception {
        

        Cocinar cocinar = new Cocinar("Cocinar");
        LavarRopa lavarRopa = new LavarRopa("Lavar Ropa");
        Limpiar limpiar = new Limpiar("Limpiar");

        Thread t1 = new Thread(cocinar);
        Thread t2 = new Thread(lavarRopa);
        Thread t3 = new Thread(limpiar);

        t1.start();
        t2.start();
        t3.start();

    }
}
