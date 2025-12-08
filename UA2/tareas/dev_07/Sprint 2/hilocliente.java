import java.net.*;
import java.io.*;
import java.util.Date;
import java.text.SimpleDateFormat;

public class hilocliente implements Runnable {
    
    private Socket socketCliente;
    private int idCliente;
    private multihilo servidor;
    
    private BufferedReader entrada;
    private PrintWriter salida;
    
    public hilocliente(Socket socket, int id, multihilo servidor) {
        this.socketCliente = socket;
        this.idCliente = id;
        this.servidor = servidor;
    }
    
    @Override
    public void run() {
        System.out.println("Hilo del cliente #" + idCliente + " iniciado");
        
        try {
            entrada = new BufferedReader(new InputStreamReader(socketCliente.getInputStream()));
            salida = new PrintWriter(socketCliente.getOutputStream(), true);
            
            enviarMensajeBienvenida();
            
            String mensajeCliente;
            boolean clienteActivo = true;
            
            while(clienteActivo && (mensajeCliente = entrada.readLine()) != null) {
                System.out.println("Cliente #" + idCliente + " dijo: " + mensajeCliente);
                
                String respuesta = procesarMensaje(mensajeCliente);
                salida.println(respuesta);
                
                if(mensajeCliente.equalsIgnoreCase("salir")){
                    clienteActivo = false;
                }
            }
            
        } catch (IOException e) {
            System.out.println("Error con cliente #" + idCliente + ": " + e.getMessage());
        } finally {
            cerrarConexion();
        }
    }
    
    private void enviarMensajeBienvenida() {
        String bienvenida = "BIENVENIDO AL SERVIDOR MULTIHILO";
            
        salida.println(bienvenida);
        System.out.println("Bienvenido cliente #" + idCliente);
    }
    
    private String procesarMensaje(String mensaje) {
        String mensajeMin = mensaje.toLowerCase();
        
        if(mensaje.equalsIgnoreCase("hola")) {
            return "Cómo estás, cliente #" + idCliente + "?";
        }
        else if(mensaje.equalsIgnoreCase("hora")) {
            return " Hora actual: " + obtenerHoraActual();
        }
        else if(mensaje.equalsIgnoreCase("info")) {
            return " Información del servidor:\n" +
                   "   - Cliente actual: #" + idCliente;
        }
        else if(mensajeMin.equals("salir")) {
            return "¡Adiós, cliente #" + idCliente + "!";
        }
        else {
            return "Mensaje recibido: " + mensaje;
        }
    }
    
    private String obtenerHoraActual() {
        SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
        return formato.format(new Date());
    }
    
    private void cerrarConexion() {
        try {
            salida.println("CONEXIÓN FINALIZADA");
            
            if(entrada != null) entrada.close();
            if(salida != null) salida.close();
            if(socketCliente != null && !socketCliente.isClosed()) {
                socketCliente.close();
            }
            
            servidor.clienteDesconectado();
            System.out.println("Conexión del cliente #" + idCliente);
            
        } catch (IOException e) {
            System.out.println("Error al cerrar la conexión del cliente #" + idCliente);
        }
    }
}