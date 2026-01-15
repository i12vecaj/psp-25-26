
import java.io.*;
import java.net.*;

public class cliente2  {
    public static void main(String[] args) throws Exception {
        String Host = "localhost";
        int Puerto = 6000;//puerto remoto

        System.out.println("PROGRAMA CLIENTE INICIADO....");
        Socket Cliente = new Socket(Host, Puerto);

        // CREO FLUJO DE SALIDA AL SERVIDOR
        DataOutputStream flujoSalida = new DataOutputStream(Cliente.getOutputStream());

        // ENVIO UN SALUDO AL SERVIDOR
        int puertoRemoto = Cliente.getPort();
        int puertoLocal = Cliente.getLocalPort();
        String ipHost = Cliente.getInetAddress().toString();
        String prueba = Cliente.getLocalSocketAddress().toString();




        // ENVIO UN SALUDO AL SERVIDOR
        flujoSalida.writeUTF("Saludos al SERVIDOR DESDE EL CLIENTE 2," +
                " El puerto remoto es: "+puertoRemoto+
                " El puerto local: "+puertoLocal+
                " El IP host: "+ipHost+
                " El IP y puerto local juntos: "+prueba);

        // CREO FLUJO DE ENTRADA AL SERVIDOR
        DataInputStream flujoEntrada = new DataInputStream(Cliente.getInputStream());

        // EL SERVIDOR ME ENVIA UN MENSAJE
        System.out.println("Recibiendo del SERVIDOR: \n\t" + flujoEntrada.readUTF());

        // CERRAR STREAMS Y SOCKETS
        flujoEntrada.close();
        flujoSalida.close();
        Cliente.close();
    }// main
}//