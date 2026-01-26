# Tarea 3 – Programación de Sockets 3

## Objetivo
Implementar una comunicación cliente-servidor TCP donde:
- El cliente envía un mensaje de texto.
- El servidor devuelve el mensaje convertido a mayúsculas.

## Descripción de la solución

### Servidor
El servidor:
1. Espera la conexión de un cliente mediante `ServerSocket`.
2. Recibe una cadena de texto usando flujos de entrada.
3. Convierte el texto a mayúsculas.
4. Envía la respuesta al cliente.

### Cliente
El cliente:
1. Solicita un mensaje por consola.
2. Envía el mensaje al servidor.
3. Recibe la respuesta del servidor.
4. Muestra el mensaje recibido por pantalla.

## Conceptos utilizados
- Comunicación TCP
- Flujos de entrada y salida (`BufferedReader`, `PrintWriter`)
- Procesamiento de cadenas
- Cliente/servidor orientado a conexión

## Resultado
El cliente recibe correctamente el mensaje transformado por el servidor, demostrando un intercambio de información bidireccional.
