package StrangerThings;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class LaboratorioDeHawkins {
     private static final int TIEMPO_SIMULACION_MS = 5000; // 5 segundos de simulación

    private static final Logger logger = Logger.getLogger(LaboratorioDeHawkins.class.getName());

    public static void main(String[] args) {
        logger.info("=== INICIO DE ABRIR PORTALES ===");

        Eleven eleven = new Eleven("Eleven");

        List<HilosDemogorgon> demogorgonList = new ArrayList<>();
        String[] nombresClientes = {"Helen"};

        for (String nombre : nombresClientes) {
            demogorgonList.add(new HilosDemogorgon(nombre, eleven));
        }

        for (HilosDemogorgon demogorgon2 : demogorgonList) {
            demogorgon2.start();
        }

        // FR4:
        try {
            logger.info("\nLa simulación correrá durante " + TIEMPO_SIMULACION_MS / 1000 + " segundos");
            Thread.sleep(TIEMPO_SIMULACION_MS);

            logger.info("\nTiempo de abrir portales terminado");
            for (HilosDemogorgon demogorgon2 : demogorgonList) {
                demogorgon2.interrupt();
            }

            for (HilosDemogorgon demogorgon2 : demogorgonList) {
                demogorgon2.join();
            }

        } catch (InterruptedException e) {
            logger.severe("Error: El hilo principal fue interrumpido: " + e.getMessage());
            Thread.currentThread().interrupt();
        }

        logger.info("=== FIN DE LOS PORTALES ===");
    }
}
