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

Antes de nada, me gustaría añadir que al principio si que hacía ping, pero tras hacerle algunos cambios dejó de hacer ping a los 2 últimos y no entiendo el porqué.

La diferencia principal entre proceso e hilo, es que los hilos se ejecutan de manera paralela mientras que los procesos se ejecutan de manera secuencial y estos, compiten por los recursos a los cuales tienen que acceder.
