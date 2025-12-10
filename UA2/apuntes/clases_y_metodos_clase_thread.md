<div align="center">

# Clases y Métodos de la Clase Thread en Java

</div>

En Java, la clase [`Thread`](https://docs.oracle.com/javase/8/docs/api/java/lang/Thread.html) proporciona varios métodos útiles para controlar y gestionar la ejecución de hilos.
A continuación se explican los más importantes, con ejemplos prácticos.

## `isAlive()`

### Descripción:

Devuelve un valor booleano indicando si el hilo **está vivo** (es decir, si ha sido iniciado y aún no ha terminado su ejecución).

### Declaración:

```
public final boolean isAlive()
```

### Ejemplo:

```
Thread hilo = new Thread(() -> {
    System.out.println("Ejecutando hilo...");
});
System.out.println(hilo.isAlive()); // false (aún no se ha iniciado)
hilo.start();
System.out.println(hilo.isAlive()); // true (ya está en ejecución)
```

### 📘 Uso típico:

Comprobar si un hilo sigue activo antes de interactuar con él o después de usar `join()`.

## `toString()`

### Descripción:

Devuelve una representación en texto del hilo, que incluye su nombre, prioridad y grupo.

### Declaración:

```
public String toString()
```

### Ejemplo:

```
Thread hilo = new Thread("MiHilo");
System.out.println(hilo.toString());
```

### Salida posible:

```
Thread[MiHilo,5,main]
```

### 📘 Significado:

- `MiHilo` → nombre del hilo.
- `5` → prioridad (por defecto).
- `main` → grupo al que pertenece el hilo.

## `getId()`

### Descripción:

Devuelve el identificador único (tipo long) de un hilo.
Este valor se asigna automáticamente cuando el hilo es creado.

### Declaración:

```
public long getId()
```

### Ejemplo:

```
Thread hilo = new Thread(() -> {});
System.out.println("ID del hilo: " + hilo.getId());
```

### 📘 Nota:

El ID es único para cada hilo mientras el programa se ejecuta.

## `yield()`

### Descripción:

Método estático que sugiere al planificador de hilos que el hilo actual ceda el uso del procesador para permitir que otros hilos se ejecuten.

### Declaración:

```
public static void yield()
```

### Ejemplo:

```
public void run() {
    for (int i = 0; i < 5; i++) {
        System.out.println(Thread.currentThread().getName() + " -> " + i);
        if (i == 2) {
            Thread.yield(); // cede el turno a otro hilo
        }
    }
}
```

### 📘 Importante:

`yield()` no garantiza que otro hilo se ejecute inmediatamente, solo da una “pista” al planificador.

## `setPriority(int p)`

### Descripción:

Establece la prioridad del hilo.
Los valores válidos están entre `Thread.MIN_PRIORITY` (1) y `Thread.MAX_PRIORITY` (10).

### Declaración:

```
public final void setPriority(int newPriority)
```

### Ejemplo:

```
Thread hilo1 = new Thread(() -> {});
hilo1.setPriority(Thread.MAX_PRIORITY);
System.out.println("Prioridad: " + hilo1.getPriority()); // 10
```

### 📘 Efecto:

El planificador puede dar más tiempo de CPU a los hilos con prioridad más alta, aunque no está garantizado (depende del sistema operativo).

## `interrupt()`

### Descripción:

Interrumpe un hilo que esté en espera, dormido o bloqueado.
No lo detiene directamente, sino que lanza una excepción `InterruptedException` si está dormido (`sleep`, `wait`, etc.).

### Declaración:

```
public void interrupt()
```

### Ejemplo:

```
Thread hilo = new Thread(() -> {
    try {
        Thread.sleep(5000);
    } catch (InterruptedException e) {
        System.out.println("❌ Hilo interrumpido.");
    }
});

hilo.start();
hilo.interrupt(); // interrumpe el sueño del hilo
```

### 📘 Diferencia con `interrupted()`:

- `interrupt()` → interrumpe otro hilo.
- `interrupted()` → comprueba e inicializa a false el estado de interrupción del hilo actual.

## `join()`

### Descripción:

Hace que el hilo actual espere a que otro hilo termine antes de continuar.

### Declaración:

```
public final void join() throws InterruptedException
```

### Ejemplo:

```
Thread hilo = new Thread(() -> {
    for (int i = 1; i <= 3; i++) {
        System.out.println("Hilo trabajando... " + i);
    }
});

hilo.start();
hilo.join(); // el hilo principal espera a que termine
System.out.println("El hilo ha finalizado.");
```

### 📘 Uso común:

Coordinar tareas dependientes: por ejemplo, esperar a que un hilo de carga termine antes de continuar con el procesamiento.

<div align="center">

## Conclusión

</div>

Estos métodos permiten gestionar el ciclo de vida de un hilo y controlar su comportamiento en ejecución.
Comprenderlos es fundamental para trabajar con programación concurrente en Java.

## 📚 Referencias

- [Documentación oficial de la clase Thread](https://docs.oracle.com/javase/8/docs/api/java/lang/Thread.html)
- [Guía de concurrencia en Java (Oracle)](https://docs.oracle.com/javase/tutorial/essential/concurrency/)
