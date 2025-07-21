import java.security.*;
import javax.crypto.*;

public class Ejemplo1CypherRSA {
	public static void main(String args[]) {

		try {
			// SE CREA EL PAR DE CLAVES
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
			keyGen.initialize (1024);
			KeyPair par = keyGen.generateKeyPair();
			PrivateKey clavepriv = par.getPrivate();
			PublicKey clavepub = par.getPublic();

			Cipher c = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			c.init(Cipher.ENCRYPT_MODE, clavepub);

			// CIFRAMOS TEXTO
			byte textoPlano[] = "Esto es un Texto Plano".getBytes();
			byte textoCifrado[] = c.doFinal(textoPlano);
			System.out.println("Encriptado: "+ new String(textoCifrado));

		    // DESCIFRAMOS TEXTO
			c.init(Cipher.DECRYPT_MODE, clavepriv);
			byte desencriptado[] = c.doFinal(textoCifrado);
			System.out.println("Desencriptado: "+ new String(desencriptado));

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}