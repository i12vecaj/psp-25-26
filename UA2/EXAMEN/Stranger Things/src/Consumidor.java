
public class Consumidor extends Thread {
    private Cola cola;
    private int n;

    public Consumidor(Cola c, int n) {
        cola = c;
        this.n = n;
    }



    //Hawkings va recogiendo todos los
    public void run() {
        String evento = "Demogorgon detectado";
        for (int i = 0; i < 5; i++) {

            if (i == 0 || i == 1 || i == 2) {
                evento = "Demogorgon detectado";
            } else {
                evento = "Portal inestable";
            }
            evento = cola.get(); //aquí es donde se van recogiendo los eventos del buffer

        }
    }
}