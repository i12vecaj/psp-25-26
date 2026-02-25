import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

public class GenerateKeys {

    private KeyPairGenerator keyGen;
    private KeyPair pair;
    private PrivateKey privateKey;
    private PublicKey publicKey;

    // Constructor que inicializa el generador con el algoritmo RSA y tamaño 1024 o 2048
    public GenerateKeys(int keylength) throws NoSuchAlgorithmException {
        this.keyGen = KeyPairGenerator.getInstance("RSA");
        this.keyGen.initialize(keylength);
    }

    public void createKeys() {
        this.pair = this.keyGen.generateKeyPair();
        this.privateKey = pair.getPrivate();
        this.publicKey = pair.getPublic();
    }

    public PrivateKey getPrivateKey() {
        return this.privateKey;
    }

    public PublicKey getPublicKey() {
        return this.publicKey;
    }

    // Método para escribir los bytes de la clave en un archivo
    public void writeToFile(String path, byte[] key) throws IOException {
        File f = new File(path);
        f.getParentFile().mkdirs();

        FileOutputStream fos = new FileOutputStream(f);
        fos.write(key);
        fos.flush();
        fos.close();
    }

    public static void main(String[] args) {
        GenerateKeys gk;
        try {
            // Creamos las llaves con una longitud de 2048 bits
            gk = new GenerateKeys(2048);
            gk.createKeys();
            
            // Guardamos las llaves en la carpeta "KeyPair"
            gk.writeToFile("KeyPair/publicKey", gk.getPublicKey().getEncoded());
            gk.writeToFile("KeyPair/privateKey", gk.getPrivateKey().getEncoded());
            
            System.out.println("✅ Tarea 2 completada: Claves generadas en la carpeta 'KeyPair'");
        } catch (NoSuchAlgorithmException | IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
