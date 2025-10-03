public class Proceso2 {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Proceso 2 iniciado");
        Thread.sleep(3000); // simula tarea de 3 segundos
        System.out.println("Proceso 2 terminado");
    }
}
