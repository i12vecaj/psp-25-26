package com.ceslopedevega.productorconsumidor.ejemplo3;// Archivo: UA2/examen/dev_X/ua2ex1.java


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// ----------------------------------------------------------------------------------
// FR1 [2 puntos]. Clase VasoCerveza
// ----------------------------------------------------------------------------------

class VasoCerveza {
    /** Identificador único del vaso. */
    private int id;
    /** Tipo de cerveza: 0 para media pinta, 1 para pinta. */
    private int tipo;
    /** Capacidad en litros de una media pinta. */
    private static final double LITROS_MEDIA_PINTA = 0.284;
    /** Capacidad en litros de una pinta (doble de media pinta). */
    private static final double LITROS_PINTA = 0.568;

    public VasoCerveza(int id, int tipo) {
        try {
            if (tipo != 0 && tipo != 1) {
                throw new IllegalArgumentException("Error: El tipo de vaso debe ser 0 (media pinta) o 1 (pinta).");
            }
            this.id = id;
            this.tipo = tipo;
            System.out.println("[VasoCerveza] Creado vaso ID: " + id + ", Tipo: " + getTipoString());
        } catch (IllegalArgumentException e) {
            System.err.println("[VasoCerveza::Constructor] Control de Error: " + e.getMessage());
            // En un caso real, se podría asignar un valor por defecto o re-lanzar la excepción.
            this.id = -1; // Marcar como inválido
            this.tipo = 0;
        }
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public double getCapacidadLitros() {
        return (tipo == 1) ? LITROS_PINTA : LITROS_MEDIA_PINTA;
    }


    public String getTipoString() {
        return (tipo == 1) ? "Pinta" : "Media Pinta";
    }


    @Override
    public String toString() {
        return "VasoCerveza [ID=" + id + ", Tipo=" + getTipoString() + ", Litros=" + String.format("%.3f", getCapacidadLitros()) + "]";
    }
}

// ----------------------------------------------------------------------------------
// FR2 [2 puntos]. Clase Camarero
// ----------------------------------------------------------------------------------


class Camarero {
    /** Nombre del camarero. */
    private String nombre;
    /** Lista que contiene los Vasos de Cerveza disponibles/devueltos (recurso compartido). */
    private List<VasoCerveza> listaVasos;
    /** Contador estático para asignar IDs únicos a los vasos. */
    private static int contadorVasos = 0;
    /** Generador de números aleatorios. */
    private Random random;


    public Camarero(String nombre) {
        this.nombre = nombre;
        this.listaVasos = new ArrayList<>();
        this.random = new Random();
        System.out.println("--- [Camarero::Constructor] El camarero " + nombre + " ha comenzado a trabajar. ---");

        // Inicialización: crear 3 vasos de tipo aleatorio
        for (int i = 0; i < 3; i++) {
            int tipoAleatorio = random.nextInt(2); // 0 o 1
            VasoCerveza vaso = new VasoCerveza(contadorVasos++, tipoAleatorio);
            listaVasos.add(vaso);
        }
        contarVasos();
    }


    public synchronized VasoCerveza servirCerveza() {
        String clienteNombre = Thread.currentThread().getName();

        try {
            // Mientras no haya vasos, el camarero debe esperar (Wait-Notify/NotifyAll)
            while (listaVasos.isEmpty()) {
                System.out.println("[" + nombre + "::servirCerveza] El bar está sin vasos. " + clienteNombre + " debe esperar.");
                // Espera a que un cliente devuelva un vaso (notify/notifyAll)
                wait();
            }

            // Elegir un vaso aleatoriamente
            int indice = random.nextInt(listaVasos.size());
            VasoCerveza vasoServido = listaVasos.remove(indice);

            System.out.println(" [" + nombre + "::servirCerveza] " + nombre + " sirve el vaso " + vasoServido.toString() + " a " + clienteNombre + ".");
            contarVasos();
            return vasoServido;

        } catch (InterruptedException e) {
            System.err.println(" [" + nombre + "::servirCerveza] Error: Hilo interrumpido mientras esperaba vasos.");
            Thread.currentThread().interrupt(); // Reestablecer el estado de interrupción
            return null;
        } catch (Exception e) {
            // Control de errores genérico (ej. si random.nextInt fallase, improbable aquí)
            System.err.println(" [" + nombre + "::servirCerveza] Control de Error Inesperado: " + e.getMessage());
            return null;
        }
    }


    public synchronized void devolverCerveza(VasoCerveza vasoDevuelto) {
        String clienteNombre = Thread.currentThread().getName();

        try {
            listaVasos.add(vasoDevuelto);
            System.out.println("⬅ [" + nombre + "::devolverCerveza] " + clienteNombre + " devuelve el vaso " + vasoDevuelto.getId() + ". " + nombre + " lo recoge.");

            // Notificar a los clientes que puedan estar esperando por un vaso
            notifyAll();
            contarVasos();
        } catch (Exception e) {
            System.err.println(" [" + nombre + "::devolverCerveza] Control de Error Inesperado: Fallo al añadir el vaso a la lista. " + e.getMessage());
        }
    }

    public void contarVasos() {
        System.out.println("ℹ [" + nombre + "::contarVasos] Vasos disponibles en el bar: " + listaVasos.size());
    }
}

// ----------------------------------------------------------------------------------
// FR3 [2 puntos]. Clase HilosClientes
// ----------------------------------------------------------------------------------

class HilosClientes extends Thread {
    /** Referencia al objeto compartido Camarero. */
    private Camarero camarero;
    /** Contador del total de cerveza consumida por este cliente (en litros). */
    private double totalLitrosBebidos;
    /** Generador de números aleatorios para el tiempo de espera. */
    private Random random;

    public HilosClientes(String nombre, Camarero camarero) {
        // Asignar el nombre al hilo usando la función adecuada (super(nombre))
        super(nombre);
        this.camarero = camarero;
        this.totalLitrosBebidos = 0.0;
        this.random = new Random();
    }

    @Override
    public void run() {
        // Indicar que el hilo está ejecutándose
        System.out.println(" [HilosClientes::run] Cliente " + getName() + " está entrando en el bar.");

        // Infinitamente repetir
        while (!Thread.currentThread().isInterrupted()) {
            VasoCerveza vaso = null;
            try {
                // 1. Pedir un vaso de Cerveza
                System.out.println(" [" + getName() + "::run] " + getName() + " pide una cerveza...");
                vaso = camarero.servirCerveza();

                // Si el vaso es null (error en servirCerveza), esperar y continuar el bucle
                if (vaso == null) {
                    System.out.println(" [" + getName() + "::run] " + getName() + " no pudo conseguir un vaso. Reintentando...");
                    Thread.sleep(500); // Esperar un poco antes de reintentar
                    continue;
                }

                // 2. Beber la rica y deliciosa Cerveza
                System.out.println(" [" + getName() + "::run] " + getName() + " está bebiendo su " + vaso.getTipoString() + " (ID: " + vaso.getId() + ")...");
                // Simular el tiempo de consumo
                Thread.sleep(random.nextInt(1000) + 500); // Entre 500ms y 1500ms para beber

                // 3. Ir contabilizando la cantidad total de cerveza bebida (en LITROS)
                double litrosConsumidos = vaso.getCapacidadLitros();
                totalLitrosBebidos += litrosConsumidos;
                System.out.println(" [" + getName() + "::run] " + getName() + " terminó su cerveza. Total bebido: " + String.format("%.3f", totalLitrosBebidos) + " litros.");

                // 4. Devolver el vaso de Cerveza
                camarero.devolverCerveza(vaso);

                // 5. Esperar antes de pedir otra Cerveza (dormir al cliente)
                int tiempoEspera = random.nextInt(751) + 250; // Entre 250 ms y 1000 ms
                System.out.println(" [" + getName() + "::run] " + getName() + " espera " + tiempoEspera + "ms antes de pedir otra...");
                Thread.sleep(tiempoEspera);

            } catch (InterruptedException e) {
                // El hilo fue interrumpido (ej. al terminar la aplicación)
                System.out.println("🛑 [" + getName() + "::run] " + getName() + " ha sido interrumpido y se retira. Total consumido: " + String.format("%.3f", totalLitrosBebidos) + " litros.");
                Thread.currentThread().interrupt(); // Reestablecer el estado de interrupción
                break; // Salir del bucle infinito
            } catch (Exception e) {
                // Control de errores genérico dentro del bucle
                System.err.println("❌ [" + getName() + "::run] Control de Error Inesperado: " + e.getMessage());
                try {
                    Thread.sleep(1000); // Esperar un poco para no saturar
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
    }
}

// ----------------------------------------------------------------------------------
// FR4 [2 puntos] y FR5 [2 puntos]. Clase Aplicacion (main)
// ----------------------------------------------------------------------------------

/**
 * @class AplicacionBar
 * @brief Clase principal (main) que genera el Camarero y los HilosClientes,
 * e inicia la ejecución concurrente.
 */
public class AplicacionBar {

    /**
     * @brief Método principal de la aplicación.
     * @param args Argumentos de la línea de comandos (no utilizados).
     */
    public static void main(String[] args) {
        System.out.println("=================================================");
        System.out.println("        INICIO DE SIMULACIÓN DEL BAR DE MOE        ");
        System.out.println("=================================================");

        // Crear un Camarero, de nombre Mou
        Camarero mou = new Camarero("Mou");

        // Crear los Clientes
        HilosClientes homer = new HilosClientes("Homer", mou);
        HilosClientes barney = new HilosClientes("Barney", mou);
        HilosClientes carl = new HilosClientes("Carl", mou);
        HilosClientes lenny = new HilosClientes("Lenny", mou);
        HilosClientes lurleen = new HilosClientes("Lurleen", mou);

        // Crear una lista de clientes para la gestión
        List<HilosClientes> clientes = List.of(homer, barney, carl, lenny, lurleen);

        // Iniciar todos los hilos
        System.out.println("\n--- 🚀 Iniciando la juerga... ---");
        clientes.forEach(Thread::start);

        // **Pruebas de Ejecución para Garantizar el Funcionamiento Correcto**
        // Permitir que la simulación se ejecute por un tiempo limitado para verificar la sincronización.
        long tiempoSimulacionMs = 15000; // 15 segundos de simulación

        try {
            System.out.println("\n--- ⏳ La simulación se ejecutará por " + (tiempoSimulacionMs / 1000) + " segundos. ---");
            Thread.sleep(tiempoSimulacionMs);

            // Finalizar la simulación
            System.out.println("\n=================================================");
            System.out.println("        FIN DE SIMULACIÓN (15 SEGUNDOS)          ");
            System.out.println("=================================================");

            // Interrumpir todos los hilos clientes para que finalicen su ejecución
            for (HilosClientes cliente : clientes) {
                cliente.interrupt();
            }

            // Esperar a que todos los hilos terminen (opcional, para una terminación limpia)
            for (HilosClientes cliente : clientes) {
                try {
                    cliente.join(2000); // Esperar un máximo de 2 segundos por cliente
                } catch (InterruptedException e) {
                    System.err.println("Error al esperar por el hilo " + cliente.getName());
                }
            }

            System.out.println("\n--- 📊 Resultados Finales de Consumo ---");
            clientes.forEach(cliente -> {
                System.out.println(cliente.getName() + " consumió un total de " + String.format("%.3f", cliente.totalLitrosBebidos) + " litros.");
            });
            mou.contarVasos();

        } catch (InterruptedException e) {
            System.err.println("El hilo principal fue interrumpido.");
            Thread.currentThread().interrupt();
        }
    }
}