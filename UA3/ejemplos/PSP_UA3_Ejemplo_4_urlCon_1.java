import java.net.*;
import java.io.*;

// El siguiente ejemplo crea un objeto URL a la direccion http://www.elaltopzano.es, se invoca al metodo openConnection() del objeto para crear una conezion y se obtiene un URLConnection
// Despues se abre un stream de entrada sobre esa conexion mediante el metodo getInputStream()
// Al ejecutar el programa se muestra la misma salida que en el ejemplo anterior, sin embargo este programa crea una conexion con el recurso representado por la URL y el anterior abre directamente un stream desde la URL.
}
public class PSP_UA3_Ejemplo_4_urlCon_1 {
  public static void main(String[] args) {
	URL url=null;
	URLConnection urlCon=null;
	try {
		url = new URL("http://www.elaltozano.es");
		urlCon= url.openConnection();

		BufferedReader in;
		InputStream inputStream = urlCon.getInputStream();
		in = new BufferedReader(new
                               InputStreamReader(inputStream));
		String inputLine;
		while ((inputLine = in.readLine()) != null)
			System.out.println(inputLine);

		in.close();
	}
	catch (MalformedURLException e) {e.printStackTrace();}
	catch (IOException e) {e.printStackTrace();}
  }//
}//Ejemplo1urlCon
