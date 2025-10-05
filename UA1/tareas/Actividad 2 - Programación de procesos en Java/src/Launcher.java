import java.io.IOException;

public class Launcher {
    public static void main(String[] args) {
        try {
            ProcessBuilder pb = new ProcessBuilder("java", "-cp", "bin", "App");
            pb.inheritIO();
            Process process = pb.start();
            int exitCode = process.waitFor();
            System.out.println("Codigo final: " + exitCode);
        } catch (IOException | InterruptedException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
