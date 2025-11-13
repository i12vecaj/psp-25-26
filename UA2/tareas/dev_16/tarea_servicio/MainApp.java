package com.ejemplo.servicio;

/**
 * Clase principal que usa el servicio CalculadoraService.
 */
public class MainApp {
    public static void main(String[] args) {
        // Crear una instancia del servicio
        CalculadoraService servicio = new CalculadoraService();

        // Utilizar los métodos del servicio
        int suma = servicio.sumar(5, 3);
        int resta = servicio.restar(10, 4);
        int producto = servicio.multiplicar(6, 2);
        double division = servicio.dividir(8, 2);

        // Mostrar resultados
        System.out.println("Resultados del servicio de cálculo:");
        System.out.println("Suma: " + suma);
        System.out.println("Resta: " + resta);
        System.out.println("Multiplicación: " + producto);
        System.out.println("División: " + division);
    }
}
