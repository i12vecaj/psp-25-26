import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @file ua2ex1.java
 * @brief Versión simplificada de un problema clásico de concurrencia (bar + vasos).
 *
 * Documentación Doxygen-style incluida en clases y métodos.
 *
 * Cómo compilar:
 *   javac ua2ex1.java
 * Cómo ejecutar:
 *   java ua2ex1
 *
 * NOTA: El programa crea un camarero "Mou" y los clientes: Homer, Barney, Carl, Lenny, Lurleen.
 *       Los clientes ejecutan un bucle infinito pidiendo y devolviendo vasos. Para finalizar el
 *       programa, usa Ctrl+C o añade lógica en main para interrumpir hilos si lo deseas.
 */

/**
 * @class VasoCerveza
 * @brief Representa un vaso de cerveza que puede tener dos tipos: media pinta o pinta.
 *
 * Atributos:
 * - id: identificador del vaso (0,1,2,...)
 * - tipo: 0 -> media pinta, 1 -> pinta
 *
 * Métodos: constructor, getters, setters y toString.
 */
class VasoCerveza {
    /** Identificador del vaso */
    private int id;
    /** Tipo: 0 = media pinta, 1 = pinta */
    private int tipo;

    /** Volúmenes (litros) */
    public static final double VOLUMEN_MEDIA_PINTA = 0.284; // aprox. 0.284 L
    public static final double VOLUMEN_PINTA = 0.568;       // aprox. 0.568 L

    /**
     * Constructor.
     * @param id identificador (debe ser >= 0)
     * @param tipo 0 media pinta, 1 pinta
     */
    public VasoCerveza(int id, int tipo) {
        if (id < 0) {
            System.out.println("[VasoCerveza] Error: id inválido (" + id + "), asignando 0");
            this.id = 0;
        } else {
            this.id = id;
        }
        if (tipo != 0 && tipo != 1) {
            System.out.println("[VasoCerveza] Error: tipo inválido (" + tipo + "), asignando 0 (media pinta)");
            this.tipo = 0;
        } else {
            this.tipo = tipo;
        }
        System.out.println("[VasoCerveza] Creado: " + this.toString());
    }

    /** Getter id */
    public int getId() {
        System.out.println("[VasoCerveza] getId() -> " + id);
        return id;
    }

    /** Setter id */
    public void setId(int id) {
        if (id < 0) {
            System.out.println("[VasoCerveza] setId() recibido valor inválido: " + id);
            return;
        }
        System.out.println("[VasoCerveza] setId(" + id + ")");
        this.id = id;
    }

    /** Getter tipo */
    public int getTipo() {
        System.out.println("[VasoCerveza] getTipo() -> " + tipo);
        return tipo;
    }

    /** Setter tipo */
    public void setTipo(int tipo) {
        if (tipo != 0 && tipo != 1) {
            System.out.println("[VasoCerveza] setTipo() recibido valor inválido: " + tipo);
            return;
        }
        System.out.println("[VasoCerveza] setTipo(" + tipo + ")");
        this.tipo = tipo;
    }

    /**
     * Devuelve el volumen en litros según el tipo.
     * @return volumen en litros (double)
     */
    public double getVolumenLitros() {
        double vol = (tipo == 0) ? VOLUMEN_MEDIA_PINTA : VOLUMEN_PINTA;
        System.out.println("[VasoCerveza] getVolumenLitros() -> " + vol + " L (tipo=" + tipo + ")");
        return vol;
    }

    @Override
    public String toString() {
        String nombreTipo = (tipo == 0) ? "Media pinta" : "Pinta";
        return "Vaso[id=" + id + ", tipo=" + nombreTipo + "]";
    }
}

/**
 * @class Camarero
 * @brief Simula al camarero que gestiona los vasos: servir y recibir devoluciones.
 *
 * Atributos:
 * - listaVasos: lista de vasos disponibles
 * - nombre: nombre del camarero
 *
 * Métodos:
 * - constructor: crea 3 vasos con tipo aleatorio y los añade a la lista
 * - servirCerveza: devuelve un VasoCerveza si hay; si no, espera hasta que uno esté disponible
 * - devolverCerveza: inserta un vaso devuelto y notifica a clientes en espera
 * - contarVasos: imprime el estado actual de vasos disponibles
 */
class Camarero {
    private final List<VasoCerveza> listaVasos;
    private final String nombre;
    private final Random rnd = new Random();

    /**
     * Constructor.
     * Crea 3 vasos con id 0..2 y tipo aleatorio (0 o 1)
     * @param nombre Nombre del camarero
     */
    public Camarero(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            System.out.println("[Camarero] Nombre inválido, asignando 'CamareroAnonimo'");
            this.nombre = "CamareroAnonimo";
        } else {
            this.nombre = nombre;
        }
        this.listaVasos = new ArrayList<>();
        System.out.println("[Camarero] Inicializando camarero '" + this.nombre + "' y creando 3 vasos");
        for (int i = 0; i < 3; i++) {
            int tipo = rnd.nextInt(2); // 0 o 1
            VasoCerveza v = new VasoCerveza(i, tipo);
            listaVasos.add(v);
        }
        contarVasos();
    }

    /**
     * Sirve un vaso a un cliente.
     * Si no hay vasos disponibles, espera hasta que uno sea devuelto.
     *
     * @param nombreCliente nombre del cliente que pide el vaso (para mensajes)
     * @return VasoCerveza entregado (no nulo)
     */
    public synchronized VasoCerveza servirCerveza(String nombreCliente) {
        System.out.println("[" + nombre + "] " + nombreCliente + " solicita un vaso.");
        while (listaVasos.isEmpty()) {
            System.out.println("[" + nombre + "] No hay vasos disponibles. " + nombreCliente + " espera...");
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println("[" + nombre + "] servirCerveza interrumpido mientras " + nombreCliente + " esperaba.");
                Thread.currentThread().interrupt(); // preservar estado de interrupción
                return null;
            }
        }

        // Elegir vaso aleatoriamente de la lista
        int idx = rnd.nextInt(listaVasos.size());
        VasoCerveza vaso = listaVasos.remove(idx);
        System.out.println("[" + nombre + "] Entregando " + vaso + " a " + nombreCliente + ". Vasos restantes: " + listaVasos.size());
        return vaso;
    }

    /**
     * Recibe un vaso devuelto por un cliente e inserta en la lista.
     * Notifica a hilos esperando.
     * @param vaso vaso devuelto (no debe ser null)
     * @param nombreCliente nombre del cliente que devuelve (para mensajes)
     */
    public synchronized void devolverCerveza(VasoCerveza vaso, String nombreCliente) {
        if (vaso == null) {
            System.out.println("[" + nombre + "] Error: devolverCerveza() recibió null de " + nombreCliente);
            return;
        }
        System.out.println("[" + nombre + "] " + nombreCliente + " devuelve " + vaso);
        listaVasos.add(vaso);
        System.out.println("[" + nombre + "] Ahora hay " + listaVasos.size() + " vasos disponibles.");
        // Notificar a clientes en espera
        notifyAll();
    }

    /**
     * Imprime los vasos disponibles actualmente.
     */
    public synchronized void contarVasos() {
        System.out.println("[" + nombre + "] Estado actual de vasos (" + listaVasos.size() + "):");
        for (VasoCerveza v : listaVasos) {
            System.out.println("    - " + v.toString());
        }
    }

    /** Getter del nombre */
    public String getNombre() {
        return nombre;
    }
}

/**
 * @class HilosClientes
 * @brief Representa un cliente que pide, bebe y devuelve vasos cíclicamente.
 *
 * Extiende Thread. Recibe en el constructor el nombre del cliente y referencia al Camarero compartido.
 *
 * Algoritmo en run():
 * - Indicar ejecución
 * - Bucle infinito:
 *     - pedir vaso
 *     - beber (simulado con sleep)
 *     - contabilizar litros consumidos (acumulado por cliente)
 *     - devolver vaso
 *     - dormir entre 250 ms y 1000 ms antes de pedir otra cerveza
 */
class HilosClientes extends Thread {
    private final Camarero camarero;
    private double litrosConsumidos = 0.0;
    private final Random rnd = new Random();

    /**
     * Constructor.
     * @param nombre nombre del hilo/cliente
     * @param camarero objeto Camarero compartido (no null)
     */
    public HilosClientes(String nombre, Camarero camarero) {
        super(nombre);
        if (camarero == null) {
            throw new IllegalArgumentException("[HilosClientes] camarero no puede ser null");
        }
        this.camarero = camarero;
        System.out.println("[HilosClientes] Creado cliente: " + nombre + " (hilo).");
    }

    @Override
    public void run() {
        System.out.println("[HilosClientes - " + getName() + "] Iniciando ejecución del cliente.");
        while (!Thread.currentThread().isInterrupted()) {
            // Pedir un vaso
            VasoCerveza vaso = camarero.servirCerveza(getName());
            if (vaso == null) {
                // Si recibimos null es que fuimos interrumpidos mientras esperábamos
                System.out.println("[HilosClientes - " + getName() + "] No se ha podido obtener vaso (null). Saliendo.");
                break;
            }

            // Beber la cerveza (simulación)
            System.out.println("[HilosClientes - " + getName() + "] Empieza a beber de " + vaso);
            try {
                // Simular tiempo de beber entre 400 y 900 ms
                int tiempoBeber = 400 + rnd.nextInt(501);
                Thread.sleep(tiempoBeber);
            } catch (InterruptedException e) {
                System.out.println("[HilosClientes - " + getName() + "] Interrumpido mientras bebía.");
                Thread.currentThread().interrupt();
                // intentar devolver el vaso antes de salir
                camarero.devolverCerveza(vaso, getName());
                break;
            }

            // Contabilizar litros
            double litros = vaso.getVolumenLitros();
            litrosConsumidos += litros;
            System.out.printf("[HilosClientes - %s] Ha bebido %.3f L (esta vez). Total acumulado: %.3f L%n",
                    getName(), litros, litrosConsumidos);

            // Devolver el vaso
            camarero.devolverCerveza(vaso, getName());

            // Esperar antes de pedir otra cerveza (250 - 1000 ms)
            try {
                int espera = 250 + rnd.nextInt(751); // [250,1000]
                System.out.println("[HilosClientes - " + getName() + "] Esperando " + espera + " ms antes de la próxima cerveza.");
                Thread.sleep(espera);
            } catch (InterruptedException e) {
                System.out.println("[HilosClientes - " + getName() + "] Interrumpido mientras esperaba antes de pedir otra cerveza.");
                Thread.currentThread().interrupt();
                break;
            }
        }

        System.out.printf("[HilosClientes - %s] Terminando. Total consumido: %.3f L%n", getName(), litrosConsumidos);
    }

    /** Devuelve litros consumidos hasta el momento */
    public double getLitrosConsumidos() {
        return litrosConsumidos;
    }
}

/**
 * @brief Aplicación principal que crea el camarero y varios clientes.
 *
 * FR4: genera un Camarero "Mou" y clientes: Homer, Barney, Carl, Lenny, Lurleen.
 * Cada cliente recibe la referencia al mismo camarero.
 */
public class ua2ex1 {
    public static void main(String[] args) {
        System.out.println("[Main] Iniciando simulación UA2 BardeMou");

        // Crear camarero
        Camarero mou = new Camarero("Mou");

        // Crear clientes
        String[] nombres = {"Homer", "Barney", "Carl", "Lenny", "Lurleen"};
        List<HilosClientes> clientes = new ArrayList<>();

        for (String nombre : nombres) {
            try {
                HilosClientes c = new HilosClientes(nombre, mou);
                clientes.add(c);
            } catch (IllegalArgumentException e) {
                System.out.println("[Main] Error creando cliente " + nombre + ": " + e.getMessage());
            }
        }

        // Iniciar clientes
        System.out.println("[Main] Arrancando hilos clientes...");
        for (HilosClientes c : clientes) {
            c.start();
        }

        // NOTA: De acuerdo al enunciado los clientes repiten infinitamente.
        // Aquí NO detenemos automáticamente los hilos: para pruebas detén el programa con Ctrl+C
        // Si quieres hacer pruebas controladas, puedes descomentar el bloque siguiente para
        // dejarlo correr N segundos y luego interrumpir los hilos (ejemplo para pruebas).
        /*
        try {
            Thread.sleep(20000); // dejar correr 20s para pruebas
        } catch (InterruptedException e) {
            // ignorar
        }
        System.out.println("[Main] Interrumpiendo clientes para finalizar prueba.");
        for (HilosClientes c : clientes) {
            c.interrupt();
        }
        for (HilosClientes c : clientes) {
            try {
                c.join();
            } catch (InterruptedException e) {
                // ignorar
            }
        }
        System.out.println("[Main] Fin de la prueba controlada.");
        */

        // Si no se usa la prueba controlada, main termina mientras los hilos siguen en ejecución.
        System.out.println("[Main] main() finalizado. (Los hilos clientes siguen ejecutándose si no los has interrumpido)");
    }
}
