# EcoDragon - Malas Prácticas

## Descripción

Sistema cliente-servidor TCP que implementa un **echo server con malas prácticas de seguridad y gestión de recursos**. El servidor recibe mensajes de los clientes y devuelve los mensajes en mayúsculas.

## Funcionamiento

### [`ServidorTCP.java`](./ServidorTCP.java)

- Escucha en el **puerto 5000**
- Acepta conexiones TCP de múltiples clientes simultáneamente
- Por cada conexión, crea un nuevo hilo para manejar al cliente
- Lee un mensaje del cliente y lo devuelve en mayúsculas
- Mantiene la conexión abierta hasta que el cliente desconecta

### [`ClienteTCP.java`](./ClienteTCP.java)

- Se conecta al servidor en **10.2.0.80:5000**
- Lee una frase desde el teclado (Scanner)
- Envía la frase al servidor usando `DataOutputStream.writeUTF()`
- Recibe la respuesta del servidor (eco en mayúsculas)
- Muestra el resultado y cierra la conexión

## Malas Prácticas Documentadas

### ⚠️ En [`ServidorTCP.java`](./ServidorTCP.java)

| Problema                        | Impacto                 | Detalles                                                                     |
| ------------------------------- | ----------------------- | ---------------------------------------------------------------------------- |
| **Sin límite de clientes**      | Agotamiento de recursos | No hay control sobre el número máximo de conexiones simultáneas              |
| **Sin timeout configurado**     | Bloqueos indefinidos    | Si un cliente se desconecta abruptamente, el servidor puede quedar esperando |
| **Sin validación de entrada**   | Inyección de datos      | Acepta cualquier mensaje sin validar tamaño o contenido                      |
| **Sin monitoreo de conexiones** | Pérdida de visibilidad  | No se registra información sobre clientes activos o estadísticas             |
| **Hilos sin control**           | Memory leak             | Cada cliente genera un hilo sin límite de pool de hilos                      |

### ⚠️ En [`ClienteTCP.java`](./ClienteTCP.java)

| Problema                              | Impacto                   | Detalles                                                          |
| ------------------------------------- | ------------------------- | ----------------------------------------------------------------- |
| **Scanner sin cerrar**                | Fuga de recursos          | El Scanner nunca se cierra, dejando recursos abiertos             |
| **No usa try-with-resources**         | Gestión manual deficiente | Cierre incorrecto del Socket si ocurren excepciones               |
| **Sin timeout en lectura**            | Bloqueo indefinido        | Si el servidor no responde, `readUTF()` puede esperar eternamente |
| **Sin validación de entrada**         | Desbordamiento potencial  | Acepta strings de cualquier tamaño sin límites                    |
| **Streams sin cerrar explícitamente** | Fugas de memoria          | DataInputStream y DataOutputStream nunca se cierran               |

## Comparación: Buenas vs Malas Prácticas

### Malas Prácticas (Carpeta [`EcoDragonMalasPracticas`](/EcoDragonMalasPracticas))

```
❌ Sin semáforo/límite de clientes
❌ Sin timeout
❌ Sin validación
❌ Recursos sin cerrar
❌ Gestión de excepciones incompleta
```

### Buenas Prácticas (Carpeta [`EcoDragon`](../EcoDragon))

```
✅ Semáforo limitando 40 clientes máximo
✅ Timeout de 30 segundos (AFK)
✅ Validación de límites
✅ Try-with-resources para limpieza automática
✅ Monitoreo de conexiones activas
```

## Riesgos de Seguridad

1. **Ataque de Denegación de Servicio (DoS)**: Conectar miles de clientes abruma el servidor
2. **Memory Leaks**: Recursos no liberados acumulan hasta causar OutOfMemory
3. **Bloqueos indefinidos**: Clientes AFK pueden dejar threads esperando para siempre
4. **Consumo descontrolado de puertos**: Sin límites, agota sockets del sistema operativo

## Ejecución

### Compilar

```bash
javac ServidorTCP.java ClienteTCP.java
```

### Ejecutar Servidor

```bash
java EcoDragonMalasPracticas.ServidorTCP
```

### Ejecutar Cliente (en otra terminal)

```bash
java EcoDragonMalasPracticas.ClienteTCP
```

## Propósito Educativo

⚠️ **Esta carpeta es solo para propósitos educativos**. Documenta **qué NO hacer** en aplicaciones de producción.

Para ver las mejores prácticas implementadas correctamente, revisar la carpeta [`EcoDragon`](../EcoDragon).
