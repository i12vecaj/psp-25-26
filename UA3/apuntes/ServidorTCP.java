import java.net.*;   // Clases para redes (Socket, ServerSocket)
import java.io.*;    // Clases para entrada y salida

public class ServidorTCP {
    public static void main(String[] args) throws IOException {

        // 1️⃣ Creamos el ServerSocket indicando el puerto
        // El servidor "escucha" en el puerto 5000
        ServerSocket servidor = new ServerSocket(5000);
        System.out.println("Servidor esperando conexión...");

        // 2️⃣ accept() bloquea el programa hasta que un cliente se conecta
        Socket cliente = servidor.accept();
        System.out.println("Cliente conectado");

        // 3️⃣ Flujo de entrada: para recibir datos del cliente
        BufferedReader entrada = new BufferedReader(
                new InputStreamReader(cliente.getInputStream()));

        // 4️⃣ Flujo de salida: para enviar datos al cliente
        PrintWriter salida = new PrintWriter(
                cliente.getOutputStream(), true); // true = auto flush

        // 5️⃣ Leemos el mensaje enviado por el cliente
        String mensaje = entrada.readLine();
        System.out.println("Cliente dice: " + mensaje);

        // 6️⃣ Enviamos respuesta al cliente
        salida.println("Mensaje recibido correctamente");

        // 7️⃣ Cerramos conexiones
        cliente.close();
        servidor.close();
    }
}
