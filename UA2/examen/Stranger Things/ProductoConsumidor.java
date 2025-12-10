public class ProductoConsumidor {
    public static void main(String[] args) {
        Cola bufferCompartido = new Cola();

        Productor eleven = new Productor(bufferCompartido, 1);
        Consumidor laboratorioHawkings = new Consumidor(bufferCompartido, 1);

        System.out.println("Iniciando experiento:");

        eleven.start();
        laboratorioHawkings.start();

    }
}