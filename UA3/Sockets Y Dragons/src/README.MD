# Cliente – Servidor UDP (Simulación de pérdida de paquetes)
![Presentacion](images/presentacion.png)
Este proyecto implementa una comunicación **Cliente–Servidor usando UDP en Java**, incorporando una simulación de pérdida de paquetes para entender el comportamiento real de este protocolo.

La práctica permite observar cómo **UDP no garantiza la entrega de mensajes**, a diferencia de TCP.

---

## Objetivo del proyecto

- Comprender el funcionamiento del protocolo UDP
- Practicar el uso de `DatagramSocket` y `DatagramPacket`
- Simular un entorno real de red con pérdida de paquetes
- Diferenciar claramente UDP frente a TCP
- Trabajar comunicación cliente–servidor sin conexión persistente

---

## Conceptos trabajados

- Protocolo UDP
- Cliente–Servidor
- Envío y recepción de datagramas
- Conversión de bytes a texto
- Uso de `Math.random()` para simulaciones
- Control de flujo con bucles
- Comunicación sin conexión (connectionless)

---

## Estructura del proyecto

📁 src
├── ServidorUDP.java
└── ClienteUDP.java

- **ServidorUDP**  
  Recibe mensajes, simula pérdida de paquetes y responde en mayúsculas.

- **ClienteUDP**  
  Envía mensajes al servidor y espera la respuesta.

---

## Cómo ejecutar el proyecto

> ###  Ejecutar Primero el servidor
1. Abrir el proyecto en el IDE
2. Ejecutar la clase `ServidorUDP`
3. El servidor queda a la espera de mensajes

> ### Después Ejecutar el cliente
1. Ejecutar la clase `ClienteUDP`
2. Introducir mensajes por consola
3. Observar si llega o no respuesta del servidor

> ⚠️ Algunas veces el cliente **no recibirá respuesta**, ya que el servidor simula pérdida de paquetes.

---

## Funcionamiento de la simulación de caos

El servidor incluye una zona de simulación donde:

- Existe un **30% de probabilidad** de que el servidor no responda
- El mensaje se recibe, pero se descarta intencionadamente
- Se imita el comportamiento real de redes UDP

Esto demuestra que:
- UDP **no garantiza entrega**
- UDP **no reintenta**
- UDP **no mantiene conexión**

---

##  Esquema de comunicación




![Flujo de de trabajo](images/flujo1.png)

## Caso de Uso
![Caso de Uso](images/caso1.png)

---

##  Tecnologías utilizadas

- Java
- UDP (`DatagramSocket`, `DatagramPacket`)
- IDE: IntelliJ IDEA / Eclipse
- JDK 17 (o compatible)

---

## Contexto académico

Práctica desarrollada dentro del **Grado Superior en Desarrollo de Aplicaciones Multiplataforma (DAM)** para la asignatura de **Programación / Servicios en Red**, con fines formativos.

---

## Autores

- **Daniel Alcaparra**

- **Alvaro Caler**

- **Bernardo Cubero** 

- **German Gutierrez**

- **Pablo Herrador**
- **Rafael Moreno**

Estudiantes de Desarrollo de Aplicaciones Multiplataforma (DAM)

---


