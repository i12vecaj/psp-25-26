import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

public class Tarea02 {
    public static void main(String[] args) {
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(2048);
            
            KeyPair pair = keyGen.generateKeyPair();
            PrivateKey priv = pair.getPrivate();
            PublicKey pub = pair.getPublic();

            System.out.println("LLAVE PRIVADA ");
            System.out.println(Base64.getEncoder().encodeToString(priv.getEncoded()));

            System.out.println("LLAVE PÚBLICA");
            System.out.println(Base64.getEncoder().encodeToString(pub.getEncoded()));

        } catch (NoSuchAlgorithmException e) {
            System.err.println("Error");
        }
    }
}