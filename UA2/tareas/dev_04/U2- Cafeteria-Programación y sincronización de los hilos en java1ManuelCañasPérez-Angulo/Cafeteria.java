import java.util.List;
import java.util.ArrayList;
import java.util.Random;

class TazaCafe {

    public static final int TAMANIO_MEDIANO = 0;
    public static final double LITROS_MEDIANO = 0.5;
    public static final int TAMANIO_GRANDE = 1;
    public static final double LITROS_GRANDE = 1.0;

    private int identificador;
    private int formato;

    public TazaCafe(int identificador, int formato) {
        System.out.println("[TazaCafe] Fabricando contenedor Nro:" + identificador + ", Formato:" + formato);
        this.identificador = identificador;
        if (formato != TAMANIO_MEDIANO && formato != TAMANIO_GRANDE) {
            this.formato = TAMANIO_MEDIANO;
        } else {
            this.formato = formato;
        }
    }

    public int getIdentificador() {
        return identificador;
    }

    public void setIdentificador(int identificador) {
        this.identificador = identificador;
    }

    public int getFormato() {
        return formato;
    }

    public void setFormato(int formato) {
        this.formato = formato;
    }

    public double getCapacidadLitros() {
        return (this.formato == TAMANIO_GRANDE) ? LITROS_GRANDE : LITROS_MEDIANO;
    }

    @Override
    public String toString() {
        String etiqueta = (formato == TAMANIO_GRANDE) ? "Grande (1.0L)" : "Mediano (0.5L)";
        return "Taza ID=" + identificador + ", Formato=" + etiqueta;
    }
}

class Barista {
    private String apodo;
    private final List<TazaCafe> stockTazas;
    private final Random generadorAzar = new Random();

    public Barista(String apodo) {
        this.apodo = apodo;
        this.stockTazas = new ArrayList<>();
        System.out.println(">>> Barista " + apodo + ": Configurando vajilla inicial (3 unidades).");

        int indice = 0;
        while (indice < 3) {
            TazaCafe tazaNueva = new TazaCafe(indice, generadorAzar.nextInt(2));
            stockTazas.add(tazaNueva);
            indice++;
        }
        System.out.println(">>> Barista " + apodo + ": Inicialización concluida.");
        mostrarStock();
    }

    public synchronized TazaCafe despacharPedido(String clienteTag) throws InterruptedException {
        System.out.println("\n[SOLICITUD] " + clienteTag + " requiere servicio de " + apodo + ".");

        while (stockTazas.isEmpty()) {
            System.out.println("¡¡¡ Barista " + apodo + ": SIN VAJILLA DISPONIBLE. " + clienteTag + " retenido en espera...");
            try {
                wait();
            } catch (InterruptedException ex) {
                System.err.println("Barista " + apodo + ": Interrupción crítica en suspensión de despacho.");
                throw ex;
            }
        }

        int posicionAleatoria = generadorAzar.nextInt(stockTazas.size());
        TazaCafe tazaAsignada = stockTazas.get(posicionAleatoria);
        stockTazas.remove(posicionAleatoria);

        System.out.println("[ENTREGA] Barista " + apodo + ": Despacha " + tazaAsignada + " a " + clienteTag);
        notifyAll();

        return tazaAsignada;
    }

    public synchronized void reponerVajilla(TazaCafe tazaDevuelta, String clienteTag) {
        System.out.println("[DEVOLUCION] El usuario " + clienteTag + " regresa " + tazaDevuelta);

        // Se reasigna el formato aleatoriamente al limpiarse/reciclarse
        int dimensionNueva = generadorAzar.nextInt(2);
        tazaDevuelta.setFormato(dimensionNueva);

        stockTazas.add(tazaDevuelta);
        System.out.println("[DEVOLUCION] Barista " + apodo + ": Contenedor lavado y reconfigurado (Formato " + tazaDevuelta.getFormato() + ").");
        notifyAll();
        mostrarStock();
    }

    public void mostrarStock() {
        System.out.println("=== MONITOREO: " + apodo + " retiene [" + stockTazas.size() + "] tazas limpias ===");
    }
}

class HiloConsumidor extends Thread {

    private final Barista baristaAsignado;
    private double volumenConsumidoAcumulado = 0.0;

    public HiloConsumidor(String alias, Barista baristaAsignado) {
        super(alias);
        this.baristaAsignado = baristaAsignado;
        System.out.println("[Consumidor] " + alias + ": Registrado en el sistema.");
    }

    @Override
    public void run() {
        System.out.println("--- Consumidor " + getName() + ": Activo y operativo ---");

        while (true) {
            TazaCafe tazaEnUso = null;
            try {
                // 1. Solicitar recurso al monitor coordinado
                tazaEnUso = baristaAsignado.despacharPedido(getName());

                // 2. Simulación de procesamiento/consumo del recurso
                System.out.println("[CONSUMIENDO] " + getName() + ": Bebiendo contenido de " + tazaEnUso + "...");
                Thread.sleep(200);

                // 3. Incremento métrico individual
                volumenConsumidoAcumulado += tazaEnUso.getCapacidadLitros();
                System.out.printf("[METRICA] Usuario %s: Volumen total ingerido: %.1f L%n", getName(), volumenConsumidoAcumulado);

                // 4. Liberación del recurso al monitor
                baristaAsignado.reponerVajilla(tazaEnUso, getName());

                // 5. Período de inactividad antes de la siguiente petición
                Random temporizador = new Random();
                int retardo = temporizador.nextInt(750) + 250;
                System.out.println("Consumidor " + getName() + ": Descansando por " + retardo + " ms");
                Thread.sleep(retardo);

            } catch (InterruptedException e) {
                System.err.println("Consumidor " + getName() + ": Operación abortada mediante interrupción.");
                if (tazaEnUso != null) {
                    baristaAsignado.reponerVajilla(tazaEnUso, getName());
                }
                Thread.currentThread().interrupt();
                return;
            }
        }
    }
}

public class Cafeteria {

    public static void main(String[] args) {
        System.out.println("Establecimiento Central de Café");

        Barista encargado = new Barista("Esteban");
        List<HiloConsumidor> grupoUsuarios = new ArrayList<>();
        
        grupoUsuarios.add(new HiloConsumidor("Homer", encargado));
        grupoUsuarios.add(new HiloConsumidor("Barney", encargado));
        grupoUsuarios.add(new HiloConsumidor("Carl", encargado));
        grupoUsuarios.add(new HiloConsumidor("Lenny", encargado));
        grupoUsuarios.add(new HiloConsumidor("Lurieen", encargado));

        System.out.println("--- Lanzando subprocesos concurrentes de usuarios ---");

        for (HiloConsumidor usuario : grupoUsuarios) {
            usuario.start();
        }

        try {
            System.out.println("--- Temporizador de simulación fijado en 10 segundos ---");
            Thread.sleep(10000);

            // Envío de señales de interrupción a todos los hilos activos
            for (HiloConsumidor usuario : grupoUsuarios) {
                usuario.interrupt();
            }

            // Bloqueo de sincronización hasta la parada completa de los hilos
            for (HiloConsumidor usuario : grupoUsuarios) {
                usuario.join();
            }

        } catch (InterruptedException exPrincipal) {
            System.err.println("Excepción crítica en el hilo maestro de ejecución.");
            Thread.currentThread().interrupt();
        }
        
        System.out.println("Fin de Simulacion");
    }
}