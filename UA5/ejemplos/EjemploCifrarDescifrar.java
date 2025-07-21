import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.*;

public class EjemploCifrarDescifrar {

	public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		KeyGenerator kg = KeyGenerator.getInstance("AES");
		kg.init (128);
		SecretKey claveSecreta = kg.generateKey();

		Cipher c = Cipher.getInstance("AES/ECB/PKCS5Padding");
		c.init(Cipher.ENCRYPT_MODE, claveSecreta);

		byte textoPlano[] = "Esto es un Texto Plano".getBytes();

		byte textoCifrado[] = c.doFinal(textoPlano);
		System.out.println("Encriptado: "+ new String(textoCifrado));

		c.init(Cipher.DECRYPT_MODE, claveSecreta);
		byte desencriptado[] = c.doFinal(textoCifrado);
		System.out.println("Desencriptado: "+ new String(desencriptado));
	}
}
