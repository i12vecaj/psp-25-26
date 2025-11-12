<div align="center">

# Carrera de Hilos en Java

</div>

# Código base

El programa crea tres hilos (`Hilo-1`, `Hilo-2`, `Hilo-3`) con distintas prioridades y muestra su estado en diferentes momentos. Uno de los hilos se interrumpe durante la ejecución.

## 1. ¿El orden de ejecución de los hilos siempre es el mismo? ¿Influye la prioridad?

No, el orden de ejecución no siempre es el mismo.
La planificación de hilos depende del sistema operativo y del planificador de la JVM, por lo que puede variar en cada ejecución.

Sin embargo, la prioridad puede influir: los hilos con prioridad más alta tienen más probabilidades de obtener tiempo de CPU antes que los de prioridad baja.
Aun así, no garantiza que siempre se ejecuten primero, ya que el planificador puede repartir el tiempo de forma diferente en cada ejecución.

## 2. Diferencia entre start() y run()

- `start()` crea un nuevo hilo de ejecución. La JVM llama internamente al método `run()` dentro de ese nuevo hilo.

  - **Ejemplo**: el hilo corre en paralelo al hilo principal.

- `run()` ejecuta el código en el mismo hilo actual (no crea uno nuevo).
  - **Ejemplo**: si llamas `h1.run()`, no se ejecuta concurrentemente; simplemente se llama como un método normal dentro del `main`.

## 3. ¿Qué pasa si en lugar de start() llamas directamente a run()?

Si reemplazas `h1.start()` por `h1.run()`, no hay concurrencia:
Los hilos se ejecutan uno detrás de otro, de forma secuencial, dentro del mismo hilo principal (`main`).

Por tanto:

- No hay ejecución simultánea.
- No cambia el contexto de hilos.
- Los métodos `isAlive()` devolverán `false` después de ejecutarse, ya que nunca se inicia un hilo real.

## 4. Experimento con `setPriority()`

Se cambia la prioridad así:

```
h1.setPriority(Thread.MAX_PRIORITY);
h3.setPriority(Thread.MIN_PRIORITY);
```

**Resultados:**

- `Hilo-1` (máxima prioridad) tiende a ejecutarse antes o con más frecuencia.
- `Hilo-3` (mínima prioridad) puede esperar más tiempo o ejecutarse después.
- Aun así, el resultado no es determinista: depende del sistema operativo y la CPU disponible.

## 5. Comenta la línea `h2.interrupt();` y ejecuta de nuevo

Al comentar la interrupción:

```
// h2.interrupt();
```

Ahora el `Hilo-2` no será interrumpido y podrá terminar su bucle completo.
Antes, cuando se llamaba a `interrupt()`, se lanzaba una `InterruptedException` durante el `sleep()`, y el hilo se detenía prematuramente mostrando:

```
❌ Hilo-2 fue interrumpido.
```

Al eliminar esa llamada, el hilo continúa hasta imprimir:

```
🏁 Hilo-2 ha terminado.
```

## 6. Mostrar el estado final de los hilos

Se añade al final del `main`:

```
System.out.println("\n🔹 Estado final:");
System.out.println(h1.getName() + " vivo: " + h1.isAlive());
System.out.println(h2.getName() + " vivo: " + h2.isAlive());
System.out.println(h3.getName() + " vivo: " + h3.isAlive());
```

Cuándo pasa a `false`:

- `isAlive()` devuelve `true` mientras el hilo está en ejecución o en espera (RUNNABLE, TIMED_WAITING, etc.).
- Cuando el hilo finaliza su método `run()`, pasa al estado `TERMINATED`, y `isAlive()` devuelve `false`.

## 7. Imprimir `h1.toString()` en distintos momentos

Si haces algo como:

```
System.out.println(h1.toString());
```

en diferentes puntos del programa, observarás cambios como:

```
Thread[Hilo-1,1,main]
```

- El segundo número representa la prioridad del hilo.
- Al principio estará en estado NEW (antes de start()).
- Luego pasa a RUNNABLE o TIMED_WAITING (durante sleep()).
- Finalmente, tras terminar, su estado será TERMINATED.

Esto permite observar visualmente cómo evoluciona el ciclo de vida de un hilo.

## Conclusión

Los hilos en Java no garantizan un orden de ejecución fijo, incluso si se establecen prioridades.
El método `start()` es esencial para ejecutar código de forma concurrente.
Las interrupciones y el método `isAlive()` son útiles para controlar y supervisar el ciclo de vida de los hilos.

## 📚 Referencias

- [Documentación oficial de la clase Thread](https://docs.oracle.com/javase/8/docs/api/java/lang/Thread.html)
- [Guía de concurrencia en Java (Oracle)](https://docs.oracle.com/javase/tutorial/essential/concurrency/)
