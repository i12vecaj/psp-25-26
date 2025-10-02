# Sprint 1 – Simulación de Programación Multiproceso

## Objetivo del ejercicio

El objetivo de este proyecto es practicar el uso de **multiprocesos en Java** con la clase ProcessBuilder.  
En este ejercicio se muestra como ejecutar tres .java cada uno como un proceso independiente.

- Aqui hay dos formas de ejecución:
  - Por un lado, **Secuencial**, es decir hay un proceso detrás de otro
  - Por otro lado, **Paralela**, son todos los procesos a la vez

## Estructura del proyecto
  - **multiproceso/HolaMundo.java** → imprime "Hola mundo"
  - **multiproceso/Adios.java** → imprime "Adiós"
  - **multiproceso/Buenas.java** → imprime "Buenas tardes"
  - **multiproceso/ProcessSimulator.java** → lanza las 3 clases de forma secuencial y paralela
  - **logs/resultados_multiproceso.txt** → archivo donde se guardan los tiempos de ejecución

## ¿Qué pasa cuando se ejecuta?
  - En el archivo logs aparecerá esto: 

    Tiempo total secuencial: 311 ms
    Tiempo total paralelo: 122 ms 


## Diferencias entre proceso e hilo

  - **Proceso:**
    - Es un programa en ejecución
    - Cada proceso tiene su memoria y recursos
    - Son más lentos

  - **Hilo:**
    - Es una parte de un proceso
    - Varios hilos comparten misma memoria
    - Son más rápidos
    - Para cuando queremos teneer varias tareas dentro de un mismo programa
