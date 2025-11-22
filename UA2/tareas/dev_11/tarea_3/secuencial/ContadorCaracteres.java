public class ContadorCaracteres {

    public void contar(String fichero) {
        ContadorCaracteresHilo hilo = new ContadorCaracteresHilo(fichero);

        hilo.run();
    }
}
