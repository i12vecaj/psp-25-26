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

DIFERENCIA ENTRE PROCESOS E HILOS 
- Proceso: es un programa que se está ejecutando en la computadora y tiene su propia memoria y variables. Es independiente de otros procesos, por lo que si uno falla, los demás no se ven afectados. Crear un proceso consume más recursos, y cada programa abierto (como Chrome o Word) es un ejemplo de proceso.
- Hilo: es una “mini-tarea” dentro de un proceso que comparte la memoria y los recursos de ese proceso. Son más ligeros y rápidos de crear que los procesos, y permiten hacer varias cosas al mismo tiempo dentro de un mismo programa. Por ejemplo, en un navegador, cada pestaña podría ser un hilo que comparte recursos con el resto.
Los procesos son independientes y pesados, mientras que los hilos son más ligeros y permiten paralelismo dentro de un mismo programa.
