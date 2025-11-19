# Tarea — Conceptos clave sobre concurrencia y medición de tiempos en Java

Contenido
- AppConcurrente.java
- (Opcional) Variantes con ExecutorService y medición por hilo

Descripción breve
Este documento recoge y explica de forma concisa los conceptos principales trabajados en los ejercicios de concurrencia: creación de hilos (Runnable/Thread), diferencias entre ejecución secuencial y concurrente, condiciones de carrera y sincronización, y cómo medir tiempos de respuesta para comparar comportamientos. Incluye recomendaciones prácticas para medir correctamente y preguntas tipo examen.

Cómo compilar y ejecutar
- Compilar:
  javac AppConcurrente.java
- Ejecutar:
  java AppConcurrente

(Para el caso de paquetes, ajustar -cp y el nombre del paquete según el layout del proyecto.)

Medición de tiempos (snippet usado en los ejercicios)
- Ejemplo básico:
  long t_comienzo = System.currentTimeMillis();
  // ejecutar tarea (secuencial: .run() o concurrente: start() + join())
  long t_fin = System.currentTimeMillis();
  long t_total = t_fin - t_comienzo;
- Recomendación: para mayor precisión usa System.nanoTime() y repite la medición varias veces (media/mediana).

Conceptos clave (resumen para estudio)

1. Hilos (Thread) y Runnable
- Un hilo (Thread) es una unidad de ejecución dentro de un proceso.
- En Java se puede crear un hilo implementando Runnable (preferible por composición) o extendiendo Thread.
- start() crea/lanza el hilo y ejecuta run() en paralelo; llamar run() directamente ejecuta la tarea en el hilo actual (secuencial).

2. Secuencial vs Concurrente (diferencia esencial)
- Secuencial: las tareas se ejecutan una detrás de otra. El tiempo total tiende a ser la suma de los tiempos individuales.
- Concurrente: las tareas pueden solaparse en el tiempo. Si hay esperas (p. ej. E/S), la concurrencia permite aprovechar esos intervalos y reducir el tiempo total. Para tareas CPU-bound, la ganancia depende del número de núcleos y del overhead de los hilos.

3. Condición de carrera (race condition)
- Ocurre cuando varios hilos acceden y modifican un mismo recurso sin sincronización.
- Resultado: comportamientos no deterministas y pérdida de actualizaciones (p. ej. dos hilos leen el mismo saldo y escriben una actualización basada en ese valor antiguo).

4. synchronized y exclusión mutua
- synchronized (método o bloque) asegura que solo un hilo a la vez ejecute esa sección para el mismo objeto/lock.
- Evita condiciones de carrera en operaciones compuestas (leer-modificar-escribir).
- Además de exclusión, synchronized establece reglas de visibilidad (happens-before).

5. Atomicidad y visibilidad
- Atomicidad: operación completa sin interferencia. Operaciones compuestas (read-modify-write) no son atómicas por defecto.
- Visibilidad: sin sincronización, los cambios de un hilo pueden no ser inmediatamente visibles a otros; synchronized/volatile garantizan visibilidad.

6. Thread.sleep()
- Suspende el hilo actual durante el tiempo indicado.
- No libera locks si el hilo duerme dentro de un bloque synchronized.
- Puede lanzar InterruptedException.

7. InterruptedException y manejo de interrupciones
- Debe capturarse con atención: si se decide no finalizar, restaurar la interrupción con Thread.currentThread().interrupt().
- No silenciar interrupciones sin motivo; diseñar limpieza si se va a terminar la tarea.

8. join()
- join() hace que el hilo invocador espere a que otro hilo termine.
- Útil en main para esperar a que todas las tareas concurrentes finalicen antes de recopilar resultados o medir tiempos.

9. Medición de tiempos y buenas prácticas
- Usa System.nanoTime() para mediciones precisas; System.currentTimeMillis() es suficiente para estimaciones groseras.
- Repetir las pruebas varias veces y usar media/mediana reduce ruido.
- Suprime o reduce la salida por consola al medir (println es lento y puede serializar la ejecución).
- Medir tanto el tiempo total (desde antes de start() hasta después de join()) como el tiempo por hilo (dentro de run()) ayuda a entender comportamiento.
- Considerar overhead: creación de hilos, cambios de contexto, sincronización y operaciones de E/S pueden consumir tiempo y enmascarar beneficios de la concurrencia.

10. I/O y concurrencia
- Para tareas E/S-bound (lectura de ficheros, operaciones de red) la concurrencia suele reducir el tiempo total al solapar esperas.
- Para acceso a recursos físicos compartidos (disco HDD) la contención puede limitar o anular beneficios; en SSD o con caché, la concurrencia suele funcionar mejor.

11. Synchronized(System.out) y serialización implícita
- La salida por consola es un recurso compartido; si la protegemos con synchronized(System.out) evitamos mezcla de líneas, pero también serializamos esa parte de la ejecución.
- A la hora de medir, esta serialización puede enmascarar las ganancias de paralelismo.

Pruebas y observaciones prácticas
- Comparación secuencial vs concurrente:
  - Si las tareas son E/S-bound: concurrente normalmente más rápido.
  - Si las tareas son CPU-bound y hay pocos núcleos: la concurrencia puede no ayudar o empeorar por overhead.
- Escenario sincronizado vs no sincronizado:
  - Con synchronized: resultados deterministas (p. ej. saldo correcto).
  - Sin synchronized: pérdida de actualizaciones y resultados inconsistentes.
- Efecto de la impresión masiva: en pruebas con mucha salida, suprimir impresión para medir solo la lectura mejora la validez de las comparaciones.

Preguntas tipo examen (y respuestas cortas)

P1: ¿Cuál es la diferencia entre start() y run() en Java?
R: start() crea/lanza un nuevo hilo y ejecuta run() en ese hilo; run() directo se ejecuta en el hilo actual (no crea hilo nuevo).

P2: ¿Qué problema evita synchronized en operaciones sobre un recurso compartido?
R: Evita condiciones de carrera garantizando exclusión mutua y visibilidad entre hilos.

P3: ¿Por qué la concurrencia puede reducir el tiempo en tareas E/S-bound?
R: Porque permite solapar esperas de E/S: mientras un hilo espera datos, otro puede avanzar.

P4: ¿Por qué hay que evitar imprimir mucho en consola durante mediciones de rendimiento?
R: Porque println es lento y actúa como cuello de botella compartido, serializando parte del trabajo y distorsionando las mediciones.

Consejos prácticos para examen y laboratorio
- Saber distinguir entre problemas CPU-bound y I/O-bound y elegir la estrategia adecuada.
- Usar ExecutorService para manejar pools de hilos en programas reales y evitar el overhead de crear hilos continuamente.
- Medir varias veces y usar System.nanoTime() para mayor precisión.
- Evitar locks excesivos: sincronizar solo la sección crítica imprescindible.
- Entender happens-before: synchronized y volatile son esenciales para visibilidad de cambios entre hilos.
- Manejar InterruptedException correctamente (restaurar la interrupción si no se propaga).

Notas finales
Este README recoge los puntos esenciales para entender por qué la ejecución concurrente y la secuencial pueden dar tiempos distintos y cómo medir esas diferencias de forma fiable. Para experimentos reproducibles: suprime la salida, repite ejecuciones, usa pools de hilos y registra medidas con System.nanoTime().

He redactado este README explicando los conceptos y prácticas que aparecen en los ejercicios de concurrencia y medición. Si quieres, lo convierto en un archivo README.md en tu repositorio, añado ejemplos concretos de código para medir por hilo o preparo una pequeña guía de pruebas con scripts para automatizar múltiples ejecuciones y calcular medias.  
```
