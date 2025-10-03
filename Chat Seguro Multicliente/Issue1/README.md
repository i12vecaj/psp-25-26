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

### Diferencias entre Proceso e Hilo
- Un proceso es un programa en ejecución con su propio espacio de memoria y recursos. Ejecutarlo es más pesado porque el sistema crea un entorno separado.
Por ejemplo, abrir tu navegador web es un proceso independiente.
- Un hilo es una unidad de ejecución dentro de un proceso. Comparte la memoria y los recursos del proceso al que pertenece, por lo que es más ligero y rápido. Por ejemplo, en el navegador, cada pestaña puede tener varios hilos trabajando dentro del mismo proceso.
