public class Main {
    public static void main(String[] args) {
       Tarea lavarRopa = new LavarRopa(1);
       Cocinar cocinar = new Cocinar(2);
       Limpiar limpiar = new Limpiar(3);

       Thread t1 = new Thread(lavarRopa);
       Thread t2 = new Thread(cocinar);
       Thread t3 = new Thread(limpiar);

       t1.start();
       t2.start();
       t3.start();

    }
}