import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Clase principal que contiene la lógica de simulación del Bar.
 * Implementa FR4 y parte de FR5 (fichero único).
 */
public class BarMoe {

    // --- FR1: Clase VasoCerveza ---

    /**
     * Representa el elemento que será consumido y devuelto.
     * Tipo 0: media pinta (aprox. 0.284 litros).
     * Tipo 1: pinta (aprox. 0.568 litros).
     */
    static class VasoCerveza {
        private int id;
        private int tipo;
        private double volumenLitros; // Volumen real en litros para la contabilidad

        // Constantes de volumen (aproximaciones)
        private static final double VOLUMEN_MEDIA_PINTA = 0.284;
        private static final double VOLUMEN_PINTA = 0.568;
        private static int nextId = 0; // Para el identificador único

        /**
         * Constructor de VasoCerveza.
         * @param tipo El tipo de vaso (0: media pinta, 1: pinta).
         */
        public VasoCerveza(int tipo) {
            try {
                this.id = nextId++;
                this.tipo = tipo;
                this.volumenLitros = (tipo == 0) ? VOLUMEN_MEDIA_PINTA : VOLUMEN_PINTA;
                System.out.println("[VasoCerveza: " + id + "] Constructor ejecutado. Tipo: " + this.getTipoString() + " (" + this.volumenLitros + " L)");
            } catch (Exception e) {
                System.err.println("[VasoCerveza: " + id + "] Error en el constructor: " + e.getMessage());
            }
        }

        // Getters
        public int getId() {
            System.out.println("[VasoCerveza: " + id + "] Getter de ID ejecutado.");
            return id;
        }

        public int getTipo() {
            System.out.println("[VasoCerveza: " + id + "] Getter de Tipo ejecutado.");
            return tipo;
        }

        public double getVolumenLitros() {
            System.out.println("[VasoCerveza: " + id + "] Getter de Volumen ejecutado.");
            return volumenLitros;
        }

        // Setter (incluido por requerimiento, aunque el tipo no debería cambiarse)
        public void setTipo(int tipo) {
            System.out.println("[VasoCerveza: " + id + "] Setter de Tipo ejecutado. Tipo anterior: " + this.tipo + ", Nuevo Tipo: " + tipo);
            this.tipo = tipo;
            this.volumenLitros = (tipo == 0) ? VOLUMEN_MEDIA_PINTA : VOLUMEN_PINTA;
        }

        // Método auxiliar para imprimir el tipo de vaso
        private String getTipoString() {
            return tipo == 0 ? "Media Pinta" : "Pinta";
        }

        /**
         * Método toString.
         * @return Representación en cadena del vaso.
         */
        @Override
        public String toString() {
            System.out.println("[VasoCerveza: " + id + "] Método toString ejecutado.");
            return "VasoCerveza [ID=" + id + ", Tipo=" + getTipoString() + ", Volumen=" + String.format("%.3f", volumenLitros) + " L]";
        }
    }

    // --- FR2: Clase Camarero ---

    /**
     * Simula la persona encargada de servir y devolver vasos de cerveza.
     * Es el objeto compartido y por tanto debe gestionar la sincronización.
     */
    static class Camarero {
        private String nombre;
        private List<VasoCerveza> listaVasos;
        private Random random;

        /**
         * Constructor de Camarero.
         * Crea 3 vasos iniciales de tipo aleatorio.
         * @param nombre El nombre del camarero.
         */
        public Camarero(String nombre) {
            try {
                this.nombre = nombre;
                this.listaVasos = new ArrayList<>();
                this.random = new Random();

                // Crear 3 vasos iniciales (tipo aleatorio 0 o 1)
                for (int i = 0; i < 3; i++) {
                    int tipo = random.nextInt(2); // 0 o 1
                    listaVasos.add(new VasoCerveza(tipo));
                }

                System.out.println("--------------------------------------------------------------------------------");
                System.out.println("[CAMARERO: " + nombre + "] Constructor ejecutado. Listo para servir con 3 vasos iniciales.");
                contarVasos();
                System.out.println("--------------------------------------------------------------------------------");
            } catch (Exception e) {
                System.err.println("[CAMARERO: " + nombre + "] Error en el constructor: " + e.getMessage());
            }
        }

        /**
         * Elige un vaso aleatoriamente de la lista, lo saca de ella y lo entrega al cliente.
         * Sincronizado para acceso seguro a la listaVasos.
         * Utiliza wait() si no hay vasos disponibles.
         * @return El vaso de cerveza servido.
         * @throws InterruptedException Si el hilo es interrumpido mientras espera.
         */
        public synchronized VasoCerveza servirCerveza() throws InterruptedException {
            VasoCerveza vasoServido = null;
            try {
                System.out.println("[CAMARERO: " + nombre + "] [PETICIÓN] Comprobando disponibilidad de vasos...");

                // Mientras la lista esté vacía, el camarero espera (y el cliente espera).
                while (listaVasos.isEmpty()) {
                    System.out.println("[CAMARERO: " + nombre + "] ¡¡ATENCIÓN!! No quedan vasos! Esperando a que devuelvan uno. (listaVasos.size: 0)");
                    // El hilo del cliente se bloquea y libera el monitor.
                    wait();
                    System.out.println("[CAMARERO: " + nombre + "] Despertado! Un vaso ha sido devuelto. Intentando servir...");
                }

                // Elegir un vaso aleatoriamente
                int indice = random.nextInt(listaVasos.size());
                vasoServido = listaVasos.remove(indice);

                System.out.println("[CAMARERO: " + nombre + "] >> Vaso " + vasoServido.getId() + " (" + vasoServido.getTipoString() + ") servido. Vasos restantes: " + listaVasos.size());
                contarVasos();

            } catch (InterruptedException e) {
                System.err.println("[CAMARERO: " + nombre + "] ERROR: El camarero fue interrumpido mientras esperaba un vaso. " + e.getMessage());
                throw e; // Relanza la excepción
            } catch (Exception e) {
                System.err.println("[CAMARERO: " + nombre + "] ERROR inesperado sirviendo cerveza: " + e.getMessage());
            }
            return vasoServido;
        }

        /**
         * Inserta de nuevo en la lista el vaso devuelto por el cliente.
         * Sincronizado para acceso seguro a la listaVasos.
         * Utiliza notifyAll() para despertar a los clientes que puedan estar esperando.
         * @param vaso El vaso de cerveza a devolver.
         */
        public synchronized void devolverCerveza(VasoCerveza vaso) {
            try {
                listaVasos.add(vaso);
                System.out.println("[CAMARERO: " + nombre + "] << Vaso " + vaso.getId() + " devuelto. Total de vasos: " + listaVasos.size());

                // Despertar a todos los hilos que puedan estar esperando en wait()
                notifyAll();
                System.out.println("[CAMARERO: " + nombre + "] Hilos clientes notificados sobre la devolución del vaso.");
            } catch (Exception e) {
                System.err.println("[CAMARERO: " + nombre + "] ERROR inesperado devolviendo cerveza: " + e.getMessage());
            }
        }

        /**
         * Imprime los vasos disponibles en el bar.
         */
        public void contarVasos() {
            System.out.print("[CAMARERO: " + nombre + "] Vasos disponibles (" + listaVasos.size() + "): {");
            for (int i = 0; i < listaVasos.size(); i++) {
                VasoCerveza v = listaVasos.get(i);
                System.out.print("Vaso " + v.getId() + "(" + v.getTipoString() + ")" + (i < listaVasos.size() - 1 ? ", " : ""));
            }
            System.out.println("}");
        }
    }

    // --- FR3: Clase HilosClientes ---

    /**
     * Hilo que simula a un cliente bebiendo cerveza.
     * Repite infinitamente el ciclo de pedir, beber y devolver.
     */
    static class HilosClientes extends Thread {
        private Camarero camarero;
        private double totalCervezaBebida = 0.0;
        private Random random;

        /**
         * Constructor de HilosClientes.
         * @param nombre Nombre del cliente (hilo).
         * @param camarero Objeto compartido del camarero.
         */
        public HilosClientes(String nombre, Camarero camarero) {
            super(nombre); // Asignar nombre al hilo
            this.camarero = camarero;
            this.random = new Random();
            System.out.println("[CLIENTE: " + getName() + "] Constructor ejecutado. Asignado al camarero.");
        }

        /**
         * Lógica de ejecución del hilo (Ciclo de vida del bebedor).
         */
        @Override
        public void run() {
            System.out.println("[CLIENTE: " + getName() + "] Hilo está ejecutándose. ¡A beber!");
            int ciclo = 1;

            while (true) {
                VasoCerveza vaso = null;
                long tiempoEsperaMs = 0;

                try {
                    // 1. Pedir un vaso de Cerveza
                    System.out.println("\n--- CICLO " + ciclo + " ---");
                    System.out.println("[CLIENTE: " + getName() + "] Pidiendo un vaso de cerveza...");
                    vaso = camarero.servirCerveza();

                    if (vaso != null) {
                        System.out.println("[CLIENTE: " + getName() + "] ¡Vaso " + vaso.getId() + " recibido! Contiene: " + String.format("%.3f", vaso.getVolumenLitros()) + " L");

                        // 2. Beber la cerveza
                        // Simular el proceso de beber con una pausa corta
                        Thread.sleep(random.nextInt(100) + 50);

                        // 3. Contabilizar la cantidad total de cerveza bebida (en LITROS)
                        totalCervezaBebida += vaso.getVolumenLitros();
                        System.out.println("[CLIENTE: " + getName() + "] ¡Cerveza bebida! Total acumulado: " + String.format("%.3f", totalCervezaBebida) + " L");

                        // 4. Devolver el vaso de Cerveza
                        camarero.devolverCerveza(vaso);
                    } else {
                        // Esto no debería ocurrir con la sincronización implementada (wait/notify)
                        System.err.println("[CLIENTE: " + getName() + "] ERROR: El camarero devolvió un vaso nulo.");
                    }

                    // 5. Esperar antes de pedir otra Cerveza (dormir)
                    tiempoEsperaMs = random.nextInt(1251) + 250; // Aleatorio entre 250 ms y 1000 ms
                    System.out.println("[CLIENTE: " + getName() + "] Esperando " + tiempoEsperaMs + " ms antes de pedir la siguiente...");
                    Thread.sleep(tiempoEsperaMs);

                } catch (InterruptedException e) {
                    System.err.println("[CLIENTE: " + getName() + "] Hilo interrumpido. Terminando bucle de consumo.");
                    Thread.currentThread().interrupt();
                    break;
                } catch (Exception e) {
                    System.err.println("[CLIENTE: " + getName() + "] ERROR inesperado en el ciclo de consumo: " + e.getMessage());
                }
                ciclo++;
            }
        }
    }

    // --- FR4: Aplicación (main) ---

    public static void main(String[] args) {
        System.out.println("--- INICIO DE LA SIMULACIÓN DEL BAR DE MOE ---");

        try {
            // Generar el Camarero (objeto compartido)
            Camarero mou = new Camarero("Mou");

            // Crear los Clientes (Hilos)
            List<HilosClientes> clientes = new ArrayList<>();
            clientes.add(new HilosClientes("Homer", mou));
            clientes.add(new HilosClientes("Barney", mou));
            clientes.add(new HilosClientes("Carl", mou));
            clientes.add(new HilosClientes("Lenny", mou));
            clientes.add(new HilosClientes("Lurleen", mou));

            // Iniciar los hilos de los Clientes
            System.out.println("\n--- INICIANDO CLIENTES ---");
            for (HilosClientes cliente : clientes) {
                cliente.start();
            }

            // Dejar que la simulación se ejecute por un tiempo (ej. 10 segundos)
            // Esto permite realizar varias pruebas de ejecución.
            int tiempoSimulacionSegundos = 10;
            System.out.println("\n--- SIMULACIÓN ACTIVA POR " + tiempoSimulacionSegundos + " SEGUNDOS... ---");
            Thread.sleep(tiempoSimulacionSegundos * 3000);

            // Detener los hilos de los Clientes (interrumpiéndolos)
            System.out.println("\n--- FIN DE LA SIMULACIÓN. INTERRUMPIENDO CLIENTES... ---");
            for (HilosClientes cliente : clientes) {
                cliente.interrupt();
            }

            // Esperar a que todos los hilos terminen
            for (HilosClientes cliente : clientes) {
                cliente.join(3000); // Espera máxima de 1 segundo
            }

            // Mostrar el resultado final
            System.out.println("\n--- RESULTADOS FINALES DE CONSUMO ---");
            double totalBar = 0.0;
            for (HilosClientes cliente : clientes) {
                System.out.println("  " + cliente.getName() + " bebió un total de: " + String.format("%.3f", cliente.totalCervezaBebida) + " L");
                totalBar += cliente.totalCervezaBebida;
            }
            System.out.println("----------------------------------------");
            System.out.println("TOTAL DE CERVEZA SERVIDA EN EL BAR: " + String.format("%.3f", totalBar) + " L");

        } catch (InterruptedException e) {
            // Manejo de errores si el hilo principal es interrumpido
            System.err.println("ERROR CRÍTICO: La simulación fue interrumpida. " + e.getMessage());
        } catch (Exception e) {
            // Manejo de otros errores generales
            System.err.println("ERROR GENERAL en la aplicación principal: " + e.getMessage());
            e.printStackTrace();
        } finally {
            System.out.println("--- SIMULACIÓN FINALIZADA ---");
        }
    }
}
