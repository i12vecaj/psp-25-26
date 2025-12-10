public class Main {
    public static void main(String[] args){
        Productor eleven = new Productor("Eleven");
        Consumidor laboratorio = new Consumidor("Laboratorio de Hawkins", eleven);

        Thread hilo = new Thread(laboratorio);
        hilo.start();
    }
}
