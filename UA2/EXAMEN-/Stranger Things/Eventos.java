public class Eventos {
    public static void main(String[] args) {
        Buffer buffer = new Buffer(5); 
        Productor productor = new Productor(buffer, 20); 
        Laboratorio laboratorio = new Laboratorio(buffer, 20); 

        productor.start(); 
        laboratorio.iniciarConsumo(); 
    }
}
