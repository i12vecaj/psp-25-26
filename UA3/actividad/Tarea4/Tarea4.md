# Tarea 4 – Programación de Sockets 4

## Objetivo
Desarrollar una aplicación cliente-servidor usando UDP donde:
- El cliente envía cadenas de texto.
- El servidor devuelve el texto en mayúsculas.
- La comunicación finaliza al introducir un asterisco (*).

## Solución

Funcionamiento:
1. Recibe un paquete UDP.
2. Extrae el texto del paquete.
3. Si el texto no es un asterisco, lo convierte a mayúsculas.
4. Envía la respuesta al cliente.
5. Finaliza cuando recibe un asterisco.

### Cliente UDP
El cliente:
1. Lee texto desde la entrada estándar.
2. Envía el texto al servidor mediante un `DatagramPacket`.
3. Espera la respuesta del servidor con un tiempo de espera configurado.
4. Informa si el paquete se pierde.
5. Finaliza al introducir un asterisco.

## Conceptos utilizados
- Comunicación sin conexión (UDP)
- Datagramas
- Control de tiempo de espera
- Pérdida de paquetes

## Resultado
La aplicación demuestra el funcionamiento de UDP y la diferencia con TCP, incluyendo el control de errores y la no garantía de entrega.
