package com.ejemplo.servicio;

/**
 * Servicio que ofrece operaciones matemáticas básicas.
 * No depende de ninguna interfaz gráfica ni de entrada de usuario.
 */
public class CalculadoraService {

    // Método público accesible desde otros programas
    public int sumar(int a, int b) {
        return a + b;
    }

    public int restar(int a, int b) {
        return a - b;
    }

    public int multiplicar(int a, int b) {
        return a * b;
    }

    public double dividir(int a, int b) {
        if (b == 0) {
            throw new IllegalArgumentException("No se puede dividir entre cero");
        }
        return (double) a / b;
    }
}
