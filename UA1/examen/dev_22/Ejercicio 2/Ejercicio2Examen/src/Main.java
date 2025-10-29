/*
a) Explica cómo se comportaría un sistema operativo al ejecutar dos procesos que acceden al mismo archivo de manera simultánea. Indica qué problemas podrían surgir y cómo podrían evitarse.

- Si los procesos solo leen el sistema operativo suele permitirlo. Si uno escribe el sistema operativo debe intervenir.
- Lo que puede pasar es que se pierda alguna actualizacion

b) Define qué es un hilo daemon y describe un caso práctico en el que sería útil utilizarlo.
Indica también qué ocurriría si el hilo principal termina antes que los hilos daemon.

- Hilo daemon: Es un hilo de baja prioridad que se ejecuta en segundo plano. Ejemplo: El Recolector de Basura de Java
- Si el hilo principal el programa se cierra, y todos los hilos daemon que quedan no puede finalizar su tarea

c) En un programa Java, se crean tres hilos que comparten una misma variable global.
Explica:
* Qué tipo de problemas podrían aparecer.
    - Un hilo puede modificar la cache
    - Hay operaciones que pueden quedarse interrumpida
* Cómo podrías solucionarlos empleando mecanismos de sincronización.
    - Usar la palabra synchronized
* Si existe alguna alternativa más eficiente en ciertos casos.
    - Es más eficiente usar clases atómicas

d) Compara los modelos de ejecución concurrente, paralela y distribuida, indicando:
* Un ejemplo típico de aplicación en cada caso.
    - Concurrente: Un Navegador web
    - Paralela: Un renderizador de videos
    - Distribuida: Un motor de busqueda
* Qué tipo de hardware o entorno necesita cada modelo.
    - Concurrente: 1 CPU
    - Paralela: 1 CPU con varios nucleos 
    - Distribuida: Varios ordenadores
* Qué ventajas e inconvenientes tiene cada uno en términos de rendimiento y complejidad.
    - Concurrente:
        - Ventaja: Responsividad
        - Inconveniente: Complejidad de sincronizacion
    - Paralela:
        - Ventaja: Aumento de velocidad
        - Inconveniente: Dificil de programar
    - Distribuida:
        - Ventaja: Tolerancia a fallos
        - Inconveniente: Mucha complejidad
*/