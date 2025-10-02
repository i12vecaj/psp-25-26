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

# Diferencias entre Proceso e Hilo

## 🔹 Proceso
- Es una instancia de un programa en ejecución.
- Tiene su propio espacio de memoria independiente.
- La comunicación entre procesos requiere mecanismos como **pipes, sockets o ficheros**.
- Son más pesados de crear y administrar.
- Fallo en un proceso no afecta directamente a otros (aislamiento).

## 🔹 Hilo
- Es una unidad de ejecución dentro de un proceso.
- Comparte la misma memoria y recursos del proceso padre.
- La comunicación entre hilos es más sencilla (usan las mismas variables).
- Son más ligeros y rápidos de crear que los procesos.
- Fallo en un hilo puede comprometer todo el proceso.

📌 **Resumen:**  
- Los **procesos** ofrecen **aislamiento** pero consumen más recursos.  
- Los **hilos** son más **eficientes** pero tienen más riesgo de errores compartidos.


Tiempo de Ejecución Parela: 2,14 Segundos
Tiempo de Ejecución Secuencial:2,20 Segundos

Los Dos codigos han tenido practicamente el mismo tiempo