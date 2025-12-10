## 🧵 Sprint 2 – Servidor multicliente con hilos

### 🎯 Objetivo
Desarrollar un servidor TCP que gestione múltiples clientes simultáneamente mediante hilos.

### ✅ Tareas
- [ ] Crear clase `MultiThreadedServer` que acepte conexiones en un puerto.
- [ ] Cada cliente debe ser gestionado por un hilo diferente.
- [ ] Implementar mensajes tipo: "Bienvenido", "Hasta luego", etc.
- [ ] Permitir que los clientes se conecten desde terminal o clase Java.

### 📦 Entregables
- Código funcional en carpeta `/multihilo`.
- Captura de pruebas con 2 o más clientes conectados.
- Documentación breve en README.

### 📅 Fecha de entrega: 2025

//Documentacion Breve
Documentación – Servidor TCP Multicliente con Hilos
1.-Objetivo

Desarrollar un servidor TCP en Java capaz de atender varios clientes simultáneamente, asignando un hilo por cada cliente conectado.

2.-Componentes del sistema
    2.2-MultiThreadedServer

Función:

Abre un ServerSocket en un puerto fijo.

Escucha conexiones de clientes.

Por cada cliente crea un nuevo hilo (ClientHandler).

Responsabilidades:

Aceptar conexiones.

No bloquea a otros clientes gracias al uso de hilos.

    2.2-ClientHandler

Función:

Gestiona la comunicación con un único cliente.

Envía un mensaje de bienvenida.

Lee mensajes del cliente.

Finaliza la conexión cuando el cliente escribe exit.

Responsabilidades:

Lectura y escritura de datos (BufferedReader / PrintWriter).

Cierre correcto del socket.

3.- Flujo de funcionamiento

Se inicia el servidor.

Un cliente se conecta al puerto.

El servidor crea un hilo para ese cliente.

Cliente y servidor intercambian mensajes.

El cliente escribe exit.

El servidor responde con un mensaje de despedida y cierra la conexión.

4.- Formas de conexión

Desde terminal (telnet o nc)

Desde una clase Java cliente