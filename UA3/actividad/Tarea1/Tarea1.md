# Tarea 1 – Programación de Sockets 1

La aplicación utiliza la clase `InetAddress`, que forma parte de la librería estándar de Java y permite obtener información de red de un host.

El programa:
1. Lee una cadena desde la entrada estándar.
2. Comprueba si el valor introducido es `localhost`. En ese caso, finaliza la ejecución.
3. Obtiene un objeto `InetAddress` a partir de la URL o IP.
4. Muestra:
   - Nombre del host
   - Dirección IP
   - Si el host es alcanzable en un tiempo determinado

El proceso se repite hasta que el usuario decide salir.

## Conceptos utilizados
- Comunicación en red
- Manejo de excepciones

## Resultado
El programa permite comprobar información básica de red de cualquier URL o IP introducida por el usuario de forma continua.
