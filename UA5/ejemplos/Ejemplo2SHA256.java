import java.io.*;
import java.security.*;

public class Ejemplo2SHA256 {
	public static void main(String args[]) {
		try {
			FileOutputStream fileout = new FileOutputStream("DATOS.DAT");
			ObjectOutputStream dataOS = new ObjectOutputStream(fileout);
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			String datos = "En un lugar de la Mancha, "
					+ "de cuyo nombre no quiero acordarme, no ha mucho tiempo "
					+ "que vivía un hidalgo de los de lanza en astillero, "
					+ "adarga antigua, rocín flaco y galgo corredor.";

			byte dataBytes[] = datos.getBytes();
			md.update(dataBytes);// TEXTO A RESUMIR
			byte resumen[] = md.digest(); // SE CALCULA EL RESUMEN

			dataOS.writeObject(datos); //se escriben los datos
			dataOS.writeObject(resumen);//se escribe el resumen

			dataOS.close();
			fileout.close();

		} catch (IOException e) {
			e.printStackTrace();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
}//..Ejemplo5