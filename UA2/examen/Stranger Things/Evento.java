public class Evento {

    private Boolean bufferlleno = false;
    private Boolean buffervacio = true;
    private int buffer, numero;

    public synchronized int consumirEvento() {
        while (buffervacio) {
            try {
                System.out.println("El buffer esta vacio");
                wait();
            } catch (InterruptedException e) { }
        }
        buffer--;
        System.out.println("El Laboratorio procesa un evento");
        if (buffer == 5){
            bufferlleno = true;
            buffervacio = false;
        }else if(buffer == 0){
            buffervacio = true;
            bufferlleno = false;
        }
        notify();
        return numero;
    }

    public synchronized void crearEvento(int valor) {
        while (bufferlleno){
            try {
                System.out.println("El buffer esta lleno");
                wait();
            } catch (InterruptedException e) { }
        }
        numero = valor;
        buffer++;
        System.out.println("Eleven genera un evento");
        if (buffer == 5){
            bufferlleno = true;
            buffervacio = false;
        }else if(buffer == 0){
            buffervacio = true;
            bufferlleno = false;
        }
        notify();
    }

}
