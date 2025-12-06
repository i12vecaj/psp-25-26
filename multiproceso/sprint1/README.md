## Diferencias entre Proceso y Hilo

###  Proceso
Un proceso es un programa en ejecución que tiene su propio espacio de memoria y recursos asignados por el sistema operativo. Cada proceso funciona de manera independiente y no comparte directamente su memoria con otros procesos. Crear un proceso nuevo suele ser más costoso porque implica reservar memoria y recursos adicionales

### Hilo
Un hilo es una unidad de ejecución dentro de un proceso. Los hilos de un mismo proceso comparten la misma memoria y recursos, lo que los hace más ligeros y rápidos de crear que los procesos. Se utilizan para realizar varias tareas de forma simultánea dentro de un mismo programa

### Idea clave
- Los procesos son independientes y pesados
- Los hilos son más ligeros y trabajan dentro de un proceso, compartiendo recursos
- En programación concurrente, los hilos permiten aprovechar mejor la CPU y realizar varias tareas al mismo tiempo dentro de un mismo programa
