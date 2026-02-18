import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

public class KeyGen {

    public static void main(String[] args) throws Exception {
        // generar par de claves RSA 2048
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(2048);
        KeyPair kp = kpg.generateKeyPair();

        PublicKey publicKey = kp.getPublic();
        PrivateKey privateKey = kp.getPrivate();

        // guardar en binario
        Files.write(Paths.get("public.key"), publicKey.getEncoded());
        Files.write(Paths.get("private.key"), privateKey.getEncoded());

        // guardar en formato PEM
        writePem("public.pem", "PUBLIC KEY", publicKey.getEncoded());
        writePem("private.pem", "PRIVATE KEY", privateKey.getEncoded());

        System.out.println("Claves generadas:");
        System.out.println("  public.key (binario)");
        System.out.println("  private.key (binario)");
        System.out.println("  public.pem (PEM Base64)");
        System.out.println("  private.pem (PEM Base64)");
    }

    // escribe archivo PEM con líneas de 64 caracteres
    private static void writePem(String fileName, String type, byte[] bytes) throws IOException {
        String encoded = Base64.getEncoder().encodeToString(bytes);
        StringBuilder pem = new StringBuilder();
        pem.append("-----BEGIN ").append(type).append("-----\n");
        int index = 0;
        while (index < encoded.length()) {
            int end = Math.min(index + 64, encoded.length());
            pem.append(encoded, index, end).append("\n");
            index = end;
        }
        pem.append("-----END ").append(type).append("-----\n");
        try (FileOutputStream fos = new FileOutputStream(fileName)) {
            fos.write(pem.toString().getBytes());
        }
    }
}
