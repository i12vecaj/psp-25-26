import java.net.*;   // Clases de red
import java.io.*;    // Entrada y salida

public class ClienteTCP {
    public static void main(String[] args) throws IOException {

        // 1️⃣ Creamos el socket indicando IP y puerto del servidor
        Socket cliente = new Socket("localhost", 5000);

        // 2️⃣ Flujo para recibir datos del servidor
        BufferedReader entrada = new BufferedReader(
                new InputStreamReader(cliente.getInputStream()));

        // 3️⃣ Flujo para enviar datos al servidor
        PrintWriter salida = new PrintWriter(
                cliente.getOutputStream(), true);

        // 4️⃣ Enviamos un mensaje al servidor
        salida.println("Hola servidor");

        // 5️⃣ Leemos la respuesta del servidor
        String respuesta = entrada.readLine();
        System.out.println("Servidor responde: " + respuesta);

        // 6️⃣ Cerramos el socket
        cliente.close();
    }
}
