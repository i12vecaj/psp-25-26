import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

public class KeyGeneratorService {

    public KeyPair generateKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        return keyPairGenerator.generateKeyPair();
    }

    public void saveKeyToBinaryFile(byte[] keyBytes, String filePath) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            fos.write(keyBytes);
        }
    }

    public void savePublicKeyToTextFile(PublicKey publicKey, String filePath) throws IOException {
        String encodedKey = Base64.getEncoder().encodeToString(publicKey.getEncoded());
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write("-----BEGIN PUBLIC KEY-----\n");
            writer.write(insertLineBreaks(encodedKey));
            writer.write("\n-----END PUBLIC KEY-----\n");
        }
    }

    public void savePrivateKeyToTextFile(PrivateKey privateKey, String filePath) throws IOException {
        String encodedKey = Base64.getEncoder().encodeToString(privateKey.getEncoded());
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write("-----BEGIN PRIVATE KEY-----\n");
            writer.write(insertLineBreaks(encodedKey));
            writer.write("\n-----END PRIVATE KEY-----\n");
        }
    }

    private String insertLineBreaks(String text) {
        StringBuilder sb = new StringBuilder();
        int index = 0;
        while (index < text.length()) {
            sb.append(text, index, Math.min(index + 64, text.length()));
            sb.append("\n");
            index += 64;
        }
        return sb.toString().trim();
    }
}
