package Lacuevadeldragon;


import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class HiloServidor extends Thread {

    Socket cliente;
    Cliente clienteRecogido;

    public HiloServidor(Socket cliente, Cliente clienteRecogido) {
        this.cliente = cliente;
        this.clienteRecogido = clienteRecogido;
    }

    @Override
    public void run() {
        OutputStream salida = null;

        try {
            salida = cliente.getOutputStream();
            DataOutputStream flujoSalida = new DataOutputStream(salida);

            flujoSalida.writeUTF(clienteRecogido.getMensaje().toUpperCase());

            flujoSalida.close();
            salida.close();

        } catch(IOException e){
            System.out.println("Error en HiloServidor: " + e.getMessage());
        }
    }

}
