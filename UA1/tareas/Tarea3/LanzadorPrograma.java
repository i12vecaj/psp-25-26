import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LanzadorPrograma {

    public static void main(String[] args) {
        List<String> command = new ArrayList<>();
        command.add(detectJavaCommand());
        command.add("-cp");
        command.add(System.getProperty("java.class.path"));
        command.add("ProgramaPrincipal");
        command.addAll(Arrays.asList(args));

        ProcessBuilder pb = new ProcessBuilder(command);
        pb.redirectErrorStream(true);

        try {
            Process process = pb.start();

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println("[ProgramaPrincipal] " + line);
                }
            }

            int exitCode = process.waitFor();

            String message;
            switch (exitCode) {
                case 1:
                    message = "No se pasó ningún argumento.";
                    break;
                case 2:
                    message = "El argumento no es un entero válido.";
                    break;
                case 3:
                    message = "El argumento es un entero negativo.";
                    break;
                case 0:
                    message = "Correcto: entero 0 o positivo.";
                    break;
                default:
                    message = "Código no contemplado (" + exitCode + ").";
                    break;
            }

            System.out.println("ProgramaPrincipal terminó con código " + exitCode + ". " + message);
        } catch (IOException e) {
            System.err.println("No se pudo iniciar el proceso: " + e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("La espera del proceso fue interrumpida.");
        }
    }

    private static String detectJavaCommand() {
        String javaHome = System.getProperty("java.home");
        try {
            if (javaHome != null) {
                Path javaBin = Paths.get(javaHome, "bin", isWindows() ? "java.exe" : "java");
                if (Files.exists(javaBin)) {
                    return javaBin.toString();
                }
            }
        } catch (Exception ignored) {
        }
        return "java";
    }

    private static boolean isWindows() {
        String os = System.getProperty("os.name");
        return os != null && os.toLowerCase(Locale.ROOT).contains("win");
    }
}