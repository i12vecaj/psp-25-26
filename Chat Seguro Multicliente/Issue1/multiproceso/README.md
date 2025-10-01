¿Qué hace ProcessSimulator?
Lanza 3 scripts Java (Script1, Script2, Script3).

Mide el tiempo de ejecución de forma:
Secuencial → un proceso tras otro.
Paralela → todos los procesos a la vez.
Guardamos resultados en un log.

flowchart TD
    A["Proceso Principal"] -- Secuencial --> B1["Script1"]
    B1 --> B2["Script2"]
    B2 --> B3["Script3"]
    A -- Paralelo --> P[" "]
    P --> C1["Script1 "] & C2["Script2 "] & C3["Script3 "]

    P@{ shape: f-circ}

==============================================================
         DIFERENCIAS ENTRE PROCESO E HILO 
==============================================================

Característica        | Proceso (🖥️)                     | Hilo (🧵)
-----------------------|-----------------------------------|-----------------------------------
Unidad de ejecución    | Programa independiente            | Subtarea dentro de un proceso
Memoria                | Propia y aislada                  | Comparte memoria con otros hilos
Coste de creación      | Alto (pesado de crear)            | Bajo (ligero de crear)
Aislamiento            | Seguro: fallo no afecta a otros   | Inseguro: fallo afecta al proceso
Comunicación           | Compleja (pipes, sockets, ficheros)| Sencilla (variables compartidas)
Uso típico             | Ejecutar apps externas, aislamiento| Concurrencia dentro de la app

==============================================================



Daniel R.