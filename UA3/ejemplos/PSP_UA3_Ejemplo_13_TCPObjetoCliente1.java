//Nota: ejecutarlo desde Eclipse, se pueden corromper los streams si
//mezclamos Eclipse y CMD. También la clase Persona puede ser que no
//se referencie bien.

import java.io.*;
import java.net.*;

public class PSP_UA3_Ejemplo_13_TCPObjetoCliente1 {
  public static void main(String[] arg) throws IOException,
			ClassNotFoundException {
    String Host = "localhost";
    int Puerto = 6000;//puerto remoto

    System.out.println("PROGRAMA CLIENTE INICIADO....");
    Socket cliente = new Socket(Host, Puerto);

    //Flujo de entrada para objetos
    ObjectInputStream perEnt = new ObjectInputStream(cliente.getInputStream());
    //Se recibe un objeto
    Persona dato = (Persona) perEnt.readObject();//recibo objeto
    System.out.println("Recibo: " + dato.getNombre()+"*" + dato.getEdad());

    //Modifico el objeto
    dato.setNombre("Juan Ramos");
    dato.setEdad(22);

    //FLUJO DE salida para objetos
    ObjectOutputStream perSal = new ObjectOutputStream(cliente.getOutputStream());

    // Se envía el objeto
    perSal.writeObject(dato);
    System.out.println("Envio: "+dato.getNombre()+"*"+dato.getEdad());

    // CERRAR STREAMS Y SOCKETS
    perEnt.close();
    perSal.close();
    cliente.close();
  }
}//..
