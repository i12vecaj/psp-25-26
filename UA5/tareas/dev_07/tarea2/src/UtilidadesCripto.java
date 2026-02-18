import javax.crypto.Cipher;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;


public class UtilidadesCripto {
    
    private static final String ALGORITMO = "RSA";
    

    public static String cifrar(String textoPlano, PublicKey clavePublica) throws Exception {
        Cipher cifrador = Cipher.getInstance(ALGORITMO);
        cifrador.init(Cipher.ENCRYPT_MODE, clavePublica);
        byte[] bytesCifrados = cifrador.doFinal(textoPlano.getBytes("UTF-8"));
        return Base64.getEncoder().encodeToString(bytesCifrados);
    }

    public static String descifrar(String textoCifrado, PrivateKey clavePrivada) throws Exception {
        Cipher cifrador = Cipher.getInstance(ALGORITMO);
        cifrador.init(Cipher.DECRYPT_MODE, clavePrivada);
        byte[] bytesCifrados = Base64.getDecoder().decode(textoCifrado);
        byte[] bytesDescifrados = cifrador.doFinal(bytesCifrados);
        return new String(bytesDescifrados, "UTF-8");
    }

    public static void demostrarCifrado(PublicKey clavePublica, PrivateKey clavePrivada, String mensaje) {
        try {
            System.out.println("\n=== DEMOSTRACIÓN DE CIFRADO/DESCIFRADO ===");
            System.out.println("Mensaje original: " + mensaje);
            
            // Cifrar
            String cifrado = cifrar(mensaje, clavePublica);
            System.out.println("\nMensaje cifrado: ");
            System.out.println(cifrado);
            
            // Descifrar
            String descifrado = descifrar(cifrado, clavePrivada);
            System.out.println("\nMensaje descifrado: " + descifrado);
            System.out.println("==========================================\n");
            
        } catch (Exception e) {
            System.err.println("Error durante el cifrado/descifrado: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
