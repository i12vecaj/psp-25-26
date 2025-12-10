public class StrangerThings {
    public static void main(String[] args) throws Exception {
        System.out.println("Bienvenido al tanquilo pueblo de Hawkins");

        // Crear el pueblo en el que suceden los eventos
        Pueblo Hawkins = new Pueblo("Hawkins",5);

        // Creo a Eleven(productora)
        HiloEleven eleven = new HiloEleven("Eleven", Hawkins);

        // Crear al laboratorio (consumidor)
        HiloLaboratorio laboratorio = new HiloLaboratorio("Laboratorio", Hawkins);

        // Iniciar todos los hilos
        eleven.start();

        laboratorio.start();
      
    }
}

