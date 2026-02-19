import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class GeneradorClavesRSA {
    public static void main(String[] args) {
        try {
            int keySize = 2048;
            Path outputDir = Path.of("keys");

            if (args.length > 0 && !args[0].isBlank()) {
                outputDir = Path.of(args[0]);
            }
            if (args.length > 1 && !args[1].isBlank()) {
                keySize = Integer.parseInt(args[1]);
            }

            KeyPair keyPair = generarParDeClaves(keySize);
            Files.createDirectories(outputDir);

            Path publicPem = outputDir.resolve("public_key.pem");
            Path privatePem = outputDir.resolve("private_key.pem");
            Path publicBin = outputDir.resolve("public_key.der");
            Path privateBin = outputDir.resolve("private_key.der");

            guardarClaveEnPem(keyPair.getPublic(), publicPem, "PUBLIC KEY");
            guardarClaveEnPem(keyPair.getPrivate(), privatePem, "PRIVATE KEY");
            guardarClaveEnBinario(keyPair.getPublic(), publicBin);
            guardarClaveEnBinario(keyPair.getPrivate(), privateBin);

            System.out.println("Par de claves generado correctamente.");
            System.out.println("Carpeta de salida: " + outputDir.toAbsolutePath());
            System.out.println("Clave pública PEM: " + publicPem.getFileName());
            System.out.println("Clave privada PEM: " + privatePem.getFileName());
            System.out.println("Clave pública binaria: " + publicBin.getFileName());
            System.out.println("Clave privada binaria: " + privateBin.getFileName());
            System.out.println("Formato clave pública: " + keyPair.getPublic().getFormat());
            System.out.println("Formato clave privada: " + keyPair.getPrivate().getFormat());
        } catch (NumberFormatException e) {
            System.out.println("Error: el tamaño de clave debe ser numérico.");
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Error: algoritmo no soportado: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error de E/S: " + e.getMessage());
        }
    }

    private static KeyPair generarParDeClaves(int keySize) throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(keySize);
        return keyPairGenerator.generateKeyPair();
    }

    private static void guardarClaveEnBinario(Key key, Path filePath) throws IOException {
        Files.write(filePath, key.getEncoded());
    }

    private static void guardarClaveEnPem(Key key, Path filePath, String type) throws IOException {
        Base64.Encoder encoder = Base64.getMimeEncoder(64, "\n".getBytes(StandardCharsets.UTF_8));
        String encoded = encoder.encodeToString(key.getEncoded());

        String pem = "-----BEGIN " + type + "-----\n"
                + encoded + "\n"
                + "-----END " + type + "-----\n";

        Files.writeString(filePath, pem, StandardCharsets.UTF_8);
    }
}
