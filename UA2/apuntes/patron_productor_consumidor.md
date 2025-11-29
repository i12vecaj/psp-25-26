<div align="center">

<a href="https://www.youtube.com/watch?v=Kf3V-Gh7Dks">
    <img src="https://i.imgur.com/9yGPkBo.png" style="max-width: 400px; width: 100%;">
</a>

[Enlace al vídeo de Youtube](https://www.youtube.com/watch?v=Kf3V-Gh7Dks)

</div>

# El Patrón Productor–Consumidor en Java

## 1. Base: La Concurrencia en Acción

- **Problema fundamental**: ¿qué ocurre cuando dos o más hilos quieren acceder al mismo recurso al mismo tiempo?
  El resultado sin control es caos, resultados impredecibles y errores difíciles de depurar.

- Protagonistas:

  - Hilos (Threads): pequeñas unidades de ejecución dentro de un programa.
  - Permiten realizar varias tareas a la vez, logrando concurrencia real.

- Cómo se crean en Java:
  - Extender la clase `Thread`.
  - O (la forma recomendada): implementar `Runnable`, más flexible y desacoplado.

Todo hilo tiene un método clave: `run()`, donde vive su comportamiento.

## 2. La Comunicación: Sincronización entre Hilos

- Condición de carrera (Race Condition):
  El desastre que ocurre cuando varios hilos leen o escriben datos compartidos sin orden. _Esto provoca datos corruptos y errores fantasma_.

Para restaurar el orden, Java proporciona un trío esencial:

- `synchronized`. Marca una sección crítica: solo un hilo puede entrar a la vez.

  _Es como ponerle llave a una habitación para que nadie más pase mientras tú trabajas._

- `wait()`. Un hilo entra, ve que no puede continuar, y se va a dormir voluntariamente.

  _Libera el candado (lock) para que otros hilos puedan avanzar._

- `notifyAll()`. Despierta a todos los hilos que estaban esperando en ese mismo monitor.

  Es **más seguro** que `notify()` porque evita que se despierte al hilo equivocado.

## 3. El Patrón Productor–Consumidor: El Problema Clásico

- Este patrón describe la **colaboración** entre dos tipos de hilos:

  - **Productor**: genera datos.
  - **Consumidor**: utiliza esos datos.
  - **Búfer o Monitor**: zona compartida que controla el acceso y mantiene el orden.

- **Analogía sencilla**: una cinta transportadora en una fábrica:

  - El productor coloca piezas.
  - El consumidor las recoge.
  - El sistema debe garantizar que el productor se detenga si la cinta está llena y que el consumidor espere si está vacía.

## 4. La Implementación: Cómo se Traducen las Ideas al Código

El monitor utiliza `synchronized`, `wait()` y `notifyAll()` para coordinar a los hilos:

- **Blindaje con `synchronized`**.

  Métodos como `producir()` o `consumir()` se marcan sincronizados para impedir accesos simultáneos.

- **Lógica del Consumidor**:

  - Si el búfer está vacío, entra en un bucle `while` y hace `wait()`.
  - El `while` es esencial: cuando se despierte debe recomprobar la condición.

- **Lógica del Productor**.

  Si el búfer está lleno, también espera usando `wait()`.

- **Comunicación**:

  Tras producir un dato o consumirlo, se hace `notifyAll()` para despertar a los hilos que estaban en pausa.

## 5. Reflexión Final: ¿Por Qué Es Tan Importante?

- Este patrón no es solo teoría: aparece en colas de trabajo, sistemas de mensajería, servidores web, apps móviles, bases de datos y casi cualquier software concurrente.

- Lo esencial que enseña es:

  - Los hilos necesitan orden.
  - La sincronización evita errores invisibles y peligrosos.
  - Las herramientas fundamentales son `synchronized`, `wait()` y `notifyAll()`.

- _Igual que en un supermercado o un restaurante, la coordinación evita caos, errores y tiempos muertos._
