import java.security.*;
import javax.crypto.*;

public class Ejemplo1CypherRSA {
	public static void main(String args[]) {

		try {
			/* Aquí ocurre lo fundamental:
			Se crea un generador de claves RSA
			Se indica el tamaño de la clave (1024 bits)
			Se generan dos claves matemáticamente relacionadas
			
			🎓 Aclaración importante:
			La clave pública no permite obtener la privada
			La seguridad se basa en la dificultad de factorizar números grandes*/
			
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
