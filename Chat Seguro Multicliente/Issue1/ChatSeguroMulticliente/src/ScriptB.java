public class ScriptB {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("[ScriptB] Inicio del proceso...");
        Thread.sleep(3000); // simula trabajo de 3 segundos
        System.out.println("[ScriptB] Proceso finalizado.");
    }
}