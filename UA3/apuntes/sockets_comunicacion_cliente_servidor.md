<div align="center">

<a href="https://www.youtube.com/watch?v=uJHhCSNHIxQ">
    <img src="https://i.imgur.com/LbOJ5oQ.png" style="max-width: 400px; width: 100%;">
</a>

[Enlace al vídeo de Youtube](https://www.youtube.com/watch?v=Kf3V-Gh7Dks)

</div>

# Sockets: Comunicación Cliente Servidor

## 1. Base: El Lenguaje Secreto de Internet

- **Fundamento**: Detrás de cada clic, streaming o partida online, existe una conversación invisible y rapidísima entre aplicaciones.
- **Estructura**: La comunicación se organiza en capas (como una tarta), siendo la Capa de Transporte la protagonista crucial donde operan los protocolos.
- **Protagonistas**:
  - **TCP**: El mensajero meticuloso y ordenado.
  - **UDP**: El cohete rápido y directo.

## 2. La Puerta de Entrada: ¿Qué es un Socket?

- **Definición**: Es la puerta que permite a las aplicaciones conectarse a la red.
- **Analogía del Apartamento**:
  - La **Dirección IP** es la dirección del edificio.
  - El **Puerto** es el número del apartamento.
- _Con estos dos datos, se obtiene una dirección única y exacta para que los mensajes lleguen al destino correcto_.
- **Coreografía Cliente-Servidor**:
  - El servidor "escucha" en un puerto conocido.
  - El cliente "llama a la puerta" y proporciona su propio número de puerta (puerto del cliente) para establecer un canal único

## 3. TCP: La Apuesta por la Fiabilidad

- **Filosofía**: Es el "perfeccionista". Prioriza que todo llegue bien y en orden.
- **Funcionamiento**:
  - **Stream (Flujo de datos)**: Imagínalo como una tubería continua donde los bytes fluyen ordenadamente sin interrupciones.
  - **Protocolo**: Exige un ritual formal (apretón de manos), acuse de recibo y un cierre ordenado.
- Es como enviar un correo certificado: te aseguras de la entrega y, si algo se pierde, se vuelve a enviar.

## 4. UDP: Velocidad sin Burocracia

- **Filosofía**: Velocidad pura. Es un modelo de "enviar y olvidar".
- **Funcionamiento**:
  - **Datagramas**: Paquetes de datos independientes y autónomos que se lanzan a la red sin conexión previa.
  - No hay apretón de manos ni confirmación de llegada.
- _Es como gritar un mensaje en una habitación llena de gente: rápido y directo, aunque algunos no lo oigan o lo escuchen desordenado_.

## 5. Reflexión Final: ¿Cuál Elegir?

- La elección depende de las prioridades de lo que se quiere construir: **¿Fiabilidad o Velocidad?**
- **Usar TCP cuando**:
  - Necesitas garantizar que la información llegue intacta (ej. correos electrónicos, transferencia de archivos).
- **Usar UDP cuando**:
  - La velocidad es crítica y puedes permitirte perder algún dato (ej. videollamadas, juegos online).
- _En resumen: Si prefieres perder un fotograma antes que sufrir un parón (lag), eliges la velocidad de UDP; si necesitas el archivo perfecto, la fiabilidad de TCP_.
