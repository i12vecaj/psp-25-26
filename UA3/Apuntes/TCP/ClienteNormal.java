// Cliente TCP normal: se conecta al servidor, envía un saludo y lee la respuesta.
//
// PASOS (cliente normal TCP):
// 1) Definir host y puerto del servidor.
// 2) Crear un Socket apuntando a host:puerto (se establece la conexión TCP).
// 3) Crear un DataOutputStream para enviar datos al servidor.
// 4) Enviar un String con writeUTF().
// 5) Crear un DataInputStream para leer la respuesta del servidor.
// 6) Leer la respuesta con readUTF() y mostrarla.
// 7) Cerrar streams y socket.

import java.io.*;
import java.net.*;

public class ClienteNormal  {
    public static void main(String[] args) throws Exception {
        // 1) Datos del servidor.
        String Host = "localhost";
        int Puerto = 6000;

        System.out.println("Cliente Fran iniciado....");

        // 2) Conexión TCP con el servidor.
        Socket Cliente2 = new Socket(Host, Puerto);

        System.out.println("-- Informacion del Socket Cliente --");
        System.out.println("Puerto local del socket cliente: " + Cliente2.getLocalPort());
        System.out.println("Puerto remoto del socket cliente: " + Cliente2.getPort());
        System.out.println("Direccion ip de la maquina remota: " + Cliente2.getInetAddress().getHostAddress());
        System.out.println("------------------------------------");

        // 3) Stream de salida hacia el servidor.
        DataOutputStream flujoSalida = new DataOutputStream(Cliente2.getOutputStream());

        // 4) Enviar mensaje (UTF: longitud + bytes).
        flujoSalida.writeUTF("Saludos al Servidor -Fran-");

        // 5) Stream de entrada desde el servidor.
        DataInputStream flujoEntrada = new DataInputStream(Cliente2.getInputStream());

        // 6) Leer respuesta y mostrarla.
        System.out.println("Recibiendo desde el server: \n\t" + flujoEntrada.readUTF());

        // 7) Cerrar recursos.
        flujoEntrada.close();
        flujoSalida.close();
        Cliente2.close();
    }
}