package tests;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Arrays;

public class TestMensajePesado {

    public static void main(String[] args) {
        String host = "localhost";
        int port = 5000;
        
        // Creamos un mensaje de 50 MB aproximadamente
        // Usamos una cadena grande convertida a bytes
        System.out.println(">>> Preparando mensaje pesado (carga útil)...");
        byte[] cargaPesada = new byte[50 * 1024 * 1024]; // 50 Megabytes
        Arrays.fill(cargaPesada, (byte) 'A'); // Llenamos con el carácter 'A'

        System.out.println(">>> Intentando conectar al servidor...");

        try (Socket socket = new Socket()) {
            // 1. Conexión
            socket.connect(new InetSocketAddress(host, port), 5000);
            System.out.println(">>> Conectado. Enviando " + (cargaPesada.length / 1024 / 1024) + " MB...");

            DataOutputStream flujoSalida = new DataOutputStream(socket.getOutputStream());

            // 2. Envío del mensaje pesado
            // Usamos write en lugar de writeUTF para evitar el límite de 65535 bytes de writeUTF
            // y realmente saturar el stream de red.
            long tiempoInicio = System.currentTimeMillis();
            
            flujoSalida.write(cargaPesada);
            flujoSalida.flush(); 

            long tiempoFin = System.currentTimeMillis();
            System.out.println(">>> Envío finalizado.");
            System.out.println(">>> Tiempo transcurrido: " + (tiempoFin - tiempoInicio) + " ms");

            // Mantener un momento para observar el estado del servidor
            Thread.sleep(2000);

        } catch (IOException | InterruptedException e) {
            System.err.println("XXX Error durante la prueba de carga: " + e.getMessage());
            e.printStackTrace();
        }
    }
}