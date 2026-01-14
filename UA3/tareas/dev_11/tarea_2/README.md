<div align="center">

# T2 – Tarea 2 – Programación de Sockets 2

</div>

#### _Criterios a), f), g), h)_

**SOCKETS Y REDES**. Programa Java que implementa un servidor TCP capaz de aceptar dos clientes y mostrar información de sus conexiones. El cliente TCP se conecta al servidor, muestra sus puertos y la dirección IP remota, y envía/recibe mensajes.

## Descripción del programa

### Servidor TCP `Archivo: ServidorTCP.java`

- El servidor escucha en el puerto 6000.
- Acepta exactamente dos clientes de manera secuencial.
- Para cada cliente conectado, muestra:
  - Puerto local (del servidor)
  - Puerto remoto (del cliente)
- Recibe un mensaje del cliente y responde confirmando la recepción.
- Cierra los flujos y sockets de cada cliente al finalizar la comunicación.

### Cliente TCP `Archivo: ClienteTCP.java`

- El cliente se conecta a `localhost` en el puerto 6000.
- Muestra información de su conexión:
  - Puerto local
  - Puerto remoto
  - IP remota (del servidor)
- Envía un mensaje al servidor y muestra el mensaje de respuesta recibido.
- Cierra los flujos y el socket al terminar.

## Ejecución y resultados

### Servidor TCP

- Al ejecutar `ServidorTCP`, se muestra en la terminal:

```bash
Servidor iniciado en el puerto 6000
Esperando al cliente 1...
```

- Cuando el **primer cliente** y el **segundo cliente** se conecta:

<div align="center"> <img src="https://i.imgur.com/DQ7EVcH.png" alt="Pantalla de salida del servidor" />
</div>

### Conclusión

El servidor recibe correctamente los dos clientes, muestra los puertos locales y remotos, y gestiona la comunicación con ambos.

### Cliente TCP

- Al ejecutar `ClienteTCP`, se muestra:

<div align="center"> <img src="https://i.imgur.com/LqpKKBx.png" alt="Pantalla de salida del cliente 1" />
</div>

- Para un **segundo cliente** (ejecutando otra instancia del cliente):

<div align="center"> <img src="https://i.imgur.com/0db9ahX.png" alt="Pantalla de salida del cliente 2" />
</div>

### Conclusión

El cliente muestra correctamente los puertos y la IP remota, envía y recibe mensajes, y se cierra de manera limpia tras la comunicación.

### Observaciones

- La comunicación se realiza de manera secuencial: el servidor atiende un cliente tras otro.
- Para un sistema con múltiples clientes simultáneos, sería recomendable usar hilos (multithreading).
- Se implementa control básico de errores al cerrar correctamente los flujos y sockets.
