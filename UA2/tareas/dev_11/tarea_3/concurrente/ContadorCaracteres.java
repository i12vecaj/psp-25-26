public class ContadorCaracteres {

    public void contar(String fichero) {
        ContadorCaracteresHilo tarea = new ContadorCaracteresHilo(fichero);

        Thread hilo = new Thread(tarea);

        hilo.start();

        // Esperamos a que termine
        try {
            hilo.join();
        } catch (InterruptedException e) {
            System.out.println("Hilo interrumpido: " + e.getMessage());
        }
    }
}
