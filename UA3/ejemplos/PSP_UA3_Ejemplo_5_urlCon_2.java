package com.ceslopedevega.red;

import java.io.*;
import java.net.*;

// Desde java usando la clase URLConnection podemos intareactuar con scripts del lado del servidor y podemos enviar valores a los campos del script sin neceisdad de abrir un formulario HTML.
// Sera necesario escribir en la URL para dar los datos al script.  Nuestro programa tendra que hacer lo siguiente:
// - Crear el objeto URL al script con el que va a interactuar.  Por ejemplo, en nuestra maquina local tenemos instalado un servidor web apache y dentro de htdocs tenemos el script PHP vernombre.php
// - Abrir una conexion con la URL es decir obtener el objeto URLConnection
// - Configurar la conexion para que se puedan enviar datos usando el metodo SetDoOutput()
// - Obtener un stream de salida sobnre la conexion
// - Escribir en el stream de salida
// - Cerrar el stream de salida
// Normalmente, cuando se pasa informacion a algun script PHP, este realiza alguna accion y despues envia la informacion de vuelta por la misma URL.  Por tanto si queremos ver lo que devuelve sera necesario leer desde la URL.
// Para ello se abre un stream de entrada sobre esa conexion mediante el metodo getInputStream() y despues se realiza la lectura para obtener los datos devueltos por el script.

public class PSP_UA3_Ejemplo_5_urlCon_2 {
  public static void main(String[] args) {
    try
    {
      URL url = new URL("http://localhost/vernombre.php");
      URLConnection conexion = url.openConnection();
      conexion.setDoOutput(true);

      String cadena ="nombre=Paco&apellidos=Pil Pil";

      //ESCRIBIR EN LA URL - stream de salida
      PrintWriter output = new PrintWriter(conexion.getOutputStream());
      output.write(cadena);
	    output.close(); //cerrar flujo

      //LEER DE LA URL - stream de entrada
      BufferedReader reader = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
      String linea;
      while ((linea = reader.readLine()) != null)
      {
        System.out.println(linea);
      }
      reader.close();//cerrar flujo
    }
    catch (MalformedURLException me)
    {
            System.err.println("MalformedURLException: " + me);
    }
    catch (IOException ioe)
    {
            System.err.println("IOException: " + ioe);
    }
  }//main
}//Ejemplo2urlCon


