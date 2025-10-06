public class Script3 {
    public static void main(String[] args) {
        System.out.println("Inicio Script 3");
        try {
            Thread.sleep(4000); // Simula tarea de 4 segundos
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Fin Script 3");
    }
}
