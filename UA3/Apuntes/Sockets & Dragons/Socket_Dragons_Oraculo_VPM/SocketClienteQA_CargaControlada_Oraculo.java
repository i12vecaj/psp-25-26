import java.io.EOFException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
 * SocketClienteQA_CargaControlada_Oraculo
 *
 * Objetivo:
 *   - conectar y desconectar
 *   - desconectar estando en cola
 *   - enviar un Numeros válido
 *   - enviar un objeto inválido (String)
 *   - esperar demasiado antes de enviar (posible expulsión AFK)
 */
public class SocketClienteQA_CargaControlada_Oraculo {
    private static final Random RND = new Random();

    private enum Modo {
        DESCONECTAR_NADA_MAS_CONECTAR,
        DESCONECTAR_EN_COLA,
        ENVIAR_NUMEROS_VALIDO,
        ENVIAR_OBJETO_INVALIDO,
        SIMULAR_LENTO_ANTES_DE_ENVIAR
    }

    public static void main(String[] args) throws Exception {
        final String host = "localhost";
        final int puerto = 6000;

        final int clientesTotales = 20;
        final int hilos = 8;
        final int timeoutLecturaMs = 25_000;

        ExecutorService pool = Executors.newFixedThreadPool(hilos);
        List<Future<Resultado>> futures = new ArrayList<>();

        try {
            for (int i = 1; i <= clientesTotales; i++) {
                final int id = i;
                final Modo modo = elegirModoAleatorio();
                futures.add(pool.submit(new TareaCliente(host, puerto, id, timeoutLecturaMs, modo)));
                Thread.sleep(120);
            }

            int ok = 0;
            int fail = 0;

            for (Future<Resultado> f : futures) {
                Resultado r = f.get();
                if (r.ok) ok++; else fail++;
                System.out.println(r);
            }

            System.out.println("\nResumen DEMO ORACULO: OK=" + ok + " FAIL=" + fail);

        } finally {
            pool.shutdownNow();
        }
    }

    private static Modo elegirModoAleatorio() {
        int x = RND.nextInt(100);
        if (x < 15) return Modo.DESCONECTAR_NADA_MAS_CONECTAR;
        if (x < 30) return Modo.DESCONECTAR_EN_COLA;
        if (x < 70) return Modo.ENVIAR_NUMEROS_VALIDO;
        if (x < 85) return Modo.ENVIAR_OBJETO_INVALIDO;
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

                if (modo == Modo.DESCONECTAR_NADA_MAS_CONECTAR) {
                    socket.close();
                    return Resultado.ok(id, "Se conectó y cerró inmediatamente (demo)", t0, "");
                }

                // Primero salida y luego entrada para evitar deadlock
                try (ObjectOutputStream salida = new ObjectOutputStream(socket.getOutputStream());
                     ObjectInputStream entrada = new ObjectInputStream(socket.getInputStream())) {

                    // Esperar turno
                    while (true) {
                        Object msg = entrada.readObject();

                        if (msg instanceof String s) {
                            ultimoEstado = s;

                            if (modo == Modo.DESCONECTAR_EN_COLA && s.startsWith("Servidor ocupado")) {
                                socket.close();
                                return Resultado.ok(id, "Se desconectó estando en cola (demo)", t0, ultimoEstado);
                            }

                            if (s.startsWith("Timeout") || s.startsWith("Cancelado") || s.startsWith("Expulsado")) {
                                return Resultado.fail(id, "Finalizado por server: " + s, t0, ultimoEstado);
                            }

                            if (s.startsWith("Tu turno")) {
                                break;
                            }
                            continue;
                        }

                        // Si llega algo que no es String antes del turno, lo registramos y seguimos
                        ultimoEstado = "(inesperado:" + msg.getClass().getSimpleName() + ")";
                    }

                    if (modo == Modo.SIMULAR_LENTO_ANTES_DE_ENVIAR) {
                        int espera = 5_000 + RND.nextInt(12_000); // 5-17s (server AFK 15s)
                        Thread.sleep(espera);
                    }

                    // Envío según modo
                    Object peticion;
                    if (modo == Modo.ENVIAR_OBJETO_INVALIDO) {
                        peticion = "no soy Numeros";
                    } else {
                        int n = 2 + id;
                        peticion = new Numeros(n);
                    }

                    salida.writeObject(peticion);
                    salida.flush();

                    // Leer respuesta
                    final Object respuesta;
                    try {
                        respuesta = entrada.readObject();
                    } catch (EOFException e) {
                        return Resultado.fail(id, "Servidor cerró sin respuesta (EOF)", t0, ultimoEstado);
                    }

                    if (respuesta instanceof Numeros num) {
                        int n = num.getNumero();
                        long esperadoCuadrado = (long) n * n;
                        long esperadoCubo = (long) n * n * n;

                        boolean ok = (num.getCuadrado() == esperadoCuadrado) && (num.getCubo() == esperadoCubo);
                        if (!ok) {
                            return Resultado.fail(id, "Cálculo incorrecto: " + num, t0, ultimoEstado);
                        }
                        return Resultado.ok(id, "OK -> " + num, t0, ultimoEstado);
                    }

                    // Respuesta tipo String (inválido, expulsión...)
                    return Resultado.ok(id, "Respuesta (demo): " + respuesta, t0, ultimoEstado);
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
            return "[QA_ORACULO] id=" + id + " ok=" + ok + " ms=" + ms +
                    " ultimoEstado='" + ultimoEstado + "' detalle='" + detalle + "'";
        }
    }
}
