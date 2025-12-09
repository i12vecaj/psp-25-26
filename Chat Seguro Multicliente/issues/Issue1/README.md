## 🧱 Sprint 1 – Simulación de Programación Multiproceso

### 🎯 Objetivo
Simular la ejecución de varios procesos independientes usando la clase `ProcessBuilder` en Java.

### ✅ Tareas
- [X] Crear una clase `ProcessSimulator` que ejecute 3 scripts o clases Java simultáneamente.
- [X] Medir el tiempo total de ejecución y compararlo con la ejecución secuencial.
- [X] Guardar los resultados en un archivo de log (`logs/resultados_multiproceso.txt`).
- [X] Documentar en el README diferencias entre proceso e hilo.

### 📦 Entregables
- Código funcional en carpeta `/multiproceso`.
- Archivo de log generado.
- Actualización del README con explicación técnica.

### 📅 Fecha de entrega: 2025

---

## 📁 Estructura del Proyecto

```
/multiproceso
├── SimuladorProceso.java
├── Trabajador1.java
├── Trabajador2.java
├── Trabajador3.java
├── README.md
└── logs/
    └── resultados_multiproceso.txt
```

## 🚀 Cómo Compilar y Ejecutar

### Desde terminal/consola:

```bash
cd multiproceso
javac ProcessSimulator.java Trabajador1.java Trabajador2.java Trabajador3.java
java ProcessSimulator
```

### Desde IntelliJ IDEA:

- Asegurarnos de que el proyecto está bien configurado y el working directory puesto también.
- Click derecho en `ProcessSimulator.java`
- Seleccionar "Run ProcessSimulator.main()"
- O presionar `Shift+F10`

## 📊 Salida Esperada

```
Iniciando ejecución en paralelo...
Tiempo paralelo: 2500 ms
Iniciando ejecución secuencial...
Tiempo secuencial: 6000 ms
=====================
Comparación
Tiempo paralelo: 2500 ms
Tiempo secuencial: 6000 ms
Diferencia: 3500 ms
Incremento: 2.40x
Mejora: 58.3%
Resultados guardados en logs/resultados_multiproceso.txt
```

## 🔍 Explicación del Código

### `System.getProperty("java.class.path")`

```java
String classpath = System.getProperty("java.class.path");
ProcessBuilder pb1 = new ProcessBuilder("java", "-cp", classpath, "Trabajador1");
```

**¿Para qué sirve?**
- Obtiene el classpath actual del sistema
- El classpath le dice a Java dónde buscar las clases
- `-cp` = classpath flag
- `classpath` = ruta donde están nuestras clases (generalmente ".")
- Al usar IntelliJ es MUY cómodo usar esto

**Alternativa con `pb.directory()`:**
```java
ProcessBuilder pb = new ProcessBuilder("java", "Trabajador1");
pb.directory(new File("."));  // Establece el directorio de trabajo

// o

pb.directory("Ruta/Absoluta");
```

**Diferencia:**
- `System.getProperty("java.class.path")` = Obtiene el classpath configurado
- `pb.directory(new File("."))` = Define dónde buscar archivos del proceso

---

## 🛡️ Control de Errores (Try-Catch)

El código maneja múltiples niveles de errores:

### 1. **Try-catch principal**
```java
try {
    // Todo el programa
} catch (IOException e) {
    System.out.println("Error al crear el archivo de log: " + e.getMessage());
}
```
Captura errores al crear archivos.

### 2. **Try-catch para procesos paralelos**
```java
try {
    Process p1 = pb1.start();
    // ... más procesos
    p1.waitFor();
} catch (IOException e) {
    System.out.println("Error creando procesos paralelos: " + e.getMessage());
} catch (InterruptedException e) {
    System.out.println("Error esperando procesos: " + e.getMessage());
}
```

### 3. **Try-catch para cada proceso secuencial**
```java
try {
    new ProcessBuilder("java", "-cp", classpath, "Trabajador1").start().waitFor();
} catch (IOException e) {
    // Error al ejecutar
} catch (InterruptedException e) {
    // Error al esperar
}
```

**Excepciones capturadas:**
- `IOException`: Cuando ProcessBuilder no puede ejecutar el comando
- `InterruptedException`: Cuando se interrumpe la espera (`waitFor()`)

---

## 🔹 PROCESOS vs HILOS

### 🔵 PROCESOS (ProcessBuilder)

| Característica | Detalle |
|---|---|
| **¿Qué es?** | Instancia independiente de un programa en ejecución |
| **Memoria** | Cada proceso tiene su propio heap, stack y espacios de memoria |
| **Aislamiento** | Completamente independientes del SO |
| **Si falla uno** | NO afecta a otros ✅ Máxima seguridad |
| **Creación** | Lenta (~10-100 ms) |
| **Comunicación** | Difícil (pipes, sockets, archivos) |
| **Recursos** | Alto (>1MB cada uno) |
| **Contexto** | Cambio de contexto es muy costoso |

**En Java:**
```java
ProcessBuilder pb = new ProcessBuilder("java", "Trabajador1");
Process p = pb.start();
p.waitFor();  // Esperar a que termine
```

### 🧵 HILOS (Thread)

| Característica | Detalle |
|---|---|
| **¿Qué es?** | Unidad de ejecución dentro de un proceso |
| **Memoria** | Todos comparten el mismo heap y memoria |
| **Aislamiento** | Dependientes del proceso padre |
| **Si falla uno** | PUEDE afectar todo el proceso ⚠️ |
| **Creación** | Rápida (<1ms) |
| **Comunicación** | Fácil (acceso directo a memoria compartida) |
| **Recursos** | Bajo (50-100KB cada uno) |
| **Contexto** | Cambio de contexto es más rápido |

**En Java:**
```java
Thread hilo = new Thread(() -> {
    System.out.println("Ejecutándose en un hilo");
});
hilo.start();
hilo.join();  // Esperar a que termine
```

### 📊 Comparativa Visual

```
PROCESOS                          HILOS
Proceso 1      Proceso 2          Proceso único
┌──────────┐  ┌──────────┐        ┌─────────────┐
│ Heap 1   │  │ Heap 2   │        │ Heap compartido │
│ Stack 1  │  │ Stack 2  │        ├───┬───┬────┤
│ Vars 1   │  │ Vars 2   │        │H1 │H2 │H3  │
└──────────┘  └──────────┘        └───┴───┴────┘

Aislados      Aislados           Conectados
Seguros       Seguros            Rápidos
```

### ✅ Cuándo usar PROCESOS:

**PROS:**
- ✅ Máxima seguridad y aislamiento
- ✅ Si un proceso falla, NO afecta a otros
- ✅ Ejecutas programas completamente diferentes
- ✅ Trabajas con lenguajes diferentes
- ✅ Ideal para tareas independientes

**CONTRAS:**
- ❌ Alto consumo de memoria (>1MB cada uno)
- ❌ Lentos de crear (~10-100ms)
- ❌ Comunicación entre procesos es difícil y lenta
- ❌ No es ideal para muchas tareas simultáneas

**Ejemplos de uso:**
- Ejecutar Python, Java, C++ en paralelo
- Servidores web que ejecutan scripts CGI
- Compiladores paralelos
- Renderizado de gráficos en 3D
- Análisis de datos con múltiples programas
- Microservicios independientes

**Caso de uso típico:**
```java
// Este proyecto: 3 procesos independientes
Process p1 = new ProcessBuilder("java", "-cp", classpath, "Trabajador1").start();
Process p2 = new ProcessBuilder("java", "-cp", classpath, "Trabajador2").start();
Process p3 = new ProcessBuilder("java", "-cp", classpath, "Trabajador3").start();
```

---

### 🧵 Cuándo usar HILOS:

**PROS:**
- ✅ Bajo consumo de memoria (50-100KB cada uno)
- ✅ Muy rápidos de crear (<1ms)
- ✅ Comunicación rápida (comparten memoria)
- ✅ Ideal para muchas tareas simultáneas
- ✅ Mejor aprovechamiento de CPU multinúcleo

**CONTRAS:**
- ❌ Si un hilo falla, PUEDE afectar todo el proceso
- ❌ Más difícil de sincronizar (deadlocks, race conditions)
- ❌ Requiere cuidado con acceso a memoria compartida
- ❌ Debugging más complicado

**Ejemplos de uso:**
- Interfaz gráfica responsiva (evento + hilo de trabajo)
- Servidor web que atiende múltiples clientes
- Procesamiento de eventos en tiempo real
- Animaciones y juegos
- Descarga simultánea de múltiples archivos
- Chat en tiempo real
- Servidores HTTP como Tomcat

**Caso de uso típico:**
```java
// Múltiples clientes en un servidor web
Thread cliente1 = new Thread(() -> {
    // Procesar cliente 1
});
Thread cliente2 = new Thread(() -> {
    // Procesar cliente 2
});
cliente1.start();
cliente2.start();
```

---

### 📊 Decisión Rápida: PROCESOS vs HILOS

| Pregunta | Respuesta | Usar |
|---|---|---|
| **¿Necesitas máxima seguridad?** | Sí | **PROCESOS** ✅ |
| **¿Muchas tareas pequeñas (<100)?** | Sí | **HILOS** ✅ |
| **¿Pocas tareas grandes (1-10)?** | Sí | **PROCESOS** ✅ |
| **¿Necesitan compartir datos?** | Sí | **HILOS** ✅ |
| **¿Ejecutas programas diferentes?** | Sí | **PROCESOS** ✅ |
| **¿Baja latencia importante?** | Sí | **HILOS** ✅ |
| **¿Recursos limitados?** | Sí | **HILOS** ✅ |
| **¿Poco presupuesto de CPU?** | Sí | **PROCESOS** ✅ |

---

### 🎯 Comparación de Rendimiento

```
Tarea: Procesar 10,000 elementos

OPCIÓN 1: 1 Hilo
Tiempo: 10 segundos
Memoria: ~100KB

OPCIÓN 2: 10 Hilos
Tiempo: 2 segundos (5x más rápido)
Memoria: ~1MB

OPCIÓN 3: 10 Procesos
Tiempo: 2 segundos (similar a hilos)
Memoria: ~15MB (15x más que hilos)
CONCLUSIÓN: Para esto, HILOS son mejores

---

Tarea: Ejecutar 3 programas Java independientes

OPCIÓN 1: Secuencial (1 proceso)
Tiempo: 6000ms
Riesgo: Alto (todo falla si uno falla)

OPCIÓN 2: 3 Hilos en mismo proceso
Tiempo: 2500ms
Riesgo: Alto (si uno falla, todo cae)

OPCIÓN 3: 3 Procesos (este proyecto)
Tiempo: 2500ms
Riesgo: Bajo (solo falla ese proceso)
CONCLUSIÓN: Para esto, PROCESOS son mejores
```

---

## ⚙️ Cómo funciona la ejecución paralela vs secuencial:

### Ejecución en Paralelo (2500 ms)

```
Tiempo:  0ms   500ms  1000ms 1500ms 2000ms 2500ms
         |-----|------|------|------|------|
T1:      |========== 1500ms ==========|
T2:      |============== 2000ms ==============|
T3:      |===================== 2500ms =====================|

Total: MAX(1500, 2000, 2500) = 2500ms
```

### Ejecución Secuencial (6000 ms)

```
Tiempo:  0ms   1500ms  3500ms  6000ms
         |-----|-------|-------|
T1:      |1500ms |
T2:             | 2000ms |
T3:                     | 2500ms |

Total: 1500 + 2000 + 2500 = 6000ms
```

---

## 🐛 Posible Error

### Error 1: "No se encuentra TrabajadorX" o "java.lang.ClassNotFoundException"

**Causa:** El classpath no está bien configurado.

**Solución:**

- Verifica que el `-cp` esté correcto
- En IntelliJ, asegúrate de que el Project SDK esté configurado

---

## 📝 Conclusiones

### ✅ Lo que aprendimos:

1. **ProcessBuilder es más potente que Runtime.exec():**
    - Mejor control de procesos
    - Manejo de errores más robusto
    - Configuración del directorio de trabajo
    - Redirección de streams

2. **El multiprocesamiento es eficiente:**
    - Paralelo: 2500 ms
    - Secuencial: 6000 ms
    - **Mejora: 2.40x más rápido** ✅

3. **Los procesos son independientes:**
    - Cada uno tiene su propia memoria
    - Un fallo no afecta a otros
    - Mayor seguridad, mayor carga adicional/gasto extra

4. **Los hilos son más ligeros:**
    - Menor uso de memoria
    - Comunicación más rápida
    - PERO menos seguros

5. **El control de errores es crucial:**
    - Try-catch en múltiples niveles
    - Capturar IOException e InterruptedException
    - Registrar errores en el log

### 🎯 Cuándo usar este código:

- ✅ Tareas CPU-intensivas e independientes
- ✅ Programas que necesitan máxima confiabilidad
- ✅ Ejecutar código en procesos aislados
- ✅ Demostraciones educativas de multiprocesamiento

### ❌ Cuándo NO usar este código:

- ❌ Tareas que necesitan compartir datos frecuentemente
- ❌ Gran número de tareas (mejor usar ThreadPool)
- ❌ Comunicación frecuente entre procesos
- ❌ Aplicaciones de tiempo real con latencia baja

### 🔮 Cosa que he descubierto:

- Java 19+ introduce Threads virtuales (Virtual Threads)
- Más eficientes que ProcessBuilder para muchas tareas
- Mejor balance entre seguridad y rendimiento
  - ####  **ThreadPool en lugar de ProcessBuilder**
      Para tareas más ligeras:
      ```java
      ExecutorService executor = Executors.newFixedThreadPool(3);
      executor.execute(() -> { /* tarea */ });
      ```
    (No sé como funciona aún)

---

## 📚 Referencias

- [Java ProcessBuilder Documentation](https://docs.oracle.com/javase/8/docs/api/java/lang/ProcessBuilder.html)
- [Java System Properties](https://docs.oracle.com/javase/tutorial/i18n/resbundle/propfile.html)
- [Java Threads Documentation](https://docs.oracle.com/javase/8/docs/api/java/lang/Thread.html)
- [Process Management in Java](https://docs.oracle.com/javase/tutorial/i18n/index.html)

---

**Sprint 1 Completado ✅**