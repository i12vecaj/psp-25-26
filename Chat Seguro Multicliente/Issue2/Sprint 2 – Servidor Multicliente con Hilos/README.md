## Sprint 2 – Servidor multicliente con hilos

Servidor TCP sencillo que acepta múltiples clientes de manera concurrente mediante hilos. Incluye un cliente de terminal para probar la conexión.

### Estructura

- `src/`: código fuente (`MultiThreadedServer`, `ClientHandler`, `ClienteTerminal`).
- `bin/`: clases compiladas.

### Cómo compilar

```sh
javac -d bin src/*.java
```

### Cómo ejecutar

1. **Servidor** (una terminal):

```sh
java -cp bin MultiThreadedServer
```

<img width="814" height="238" alt="Captura de pantalla 2025-12-09 a las 21 41 53" src="https://github.com/user-attachments/assets/4b8ecd47-4710-4ae1-a1ef-a9350b9dcd28" />


2. **Clientes** (dos o más terminales distintas):

```sh
java -cp bin ClienteTerminal
```

<img width="795" height="239" alt="Captura de pantalla 2025-12-09 a las 21 41 59" src="https://github.com/user-attachments/assets/23daadb5-9e4a-4f15-9ea8-3accb09e3fbb" />

### Flujo de prueba manual (2+ clientes)

- Al conectar cada cliente se muestra: `Bienvenido al servidor multihilo!`.
- Cada línea enviada se devuelve con: `Recibido: <mensaje>`.
- Al escribir `adios` el servidor responde `Hasta luego!` y cierra ese cliente sin afectar a los demás.

