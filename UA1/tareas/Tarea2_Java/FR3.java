// FR3.java
// Programa que ejecuta FR1_FR2 como un proceso hijo (programa que es ejecutado y controlado por otro programa llamado proceso padre)

public class FR3 {
    public static void main(String[] args) {
        try {
            // Ejecuta el programa FR1_FR2
            ProcessBuilder pb = new ProcessBuilder("java", "FR1_FR2");
            pb.inheritIO(); // conecta la entrada/salida del proceso con la consola actual
            Process p = pb.start();
            p.waitFor(); // espera a que termine
        } catch (Exception e) {
            System.out.println("Error al ejecutar FR1_FR2: " + e.getMessage());
        }
    }
}
