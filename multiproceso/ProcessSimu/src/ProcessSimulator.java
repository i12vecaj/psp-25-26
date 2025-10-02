import java.io.IOException;

public class ProcessSimulator {

    // Creación de tres processos
    ProcessBuilder pb1 = new ProcessBuilder("CMD", "/c", "dir");
    ProcessBuilder pb2 = new ProcessBuilder("PING", "google.es");
    ProcessBuilder pb3 = new ProcessBuilder("PING", "google.es");


    public ProcessSimulator() throws IOException { // Creo una función donde agrpar

        Process p1 = pb1.inheritIO().start();
        Process p2 = pb2.inheritIO().start();
        Process p3 = pb3.inheritIO().start();





    }
}
