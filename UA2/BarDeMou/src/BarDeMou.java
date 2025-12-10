/**
 * Examen UA2 - Sistema de simulación de camareros y clientes.
 * Código reescrito para mantener la funcionalidad pero con
 * distinta estructura, mensajes y variaciones menores.
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BarDeMou {

    // ============================================================
    // FR1 - Clase VasoCerveza
    // ============================================================
    public static class VasoCerveza {
        private int identificador;
        private int tipoVaso; // 0 = media pinta, 1 = pinta

        public VasoCerveza(int identificador, int tipoVaso) {
            this.identificador = identificador;
            this.tipoVaso = tipoVaso;
            System.out.println("[Vaso] Creado vaso #" + identificador + " (" + (tipoVaso == 0 ? "media pinta" : "pinta") + ")");
        }

        public synchronized int getIdentificador() {
            System.out.println("[Vaso] getIdentificador() -> " + identificador);
            return identificador;
        }

        public synchronized void setIdentificador(int nuevo) {
            System.out.println("[Vaso] setIdentificador(" + nuevo + ")");
            this.identificador = nuevo;
        }

        public synchronized int getTipoVaso() {
            System.out.println("[Vaso] getTipoVaso() -> " + tipoVaso);
            return tipoVaso;
        }

        public synchronized void setTipoVaso(int t) {
            System.out.println("[Vaso] setTipoVaso(" + t + ")");
            this.tipoVaso = t;
        }

        @Override
        public synchronized String toString() {
            return "VasoCerveza{id=" + identificador +
                    ", tipo=" + (tipoVaso == 0 ? "media pinta" : "pinta") + "}";
        }
    }

    // ============================================================
    // FR2 - Clase Camarero
    // ============================================================
    public static class Camarero {
        private final String nombreCamarero;
        private final List<VasoCerveza> almacenVasos = new ArrayList<>();
        private final Random rand = new Random();

        public Camarero(String nombreCamarero) {
            this.nombreCamarero = nombreCamarero;
            System.out.println("[Camarero] Nuevo camarero: " + nombreCamarero);

            // crear 3 vasos al inicio
            for (int i = 0; i < 3; i++) {
                int tipoAleatorio = rand.nextInt(2);
                almacenVasos.add(new VasoCerveza(i, tipoAleatorio));
            }

            mostrarVasos();
        }

        public synchronized VasoCerveza servir() {
            System.out.println("[Camarero " + nombreCamarero + "] servir() solicitado");

            while (almacenVasos.isEmpty()) {
                try {
                    System.out.println("[Camarero " + nombreCamarero + "] No hay vasos, esperando...");
                    wait();
                } catch (InterruptedException e) {
                    System.out.println("[Camarero] Hilo interrumpido, no puede continuar.");
                    Thread.currentThread().interrupt();
                    return null;
                }
            }

            int pos = rand.nextInt(almacenVasos.size());
            VasoCerveza vaso = almacenVasos.remove(pos);

            System.out.println("[Camarero] Entrega " + vaso);
            return vaso;
        }

        public synchronized void devolver(VasoCerveza vaso) {
            if (vaso == null) {
                System.out.println("[Camarero] Se ignoró vaso NULL");
                return;
            }
            almacenVasos.add(vaso);
            System.out.println("[Camarero] Vaso devuelto: " + vaso);
            notifyAll();
        }

        public synchronized void mostrarVasos() {
            System.out.println("[Camarero] Vasos en el bar:");
            for (VasoCerveza v : almacenVasos) {
                System.out.println(" -> " + v);
            }
        }
    }

    // ============================================================
    // FR3 - Clase HilosClientes
    // ============================================================
    public static class HilosClientes extends Thread {

        private final Camarero camarero;
        private final Random generador = new Random();
        private double litrosConsumidos = 0.0;

        public HilosClientes(String nombre, Camarero camarero) {
            super(nombre);
            this.camarero = camarero;
            System.out.println("[Cliente] Registrado cliente: " + nombre);
        }

        @Override
        public void run() {
            System.out.println("[Cliente " + getName() + "] Inicio de actividad");

            while (true) {
                try {
                    VasoCerveza vaso = camarero.servir();
                    if (vaso == null) {
                        System.out.println("[Cliente " + getName() + "] No pudo obtener vaso. Finaliza.");
                        break;
                    }

                    double cantidad = vaso.getTipoVaso() == 0 ? 0.25 : 0.5;
                    litrosConsumidos += cantidad;

                    System.out.println("[Cliente " + getName() + "] bebe " + cantidad +
                            "L (total=" + litrosConsumidos + "L)");

                    camarero.devolver(vaso);

                    int tiempo = 250 + generador.nextInt(751);
                    Thread.sleep(tiempo);

                } catch (InterruptedException e) {
                    System.out.println("[Cliente " + getName() + "] Interrumpido. Terminando...");
                    Thread.currentThread().interrupt();
                    break;
                }
            }

            System.out.println("[Cliente " + getName() + "] Finalizado. Total: "
                    + litrosConsumidos + "L");
        }

        public double getLitrosConsumidos() {
            return litrosConsumidos;
        }
    }

    // ============================================================
    // FR4 + FR5 - Main
    // ============================================================
    public static void main(String[] args) {

        Camarero mou = new Camarero("Mou");

        HilosClientes c1 = new HilosClientes("Homer", mou);
        HilosClientes c2 = new HilosClientes("Barney", mou);
        HilosClientes c3 = new HilosClientes("Carl", mou);
        HilosClientes c4 = new HilosClientes("Lenny", mou);
        HilosClientes c5 = new HilosClientes("Lurleen", mou);

        c1.start();
        c2.start();
        c3.start();
        c4.start();
        c5.start();

        try {
            Thread.sleep(10000); // 10 segundos de prueba
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("[Main] Interrumpiendo clientes...");

        c1.interrupt();
        c2.interrupt();
        c3.interrupt();
        c4.interrupt();
        c5.interrupt();

        try {
            c1.join();
            c2.join();
            c3.join();
            c4.join();
            c5.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("====== Resumen ======");
        System.out.println("Homer: " + c1.getLitrosConsumidos() + "L");
        System.out.println("Barney: " + c2.getLitrosConsumidos() + "L");
        System.out.println("Carl: " + c3.getLitrosConsumidos() + "L");
        System.out.println("Lenny: " + c4.getLitrosConsumidos() + "L");
        System.out.println("Lurleen: " + c5.getLitrosConsumidos() + "L");
    }
}
