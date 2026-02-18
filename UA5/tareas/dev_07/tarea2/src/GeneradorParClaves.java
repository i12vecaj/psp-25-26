import java.security.*;
import java.io.*;
import java.nio.file.*;

public class GeneradorParClaves {
    
    private static final String ALGORITMO = "RSA";
    private static final int TAMAÑO_CLAVE = 2048;
    
    public static KeyPair generarParClaves(int tamañoClave) throws NoSuchAlgorithmException {
        KeyPairGenerator generador = KeyPairGenerator.getInstance(ALGORITMO);
        generador.initialize(tamañoClave);
        return generador.generateKeyPair();
    }

    public static KeyPair generarParClaves() throws NoSuchAlgorithmException {
        return generarParClaves(TAMAÑO_CLAVE);
    }
    
    public static void guardarParClaves(KeyPair parClaves, String rutaClavePublica, String rutaClavePrivada) 
            throws IOException {
        // Guardar clave pública
        PublicKey clavePublica = parClaves.getPublic();
        byte[] bytesClavePublica = clavePublica.getEncoded();
        Files.write(Paths.get(rutaClavePublica), bytesClavePublica);
        System.out.println("Clave pública guardada en: " + rutaClavePublica);
        
        // Guardar clave privada
        PrivateKey clavePrivada = parClaves.getPrivate();
        byte[] bytesClavePrivada = clavePrivada.getEncoded();
        Files.write(Paths.get(rutaClavePrivada), bytesClavePrivada);
        System.out.println("Clave privada guardada en: " + rutaClavePrivada);
    }
    
    public static PublicKey cargarClavePublica(String rutaClavePublica) throws Exception {
        byte[] bytesClave = Files.readAllBytes(Paths.get(rutaClavePublica));
        java.security.spec.X509EncodedKeySpec especificacion = 
            new java.security.spec.X509EncodedKeySpec(bytesClave);
        KeyFactory fabricaClaves = KeyFactory.getInstance(ALGORITMO);
        return fabricaClaves.generatePublic(especificacion);
    }
    
    public static PrivateKey cargarClavePrivada(String rutaClavePrivada) throws Exception {
        byte[] bytesClave = Files.readAllBytes(Paths.get(rutaClavePrivada));
        java.security.spec.PKCS8EncodedKeySpec especificacion = 
            new java.security.spec.PKCS8EncodedKeySpec(bytesClave);
        KeyFactory fabricaClaves = KeyFactory.getInstance(ALGORITMO);
        return fabricaClaves.generatePrivate(especificacion);
    }

    public static void mostrarInfoParClaves(KeyPair parClaves) {
        System.out.println("\n=== INFORMACIÓN DEL PAR DE CLAVES ===");
        System.out.println("Algoritmo: " + parClaves.getPublic().getAlgorithm());
        System.out.println("Formato: " + parClaves.getPublic().getFormat());
        System.out.println("Clave Pública: " + parClaves.getPublic());
        System.out.println("\nClave Privada: " + parClaves.getPrivate());
        System.out.println("======================================\n");
    }
}
