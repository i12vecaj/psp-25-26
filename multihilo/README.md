# Sprint 2 – Servidor multicliente con hilos

## Objetivo
Desarrollar un servidor TCP que gestione múltiples clientes simultáneamente mediante hilos

## Código

### Clase `MultiThreadedServer`

- Funcion: abrir puerto y esperar conexion con clientes
- Métodos de la clase: 
  - `new ServerSocket(port)`: crea el servidor en el puerto indicado.
  - `accept()`: espera a que un cliente se conecte.

### Clase `ClientHandler`
- Funcion: gestionar la comunicación con un cliente específico
  - Métodos de la clase:
    - Método `run()`: Envía un mensaje de bienvenida al cliente, lee los mensajes que envía el cliente, si el cliente escribe cualquier otra cosa, responde con `"Eco: <mensaje>"`

### Ejemplo de consola:
Bienvenido al servidor multicliente 
Hola servidor
Eco: Hola servidor 
bye
Hasta luego

#### Cada cliente tiene su propio hilo, por lo que ambos pueden hablar al mismo tiempo sin interferirse
