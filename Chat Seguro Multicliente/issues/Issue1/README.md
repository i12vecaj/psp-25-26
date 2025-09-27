## 🧱 Sprint 1 – Simulación de Programación Multiproceso

### 🎯 Objetivo

Simular la ejecución de varios procesos independientes usando la clase `ProcessBuilder` en Java.

### ✅ Tareas

- ✅ Crear una clase `ProcessSimulator` que ejecute 3 scripts o clases Java simultáneamente.
- ✅ Medir el tiempo total de ejecución y compararlo con la ejecución secuencial.
- ✅ Guardar los resultados en un archivo de log (`logs/resultados_multiproceso.txt`).
- ✅ Documentar en el README diferencias entre proceso e hilo.

### 📦 Entregables

- Código funcional en carpeta `/multiproceso`.
- Archivo de log generado.
- Actualización del README con explicación técnica.

### 📅 Fecha de entrega: 2025

# Resolución del Sprint 1

<div align="center">

<img src="https://i.imgur.com/ZIKuzNq.png" alt="Resultado de la Issue"  />

</div>

## Tareas

### ✅ Crear una clase `ProcessSimulator` que ejecute 3 scripts o clases Java simultáneamente

**Evidencia.** Estructura de carpetas donde se encuentran los archivos `ProcessSimulator.java`, `Worker1.java`, `Worker2.java` y `Worker3.java`:

<img src="https://i.imgur.com/9ZLb4rz.png" alt="Estructura de carpetas" />

### ✅ Medir el tiempo total de ejecución y compararlo con la ejecución secuencial + ✅ Guardar los resultados en un archivo de log (`logs/resultados_multiproceso.txt`)

**Evidencia.** salida de contenido en el archivo `resultados_multiproceso.txt` mostrando los tiempos medidos de ejecución paralela y secuencial:

<img src="https://i.imgur.com/5dUvT7Y.png" alt="Archivo de log con resultados con el tiempo de ejecución paralelo vs secuencial" />

### ✅ Documentar en el README diferencias entre proceso e hilo

**_Explicación técnica:_**

Este proyecto realiza una simulación sencilla usando la clase ProcessBuilder en Java.
Se comparan dos formas de ejecutar tareas:

- **Secuencial**: las tareas se ejecutan una tras otra.
- **Paralela**: las tareas se ejecutan al mismo tiempo (independientes).

**_📂 Archivos principales:_**

- **ProcessSimulator.java**: ejecuta los procesos y mide los tiempos.
- **Worker1.java, Worker2.java, Worker3.java**: tareas simuladas con Thread.sleep().
- **logs/resultados_multiproceso.txt**: archivo generado automáticamente con los tiempos.

**_Diferencia entre proceso 🆚 hilo:_**
| Proceso | Hilo |
|--------|------|
| Cada proceso tiene su propia memoria y recursos. | Los hilos comparten memoria dentro del mismo proceso. |
| Son más pesados y lentos de crear y gestionar. | Son más ligeros y rápidos de crear y gestionar. |
| Aíslan mejor los errores: un fallo en un proceso no afecta a otros procesos. | Un fallo en un hilo puede afectar a otros hilos del mismo proceso. |
| Cada proceso es independiente y tiene su propio espacio de direcciones. | Los hilos comparten el espacio de direcciones del proceso padre. |
| Utilizan más recursos del sistema. | Utilizan menos recursos del sistema. |

**_🛠 Requisitos:_**

- Java instalado
- Compilar todos los .java antes de ejecutar:

```sh
javac *.java
java ProcessSimulator
```
