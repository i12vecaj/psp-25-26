public class Cola {
    //buffer compartido
    private String eventoPrincipal;
    private boolean disponible = false;

    public synchronized String get() throws InterruptedException {
        while (!disponible) {
            try {
                wait();
            } catch (InterruptedException e) { }
        }
        System.out.println("El laboratorio procesa un evento " + eventoPrincipal);
        Thread.currentThread().sleep(1000);
        disponible = false;
        notify();
        return eventoPrincipal;
    }


    public synchronized void put(String evento) throws InterruptedException {
        while (disponible){
            try {
                wait();
            } catch (InterruptedException e) { }
        }
        eventoPrincipal = evento;
        disponible = true;
        System.out.println("----\n Se produce: " + evento + "\n----\n");
        Thread.currentThread().sleep(1000);
        notify();
    }



}