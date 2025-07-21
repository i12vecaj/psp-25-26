

import java.io.*;

public class EstructuraFicheros implements Serializable {

	private static final long serialVersionUID = 1L;
	private String name; // nombre
	private String path; // nombre completo
	private boolean isDir;// es un directorio

	private EstructuraFicheros[] lista; // lista de ficheros y carpetas
	private int numeFich; // numero de fich de la carpeta


    //constructores
	public EstructuraFicheros(String name) throws FileNotFoundException {
		// Construtor por defecto que se usa en el servidor
		// name: Nombre completo (directorio+nombre) del fichero/directorio
		// en el servidor local
		File file = new File(name);
		this.name = file.getName();
		this.path = file.getPath();
		this.isDir = file.isDirectory();

		this.lista = getListaFiles();
		if (file.isDirectory()) {
			File[] ficheros = file.listFiles();
			this.numeFich = 0;
			if (!(ficheros == null))
				this.numeFich = ficheros.length;
		}
	}
	public EstructuraFicheros(String name, String path, boolean isDir,
			int numF) {
		// Este constructor se usa en las operaciones
		// de actualizacion del arbol presentado en el cliente
		// No es obligatorio implementar este metodo
		this.name = name;
		this.path = path;
		this.isDir = isDir;
		this.numeFich = numF; // num ficheros si es directorio
	}

	public int getNumeFich() {
		return numeFich;
	}
	public EstructuraFicheros[] getLista() {
		return lista;
	}
	public String toString() {
		String nom = this.name;
		if (this.isDir)
			nom = "(DIR) " + name;
		return nom;
	}

	public boolean isDir() {	return isDir;	}

	public String getName() {
		String name_dir = name;
		if (isDir) {
			// En el caso de un fichero solo se presenta el nombre
			// del mismo usando el metodo getName() asociado a la clase File.
			// En el caso de un directorio getName() devuelve el nombre completo
			// es decir el path absoluto y solo se necesita el nombre de la
			// carpeta
			// ya que el atributo path almacena dicha informacón.
			int l = path.lastIndexOf(File.separator);
			name_dir = path.substring(l + 1, path.length());
		}
		return name_dir;
	}

	public String getPath() {	return path;	}

	//public String getParent() {	return parent;	}


	EstructuraFicheros[] getListaFiles() {
		EstructuraFicheros[] lista = null;
		String sDirectorio = this.path;
		File f = new File(sDirectorio);
		File[] ficheros = f.listFiles();
		int longitud = ficheros.length;
		if (longitud > 0) {//por si se selecciona carpeta vacia
			// es un directorio, creo un nuevo nodo
			lista = new EstructuraFicheros[longitud]; // array con todos los ficheros
			for (int x = 0; x < ficheros.length; x++) {
				EstructuraFicheros elemento;
				String nombre = ficheros[x].getName();
				String path = ficheros[x].getPath();
				boolean isDir = ficheros[x].isDirectory();
				int num = 0;
				//String parent = ficheros[x].getParent();
                //si alguno de los ficheros del directorio seleccionado es
				//a su vez un directorio calculo el numero de ficheros
				if (isDir) {
					File[] fic = ficheros[x].listFiles();
					if (!(fic == null))
						num = fic.length;
				}
				elemento = new EstructuraFicheros(nombre, path, isDir,  num);
				lista[x] = elemento;

			}// for
		}
		return lista;
	}

}// ..EstructuraFicheros
