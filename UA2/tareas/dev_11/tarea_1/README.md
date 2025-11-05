<div align="center">

# T1 – Tarea 1 – Programación y sincronización de hilos en Java 1

</div>

#### _Criterios a), b), c), d), e), f), g) y h)_

**PROCESOS**. Crea un programa Java que implemente las siguientes Funcionalidades Requeridas (FRs):

- FR1: Crea un programa en Java que lance 5 hilos. Cada hilo incrementará una variable contador de tipo entero en 1000 unidades. Esta variable estará compartida por todos los hilos. Comprueba el resultado final de la variable y reflexiona sobre el resultado. ¿Se obtiene el resultado esperado? - 3 puntos
- FR2: Modifica el programa anterior para sincronizar el acceso a dicha varaible. Lanza primero los hilos mediante la clase Thread y después mediante el interfaz Runnable. Comprueba los resultados e indica las variaciones - 3 puntos
- Implementa el control de errores básico - 2 puntos
- Documenta el código indicando el funcionamiento del programa y las diferencias que has observado entre el primer y el segundo apartado. - 2 puntos
- Entregables:
  - tareas\dev_X\tarea_1\ua2tarea1fr1.java
  - tareas\dev_X\tarea_1\ua2tarea1fr2.java
  - tareas\dev_X\tarea_1\ua2tarea1fr2runnable.java

## FR1 — Ejecución sin sincronización

`Archivo: ua2tarea1fr1.java`

En esta versión, los cinco hilos incrementan una misma variable contador (de tipo entero) 1000 veces cada uno, sin aplicar ningún tipo de sincronización.

### Funcionamiento

- Se crea un objeto Contador con un valor inicial de 0.
- Se lanzan 5 instancias de HiloIncrementador, cada una ejecutando 1000 incrementos.
- Como el método incrementar() no está sincronizado, varios hilos pueden modificar la variable al mismo tiempo.
- Esto genera condiciones de carrera, provocando que el valor final sea menor que 5000.

### Resultado esperado y observado

- Esperado: 5000
- Resultado real: Menor que 5000 (varía en cada ejecución)

<div align="center">

<img src="https://i.imgur.com/X9otmmT.png" alt="Ejemplo de salida"  />

Conclusión: El valor final no es el esperado debido al acceso concurrente no controlado.

</div>

## FR2 — Ejecución con sincronización (usando Thread)

`Archivo: ua2tarea1fr2.java`

En esta versión se añade la palabra clave synchronized al método incrementar() del contador compartido, garantizando que solo un hilo puede acceder a este método a la vez.

### Funcionamiento

- Se utiliza la clase Thread como en la primera parte.
- Cada hilo incrementa el contador 1000 veces, pero ahora los accesos están bloqueados temporalmente para evitar interferencias.
- El acceso sincronizado asegura que no se pierdan incrementos.

### Resultado esperado y observado

- Esperado: 5000
- Resultado real: 5000

<div align="center">

<img src="https://i.imgur.com/qjUevlj.png" alt="Ejemplo de salida"  />

Conclusión: La sincronización evita las condiciones de carrera y garantiza la consistencia de los datos.

</div>

## FR2 (Runnable) — Ejecución con Runnable sincronizado

`Archivo: ua2tarea1fr2runnable.java`

Esta versión implementa los hilos usando la interfaz Runnable en lugar de extender la clase Thread.
Aunque el comportamiento es el mismo, esta forma es más flexible y permite que la clase principal herede de otras clases si fuera necesario.

### Funcionamiento

- Se define una clase HiloIncrementador3 que implementa Runnable.
- Se sincroniza el método incrementar() igual que en la versión anterior.
- Se crean los hilos mediante new Thread(runnable).

### Resultado esperado y observado

- Esperado: 5000
- Resultado real: 5000

<div align="center">

<img src="https://i.imgur.com/1FzOIGM.png" alt="Ejemplo de salida"  />

Conclusión: Usar Runnable facilita la reutilización y escalabilidad del código, manteniendo la seguridad de la sincronización.

</div>
