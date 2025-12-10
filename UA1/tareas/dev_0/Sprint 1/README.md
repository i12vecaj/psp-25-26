# Sprint 2 – Servidor multicliente con hilos

## Objetivo
Desarrollar un servidor TCP que gestione múltiples clientes simultáneamente mediante hilos.

## Funcionalidad
El servidor acepta conexiones en un puerto y crea un hilo independiente para cada cliente conectado.  
Cada cliente recibe un mensaje de bienvenida y puede enviar mensajes al servidor.  
Si el cliente envía "bye" o "adios", el servidor responde con "Hasta luego" y cierra la conexión.

## Características implementadas
- Servidor multicliente usando hilos.
- Gestión individualizada de cada cliente.
- Mensajes básicos de bienvenida y despedida.
- Compatibilidad con clientes desde terminal o desde una clase Java.

## Entregables
- Código funcional en la carpeta `/multihilo`.
- Capturas de dos o más clientes conectados simultáneamente.
- README explicativo del funcionamiento.

## Fecha de entrega
2025