# 📘 PSP – Unidad 2 (Teoría + Hilos + Productor–Consumidor)

## 1. INTRODUCCIÓN

Esta unidad trata sobre:
- Procesos
- Hilos (threads)
- Concurrencia
- Sincronización
- El problema Productor–Consumidor

---

## 2. PROCESOS

### ¿Qué es un proceso?
Un proceso es un programa en ejecución.

### Características:
- Tiene su propia memoria.
- Gestionado por el sistema operativo.
- Puede contener uno o varios hilos.

---

## 3. HILOS (THREADS)

### ¿Qué es un hilo?
Un hilo es una unidad de ejecución dentro de un proceso.

### Ventajas:
- Más rápidos que los procesos.
- Comparten memoria.
- Permiten multitarea.

---

## 4. DIFERENCIAS PROCESO VS HILO

| Proceso | Hilo |
|--------|------|
| Más pesado | Más ligero |
| Memoria independiente | Memoria compartida |
| Comunicación más lenta | Comunicación rápida |

---

## 5. ESTADOS DE UN HILO

- NEW → creado
- RUNNABLE → listo/ejecutándose
- BLOCKED → bloqueado
- WAITING → esperando
- TIMED_WAITING → esperando con tiempo
- TERMINATED → terminado

---

## 6. CONCURRENCIA

Capacidad de ejecutar varias tareas de manera aparente simultánea.

Ejemplo: escuchar música y navegar a la vez.

---

## 7. PROBLEMAS EN LA CONCURRENCIA

### Condición de carrera:
Varios hilos modifican un recurso compartido al mismo tiempo.

### Sección crítica:
Zona del código que solo puede ejecutar un hilo cada vez.

### Deadlock:
Dos hilos se quedan bloqueados esperando recursos.

---

## 8. SINCRONIZACIÓN

### Palabra clave: `synchronized`

Garantiza que solo un hilo ejecuta el método o bloque.

Ejemplo:

```java
public synchronized void metodoSeguro() {
    // código protegido
}

---

## 9. CREACIÓN DE HILOS EN JAVA

Para crear hilos tenemos 2 formas hacerlo extendiendo de la clase Thread o implementando Runnable

Con la clase Thread seria creando una clase que extienda de esa clase haciendo sus metodos y el metodo run , luego solo reia crear los hilos de esta manera Threar t1 = new Thread ().... t1.start();

Mientras que con la interfaz seria se crea una interfaz con los nombres de las funciones que se van hacer y los daos que necesitan y luego en otra clase que seria la Main es decir donde se va a ejcutar  se tiene que implementar la clase Runnable y luego se pone los metodos que estan en la interfaz pero con el codigo(funcionando) y luego por ultimo para crear el hilo es con Thread t1 = new Thread(new Main(si necesita datos porque tiene constrauctor si no no)) y t1.start.


---

## 10. MÉTODOS IMPORTANTES DE LOS HILOS

Método	        Función

start()	        Inicia el hilo
run()	        Código que ejecuta
sleep(ms)	    Duerme el hilo
join()	        Esperar otro hilo
interrupt()	    Interrumpir hilo
isAlive()	    Saber si sigue activo
yield()	        Ceder turno

---

## 11. COMUNICACIÓN ENTRE HILOS

Estos métodos se usan para que los hilos se comuniquen entre ellos cuando comparten recursos.

⚠️ Solo pueden usarse dentro de métodos o bloques `synchronized`.

### Métodos:

#### `wait()`
Sirve para que **un hilo se quede en pausa** hasta que otro hilo lo despierte.  
Libera el monitor (el bloqueo del objeto) mientras está esperando.

Se usa cuando un hilo **no puede continuar** porque falta una condición (por ejemplo, buffer vacío).

#### `notify()`
Sirve para **despertar a un hilo** que estaba esperando con `wait()`.  
Despierta **solo a uno** de los hilos en espera.

Se usa cuando un hilo ha cumplido la condición necesaria.

#### `notifyAll()`
Sirve para **despertar a todos los hilos** que están esperando.  
Todos pasan de estado `WAITING` a `RUNNABLE`, pero solo uno podrá entrar primero en la sección sincronizada.

Se usa cuando hay varios hilos esperando y no se sabe cuál debería continuar.

---

### Resumen rápido

- `wait()` → el hilo se duerme y libera el bloqueo
- `notify()` → despierta a un único hilo
- `notifyAll()` → despierta a todos los hilos



---

## 12. PROBLEMA PRODUCTOR – CONSUMIDOR
Descripción:

Un productor genera datos y un consumidor los consume usando un buffer compartido.

Reglas:

Si el buffer está lleno → el productor espera.

Si está vacío → el consumidor espera.


## 13. IMPLEMENTACIÓN COMPLETA (PRODUCTOR – CONSUMIDOR)

---

### 🧱 Clase `Buffer`

Esta clase es el **recurso compartido** entre el productor y el consumidor.  
Contiene el dato y controla cuándo se puede producir o consumir.

```java
class Buffer {
    private int dato; // Almacena el valor producido
    private boolean disponible = false; // Indica si hay un dato disponible

    // Método para que el productor guarde un valor
    public synchronized void producir(int valor) {
        // Si ya hay un dato sin consumir, el productor espera
        while (disponible) {
            try { 
                wait(); // El hilo se duerme hasta que lo despierten
            } catch (InterruptedException e) {}
        }

        // Guarda el valor en el buffer
        dato = valor;
        disponible = true; // Marca que hay dato disponible

        // Despierta al consumidor
        notify();
    }

    // Método para que el consumidor lea el valor
    public synchronized int consumir() {
        // Si no hay datos disponibles, el consumidor espera
        while (!disponible) {
            try { 
                wait(); // El hilo se duerme hasta que haya datos
            } catch (InterruptedException e) {}
        }

        // Marca que ya no hay dato disponible
        disponible = false;

        // Despierta al productor
        notify();

        // Devuelve el dato consumido
        return dato;
    }
}

🏭 Clase Productor

Se encarga de generar datos y guardarlos en el buffer.

class Productor extends Thread {
    private Buffer buffer; // Referencia al buffer compartido

    public Productor(Buffer b) {
        buffer = b;
    }

    public void run() {
        // Produce 10 valores
        for (int i = 0; i < 10; i++) {
            buffer.producir(i); // Envía el valor al buffer
            System.out.println("Producido: " + i);
        }
    }
}

🧑‍🍳 Clase Consumidor

Se encarga de recibir y usar los datos generados por el productor.

class Consumidor extends Thread {
    private Buffer buffer; // Referencia al buffer compartido

    public Consumidor(Buffer b) {
        buffer = b;
    }

    public void run() {
        // Consume 10 valores
        for (int i = 0; i < 10; i++) {
            int valor = buffer.consumir(); // Obtiene el dato del buffer
            System.out.println("Consumido: " + valor);
        }
    }
}

▶️ Clase Main

Es el punto de inicio del programa.
Crea el buffer y lanza los hilos.

public class Main {
    public static void main(String[] args) {
        Buffer b = new Buffer(); // Crear buffer compartido

        // Crear y arrancar los hilos
        new Productor(b).start();
        new Consumidor(b).start();
    }
}
