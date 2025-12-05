## 🧵 Sprint 2 – Servidor multicliente con hilos

### 🎯 Objetivo

Desarrollar un servidor TCP que gestione múltiples clientes simultáneamente mediante hilos.

### ✅ Tareas

- ✅ Crear clase `MultiThreadedServer` que acepte conexiones en un puerto.
- ✅ Cada cliente debe ser gestionado por un hilo diferente.
- ✅ Implementar mensajes tipo: "Bienvenido", "Hasta luego", etc.
- ✅ Permitir que los clientes se conecten desde terminal o clase Java.

### 📦 Entregables

- Código funcional en carpeta `/multihilo`.
- Captura de pruebas con 2 o más clientes conectados.
- Documentación breve en README.

### 📅 Fecha de entrega: 2025

# Resolución del Sprint 2

<div align="center">
<img src="https://i.imgur.com/pZ55hLz.png" alt="Servidor activo y capaz de detectar varios clientes(conexiones)"  />

_Consola: servidor detectando 2 clientes_

</div>

## Tareas

### ✅ Crear clase `MultiThreadedServer` que acepte conexiones en un puerto

**Evidencia.** Estructura de carpetas donde se encuentran los archivos `MultiThreadedServer.java`, `ClientHandler.java` y `SimpleClient.java` dentro de `/multihilo`:

<div align="center">
<img src="https://i.imgur.com/e6jqgtV.png" alt="Estructura de carpetas"  />

</div>

> [!NOTE]
>
> Se crea la carpeta `bin` para almacenar los archivos compilados `.class` y mantener separados los fuentes `.java`.
>
> Esta carpeta no se subirá al repositorio, ya que GitHub es para **código fuente** y no para binarios.

### ✅ Cada cliente debe ser gestionado por un hilo diferente

El servidor crea un nuevo hilo por cada conexión entrante mediante la clase `ClientHandler`. Esto significa que cada cliente puede comunicarse con el servidor de forma independiente, sin bloquear a los demás.

**Evidencia.** Ejecución del servidor mostrando que detecta múltiples conexiones:

<div align="center">
<img src="https://i.imgur.com/pZ55hLz.png" alt="Cada conexión se gestiona con un hilo diferente"  />

</div>

### ✅ Implementar mensajes tipo “Bienvenido”, “Hasta luego”, etc.

Los clientes reciben:

- `"Bienvenido al servidor multihilo"`
- Mensajes interactivos
- `"Hasta luego"` al cerrar la sesión escribiendo `bye`

**Evidencia.** Interacción real entre el cliente y el servidor:

<div align="center">
<img src="https://i.imgur.com/iXv0IGW.png" alt="Muestra en consola 'Bienvenido', interacción y 'Hasta luego'"  />

_Consola: Bienvenida a los 2 clientes, interacción y hasta luego_

</div>

### ✅ Permitir que los clientes se conecten desde terminal o clase Java.

Probé ambos métodos:

- Conexión mediante `SimpleClient.java`
- Conexión desde terminal con `telnet` o `nc`

## Explicación técnica

Este proyecto implementa un servidor que usa hilos para atender múltiples clientes de forma simultánea.

- Cada conexión se gestiona en paralelo gracias a `Thread`.
- La comunicación cliente-servidor se basa en flujos de entrada/salida (`BufferedReader` y `PrintWriter`).
- Cada cliente mantiene su propia sesión, independiente del resto.
- El servidor nunca se bloquea por un cliente porque cada uno tiene su propio hilo.

Este proyecto implementa un **servidor TCP multicliente** usando **hilos**. El servidor escucha en el **puerto 5000** y, por cada conexión entrante, crea un nuevo hilo mediante `ClientHandler`, siguiendo el modelo **Thread-per-Client**. Esto permite que cada cliente mantenga una sesión independiente sin bloquear al resto.

`ClientHandler` implementa `Runnable`, y cada hilo gestiona su propio `Socket`, `BufferedReader` y `PrintWriter`. Al no compartir recursos entre hilos, no se requiere sincronización adicional. El ciclo de vida del hilo sigue los estados estándar: creación (`NEW`), ejecución (`RUNNABLE`) y finalización cuando el cliente envía `bye` (`DEAD`).

El protocolo es simple: envío de un mensaje de bienvenida, interacción y mensaje de cierre. El cliente puede conectarse mediante la clase `SimpleClient` o usando herramientas como `telnet` o `nc`.

Esta arquitectura permite **concurrencia** real, evita bloqueos globales y cumple las bases de programación multihilo.

**_🛠 Requisitos:_**

- Java instalado
- Compilar todos los .java antes de ejecutar:

```sh
javac *.java
```

- Ejecutar el servidor:

```sh
java MultiThreadedServer
```

- Ejecutar un cliente:

```sh
java SimpleClient
```

- O conectarse desde terminal:

```sh
telnet localhost 5000
```

o

```sh
nc localhost 5000
```
