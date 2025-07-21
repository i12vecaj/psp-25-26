
import java.io.*;
import java.net.*;


class HiloServidor extends Thread {
	Socket socket;	
	ObjectOutputStream outObjeto;
	ObjectInputStream inObjeto;
	EstructuraFicheros NF;

	public HiloServidor(Socket s, EstructuraFicheros nF) throws IOException {//
		socket = s;
		NF = nF;
		inObjeto = new ObjectInputStream(socket.getInputStream());
		outObjeto = new ObjectOutputStream(socket.getOutputStream());
	}

	//
	public void run() {
		try {
			// envio al cliente el objeto EstructuraFicheros 
			outObjeto.writeObject(NF);

			while (true) {
				// primero leo lo que me pide cliente
				Object peticion = inObjeto.readObject();

				if (peticion instanceof PideFichero) {
					PideFichero fichero = (PideFichero) peticion;
					// escribo en el stream
					EnviarFichero(fichero);
				}
				
				if (peticion instanceof EnviaFichero) {
					EnviaFichero fic = (EnviaFichero) peticion;
					System.out.println(fic.getNombre() + "*" +fic.getDirectorio());
					
					 File d = new File(fic.getDirectorio()); 
				     File f1 = new File(d,fic.getNombre());	//creo fichero en el directorio
					   
					FileOutputStream fos = new FileOutputStream(f1);
					
					//System.out.println(fic.getNombre() + "*" +fic.getDirectorio());
					
					fos.write(fic.getContenidoFichero());
					fos.close();
					EstructuraFicheros n = new EstructuraFicheros(fic.getDirectorio());
					outObjeto.writeObject(n);					
				}
			}
		} catch (IOException e) {
			// cuando un cliente Cierra la conexion		
			try {
				inObjeto.close();
				outObjeto.close();
				socket.close();
				System.out.println("Cerrando cliente");
			} catch (IOException ee) {
				ee.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} // de run()
    
	private void EnviarFichero(PideFichero fich) {
		File fichero = new File(fich.getNombreFichero()); 
		// crea flujo de entrada
		FileInputStream filein = null;
		try {
			filein = new FileInputStream(fichero);
			long bytes = fichero.length();
			//System.out.println("fichero:" + fich.getNombreFichero());
			byte[] buff = new byte[(int) bytes];
			int i, j = 0;
			
			while ((i = filein.read()) != -1) {// lee datos del flujo de entrada
				//System.out.println(i);
				buff[j] = (byte) i;
				j++;
			}
			filein.close(); // cerrar stream de entrada
			Object ff = new ObtieneFichero(buff);
			outObjeto.writeObject(ff);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}// ..EnviarFichero
}// ..HiloServidor
