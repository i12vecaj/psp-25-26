/**
 * Productor / Consumidor – “Stranger Things”
 * En Hawkins, el Laboratorio está analizando criaturas del Upside Down.
 * Para ello, debes implementar el patrón Productor/Consumidor:
 * El Productor será Eleven, que usa sus poderes para cerrar portales que se abren aleatoriamente.
 * Cada vez que cierra uno, genera un “evento” (por ejemplo: Demogorgon detectado, Portal inestable, etc.) y lo deposita en un buffer compartido.
 * El Consumidor será el Laboratorio de Hawkins, que recoge estos eventos del buffer y los procesa.
 * - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
 * Requisitos
 * Crea un buffer limitado (por ejemplo, de tamaño 5).
 * Implementa las clases Productor y Consumidor, cada una como un hilo.
 * Usa wait(), notify() o notifyAll() para gestionar:
 * El productor espera si el buffer está lleno.
 * El consumidor espera si el buffer está vacío.
 * Muestra por pantalla los mensajes clave:
 * Eleven genera un evento.
 * El Laboratorio procesa un evento.
 * Esperas por buffer lleno/vacío.
 * El programa debe finalizar después de producir y consumir un número determinado de eventos (por ejemplo, 20).
 * - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
 * Debes entregar el ejercicio, dentro de UD2, en tu rama, carpeta "EXAMEN" ->  carpeta "Stranger Things".
 **/


/**
 * Autor David Alberto Cruz Barranco
 * Fecha 10/12/2025
 * **/


public class Ejercicio2 {
    public static void main(String[] args) {
        BufferEventos buffer = new BufferEventos(5); // Buffer limitado a 5
        int totalEventos = 20;
        Thread productor = new Thread(new Productor(buffer, totalEventos), "Eleven");
        Thread consumidor = new Thread(new Consumidor(buffer, totalEventos), "Laboratorio Hawkins");
        productor.start();
        consumidor.start();
    }
}

// -----------------------------------------------------------
// BUFFER LIMITADO
// -----------------------------------------------------------
class BufferEventos {
    private final String[] buffer;
    private int count = 0;
    private int in = 0;
    private int out = 0;
    public BufferEventos(int size) {
        buffer = new String[size];
    }

    public synchronized void producir(String evento) throws InterruptedException {
        while (count == buffer.length) {
            System.out.println("[BUFFER LLENO] Eleven espera para generar más eventos...");
            wait();
        }
        buffer[in] = evento;
        in = (in + 1) % buffer.length;
        count++;
        System.out.println("Eleven genera evento: " + evento);
        notifyAll();
    }

    public synchronized String consumir() throws InterruptedException {
        while (count == 0) {
            System.out.println("[BUFFER VACÍO] El Laboratorio espera nuevos eventos...");
            wait();
        }
        String evento = buffer[out];
        out = (out + 1) % buffer.length;
        count--;
        System.out.println("Laboratorio procesa evento: " + evento);
        notifyAll();
        return evento;
    }
}

// -----------------------------------------------------------
// PRODUCTOR
// -----------------------------------------------------------
class Productor implements Runnable {
    private final BufferEventos buffer;
    private final int totalEventos;
    private final String[] posiblesEventos = {
            "Demogorgon detectado",
            "Portal inestable",
            "Señal psíquica anómala",
            "Criatura del Upside Down",
            "Ruptura dimensional",
            "Actividad electromagnética extraña"
    };
    public Productor(BufferEventos buffer, int totalEventos) {
        this.buffer = buffer;
        this.totalEventos = totalEventos;
    }

    @Override
    public void run() {
        try {
            for (int i = 1; i <= totalEventos; i++) {
                String evento = posiblesEventos[(int) (Math.random() * posiblesEventos.length)];
                buffer.producir(evento);
                Thread.sleep(300);
            }
        } catch (InterruptedException e) {
            System.out.println("Productor interrumpido.");
        }
    }
}

// -----------------------------------------------------------
// CONSUMIDOR
// -----------------------------------------------------------
class Consumidor implements Runnable {
    private final BufferEventos buffer;
    private final int totalEventos;
    public Consumidor(BufferEventos buffer, int totalEventos) {
        this.buffer = buffer;
        this.totalEventos = totalEventos;
    }

    @Override
    public void run() {
        try {
            for (int i = 1; i <= totalEventos; i++) {
                buffer.consumir();
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
            System.out.println("Consumidor interrumpido.");
        }
    }
}
