import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;

public class ServidorNumeros {
    public static void main(String[] args) {
        try(ServerSocket servidor = new ServerSocket(6069)){
            Socket cliente = servidor.accept();

            ObjectInputStream entrada = new ObjectInputStream(cliente.getInputStream());
            ObjectOutputStream salida = new ObjectOutputStream(cliente.getOutputStream());

            Numeros numerin =(Numeros) entrada.readObject();

            int num = numerin.getNumero();
            numerin.setCuadrado((long) num * num);
            numerin.setCubo((long) num * num * num);
            salida.writeObject(numerin);

            cliente.close();

        }
        catch(IOException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
