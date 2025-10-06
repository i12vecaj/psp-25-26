package UA1.tareas.dev_0;

import java.io.*;
import java.nio.file.*;

public class Tarea2Ejecutor {

    public static void main(String[] args) {
        Process proceso = null;

        try {
            System.out.println("=== EJECUTANDO PROGRAMA TAREA2 ===");
            System.out.println("Iniciando proceso...\n");

            // Construir el comando Java
            String javaHome = System.getProperty("java.home");
            String javaCommand = javaHome + File.separator + "bin" + File.separator + "java";

            String classpath = buildClassPath();
            System.out.println("Usando classpath: " + classpath);

            ProcessBuilder pb = new ProcessBuilder(
                    javaCommand,
                    "-cp",
                    classpath,
                    "UA1.tareas.dev_0.Tarea2"
            );

            // Configurar para usar la misma entrada/salida que este proceso
            pb.redirectInput(ProcessBuilder.Redirect.INHERIT);
            pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);
            pb.redirectError(ProcessBuilder.Redirect.INHERIT);

            proceso = pb.start();

            // Esperar a que termine
            int codigoSalida = proceso.waitFor();
            System.out.println("\n=== PROCESO FINALIZADO CON CÓDIGO: " + codigoSalida + " ===");

        } catch (Exception e) {
            System.err.println("Error ejecutando Tarea2: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (proceso != null && proceso.isAlive()) {
                proceso.destroyForcibly();
            }
        }
    }

    private static String buildClassPath() {
        StringBuilder classpath = new StringBuilder();

        // 1. Directorio actual (para clases compiladas en la misma carpeta)
        classpath.append(".").append(File.pathSeparator);

        // 2. Classpath del sistema
        classpath.append(System.getProperty("java.class.path"));

        // 3. Buscar directorios comunes de IntelliJ
        String[] intelliJPaths = {
                "out/production",
                "target/classes",
                "build/classes"
        };

        for (String path : intelliJPaths) {
            File dir = new File(path);
            if (dir.exists() && dir.isDirectory()) {
                classpath.append(File.pathSeparator).append(path);
            }
        }

        return classpath.toString();
    }
}