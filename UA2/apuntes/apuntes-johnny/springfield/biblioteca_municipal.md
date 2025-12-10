# Ejercicio 3: Biblioteca Municipal

## Enunciado

Simula una biblioteca donde hay un número limitado de libros disponibles para préstamo. Los usuarios (hilos) llegan para tomar libros prestados y luego los devuelven. El sistema debe:

- Gestionar un catálogo de libros disponibles (inicialmente 4 libros)
- Permitir que los usuarios tomen libros prestados
- Los usuarios esperan si no hay libros disponibles
- Los usuarios leen durante un tiempo aleatorio y devuelven el libro
- Contabilizar cuántos libros ha leído cada usuario

**Requisitos Funcionales:**
1. **RF1**: Implementar una clase `Libro` con título y autor
2. **RF2**: Implementar una clase `Biblioteca` que gestione el catálogo (sincronización)
3. **RF3**: Implementar hilos `HiloLector` que simulen usuarios tomando y devolviendo libros
4. **RF4**: Cada lector debe contabilizar los libros leídos
5. **RF5**: Mostrar el estado del catálogo disponible

## Conceptos de Concurrencia Aplicados

- **Pool de recursos** (libros compartidos)
- **Sincronización** con synchronized
- **wait()** y **notifyAll()** para gestión de disponibilidad
- **Productor-consumidor** aplicado a préstamos

---

## Solución

```java
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BibliotecaMunicipal {
    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("📚 Biblioteca Municipal de Springfield");
        System.out.println("==================================================\n");

        Biblioteca biblioteca = new Biblioteca();

        // Crear varios lectores
        String[] lectores = {"Lisa", "Martin", "Bart", "Milhouse", "Ralph", "Nelson"};
        
        for (String lector : lectores) {
            new HiloLector(lector, biblioteca).start();
        }
    }
}

/**
 * RF1 - Representa un libro en la biblioteca
 */
class Libro {
    private final int id;
    private final String titulo;
    private final String autor;
    private static int contador = 1;

    public Libro(String titulo, String autor) {
        this.id = contador++;
        this.titulo = titulo;
        this.autor = autor;
    }

    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    @Override
    public String toString() {
        return "📖 [#" + id + "] '" + titulo + "' - " + autor;
    }
}

/**
 * RF2 - Gestiona el catálogo de libros
 */
class Biblioteca {
    private final List<Libro> librosDisponibles;
    private int totalPrestamos = 0;

    public Biblioteca() {
        this.librosDisponibles = new ArrayList<>();
        
        // Inicializar con 4 libros
        librosDisponibles.add(new Libro("El Principito", "Antoine de Saint-Exupéry"));
        librosDisponibles.add(new Libro("1984", "George Orwell"));
        librosDisponibles.add(new Libro("Cien Años de Soledad", "Gabriel García Márquez"));
        librosDisponibles.add(new Libro("Don Quijote", "Miguel de Cervantes"));
        
        System.out.println("📚 Biblioteca abierta con " + librosDisponibles.size() + " libros:");
        for (Libro libro : librosDisponibles) {
            System.out.println("   " + libro);
        }
        System.out.println("==================================================\n");
    }

    public synchronized Libro prestarLibro(String nombreLector) {
        while (librosDisponibles.isEmpty()) {
            try {
                System.out.println("⏳ " + nombreLector + " esperando (no hay libros disponibles)");
                mostrarEstado();
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Libro libro = librosDisponibles.remove(new Random().nextInt(librosDisponibles.size()));
        totalPrestamos++;
        
        System.out.println("==================================================");
        System.out.println("✅ " + nombreLector + " ha tomado prestado:");
        System.out.println("   " + libro);
        mostrarEstado();
        
        notifyAll();
        return libro;
    }

    public synchronized void devolverLibro(Libro libro, String nombreLector) {
        librosDisponibles.add(libro);
        
        System.out.println("==================================================");
        System.out.println("📥 " + nombreLector + " ha devuelto:");
        System.out.println("   " + libro);
        mostrarEstado();
        
        notifyAll();
    }

    private void mostrarEstado() {
        System.out.println("📊 Libros disponibles: " + librosDisponibles.size() + "/4");
        System.out.println("📈 Total préstamos realizados: " + totalPrestamos);
        System.out.println("==================================================\n");
    }
}

/**
 * RF3 - Hilos que representan lectores
 */
class HiloLector extends Thread {
    private final Biblioteca biblioteca;
    private int librosLeidos = 0;

    public HiloLector(String nombre, Biblioteca biblioteca) {
        super(nombre);
        this.biblioteca = biblioteca;
    }

    @Override
    public void run() {
        System.out.println("👤 " + getName() + " ha entrado a la biblioteca");
        System.out.println("==================================================\n");

        // Cada lector lee varios libros
        for (int i = 0; i < 3; i++) {
            try {
                // Tomar un libro prestado
                Libro libro = biblioteca.prestarLibro(getName());

                // Simular lectura
                int tiempoLectura = new Random().nextInt(2000) + 1000;
                System.out.println("📖 " + getName() + " está leyendo '" + libro.getTitulo() + "'...");
                System.out.println("==================================================\n");
                Thread.sleep(tiempoLectura);

                // Devolver el libro
                librosLeidos++;
                biblioteca.devolverLibro(libro, getName());
                
                System.out.println("✨ " + getName() + " ha leído " + librosLeidos + " libro(s) en total");
                System.out.println("==================================================\n");

                // Descanso antes de tomar otro libro
                Thread.sleep(new Random().nextInt(500) + 200);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("👋 " + getName() + " se retira de la biblioteca con " + librosLeidos + " libros leídos");
        System.out.println("==================================================\n");
    }
}
```

## Ejecución Esperada

El programa mostrará:
- Lectores entrando a la biblioteca
- Libros siendo prestados del catálogo
- Lectores esperando cuando no hay libros disponibles
- Tiempo de lectura de cada libro
- Libros siendo devueltos
- Contador personal de libros leídos por cada lector
- Estado del catálogo en tiempo real
- Total de préstamos realizados