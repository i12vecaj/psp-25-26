/*
 * Basado en el Trabajo:
 * 		Programación de servicios y procesos - Técnico Superior en DAM.
 *		Mª Jesús Ramos Martín. Editorial Garceta. 2ª Edición. 2018. ISBN: 978-84-1728-931-7. *
 */

import java.io.*;
import java.net.*;
import java.util.Scanner;

// He visto un Error que no me ha dado tiempo a corregir donde aunque el cliente ponga * en el chat,
// internamente se ha cerrado, pero la clase ClienteChat sigue abierta or lo que al cerrarlo a la fuerza,
// ya que no hay otra forma el servidor coge ese cerrado forzado como que se ha vuelto a cerrar
// haciendo que se resten dos conexiones en vez de una.

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
		}
	}// fin constructor

	public void run() {
		String texto = "";
		while (repetir) {
			try {
				texto = fentrada.readUTF(); // Aqui ponemos fentrada ya que es el DataInputStream donde el cliente
											// recibe el texto que han enviado el resto de clientes por el chat
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
		Scanner sc = new Scanner(System.in);
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
			cliente = new  ClienteChat(s, nombre); // Creamos al cliente con su constructor añadiendole su socket y nombre de usuario
			new Thread(cliente).start();

		} catch (IOException e) {
			System.out.println("IMPOSIBLE CONECTAR CON EL SERVIDOR\n" + e.getMessage());
		}

		while(repetir)
		{
			String texto = sc.nextLine();
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
		} //while
	}// main
}// ..ClienteChat
