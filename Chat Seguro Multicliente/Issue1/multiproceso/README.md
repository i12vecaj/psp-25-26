# Simulación de Programación Multiproceso

## Objetivo
Ejecutar tres procesos Java independientes mediante `ProcessBuilder`, medir tiempo paralelo vs. secuencial y registrar resultados en un log.

## Estructura
```
multiproceso/
  src/
    tasks/
      TaskLento.java
      TaskIO.java
      TaskCPU.java
    ProcessSimulator.java
    SequentialRunner.java
  logs/
    resultados_multiproceso.txt (generado en runtime)
```

## Compilación
Se compilan las clases dentro de `src` como destino, para que el classpath pueda apuntar directamente a `src`.
```bash
javac -d "Chat Seguro Multicliente/Issue1/multiproceso/src" \
  "Chat Seguro Multicliente/Issue1/multiproceso/src/tasks/"*.java \
  "Chat Seguro Multicliente/Issue1/multiproceso/src/"*.java
```

## Ejecución
```bash
java -cp "Chat Seguro Multicliente/Issue1/multiproceso/src" ProcessSimulator
```
El log se genera (o append) en:
```
Chat Seguro Multicliente/Issue1/multiproceso/logs/resultados_multiproceso.txt
```

## Ejemplo de salida (fragmento)
```
=== EJECUCIÓN PARALELA ===
Tiempo total paralelo: 2,700.00 ms

=== EJECUCIÓN SECUENCIAL ===
Tiempo total secuencial: 6,950.00 ms

=== RESUMEN COMPARATIVO ===
Speedup: 2.57x
Ahorro estimado: 61.17%
Fecha: 2025-10-07 10:05:00
```

## Diferencias: Proceso vs Hilo
| Aspecto | Proceso | Hilo |
|---------|---------|------|
| Aislamiento | Espacio de direcciones propio | Comparte memoria del proceso |
| Coste creación | Más alto | Más bajo |
| Comunicación | IPC (pipes, sockets, ficheros) | Memoria compartida + sincronización |
| Fallo | Crash aislado | Puede afectar a todos los hilos |
| Context switch | Más pesado | Más ligero |
| Uso típico | Programas externos, microservicios, aislamiento | Paralelismo fino interno, servidores concurrentes |
| Seguridad | Mayor (barrera de memoria) | Menor (todo accesible) |
| Sincronización | Menos frecuente | Necesaria (locks, semáforos) |

## Justificación de `ProcessBuilder`
Permite lanzar procesos realmente separados del JVM actual, capturar STDOUT/STDERR y medir el beneficio del paralelismo cuando las tareas son independientes.

## Mejoras futuras
- Parametrizar tareas (número, tipo) por argumentos.
- Exportar métricas a JSON/CSV.
- Añadir medición de CPU / memoria (JMX + OS). 
- Integrar con tests automatizados.

## Notas
- El archivo de log se sobrescribe mediante append; puedes limpiarlo manualmente.
- Para evitar versionar logs cambiantes se incluye `.gitignore`.

## Licencia
(Según aplique en el repositorio principal.)