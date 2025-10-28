import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class sensores implements Runnable {
    private String nombreSensor;
    private List<String> tiposSensor;
    private Random random;

    // Constructor con String... para indicar la varianza de cantidad de datos que podemos introducir dentro, 1 2 o 3 lecturas(temperatura, humedad o estado)
    public sensores(String nombreSensor, String... tiposSensor) {
        this.nombreSensor = nombreSensor;
        this.tiposSensor = Arrays.asList(tiposSensor);
        this.random = new Random();
    }

    // Override del run
    @Override
    public void run() {
        for (int i = 1; i <= 10; i++) {
            long tiempo = System.currentTimeMillis();

            for (String tipo : tiposSensor) {
                double valor = generarValorAleatorio(tipo);
                String lectura = "[" + tiempo + "] " + nombreSensor +
                        " (" + tipo + ") -> Lectura " + i + ": " + valor;

                System.out.println(lectura);

                escribirEnArchivo(tipo, lectura);
            }

            try {
                Thread.sleep((1 + random.nextInt(3)) * 1000);
            } catch (InterruptedException e) {
                System.out.println(nombreSensor + " interrumpido.");
            }
        }

        System.out.println(nombreSensor + " ha finalizado sus lecturas.\n");
    }

    private double generarValorAleatorio(String tipo) {
        switch (tipo.toLowerCase()) {
            case "temperatura":
                return 15 + random.nextDouble() * 15; // 15–30°C
            case "humedad":
                return 20 + random.nextDouble() * 60; // 20–80%
            case "estado":
                return random.nextInt(101); // 0–100%
            default:
                return 0.0;
        }
    }

    private synchronized void escribirEnArchivo(String tipo, String texto) {
        String nombreArchivo = "";
        switch (tipo.toLowerCase()) {
            case "temperatura":
                nombreArchivo = "registro_temperatura.txt";
                break;
            case "humedad":
                nombreArchivo = "registro_humedad.txt";
                break;
            case "estado":
                nombreArchivo = "registro_estado.txt";
                break;
        }

        try (BufferedWriter escribir = new BufferedWriter(new FileWriter(nombreArchivo, true))) {
            escribir.write(texto);
            escribir.newLine();
        } catch (IOException e) {
            System.err.println("Error escribiendo en " + nombreArchivo + ": "+e.getMessage());
        }
    }
}
