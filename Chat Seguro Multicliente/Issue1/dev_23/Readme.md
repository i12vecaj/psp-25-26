# Simulación de Ejecución Concurrente de Procesos

Este proyecto compara el tiempo de ejecución **secuencial** frente al **paralelo** de tres programas Java independientes (`Script1`, `Script2`, `Script3`) que simulan tareas intensivas en cálculo de duración variable.

## Los resultados:

Los resultados de la simulación de tiempo se guardan automáticamente en: `logs/resultados_multiproceso.txt`.

## Diferencias Clave: Proceso vs. Hilo

| Característica | Proceso | Hilo |
| :--- | :--- | :--- |
| **Definición** | Una instancia de un programa en ejecución con sus propios recursos. | Una unidad de ejecución dentro de un proceso. |
| **Memoria** | Posee su **propio espacio de memoria** totalmente aislado. | **Comparte el espacio de memoria** de su proceso padre. |
| **Aislamiento** | **Alto aislamiento**. El fallo de un proceso no afecta a los demás. | **Bajo aislamiento**. El fallo de un hilo puede afectar y detener todo el proceso. |
| **Creación** | Más costosa y lenta, ya que requiere la asignación de recursos del sistema operativo. | Menos costosa y más rápida. |
| **Velocidad de Procesamiento** | **Menor eficiencia en comunicación**. El cambio de contexto entre procesos es más pesado para el SO. | **Mayor rendimiento en comunicación**. El cambio de contexto es más ligero, ya que el espacio de memoria es compartido. |
