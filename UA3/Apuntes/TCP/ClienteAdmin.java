// Cliente TCP Admin: permite elegir entre enviar un saludo o pedir el cierre del servidor.
//
// PASOS (cliente admin TCP):
// 1) Mostrar menú y leer opción.
// 2) Validar opción y construir el mensaje.
// 3) Conectarse al servidor con Socket(host, puerto).
// 4) Crear DataOutputStream y enviar mensaje con writeUTF().
// 5) Crear DataInputStream y leer respuesta con readUTF().
// 6) Cerrar streams y socket.

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ClienteAdmin  {
    public static void main(String[] args) throws Exception {
        // 1) Entrada por teclado.
        Scanner sc = new Scanner(System.in);

        // 1) Datos del servidor.
        String Host = "localhost";
        int Puerto = 6000, opcion;
        String mensaje;

        // 1) Menú.
        System.out.println("Que mensaje deseas enviar al servidor, usuario Admin?");
        System.out.println("1. Cerrar Servidor");
        System.out.println("2. Saludar al Servidor");

        // 1) Leer opción.
        opcion = sc.nextInt();

        // 2) Validación sencilla.
        do {
            if (opcion < 1 || opcion > 2) {
                System.out.println("Opcion no valida. Introduce 1 o 2");
                opcion = sc.nextInt();
            }
        } while (opcion < 1 || opcion > 2);

        // 2) Construir mensaje.
        if (opcion == 1) {
            // Si el servidor recibe exactamente esto, se cerrará.
            mensaje = "Cerrar Servidor -1234-";
        } else {
            mensaje = "Saludos al Servidor -Admin-";
        }

        System.out.println("Cliente Admin iniciado....");

        // 3) Conectar con el servidor por TCP.
        Socket Cliente = new Socket(Host, Puerto);

        System.out.println("-- Informacion del Socket Cliente --");
        System.out.println("Puerto local del socket cliente: " + Cliente.getLocalPort());
        System.out.println("Puerto remoto del socket cliente: " + Cliente.getPort());
        System.out.println("Direccion ip de la maquina remota: " + Cliente.getInetAddress().getHostAddress());
        System.out.println("------------------------------------");

        // 4) Enviar el mensaje al servidor.
        DataOutputStream flujoSalida = new DataOutputStream(Cliente.getOutputStream());
        flujoSalida.writeUTF(mensaje);

        // 5) Leer la respuesta del servidor.
        DataInputStream flujoEntrada = new DataInputStream(Cliente.getInputStream());
        System.out.println("Recibiendo del Server: \n\t" + flujoEntrada.readUTF());

        // 6) Cerrar recursos.
        flujoEntrada.close();
        flujoSalida.close();
        Cliente.close();
    }
}