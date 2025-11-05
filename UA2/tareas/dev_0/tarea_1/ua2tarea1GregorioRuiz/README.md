# Contador con hilos — Resumen y claves para examen (HECHO CON IA PARA QUE LUEGO SIRVA DE APOYO EN EL EXAMEN)

Contenido
- Breve explicación de los conceptos usados en los ejercicios.
- Instrucciones rápidas para compilar/ejecutar los programas entregados.
- Claves/resumen para un examen (puntos que debes saber y recordar).

---

## Conceptos básicos (resumen)
- Hilo (Thread): unidad de ejecución concurrente dentro de un proceso. En Java se crea extendiendo `Thread` o implementando `Runnable` y pasando la tarea a un `Thread`.
- Runnable vs Thread:
  - `implements Runnable`: separa tarea (trabajo) del hilo; permite reutilizar la tarea y la clase puede extender otra clase.
  - `extends Thread`: define comportamiento del hilo en la propia clase; menos flexible.
- Contador compartido: objeto con estado mutable accesible desde varios hilos. Si varios hilos modifican el mismo dato sin coordinación, aparecen problemas.
- Condición de carrera (race condition): cuando dos o más hilos leen/escriben datos compartidos sin sincronizar, la interleaving de operaciones puede provocar resultados incorrectos. Ejemplo típico: `c++` (lectura-modificación-escritura) no es atómico.
- Sincronización (`synchronized`): permite que solo un hilo a la vez ejecute una sección crítica protegida por un mismo monitor/objeto. Evita pérdidas de actualizaciones.
- AtomicInteger: clase de `java.util.concurrent.atomic` que ofrece operaciones atómicas (ej. `incrementAndGet()`) sin usar bloques `synchronized`.
- join(): espera a que un hilo termine. `InterruptedException` debe tratarse correctamente (restaurar el flag de interrupción si corresponde).
- UncaughtExceptionHandler: permite capturar excepciones no controladas que ocurren dentro de hilos.
- Diseño y rendimiento: sincronizar con un bloque muy amplio puede reducir paralelismo; sincronizar solo lo necesario (o usar atómicos) mejora rendimiento.

---

## Archivos incluidos
- `CounterFR1.java` — FR1: 5 hilos incrementan compartido 1000 veces SIN sincronización (muestra condición de carrera).
- `CounterFR2_Thread.java` — FR2.a: misma operación sincronizada usando clases que extienden `Thread`.
- `CounterFR2_Runnable.java` — FR2.b: misma operación sincronizada usando `Runnable`.

Cómo compilar y ejecutar (ejemplo):
- Compilar:
  - `javac CounterFR1.java`
  - `javac CounterFR2_Thread.java`
  - `javac CounterFR2_Runnable.java`
- Ejecutar:
  - `java CounterFR1`
  - `java CounterFR2_Thread`
  - `java CounterFR2_Runnable`

Salida esperada:
- `CounterFR1`: resultado final normalmente distinto de `5000` (variable entre ejecuciones).
- `CounterFR2_Thread` / `CounterFR2_Runnable`: resultado final debe ser `5000`.

---

## Comprobaciones y pruebas recomendadas
- Ejecutar `CounterFR1` varias veces; observar variabilidad.
- Ejecutar versiones sincronizadas para confirmar resultado estable `5000`.
- Probar aumentar el número de incrementos (por ejemplo 3000) para observar más claramente la pérdida en la versión no sincronizada.
- (Opcional) Reemplazar el contador por `AtomicInteger` y comparar rendimiento y código.

---

## Claves para el examen (qué memorizar / escribir en preguntas prácticas)
- Definición corta: "Condición de carrera = comportamiento inesperado por acceso concurrente no coordinado a memoria compartida".
- `counter++` no es atómico: descomponer en pasos (leer, sumar, escribir) facilita explicar la pérdida de incrementos.
- Formas de eliminar condiciones de carrera:
  - `synchronized` (bloques o métodos), bloqueo por el mismo objeto.
  - `java.util.concurrent.locks` (ej. `ReentrantLock`).
  - Clases atómicas (`AtomicInteger`, `AtomicLong`).
- Diferencia `Runnable` / `Thread` en una frase:
  - `Runnable`: define la tarea.
  - `Thread`: define el hilo que ejecuta la tarea.
- Uso de `join()` para esperar a hilos y `InterruptedException` para manejo correcto de interrupciones (restaurar estado con `Thread.currentThread().interrupt()`).
- Buenas prácticas:
  - Sincroniza la mínima sección crítica necesaria.
  - Evita sincronizar en tipos públicos o en `this` si la clase se expone.
  - Prefiere `Runnable` (y `ExecutorService`) para aplicaciones reales y mejor gestión de hilos.
- Pregunta típica de examen y respuesta corta:
  - P: "¿Por qué la versión sin sincronizar devuelve menos de 5000?"
  - R: "Porque varias hebras realizan `counter++` simultáneamente y se pierden actualizaciones por condición de carrera."

---

## Frases cortas para recordar (mnemotécnico)
- "Leer-Sumar-Escribir = pérdida si no bloqueo"  
- "synchronized = uno a la vez"  
- "Atomic = operación garantizada"  
- "Runnable = tarea; Thread = ejecutor"  
- "join() espera; InterruptedException: restaurar flag"

---
