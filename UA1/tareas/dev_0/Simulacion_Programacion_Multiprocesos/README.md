En programación concurrente, los procesos y los hilos (threads) son dos formas de ejecutar tareas simultáneamente, pero funcionan de manera diferente:
| Característica | **Proceso** | **Hilo** |
| ------------------- | ---------------------------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------- |
| **Definición** | Es una instancia independiente de un programa en ejecución. | Es una unidad de ejecución dentro de un proceso.  |
| **Memoria** | Tiene su propio espacio de memoria (aislado de otros procesos).  | Comparte la memoria y los recursos del proceso principal.  |
| **Comunicación** | La comunicación entre procesos es más lenta y requiere mecanismos como tuberías, sockets o archivos. | Los hilos pueden comunicarse fácilmente entre sí, ya que comparten variables y datos.  |
| **Rendimiento** | Más pesado: crear o destruir un proceso consume más recursos.  | Más ligero: los hilos se crean y gestionan más rápido.  |
| **Seguridad** | Un error en un proceso no afecta a los demás.  | Un error en un hilo puede afectar a todo el proceso.  |
| **Ejemplo en Java** | Usar `ProcessBuilder` para lanzar programas o scripts externos. | Usar la clase `Thread` o `ExecutorService` para ejecutar tareas concurrentes dentro del mismo programa. |


En resumen

Un proceso es como ejecutar un programa separado (por ejemplo, abrir tres terminales y ejecutar tres comandos distintos).

Un hilo es como tener varias tareas trabajando al mismo tiempo dentro del mismo programa, compartiendo la memoria y los recursos.

En este proyecto, la clase ProcessSimulator demuestra la ejecución de procesos simultáneos, lanzando varios comandos de shell al mismo tiempo y comparando su rendimiento frente a la ejecución secuencial.
