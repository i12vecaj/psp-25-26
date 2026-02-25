import java.io.File;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.logging.Logger;

public class Main {
    private static final Logger LOG = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        try {
            KeyGeneratorService keyService = new KeyGeneratorService();

            // Create keys directory
            File keysDir = new File("keys");
            if (!keysDir.exists()) {
                keysDir.mkdirs();
            }

            LOG.info("Generating RSA Key Pair...");
            KeyPair keyPair = keyService.generateKeyPair();
            PublicKey publicKey = keyPair.getPublic();
            PrivateKey privateKey = keyPair.getPrivate();

            LOG.info("Public key format: " + publicKey.getFormat());
            LOG.info("Private key format: " + privateKey.getFormat());

            // Save Binary Files
            LOG.info("Saving keys to binary files...");
            keyService.saveKeyToBinaryFile(publicKey.getEncoded(), "keys/public.key");
            keyService.saveKeyToBinaryFile(privateKey.getEncoded(), "keys/private.key");

            // Save Text Files
            LOG.info("Saving keys to text files (Base64)...");
            keyService.savePublicKeyToTextFile(publicKey, "keys/public_key.txt");
            keyService.savePrivateKeyToTextFile(privateKey, "keys/private_key.txt");

            LOG.info("Keys successfully generated and saved in 'keys' directory.");

        } catch (Exception e) {
            LOG.severe("Error generating keys: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
