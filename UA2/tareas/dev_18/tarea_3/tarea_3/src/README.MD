
**FR3 – Comparación de tiempos**

En la versión secuencial (FR1), los dos ficheros se han leído uno después de otro. Los tiempos fueron:

La Metamorfosis: 41 ms
El Quijote: 101 ms

En la versión concurrente (FR2), cada fichero se procesa en su propio hilo. Los tiempos fueron:

La Metamorfosis: 24 ms
El Quijote: 78 ms

Resultado: la ejecución concurrente reduce el tiempo global.
