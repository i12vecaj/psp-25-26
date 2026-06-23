package org.example;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

    Scanner sc = new Scanner(System.in);

    System.out.print("Ruta del PDF a firmar: ");
    String pdf = sc.nextLine();

    System.out.print("Ruta del certificado (.p12): ");
    String cert = sc.nextLine();

    System.out.print("Contraseña del certificado: ");
    String password = sc.nextLine();

    System.out.print("Nombre del PDF firmado: ");
    String salida = sc.nextLine();

    try {
    FirmaService.firmar(pdf, salida, cert, password);
    System.out.println("PDF firmado correctamente.");
    } catch (Exception e) {
    System.out.println("Error al firmar: " + e.getMessage());
    }

    sc.close();
    }
}