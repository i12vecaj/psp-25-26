# 🧵 Ejercicio — Observación del comportamiento de los hilos en Java

##🔹 Orden de ejecución de los hilos

¿Siempre es el mismo?
No hace siempre lo mismo al ejecutar el .yield() hace como una sugerencia pero es exactamente eso, además al tener que tomar el valor i puede estar pasando un hilo u otro sin que sea siempre exacto salvo el 2 que está interrumpido previamente.

Influencia de la prioridad

¿Influye la prioridad?
Esto la influye en esa parte del código:

´´´ java 
h1.setPriority(Thread.MIN_PRIORITY);   // Prioridad más baja
h2.setPriority(Thread.NORM_PRIORITY);  // Prioridad normal
h3.setPriority(Thread.MAX_PRIORITY);   // Prioridad más alta
```
En esta parte del código establecemos qué prioridad tiene cada hilo, pero en esta parte de código:

```Java
// Interrumpimos el hilo 2 tras un pequeño retraso
try {
   Thread.sleep(10);
   h2.interrupt();
} catch (InterruptedException e) {
   e.printStackTrace();
}

Establecemos un tiempo de “dormir” todos los hilos, incluso uno de ellos se para totalmente.
Y ese tiempo que están dormidos ya no afecta a la prioridad que hemos establecido previamente.

##🔹 Diferencia entre start() y run()

En pocas palabras:
Al usar run() los hilos que haya van de forma secuencial
y al establecer start() los hilos van de forma concurrente.

##🔹 ¿Qué pasa si en lugar de start() llamas directamente a run()?

Empieza y va alternando los dos hilos, o sea, de forma concurrente.

##🔹 Experimenta con setPriority()

Prueba a darle al Hilo-1 prioridad máxima y al Hilo-3 mínima.
¿Qué cambia?
Comenta la línea donde se llama a interrupt() y vuelve a ejecutar.

¿Qué ocurre ahora con el hilo 2?
Al comentar la línea 25 el hilo 2 ya no se interrumpe y ya están todos los hilos en true, o dicho de otra forma, funcionan correctamente.

##🔹 Añade una línea al final del main que muestre el estado final de los hilos con isAlive()

```java
System.out.println("Estado final → " 
   + h1.isAlive() + ", " 
   + h2.isAlive() + ", " 
   + h3.isAlive());
```
¿Cuándo pasa a false?
Ya está en true porque al comentar la línea 25 no se ha interrumpido.

##🔹 Prueba a imprimir h1.toString() en distintos momentos

No noto cambios ya que la información que muestra es la del hilo.