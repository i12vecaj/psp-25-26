package Oráculo;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {
    public static void main(String[] args) {
        {


            try {
                int puerto = 6000;
                ServerSocket servidor = new ServerSocket(puerto);
                Socket cliente = null;
                cliente = servidor.accept();


                InputStream entrada;
                entrada = cliente.getInputStream();
                ObjectInputStream flujoEntrada = new ObjectInputStream(entrada);

                Numeros numerosObjeto = (Numeros) flujoEntrada.readObject();

                int valor= numerosObjeto.getNum();

                int cuadrado=(int) Math.pow(valor,2);
                int cubo=(int) Math.pow(valor,3);

                numerosObjeto.setCuadrado(cuadrado);
                numerosObjeto.setCubo(cubo);

                OutputStream salida = null;
                salida = cliente.getOutputStream();
                ObjectOutputStream flujoSalida = new ObjectOutputStream(salida);
                flujoSalida.writeObject(numerosObjeto);

                entrada.close();
                flujoEntrada.close();
                salida.close();
                flujoSalida.close();
                cliente.close();


            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                System.out.println("Cliente no detectado" + e.getMessage());
            }
        }
    }
}