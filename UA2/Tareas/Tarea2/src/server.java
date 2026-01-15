/*
* -------------
* OBSERVACIONES
* -------------
*
* E una prueba salio:
*
* Esperando al cliente.....
Recibiendo del CLIENTE:
	Saludos al SERVIDOR DESDE EL CLIENTE 1, El puerto remoto es: 6000 El puerto local: 63187 El IP host: localhost/127.0.0.1 El IP y puerto local juntos: /127.0.0.1:63187
Esperando al cliente.....
Recibiendo del CLIENTE:
	Saludos al SERVIDOR DESDE EL CLIENTE 2, El puerto remoto es: 6000 El puerto local: 63192 El IP host: localhost/127.0.0.1 El IP y puerto local juntos: /127.0.0.1:63192

* Con el envio de información siendo:
* flujoSalida.writeUTF("Saludos al SERVIDOR DESDE EL CLIENTE 2," +
                " El puerto remoto es: "+puertoRemoto+
                " El puerto local: "+puertoLocal+
                " El IP host: "+ipHost+
                " El IP y puerto local juntos: "+prueba);
                *
                *
Como está claro, los puertos locales son diferentes para identificar ambos clientes pero...porque?
* El sistema operativo asigna automáticamente un puerto local libre (puerto efímero) cada vez que se abre una conexión nueva.
* Los puertos efímeros son puertos libres asignados en el momento y están presentes a lo largo del tiempo que dure la conexión,
* tras ello, se liberan de nuevo, no se mantienen presentes ni se guardan para próxima conexión
* */





import java.io.*;
import java.net.*;

public class server {
    public static void main(String[] arg) throws IOException {
        int numeroPuerto = 6000;// Puerto
        ServerSocket servidor = new ServerSocket(numeroPuerto);
        Socket clienteConectado = null;
        System.out.println("Esperando al cliente.....");
        clienteConectado = servidor.accept();

        // CREO FLUJO DE ENTRADA DEL CLIENTE
        InputStream entrada = null;
        entrada = clienteConectado.getInputStream();
        DataInputStream flujoEntrada = new DataInputStream(entrada);

        // EL CLIENTE ME ENVIA UN MENSAJE
        System.out.println("Recibiendo del CLIENTE: \n\t" + flujoEntrada.readUTF());

        // CREO FLUJO DE SALIDA AL CLIENTE
        OutputStream salida = null;
        salida = clienteConectado.getOutputStream();
        DataOutputStream flujoSalida = new DataOutputStream(salida);

        // ENVIO UN SALUDO AL CLIENTE
        flujoSalida.writeUTF("Saludos al cliente del servidor");





        Socket clienteConectado2 = null;
        System.out.println("Esperando al cliente.....");
        clienteConectado2 = servidor.accept();

        InputStream entrada2 = null;
        entrada2 = clienteConectado2.getInputStream();
        DataInputStream flujoEntrada2 = new DataInputStream(entrada2);

        System.out.println("Recibiendo del CLIENTE: \n\t" + flujoEntrada2.readUTF());

        OutputStream salida2 = null;
        salida2 = clienteConectado2.getOutputStream();
        DataOutputStream flujoSalida2 = new DataOutputStream(salida2);


        flujoSalida2.writeUTF("Saludos al cliente del servidor");


        // CERRAR STREAMS Y SOCKETS
        entrada.close();
        entrada2.close();
        flujoEntrada.close();
        flujoEntrada2.close();
        salida.close();
        salida2.close();
        flujoSalida.close();
        flujoSalida2.close();
        clienteConectado.close();
        clienteConectado2.close();
        servidor.close();
    }
}