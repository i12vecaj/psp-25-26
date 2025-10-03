public class Proceso1 {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Proceso 1 iniciado");
        Thread.sleep(2000); // simula tarea de 2 segundos
        System.out.println("Proceso 1 terminado");
    }
}
