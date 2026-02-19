import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class GeneradorClavesRSA {

    private static final int KEY_SIZE = 2048;
    private static final String ALGORITHM = "RSA";

    public static void main(String[] args) {
        try {
            KeyPair keyPair = generateKeyPair();
            Path keysDirectory = Paths.get("keys");
            Files.createDirectories(keysDirectory);

            Path publicKeyPath = keysDirectory.resolve("public.key");
            Path privateKeyPath = keysDirectory.resolve("private.key");
            Path publicKeyBase64Path = keysDirectory.resolve("public_base64.txt");
            Path privateKeyBase64Path = keysDirectory.resolve("private_base64.txt");

            saveBinaryKey(publicKeyPath, keyPair.getPublic().getEncoded());
            saveBinaryKey(privateKeyPath, keyPair.getPrivate().getEncoded());
            saveBase64Key(publicKeyBase64Path, keyPair.getPublic().getEncoded());
            saveBase64Key(privateKeyBase64Path, keyPair.getPrivate().getEncoded());

            System.out.println("Par de claves RSA generado correctamente.");
            System.out.println("Clave pública (binario): " + publicKeyPath.toAbsolutePath());
            System.out.println("Clave privada (binario): " + privateKeyPath.toAbsolutePath());
            System.out.println("Clave pública (Base64): " + publicKeyBase64Path.toAbsolutePath());
            System.out.println("Clave privada (Base64): " + privateKeyBase64Path.toAbsolutePath());
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Error: el algoritmo RSA no está disponible en esta JVM.");
        } catch (IOException e) {
            System.err.println("Error al guardar las claves en disco: " + e.getMessage());
        }
    }

    private static KeyPair generateKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM);
        keyPairGenerator.initialize(KEY_SIZE);
        return keyPairGenerator.generateKeyPair();
    }

    private static void saveBinaryKey(Path path, byte[] keyBytes) throws IOException {
        Files.write(path, keyBytes);
    }

    private static void saveBase64Key(Path path, byte[] keyBytes) throws IOException {
        String base64Key = Base64.getEncoder().encodeToString(keyBytes);
        Files.write(path, base64Key.getBytes(StandardCharsets.UTF_8));
    }
}
