# Simulación de Procesos con ProcessBuilder

Este proyecto muestra cómo ejecutar varios procesos independientes en Java usando la clase `ProcessBuilder`.

## ¿Qué hace?
- Ejecuta 3 scripts/clases Java de forma simultánea y secuencial.
- Mide el tiempo total de cada método.
- Guarda los resultados en `logs/resultados_multiproceso.txt`.

## Diferencias entre proceso e hilo
- **Proceso:** Es un programa en ejecución, con su propia memoria. Los procesos no comparten memoria entre sí.
- **Hilo:** Es una unidad de ejecución dentro de un proceso. Los hilos de un mismo proceso comparten memoria.
- Los procesos son más "pesados" y aislados, los hilos son más "ligeros" y pueden comunicarse fácilmente.

## ¿Para qué sirve?
- Entender cómo se comporta la ejecución paralela y secuencial.
- Ver la diferencia de tiempos entre procesos independientes y ejecución uno tras otro.

