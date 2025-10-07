Sprint 1 – Simulación de Programación Multiproceso

Objetivo del ejercicio
El objetivo de este proyecto es practicar el uso de multiprocesos en Java utilizando la clase ProcessBuilder.
En este ejercicio se muestra cómo ejecutar tres clases .java, cada una como un proceso independiente.

Formas de ejecución
El programa ejecuta los procesos de dos formas:
- Secuencial → se ejecuta un proceso tras otro.
- Paralela → se ejecutan todos los procesos al mismo tiempo.

Estructura del proyecto
multiproceso/
└── Main.java → lanza las 3 clases de forma secuencial y paralela
logs/
└── tiempos.txt → archivo donde se guardan los tiempos de ejecución

¿Qué pasa cuando se ejecuta?
Cuando ejecutas Main.java, el programa lanza los tres procesos, primero de forma secuencial y luego en paralelo.
En la carpeta logs aparecerá un archivo llamado tiempos.txt con el siguiente formato:

Secuencial: 311 ms
Paralelo: 122 ms

Diferencias entre proceso e hilo

Proceso:
- Es un programa en ejecución.
- Cada proceso tiene su propia memoria y recursos.
- Son más lentos.
- Se comunican entre sí mediante mecanismos del sistema operativo.

Hilo:
- Es una parte de un proceso.
- Varios hilos comparten la misma memoria.
- Son más rápidos.
- Ideales para tareas simultáneas dentro del mismo programa.

