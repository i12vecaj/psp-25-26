# Ejercicio 5: Lavandería Automática

## Enunciado

Simula una lavandería automática con un número limitado de lavadoras (4 lavadoras) y secadoras (3 secadoras). Los clientes (hilos) llegan con su ropa sucia, necesitan usar primero una lavadora y después una secadora. El sistema debe:

- Gestionar el acceso a lavadoras y secadoras
- Los clientes esperan si no hay máquinas disponibles
- Cada ciclo de lavado y secado tarda un tiempo aleatorio
- Los clientes pagan según el número de ciclos usados
- Mostrar el estado de las máquinas disponibles

**Requisitos Funcionales:**
1. **RF1**: Implementar clases `Lavadora` y `Secadora` con diferentes capacidades
2. **RF2**: Implementar una clase `Lavanderia` que gestione ambos tipos de máquinas (sincronización)
3. **RF3**: Implementar hilos `HiloCliente` que usen lavadora y secadora secuencialmente
4. **RF4**: Contabilizar el dinero gastado por cada cliente
5. **RF5**: Mostrar el estado de máquinas disponibles

## Conceptos de Concurrencia Aplicados

- **Múltiples recursos compartidos** (lavadoras y secadoras)
- **Sincronización en dos fases** (lavar y secar)
- **wait()** y **notifyAll()** para gestión de recursos
- **Secciones críticas** independientes para cada tipo de recurso

---

## Solución

```java
import java.util.Random;

public class LavanderiaAutomatica {
    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("🧺 Lavandería Automática - Springfield");
        System.out.println("==================================================\n");

        Lavanderia lavanderia = new Lavanderia(4, 3);

        // Crear varios clientes
        String[] clientes = {"Marge", "Helen", "Edna", "Luann", "Agnes", 
                            "Maude", "Ruth", "Cookie"};
        
        for (String cliente : clientes) {
            new HiloCliente(cliente, lavanderia).start();
            try {
                Thread.sleep(new Random().nextInt(300) + 100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

/**
 * RF1 - Representa una lavadora
 */
class Lavadora {
    private final int numero;
    private final int capacidadKg;
    private final double precioCiclo;

    public Lavadora(int numero) {
        this.numero = numero;
        this.capacidadKg = new Random().nextInt(3) + 6; // Entre 6 y 8 kg
        this.precioCiclo = 3.50;
    }

    public int getNumero() {
        return numero;
    }

    public int getCapacidadKg() {
        return capacidadKg;
    }

    public double getPrecioCiclo() {
        return precioCiclo;
    }

    @Override
    public String toString() {
        return "Lavadora #" + numero + " (" + capacidadKg + "kg)";
    }
}

/**
 * RF1 - Representa una secadora
 */
class Secadora {
    private final int numero;
    private final int capacidadKg;
    private final double precioCiclo;

    public Secadora(int numero) {
        this.numero = numero;
        this.capacidadKg = new Random().nextInt(3) + 7; // Entre 7 y 9 kg
        this.precioCiclo = 2.50;
    }

    public int getNumero() {
        return numero;
    }

    public int getCapacidadKg() {
        return capacidadKg;
    }

    public double getPrecioCiclo() {
        return precioCiclo;
    }

    @Override
    public String toString() {
        return "Secadora #" + numero + " (" + capacidadKg + "kg)";
    }
}

/**
 * RF2 - Gestiona las máquinas de la lavandería
 */
class Lavanderia {
    private final int totalLavadoras;
    private final int totalSecadoras;
    private int lavadorasDisponibles;
    private int secadorasDisponibles;
    private int ciclosLavadoCompletados = 0;
    private int ciclosSecadoCompletados = 0;
    private double recaudacionTotal = 0;

    public Lavanderia(int numLavadoras, int numSecadoras) {
        this.totalLavadoras = numLavadoras;
        this.totalSecadoras = numSecadoras;
        this.lavadorasDisponibles = numLavadoras;
        this.secadorasDisponibles = numSecadoras;
        
        System.out.println("🏪 Lavandería abierta:");
        System.out.println("   🧼 " + numLavadoras + " lavadoras disponibles");
        System.out.println("   🌀 " + numSecadoras + " secadoras disponibles");
        System.out.println("==================================================\n");
    }

    public synchronized Lavadora ocuparLavadora(String cliente) {
        while (lavadorasDisponibles == 0) {
            try {
                System.out.println("⏳ " + cliente + " esperando por una lavadora...");
                mostrarEstado();
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        lavadorasDisponibles--;
        int numeroLavadora = totalLavadoras - lavadorasDisponibles;
        Lavadora lavadora = new Lavadora(numeroLavadora);
        
        System.out.println("==================================================");
        System.out.println("🧼 " + cliente + " está usando " + lavadora);
        mostrarEstado();
        
        notifyAll();
        return lavadora;
    }

    public synchronized void liberarLavadora(Lavadora lavadora, String cliente) {
        lavadorasDisponibles++;
        ciclosLavadoCompletados++;
        recaudacionTotal += lavadora.getPrecioCiclo();
        
        System.out.println("==================================================");
        System.out.println("✅ " + cliente + " ha terminado con " + lavadora);
        System.out.println("💰 Pagado: $" + String.format("%.2f", lavadora.getPrecioCiclo()));
        mostrarEstado();
        
        notifyAll();
    }

    public synchronized Secadora ocuparSecadora(String cliente) {
        while (secadorasDisponibles == 0) {
            try {
                System.out.println("⏳ " + cliente + " esperando por una secadora...");
                mostrarEstado();
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        secadorasDisponibles--;
        int numeroSecadora = totalSecadoras - secadorasDisponibles;
        Secadora secadora = new Secadora(numeroSecadora);
        
        System.out.println("==================================================");
        System.out.println("🌀 " + cliente + " está usando " + secadora);
        mostrarEstado();
        
        notifyAll();
        return secadora;
    }

    public synchronized void liberarSecadora(Secadora secadora, String cliente) {
        secadorasDisponibles++;
        ciclosSecadoCompletados++;
        recaudacionTotal += secadora.getPrecioCiclo();
        
        System.out.println("==================================================");
        System.out.println("✅ " + cliente + " ha terminado con " + secadora);
        System.out.println("💰 Pagado: $" + String.format("%.2f", secadora.getPrecioCiclo()));
        mostrarEstado();
        
        notifyAll();
    }

    private void mostrarEstado() {
        System.out.println("📊 Estado de la lavandería:");
        System.out.println("   🧼 Lavadoras: " + lavadorasDisponibles + "/" + totalLavadoras + " disponibles");
        System.out.println("   🌀 Secadoras: " + secadorasDisponibles + "/" + totalSecadoras + " disponibles");
        System.out.println("   📈 Ciclos completados: " + ciclosLavadoCompletados + " lavados, " + ciclosSecadoCompletados + " secados");
        System.out.println("   💵 Recaudación: $" + String.format("%.2f", recaudacionTotal));
        System.out.println("==================================================\n");
    }
}

/**
 * RF3 - Hilos que representan clientes
 */
class HiloCliente extends Thread {
    private final Lavanderia lavanderia;
    private double dineroGastado = 0;

    public HiloCliente(String nombre, Lavanderia lavanderia) {
        super(nombre);
        this.lavanderia = lavanderia;
    }

    @Override
    public void run() {
        System.out.println("👤 " + getName() + " ha entrado a la lavandería");
        System.out.println("==================================================\n");

        try {
            // FASE 1: LAVADO
            Lavadora lavadora = lavanderia.ocuparLavadora(getName());
            dineroGastado += lavadora.getPrecioCiclo();
            
            // Simular ciclo de lavado
            int tiempoLavado = new Random().nextInt(2000) + 1500;
            System.out.println("🧼 " + getName() + " lavando ropa (" + (tiempoLavado/1000) + " segundos)...");
            System.out.println("==================================================\n");
            Thread.sleep(tiempoLavado);
            
            lavanderia.liberarLavadora(lavadora, getName());

            // Pequeña pausa entre lavado y secado
            Thread.sleep(300);

            // FASE 2: SECADO
            Secadora secadora = lavanderia.ocuparSecadora(getName());
            dineroGastado += secadora.getPrecioCiclo();
            
            // Simular ciclo de secado
            int tiempoSecado = new Random().nextInt(1500) + 1000;
            System.out.println("🌀 " + getName() + " secando ropa (" + (tiempoSecado/1000) + " segundos)...");
            System.out.println("==================================================\n");
            Thread.sleep(tiempoSecado);
            
            lavanderia.liberarSecadora(secadora, getName());

            System.out.println("✨ " + getName() + " ha terminado. Total gastado: $" + String.format("%.2f", dineroGastado));
            System.out.println("==================================================\n");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("👋 " + getName() + " se va con su ropa limpia");
        System.out.println("==================================================\n");
    }
}
```

## Ejecución Esperada

El programa mostrará:
- Clientes entrando a la lavandería
- Clientes esperando por lavadoras o secadoras
- Proceso de lavado y secado secuencial
- Tiempos de cada ciclo
- Pagos por cada ciclo usado
- Estado de máquinas disponibles en tiempo real
- Recaudación total de la lavandería
- Total de ciclos completados
- Gasto total de cada cliente