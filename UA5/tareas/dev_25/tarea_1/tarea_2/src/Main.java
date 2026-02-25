import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    private static final Logger LOG = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {

        //Primera parte
        PublicKey publicKey = null;
        PrivateKey privateKey = null;

        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(1024); // exactamente como lo pusiste
            KeyPair keyPair = keyPairGenerator.generateKeyPair();

            publicKey = keyPair.getPublic();
            privateKey = keyPair.getPrivate();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        //Segunda parte
        Base64.Encoder encoder = Base64.getEncoder();
        try (FileWriter out = new FileWriter("private.txt")) {
            out.write("-----BEGIN RSA PRIVATE KEY-----");
            out.write("\n");

            out.write(encoder.encodeToString(privateKey.getEncoded()));
            out.write("\n");

            out.write("-----END RSA PRIVATE KEY-----");
            out.write("\n");
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, ex.getMessage(), ex);
        }

        //Tercera parte
        try (FileWriter out = new FileWriter("public.txt")) {
            out.write("-----BEGIN RSA PUBLIC KEY-----");
            out.write("\n");

            out.write(encoder.encodeToString(publicKey.getEncoded()));
            out.write("\n");

            out.write("-----END RSA PUBLIC KEY-----");
            out.write("\n");
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, ex.getMessage(), ex);
        }

        //Cuarta parte
        try (FileOutputStream out = new FileOutputStream("public.der")) {
            out.write(publicKey.getEncoded());
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, ex.getMessage(), ex);
        }

        //Lectura
        LOG.info(String.format("Public key format: %s", publicKey.getFormat()));
        LOG.info(String.format("Private key format: %s", privateKey.getFormat()));
    }
}