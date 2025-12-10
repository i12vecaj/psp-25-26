

public class Cola {
    private String evento; //Aquí es donde se recibe el evento
    private boolean disponible = false;//inicialmente cola vacia

    public synchronized String get() {
        while (!disponible) {
            try {
                wait();
            } catch (InterruptedException e) { }
        }
        System.out.println("Eleven genera un evento.");
        disponible = false;
        notify();
        System.out.println("Esperando a que el buffer esté vacío");
        return evento;
    }




    public synchronized void put(String valor) {
        while (disponible){
            try {
                wait();
            } catch (InterruptedException e) { }
        }
        evento = valor;
        System.out.println("Esperando a que el buffer esté lleno");
        disponible = true;
        System.out.println("El Laboratorio procesa un evento.");
        notify();
    }



}