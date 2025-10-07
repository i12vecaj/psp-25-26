# 🧱 Sprint 1 – Simulación de Programación Multiproceso

## 🎯 Objetivo
Simular la ejecución de varios procesos independientes usando la clase `ProcessBuilder` en Java.

## 🚀 Ejecución
El proyecto contiene dos clases principales:

- **Worker.java**: simula una tarea con retardo (sleep) para representar una ejecución costosa.
- **ProcessSimulator.java**: lanza tres procesos de `Worker` de forma **paralela** y luego **secuencial**, midiendo el tiempo de ejecución y guardando los resultados en `logs/resultados_multiproceso.txt`.

### Pasos para ejecutar:
1. Compilar ambos archivos:
   ```bash
   javac Worker.java ProcessSimulator.java
