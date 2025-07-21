import java.io.*;
import java.security.*;
import java.security.spec.*;

public class Ejemplo2KeySignature {
   public static void main(String[] args) {
    try {
	// LECTURA DEL FICHERO DE CLAVE PRIVADA
	FileInputStream inpriv = new FileInputStream("Clave.privada");
	byte[] bufferPriv = new byte[inpriv.available()];
	inpriv.read(bufferPriv);// lectura de bytes
	inpriv.close();

	//RECUPERA CLAVE PRIVADA DESDE DATOS CODIFICADOS EN FORMATO PKCS8
	PKCS8EncodedKeySpec clavePrivadaSpec = new PKCS8EncodedKeySpec(bufferPriv);
	KeyFactory keyDSA = KeyFactory.getInstance("DSA");
	PrivateKey clavePrivada = keyDSA.generatePrivate(clavePrivadaSpec);

	//INICIALIZA FIRMA CON CLAVE PRIVADA
	Signature dsa = Signature.getInstance("SHA256withDSA");
	dsa.initSign (clavePrivada);

	//LECTURA DEL FICHERO A FIRMAR. Se suministra al objeto Signature los datos a firmar
	FileInputStream fichero = new FileInputStream("FICHERO.DAT");
	BufferedInputStream bis = new BufferedInputStream(fichero);
	byte[] buffer = new byte[bis.available()];
	int len;

	while ((len = bis.read(buffer)) >= 0)
		dsa.update(buffer, 0, len);

	bis.close();

	//GENERA LA FIRMA DE LOS DATOS DEL FICHERO
	byte[] firma = dsa.sign();

	// GUARDA LA FIRMA EN OTRO FICHERO
	FileOutputStream fos = new FileOutputStream("FICHERO.FIRMA");
	fos.write(firma);
	fos.close();
     } catch (Exception  e1) {
	e1.printStackTrace();
     }
   }// main
}//..Ejemplo2KeySignature

