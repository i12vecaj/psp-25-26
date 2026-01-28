# 1. Diagrama de Flujo de la Conexión

Antes de tocar código, visualicemos qué va a pasar. El servidor actúa como un "puerto de embarque" que espera. Cuando llegan los clientes,
se asignan canales de comunicación específicos (Sockets).
![Diagrama de Flujo](/imagenes/grafico2.png)

## Código del Servidor (Servidor.java)

Este código abrirá el puerto y se quedará esperando (bloqueado) 
hasta que entre alguien. Lo configuraremos para atender exactamente a 2 clientes.
````java
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    public static void main(String[] args) {
        // Puerto donde el servidor "escuchará"
        final int PUERTO = 6000; 

        try {
            // 1. Iniciamos el servidor
            ServerSocket servidor = new ServerSocket(PUERTO);
            System.out.println("--- Servidor iniciado esperando a 2 clientes ---");

            // 2. Bucle para aceptar exactamente 2 clientes
            for (int i = 1; i <= 2; i++) {
                // El programa se detiene aquí (bloqueo) hasta que entra un cliente
                Socket clienteConectado = servidor.accept(); 
                
                System.out.println("\nCLIENTE " + i + " CONECTADO:");
                System.out.println("------------------------------------------------");
                
                // EXPLICACIÓN DEL MÉTODO BERNARDO:
                // getLocalPort(): Es el puerto del servidor (6000).
                // getPort(): Es el puerto desde donde viene el cliente (aleatorio/efímero).
                System.out.println(" -> Puerto Local (Mío/Servidor): " + clienteConectado.getLocalPort());
                System.out.println(" -> Puerto Remoto (Del Cliente): " + clienteConectado.getPort());
                
                // Cerramos la conexión con este cliente específico
                clienteConectado.close();
            }

            System.out.println("\nSe han atendido los 2 clientes. Cerrando servidor.");
            servidor.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
````
## 3. Código del Cliente (Cliente.java)

El cliente es quien inicia la acción. Necesita saber la IP (localhost en este caso) y el puerto del servidor (6000).

````java
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Cliente {
    public static void main(String[] args) {
        // Datos del servidor al que vamos a llamar
        final String HOST = "localhost";
        final int PUERTO = 6000;

        try {
            System.out.println("Intentando conectar al servidor...");
            
            // 1. Establecemos la conexión (El Handshake)
            Socket socket = new Socket(HOST, PUERTO);

            // 2. Obtenemos la dirección IP
            InetAddress direccionRemota = socket.getInetAddress();

            System.out.println("\n--- CONEXIÓN ESTABLECIDA ---");
            
            // EXPLICACIÓN DEL MÉTODO BERNARDO:
            // getLocalPort(): El sistema operativo asignó un puerto libre a este cliente (ej: 50123).
            // getPort(): El puerto al que nos conectamos en el servidor (6000).
            System.out.println(" -> Mi Puerto Local (Cliente): " + socket.getLocalPort());
            System.out.println(" -> Puerto Remoto (Servidor): " + socket.getPort());
            System.out.println(" -> IP de la máquina remota:  " + direccionRemota.getHostAddress());

            // 3. Cerramos
            socket.close();
            System.out.println("\nCliente finalizado.");

        } catch (UnknownHostException e) {
            System.out.println("No se encuentra el host");
        } catch (IOException e) {
            System.out.println("Error de entrada/salida o servidor no disponible");
        }
    }
}
````
### Conceptos Clave



La perspectiva lo es todo:

En el Servidor, getLocalPort() devuelve 6000 (donde él vive).

En el Cliente, getPort() devuelve 6000 (a donde él viaja).

Es como una llamada telefónica: Mi número es "Local", el número al que llamo es "Remoto".

El Puerto Efímero:

Notarás que el cliente muestra un "Puerto Local" raro (ej: 54321). Tú no programaste ese número. El Sistema Operativo se lo asignó automáticamente porque para haber una comunicación bidireccional, el cliente también necesita un puerto abierto para recibir la respuesta del servidor.

