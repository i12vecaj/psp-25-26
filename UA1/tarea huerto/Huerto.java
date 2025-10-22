import java.util.Random;

public class HuertoMonitor {

    public static void main(String[] args) {
        Thread temperaturaSensor = new Thread(new Sensor("Temperatura", -5, 40));
        Thread humedadSensor = new Thread(new Sensor("Humedad del suelo", 0, 100));
        Thread estadoPlantaSensor = new Thread(new Sensor("Estado de las plantas", 0, 10)); // 0 = muy mal, 10 = excelente

        temperaturaSensor.start();
        humedadSensor.start();
        estadoPlantaSensor.start();
    }
}

class Sensor implements Runnable {
    private final String tipoSensor;
    private final int minValor;
    private final int maxValor;
    private final Random random = new Random();

    public Sensor(String tipoSensor, int minValor, int maxValor) {
        this.tipoSensor = tipoSensor;
        this.minValor = minValor;
        this.maxValor = maxValor;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 10; i++) {
            int valor = random.nextInt(maxValor - minValor + 1) + minValor;
            long timestamp = System.currentTimeMillis();
            System.out.printf("[%s] Lectura %d: %d (ms: %d)%n", tipoSensor, i, valor, timestamp);

            try {
                int espera = random.nextInt(3) + 1; // entre 1 y 3 segundos
                Thread.sleep(espera * 1000L);
            } catch (InterruptedException e) {
                System.err.println("Error en el sensor " + tipoSensor);
                Thread.currentThread().interrupt();
            }
        }
    }
}
