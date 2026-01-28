package tests;
import java.net.Socket;

public class TestdeVacio {
    public static void main(String[] args) {
        String host = "localhost";
        int port = 5000;
        try {
            ejemploAtaque(host, port);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void ejemploAtaque(String host, int port) throws Exception {
        // 1 ATAQUE Conectar un socket sin enviar nada
        Socket socket = new Socket(host, port);
        System.out.println("Conectado... pero no diré nada.");
        Thread.sleep(60000); // Se queda callado 1 minuto

        socket.close();
        System.out.println("Me desconecto.");
    }
}

