# Ejercicio 6: Krusty Burger

## Enunciado

Simula el funcionamiento de un restaurante Krusty Burger donde hay cocineros que preparan hamburguesas y las colocan en una bandeja de pedidos listos (con capacidad limitada de 5 hamburguesas). Los clientes (hilos) llegan al restaurante, hacen su pedido y esperan a que haya hamburguesas disponibles en la bandeja. El sistema debe:

- Gestionar una bandeja compartida con capacidad limitada (5 hamburguesas máximo)
- Los cocineros producen hamburguesas y las colocan en la bandeja
- Los cocineros esperan si la bandeja está llena
- Los clientes toman hamburguesas de la bandeja
- Los clientes esperan si la bandeja está vacía
- Contabilizar hamburguesas producidas y consumidas

**Requisitos Funcionales:**

1. **RF1**: Implementar una clase `Hamburguesa` con diferentes tipos (Krusty Burger, Krusty Deluxe, Ribwich, Veggie Burger)
2. **RF2**: Implementar una clase `Restaurante` que gestione la bandeja de pedidos (sincronización productor-consumidor)
3. **RF3**: Implementar hilos `HiloCocinero` que produzcan hamburguesas continuamente
4. **RF4**: Implementar hilos `HiloCliente` que consuman hamburguesas
5. **RF5**: Contabilizar producción y consumo, mostrar estado de la bandeja

## Conceptos de Concurrencia Aplicados

- **Problema Productor-Consumidor clásico**
- **Buffer limitado** (bandeja con capacidad máxima)
- **Sincronización bidireccional** (productores y consumidores)
- **wait()** y **notifyAll()** para coordinación
- **Condiciones múltiples** (bandeja llena/vacía)

---

## Solución

```java
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class KrustyBurger {
    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("🍔 Bienvenido a Krusty Burger");
        System.out.println("'I'm Lovin' It... wait, that's not our slogan!'");
        System.out.println("==================================================\n");

        // Crear el restaurante con capacidad de 5 hamburguesas en la bandeja
        Restaurante restaurante = new Restaurante("Krusty Burger Springfield", 5);

        // Crear 2 cocineros (productores)
        HiloCocinero cocinero1 = new HiloCocinero("Cocinero-1", restaurante);
        HiloCocinero cocinero2 = new HiloCocinero("Cocinero-2", restaurante);

        // Crear 5 clientes (consumidores)
        HiloCliente homer = new HiloCliente("Homer", restaurante);
        HiloCliente bart = new HiloCliente("Bart", restaurante);
        HiloCliente lisa = new HiloCliente("Lisa", restaurante);
        HiloCliente marge = new HiloCliente("Marge", restaurante);
        HiloCliente maggie = new HiloCliente("Maggie", restaurante);

        // Iniciar todos los hilos
        cocinero1.start();
        cocinero2.start();

        homer.start();
        bart.start();
        lisa.start();
        marge.start();
        maggie.start();
    }
}

/**
 * RF1 - Representa una hamburguesa
 */
class Hamburguesa {
    private final int id;
    private final String tipo;
    private final String cocinero;
    private static int contador = 1;
    private static final String[] TIPOS_HAMBURGUESA = {
        "Krusty Burger", "Krusty Deluxe", "Ribwich", "Veggie Burger"
    };

    public Hamburguesa(String cocinero) {
        this.id = contador++;
        this.tipo = TIPOS_HAMBURGUESA[new Random().nextInt(TIPOS_HAMBURGUESA.length)];
        this.cocinero = cocinero;
    }

    public int getId() {
        return id;
    }

    public String getTipo() {
        return tipo;
    }

    public String getCocinero() {
        return cocinero;
    }

    @Override
    public String toString() {
        return "🍔 Hamburguesa #" + id + " - " + tipo + " (por " + cocinero + ")";
    }
}

/**
 * RF2 - Gestiona la bandeja de pedidos (buffer limitado)
 * Implementa el patrón Productor-Consumidor
 */
class Restaurante {
    private final String nombre;
    private final int capacidadMaxima;
    private final List<Hamburguesa> bandejaPedidos;
    private int totalProducidas = 0;
    private int totalConsumidas = 0;

    public Restaurante(String nombre, int capacidad) {
        this.nombre = nombre;
        this.capacidadMaxima = capacidad;
        this.bandejaPedidos = new ArrayList<>();
        System.out.println("🏪 " + nombre + " está abierto");
        System.out.println("📊 Capacidad de la bandeja: " + capacidad + " hamburguesas");
        System.out.println("==================================================\n");
    }

    /**
     * Método para que los cocineros (PRODUCTORES) coloquen hamburguesas
     */
    public synchronized void producirHamburguesa(Hamburguesa hamburguesa) {
        // Si la bandeja está llena, el cocinero debe esperar
        while (bandejaPedidos.size() >= capacidadMaxima) {
            try {
                System.out.println("⏳ " + hamburguesa.getCocinero() + " esperando (bandeja llena)");
                mostrarEstado();
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Colocar la hamburguesa en la bandeja
        bandejaPedidos.add(hamburguesa);
        totalProducidas++;

        System.out.println("==================================================");
        System.out.println("👨‍🍳 " + hamburguesa.getCocinero() + " ha cocinado:");
        System.out.println("   " + hamburguesa);
        mostrarEstado();

        // Notificar a los clientes que hay hamburguesas disponibles
        notifyAll();
    }

    /**
     * Método para que los clientes (CONSUMIDORES) tomen hamburguesas
     */
    public synchronized Hamburguesa consumirHamburguesa(String nombreCliente) {
        // Si la bandeja está vacía, el cliente debe esperar
        while (bandejaPedidos.isEmpty()) {
            try {
                System.out.println("⏳ " + nombreCliente + " esperando (no hay hamburguesas)");
                mostrarEstado();
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Tomar la primera hamburguesa de la bandeja
        Hamburguesa hamburguesa = bandejaPedidos.remove(0);
        totalConsumidas++;

        System.out.println("==================================================");
        System.out.println("😋 " + nombreCliente + " ha recibido:");
        System.out.println("   " + hamburguesa);
        mostrarEstado();

        // Notificar a los cocineros que hay espacio en la bandeja
        notifyAll();
        return hamburguesa;
    }

    private void mostrarEstado() {
        System.out.println("📊 Estado del restaurante:");
        System.out.println("   🍔 En bandeja: " + bandejaPedidos.size() + "/" + capacidadMaxima);
        System.out.println("   📈 Producidas: " + totalProducidas + " | Consumidas: " + totalConsumidas);
        System.out.println("   📊 Balance: " + (totalProducidas - totalConsumidas));
        System.out.println("==================================================\n");
    }
}

/**
 * RF3 - Hilos que representan cocineros (PRODUCTORES)
 */
class HiloCocinero extends Thread {
    private final Restaurante restaurante;
    private int hamburguesasCocinadas = 0;

    public HiloCocinero(String nombre, Restaurante restaurante) {
        super(nombre);
        this.restaurante = restaurante;
    }

    @Override
    public void run() {
        System.out.println("👨‍🍳 " + getName() + " ha comenzado su turno");
        System.out.println("==================================================\n");

        // Bucle infinito: el cocinero produce hamburguesas continuamente
        while (true) {
            try {
                // Simular tiempo de preparación de la hamburguesa
                int tiempoPreparacion = new Random().nextInt(1500) + 1000;
                System.out.println("🔥 " + getName() + " cocinando... (" + (tiempoPreparacion/1000) + "s)");
                System.out.println("==================================================\n");
                Thread.sleep(tiempoPreparacion);

                // Crear y producir la hamburguesa
                Hamburguesa hamburguesa = new Hamburguesa(getName());
                restaurante.producirHamburguesa(hamburguesa);
                hamburguesasCocinadas++;

                System.out.println("✨ " + getName() + " lleva " + hamburguesasCocinadas + " hamburguesa(s) cocinadas");
                System.out.println("==================================================\n");

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

/**
 * RF4 - Hilos que representan clientes (CONSUMIDORES)
 */
class HiloCliente extends Thread {
    private final Restaurante restaurante;
    private int hamburguesasComidas = 0;

    public HiloCliente(String nombre, Restaurante restaurante) {
        super(nombre);
        this.restaurante = restaurante;
    }

    @Override
    public void run() {
        System.out.println("🚶 " + getName() + " ha entrado a Krusty Burger");
        System.out.println("==================================================\n");

        // Bucle infinito: el cliente consume hamburguesas continuamente
        while (true) {
            try {
                // El cliente pide una hamburguesa
                Hamburguesa hamburguesa = restaurante.consumirHamburguesa(getName());

                // Simular tiempo de consumo
                int tiempoConsumiendo = new Random().nextInt(2000) + 1000;
                System.out.println("🍔 " + getName() + " está comiendo " + hamburguesa.getTipo() + "...");
                System.out.println("==================================================\n");
                Thread.sleep(tiempoConsumiendo);

                hamburguesasComidas++;
                System.out.println("😋 " + getName() + " ha comido " + hamburguesasComidas + " hamburguesa(s)");
                System.out.println("==================================================\n");

                // Pequeña pausa antes de pedir otra (el cliente descansa)
                Thread.sleep(new Random().nextInt(1000) + 500);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
```

## Ejecución Esperada

El programa mostrará:

- Cocineros comenzando sus turnos como productores
- Clientes entrando al restaurante como consumidores
- Cocineros cocinando hamburguesas de diferentes tipos
- Cocineros esperando cuando la bandeja está llena (buffer lleno)
- Hamburguesas siendo colocadas en la bandeja
- Clientes esperando cuando no hay hamburguesas disponibles (buffer vacío)
- Clientes recibiendo y comiendo hamburguesas
- Estado de la bandeja en tiempo real (ocupación)
- Balance entre producción y consumo
- Contadores individuales de hamburguesas cocinadas por cocinero
- Contadores individuales de hamburguesas comidas por cliente

## Características del Patrón Productor-Consumidor

Este ejercicio implementa el **patrón productor-consumidor clásico**:

- **Productores (Cocineros)**: Generan hamburguesas y las colocan en un buffer compartido
- **Consumidores (Clientes)**: Toman hamburguesas del buffer compartido
- **Buffer limitado (Bandeja)**: Capacidad máxima de 5 elementos
- **Sincronización**:
  - Los productores esperan si el buffer está lleno
  - Los consumidores esperan si el buffer está vacío
  - Se usa `notifyAll()` para despertar hilos apropiados cuando cambia el estado
