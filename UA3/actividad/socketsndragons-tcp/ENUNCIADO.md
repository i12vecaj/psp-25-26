### ENUNCIADO DEL EJERCICIO SACADO DEL VIDEO

---

## 📜 Reglas del Gremio (Organización)

Antes de empezar, el equipo debe asignar estos roles (que rotarán en cada misión):

- **Arquitecto de Red:** Diseña el mapa de comunicación y flujo de datos.
- **Coder Principal:** Forja el código y pica la solución.
- **Cazador de Bugs:** Intenta romper el sistema para asegurar su resistencia.
- **Cronista:** Documenta la leyenda para la demo final.

---

## 🐉 Misión 1: La Guarida del Dragón (TCP)

**Objetivo:** Dominar la comunicación fiable y orientada a la conexión. El eco del dragón es perfecto; nada se pierde.

- **Descripción del Ejercicio:** \* El **Cliente** debe enviar un mensaje de texto (un "grito") al servidor.
- El **Servidor (Dragón)** recibe el mensaje, lo transforma íntegramente a **MAYÚSCULAS** y lo devuelve.
- El **Cliente** debe mostrar por pantalla el "eco" recibido.

- **Requisitos Técnicos:** Uso de `ServerSocket` y `Socket`.
- **Reto de Maestría (Bonus):**
- **Multicliente:** El servidor debe ser capaz de atender a varios aventureros de forma secuencial.
- **Gestión de Escudos:** Implementar control de excepciones para que, si un cliente se desconecta abruptamente, el servidor no se colapse.

---

## 🌊 Misión 2: El Mar de los Mensajes (UDP)

**Objetivo:** Sobrevivir a la incertidumbre. En el mar de UDP, los mensajes son botellas lanzadas a las olas; pueden llegar o perderse para siempre.

- **Descripción del Ejercicio:**
- El **Cliente** envía un datagrama al **Servidor (Faro)**.
- Debido a la falta de fiabilidad, el cliente debe implementar un **Reloj de Arena Mágico (Timeout)**.
- Si el servidor no responde en un tiempo determinado, el cliente debe dejar de esperar y notificar que el mensaje se ha perdido en la tormenta.

- **Requisitos Técnicos:** Uso de `DatagramSocket`, `DatagramPacket` y el método `.setSoTimeout()`.
- **Reto de Maestría (Bonus):**
- **Identificación:** El servidor debe mostrar la IP y el puerto de procedencia de cada "barco" que le envía un mensaje.
- **Notificación de Naufragio:** El sistema debe informar claramente al usuario si el paquete se ha perdido.

---

## 🔮 Misión 3: El Oráculo (Serialización de Objetos)

**Objetivo:** No solo enviar texto, sino transferir "conocimiento estructurado" (objetos) de forma segura.

- **Descripción del Ejercicio:**
- **Creación del Recipiente:** Definir una clase llamada `Numeros` que sea `Serializable`. Debe contener campos para un número, su cuadrado y su cubo.
- **La Consulta:** El **Cliente** envía un número simple (ej. **7**) al servidor.
- **La Transformación:** El **Servidor** recibe el número, calcula:
- Cuadrado:
- Cubo:

- **El Retorno:** El servidor no devuelve los números sueltos, sino que instancia un objeto `Numeros`, lo rellena y envía el **objeto completo** de vuelta al cliente.

- **Invocación de Gremlins (Robustez):** El Cazador de Bugs debe corromper los datos o forzar errores de conexión. El objetivo es que el servidor sea un **"Golem de Código"**: resiliente e inmortal ante los fallos.

---

## 🏆 La Gran Prueba Final: La Demo

Cada gremio tiene **3 minutos** para demostrar su valía ante el reino:

1. **Arquitectura:** Explicar cómo se conectan las piezas.
2. **Ejecución:** Mostrar el programa funcionando en vivo.
3. **Resiliencia:** Desatar un error a propósito (matar un proceso, enviar datos corruptos) y demostrar que el sistema **sobrevive** y se recupera.

---

