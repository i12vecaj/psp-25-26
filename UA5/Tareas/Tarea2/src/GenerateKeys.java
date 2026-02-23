import java.security.*;
import java.io.*;
import java.util.Base64;

public class GenerateKeys {

    public static void main(String[] args) {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(1024);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();

            PublicKey publicKey = keyPair.getPublic();
            PrivateKey privateKey = keyPair.getPrivate();

            System.out.println("Public key format: " + publicKey.getFormat());
            System.out.println("Private key format: " + privateKey.getFormat());

            savePrivateKeyAsText(privateKey, "private_key.pem");
            savePublicKeyAsText(publicKey, "public_key.pem");
            saveKeyAsBinary(privateKey, "private_key.bin");
            saveKeyAsBinary(publicKey, "public_key.bin");

            System.out.println("Claves generadas y guardadas correctamente.");

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private static void savePrivateKeyAsText(PrivateKey privateKey, String fileName) {
        Base64.Encoder encoder = Base64.getEncoder();
        try (FileWriter out = new FileWriter(fileName)) {
            out.write("----BEGIN RSA PRIVATE KEY----");
            out.write("\n");
            out.write(encoder.encodeToString(privateKey.getEncoded()));
            out.write("\n");
            out.write("----END RSA PRIVATE KEY----");
            out.write("\n");
            System.out.println("Clave privada guardada en: " + fileName);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static void savePublicKeyAsText(PublicKey publicKey, String fileName) {
        Base64.Encoder encoder = Base64.getEncoder();
        try (FileWriter out = new FileWriter(fileName)) {
            out.write("----BEGIN RSA PUBLIC KEY----");
            out.write("\n");
            out.write(encoder.encodeToString(publicKey.getEncoded()));
            out.write("\n");
            out.write("----END RSA PUBLIC KEY----");
            out.write("\n");
            System.out.println("Clave publica guardada en: " + fileName);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static void saveKeyAsBinary(Key key, String fileName) {
        try (FileOutputStream out = new FileOutputStream(fileName)) {
            out.write(key.getEncoded());
            System.out.println("Clave guardada en binario: " + fileName);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}