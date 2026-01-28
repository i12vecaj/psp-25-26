# Guía Definitiva: Comunicación de Red y Sockets en Java

## 1. Fundamentos de Redes para Desarrolladores

Para programar sockets, es vital entender dónde se sitúan en el **Modelo OSI**. Los sockets operan principalmente en la **Capa de Transporte (Capa 4)**, sirviendo de interfaz entre la aplicación y la red.

### Protocolos de Transporte

- **TCP (Transmission Control Protocol):** \* **Orientado a conexión:** Se establece un "circuito virtual" antes de enviar datos (**Three-way Handshake**).
- **Fiabilidad:** Reenvía paquetes perdidos, controla el flujo y garantiza que los datos lleguen en el orden correcto.
- **Uso:** HTTP, FTP, SMTP, SSH.

- **UDP (User Datagram Protocol):**
- **Sin conexión:** Envía "datagramas" de forma independiente sin previo aviso.
- **Rápido y ligero:** No tiene sobrecarga de confirmación ni ordenamiento.
- **Uso:** Streaming de video, VoIP, juegos online, DNS.

### Puertos y Direcciones IP

- **Dirección IP:** Identifica al host en la red.
- **Puerto (16 bits):** Identifica el proceso o servicio dentro del host.
- _Rango:_ 0 - 65535.
- _Sugerencia:_ Usa puertos superiores al 1024 para evitar conflictos con servicios del sistema (Well-known ports).

---

## 2. La API `java.net`

Java abstrae la complejidad de la red mediante clases específicas:

| Clase            | Función                                                | Protocolo |
| ---------------- | ------------------------------------------------------ | --------- |
| `Socket`         | Lado del cliente: inicia la conexión.                  | TCP       |
| `ServerSocket`   | Lado del servidor: escucha y acepta conexiones.        | TCP       |
| `DatagramSocket` | Envío y recepción de paquetes individuales.            | UDP       |
| `DatagramPacket` | El contenedor de los datos (el paquete).               | UDP       |
| `InetAddress`    | Representa direcciones IP (v4 o v6) y nombres de host. | Ambos     |

---

## 3. Arquitectura Cliente/Servidor Multihilo (TCP)

En aplicaciones reales, un servidor debe atender a **múltiples clientes simultáneamente**. Un servidor básico se bloquea con el primer cliente; para evitarlo, usamos **Threads**.

### Ejemplo: Servidor Multihilo

Este servidor acepta una conexión y lanza un hilo para gestionarla, quedando libre inmediatamente para aceptar a otro cliente.

```java
import java.io.*;
import java.net.*;

public class ServidorMultihilo {
    public static void main(String[] args) {
        int puerto = 5000;
        try (ServerSocket serverSocket = new ServerSocket(puerto)) {
            System.out.println("Servidor iniciado en puerto " + puerto);

            while (true) {
                Socket cliente = serverSocket.accept();
                System.out.println("Nuevo cliente conectado: " + cliente.getInetAddress());

                // Delegar la atención del cliente a un hilo independiente
                new Thread(new ManejadorCliente(cliente)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ManejadorCliente implements Runnable {
    private Socket socket;

    public ManejadorCliente(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Mensaje recibido: " + inputLine);
                out.println("Servidor dice: " + inputLine.toUpperCase());
                if (inputLine.equals("adiós")) break;
            }
        } catch (IOException e) {
            System.err.println("Error con el cliente: " + e.getMessage());
        } finally {
            try { socket.close(); } catch (IOException e) { e.printStackTrace(); }
        }
    }
}

```

---

## 4. Comunicación mediante Objetos (Serialización)

A menudo no solo enviamos texto, sino objetos complejos. Para esto usamos `ObjectOutputStream` y `ObjectInputStream`.

> **Regla de oro:** La clase del objeto a enviar debe implementar la interfaz `Serializable`.

```java
// Clase común que deben conocer cliente y servidor
import java.io.Serializable;

public class Mensaje implements Serializable {
    private String emisor;
    private String contenido;

    public Mensaje(String emisor, String contenido) {
        this.emisor = emisor;
        this.contenido = contenido;
    }
    // Getters y Setters...
}

```

**Envío del objeto (en el Cliente):**

```java
ObjectOutputStream outObj = new ObjectOutputStream(socket.getOutputStream());
outObj.writeObject(new Mensaje("Admin", "Reinicio de sistema"));

```

---

## 5. Comunicación UDP (Datagramas)

UDP no garantiza que el mensaje llegue, pero es extremadamente eficiente.

### Ejemplo: Envío de Datagramas

```java
// Servidor UDP que escucha
DatagramSocket ds = new DatagramSocket(6000);
byte[] buffer = new byte[1024];
DatagramPacket dp = new DatagramPacket(buffer, buffer.length);

ds.receive(dp); // El método se bloquea hasta recibir algo
String mensaje = new String(dp.getData(), 0, dp.getLength());
System.out.println("Recibido vía UDP: " + mensaje);

```

---

## 6. Diferencias y Casos de Uso (Tabla Comparativa)

| Característica     | TCP Sockets                           | UDP Sockets                             |
| ------------------ | ------------------------------------- | --------------------------------------- |
| **Garantía**       | Entrega confirmada y ordenada.        | No hay garantía de entrega.             |
| **Velocidad**      | Menor (debido al control de errores). | Muy alta (mínima latencia).             |
| **Forma de datos** | Flujo continuo de bytes (Stream).     | Paquetes discretos (Datagramas).        |
| **Uso Ideal**      | Transferencia de archivos, Chat, Web. | Juegos online, Streaming, Sensores IoT. |

---

## 7. Buenas Prácticas y Seguridad

1. **Try-with-resources:** Asegura que los sockets y streams se cierren automáticamente para evitar fugas de memoria y puertos bloqueados.
2. **Timeouts:** Configura `socket.setSoTimeout(ms)` para evitar que el programa se quede colgado indefinidamente esperando datos.
3. **Seguridad (SSL/TLS):** En entornos reales, usa `SSLSocket` y `SSLServerSocket` para cifrar la comunicación.
4. **Validación:** Siempre valida y limpia los datos recibidos antes de procesarlos para evitar ataques de inyección.

### Glosario Técnico Avanzado

- **Backlog:** La cantidad de conexiones entrantes que el sistema puede poner en cola antes de empezar a rechazarlas (parámetro en `ServerSocket`).
- **Loopback (127.0.0.1):** Dirección IP especial que apunta a la propia máquina actual.
- **NIO (Non-blocking I/O):** Una alternativa avanzada en Java (`java.nio`) para manejar miles de conexiones con muy pocos hilos.
