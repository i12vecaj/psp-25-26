package tasks;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class TaskIO {
    public static void main(String[] args) throws IOException {
        System.out.println("[TaskIO] Escribiendo archivo temporal...");
        Path temp = Files.createTempFile("taskio_", ".txt");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(temp.toFile()))) {
            for (int i = 0; i < 5000; i++) {
                bw.write("Línea " + i + "\n");
            }
        }
        System.out.println("[TaskIO] Archivo creado en: " + temp);
    }
}