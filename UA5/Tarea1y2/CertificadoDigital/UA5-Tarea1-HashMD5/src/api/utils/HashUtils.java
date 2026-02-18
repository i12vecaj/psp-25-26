package api.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;

/**
 * Utilidad para generar hashes criptográficos.
 * Soporta MD5 y puede extenderse a otros algoritmos (SHA-1, SHA-256, etc.).
 */
public class HashUtils {

    private HashUtils() {
        // Clase de utilidad, no se instancia
    }

    /**
     * Genera el hash MD5 del texto proporcionado.
     *
     * @param text Texto de entrada
     * @return Hash MD5 en hexadecimal (32 caracteres en minúsculas)
     * @throws IllegalArgumentException si el texto es nulo o vacío
     */
    public static String md5(String text) {
        if (text == null || text.isEmpty()) {
            throw new IllegalArgumentException("El texto no puede ser nulo o vacío");
        }
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hashBytes = md.digest(text.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(hashBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Algoritmo MD5 no disponible en esta JVM", e);
        }
    }

    /**
     * Genera el hash del texto con el algoritmo especificado.
     *
     * @param algorithm Algoritmo: "md5", "sha1", "sha256"
     * @param text      Texto de entrada
     * @return Hash en hexadecimal
     */
    public static String hash(String algorithm, String text) {
        if (text == null || text.isEmpty()) {
            throw new IllegalArgumentException("El texto no puede ser nulo o vacío");
        }
        String javaAlgorithm = switch (algorithm.toLowerCase()) {
            case "md5"    -> "MD5";
            case "sha1"   -> "SHA-1";
            case "sha256" -> "SHA-256";
            default -> throw new IllegalArgumentException("Algoritmo no soportado: " + algorithm);
        };
        try {
            MessageDigest md = MessageDigest.getInstance(javaAlgorithm);
            byte[] hashBytes = md.digest(text.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(hashBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Algoritmo " + javaAlgorithm + " no disponible", e);
        }
    }

    /**
     * Convierte un array de bytes a su representación hexadecimal.
     */
    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}