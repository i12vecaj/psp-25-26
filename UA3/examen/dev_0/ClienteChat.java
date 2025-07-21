/*
 * Basado en el Trabajo:
 * 		Programación de servicios y procesos - Técnico Superior en DAM.
 *		Mª Jesús Ramos Martín. Editorial Garceta. 2ª Edición. 2018. ISBN: 978-84-1728-931-7. *
 */

import java.io.*;
import java.net.*;

public class ClienteChat implements Runnable {
	Socket socket = null;
	// streams
	DataInputStream fentrada;
	DataOutputStream fsalida;
	String nombre;
	boolean repetir = true;

	// constructor
	public ClienteChat(Socket s, String nombre) {
		System.out.println("CONEXION DEL CLIENTE CHAT: " + nombre);
		socket = s;
		this.nombre = nombre;
		try {
			fentrada = new DataInputStream(socket.getInputStream());
			fsalida = new DataOutputStream(socket.getOutputStream());
			String texto = " > Entra en el Chat ... " + nombre;
			fsalida.writeUTF(texto);
		} catch (IOException e) {
			System.out.println("ERROR DE E/S");
			e.printStackTrace();
			System.exit(0);
		}
	}// fin constructor

	public void run() {
		String texto = "";
		while (repetir) {
			try {
				texto = /* RELLENAR */
				System.out.println(texto);

			} catch (IOException e) {
				// este error sale cuando el servidor se cierra
				System.out.println("IMPOSIBLE CONECTAR CON EL SERVIDOR\n" + e.getMessage());
				repetir = false;
			}
		} // while

		try {
			socket.close();
			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}// run

	public static void main(String args[]) throws IOException {
		int puerto = 44444;
		Socket s = null;
		BufferedReader bufferedReader = new BufferedReader (new InputStreamReader(System.in));
		boolean repetir = true;
		System.out.print("Introduce tu nombre o nick: ");
		ClienteChat cliente = null;
		String nombre = bufferedReader.readLine();

		if (nombre.trim().length() == 0) {
			System.out.println("El nombre está vacío....");
			return;
		}

		try {
			s = new Socket("localhost", puerto);
			cliente = /* RELLENAR */
			new Thread(cliente).start();

		} catch (IOException e) {
			System.out.println("IMPOSIBLE CONECTAR CON EL SERVIDOR\n" + e.getMessage());
		}

		while(repetir)
		{
			String texto = /* RELLENAR */
			if(texto.length()>0)
			{
				if(texto.equals("*"))
				{
					repetir = false;
					texto = " > Abandona el Chat ... " + nombre;
					try {
						cliente.fsalida.writeUTF(texto);
						cliente.fsalida.writeUTF("*");
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				else
				{
					texto = nombre + " > " + texto;

					try
					{
						cliente.fsalida.writeUTF(texto);
					}
					catch (IOException e1)
					{
						e1.printStackTrace();
					}
				}
			}
		}
	}// main
}// ..ClienteChat
