public class Productor extends Thread {
    private Cola cola;
    private int n;

    public Productor(Cola c, int n) {
        cola = c;
        this.n = n;
    }

    public void run() {
        String[] eventos = new String[6];
        int random = (int)(Math.random()*5);
        eventos[0] = "Portal inestable!!";
        eventos[1] = "Demogorgon detectado!!";
        eventos[2] = "Criatura desconocida detectada!!";
        eventos[3] = "Se está atravesando el portal!!";
        eventos[4] = "EL PORTAL VA A COLAPSAR!!";
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 5; j++) {
                try {
                    cola.put(eventos[j]);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Eleven ha cerrado un Portal!!");
            }
            try {
                sleep(100);
            } catch (InterruptedException e) { }

        }
    }
}