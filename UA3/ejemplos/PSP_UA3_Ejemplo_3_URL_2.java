import java.net.*;
import java.io.*;

// El siguiente ejemplo crea un objeto URL a la direccion http://www.elaltozano.es, abre una conexion con el, creando un objetio InputStream y lo utiliza como flujo de entrada para leer los datos de la pagina inicial del sitio.
// Al ejecutar el programa se muestra en pantalla el codigo HTML de la pagina inicial del sitio.

public class PSP_UA3_Ejemplo_3_URL_2
{
	public static void main(String[] args)
	{
		URL url=null;
		try
		{
			url = new URL("http://www.elaltozano.es");
		}
		catch (MalformedURLException e)
		{
			e.printStackTrace();
		}

		BufferedReader in;
		try
		{
			InputStream inputStream = url.openStream();
			in = new BufferedReader(new InputStreamReader(inputStream));
			String inputLine;
			while ((inputLine = in.readLine()) != null)
			{
				System.out.println(inputLine);
			}

			in.close();

		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}	//Ejemplo2URL
