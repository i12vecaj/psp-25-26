// Nota: ejecutarlo desde Eclipse, se pueden corromper los streams si
// mezclamos Eclipse y CMD. También la clase Persona puede ser que no
// se referencie bien.

import java.io.*;
import java.net.*;

public class PSP_UA3_Ejemplo_13_TCPObjetoServidor1 {
  public static void main(String[] arg) throws IOException,
						ClassNotFoundException {
   int numeroPuerto = 6000;// Puerto
   ServerSocket servidor =  new ServerSocket(numeroPuerto);
   System.out.println("Esperando al cliente.....");
   Socket cliente = servidor.accept();

   // Se prepara un flujo de salida para objetos
   ObjectOutputStream outObjeto = new ObjectOutputStream(
				cliente.getOutputStream());
   // Se prepara un objeto y se envía
   Persona per = new Persona("Juan", 20);
   outObjeto.writeObject(per); //enviando objeto
   System.out.println("Envio: " + per.getNombre() +"*"+ per.getEdad());

   // Se obtiene un stream para leer objetos
   ObjectInputStream inObjeto = new ObjectInputStream(
				cliente.getInputStream());
   Persona dato = (Persona) inObjeto.readObject();
   System.out.println("Recibo: "+dato.getNombre()+"*"+dato.getEdad());

   // CERRAR STREAMS Y SOCKETS
   outObjeto.close();
   inObjeto.close();
   cliente.close();
   servidor.close();
  }
}//..
