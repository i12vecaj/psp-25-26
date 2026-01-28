# Sockets & Dragons

Repositorio de prácticas de **PSP (Programación de Servicios y Procesos)**.

## Integrantes del grupo

- Pablo: Coder Principal, Arquitecto/a de Red, Cazador de Bugs.
- Miguel Ángel Varo: Coder Principal, Arquitecto/a de Red.
- Manuel Cañas: Cronista, Coder Principal.

---

## Estructura del repositorio

- [`Socket_Dragons_TCP_VPM/`](./Socket_Dragons_TCP_VPM) → Mensajes UTF (`DataInputStream/DataOutputStream`)
- [`Socket_Dragons_Oraculo_VPM/`](./Socket_Dragons_Oraculo_VPM) → Objetos (`ObjectInputStream/ObjectOutputStream`)

---

## Contexto

En esta entrega trabajamos con **sockets TCP en Java**.

Hay dos mini-proyectos independientes:

1) **Socket_Dragons_TCP_VPM** → comunicación por `DataInputStream/DataOutputStream` usando `readUTF()` / `writeUTF()`.
2) **Socket_Dragons_Oraculo_VPM** → comunicación por `ObjectInputStream/ObjectOutputStream` enviando un objeto `Numeros` (Serializable).

Cada proyecto incluye:
- **Servidor**
- **Clientes** (para probar cola/turno y respuestas)

---

## 1) Socket_Dragons_TCP_VPM

Carpeta: [`Socket_Dragons_TCP_VPM/`](./Socket_Dragons_TCP_VPM)

### Objetivo

- Practicar una comunicación TCP sencilla.
- El servidor recibe un mensaje (UTF) y lo devuelve en **MAYÚSCULAS**.
- El servidor controla:
  - **Turnos** (solo atiende a 1 cliente a la vez)
  - **Cola con timeout**
  - **Timeout por inactividad (AFK)**
  - Validación básica para no aceptar mensajes vacíos o demasiado largos

### Estructura (carpeta `Socket_Dragons_TCP_VPM/src`)

- `SocketServer.java` → servidor con cola/turnos.
- `SocketCliente.java` → cliente por consola.
- `SocketCliente2.java` → segundo cliente.
- `SocketClienteDemoQA.java` → demo para ver cola/turnos.
- `SocketClienteQA_CargaControlada.java` → demo "QA" con comportamientos aleatorios (desconexiones, vacío, lento).

### Evidencias

**Servidor TCP (eco a mayúsculas):**

![Servidor TCP Eco](./imagenes/SocketServerPruebaTCPEco.png)

**Cliente TCP (eco a mayúsculas):**

![Cliente TCP Eco](./imagenes/SocketClientePruebaTCPEco.png)

### Clientes de debug

En este proyecto hay clientes "extra" pensados para **demostrar** y **comprobar** que la cola y los timeouts funcionan.

- `SocketClienteDemoQA.java`
  - Qué hace:
    - Lanza varios clientes casi a la vez.
    - Muestra por consola los mensajes del servidor (cola → turno).
    - Si `Afk=true`, el último cliente no envía y se ve la expulsión por inactividad.

- `SocketClienteQA_CargaControlada.java`
  - Qué hace (elige aleatoriamente por cliente):
    - Conectar y desconectar inmediatamente.
    - Desconectar mientras está en cola.
    - Enviar mensaje vacío (el servidor lo marca como inválido).
    - Enviar mensaje normal y comprobar el eco a mayúsculas.
    - Esperar demasiado antes de enviar para provocar expulsión por inactividad (AFK) en el servidor.

---

## 2) Socket_Dragons_Oraculo_VPM

Carpeta: [`Socket_Dragons_Oraculo_VPM/`](./Socket_Dragons_Oraculo_VPM)

### Objetivo

- Practicar envío de objetos por sockets usando **serialización**.
- El cliente envía un objeto `Numeros` con un entero.
- El servidor calcula:
  - `cuadrado = n * n`
  - `cubo = n * n * n`
- Devuelve el mismo objeto con los resultados.
- El servidor usa **turnos** y **timeouts** para no quedarse bloqueado.

### Estructura (carpeta `Socket_Dragons_Oraculo_VPM/src`)

- `Numeros.java` → clase `Serializable` que viaja por la red.
- `SocketServer.java` → servidor "Oráculo".
- `SocketCliente.java` → cliente 1 por consola.
- `SocketCliente2.java` → cliente 2 por consola.
- `SocketClienteDemoQA_Oraculo.java` → demo de cola/turnos + opción de simular AFK.
- `SocketClienteQA_CargaControlada_Oraculo.java` → demo "QA" con comportamientos aleatorios (desconexiones, inválidos, lento).

### Evidencias (capturas)

**Servidor TCP (Oráculo con objetos):**

![Servidor TCP Oráculo](./imagenes/SocketServerPruebaTCPOraculo.png)

**Cliente TCP (Oráculo con objetos):**

![Cliente TCP Oráculo](./imagenes/SocketClientePruebaTCPOraculo.png)

### Clientes de debug

- `SocketClienteDemoQA_Oraculo.java`
  - Qué hace:
    - Lanza varios clientes casi a la vez.
    - Muestra mensajes del servidor (cola → turno).
    - Si `demoAfk=true`, el último cliente no envía el objeto tras "Tu turno" y se ve la expulsión por inactividad.

- `SocketClienteQA_CargaControlada_Oraculo.java`
  - Qué hace (elige aleatoriamente por cliente):
    - Conectar y desconectar inmediatamente.
    - Desconectar mientras está en cola.
    - Enviar un `Numeros` válido y validar cuadrado/cubo.
    - Enviar un objeto inválido (por ejemplo `String`) y ver la respuesta del servidor.
    - Esperar demasiado antes de enviar para provocar expulsión por inactividad (AFK) en el servidor.

---

## Arquitectura Cliente–Servidor (TCP) en este proyecto

En ambos ejercicios usamos el modelo **cliente–servidor**:

- El **servidor** se queda escuchando en un **puerto fijo** (`6000`), acepta conexiones y atiende a los clientes.
- El **cliente** inicia la conexión contra `localhost:6000`, espera el mensaje de **turno** y realiza su petición.

En nuestro contexto, además de la comunicación, el servidor implementa **control de concurrencia**:
- Atiende **solo 1 cliente a la vez** (semáforo/turno).
- El resto queda en **cola** con un **timeout**.
- Si un cliente no envía datos cuando le toca, se expulsa por **AFK / timeout**.

### Flujo básico:

1. El **cliente** inicia la conexión TCP (`Socket`) contra `localhost:6000`.
2. El **servidor** acepta (`ServerSocket.accept()`) y crea los *streams*:
   - TCP Eco: `DataInputStream/DataOutputStream`
   - Oráculo: `ObjectInputStream/ObjectOutputStream`
3. El **servidor** envía mensajes de estado (cola/turno) y el cliente espera a "Tu turno".
4. El **cliente** envía datos:
   - Eco: un `String` con `writeUTF()`
   - Oráculo: un objeto `Numeros` con `writeObject()`
5. El **servidor** procesa:
   - Eco: `toUpperCase()`
   - Oráculo: calcula cuadrado y cubo
6. El **servidor** devuelve la respuesta y ambos lados cierran la conexión.

### ¿Por qué TCP y no UDP aquí?

- Con **TCP** tenemos conexión, orden y fiabilidad: lo que el cliente envía llega (o falla de forma controlada) y llega **en orden**. Esto encaja con:
  - envío/recepción de `readUTF()/writeUTF()` (eco)
  - envío/recepción de objetos `Serializable` (Oráculo)
  - una cola/turno con estados claros

- Con **UDP** podríamos perder datagramas o recibirlos desordenados; para este trabajo eso complicaría la práctica porque habría que implementar a mano:
  - reintentos
  - confirmaciones (ACK)
  - control de orden

---

## Presentación

- Enlace: [Canva - Presentación](https://www.canva.com/design/DAG_sna3qXw/V04MH15krLcZZmCHuYdTdg/view?utm_content=DAG_sna3qXw&utm_campaign=designshare&utm_medium=link2&utm_source=uniquelinks&utlId=haf23db70e9)

##
Arquitectura Cliente-Servidor TCP
La arquitectura sigue un modelo cliente–servidor clásico:

El cliente inicia la conexión TCP.
El servidor acepta la conexión y crea los streams.
El cliente envía datos.
El servidor procesa la información.
El servidor devuelve la respuesta.
Ambos extremos cierran la conexión de forma ordenada.
El uso de TCP garantiza fiabilidad, orden y entrega completa de los mensajes.


Figura 1. Diagrama de secuencia de la arquitectura Cliente–Servidor TCP (Eco del Dragón). El cliente establece una conexión TCP, envía un mensaje de texto al servidor, el servidor lo transforma a mayúsculas y devuelve el eco antes de cerrar la conexión de forma ordenada.

Descripción de la arquitectura
La arquitectura implementada sigue un modelo cliente-servidor clásico basado en TCP, donde el servidor actúa como un punto central de escucha y el cliente como iniciador de la comunicación. El servidor utiliza un ServerSocket para permanecer a la espera de conexiones entrantes, mientras que el cliente establece la conexión mediante un Socket.

Una vez aceptada la conexión (accept()), se crea un canal de comunicación bidireccional fiable. El cliente envía un mensaje de texto utilizando un stream de datos (writeUTF), que el servidor recibe de forma íntegra y sin pérdidas gracias a las garantías del protocolo TCP.

Ventajas de la arquitectura
Esta arquitectura es adecuada y sólida por varios motivos:

Fiabilidad: Al usar TCP, se asegura que el mensaje llega completo, en orden y sin duplicados, lo cual es esencial para este tipo de comunicación.
Separación de responsabilidades:
El cliente se limita a enviar datos y mostrar resultados.
El servidor se encarga exclusivamente del procesamiento y la respuesta.
Escalabilidad básica: El uso de un bucle de aceptación de clientes permite atender a múltiples conexiones de forma secuencial, sentando las bases para una futura evolución a multihilo.
Robustez: La inclusión de control de excepciones evita que el servidor se detenga ante errores de conexión o desconexiones inesperadas de los clientes.

Figura 2. Arquitectura funcional del sistema Cliente–Servidor TCP. Se representa el flujo completo de ejecución desde la introducción del mensaje por el usuario hasta la recepción del eco en mayúsculas, incluyendo los métodos y clases utilizados en cada extremo.

Descripción de la arquitectura funcional
Este diagrama muestra una visión más detallada y cercana a la implementación real en Java, complementando el diagrama de secuencia anterior. En él se identifican claramente las clases principales (ClienteTCP y ServidorTCP), los métodos utilizados y el flujo de datos a través de la red.

El cliente (Aventurero) inicia la comunicación solicitando una frase al usuario y estableciendo una conexión TCP con el servidor mediante un Socket, indicando explícitamente el host y el puerto 5000. El servidor (Dragón) permanece escuchando de forma permanente en ese puerto mediante un ServerSocket.

Flujo detallado de ejecución
La arquitectura sigue un flujo ordenado en tres fases principales:

Conexión
El cliente crea un socket hacia el servidor (host + puerto).
El servidor acepta la conexión mediante accept() y obtiene los streams de entrada y salida.
Intercambio de datos
El cliente envía el mensaje original usando writeUTF().
El servidor recibe el texto con readUTF(), lo procesa y lo transforma a mayúsculas mediante toUpperCase().
El servidor envía el eco resultante al cliente.
Finalización
El cliente recibe el eco con readUTF() y lo muestra por pantalla.
Ambos extremos cierran correctamente los streams y el socket, liberando recursos.
Este flujo garantiza una comunicación clara, predecible y segura.

Relación con el código implementado
Esta arquitectura refleja fielmente el comportamiento del código desarrollado:

Los métodos readUTF() y writeUTF() aseguran una comunicación consistente de cadenas.
El uso de TCP garantiza que el mensaje no se pierde ni llega corrupto.
El cierre explícito de conexiones evita fugas de recursos.
La separación visual entre cliente, red y servidor facilita la comprensión del sistema.
Gracias a esta arquitectura, el proyecto resulta fácil de entender, depurar y ampliar, por ejemplo, añadiendo nuevas funcionalidades o evolucionando hacia un modelo más complejo.