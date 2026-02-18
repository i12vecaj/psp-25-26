import java.io.FileWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.*;
import java.util.Base64;

public class Main {

    public static void main(String[] args) {

        try {

            // Parte 1
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(1024);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();

            PublicKey publicKey = keyPair.getPublic();
            PrivateKey privateKey = keyPair.getPrivate();

            System.out.println("Se han generado las llaves");

            // Parte 2
            Base64.Encoder encoder = Base64.getEncoder();
            try (FileWriter out = new FileWriter("private_key.txt")) {
                out.write("----BEGIN RSA PRIVATE KEY----\n");
                out.write(encoder.encodeToString(privateKey.getEncoded()));
                out.write("\n----END RSA PRIVATE KEY----\n");
            }

            System.out.println("La llave privaada se ha guardado en private_key.txt");


            //Parte 3
            try (FileWriter out = new FileWriter("public_key.txt")) {
                out.write("----BEGIN RSA PUBLIC KEY----\n");
                out.write(encoder.encodeToString(publicKey.getEncoded()));
                out.write("\n----END RSA PUBLIC KEY----\n");
            }

            System.out.println("La Llave publica se ha guardado en public_key.txt");


            //Parte 4
            try (FileOutputStream out = new FileOutputStream("private_key.bin")) {
                out.write(privateKey.getEncoded());
            }

            try (FileOutputStream out = new FileOutputStream("public_key.bin")) {
                out.write(publicKey.getEncoded());
            }

            System.out.println("Llaves guardadas en los .bin");


            //Parte 5
            System.out.println("Formato llave publica: " + publicKey.getFormat());
            System.out.println("Formato llave privada: " + privateKey.getFormat());

        } catch (NoSuchAlgorithmException | IOException e) {

            e.printStackTrace();

        }

    }

}