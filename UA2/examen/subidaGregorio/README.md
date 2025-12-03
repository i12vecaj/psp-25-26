¡Perfecto! He creado un README completo de repaso con todos los conceptos de programación multihilo del examen.
📚 Contenido del documento:
Incluye:

    Conceptos básicos de Threads - Creación, métodos importantes
    Sincronización - synchronized, wait(), notify(), notifyAll()
    Problemas de concurrencia - Race conditions, deadlock, starvation
    Patrones comunes - Productor-consumidor, pool de recursos
    Métodos clave - sleep(), join(), interrupt(), Random
    Documentación Doxygen - Todos los tags y ejemplos
    Checklist completo de examen - Qué verificar antes de entregar
    Plantillas de código - Para Thread, Clase compartida y Main
    Errores comunes - Qué NO hacer
    Fórmulas rápidas - Tiempos aleatorios, formato de números
    Resumen ultra-rápido - Para repasar 5 minutos antes del examen

El documento está diseñado para:

    ✅ Acceso rápido con índice y tablas
    ✅ Ejemplos prácticos de cada concepto
    ✅ Código copiable listo para usar
    ✅ Marcadores visuales (✅ ❌ 📚 🎯) para lectura rápida

¡Este documento será tu mejor aliado para futuros exámenes de PSP! 🚀
Make these code changes?

UA2/examen/REPASO_CONCEPTOS_MULTIHILO.md
md

# 📚 REPASO CONCEPTOS - PROGRAMACIÓN MULTIHILO (PSP - UA2)

> **Documento de referencia rápida para exámenes de Programación de Servicios y Procesos**
>
> Conceptos clave del examen de Programación Multihilo en Java

---

## 📑 ÍNDICE RÁPIDO

1. [Conceptos Básicos de Threads](#1-conceptos-básicos-de-threads)
2. [Sincronización](#2-sincronización)
3. [Problemas de Concurrencia](#3-problemas-de-concurrencia)
4. [Patrones Comunes](#4-patrones-comunes)
5. [Métodos Clave](#5-métodos-clave)
6. [Documentación Doxygen](#6-documentación-doxygen)
7. [Checklist de Examen](#7-checklist-de-examen)

---

## 1. CONCEPTOS BÁSICOS DE THREADS

### 1.1 ¿Qué es un Thread?

- **Thread (hilo)**: Unidad básica de ejecución dentro de un proceso
- Permite ejecutar múltiples tareas concurrentemente
- Comparten memoria del proceso principal (variables globales, objetos)

### 1.2 Crear un Thread en Java

**Opción 1: Extender la clase Thread**

```java
class MiHilo extends Thread {
    @Override
    public void run() {
        // Código que ejecutará el hilo
        System.out.println("Hilo ejecutándose: " + getName());
    }
}

// Uso
MiHilo hilo = new MiHilo();
hilo.start(); // NO usar run() directamente

Opción 2: Implementar Runnable
Java

class MiTarea implements Runnable {
    @Override
    public void run() {
        // Código de la tarea
    }
}

// Uso
Thread hilo = new Thread(new MiTarea());
hilo.start();

1.3 Métodos Importantes del Thread
Método	Descripción	Uso
start()	Inicia la ejecución del hilo	hilo.start()
run()	Método que contiene el código del hilo	Sobrescribir, NO llamar directamente
sleep(ms)	Pausa el hilo por X milisegundos	Thread.sleep(1000)
join()	Espera a que el hilo termine	hilo.join()
interrupt()	Interrumpe el hilo	hilo.interrupt()
getName()	Obtiene el nombre del hilo	String nombre = getName()
setName(String)	Establece el nombre del hilo	setName("Cliente1")
2. SINCRONIZACIÓN
2.1 ¿Por qué sincronizar?

Problema sin sincronización:
Java

// ❌ PELIGRO: Condición de carrera (Race Condition)
class Contador {
    private int valor = 0;

    public void incrementar() {
        valor++; // NO es atómico (lectura + incremento + escritura)
    }
}

Múltiples hilos pueden:

    Leer el mismo valor simultáneamente
    Sobrescribir cambios de otros hilos
    Generar resultados inconsistentes

2.2 Palabra clave synchronized

En métodos:
Java

class Contador {
    private int valor = 0;

    // ✅ Solo un hilo puede ejecutar este método a la vez
    public synchronized void incrementar() {
        valor++;
    }
}

En bloques:
Java

class Contador {
    private int valor = 0;
    private Object lock = new Object();

    public void incrementar() {
        synchronized(lock) {
            valor++;
        }
    }
}

2.3 Wait, Notify y NotifyAll

Patrón Productor-Consumidor:
Java

class Buffer {
    private List<Item> items = new ArrayList<>();
    private final int MAX = 10;

    // Productor
    public synchronized void agregar(Item item) throws InterruptedException {
        while (items.size() >= MAX) {
            wait(); // Esperar si está lleno
        }
        items.add(item);
        notifyAll(); // Avisar a consumidores
    }

    // Consumidor
    public synchronized Item quitar() throws InterruptedException {
        while (items.isEmpty()) {
            wait(); // Esperar si está vacío
        }
        Item item = items.remove(0);
        notifyAll(); // Avisar a productores
        return item;
    }
}

Método	Descripción	Cuándo usar
wait()	Libera el lock y espera	Cuando falta un recurso
notify()	Despierta UN hilo en espera	Cuando hay 1 recurso disponible
notifyAll()	Despierta TODOS los hilos en espera	Recomendado (más seguro)

⚠️ IMPORTANTE: wait(), notify() y notifyAll() deben estar dentro de un bloque synchronized
3. PROBLEMAS DE CONCURRENCIA
3.1 Condición de Carrera (Race Condition)

    Problema: Múltiples hilos acceden/modifican datos compartidos
    Síntoma: Resultados inconsistentes o impredecibles
    Solución: Usar synchronized

3.2 Deadlock (Bloqueo Mutuo)
Java

// ❌ DEADLOCK: Cada hilo espera el recurso del otro
synchronized(recurso1) {
    synchronized(recurso2) {
        // ...
    }
}

// Otro hilo hace:
synchronized(recurso2) {
    synchronized(recurso1) { // ¡DEADLOCK!
        // ...
    }
}

Solución: Adquirir locks en el mismo orden
3.3 Starvation (Inanición)

    Un hilo nunca obtiene acceso al recurso
    Otros hilos lo monopolizan continuamente

3.4 Livelock

    Hilos cambian de estado en respuesta a otros
    Ninguno progresa

4. PATRONES COMUNES
4.1 Productor-Consumidor
Java

class Buffer {
    private Queue<Item> cola = new LinkedList<>();
    private final int capacidad;

    public synchronized void producir(Item item) throws InterruptedException {
        while (cola.size() >= capacidad) {
            wait(); // Buffer lleno, esperar
        }
        cola.add(item);
        notifyAll(); // Notificar a consumidores
    }

    public synchronized Item consumir() throws InterruptedException {
        while (cola.isEmpty()) {
            wait(); // Buffer vacío, esperar
        }
        Item item = cola.poll();
        notifyAll(); // Notificar a productores
        return item;
    }
}

4.2 Pool de Recursos Compartidos
Java

class PoolRecursos {
    private List<Recurso> disponibles = new ArrayList<>();

    public synchronized Recurso obtener() throws InterruptedException {
        while (disponibles.isEmpty()) {
            wait(); // Esperar recurso disponible
        }
        return disponibles.remove(0);
    }

    public synchronized void devolver(Recurso r) {
        disponibles.add(r);
        notifyAll(); // Avisar que hay recurso disponible
    }
}

4.3 Contador de Tareas Completadas
Java

class ContadorTareas {
    private int completadas = 0;

    public synchronized void incrementar() {
        completadas++;
        System.out.println("Tareas completadas: " + completadas);
    }

    public synchronized int getCompletadas() {
        return completadas;
    }
}

5. MÉTODOS CLAVE
5.1 Thread.sleep()
Java

// Pausar el hilo actual por X milisegundos
try {
    Thread.sleep(1000); // 1 segundo
} catch (InterruptedException e) {
    System.err.println("Hilo interrumpido");
}

5.2 Thread.join()
Java

// Esperar a que un hilo termine
Thread hilo = new Thread(() -> {
    // Tarea larga
});
hilo.start();
hilo.join(); // Esperar hasta que termine
System.out.println("Hilo ha terminado");

5.3 Thread.interrupt()
Java

Thread hilo = new Thread(() -> {
    while (!Thread.interrupted()) { // Verificar interrupción
        // Trabajar
    }
});
hilo.start();
Thread.sleep(5000);
hilo.interrupt(); // Enviar señal de interrupción

5.4 Random en multihilo
Java

import java.util.Random;

class MiHilo extends Thread {
    private Random random = new Random();

    public void run() {
        // Entero aleatorio entre 0 y 9
        int num = random.nextInt(10);

        // Entero entre 250 y 1000
        int espera = random.nextInt(751) + 250;

        // Booleano aleatorio
        boolean flag = random.nextBoolean();
    }
}

6. DOCUMENTACIÓN DOXYGEN
6.1 Estructura Básica

Archivo:
Java

/**
 * @file NombreArchivo.java
 * @brief Descripción breve del archivo
 * @details Descripción detallada de lo que contiene
 * @author Tu Nombre
 * @date 2025-12-03
 */

Clase:
Java

/**
 * @class NombreClase
 * @brief Descripción breve de la clase
 * @details Descripción detallada de su propósito y funcionamiento
 */
public class NombreClase {
    // ...
}

Atributo:
Java

/** Descripción del atributo */
private int miAtributo;

/** Contador de instancias creadas */
private static int contador = 0;

Método:
Java

/**
 * @brief Descripción breve del método
 * @details Explicación detallada de qué hace
 * @param parametro1 Descripción del primer parámetro
 * @param parametro2 Descripción del segundo parámetro
 * @return Descripción de lo que retorna
 * @throws ExcepcionTipo Cuándo se lanza esta excepción
 */
public int miMetodo(String parametro1, int parametro2) throws ExcepcionTipo {
    // ...
}

Constructor:
Java

/**
 * @brief Constructor de la clase
 * @param nombre Nombre inicial
 * @param edad Edad inicial
 */
public Persona(String nombre, int edad) {
    this.nombre = nombre;
    this.edad = edad;
}

6.2 Tags Importantes
Tag	Uso	Ejemplo
@file	Documentar archivo	@file App.java
@class	Documentar clase	@class Camarero
@brief	Descripción breve	@brief Sirve cervezas
@details	Descripción detallada	@details Gestiona pool de vasos...
@param	Parámetro de método	@param nombre Nombre del cliente
@return	Valor de retorno	@return Vaso de cerveza
@throws	Excepción lanzada	@throws InterruptedException
@author	Autor del código	@author GregorioRuiz98
@date	Fecha	@date 2025-12-03
7. CHECKLIST DE EXAMEN
✅ Antes de empezar

    Leer TODO el examen completo
    Identificar clases necesarias
    Identificar recursos compartidos
    Identificar qué métodos necesitan sincronización
    Planificar la estructura (no empezar a codificar inmediatamente)

✅ Durante la implementación

Threads:

    ¿Extiendes Thread o implementas Runnable?
    ¿Llamas a start() (no run()) para iniciar hilos?
    ¿Asignas nombre al hilo en el constructor? (setName() o super(nombre))
    ¿Usas Thread.sleep() para simular tiempos?
    ¿Capturas InterruptedException?

Sincronización:

    ¿Los métodos que acceden a recursos compartidos son synchronized?
    ¿Usas wait() cuando no hay recursos disponibles?
    ¿Usas notifyAll() (preferible) o notify() cuando hay recursos?
    ¿Están wait() y notifyAll() dentro de métodos synchronized?
    ¿Usas while (no if) para verificar condiciones antes de wait()?

Manejo de errores:

    ¿Tienes bloques try-catch para InterruptedException?
    ¿Capturas excepciones genéricas en el run()?
    ¿Validas parámetros (null, rangos)?
    ¿Imprimes mensajes de error descriptivos?

Impresiones por pantalla:

    ¿Todos los métodos imprimen su estado?
    ¿Incluyes el nombre del hilo en las impresiones?
    ¿Usas prefijos como [Clase] para identificar origen?
    ¿Las impresiones son claras y descriptivas?

Documentación Doxygen:

    ¿Tienes el tag @file al inicio?
    ¿Todas las clases tienen @class y @brief?
    ¿Todos los métodos tienen @brief y @param/@return?
    ¿Los atributos tienen comentarios descriptivos?
    ¿Incluyes @author y @date?

Estructura del código:

    ¿Está todo en un solo archivo si lo piden?
    ¿Las clases están en el orden lógico?
    ¿El código está indentado correctamente?
    ¿Los nombres de variables son descriptivos?

Main (Aplicación):

    ¿Creas el objeto compartido primero?
    ¿Pasas el objeto compartido a todos los hilos?
    ¿Llamas a start() en todos los hilos?
    ¿Tienes un mecanismo para detener la simulación?
    ¿Muestras estadísticas finales?

8. CÓDIGO DE PLANTILLA RÁPIDA
Plantilla Clase Thread Básica
Java

/**
 * @class MiHilo
 * @brief Descripción breve
 */
class MiHilo extends Thread {
    private ObjetoCompartido compartido;
    private int contador = 0;
    private Random random = new Random();

    /**
     * @brief Constructor
     * @param nombre Nombre del hilo
     * @param compartido Objeto compartido
     */
    public MiHilo(String nombre, ObjetoCompartido compartido) {
        super(nombre);
        this.compartido = compartido;
        System.out.println("[MiHilo] " + nombre + " creado");
    }

    /**
     * @brief Método principal de ejecución
     */
    @Override
    public void run() {
        try {
            System.out.println("[MiHilo] " + getName() + " iniciado");

            while (!Thread.interrupted()) {
                // Hacer algo
                compartido.operar();

                // Esperar tiempo aleatorio
                Thread.sleep(random.nextInt(751) + 250);
                contador++;
            }

        } catch (InterruptedException e) {
            System.err.println("[MiHilo] " + getName() + " interrumpido");
        } catch (Exception e) {
            System.err.println("[ERROR] " + getName() + ": " + e.getMessage());
        } finally {
            System.out.println("[MiHilo] " + getName() + " finalizado. Operaciones: " + contador);
        }
    }
}

Plantilla Clase Compartida
Java

/**
 * @class ObjetoCompartido
 * @brief Recurso compartido entre hilos
 */
class ObjetoCompartido {
    private List<Recurso> recursos = new ArrayList<>();
    private Random random = new Random();

    /**
     * @brief Constructor
     */
    public ObjetoCompartido() {
        // Inicializar recursos
        for (int i = 0; i < 3; i++) {
            recursos.add(new Recurso(i));
        }
        System.out.println("[ObjetoCompartido] Inicializado con " + recursos.size() + " recursos");
    }

    /**
     * @brief Obtiene un recurso
     * @return Recurso obtenido
     * @throws InterruptedException Si es interrumpido
     */
    public synchronized Recurso obtener() throws InterruptedException {
        while (recursos.isEmpty()) {
            System.out.println("[ObjetoCompartido] Sin recursos, esperando...");
            wait();
        }

        Recurso r = recursos.remove(random.nextInt(recursos.size()));
        System.out.println("[ObjetoCompartido] Recurso entregado (disponibles: " + recursos.size() + ")");
        return r;
    }

    /**
     * @brief Devuelve un recurso
     * @param recurso Recurso a devolver
     */
    public synchronized void devolver(Recurso recurso) {
        if (recurso == null) {
            throw new IllegalArgumentException("Recurso no puede ser null");
        }

        recursos.add(recurso);
        System.out.println("[ObjetoCompartido] Recurso devuelto (disponibles: " + recursos.size() + ")");
        notifyAll();
    }
}

Plantilla Main
Java

/**
 * @class App
 * @brief Aplicación principal
 */
public class App {
    /**
     * @brief Método principal
     * @param args Argumentos de línea de comandos
     */
    public static void main(String[] args) {
        System.out.println("=== INICIO SIMULACIÓN ===");

        try {
            // 1. Crear objeto compartido
            ObjetoCompartido compartido = new ObjetoCompartido();

            // 2. Crear hilos
            MiHilo hilo1 = new MiHilo("Hilo-1", compartido);
            MiHilo hilo2 = new MiHilo("Hilo-2", compartido);
            MiHilo hilo3 = new MiHilo("Hilo-3", compartido);

            // 3. Iniciar hilos
            hilo1.start();
            hilo2.start();
            hilo3.start();

            // 4. Dejar ejecutar
            Thread.sleep(10000); // 10 segundos

            // 5. Detener hilos
            System.out.println("=== DETENIENDO ===");
            hilo1.interrupt();
            hilo2.interrupt();
            hilo3.interrupt();

            // 6. Esperar terminación
            hilo1.join(1000);
            hilo2.join(1000);
            hilo3.join(1000);

            System.out.println("=== FIN SIMULACIÓN ===");

        } catch (Exception e) {
            System.err.println("[ERROR FATAL] " + e.getMessage());
            e.printStackTrace();
        }
    }
}

9. ERRORES COMUNES A EVITAR
❌ Llamar a run() en lugar de start()
Java

MiHilo hilo = new MiHilo();
hilo.run(); // ❌ Se ejecuta en el hilo principal
hilo.start(); // ✅ Se ejecuta en un nuevo hilo

❌ Olvidar synchronized
Java

// ❌ Sin sincronización = race condition
public void modificar() {
    contador++;
}

// ✅ Con sincronización
public synchronized void modificar() {
    contador++;
}

❌ Usar if en lugar de while con wait()
Java

// ❌ Puede despertar sin que se cumpla la condición
public synchronized void consumir() throws InterruptedException {
    if (cola.isEmpty()) { // ❌
        wait();
    }
    // ...
}

// ✅ Verifica la condición después de despertar
public synchronized void consumir() throws InterruptedException {
    while (cola.isEmpty()) { // ✅
        wait();
    }
    // ...
}

❌ No capturar InterruptedException
Java

// ❌ No compila
public void run() {
    Thread.sleep(1000);
}

// ✅ Captura la excepción
public void run() {
    try {
        Thread.sleep(1000);
    } catch (InterruptedException e) {
        System.err.println("Interrumpido");
    }
}

❌ No usar notifyAll() después de modificar
Java

// ❌ Otros hilos no se despiertan
public synchronized void agregar(Item item) {
    items.add(item);
    // Falta notifyAll()
}

// ✅ Notifica a hilos en espera
public synchronized void agregar(Item item) {
    items.add(item);
    notifyAll(); // ✅
}

10. FÓRMULAS RÁPIDAS
Tiempo aleatorio entre MIN y MAX milisegundos
Java

int min = 250;
int max = 1000;
int tiempoAleatorio = random.nextInt(max - min + 1) + min;

Número aleatorio entre 0 y N-1
Java

int numero = random.nextInt(N);

Elegir elemento aleatorio de una lista
Java

int indice = random.nextInt(lista.size());
Elemento elem = lista.get(indice);
// o
Elemento elem = lista.remove(indice); // Si lo quieres sacar

Formatear números decimales
Java

double valor = 1.234567;
String formateado = String.format("%.3f", valor); // "1.235"

11. RECURSOS ADICIONALES
Documentación Oficial

    Java Thread Tutorial
    Doxygen para Java

Conceptos clave para estudiar más

    Volatile: Variables que se leen/escriben directamente de memoria
    AtomicInteger: Contadores thread-safe sin synchronized
    ReentrantLock: Alternativa más flexible a synchronized
    ExecutorService: Pool de hilos gestionado
    Semaphore: Controlar acceso con múltiples permisos

📌 RESUMEN ULTRA-RÁPIDO (5 minutos antes del examen)

    Thread: Extender Thread, sobrescribir run(), llamar start()
    Synchronized: Métodos que modifican recursos compartidos
    Wait/Notify: wait() cuando falta recurso, notifyAll() cuando hay
    Try-Catch: Siempre capturar InterruptedException
    Random: random.nextInt(max - min + 1) + min
    Doxygen: @file, @class, @brief, @param, @return
    Impresiones: En TODOS los métodos con nombre de hilo
    Main: Crear compartido → Crear hilos → start() → sleep() → interrupt()
```
