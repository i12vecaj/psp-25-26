# 🧱 Sprint 1 – Simulación de Programación Multiproceso

## 🎯 Objetivo
Simular la ejecución de varios procesos independientes con la clase **`ProcessBuilder`** en Java, midiendo el tiempo de ejecución en modo **secuencial** y en modo **paralelo**, y guardar los resultados en un archivo de log.

---

## 📂 Estructura del Sprint
Chat Seguro Multicliente/
├── server/
│ └── src/
│ └── ProcessSimulator.java # Código principal
├── logs/
│ └── resultados_multiproceso.txt # Resultados guardados
└── README.md # Este archivo


---

## ⚙️ Funcionamiento
- **Secuencial**: los procesos se ejecutan uno detrás de otro, esperando a que cada uno termine.
- **Paralelo**: los procesos se lanzan todos a la vez y el programa espera a que finalicen.
- Se mide el tiempo total de cada modo con `System.currentTimeMillis()`.
- Se guardan los resultados en `logs/resultados_multiproceso.txt`.

Ejemplo del log:
=== Sprint 1 – Ejecución ===
Tiempo SECUENCIAL: 6045 ms
Tiempo PARALELO  : 2018 ms
Diferencia       : 4027 ms

---

### 🧱 Proceso
- Es un **programa en ejecución completo**.
- Tiene su **propio espacio de memoria** (variables, pila, recursos).
- No comparte memoria con otros procesos → para comunicarse necesitan ficheros, sockets, etc.
- Crear un proceso es **más costoso** porque el sistema debe reservar memoria y recursos nuevos.

👉 Ejemplo: abrir **IntelliJ** o lanzar un `ping` con `ProcessBuilder` en Java.

---

### 🧵 Hilo (Thread)
- Es una **subunidad de ejecución dentro de un proceso**.
- **Comparte la memoria** con los demás hilos de ese proceso.
- Se crean y gestionan más **rápido** que un proceso.
- Pueden acceder a las mismas variables → requieren **sincronización** para no interferir entre sí.

👉 Ejemplo: en **Chrome**, un hilo gestiona la interfaz, otro escucha el teclado y otro procesa la red.  
En Java, se crean con `Thread`, `Runnable` o `ExecutorService`.

---

## 📦 Entregables
- ✅ Código en `server/src/ProcessSimulator.java`
- ✅ Log en `logs/resultados_multiproceso.txt`
- ✅ README con explicación técnica
