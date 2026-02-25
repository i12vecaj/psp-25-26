import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

public class KeyPairPractice {
    public static void main(String[] args) throws Exception {
        KeyPair keyPair = generateKeyPair();
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();

        savePrivateKeyText(privateKey, "private_key.pem");
        savePublicKeyText(publicKey, "public_key.pem");
        saveKeyBinary(privateKey, "private_key.bin");
        saveKeyBinary(publicKey, "public_key.bin");

        System.out.println("Public key format: " + publicKey.getFormat());
        System.out.println("Private key format: " + privateKey.getFormat());
        System.out.println("Archivos generados en: " + Path.of(".").toAbsolutePath().normalize());
    }

    private static KeyPair generateKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(1024);
        return keyPairGenerator.generateKeyPair();
    }

    private static void savePrivateKeyText(PrivateKey privateKey, String fileName) throws IOException {
        Base64.Encoder encoder = Base64.getEncoder();
        try (FileWriter out = new FileWriter(fileName)) {
            out.write("-----BEGIN RSA PRIVATE KEY-----\n");
            out.write(encoder.encodeToString(privateKey.getEncoded()));
            out.write("\n-----END RSA PRIVATE KEY-----\n");
        }
    }

    private static void savePublicKeyText(PublicKey publicKey, String fileName) throws IOException {
        Base64.Encoder encoder = Base64.getEncoder();
        try (FileWriter out = new FileWriter(fileName)) {
            out.write("-----BEGIN RSA PUBLIC KEY-----\n");
            out.write(encoder.encodeToString(publicKey.getEncoded()));
            out.write("\n-----END RSA PUBLIC KEY-----\n");
        }
    }

    private static void saveKeyBinary(java.security.Key key, String fileName) throws IOException {
        try (FileOutputStream out = new FileOutputStream(fileName)) {
            out.write(key.getEncoded());
        }
    }
}
