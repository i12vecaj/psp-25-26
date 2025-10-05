public class Script1 {
    // throws Exception esta para poder usar el Thread.sleep
    public static void main(String[] args) throws Exception {
        System.out.println("Script1: iniciando tarea...");
        Thread.sleep(2000);
        System.out.println("Script1: tarea finalizada.");
    }
}