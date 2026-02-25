import java.security.*;

public class GeneradorClaves {

    public static void main(String[] args) throws Exception {

        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048);

        KeyPair pair = keyGen.generateKeyPair();

        PublicKey publicKey = pair.getPublic();
        PrivateKey privateKey = pair.getPrivate();

        System.out.println("Clave pública: " + publicKey);
        System.out.println("Clave privada: " + privateKey);
    }
}
