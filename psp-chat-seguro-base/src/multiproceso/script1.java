package multiproceso;

public class script1 {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Script1 iniciado");
        Thread.sleep(2000); // Simula tarea de 2 segundos
        System.out.println("Script1 terminado");
    }
}
