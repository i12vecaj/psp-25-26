import java.sql.*;
import java.util.Scanner;
import java.security.MessageDigest;
import java.util.Base64;

/*

Nombre Y Apellidos: Pablo Rodríguez Casado
Fecha: 25/02/2026

Enunciado:
    Reescribe el fragmento vulnerable aplicando programación segura, incluyendo:
    Prevención de SQL Injection\
    Buen manejo de credenciales\
    Buen uso de recursos\

*/


public class Login {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("Usuario:");
        String user = sc.nextLine();

        System.out.println("Password:");
        String pass = sc.nextLine();

        // Uso try-with-resources para la gestión automática de recursos
        try (Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost/app", "root", "root")) {

            // Uso PreparedStatement para prevenir SQL Injection
            String query = "SELECT * FROM usuarios WHERE nombre = ? AND password = ?";
            try (PreparedStatement pst = con.prepareStatement(query)) {

                // Establezco parámetros de forma segura
                pst.setString(1, user);
                pst.setString(2, hashPassword(pass));

                try (ResultSet rs = pst.executeQuery()) {
                    if(rs.next()) {
                        System.out.println("Acceso concedido");
                    } else {
                        System.out.println("Acceso denegado");
                    }
                }
            }

        } catch (SQLException e) {
            System.err.println("Error en la base de datos");
            e.printStackTrace();
        } finally {
            sc.close();
        }
    }

    // Metodo para hash seguro de contraseñas
    private static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hash);
        } catch (java.security.NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al hashear la contraseña", e);
        }
    }
}