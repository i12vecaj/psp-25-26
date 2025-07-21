

import java.io.Serializable;

@SuppressWarnings("serial")
public class PideFichero implements Serializable {
	String nombreFichero;

	public String getNombreFichero() {
		return nombreFichero;
	}

	public PideFichero(String nombreFichero) {
		this.nombreFichero = nombreFichero;
	}
}
