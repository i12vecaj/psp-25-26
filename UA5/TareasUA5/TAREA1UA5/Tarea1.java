import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class Tarea1 {
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Introduce el texto: ");
            String texto = scanner.nextLine();

            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hash = md.digest(texto.getBytes("UTF-8"));

            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }

            System.out.println("Texto:    " + texto);
            System.out.println("Hash MD5: " + sb.toString());

        } catch (NoSuchAlgorithmException e) {
            System.err.println("Error: algoritmo MD5 no encontrado");
        } catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
        }
    }
}