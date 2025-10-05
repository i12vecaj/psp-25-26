import java.io.File;
import java.io.FileWriter;

public class ProcessSimulator {

    public static void main(String[] args) throws Exception {
        File carpetaLogs = new File("logs");
        if (!carpetaLogs.exists()) carpetaLogs.mkdirs();

        String[] comandos = { "java Script1", "java Script2", "java Script3" };

        long inicio = System.currentTimeMillis();
        for (String cmd : comandos) {
            ejecutarProceso(cmd);
        }
        long fin = System.currentTimeMillis();

        try (FileWriter fw = new FileWriter("logs/resultados_multiproceso.txt", true)) {
            fw.write("Tiempo secuencial: " + (fin - inicio) + " ms\n\n");
        }
    }

    static void ejecutarProceso(String comando) {
        try {
            Process p = Runtime.getRuntime().exec(comando);
            p.waitFor();
        } catch (Exception e) {
            System.out.println("Error ejecutando: " + comando);
        }
    }
}
