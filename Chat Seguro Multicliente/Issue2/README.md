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

2. **Clientes** (dos o más terminales distintas):

```sh
java -cp bin ClienteTerminal
```

### Flujo de prueba manual (2+ clientes)

- Al conectar cada cliente se muestra: `Bienvenido al servidor multihilo!`.
- Cada línea enviada se devuelve con: `Recibido: <mensaje>`.
- Al escribir `adios` el servidor responde `Hasta luego!` y cierra ese cliente sin afectar a los demás.
