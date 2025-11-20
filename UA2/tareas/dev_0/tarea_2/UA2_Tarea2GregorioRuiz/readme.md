# Tarea 2 - Programación y sincronización de hilos en Java

Contenido
- CuentaCorriente.java
- IngresadorThread.java
- Tarea2Main.java

Descripción breve
El proyecto muestra cómo varios hilos pueden compartir un mismo objeto (CuentaCorriente) y cómo la sincronización evita condiciones de carrera. Se incluyen dos métodos de ingreso: uno sincronizado (ingresarSync) y otro no sincronizado (ingresarNoSync). Los getters/setters simulan trabajo con sleeps aleatorios (250-2000 ms) para aumentar la probabilidad de interleaving.

Cómo compilar y ejecutar
1. Compilar:
   javac files/UA2/tareas/dev_0/tarea_2/*.java

2. Ejecutar:
   java -cp files/ UA2.tareas.dev_0.tarea_2.Tarea2Main

Conceptos clave (resumen para examen)

1) Hilos (Thread)
- Un hilo es una unidad de ejecución dentro de un proceso.
- En Java se puede crear un hilo extendiendo Thread o implementando Runnable.
- start() lanza el hilo y ejecuta su método run() en paralelo.

2) Condición de carrera (race condition)
- Ocurre cuando dos o más hilos acceden y modifican un recurso compartido sin sincronización.
- Resulta en comportamientos no deterministas y pérdida de actualizaciones.
- En este ejercicio, si varios hilos leen el saldo antes de que otro lo actualice, se pueden perder ingresos.

3) synchronized y bloqueo intrínseco (intrinsic lock)
- La palabra clave synchronized en un método (o bloque) obliga a que sólo un hilo a la vez pueda ejecutar esa sección para el mismo objeto.
- Garantiza exclusión mutua y hace que las operaciones compuestas (leer-modificar-escribir) sean atómicas respecto a otros hilos que usan el mismo lock.
- Usar synchronized en ingresarSync evita que se produzcan condiciones de carrera en la actualización del saldo.

4) atomicidad y visibilidad
- Atomicidad: una operación completa sin interferencia. Operaciones compuestas necesitan sincronización.
- Visibilidad: cambios hechos por un hilo deben ser visibles para otros; synchronized además de exclusión, establece las reglas de visibilidad (happens-before).

5) Thread.sleep()
- Suspende el hilo actual durante el tiempo indicado.
- Puede lanzar InterruptedException.
- No libera locks al dormir: si el hilo tiene un lock, seguirás manteniéndolo.

6) InterruptedException y manejo de interrupciones
- Se lanza cuando otro hilo interrumpe a un hilo bloqueado.
- Buenas prácticas:
  - Restaurar el estado de interrupción con Thread.currentThread().interrupt() si se captura y no se repropaga.
  - No silenciar las interrupciones sin motivo.

7) join()
- join() permite que el hilo invocador espere a que otro hilo termine.
- Se usa en el main para asegurar que todos los ingresos hayan terminado antes de mostrar el saldo final.

8) Control de errores básico
- Validar entradas (ej.: cantidades positivas, saldo inicial no negativo).
- Capturar excepciones esperadas y manejar InterruptedException como se indicó.

9) Pruebas y observaciones prácticas
- Escenario sincronizado: el saldo final suele coincidir con saldoInicial + sumaDeIngresos.
- Escenario no sincronizado: el saldo final frecuentemente es menor que el esperado por pérdida de actualizaciones.
- Ejecutar varias veces para observar el comportamiento no determinista cuando no se sincroniza.
- Para amplificar la concurrencia se introducen sleeps en getter/setter.

Preguntas tipo examen (y respuestas cortas)

P1: ¿Qué problema resuelve synchronized en este ejercicio?
R: Evita condiciones de carrera durante el lector-modificador-escritor del saldo, garantizando exclusión mutua.

P2: ¿Qué ocurriría si quitamos synchronized?
R: Varias actualizaciones pueden perderse porque varios hilos leen el mismo saldo previo y escriben nuevos saldos basados en ese valor viejo.

P3: ¿Por qué se usan sleeps en getters/setters?
R: Para simular trabajo y aumentar la probabilidad de interleaving entre hilos, haciendo más evidente la condición de carrera.

P4: ¿Cómo debería manejarse InterruptedException en un programa que no quiere terminar inmediatamente?
R: Restaurar el estado de interrupción con Thread.currentThread().interrupt() y realizar limpieza necesaria o propagar la interrupción si procede.

Consejos prácticos para examen
- Saber distinguir entre sincronización por método (synchronized) y sincronización por bloque (synchronized(obj)).
- Entender el concepto happens-before y por qué synchronized garantiza visibilidad.
- Poder explicar ejemplos concretos de pérdida de actualizaciones con pseudocódigo (leer X; X = X + 1; escribir X).
- Conocer cómo usar join() para coordinar la finalización de hilos.

Fin del documento