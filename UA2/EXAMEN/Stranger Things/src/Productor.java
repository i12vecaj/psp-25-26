

public class Productor extends Thread {
    private Cola cola;
    private int n;

    public Productor(Cola c, int n) {
        cola = c;
        this.n = n;
    }

    public void run() {
        for (int i = 0; i < 5; i++) {

            String evento = "Demogorgon detectado";

            cola.put(evento); //Se introduce el evento en el buffer compartido para que pueda ser consumido por el laboratorio

            try {
                sleep(100);
            } catch (InterruptedException e) { }

        }
    }
}