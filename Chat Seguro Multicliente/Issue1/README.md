## 🧱 Sprint 1 – Simulación de Programación Multiproceso

### 🎯 Objetivo
Simular la ejecución de varios procesos independientes usando la clase `ProcessBuilder` en Java.

### ✅ Tareas
- [ ] Crear una clase `ProcessSimulator` que ejecute 3 scripts o clases Java simultáneamente.
- [ ] Medir el tiempo total de ejecución y compararlo con la ejecución secuencial.
- [ ] Guardar los resultados en un archivo de log (`logs/resultados_multiproceso.txt`).
- [ ] Documentar en el README diferencias entre proceso e hilo.

### 📦 Entregables
- Código funcional en carpeta `/multiproceso`.
- Archivo de log generado.
- Actualización del README con explicación técnica.

### 📅 Fecha de entrega: 2025

### DIFERENCIA ENTRE PROCESOS E HILOS 

- Proceso: es un programa que se está ejecutando en la computadora y tiene su propia memoria y variables. Es independiente de otros procesos, por lo que si uno falla, los demás no se ven afectados. Crear un proceso consume más recursos, y cada programa abierto (como Chrome o Word) es un ejemplo de proceso.
- Hilo: es una “mini-tarea” dentro de un proceso que comparte la memoria y los recursos de ese proceso. Son más ligeros y rápidos de crear que los procesos, y permiten hacer varias cosas al mismo tiempo dentro de un mismo programa. Por ejemplo, en un navegador, cada pestaña podría ser un hilo que comparte recursos con el resto.
- Los procesos son independientes y pesados, mientras que los hilos son más ligeros y permiten paralelismo dentro de un mismo programa.

### Explicación paso a paso

1- Primero, tenemos tres programas Java separados: Script1, Script2 y Script3. Cada uno simula una tarea que tarda un tiempo determinado usando Thread.sleep(). Técnicamente, cada script es un proceso independiente, con su propia memoria y variables, que se ejecuta por separado del resto.

2- En el programa principal ProcessSimulator, verificamos que exista la carpeta logs donde guardaremos los resultados. Si no existe, la creamos usando mkdirs(). Esto evita errores al intentar escribir el archivo de log.

3️- Recorremos los tres scripts uno por uno dentro de un bucle for. Para cada script, usamos Runtime.getRuntime().exec(comando) para lanzar el proceso y waitFor() para esperar a que termine antes de pasar al siguiente. De esta manera, los procesos se ejecutan de manera estrictamente secuencial, sin paralelismo.

4️- Usamos System.currentTimeMillis() antes de iniciar el bucle y después de que termina el último proceso. La diferencia entre ambos tiempos nos da el tiempo total de ejecución secuencial.

5️- Finalmente, abrimos un FileWriter para escribir en logs/resultados_multiproceso.txt y guardamos el tiempo total medido. Así podemos comparar posteriormente este tiempo con una versión que sí use ejecución paralela o hilos.
