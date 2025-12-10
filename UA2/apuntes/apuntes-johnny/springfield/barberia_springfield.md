# Ejercicio 1: Barbería de Springfield

## Enunciado

Simula una barbería donde hay un barbero que atiende a varios clientes. La barbería tiene un número limitado de sillas de espera (3 sillas). Los clientes llegan continuamente y:

- Si hay sillas disponibles, se sientan a esperar
- Si no hay sillas, se van del establecimiento
- El barbero atiende a un cliente a la vez
- Cuando no hay clientes, el barbero duerme
- Cuando llega un cliente y el barbero está durmiendo, lo despierta

**Requisitos Funcionales:**
1. **RF1**: Implementar una clase `Silla` que represente las sillas de espera
2. **RF2**: Implementar una clase `Barbero` que gestione las sillas y atienda clientes (sincronización)
3. **RF3**: Implementar hilos `HiloCliente` que simulen clientes llegando aleatoriamente
4. **RF4**: Contabilizar cuántos clientes ha atendido el barbero
5. **RF5**: Mostrar el estado de las sillas de espera

## Conceptos de Concurrencia Aplicados

- **Sincronización** con `synchronized`
- **wait()** y **notifyAll()** para coordinación entre hilos
- **Problema productor-consumidor** adaptado
- **Sección crítica** en el acceso a recursos compartidos

---

## Solución

```java
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class BarberiaSpringfield {
    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("Bienvenido a la Barbería de Springfield");
        System.out.println("==================================================\n");

        Barbero barbero = new Barbero("Pepe el Barbero", 3);
        
        // Crear múltiples hilos de clientes que llegan continuamente
        for (int i = 1; i <= 8; i++) {
            new HiloCliente("Cliente-" + i, barbero).start();
            try {
                Thread.sleep(new Random().nextInt(500) + 200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

/**
 * RF1 - Representa un cliente en la silla de espera
 */
class Silla {
    private final String nombreCliente;
    private final int numeroSilla;

    public Silla(String nombreCliente, int numeroSilla) {
        this.nombreCliente = nombreCliente;
        this.numeroSilla = numeroSilla;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public int getNumeroSilla() {
        return numeroSilla;
    }

    @Override
    public String toString() {
        return "Silla " + numeroSilla + " ocupada por " + nombreCliente;
    }
}

/**
 * RF2 - Barbero que gestiona las sillas y atiende clientes
 */
class Barbero {
    private final String nombre;
    private final int capacidadSillas;
    private final Queue<String> sillasEspera;
    private int clientesAtendidos = 0;
    private boolean durmiendo = true;

    public Barbero(String nombre, int capacidadSillas) {
        this.nombre = nombre;
        this.capacidadSillas = capacidadSillas;
        this.sillasEspera = new LinkedList<>();
        System.out.println(nombre + " está listo para trabajar.");
        System.out.println("La barbería tiene " + capacidadSillas + " sillas de espera.\n");
    }

    public synchronized void llegarCliente(String nombreCliente) {
        if (sillasEspera.size() >= capacidadSillas) {
            System.out.println("❌ " + nombreCliente + " se va (no hay sillas disponibles)");
            System.out.println("==================================================\n");
            return;
        }

        sillasEspera.add(nombreCliente);
        System.out.println("✓ " + nombreCliente + " se sienta a esperar");
        mostrarEstadoSillas();

        if (durmiendo) {
            System.out.println("⏰ " + nombreCliente + " despierta al barbero");
            durmiendo = false;
        }

        notifyAll();
    }

    public synchronized String siguienteCliente() {
        while (sillasEspera.isEmpty()) {
            try {
                durmiendo = true;
                System.out.println("💤 " + nombre + " se duerme (no hay clientes)");
                System.out.println("==================================================\n");
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        String cliente = sillasEspera.poll();
        System.out.println("==================================================");
        System.out.println("✂️  " + nombre + " está atendiendo a " + cliente);
        mostrarEstadoSillas();
        return cliente;
    }

    public synchronized void terminarCorte(String cliente) {
        clientesAtendidos++;
        System.out.println("==================================================");
        System.out.println("✅ " + nombre + " ha terminado con " + cliente);
        System.out.println("📊 Clientes atendidos hoy: " + clientesAtendidos);
        System.out.println("==================================================\n");
        notifyAll();
    }

    private void mostrarEstadoSillas() {
        System.out.println("🪑 Sillas ocupadas: " + sillasEspera.size() + "/" + capacidadSillas);
        System.out.println("==================================================\n");
    }

    public String getNombre() {
        return nombre;
    }
}

/**
 * RF3 - Hilos que representan a los clientes
 */
class HiloCliente extends Thread {
    private final Barbero barbero;

    public HiloCliente(String nombre, Barbero barbero) {
        super(nombre);
        this.barbero = barbero;
    }

    @Override
    public void run() {
        // El cliente llega a la barbería
        barbero.llegarCliente(getName());

        // Simular que el barbero corta el pelo (solo si el cliente pudo sentarse)
        try {
            Thread.sleep(new Random().nextInt(1000) + 500);
            
            // El barbero atiende al cliente
            String cliente = barbero.siguienteCliente();
            
            // Simular el tiempo de corte
            System.out.println("✂️  Cortando el pelo de " + cliente + "...");
            Thread.sleep(new Random().nextInt(2000) + 1000);
            
            barbero.terminarCorte(cliente);
            
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
```

## Ejecución Esperada

El programa mostrará:
- Clientes llegando y sentándose en las sillas de espera
- Clientes siendo rechazados cuando no hay sillas disponibles
- El barbero durmiendo cuando no hay clientes
- El barbero siendo despertado por los clientes
- Contador de clientes atendidos
- Estado de las sillas de espera en tiempo real