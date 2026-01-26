import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorMayusculas {
    public static void main(String[] args){
        try (ServerSocket servidorcito = new ServerSocket (6001)){
            Socket clientecito = servidorcito.accept();

            BufferedReader entradita = new BufferedReader(
                new InputStreamReader(clientecito.getInputStream()));
            PrintWriter salidita = new PrintWriter(
                clientecito.getOutputStream(), true);
            String mensaje = entradita.readLine();
            salidita.println(mensaje.toUpperCase());
            clientecito.close();           
        } catch (IOException e){
            System.out.println("Error: " + e.getMessage());
        }
    }
}
