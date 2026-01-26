# Sprint 3 – Chat básico por sockets

## Objetivo
Implementar un sistema de chat en Java utilizando sockets TCP que permita la comunicación entre un servidor y múltiples clientes simultáneos.

## Arquitectura
La aplicación sigue un modelo cliente/servidor:
- El servidor acepta múltiples clientes usando hilos.
- Cada cliente se gestiona de forma independiente.
- Los mensajes se distribuyen mediante difusión (broadcast).

## Funcionalidades implementadas
- Envío y recepción de mensajes entre clientes.
- Comando /exit para salir del chat.
- Comando /list para listar usuarios conectados.
- Comando /name para cambiar el nombre de usuario.
- Control de nombres únicos.
- Registro de actividad en archivo chatlog.txt.

## Tecnologías usadas
- Java SE
- Sockets TCP
- Hilos
- Flujos de entrada y salida
- Serialización de texto
- Escritura en archivos

## Ejecución
1. Ejecutar ServidorChat
2. Ejecutar uno o más ClienteChat
3. Usar los comandos disponibles desde el cliente

## Resultado
El sistema permite una comunicación concurrente y estable entre múltiples clientes, cumpliendo los requisitos del sprint.
