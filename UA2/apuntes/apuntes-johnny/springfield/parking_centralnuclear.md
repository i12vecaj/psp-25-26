# Ejercicio 2: Parking de la Central Nuclear

## Enunciado

Simula un parking con capacidad limitada (5 plazas) donde los empleados de la Central Nuclear de Springfield llegan en sus coches. El sistema debe gestionar:

- La entrada de coches cuando hay plazas disponibles
- Los coches esperan si el parking está lleno
- La salida de coches libera plazas
- Cada coche permanece estacionado un tiempo aleatorio
- Se debe mostrar el estado actual del parking

**Requisitos Funcionales:**
1. **RF1**: Implementar una clase `Coche` que represente los vehículos
2. **RF2**: Implementar una clase `Parking` que gestione las plazas (sincronización)
3. **RF3**: Implementar hilos `HiloCoche` que simulen coches entrando y saliendo
4. **RF4**: Contabilizar el tiempo total de estacionamiento de cada coche
5. **RF5**: Mostrar el estado de ocupación del parking

## Conceptos de Concurrencia Aplicados

- **Semáforos simulados** con synchronized
- **Recursos limitados** (plazas de parking)
- **wait()** y **notifyAll()** para gestión de recursos
- **Exclusión mutua** en operaciones críticas

---

## Solución

```java
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ParkingCentralNuclear {
    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("Parking de la Central Nuclear de Springfield");
        System.out.println("==================================================\n");

        Parking parking = new Parking(5);

        // Crear hilos de coches que intentan aparcar
        String[] empleados = {"Homer", "Lenny", "Carl", "Burns", "Smithers", 
                             "Frank Grimes", "Mindy", "Charlie"};
        
        for (String empleado : empleados) {
            new HiloCoche("Coche de " + empleado, parking).start();
            try {
                Thread.sleep(new Random().nextInt(300) + 100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

/**
 * RF1 - Representa un coche en el parking
 */
class Coche {
    private final String propietario;
    private final int id;
    private static int contador = 1;
    private long tiempoEntrada;

    public Coche(String propietario) {
        this.propietario = propietario;
        this.id = contador++;
    }

    public String getPropietario() {
        return propietario;
    }

    public int getId() {
        return id;
    }

    public void registrarEntrada() {
        this.tiempoEntrada = System.currentTimeMillis();
    }

    public long getTiempoEstacionado() {
        return (System.currentTimeMillis() - tiempoEntrada) / 1000;
    }

    @Override
    public String toString() {
        return "Coche #" + id + " (" + propietario + ")";
    }
}

/**
 * RF2 - Gestiona las plazas del parking
 */
class Parking {
    private final int capacidadMaxima;
    private final List<Coche> cochesEstacionados;
    private int totalVehiculosAtendidos = 0;

    public Parking(int capacidad) {
        this.capacidadMaxima = capacidad;
        this.cochesEstacionados = new ArrayList<>();
        System.out.println("🅿️  Parking creado con capacidad: " + capacidad + " plazas");
        System.out.println("==================================================\n");
    }

    public synchronized void entrarParking(Coche coche) {
        while (cochesEstacionados.size() >= capacidadMaxima) {
            try {
                System.out.println("🚫 " + coche + " esperando (parking lleno)");
                mostrarEstado();
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        cochesEstacionados.add(coche);
        coche.registrarEntrada();
        totalVehiculosAtendidos++;
        
        System.out.println("==================================================");
        System.out.println("✅ " + coche + " ha entrado al parking");
        mostrarEstado();
        
        notifyAll();
    }

    public synchronized void salirParking(Coche coche) {
        cochesEstacionados.remove(coche);
        long tiempoEstacionado = coche.getTiempoEstacionado();
        
        System.out.println("==================================================");
        System.out.println("🚗 " + coche + " ha salido del parking");
        System.out.println("⏱️  Tiempo estacionado: " + tiempoEstacionado + " segundos");
        mostrarEstado();
        
        notifyAll();
    }

    private void mostrarEstado() {
        System.out.println("📊 Plazas ocupadas: " + cochesEstacionados.size() + "/" + capacidadMaxima);
        System.out.println("📈 Total vehículos atendidos: " + totalVehiculosAtendidos);
        System.out.println("==================================================\n");
    }
}

/**
 * RF3 - Hilos que representan coches
 */
class HiloCoche extends Thread {
    private final Parking parking;
    private final Coche coche;

    public HiloCoche(String nombre, Parking parking) {
        super(nombre);
        this.parking = parking;
        this.coche = new Coche(nombre);
    }

    @Override
    public void run() {
        try {
            // Intentar entrar al parking
            parking.entrarParking(coche);

            // Simular que el coche está estacionado
            int tiempoEstacionamiento = new Random().nextInt(3000) + 1000;
            System.out.println("🕐 " + coche + " estacionado por " + (tiempoEstacionamiento/1000) + " segundos");
            System.out.println("==================================================\n");
            Thread.sleep(tiempoEstacionamiento);

            // Salir del parking
            parking.salirParking(coche);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
```

## Ejecución Esperada

El programa mostrará:
- Coches intentando entrar al parking
- Coches esperando cuando el parking está lleno
- Tiempo de estacionamiento de cada vehículo
- Estado de ocupación en tiempo real
- Total de vehículos atendidos
- Liberación de plazas cuando los coches salen