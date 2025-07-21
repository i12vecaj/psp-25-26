import java.io.*;
import java.security.*;
import javax.crypto.*;
public class EjemploDescifraPDF {
	public static void main(String[] args) {
		try {
			//RECUPERAMOS CLAVE SECRETA DEL FICHERO
			ObjectInputStream oin = new ObjectInputStream(new FileInputStream("Clave.secreta"));
	        Key clavesecreta = (Key) oin.readObject();
	        oin.close();
	        //
			Cipher c = Cipher.getInstance("AES/ECB/PKCS5Padding");
			c.init(Cipher.DECRYPT_MODE, clavesecreta);

			CipherInputStream in = new CipherInputStream(new FileInputStream("FicheroPDF.Cifrado"), c);

			int tambloque = c.getBlockSize();
			byte[] bytes = new byte[tambloque];

			int i = in.read(bytes);
			FileOutputStream fileout = new FileOutputStream("FICHEROdescifrado.pdf");

			while (i != -1)	{
			  fileout.write(bytes, 0, i);
			  i = in.read(bytes);
			}
			fileout.close();
			in.close();
			System.out.println("Fichero descifrado con clave secreta.");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}//main
}//.. Ejemplo13Descifra
