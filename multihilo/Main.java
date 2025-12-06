import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        // se crea el servidor en el puerto 5000
        MultiThreadedServer server = new MultiThreadedServer(5000);
        server.start(); // Lo arrancamos
    }
}
