public class Main {
    public static void main(String[] args) {
        Buffer buffer = new Buffer();

        Thread ElevenProductor = new Thread(new ProductorEleven(buffer), "Eleven");
        Thread LaboratorioConsumidor = new Thread(new ConsumidorLaboratorio(buffer), "Laboratorio de Hawkins");

        ElevenProductor.start();
        LaboratorioConsumidor.start();
    }
}