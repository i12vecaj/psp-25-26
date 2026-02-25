package org.example;

import java.sql.*;
import java.util.Scanner;
import java.security.MessageDigest;

public class Login {

    // Las credenciales deberían cargarse desde variables de entorno
    // o un fichero de configuración externo, nunca hardcodeadas
    private static final String DB_URL  = System.getenv("DB_URL");
    private static final String DB_USER = System.getenv("DB_USER");
    private static final String DB_PASS = System.getenv("DB_PASS");

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("Usuario:");
        String user = sc.nextLine();

        System.out.println("Password:");
        String pass = sc.nextLine();

        // Hasheamos la contraseña antes de compararla con la BD
        String passHash = hashSHA256(pass);

        // try-with-resources garantiza el cierre de todos los recursos
        try (
                Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);

                // PreparedStatement previene SQL Injection
                PreparedStatement ps = con.prepareStatement(
                        "SELECT * FROM usuarios WHERE nombre = ? AND password = ?")
        ) {
            ps.setString(1, user);
            ps.setString(2, passHash);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    System.out.println("Acceso concedido");
                } else {
                    System.out.println("Acceso denegado");
                }
            }

        } catch (SQLException e) {
            // Nunca mostrar el mensaje real de la excepción al usuario
            System.out.println("Error al conectar con la base de datos.");
            // El error real se registraría en un log interno para evitar datos a un atacante
            e.printStackTrace();
        }
    }

    // Método para hashear la contraseña con SHA-256
    private static String hashSHA256(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(input.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException("Error al hashear la contraseña", e);
        }
    }
}