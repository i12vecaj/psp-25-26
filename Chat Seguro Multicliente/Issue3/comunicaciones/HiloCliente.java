package comunicaciones;
import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;

public class HiloCliente extends Thread {

    private Socket socket;
    private BufferedReader entrada;
    private PrintWriter salida;
    private String nombreUsuario = "SinNombre";

    public HiloCliente(Socket socket) {
        this.socket = socket;
        try {
            entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            salida = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            System.out.println("Error al crear flujos");
        }
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void enviarMensaje(String mensaje) {
        salida.println(mensaje);
    }

    @Override
    public void run() {
        try {
            salida.println("HOLIII!. Usa /name para cambiar tu nombre.");

            String mensaje;
            while ((mensaje = entrada.readLine()) != null) {

                if (mensaje.equalsIgnoreCase("/exit")) {
                    salida.println("Desconectado");
                    break;
                }

                if (mensaje.equalsIgnoreCase("/list")) {
                    salida.println(Servidor.listaUsuarios());
                    continue;
                }

                if (mensaje.startsWith("/name")) {
                    String[] partes = mensaje.split(" ");
                    if (partes.length == 2 && Servidor.nombreDisponible(partes[1])) {
                        nombreUsuario = partes[1];
                        salida.println("Nombre cambiado a " + nombreUsuario);
                    } else {
                        salida.println("Nombre no válido o ya en uso");
                    }
                    continue;
                }

                String mensajeFinal = nombreUsuario + ": " + mensaje;
                Servidor.broadcast(mensajeFinal, this);
                registrarLog(mensajeFinal);
            }

        } catch (IOException e) {
            System.out.println("Cliente desconectado");
        } finally {
            Servidor.eliminarCliente(this);
            try {
                socket.close();
            } catch (IOException e) {
            
            }
        }
    }

    private void registrarLog(String mensaje) {
        try (FileWriter fw = new FileWriter("chatlog.txt", true)) {
            fw.write(LocalDateTime.now() + " - " + mensaje + "\n");
        } catch (IOException e) {
            System.out.println("Error al escribir log");
        }
    }
}
