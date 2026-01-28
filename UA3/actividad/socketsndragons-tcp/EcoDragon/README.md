# EcoDragon TCP

## Descripción

Servidor y cliente TCP que implementan un eco simple.
El cliente envía una frase, el servidor responde con la misma frase en mayúsculas.

## Componentes

- [`ServidorTCP.java`](./ServidorTCP.java).
  Servidor multihilo en el puerto 5000. Control de concurrencia con semáforo. Timeout para clientes inactivos.
- [`ClienteTCP.java`](./ClienteTCP.java).
  Cliente que lee una línea desde la entrada estándar, la envía al servidor y muestra la respuesta.

## Constantes relevantes

- Host por defecto en el cliente: `10.2.0.80`
- Puerto: `5000`
- `MAX_CLIENTES`: `40`
- `TIMEOUT_MS`: `30000` milisegundos

## Protocolo

1. Cliente abre conexión TCP con servidor en host:5000
2. Cliente envía una cadena con `DataOutputStream.writeUTF`
3. Servidor lee la cadena con `DataInputStream.readUTF`
4. Servidor convierte la cadena a mayúsculas y la devuelve con `DataOutputStream.writeUTF`
5. Cliente recibe la respuesta con `DataInputStream.readUTF` y la muestra

## Comportamiento del servidor

- Acepta conexiones hasta `MAX_CLIENTES` de forma concurrente
- Cada conexión se atiende en un hilo `ManejadorCliente`
- Semáforo evita exceder el número real de clientes activos
- `cliente.setSoTimeout(TIMEOUT_MS)` expulsa conexiones inactivas por timeout
- Streams y sockets se cierran con bloques try o en finally

## Requisitos

- Java 8 o superior
- Conectividad TCP entre cliente y servidor en el puerto configurado

## Compilar y ejecutar

Desde el directorio que contiene la carpeta [`EcoDragon`](/EcoDragon)

```bash
javac EcoDragon/*.java
java EcoDragon.ServidorTCP
```

En otra terminal o máquina

```bash
java EcoDragon.ClienteTCP
```

Modificar la variable `host` en [`ClienteTCP.java`](./ClienteTCP.java) para apuntar al servidor si es necesario

## Ejemplo de uso

Cliente introduce `hola mundo`
Salida del cliente

```
Grito enviado: hola mundo
Eco recibido: HOLA MUNDO
```

## Consideraciones operativas

- Ajustar `MAX_CLIENTES` según recursos del servidor
- Ajustar `TIMEOUT_MS` según latencia y comportamiento de clientes
- Abrir el puerto 5000 en firewalls si procede
- Registrar excepciones en ficheros para diagnóstico en producción
