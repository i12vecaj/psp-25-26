import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {

    public static void main(String[] args) {
        Scanner lectorTeclado = new Scanner(System.in);
        String entradaUsuario = "";

        while (true) {
            System.out.println("Introduzca URL o IP (escriba 'localhost' para salir):");
            entradaUsuario = lectorTeclado.nextLine();

            try {
                Socket socketCliente = new Socket("127.0.0.1", 5000);
                DataOutputStream flujoSalida = new DataOutputStream(socketCliente.getOutputStream());
                DataInputStream flujoEntrada = new DataInputStream(socketCliente.getInputStream());

                flujoSalida.writeUTF(entradaUsuario);

                String respuestaServidor = flujoEntrada.readUTF();
                System.out.println("--- INFORMACION RECIBIDA ---");
                System.out.println(respuestaServidor);
                System.out.println("----------------------------");

                socketCliente.close();

                if (entradaUsuario.equalsIgnoreCase("localhost")) {
                    break;
                }
            } catch (IOException excepcionConexion) {
                System.out.println("Error de conexion: " + excepcionConexion.getMessage());
                break;
            }
        }
        lectorTeclado.close();
        System.out.println("Cliente finalizado.");
    }
}