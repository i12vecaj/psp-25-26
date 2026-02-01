package Lacuevadeldragon;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServidor {
    public static void main(String[] args) {
        {


            try {
                int puerto = 6000;
                ServerSocket servidor = new ServerSocket(puerto);
                Socket cliente = null;
                cliente = servidor.accept();
                //DataInputStream flujoEntrada = new DataInputStream(entrada);




                InputStream entrada;
                entrada = cliente.getInputStream();
                ObjectInputStream flujoEntrada = new ObjectInputStream(entrada);

                Cliente client = (Cliente) flujoEntrada.readObject();

                HiloServidor hilo = new HiloServidor(cliente,client);
                hilo.start();
                hilo.sleep((long)(Math.random() * 5000));




/*
                OutputStream salida = null;
                salida = cliente.getOutputStream();
                DataOutputStream flujoSalida = new DataOutputStream(salida);
                flujoSalida.writeUTF(client.getMensaje().toUpperCase());
 */
                //entrada.close();
                //flujoEntrada.close();
                //salida.close();
                //flujoSalida.close();
                //cliente.close();


            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                System.out.println("Cliente no detectado" + e.getMessage());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
