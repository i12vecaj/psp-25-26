# Servidor de Chat Multicliente - Solución Gregorio Ruiz

## Descripción General

Esta solución implementa un servidor de chat TCP multicliente con arquitectura basada en hilos. El servidor permite que múltiples clientes se conecten simultáneamente y se comuniquen entre sí en tiempo real.

## Arquitectura del Sistema

### Componentes Principales

1. **MultiThreadedServer**: Servidor principal que acepta conexiones
2. **ClientHandler**: Manejador de cliente ejecutado en hilo independiente
3. **SimpleClient**: Cliente de chat con interfaz de consola

### Diagrama de Arquitectura

```
┌─────────────────────────────────────────────────────────┐
│                  MultiThreadedServer                    │
│  - Puerto: 8080                                         │
│  - Gestiona lista de clientes activos                   │
│  - Crea un hilo por cada conexión                       │
└─────────────────┬───────────────────────────────────────┘
                  │
                  │ Acepta conexiones
                  │
      ┌───────────┴───────────┬────────────────┐
      │                       │                │
      ▼                       ▼                ▼
┌──────────┐          ┌──────────┐      ┌──────────┐
│  Client  │          │  Client  │      │  Client  │
│ Handler  │          │ Handler  │ ...  │ Handler  │
│  (Hilo)  │          │  (Hilo)  │      │  (Hilo)  │
└────┬─────┘          └────┬─────┘      └────┬─────┘
     │                     │                   │
     │                     │                   │
     ▼                     ▼                   ▼
┌──────────┐          ┌──────────┐      ┌──────────┐
│  Socket  │          │  Socket  │      │  Socket  │
│ Cliente1 │          │ Cliente2 │      │ Cliente3 │
└──────────┘          └──────────┘      └──────────┘
```

## Características Implementadas

### Funcionalidades del Servidor

✅ **Multihilo**: Cada cliente es gestionado por un hilo independiente
✅ **Thread-Safe**: Uso de colecciones sincronizadas y métodos sincronizados
✅ **Broadcasting**: Los mensajes se retransmiten a todos los clientes conectados
✅ **Gestión de Conexiones**: Control de conexiones activas y desconexiones
✅ **Logging Detallado**: Registro de todas las operaciones con timestamps
✅ **Identificación de Clientes**: Cada cliente recibe un ID único
✅ **Nombres de Usuario**: Los clientes pueden establecer su nombre
✅ **Mensajes del Sistema**: Notificaciones de conexión/desconexión

### Funcionalidades del Cliente

✅ **Conexión TCP**: Establece conexión con el servidor
✅ **Envío Asíncrono**: Hilo dedicado para enviar mensajes
✅ **Recepción Asíncrona**: Hilo dedicado para recibir mensajes
✅ **Interfaz de Consola**: Interacción mediante entrada/salida estándar
✅ **Comandos**: Soporte para comandos como 'exit' y 'salir'
✅ **Manejo de Errores**: Gestión robusta de errores de conexión

## Estructura de Archivos

```
solucionGregorioRuiz/
├── MultiThreadedServer.java    # Servidor principal
├── ClientHandler.java          # Manejador de clientes
├── SimpleClient.java           # Cliente de chat
└── README.md                   # Esta documentación
```

## Compilación y Ejecución

### Requisitos Previos

- Java Development Kit (JDK) 8 o superior
- Sistema operativo: Windows, Linux o macOS

### Compilación

```bash
# Compilar todos los archivos Java
javac MultiThreadedServer.java ClientHandler.java SimpleClient.java
```

### Ejecución

**1. Iniciar el Servidor**

```bash
java MultiThreadedServer
```

Salida esperada:
```
[SERVER HH:mm:ss] Servidor Multicliente iniciado
[SERVER HH:mm:ss] Escuchando en puerto: 8080
[SERVER HH:mm:ss] Esperando conexiones...
----------------------------------------
```

**2. Iniciar Cliente(s)**

En otra(s) terminal(es):

```bash
java SimpleClient
```

Salida esperada:
```
[CLIENT HH:mm:ss] Iniciando cliente de chat...
[CLIENT HH:mm:ss] Conectando a localhost:8080
[CLIENT HH:mm:ss] Conexion establecida correctamente
========================================
    SERVIDOR DE CHAT SEGURO v1.0
========================================
Conexion establecida correctamente
Servidor: localhost:8080
Timestamp: HH:mm:ss
----------------------------------------

Por favor, ingrese su nombre de usuario:
```

## Flujo de Comunicación

### Secuencia de Conexión

```
Cliente                    Servidor                    Otros Clientes
  │                           │                              │
  │──── Socket Connect ───────▶│                              │
  │                           │                              │
  │                           │ Crea ClientHandler           │
  │                           │ Inicia nuevo hilo            │
  │                           │                              │
  │◀── Mensaje Bienvenida ────│                              │
  │                           │                              │
  │◀── Solicita Nombre ───────│                              │
  │                           │                              │
  │──── Envía Nombre ─────────▶│                              │
  │                           │                              │
  │◀── Confirmación ──────────│                              │
  │                           │                              │
  │                           │──── [SISTEMA] Usuario se ────▶│
  │                           │     ha conectado             │
```

### Secuencia de Envío de Mensaje

```
Cliente A                  Servidor                    Cliente B
  │                           │                              │
  │──── "Hola mundo" ─────────▶│                              │
  │                           │                              │
  │                           │ Procesa mensaje              │
  │                           │                              │
  │◀── [ENVIADO] Hola ────────│                              │
  │     mundo                 │                              │
  │                           │                              │
  │                           │──── [HH:mm:ss] <UserA> ──────▶│
  │                           │     Hola mundo               │
```

### Secuencia de Desconexión

```
Cliente                    Servidor                    Otros Clientes
  │                           │                              │
  │──── "exit" ───────────────▶│                              │
  │                           │                              │
  │                           │ Procesa desconexión          │
  │                           │                              │
  │◀── Mensaje Despedida ─────│                              │
  │                           │                              │
  │                           │──── [SISTEMA] Usuario se ────▶│
  │                           │     ha desconectado          │
  │                           │                              │
  │ Cierra Socket            │ Libera recursos              │
  │                           │ Elimina de lista             │
```

## Detalles de Implementación

### Thread Safety

La aplicación utiliza varias técnicas para garantizar la seguridad en entornos multihilo:

1. **Colecciones Sincronizadas**:
   ```java
   private static Set<ClientHandler> clientHandlers = 
       Collections.synchronizedSet(new HashSet<>());
   ```

2. **Métodos Sincronizados**:
   ```java
   public static synchronized void broadcast(String message, ClientHandler sender) {
       // Código thread-safe
   }
   ```

3. **Sincronización en Escritura**:
   ```java
   public synchronized void sendMessage(String message) {
       if (out != null && isConnected) {
           out.println(message);
       }
   }
   ```

### Gestión de Recursos

Todos los recursos se gestionan adecuadamente:

```java
// Try-with-resources para cerrar automáticamente
try (ServerSocket serverSocket = new ServerSocket(PORT)) {
    // Código del servidor
}

// Cierre explícito en finally
finally {
    if (in != null) in.close();
    if (out != null) out.close();
    if (socket != null && !socket.isClosed()) socket.close();
}
```

### Formato de Mensajes

La aplicación utiliza varios formatos de mensaje:

- **Mensaje de Chat**: `[HH:mm:ss] <username> mensaje`
- **Mensaje del Sistema**: `[HH:mm:ss] [SISTEMA] mensaje`
- **Mensaje Enviado**: `[HH:mm:ss] [ENVIADO] mensaje`
- **Log del Servidor**: `[SERVER HH:mm:ss] mensaje`
- **Log del Cliente**: `[CLIENT HH:mm:ss] mensaje`

## Ejemplo de Sesión Completa

### Terminal del Servidor

```
[SERVER 14:30:15] Servidor Multicliente iniciado
[SERVER 14:30:15] Escuchando en puerto: 8080
[SERVER 14:30:15] Esperando conexiones...
----------------------------------------
[SERVER 14:30:20] Nueva conexión aceptada
[SERVER 14:30:20]   Dirección: 127.0.0.1:54321
[SERVER 14:30:20]   Cliente ID: 1
[SERVER 14:30:20] Clientes conectados: 1
----------------------------------------
[CLIENT-1 14:30:23] Usuario autenticado: Alice
[SERVER 14:30:30] Nueva conexión aceptada
[SERVER 14:30:30]   Dirección: 127.0.0.1:54322
[SERVER 14:30:30]   Cliente ID: 2
[SERVER 14:30:30] Clientes conectados: 2
----------------------------------------
[CLIENT-2 14:30:33] Usuario autenticado: Bob
[CLIENT-1 14:30:40] Mensaje recibido: Hola Bob!
[CLIENT-2 14:30:45] Mensaje recibido: Hola Alice!
[CLIENT-1 14:31:00] Desconectando cliente: Alice
[CLIENT-1 14:31:00] Cliente desconectado exitosamente
[SERVER 14:31:00] Cliente removido. Clientes activos: 1
```

### Terminal del Cliente 1 (Alice)

```
[CLIENT 14:30:20] Iniciando cliente de chat...
[CLIENT 14:30:20] Conectando a localhost:8080
[CLIENT 14:30:20] Conexion establecida correctamente
========================================
========================================
    SERVIDOR DE CHAT SEGURO v1.0
========================================
Conexion establecida correctamente
Servidor: localhost:8080
Timestamp: 14:30:20
----------------------------------------

Por favor, ingrese su nombre de usuario:
Alice

Bienvenido, Alice
Su ID de sesion es: 1
Puede comenzar a enviar mensajes.
Escriba 'exit' o 'salir' para desconectarse.
========================================

[14:30:33] [SISTEMA] Bob se ha conectado al servidor
Hola Bob!
[14:30:40] [ENVIADO] Hola Bob!
[14:30:45] <Bob> Hola Alice!
exit

========================================
       DESCONEXION DEL SERVIDOR
========================================
Gracias por usar el servicio
Sesion finalizada: 14:31:00
========================================
[CLIENT 14:31:00] Cliente finalizado
```

### Terminal del Cliente 2 (Bob)

```
[CLIENT 14:30:30] Iniciando cliente de chat...
[CLIENT 14:30:30] Conectando a localhost:8080
[CLIENT 14:30:30] Conexion establecida correctamente
========================================
========================================
    SERVIDOR DE CHAT SEGURO v1.0
========================================
Conexion establecida correctamente
Servidor: localhost:8080
Timestamp: 14:30:30
----------------------------------------

Por favor, ingrese su nombre de usuario:
Bob

Bienvenido, Bob
Su ID de sesion es: 2
Puede comenzar a enviar mensajes.
Escriba 'exit' o 'salir' para desconectarse.
========================================

[14:30:40] <Alice> Hola Bob!
Hola Alice!
[14:30:45] [ENVIADO] Hola Alice!
[14:31:00] [SISTEMA] Alice se ha desconectado
```

## Pruebas y Validación

### Casos de Prueba

#### 1. Conexión Básica
- ✅ El servidor acepta conexiones en el puerto 8080
- ✅ El cliente puede conectarse exitosamente
- ✅ Se muestra mensaje de bienvenida

#### 2. Múltiples Clientes
- ✅ Se pueden conectar varios clientes simultáneamente
- ✅ Cada cliente recibe un ID único
- ✅ Los mensajes se distribuyen correctamente

#### 3. Broadcasting
- ✅ Los mensajes de un cliente llegan a todos los demás
- ✅ El remitente no recibe su propio mensaje por broadcast
- ✅ El remitente recibe confirmación de envío

#### 4. Desconexión
- ✅ Comando 'exit' cierra la conexión correctamente
- ✅ Comando 'salir' cierra la conexión correctamente
- ✅ Desconexión abrupta se maneja sin errores
- ✅ Otros clientes son notificados de la desconexión

#### 5. Manejo de Errores
- ✅ Mensajes vacíos son ignorados
- ✅ Errores de I/O no causan crash del servidor
- ✅ Cliente desconectado es removido de la lista

### Comandos de Prueba

```bash
# Prueba con múltiples clientes (Linux/Mac)
# Terminal 1
java MultiThreadedServer &

# Terminal 2
java SimpleClient &

# Terminal 3
java SimpleClient &

# Terminal 4
java SimpleClient &
```

## Limitaciones Conocidas

1. **Sin Cifrado**: La comunicación no está cifrada (texto plano)
2. **Sin Autenticación**: No hay verificación de identidad de usuarios
3. **Sin Persistencia**: Los mensajes no se almacenan en base de datos
4. **Nombres Duplicados**: No hay validación de nombres de usuario únicos
5. **Sin Mensajes Privados**: Todos los mensajes son públicos (broadcast)
6. **Sin Historial**: Los nuevos clientes no ven mensajes anteriores

## Mejoras Futuras

### Seguridad
- [ ] Implementar SSL/TLS para cifrado de comunicaciones
- [ ] Agregar sistema de autenticación de usuarios
- [ ] Validar y sanitizar entrada de usuarios
- [ ] Implementar rate limiting para prevenir spam

### Funcionalidad
- [ ] Mensajes privados entre usuarios
- [ ] Salas de chat o canales
- [ ] Historial de mensajes
- [ ] Lista de usuarios conectados
- [ ] Comandos adicionales (/help, /users, /whisper, etc.)

### Persistencia
- [ ] Almacenar mensajes en base de datos
- [ ] Guardar perfiles de usuario
- [ ] Logs persistentes de actividad

### UI/UX
- [ ] Interfaz gráfica (GUI) con Swing o JavaFX
- [ ] Cliente web con WebSockets
- [ ] Notificaciones de escritorio
- [ ] Formateo de mensajes (negrita, cursiva, etc.)

### Escalabilidad
- [ ] Pool de hilos para limitar recursos
- [ ] Balanceo de carga para múltiples instancias
- [ ] Clustering para alta disponibilidad

## Conclusiones

Esta implementación demuestra:

1. **Programación Multihilo**: Uso efectivo de hilos para operaciones concurrentes
2. **Comunicación de Red**: Implementación de sockets TCP cliente-servidor
3. **Sincronización**: Uso de técnicas de sincronización para thread-safety
4. **Gestión de Recursos**: Cierre apropiado de streams y conexiones
5. **Manejo de Errores**: Gestión robusta de excepciones
6. **Broadcasting**: Distribución eficiente de mensajes a múltiples clientes

## Recursos Adicionales

### Documentación Java
- [Java Socket API](https://docs.oracle.com/javase/8/docs/api/java/net/Socket.html)
- [Java Thread API](https://docs.oracle.com/javase/8/docs/api/java/lang/Thread.html)
- [Java Collections](https://docs.oracle.com/javase/8/docs/api/java/util/Collections.html)

### Conceptos Relacionados
- TCP/IP Protocol
- Client-Server Architecture
- Multithreading and Concurrency
- Network Programming

## Autor

**Gregorio Ruiz**  
Solución para: Chat Seguro Multicliente - Issue #2  
Repositorio: i12vecaj/psp-25-26  
Fecha: Diciembre 2025

---

*Esta documentación forma parte de la solución completa del servidor de chat multicliente.*