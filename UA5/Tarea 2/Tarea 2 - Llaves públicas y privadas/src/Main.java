import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.*;
import java.util.Base64;

public class Main {

    private static final String ALGORITHM = "RSA";
    private static final int KEY_SIZE = 2048;
    private static final String PRIVATE_KEY_FILE = "private.key";
    private static final String PUBLIC_KEY_FILE = "public.key";

    public static void main(String[] args) {
        try {
            System.out.println("=== Generación de Llaves Públicas y Privadas en Java ===\n");

            KeyPair keyPair = generateKeyPair();
            System.out.println("· Par de claves generado correctamente");
            System.out.println("  Algoritmo: " + ALGORITHM);
            System.out.println("  Tamaño de clave: " + KEY_SIZE + " bits\n");

            PrivateKey privateKey = keyPair.getPrivate();
            System.out.println("· Clave privada obtenida");
            System.out.println("  Formato: " + privateKey.getFormat());
            System.out.println("  Algoritmo: " + privateKey.getAlgorithm() + "\n");

            PublicKey publicKey = keyPair.getPublic();
            System.out.println("· Clave pública obtenida");
            System.out.println("  Formato: " + publicKey.getFormat());
            System.out.println("  Algoritmo: " + publicKey.getAlgorithm() + "\n");

            saveKeyToFile(PRIVATE_KEY_FILE, privateKey.getEncoded());
            System.out.println("· Clave privada guardada en: " + PRIVATE_KEY_FILE);

            saveKeyToFile(PUBLIC_KEY_FILE, publicKey.getEncoded());
            System.out.println("· Clave pública guardada en: " + PUBLIC_KEY_FILE + "\n");

            System.out.println("=== Claves en formato Base64 ===\n");

            String privateKeyBase64 = Base64.getEncoder().encodeToString(privateKey.getEncoded());
            System.out.println("Clave Privada (Base64):");
            printFormattedKey(privateKeyBase64);
            System.out.println();

            String publicKeyBase64 = Base64.getEncoder().encodeToString(publicKey.getEncoded());
            System.out.println("Clave Pública (Base64):");
            printFormattedKey(publicKeyBase64);
            System.out.println();

            System.out.println("=== Información Adicional ===");
            System.out.println("Tamaño de clave privada: " + privateKey.getEncoded().length + " bytes");
            System.out.println("Tamaño de clave pública: " + publicKey.getEncoded().length + " bytes");
            System.out.println("\n· Proceso completado exitosamente");

        } catch (NoSuchAlgorithmException e) {
            System.err.println("Error: Algoritmo no disponible - " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error al guardar archivos - " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error inesperado - " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static KeyPair generateKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM);
        keyPairGenerator.initialize(KEY_SIZE);
        return keyPairGenerator.generateKeyPair();
    }

    private static void saveKeyToFile(String fileName, byte[] keyBytes) throws IOException {
        File file = new File(fileName);
        file.getParentFile();

        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(keyBytes);
        }
    }

    private static void printFormattedKey(String key) {
        int lineLength = 64;
        for (int i = 0; i < key.length(); i += lineLength) {
            int end = Math.min(i + lineLength, key.length());
            System.out.println(key.substring(i, end));
        }
    }
}