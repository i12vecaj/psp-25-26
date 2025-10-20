
public class ScriptA {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("[ScriptA] Inicio del proceso...");
        Thread.sleep(2000); // simula trabajo de 2 segundos
        System.out.println("[ScriptA] Proceso finalizado.");
    }
}