import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

public class GeneradorClaves {

    public static void main(String[] args) {
        try {
            KeyPairGenerator generador = KeyPairGenerator.getInstance("RSA");
            generador.initialize(2048);
            
            KeyPair par = generador.generateKeyPair();
            PublicKey publica = par.getPublic();
            PrivateKey privada = par.getPrivate();

            guardarEnDisco("publica.key", publica.getEncoded());
            guardarEnDisco("privada.key", privada.getEncoded());

            System.out.println("--- LLAVE PÚBLICA (Base64) ---");
            System.out.println(Base64.getEncoder().encodeToString(publica.getEncoded()));
            
            System.out.println("\n--- LLAVE PRIVADA (Base64) ---");
            System.out.println(Base64.getEncoder().encodeToString(privada.getEncoded()));
            
            System.out.println("\nArchivos 'publica.key' y 'privada.key' generados correctamente.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void guardarEnDisco(String ruta, byte[] datos) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(ruta)) {
            fos.write(datos);
        }
    }
}