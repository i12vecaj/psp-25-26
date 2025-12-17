import java.net.*;
import java.io.*;

public class multihilo {
    
    private ServerSocket serverSocket;
    private boolean servidorActivo;
    private int contadorClientes;
    
    public static void main(String[] args) {
        System.out.println("INICIANDO SERVIDOR MULTICLIENTE");
        multihilo servidor = new multihilo();
        servidor.iniciarServidor(8080);
    }
    
    public void iniciarServidor(int puerto) {
        try {
            serverSocket = new ServerSocket(puerto);
            servidorActivo = true;
            contadorClientes = 0;
            
            System.out.println("Servidor escuchando en puerto: " + puerto);
            System.out.println("IP Local: " + InetAddress.getLocalHost().getHostAddress());
            System.out.println("Esperando conexiones de clientes...");
            
            while(servidorActivo) {
                Socket socketCliente = serverSocket.accept();
                contadorClientes++;
                
                System.out.println("Nuevo cliente conectado #" + contadorClientes);
                System.out.println("IP Cliente: " + socketCliente.getInetAddress());
                System.out.println("Clientes conectados: " + contadorClientes);
                
                hilocliente hilo = new hilocliente(socketCliente, contadorClientes, this);
                Thread hiloCliente = new Thread(hilo);
                hiloCliente.start();
                
                System.out.println("Hilo creado para el cliente #" + contadorClientes);
                }
            
        } catch (IOException e) {
            System.out.println("Error en el servidor: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public synchronized void clienteDesconectado() {
        contadorClientes--;
        System.out.println("Cliente desconectado. Clientes activos: " + contadorClientes);
    }
    
    public void cerrarServidor() {
        try {
            servidorActivo = false;
            if(serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
            System.out.println("Servidor cerrado correctamente");
        } catch (IOException e) {
            System.out.println("Error al cerrar el servidor");
        }
    }
}