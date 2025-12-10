# Ejercicio 4: Pizzería Luigi's

## Enunciado

Simula una pizzería donde hay un número limitado de hornos (3 hornos) para cocinar pizzas. Los cocineros (hilos) preparan pizzas que necesitan ser horneadas. El sistema debe:

- Gestionar el acceso a los hornos disponibles
- Los cocineros esperan si todos los hornos están ocupados
- Cada pizza tarda un tiempo aleatorio en cocinarse
- Los cocineros deben hornear diferentes tipos de pizza
- Contabilizar las pizzas preparadas por cada cocinero

**Requisitos Funcionales:**
1. **RF1**: Implementar una clase `Pizza` con diferentes tipos (Margarita, Pepperoni, Cuatro Quesos, Hawaiana)
2. **RF2**: Implementar una clase `Pizzeria` que gestione los hornos (sincronización)
3. **RF3**: Implementar hilos `HiloCocinero` que preparen y horneen pizzas
4. **RF4**: Contabilizar pizzas preparadas por cada cocinero
5. **RF5**: Mostrar el estado de los hornos

## Conceptos de Concurrencia Aplicados

- **Recursos limitados** (hornos)
- **Contador de recursos** con sincronización
- **wait()** y **notifyAll()** para gestión de disponibilidad
- **Sección crítica** en uso de hornos

---

## Solución

```java
import java.util.Random;

public class PizzeriaLuigi {
    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("🍕 Pizzería Luigi's");
        System.out.println("==================================================\n");

        Pizzeria pizzeria = new Pizzeria("Luigi's", 3);

        // Crear varios cocineros
        String[] cocineros = {"Luigi", "Mario", "Giuseppe", "Antonio", "Vincenzo"};
        
        for (String cocinero : cocineros) {
            new HiloCocinero(cocinero, pizzeria).start();
        }
    }
}

/**
 * RF1 - Representa una pizza
 */
class Pizza {
    private final int id;
    private final String tipo;
    private final String cocinero;
    private static int contador = 1;
    private static final String[] TIPOS_PIZZA = {
        "Margarita", "Pepperoni", "Cuatro Quesos", "Hawaiana"
    };

    public Pizza(String cocinero) {
        this.id = contador++;
        this.tipo = TIPOS_PIZZA[new Random().nextInt(TIPOS_PIZZA.length)];
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

    public int getTiempoCoccion() {
        // Cada tipo de pizza tiene un tiempo diferente
        switch (tipo) {
            case "Margarita": return 1000;
            case "Pepperoni": return 1200;
            case "Cuatro Quesos": return 1500;
            case "Hawaiana": return 1300;
            default: return 1000;
        }
    }

    @Override
    public String toString() {
        return "Pizza #" + id + " - " + tipo + " (hecha por " + cocinero + ")";
    }
}

/**
 * RF2 - Gestiona los hornos de la pizzería
 */
class Pizzeria {
    private final String nombre;
    private final int totalHornos;
    private int hornosDisponibles;
    private int totalPizzasCocinadas = 0;

    public Pizzeria(String nombre, int hornos) {
        this.nombre = nombre;
        this.totalHornos = hornos;
        this.hornosDisponibles = hornos;
        System.out.println("🏪 Pizzería '" + nombre + "' abierta con " + hornos + " hornos");
        System.out.println("==================================================\n");
    }

    public synchronized void ocuparHorno(Pizza pizza) {
        while (hornosDisponibles == 0) {
            try {
                System.out.println("⏳ " + pizza.getCocinero() + " esperando horno para " + pizza.getTipo());
                mostrarEstado();
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        hornosDisponibles--;
        System.out.println("==================================================");
        System.out.println("🔥 " + pizza.getCocinero() + " está horneando:");
        System.out.println("   🍕 " + pizza);
        mostrarEstado();
        
        notifyAll();
    }

    public synchronized void liberarHorno(Pizza pizza) {
        hornosDisponibles++;
        totalPizzasCocinadas++;
        
        System.out.println("==================================================");
        System.out.println("✅ Pizza lista:");
        System.out.println("   🍕 " + pizza);
        mostrarEstado();
        
        notifyAll();
    }

    private void mostrarEstado() {
        System.out.println("🔥 Hornos disponibles: " + hornosDisponibles + "/" + totalHornos);
        System.out.println("📊 Hornos en uso: " + (totalHornos - hornosDisponibles) + "/" + totalHornos);
        System.out.println("📈 Total pizzas cocinadas: " + totalPizzasCocinadas);
        System.out.println("==================================================\n");
    }
}

/**
 * RF3 - Hilos que representan cocineros
 */
class HiloCocinero extends Thread {
    private final Pizzeria pizzeria;
    private int pizzasPreparadas = 0;

    public HiloCocinero(String nombre, Pizzeria pizzeria) {
        super(nombre);
        this.pizzeria = pizzeria;
    }

    @Override
    public void run() {
        System.out.println("👨‍🍳 Cocinero " + getName() + " ha llegado al trabajo");
        System.out.println("==================================================\n");

        // Cada cocinero prepara varias pizzas
        for (int i = 0; i < 4; i++) {
            try {
                // Crear una nueva pizza
                Pizza pizza = new Pizza(getName());
                System.out.println("👨‍🍳 " + getName() + " ha preparado una " + pizza.getTipo());
                System.out.println("==================================================\n");

                // Ocupar un horno
                pizzeria.ocuparHorno(pizza);

                // Simular cocción
                Thread.sleep(pizza.getTiempoCoccion());

                // Liberar el horno
                pizzeria.liberarHorno(pizza);
                pizzasPreparadas++;

                System.out.println("✨ " + getName() + " lleva " + pizzasPreparadas + " pizza(s) preparadas");
                System.out.println("==================================================\n");

                // Pequeña pausa antes de preparar la siguiente
                Thread.sleep(new Random().nextInt(500) + 200);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("👋 Cocinero " + getName() + " termina su turno con " + pizzasPreparadas + " pizzas preparadas");
        System.out.println("==================================================\n");
    }
}
```

## Ejecución Esperada

El programa mostrará:
- Cocineros llegando al trabajo
- Pizzas siendo preparadas de diferentes tipos
- Cocineros esperando cuando todos los hornos están ocupados
- Pizzas siendo horneadas con tiempos diferentes según el tipo
- Hornos liberándose al terminar la cocción
- Contador de pizzas por cocinero
- Estado de los hornos en tiempo real
- Total de pizzas cocinadas en la pizzería