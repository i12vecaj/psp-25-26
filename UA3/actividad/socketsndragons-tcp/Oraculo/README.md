# Oráculo TCP

## Descripción

Servidor y cliente TCP que intercambian un número entero.
El cliente envía un entero. El servidor calcula el cuadrado y el cubo del número y devuelve una cadena con el resultado.

## Componentes

- [`ServidorTCP`](./ServidorTCP.java).
  Servidor multihilo que atiende peticiones TCP en el puerto 5000.
  Control de concurrencia mediante un semáforo. Tiempo de espera para clientes inactivos.
- [`ClienteTCP`](./ClienteTCP.java).
  Cliente simple que pide al usuario un número, lo envía al servidor y muestra la respuesta.

## Constantes relevantes

- Puerto: 5000
- Host por defecto en el cliente: 10.2.0.8
- MAX_CLIENTES: 40
- TIMEOUT_MS: 30000

## Protocolo

1. Cliente abre conexión TCP con servidor en host:5000.
2. Cliente envía un entero usando `DataOutputStream.writeInt`.
3. Servidor lee el entero con `DataInputStream.readInt`.
4. Servidor calcula `cuadrado = n * n` y `cubo = n * n * n`.
5. Servidor envía respuesta con `DataOutputStream.writeUTF` en formato:
   `Cuadrado: <cuadrado> | Cubo: <cubo>`.
6. Cliente recibe la cadena con `DataInputStream.readUTF` y la muestra.

## Comportamiento del servidor

- Acepta conexiones de clientes hasta alcanzar `MAX_CLIENTES`.
- Cada cliente se atiende en un hilo `ManejadorCliente`.
- El semáforo controla el número real de clientes activos. Cuando el semáforo está agotado, el servidor cierra la conexión entrante y registra el rechazo.
- Se aplica timeout de socket `TIMEOUT_MS` para expulsar clientes inactivos.
- Uso de `try` con recursos para streams y cierre seguro de sockets.
- Registros por consola con eventos clave: arranque, conexión, número recibido, respuesta enviada, desconexión, expulsión por timeout.

## Requisitos

- Java 8 o superior.
- Red que permita conexiones TCP entre cliente y servidor en el puerto configurado.

## Compilar y ejecutar

Desde el directorio que contiene la carpeta [`Oraculo`](/Oraculo):

```bash
javac Oraculo/*.java
java Oraculo.ServidorTCP
```

En otra terminal o máquina:

```bash
java Oraculo.ClienteTCP
```

Modificar `host` en `ClienteTCP` para apuntar al servidor si es necesario.

## Ejemplo de uso

Cliente introduce `3`. Resultado mostrado:

```
Respuesta del Oráculo: Cuadrado: 9 | Cubo: 27
```

## Consideraciones operativas

- Escalar `MAX_CLIENTES` según recursos de la máquina y pruebas de carga.
- Ajustar `TIMEOUT_MS` según latencia de la red y comportamiento de clientes.
- Registrar errores y excepciones en ficheros para diagnóstico en producción.
- Asegurar que el puerto 5000 esté abierto en firewalls relevantes.
