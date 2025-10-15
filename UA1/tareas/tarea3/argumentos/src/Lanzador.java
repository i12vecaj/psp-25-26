
// FR3: ejecuta al programa ValidadorArgumentos y muestra el resultado
// Control de errores: IOException, InterruptedException

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Lanzador {

    public static void main(String[] args) {

        System.out.println("[Lanzador] args recibidos (" + args.length + "): " + Arrays.toString(args));

        Integer resultado = ejecutarValidador(args); // admite 0 o 1 argumento (FR1 cumplida en el conjunto)
        if (resultado == null) {
            System.out.println("⚡ Error inesperado al ejecutar el proceso hijo.");
            return;
        }

        switch (resultado) {
            case 1 -> System.out.println("No se ha proporcionado ningún argumento");
            case 2 -> System.out.println("El argumento no es un número entero");
            case 3 -> System.out.println("El número proporcionado es negativo");
            case 0 -> System.out.println("El argumento es correcto");
            default -> System.out.println("Código inesperado: " + resultado);
        }
    }

    public static Integer ejecutarValidador(String[] args) {
        try {
            // Usamos el classpath actual que pone IntelliJ al ejecutar
            String classpath = System.getProperty("java.class.path");

            // Construimos el comando dinámicamente: si no hay argumento, NO añadimos nada
            List<String> comando = new ArrayList<>();
            comando.add("java");
            comando.add("-cp");
            comando.add(classpath);
            comando.add("ValidadorArgumentos");
            if (args.length > 0) {
                comando.add(args[0]);
            }

            // (Opcional) imprimir comando para depuración
            System.out.println("[Lanzador] Comando: " + String.join(" ", comando));

            ProcessBuilder pb = new ProcessBuilder(comando);
            pb.redirectErrorStream(true);
            Process proceso = pb.start();

            // Leemos salida del hijo (por si queremos mostrarla o diagnosticar errores de clase)
            StringBuilder salida = new StringBuilder();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(proceso.getInputStream()))) {
                String linea;
                while ((linea = br.readLine()) != null) {
                    salida.append(linea).append(System.lineSeparator());
                }
            }

            int exitCode = proceso.waitFor();

            // Diagnóstico si la JVM no encuentra la clase objetivo
            String out = salida.toString();
            if (out.contains("Could not find or load main class")) {
                System.err.println("No se pudo encontrar la clase 'ValidadorArgumentos'.");
                System.err.println("Asegúrate de que ValidadorArgumentos.java y Lanzador.java están en el MISMO módulo.");
                System.err.println("Que ambos compilan (Build > Build Project) y que no tienen 'package' (o usa FQCN).");
                return null;
            }

            return exitCode;
        } catch (IOException e) {
            System.err.println("Error al lanzar el proceso: " + e.getMessage());
            return null;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("La ejecución fue interrumpida: " + e.getMessage());
            return null;
        }
    }
}
