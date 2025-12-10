# Parte 1: Enfoque en Hilos (Java Threads)

### Ejercicio 1: Interrupción Segura de un Hilo

**Tema:** Gestión del ciclo de vida, evitar el método obsoleto `stop()` y uso de `interrupt()`.

#### Enunciado

Crea un hilo llamado "Reloj" que imprima la hora actual cada segundo en un bucle infinito. En el `main`, deja que el reloj funcione durante 3 segundos y luego detenlo.

- **Requisito:** No uses el método `stop()` (está obsoleto). Debes usar el mecanismo de interrupción y capturar la excepción `InterruptedException`.

#### Resolución

```java
class Reloj implements Runnable {
    @Override
    public void run() {
        // Mantenemos el bucle mientras el hilo no sea interrumpido
        while (!Thread.currentThread().isInterrupted()) {
            try {
                System.out.println("Tic-Tac: " + java.time.LocalTime.now());
                Thread.sleep(1000); // Pausa de 1 segundo
            } catch (InterruptedException e) {
                // Al capturar la excepción, salimos del bucle para terminar limpiamente
                System.out.println("Reloj detenido por interrupción.");
                return;
            }
        }
    }
}

public class MainInterrupcion {
    public static void main(String[] args) {
        Thread hiloReloj = new Thread(new Reloj());
        hiloReloj.start();

        try {
            Thread.sleep(3500); // El main espera 3.5 segundos
        } catch (InterruptedException e) { e.printStackTrace(); }

        hiloReloj.interrupt(); // Solicitamos la interrupción de forma segura
    }
}
```

**Concepto clave:** El método `stop()` podría provocar interbloqueos y es inseguro. Lo correcto es lanzar una interrupción con `interrupt()` y gestionarla dentro del `run()`.

---

### Ejercicio 2: Prioridades y `yield()`

**Tema:** Planificador de hilos, prioridades y cesión de CPU.

#### Enunciado

Crea dos hilos: "HiloPrioritario" (Prioridad Máxima) e "HiloHumilde" (Prioridad Mínima).

1.  Ambos deben realizar un conteo intensivo (por ejemplo, contar hasta 1.000.000).
2.  El "HiloHumilde" debe invocar `Thread.yield()` en cada iteración de su bucle.
3.  Asigna las prioridades usando las constantes de la clase `Thread`.

#### Resolución

```java
class Contador implements Runnable {
    private String nombre;
    private boolean cederPaso;

    public Contador(String nombre, boolean cederPaso) {
        this.nombre = nombre;
        this.cederPaso = cederPaso;
    }

    @Override
    public void run() {
        for (int i = 0; i < 1000000; i++) {
            if (cederPaso) {
                Thread.yield(); // Sugiere ceder el procesador
            }
        }
        System.out.println(nombre + " ha terminado.");
    }
}

public class MainPrioridades {
    public static void main(String[] args) {
        Thread h1 = new Thread(new Contador("HiloPrioritario", false));
        Thread h2 = new Thread(new Contador("HiloHumilde", true));

        // Asignación de prioridades (1 a 10)
        h1.setPriority(Thread.MAX_PRIORITY); // 10
        h2.setPriority(Thread.MIN_PRIORITY); // 1

        h1.start();
        h2.start();
    }
}
```

**Concepto clave:** `yield()` sugiere al planificador que ceda el uso del procesador a otros hilos. Sin embargo, las prioridades no garantizan el orden de ejecución, ya que depende de la plataforma y la carga del sistema.

---

### Ejercicio 3: Sincronización de Bloques (Cuenta Bancaria)

**Tema:** Exclusión mutua, `synchronized` y memoria compartida.

#### Enunciado

Simula una cuenta bancaria compartida con un saldo inicial de 1000.

1.  Crea dos hilos que intenten retirar dinero simultáneamente (por ejemplo, 10 retiros de 100 cada uno).
2.  Utiliza un bloque `synchronized` (o método sincronizado) para evitar que el saldo sea inconsistente (Condición de carrera).
3.  Imprime el saldo final, que debería ser 0 si la sincronización funciona.

#### Resolución

```java
class CuentaBancaria {
    private int saldo = 1000;

    public synchronized void retirar(int cantidad) { // Método sincronizado
        if (saldo >= cantidad) {
            System.out.println(Thread.currentThread().getName() + " va a retirar.");
            saldo -= cantidad;
            System.out.println("Retiro completado. Saldo actual: " + saldo);
        } else {
            System.out.println("Saldo insuficiente para " + Thread.currentThread().getName());
        }
    }
}

class Cliente implements Runnable {
    private CuentaBancaria cuenta;
    public Cliente(CuentaBancaria cuenta) { this.cuenta = cuenta; }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            cuenta.retirar(100); // Sección crítica protegida por el método synchronized
            try { Thread.sleep(100); } catch (InterruptedException e) {}
        }
    }
}
// En el main crearías 1 instancia de CuentaBancaria y 2 hilos compartiéndola.
```

**Concepto clave:** Sin `synchronized`, varios hilos podrían leer el mismo valor de saldo antes de que se actualice, causando corrupción de datos. El bloque sincronizado garantiza la atomicidad.

---

### Ejercicio 4: Monitor Complejo (El Parking)

**Tema:** `wait()`, `notifyAll()` y gestión de recursos limitados.

#### Enunciado

Implementa un control de acceso para un Parking con capacidad para **3 coches**.

1.  Los coches (hilos) intentan `entrar()`. Si el parking está lleno, deben esperar (`wait`).
2.  Cuando un coche sale (`salir()`), debe notificar a los que esperan (`notifyAll`).
3.  Muestra mensajes: "Coche X entrando. Plazas libres: Y".

#### Resolución

```java
class Parking {
    private int plazasLibres = 3;

    public synchronized void entrar() {
        while (plazasLibres == 0) { // Comprobación en bucle
            try {
                System.out.println(Thread.currentThread().getName() + " espera plaza.");
                wait(); // Se duerme y libera el cerrojo
            } catch (InterruptedException e) { e.printStackTrace(); }
        }
        plazasLibres--;
        System.out.println(Thread.currentThread().getName() + " entra. Plazas: " + plazasLibres);
    }

    public synchronized void salir() {
        plazasLibres++;
        System.out.println(Thread.currentThread().getName() + " sale. Plazas: " + plazasLibres);
        notifyAll(); // Despierta a todos los coches esperando
    }
}
```

**Concepto clave:** Se usa `notifyAll()` para evitar despertar al hilo incorrecto. Es vital usar `while` en lugar de `if` al verificar la condición antes del `wait` para protegerse de despertares espurios.

---

### Ejercicio 5: Diagnóstico de Hilos (`isAlive` y `getState`)

**Tema:** Estados del hilo e información de depuración.

#### Enunciado

Crea un hilo que realice una tarea larga (dormir 2 segundos). Desde el hilo `main`:

1.  Imprime su estado (`getState()`) antes de iniciarlo (`NEW`).
2.  Imprime su estado justo después de iniciarlo (`RUNNABLE`).
3.  Usa `isAlive()` para confirmar que sigue vivo mientras esperas a que termine.

#### Resolución

```java
public class DiagnosticoHilo {
    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(() -> {
            try { Thread.sleep(2000); } catch (InterruptedException e) {}
        });

        System.out.println("Estado antes de start: " + t.getState()); // Imprime NEW

        t.start();
        System.out.println("Estado tras start: " + t.getState()); // Imprime RUNNABLE
        System.out.println("¿Está vivo?: " + t.isAlive());

        while (t.isAlive()) { // Espera activa (solo para demostración)
            System.out.println("Esperando a que termine... Estado: " + t.getState());
            Thread.sleep(500); // El hilo estará en TIMED_WAITING o BLOCKED durante su sleep
        }

        System.out.println("Hilo terminado. ¿Vivo?: " + t.isAlive()); // false
    }
}
```

**Concepto clave:** Un hilo pasa por estados como New, Runnable, Blocked y Dead. `isAlive()` devuelve true si el hilo ha iniciado y no ha muerto.

---

## Parte 2: Procesos y Spring Boot

### Ejercicio 6: Variables de Entorno con ProcessBuilder

**Tema:** Entorno del proceso y configuración.

#### Enunciado

Crea un programa que lance un proceso hijo (puede ser otro programa Java o un script).

1.  Antes de lanzarlo, modifica su **entorno** usando `ProcessBuilder.environment()`.
2.  Añade una variable personalizada clave-valor: `"MI_CLAVE" = "HolaDesdeElPadre"`.
3.  Haz que el proceso hijo imprima esa variable (si es Java, usando `System.getenv("MI_CLAVE")`).

#### Resolución

```java
ProcessBuilder pb = new ProcessBuilder("java", "ClaseHija");
// Accedemos al mapa de entorno
java.util.Map<String, String> env = pb.environment();
env.put("MI_CLAVE", "HolaDesdeElPadre");

// Al hacer pb.start(), el proceso hijo tendrá acceso a esta variable.
```

**Concepto clave:** El entorno es una asignación de variables dependiente del sistema. `ProcessBuilder` permite modificar este mapa antes de crear el proceso.

---

### Ejercicio 7: Comunicación Padre-Hijo (OutputStream)

**Tema:** Escribir en la entrada estándar (`stdin`) de un subproceso.

#### Enunciado

Lanza un proceso que espere entrada del usuario (por ejemplo, un programa Java que lea un `Scanner` o el comando `grep` en Linux). Desde el proceso padre (Java), obtén el `OutputStream` y envíale datos simulando que un usuario escribe por teclado.

#### Resolución

```java
ProcessBuilder pb = new ProcessBuilder("grep", "java"); // Filtra líneas con "java"
Process p = pb.start();

// Obtenemos el flujo para "escribirle" al proceso
try (java.io.OutputStream os = p.getOutputStream();
     java.io.PrintWriter writer = new java.io.PrintWriter(os)) {
    writer.println("texto de ejemplo");
    writer.println("esta linea tiene java");
    writer.println("fin");
} // Al cerrar el try, se cierra el flujo y grep procesa
```

**Concepto clave:** El código Java escribe en un flujo que se conecta a la entrada estándar del subproceso.

---

### Ejercicio 8: Spring Boot - El Repositorio

**Tema:** Capa de datos y Spring Data JPA.

#### Enunciado

Estás creando la API de Biblioteca. Ya tienes la entidad `Libro`. Escribe la interfaz del **Repositorio** necesaria para que el Servicio pueda interactuar con la base de datos.

- ¿Qué interfaz de Spring debe extender?
- ¿Necesitas implementar los métodos básicos como `save` o `findById` manualmente?

#### Resolución

```java
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // Indica que esta clase maneja el acceso a datos (tiene las llaves de la BD)
public interface LibroRepository extends JpaRepository<Libro, String> {
    // No hace falta implementar save() ni findById(), Spring los genera "mágicamente"

    // Podemos añadir métodos personalizados si queremos:
    // List<Libro> findByAutor(String autor);
}
```

**Concepto clave:** El `@Repository` es quien tiene las llaves de la base de datos. Usando `Spring Data`, al extender interfaces como `JpaRepository`, la autoconfiguración nos ahorra escribir el código SQL aburrido.

---

### Ejercicio 9: Spring Boot - Códigos de Estado

**Tema:** Respuestas HTTP y `ResponseEntity`.

#### Enunciado

Modifica el controlador del ejercicio de la Biblioteca para el método `obtenerLibro`.

1.  Si el libro no se encuentra, el método no debe devolver `null`.
2.  Debe devolver un código HTTP **404 Not Found**.
3.  Si lo encuentra, devuelve el libro y un código **200 OK**.

#### Resolución

```java
@GetMapping("/libros/{id}")
public ResponseEntity<Libro> obtenerLibro(@PathVariable String id) {
    Libro libro = libroService.buscarLibro(id);

    if (libro != null) {
        return ResponseEntity.ok(libro); // 200 OK: "Marchando, aquí tienes"
    } else {
        return ResponseEntity.notFound().build(); // 404: "Lo siento, no nos queda"
    }
}
```

**Concepto clave:** Los códigos HTTP estandarizan la comunicación. 200 significa éxito y 404 que el recurso no existe. `ResponseEntity` permite controlar totalmente la respuesta.

---

### Ejercicio 10: Diferencia Teórica Proceso vs Hilo

**Tema:** Conceptos básicos de S.O. y Java.

#### Enunciado

Responde brevemente: Si quieres realizar una tarea muy pesada de cálculo matemático en tu aplicación Java, ¿es más eficiente (en términos de uso de recursos del sistema) lanzar un nuevo **Proceso** (usando `ProcessBuilder`) o un nuevo **Hilo**? Justifica tu respuesta basándote en la memoria y el tiempo de creación.

#### Resolución

Es más eficiente lanzar un **Hilo**.

1.  **Recursos:** Los hilos son "procesos ligeros"; comparten el espacio de memoria y contexto del proceso padre. Crear un proceso nuevo implica duplicar estructuras de datos, tener un PCB propio y memoria independiente, lo cual es más costoso.
2.  **Comunicación:** Comunicar hilos es rápido (objetos compartidos). Comunicar procesos requiere mecanismos del S.O. (pipes, sockets) que son más lentos.
