# Tarea 2 – Programación de Sockets 2

- El servidor acepte dos clientes.
- Se muestren los puertos locales y remotos de cada conexión.

## Descripción de la solución

### Servidor
El servidor utiliza la clase `ServerSocket` para escuchar conexiones en un puerto concreto.

Funcionamiento:
1. Se crea un `ServerSocket` en un puerto fijo.
2. El servidor espera conexiones mediante `accept()`.
3. Por cada cliente conectado, se muestran:
   - Puerto local del servidor
   - Puerto remoto del cliente
4. Se aceptan exactamente dos clientes y se cierra el servidor.

### Cliente
El cliente crea un `Socket` para conectarse al servidor.

Al conectarse:
- Muestra su puerto local.
- Muestra el puerto remoto (del servidor).
- Muestra la dirección IP del servidor.

## Conceptos utilizados
- Modelo cliente/servidor
- Sockets TCP
- Puertos locales y remotos

## Resultado
Se establece comunicación TCP entre cliente y servidor y se visualiza correctamente la información de red de cada conexión.
