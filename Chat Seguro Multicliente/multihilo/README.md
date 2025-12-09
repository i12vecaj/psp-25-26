## 🧵 Sprint 2 – Servidor multicliente con hilos

### 🎯 Objetivo

Desarrollar un servidor TCP que gestione múltiples clientes simultáneamente mediante hilos.

### ✅ Tareas

- [x] Crear clase `MultiThreadedServer` que acepte conexiones en un puerto.
- [x] Cada cliente debe ser gestionado por un hilo diferente.
- [x] Implementar mensajes tipo: "Bienvenido", "Hasta luego", etc.
- [x] Permitir que los clientes se conecten desde terminal o clase Java.

### 📦 Entregables

- Código funcional en carpeta `/multihilo`.
- Captura de pruebas con 2 o más clientes conectados.
- Documentación breve en README.

### 📸 Capturas de pantalla

<div align="center">
<img src="https://i.imgur.com/TGAyZEQ.png"/>

_[Servidor Inet] 2 clientes hablando desde la perspectiva de servidor_

<img src="https://i.imgur.com/chnHl7u.png"/>

_[Servidor Estándar] Cliente #3 observando a Cliente #1 y Cliente #2 hablando_

</div>

### 🚀 Instrucciones de Uso

#### Ejecución (Elige tu versión)

| Versión      | Puerto | Comando                                          |
| :----------- | :----- | :----------------------------------------------- |
| **Estándar** | 8080   | `java -cp bin server.MultiThreadedServer`        |
| **Atomic**   | 8081   | `java -cp bin server.MultiThreadedServerAtomic`  |
| **Inet**     | 8082   | `java -cp bin server.MultiThreadedServerInet`    |
| **Antigua**  | 8079   | `java -cp bin server.MultiThreadedServerAntiguo` |

"bin" es una carpeta donde meter los archivos compilados

#### Ejecución del Cliente

```bash
java -cp bin client.Cliente
```

_Por defecto conecta al puerto 8082 (o al configurado en código). Para otros servidores, modifica `PORT` en `Cliente.java` y recompila._

---

### 📚 Notas

En principio la tarea era básica pero al final me puse a hacer varias cosas y aprendiendo por el camino, como consecuencia las diferentes versiones que hay como servidor.

#### 1. Comparativa de Versiones

Diferencias entre las implementaciones del servidor:

| Característica     | `MultiThreadedServer`   | `MultiThreadedServerAtomic`  | `MultiThreadedServerInet` | `MultiThreadedServerAntiguo` |
| :----------------- | :---------------------- | :--------------------------- | :------------------------ | :--------------------------- |
| **Generación IDs** | `static int` (Básico)   | `AtomicInteger` (Seguro)     | N/A (Usa IP:Puerto)       | `static int` (Básico)        |
| **Comunicación**   | Broadcast (Chat Grupal) | Broadcast (Chat Grupal)      | Broadcast (Chat Grupal)   | Echo (Individual)            |
| **Identificación** | ID Numérico (#1, #2...) | ID Numérico (#1, #2...)      | IP + Puerto Remoto        | ID Numérico (#1, #2...)      |
| **Concurrencia**   | `synchronized(Set)`     | `synchronized(Set)` + Atomic | `synchronized(Set)`       | Aislamiento por hilo         |

#### 2. Conceptos Clave Implementados

**📢 Broadcast (Difusión)**

El broadcast permite que los clientes "hablen" entre sí. (Osea el servidor reenvía el mensaje a todos los clientes conectados)

- **Implementación**: El servidor mantiene una lista (`Set<ClientHandler>`) de todos los clientes conectados.
- **Funcionamiento**: Cuando llega un mensaje, el servidor recorre esa lista y envía el mensaje a todos los sockets excepto al remitente.
- **Sincronización**: Se usa `Collections.synchronizedSet` o bloques `synchronized` para evitar errores si un cliente se conecta/desconecta mientras se envía un mensaje.

**⚡ AtomicInteger vs static int**

- En la versión estándar usamos `static int id++`. Esto **no es thread-safe** teóricamente si múltiples hilos aceptaran conexiones a la vez (aunque aquí solo acepta `main`).
- En la versión **Atomic**, usamos `AtomicInteger`. Sus métodos (como `incrementAndGet()`) son atómicos, lo que garantiza que dos hilos nunca obtengan el mismo ID, incluso bajo carga extrema concurrente.

**🌐 InetAddress (Versión semi-ideal)**

En la versión **Inet**, en lugar de inventar un ID, preguntamos al socket:

- `socket.getInetAddress().getHostAddress()`: IP del cliente.
- `socket.getPort()`: Puerto efímero del cliente.

Esto identifica de forma única y real a cada conexión. Faltaría asignarle un nombre o un identificador más básico pero eso es Quality of Life.
