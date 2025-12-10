# 📚 **Ejercicios de Programación – Java & Spring Boot**

---

## 1️⃣ Ejercicio 1 – Creación y Redirección de Procesos (`ProcessBuilder`)

### 🎯 Tema

Gestión de procesos del Sistema Operativo y redirección de E/S.

### 📋 Enunciado

Crea un programa en Java que:

1. Ejecute un comando del SO (por ejemplo `DIR` en Windows o `ls` en Linux) para listar los archivos del directorio actual.
2. Redirija la salida estándar a un archivo llamado **`listado_archivos.txt`**.
3. Inicie el proceso y espere a que termine.

### 💡 Resolución y Explicación

`ProcessBuilder` permite configurar atributos del proceso (comando, redirección, directorio de trabajo, etc.).

```java
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EjercicioProcesos {
    public static void main(String[] args) {
        // 1️⃣ Definir el comando según el SO
        List<String> comando = new ArrayList<>();
        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            comando.add("cmd.exe");
            comando.add("/c");
            comando.add("dir");
        } else {
            comando.add("ls");
        }

        ProcessBuilder pb = new ProcessBuilder(comando);

        // 2️⃣ Redirigir la salida a un fichero
        File archivoSalida = new File("listado_archivos.txt");
        pb.redirectOutput(archivoSalida);

        try {
            // 3️⃣ Iniciar el proceso y esperar su finalización
            Process proceso = pb.start();
            int exitCode = proceso.waitFor();
            System.out.println("Proceso finalizado con código: " + exitCode);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
```

#### 🔑 Puntos clave

| Concepto           | Detalle                                                                              |
| ------------------ | ------------------------------------------------------------------------------------ |
| **Instancia**      | Cada `ProcessBuilder` gestiona los atributos de un proceso.                          |
| **`start()`**      | Crea y lanza el proceso con los atributos configurados.                              |
| **Sincronización** | `ProcessBuilder` no es thread‑safe; sincronizar su uso si varios hilos lo comparten. |

---

## 2️⃣ Ejercicio 2 – Hilos Básicos con `Runnable` y `join()`

### 🎯 Tema

Creación de hilos, interfaz `Runnable` y gestión de espera.

### 📋 Enunciado

Implementa una aplicación que simule una carrera entre dos corredores (hilos).

1. Crea una clase **`Corredor`** que implemente `Runnable`.
2. En `run()`, el corredor debe imprimir su nombre 5 veces, durmiendo un tiempo aleatorio entre cada impresión.
3. En `main`, lanza dos hilos y usa `join()` para que el hilo principal espere a que ambos terminen antes de imprimir **"Carrera finalizada"**.

### 💡 Resolución y Explicación

`Runnable` es preferible a extender `Thread` porque permite heredar de otra clase y ofrece mayor flexibilidad.

```java
public class Corredor implements Runnable {
    private final String nombre;
    public Corredor(String nombre) { this.nombre = nombre; }

    @Override
    public void run() {
        try {
            for (int i = 1; i <= 5; i++) {
                System.out.println(nombre + " avanza paso " + i);
                Thread.sleep((long) (Math.random() * 1000)); // pausa aleatoria
            }
            System.out.println(nombre + " ha llegado a la meta.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }
}

public class Carrera {
    public static void main(String[] args) throws InterruptedException {
        Thread h1 = new Thread(new Corredor("Liebre"));
        Thread h2 = new Thread(new Corredor("Tortuga"));
        h1.start();
        h2.start();
        h1.join(); // espera a que termine la Liebre
        h2.join(); // espera a que termine la Tortuga
        System.out.println("Carrera finalizada.");
    }
}
```

#### 🔑 Puntos clave

- **`sleep()`** → estado **Blocked** mientras el hilo está dormido.
- **`join()`** → sincroniza hilos, garantizando que el hilo principal no avanza hasta que los hilos hijos finalicen.

---

## 3️⃣ Ejercicio 3 – Patrón Productor‑Consumidor

### 🎯 Tema

Sincronización con `synchronized`, `wait()` y `notifyAll()`.

### 📋 Enunciado

Modela una **Cinta Transportadora** (buffer) con capacidad para **un solo producto**.

1. Clase **`CintaTransportadora`** con métodos `producir()` y `consumir()`.
2. Usa `synchronized`, `wait()` y `notifyAll()` para que:
   - El productor espere si la cinta está llena.
   - El consumidor espere si la cinta está vacía.
3. Evita condiciones de carrera y asegura la integridad de los datos.

### 💡 Resolución y Explicación

El monitor (`synchronized`) actúa como un cerrojo; `wait()` libera el cerrojo y suspende el hilo; `notifyAll()` despierta a los hilos en espera.

```java
public class CintaTransportadora {
    private String producto = null;
    private boolean llena = false;

    public synchronized void producir(String nuevo) {
        while (llena) {
            try { wait(); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }
        this.producto = nuevo;
        this.llena = true;
        System.out.println("Producido: " + producto);
        notifyAll(); // despierta a los consumidores
    }

    public synchronized String consumir() {
        while (!llena) {
            try { wait(); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }
        String entregado = producto;
        this.llena = false;
        System.out.println("Consumido: " + entregado);
        notifyAll(); // despierta al productor
        return entregado;
    }
}
```

#### 🔑 Puntos clave

| Concepto            | Por qué es importante                                                             |
| ------------------- | --------------------------------------------------------------------------------- |
| **`synchronized`**  | Garantiza exclusión mutua; solo un hilo accede al monitor a la vez.               |
| **`while` vs `if`** | `while` protege contra _spurious wake‑ups_ y vuelve a comprobar la condición.     |
| **`notifyAll()`**   | Asegura que todos los hilos esperando sean despertados; evita pérdida de señales. |

---

## 4️⃣ Ejercicio 4 – Arquitectura REST con Spring Boot

### 🎯 Tema

Diseño de API, capas de la aplicación y verbos HTTP.

### 📋 Enunciado

Diseña conceptualmente (código esquemático) una API para gestionar una **Biblioteca** usando Spring Boot.

1. Anotación para la clase que recibe peticiones web (el _portero_).
2. Anotación para la clase que contiene la lógica de negocio (el _cerebro_).
3. Implementa dos métodos en el controlador:
   - **GET** para leer la información de un libro.
   - **POST** para crear un libro nuevo.

### 💡 Resolución y Explicación

Spring Boot sigue una arquitectura de capas basada en anotaciones (`@RestController`, `@Service`, `@Repository`).

```java
// 1️⃣ Portero – controlador REST
@RestController
@RequestMapping("/biblioteca")
public class LibroController {

    @Autowired
    private LibroService libroService;

    // 📖 GET – obtener libro por ID
    @GetMapping("/libros/{id}")
    public Libro obtenerLibro(@PathVariable String id) {
        return libroService.buscarLibro(id);
    }

    // ➕ POST – crear nuevo libro
    @PostMapping("/libros")
    public Libro guardarLibro(@RequestBody Libro nuevoLibro) {
        return libroService.guardar(nuevoLibro);
    }
}

// 2️⃣ Cerebro – lógica de negocio
@Service
public class LibroService {
    @Autowired
    private LibroRepository repositorio;

    public Libro buscarLibro(String id) {
        return repositorio.findById(id).orElse(null);
    }

    public Libro guardar(Libro libro) {
        return repositorio.save(libro);
    }
}
```

#### 🔑 Puntos clave

- **Capas**: `Controller → Service → Repository`.
- **Verbos HTTP**: `GET` para lectura, `POST` para creación.
- **Códigos de estado**: `200 OK` para éxito, `404 Not Found` si el libro no existe.

---

## 📚 Analogía de Consolidación

| Concepto                   | Analogy                                                                                   |
| -------------------------- | ----------------------------------------------------------------------------------------- |
| **API Web (Spring Boot)**  | El _camarero_ que recibe pedidos del cliente y entrega la respuesta.                      |
| **Hilos y sincronización** | Los _cocineros_ en la cocina; el _monitor_ es la ventanilla donde se entregan los platos. |
| **`notifyAll()`**          | El _timbre_ que suena cuando un plato está listo o cuando hay espacio disponible.         |

---

_¡Listo! El documento ahora está estructurado con encabezados claros, bloques de código resaltados, tablas de referencia y una narrativa visual que facilita el estudio y la comprensión._
