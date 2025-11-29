import java.util.ArrayList;
import java.util.Random;

/**
 * Clase principal del programa que simula un bar con camarero y clientes.
 */
public class Main {

    /**
     * Genera un número aleatorio entre min y max (ambos incluidos).
     * @param min Valor mínimo
     * @param max Valor máximo
     * @return Número aleatorio entre min y max
     */
    public static int randomEntre(int min, int max) {
        return min + (int)(Math.random() * (max - min + 1));
    }

    /**
     * Clase que representa un vaso de cerveza.
     */
    public static class VasoCerveza {

        /** Contador estático para asignar IDs únicos a los vasos */
        private static int contador = 0;

        /** Identificador del vaso */
        int id;

        /** Tipo de vaso: 0 = media pinta, 1 = pinta */
        int tipo;

        /**
         * Constructor del vaso de cerveza.
         * Asigna un ID único y un tipo aleatorio.
         */
        public VasoCerveza() {
            this.id = contador++;
            this.tipo = (int) (Math.random() * 2);
        }

        /** @return ID del vaso */
        public int getId() {
            return id;
        }

        /** @param id Nuevo ID del vaso */
        public void setId(int id) {
            this.id = id;
        }

        /** @return Tipo del vaso */
        public int getTipo() {
            return tipo;
        }

        /** @param tipo Nuevo tipo del vaso */
        public void setTipo(int tipo) {
            this.tipo = tipo;
        }

        /**
         * Representación en String del vaso de cerveza.
         * @return Descripción del vaso
         */
        @Override
        public String toString() {
            if (this.tipo == 0) {
                return "VasoCerveza{" +
                        "id=" + id +
                        ", tipo=" + tipo + "(media pinta)" +
                        '}';
            } else if (this.tipo == 1) {
                return "VasoCerveza{" +
                        "id=" + id +
                        ", tipo=" + tipo + "(pinta)" +
                        '}';
            } else {
                return "VasoCerveza{id=" + id + ", tipo=" + tipo + "}";
            }
        }
    }

    /**
     * Clase que representa un camarero que sirve y recoge vasos de cerveza.
     */
    public static class Camarero {

        /** Lista de vasos disponibles */
        ArrayList<VasoCerveza> listaVasos = new ArrayList<>();

        /** Nombre del camarero */
        String nombre;

        /** Último vaso servido (para devolverlo posteriormente) */
        VasoCerveza vasoServido;

        /**
         * Constructor del camarero.
         * Inicializa nombre y agrega 3 vasos a la lista.
         * @param nombre Nombre del camarero
         */
        public Camarero(String nombre) {
            this.nombre = nombre;
            listaVasos.add(new VasoCerveza());
            listaVasos.add(new VasoCerveza());
            listaVasos.add(new VasoCerveza());
        }

        /**
         * Sirve un vaso de cerveza aleatorio al cliente.
         * Si no hay vasos disponibles, espera hasta que se devuelva alguno.
         * @return Vaso servido
         */
        public synchronized VasoCerveza servirCerveza() {
            while (listaVasos.isEmpty()) {
                try {
                    wait(); // Espera hasta que haya un vaso disponible
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            int numeroRandom = (int) (Math.random() * listaVasos.size());
            System.out.println("Aquí tienes la cerveza:" + listaVasos.get(numeroRandom).toString());

            vasoServido = listaVasos.get(numeroRandom);
            listaVasos.remove(numeroRandom);

            return vasoServido;
        }

        /**
         * Devuelve un vaso de cerveza a la lista y notifica a los hilos que esperan.
         */
        public synchronized void devolverCerveza() {
            listaVasos.add(vasoServido);
            notifyAll(); // Notifica a todos los hilos que hay un vaso disponible
        }

        /**
         * Cuenta y muestra la cantidad de vasos disponibles en la lista.
         */
        public void contarVasos() {
            int contador = 0;
            for (VasoCerveza v : listaVasos) {
                contador++;
            }
            System.out.println("Hay un total de " + contador + " vasos en la lista");
        }
    }

    /**
     * Hilo que representa a un cliente que pide, bebe y devuelve vasos de cerveza.
     */
    public static class HiloClientes extends Thread {

        /** Camarero al que pide los vasos */
        Camarero camarero;

        /**
         * Constructor del hilo cliente.
         * @param nombre Nombre del cliente
         * @param camarero Camarero al que pedirá la cerveza
         */
        public HiloClientes (String nombre, Camarero camarero) {
            super(nombre);
            this.camarero = camarero;
        }

        /**
         * Método que ejecuta el hilo.
         * El cliente pide un vaso, lo bebe y lo devuelve repetidamente.
         */
        public void run() {
            double contadorLitros = 0;

            while (true) {
                VasoCerveza vasoServido = camarero.servirCerveza();

                System.out.println("Bebiendo la rica y deliciosa cerveza...");
                try {
                    Thread.sleep(1000); // Simula tiempo de beber
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (vasoServido.getTipo() == 0) {
                    contadorLitros += 0.25;
                } else {
                    contadorLitros += 0.5;
                }

                System.out.println("Total de litros bebidos: " + contadorLitros + " L");

                camarero.devolverCerveza();

                try {
                    Thread.sleep(randomEntre(250,1000)); // Espera antes de pedir otro vaso
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Método principal que inicia la simulación del bar.
     * @param args Argumentos de la línea de comandos
     */
    public static void main(String[] args) {
        Camarero camarero = new Camarero("Mou");

        HiloClientes cliente1 = new HiloClientes("Homer", camarero);
        HiloClientes cliente2 = new HiloClientes("Barney", camarero);
        HiloClientes cliente3 = new HiloClientes("Carl", camarero);
        HiloClientes cliente4 = new HiloClientes("Lenny", camarero);
        HiloClientes cliente5 = new HiloClientes("Lurleen", camarero);

        cliente1.start();
        cliente2.start();
        cliente3.start();
        cliente4.start();
        cliente5.start();
    }
}
