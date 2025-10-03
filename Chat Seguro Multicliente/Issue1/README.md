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

== EXPLICACIÓN TÉCNICA DE LA DIFERENCIA ENTRE PROCESO E HILO ==
Proceso: Programa en ejecución con su propio espacio de memoria
Hilo: Unidad de ejecución dentro de un proceso que comparte memoria
ProcessBuilder: Crea procesos del SO, no hilos de Java

