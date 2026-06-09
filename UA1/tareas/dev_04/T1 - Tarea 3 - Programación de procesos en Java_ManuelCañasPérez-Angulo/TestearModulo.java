package Parte5;

import java.io.*;
import java.util.*;

public class TestearModulo {
    private static final String APP_INTERNA = "InspectorDatos";

    public static void main(String[] args) {
        System.out.println("Pruebas del validador");
        
        // Almacenamos los diferentes inputs en un array para procesarlos de forma iterativa
        String[] casosDePrueba = { null, "texto", "-10", "30" };
        
        // Recorremos los escenarios de test uno por uno utilizando un bucle
        for (int i = 0; i < casosDePrueba.length; i++) {
            desplegarCaso(casosDePrueba[i]);
        }
    }

    public static void desplegarCaso(String entrada) {
        List<String> lineaComando = new ArrayList<>();
        lineaComando.add("java");
        lineaComando.add(APP_INTERNA);
        System.out.println("--");

        // Construcción dinámica de los parámetros de ejecución para el ProcessBuilder
        if (entrada != null) {
            lineaComando.add(entrada);
            System.out.println("lanzando: java " + APP_INTERNA + " " + entrada);
        } else {
            System.out.println("lanzando: java " + APP_INTERNA + " (Sin argumentos)");
        }

        ProcessBuilder creadorProceso = new ProcessBuilder(lineaComando);

        try {
            // Levantamos el subproceso en segundo plano
            Process runtimeProceso = creadorProceso.start();
            
            // Pausamos el flujo principal hasta que el programa lanzado finalice su tarea
            int flagSalida = runtimeProceso.waitFor();

            // Enviamos el código capturado para su traducción textual
            mostrarMensajeRetorno(flagSalida);

        } catch (IOException eIO) {
            System.out.println("el programa validador no se pudo ejecutar" + eIO.getMessage());
        } catch (InterruptedException eInterruct) {
            Thread.currentThread().interrupt();
        }
    }

    public static void mostrarMensajeRetorno(int codigoSalida) {
        System.out.println("codigo de salida " + codigoSalida + "Significado: ");

        // Evaluamos el resultado devuelto utilizando condicionales anidados independientes
        if (codigoSalida == 0) {
            System.out.println("Argumento vaslido, numero entero positivo" + codigoSalida);
        }
        if (codigoSalida == 1) {
            System.out.println("el numero de argumentos es nulo( < 1)");
        }
        if (codigoSalida == 2) {
            System.out.println("el argumento es una cadena de texto");
        }
        if (codigoSalida == 3) {
            System.out.println("el argumento es un numero entero menor de 0");
        }
    }
}