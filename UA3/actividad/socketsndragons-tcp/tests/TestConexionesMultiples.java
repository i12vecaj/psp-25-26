package tests;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class TestConexionesMultiples {
    public static void main(String[] args) {
        String host = "localhost";
        int port = 5000;
        int numConexiones = 5000;

        testAbrazoDeLaMuerteConHilos(host, port, numConexiones);
    }

    public static void testAbrazoDeLaMuerteConHilos(String host, int port, int numConexiones) {
        System.out.println(">>> Iniciando ataque sincronizado: " + numConexiones + " hilos");
        System.out.println(">>> Cada conexión enviará: 'Hola de parte del grupo De TCP'");

        List<Socket> socketsAbiertos = Collections.synchronizedList(new ArrayList<>());
        AtomicInteger exitos = new AtomicInteger(0);
        AtomicInteger fallos = new AtomicInteger(0);
        List<Thread> hilos = new ArrayList<>();

        for (int i = 0; i < numConexiones; i++) {
            Thread t = new Thread(() -> {
                try {
                    Socket s = new Socket();
                    s.connect(new InetSocketAddress(host, port), 3000);

                    // --- NUEVA LÓGICA: ENVÍO DEL MENSAJE ---
                    OutputStream out = s.getOutputStream();
                    String mensaje = "Hola de parte del grupo De TCP\n";
                    out.write(mensaje.getBytes());
                    out.flush(); // Fuerza el envío inmediato
                    // ---------------------------------------

                    socketsAbiertos.add(s);
                    exitos.incrementAndGet();
                } catch (IOException e) {
                    fallos.incrementAndGet();
                }
            });
            hilos.add(t);
            t.start();

            // Pequeña pausa cada 100 hilos para no saturar tu propia CPU
            if (i % 100 == 0) {
                try {
                    Thread.sleep(5);
                } catch (InterruptedException ignored) {
                }
            }
        }

        for (Thread t : hilos) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("\n[RESULTADO] Conexiones logradas y mensajes enviados: " + exitos.get());
        System.out.println("[RESULTADO] Conexiones fallidas: " + fallos.get());

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
        }

        synchronized (socketsAbiertos) {
            for (Socket s : socketsAbiertos) {
                try {
                    s.close();
                } catch (IOException ignored) {
                }
            }
        }
        System.out.println(">>> Sockets cerrados.");
    }
}