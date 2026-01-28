import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * SocketClienteQA_CargaControlada
 *
 * Objetivo:
 * - Prueba sobre cola/turnos y comportamientos raros:
 *   - desconexiones voluntarias
 *   - envío de mensaje vacío (servidor lo marca como inválido)
 *   - envío normal (eco a mayúsculas)
 *   - cliente "lento" (puede provocar expulsión por inactividad del servidor)
 */

public class SocketClienteQA_CargaControlada {
    private static final Random RND = new Random();

    private enum Modo {
        DESCONECTAR_NADA_MAS_CONECTAR,
        DESCONECTAR_EN_COLA,
        ENVIAR_VACIO,
        ENVIAR_NORMAL,
        SIMULAR_LENTO_ANTES_DE_ENVIAR
    }

    public static void main(String[] args) throws Exception {
        final String host = "localhost";
        final int puerto = 6000;

        // Parámetros
        final int clientesTotales = 25;
        final int hilos = 10;

        // Debe cubrir cola + turno (el server usa 30s cola)
        final int timeoutLecturaMs = 25_000;

        ExecutorService pool = Executors.newFixedThreadPool(hilos);
        List<Future<Resultado>> futures = new ArrayList<>();

        try {
            for (int i = 1; i <= clientesTotales; i++) {
                final int id = i;
                final Modo modo = elegirModoAleatorio();

                futures.add(pool.submit(new TareaCliente(host, puerto, id, timeoutLecturaMs, modo)));

                // Pausa para que se vea la cola/turno en consola
                Thread.sleep(120);
            }

            int ok = 0;
            int fail = 0;

            for (Future<Resultado> f : futures) {
                Resultado r = f.get();
                if (r.ok) ok++; else fail++;
                System.out.println(r);
            }

            System.out.println("\nResumen: OK=" + ok + " FAIL=" + fail);

        } finally {
            pool.shutdownNow();
        }
    }

    private static Modo elegirModoAleatorio() {
        int x = RND.nextInt(100);
        if (x < 15) return Modo.DESCONECTAR_NADA_MAS_CONECTAR;
        if (x < 30) return Modo.DESCONECTAR_EN_COLA;
        if (x < 45) return Modo.ENVIAR_VACIO;
        if (x < 80) return Modo.ENVIAR_NORMAL;
        return Modo.SIMULAR_LENTO_ANTES_DE_ENVIAR;
    }

    private static class TareaCliente implements Callable<Resultado> {
        private final String host;
        private final int puerto;
        private final int id;
        private final int timeoutLecturaMs;
        private final Modo modo;

        TareaCliente(String host, int puerto, int id, int timeoutLecturaMs, Modo modo) {
            this.host = host;
            this.puerto = puerto;
            this.id = id;
            this.timeoutLecturaMs = timeoutLecturaMs;
            this.modo = modo;
        }

        @Override
        public Resultado call() {
            long t0 = System.currentTimeMillis();
            String ultimoEstado = "";

            try (Socket socket = new Socket()) {
                socket.connect(new InetSocketAddress(host, puerto), 5_000);
                socket.setSoTimeout(timeoutLecturaMs);

                // Caso 1: conectar y cerrar sin hacer nada
                if (modo == Modo.DESCONECTAR_NADA_MAS_CONECTAR) {
                    // cerramos explícitamente para que sea inmediato
                    socket.close();
                    return Resultado.ok(id, "Se conectó y cerró inmediatamente", t0, "");
                }

                try (DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                     DataInputStream in = new DataInputStream(socket.getInputStream())) {

                    // Esperar turno
                    while (true) {
                        String msg = in.readUTF();
                        ultimoEstado = msg;

                        // Caso 2: desconectar mientras está en cola
                        if (modo == Modo.DESCONECTAR_EN_COLA && msg.startsWith("Servidor ocupado")) {
                            socket.close();
                            return Resultado.ok(id, "Se desconectó estando en cola", t0, ultimoEstado);
                        }

                        // Si el servidor nos corta por timeout/expulsión/etc.
                        if (msg.startsWith("Timeout") || msg.startsWith("Cancelado") || msg.startsWith("Expulsado") || msg.startsWith("IP bloqueada") || msg.startsWith("Servidor saturado") || msg.startsWith("Servidor ocupado: cola interna")) {
                            return Resultado.fail(id, "Finalizado por server: " + msg, t0, ultimoEstado);
                        }

                        if (msg.startsWith("Tu turno")) {
                            break;
                        }
                    }

                    // Caso 3: simular cliente lento ANTES de enviar
                    if (modo == Modo.SIMULAR_LENTO_ANTES_DE_ENVIAR) {
                        int espera = 5_000 + RND.nextInt(12_000); // 5-17s
                        Thread.sleep(espera);
                    }

                    String payload = (modo == Modo.ENVIAR_VACIO) ? "" : ("hola carga#" + id);

                    out.writeUTF(payload);
                    out.flush();

                    // El servidor en estos casos responde y normalmente cierra (mensajes inválidos / expulsión)
                    final String respuesta;
                    try {
                        respuesta = in.readUTF();
                    } catch (EOFException e) {
                        return Resultado.fail(id, "Servidor cerró sin respuesta (EOF)", t0, ultimoEstado);
                    }

                    if (modo == Modo.ENVIAR_NORMAL) {
                        boolean ok = respuesta.equals(payload.toUpperCase());
                        if (!ok) {
                            return Resultado.fail(id, "Respuesta inesperada: '" + respuesta + "'", t0, ultimoEstado);
                        }
                        return Resultado.ok(id, "OK -> " + respuesta, t0, ultimoEstado);
                    }

                    // En vacío/lento puede llegar un mensaje tipo "Mensaje inválido" o "Expulsado..."
                    return Resultado.ok(id, "Respuesta recibida: '" + respuesta + "'", t0, ultimoEstado);
                }

            } catch (SocketTimeoutException e) {
                return Resultado.fail(id, "Timeout en cliente", t0, ultimoEstado);
            } catch (Exception e) {
                return Resultado.fail(id, "Error: " + e.getClass().getSimpleName() + ": " + e.getMessage(), t0, ultimoEstado);
            }
        }
    }

    private static class Resultado {
        final int id;
        final boolean ok;
        final long ms;
        final String detalle;
        final String ultimoEstado;

        private Resultado(int id, boolean ok, long ms, String detalle, String ultimoEstado) {
            this.id = id;
            this.ok = ok;
            this.ms = ms;
            this.detalle = detalle;
            this.ultimoEstado = ultimoEstado;
        }

        static Resultado ok(int id, String detalle, long t0, String ultimoEstado) {
            return new Resultado(id, true, System.currentTimeMillis() - t0, detalle, ultimoEstado);
        }

        static Resultado fail(int id, String detalle, long t0, String ultimoEstado) {
            return new Resultado(id, false, System.currentTimeMillis() - t0, detalle, ultimoEstado);
        }

        @Override
        public String toString() {
            return "[QA] id=" + id + " ok=" + ok + " ms=" + ms +
                    " ultimoEstado='" + ultimoEstado + "' detalle='" + detalle + "'";
        }
    }
}
