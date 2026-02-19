import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.NoSuchAlgorithmException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

public class GenerateKeys {

    public static void main(String[] args) {

        try {
            // Crear generador de claves RSA
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(2048);

            // Generar par de claves
            KeyPair pair = keyGen.generateKeyPair();
            PublicKey publicKey = pair.getPublic();
            PrivateKey privateKey = pair.getPrivate();

            // Convertir a Base64
            String publicKeyBase64 = Base64.getEncoder().encodeToString(publicKey.getEncoded());
            String privateKeyBase64 = Base64.getEncoder().encodeToString(privateKey.getEncoded());

            // Guardar en archivos
            Files.write(Paths.get("public.key"), publicKeyBase64.getBytes());
            Files.write(Paths.get("private.key"), privateKeyBase64.getBytes());

            // Mostrar confirmación
            System.out.println("✅ Claves generadas correctamente:");
            System.out.println(" - public.key");
            System.out.println(" - private.key");

        } catch (NoSuchAlgorithmException e) {
            System.err.println("Error: algoritmo RSA no disponible.");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Error al guardar los archivos.");
            e.printStackTrace();
        }
    }
}
