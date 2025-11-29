import java.util.ArrayList;
import java.util.Random;

/**
 * Clase principal del programa que simula un bar con camarero y clientes.
 */
public class Main {

    /**
     * Función para generar número aleatorio después en la clase HiloClientes
     * @param min Valor mínimo
     * @param max Valor máximo
     * @return Número aleatorio entre min y max
     */
    public static int randomEntre(int min, int max) {
        return min + (int)(Math.random() * (max - min + 1));
    }

    /**
     * Clase VasoCerveza
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
         * Método toString.
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
     * Clase Camarero
     */
    public static class Camarero {

        /** Lista de vasos disponibles */
        ArrayList<VasoCerveza> listaVasos = new ArrayList<>();

        /** Nombre del camarero */
        String nombre;

        /** Utilizo una variable para devolver el último vaso que se sirve para después usar esa misma variable para devolverlo */
        VasoCerveza vasoServido;

        /**
         * Constructor del camarero.
         * @param nombre Nombre del camarero
         */
        public Camarero(String nombre) {
            this.nombre = nombre;
            //Añado tres vasos al array de vasos
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

            //Genero un número aleatorio, lo multiplico por el tamaño de la lista para que el número que me devuelva que usaré como id no sea un id inexistente.
            int numeroRandom = (int) (Math.random() * listaVasos.size());
            System.out.println("Aquí tienes la cerveza:" + listaVasos.get(numeroRandom).toString());

            vasoServido = listaVasos.get(numeroRandom);
            listaVasos.remove(numeroRandom);
            //Elimino el vaso de la lista

            return vasoServido;
            //Devuelvo que vaso se ha servido
        }

        /**
         * Devuelve un vaso de cerveza a la lista y notifica a los hilos que esperan.
         */
        public synchronized void devolverCerveza() {
            //Añado a la lista el último vaso que se sirvió y por lo tanto el último que se eliminó
            listaVasos.add(vasoServido);
            notifyAll(); // Notifica a todos los hilos que hay un vaso disponible, ya que en el método servirCerveza primero se comprobó si la lista está vacía y en caso de estarla se usó wait() para poner a la instancia en espera hasta que haya un vaso disponible, en este caso la instancia que usa este método es un hilo, entonces en este caso con NotifyAll() se notifica a todos los hilos de que ya hay un vaso libre por lo tanto:
            //1. Se le da el vaso al primer hilo que estaba esperando.
            //2. Este hilo bebe cerveza y devuelve el vaso al camarero.
            //3. Al devolver el vaso al camarero usa notifyAll() también, por lo tanto el siguiente hilo que estaba en espera es notificado (junto al resto de los hilos) pero es él el que recibe la cerveza, la devuelve y así continuamente.


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
     * Método main
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
