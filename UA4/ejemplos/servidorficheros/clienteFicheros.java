import javax.swing.*;
import javax.swing.event.*;

import java.io.*;
import java.net.Socket;
import java.awt.*;
import java.awt.event.*;



public class clienteFicheros extends JFrame implements Runnable {
	private static final long serialVersionUID = 1L;
	static Socket socket;
	EstructuraFicheros nodo = null;
	ObjectInputStream inObjeto;
	ObjectOutputStream outObjeto;
    EstructuraFicheros Raiz;

	// campos de cabecera parte superior
	static JTextField cab = new JTextField();
	static JTextField cab2 = new JTextField();
	static JTextField cab3 = new JTextField();

	// campos de mensajes parte inferior
	private static JTextField campo = new JTextField();
	private static JTextField campo2 = new JTextField();

	// botones
	JButton botonCargar = new JButton("Subir fichero");
	JButton botonDescargar = new JButton("Descargar fichero");
	JButton botonSalir = new JButton("Salir");

	// lista para los datos del directorio
	@SuppressWarnings("rawtypes")
	static JList listaDirec = new JList();

	// contenedor
	private final Container c = getContentPane();

	// para saber directorio y fichero seleccionado
	static String direcSelec = "";
	static String ficheroSelec = "";
	static String ficherocompleto = "";

	// constructor
	public clienteFicheros(Socket s) throws IOException {
		super("SERVIDOR DE FICHEROS BÁSICO");
		socket = s;
		try {
			// flujo de salida -envio objeto
			outObjeto = new ObjectOutputStream(socket.getOutputStream());
			// flujo de entrada -recibo objeto
			inObjeto = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
		campo.setBounds(new Rectangle(3, 385, 485, 30));
		campo.setForeground(Color.blue);
		campo.setFont(new Font("Verdana", Font.BOLD, 12));
		campo2.setBounds(new Rectangle(3, 415, 485, 30));
		campo2.setForeground(Color.blue);
		campo2.setFont(new Font("Verdana", Font.BOLD, 12));

		cab.setBounds(new Rectangle(5, 5, 400, 30));
		cab.setBorder(null);
		cab.setForeground(Color.blue);
		cab.setFont(new Font("Arial", Font.BOLD, 14));

		cab2.setBounds(new Rectangle(350, 5, 140, 30));
		cab2.setBorder(null);
		cab2.setFont(new Font("Arial", Font.BOLD, 14));
		cab2.setForeground(Color.blue);

		cab3.setBounds(new Rectangle(5, 34, 300, 30));
		cab3.setBorder(null);
		cab3.setFont(new Font("Arial", Font.BOLD, 14));
		cab3.setForeground(Color.blue);

		botonCargar.setBounds(new Rectangle(350, 100, 140, 30));
		botonDescargar.setBounds(new Rectangle(350, 150, 140, 30));
		botonSalir.setBounds(new Rectangle(350, 200, 140, 30));

		listaDirec.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);// SINGLE_INTERVAL_SELECTION);
		JScrollPane barraDesplazamiento = new JScrollPane(listaDirec);
		barraDesplazamiento.setPreferredSize(new Dimension(335, 300));
		barraDesplazamiento.setBounds(new Rectangle(5, 65, 335, 300));
		c.add(barraDesplazamiento);
		c.setLayout(null);

		c.add(campo);
		campo.setEditable(false);
		c.add(campo2);
		campo2.setEditable(false);
		c.add(botonCargar);
		c.add(botonDescargar);

		c.add(botonSalir);

		c.add(cab);
		c.add(cab2);
		c.add(cab3);
		cab.setEditable(false);
		cab2.setEditable(false);
		cab3.setEditable(false);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(510, 450);
		setVisible(true);

		// --clic en un elemento de la lista
		listaDirec.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent lse) {
				if (lse.getValueIsAdjusting()) {
					ficheroSelec = "";
					ficherocompleto = "";
				    nodo = (EstructuraFicheros) listaDirec.getSelectedValue();
					if (nodo.isDir()) {
						// es un directorio
                       campo.setText("FUNCIÓN NO IMPLEMENTADA..... " );
					} else {
						// SE TRATA DE UN FICHERO
						ficheroSelec = nodo.getName();
						ficherocompleto = nodo.getPath();
						campo.setText("FICHERO seleccionado: " + ficheroSelec);
					}// fin else
				}//
			}
		});// fin lista

		// --al hacer clic en el botón Salir
		botonSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					socket.close();
					System.exit(0);
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		});

		// --al hacer clic en el botón Actualizar


		// --al hacer clic en el botón Descargar
		botonDescargar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (ficherocompleto.equals(""))
					return;
				System.out.println("PIDO ESTE FICHERO " + ficherocompleto);
				PideFichero pido = new PideFichero(ficherocompleto);

				try {
					outObjeto.writeObject(pido);
					// pido el fichero
					// Se abre un fichero para empezar a copiar lo que se
					// reciba.
					FileOutputStream fos = new FileOutputStream(ficheroSelec);
					// Se crea un ObjectInputStream del socket para leer los
					// bytes del fichero
					// obtengo los bytes
					Object obtengo = inObjeto.readObject();
					if (obtengo instanceof ObtieneFichero) {
				          ObtieneFichero fic = (ObtieneFichero)obtengo;
						fos.write(fic.getContenidoFichero());
						fos.close();
						JOptionPane.showMessageDialog(null,	"FICHERO DESCARGADO");
					}

				} catch (IOException e1) {e1.printStackTrace();}
				  catch (ClassNotFoundException e1) {e1.printStackTrace();}
			}
		});// Fin boton descargar

		// --al hacer clic en el botón cargar
		botonCargar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser f = new JFileChooser();
				f.setFileSelectionMode(JFileChooser.FILES_ONLY);
				f.setDialogTitle("Selecciona el Fichero a SUBIR AL SERVIDOR DE FICHEROS");
				int returnVal = f.showDialog(f, "Cargar");

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = f.getSelectedFile();
					String archivo = file.getAbsolutePath();
					String nombreArchivo = file.getName();
					BufferedInputStream in;
					try {
						in = new BufferedInputStream(new FileInputStream(
								archivo));
						long bytes = file.length();// fichero.length();
						System.out.println("tamaño:"+file.length());
						byte[] buff = new byte[(int) bytes];
						int i, j = 0;

						while ((i = in.read()) != -1) {
							buff[j] = (byte) i; //carga los datos en el array
							j++;
						}
						in.close(); // cerrar stream de entrada
						Object ff = new EnviaFichero(buff, nombreArchivo,direcSelec);
						outObjeto.writeObject(ff);
                                                JOptionPane.showMessageDialog(null,"FICHERO CARGADO");

						//obtengo de nuevo la lista de ficheros
						nodo = (EstructuraFicheros) inObjeto.readObject();
						EstructuraFicheros[] lista = nodo.getLista();
						direcSelec = nodo.getPath();
						llenarLista(lista, nodo.getNumeFich());
						campo2.setText("Número de ficheros en el directorio: " + lista.length);

					} catch (FileNotFoundException e1) {e1.printStackTrace();}
					  catch (IOException ee) {ee.printStackTrace();	}
					  catch (ClassNotFoundException e2) {e2.printStackTrace();}

				}

			}

		});// Fin boton cargar
	}// ..FIN CONSTRUCTOR
		// -----------------------------------------------------------------------------

	// REPETITIVO-------------------------------------------------------------
	public void run() {
		try {
			cab.setText("Conectando con el servidor ........");
             // OBTENER DIRECTORIO RAIZ
			Raiz = (EstructuraFicheros) inObjeto.readObject();
			EstructuraFicheros[] nodos = Raiz.getLista();//
			// Directorio seleccionadoara saber directorio y fichero seleccionado
			direcSelec = Raiz.getPath();
			//if(Raiz.getNumeFich()> 0)
			llenarLista(nodos,  Raiz.getNumeFich());
			cab3.setText("RAIZ: " + direcSelec);
			cab.setText("CONECTADO AL SERVIDOR DE FICHEROS");
            campo2.setText("Número de ficheros en el directorio: " + Raiz.getNumeFich());

		} catch (IOException e1) {
			e1.printStackTrace();
			System.exit(1);
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
			System.exit(1);
		}

	}// fin run
		// -----------------------------------------------------------------

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static void llenarLista(EstructuraFicheros[] files, int numero) {
		if (numero==0) return;
		DefaultListModel modeloLista = new DefaultListModel();
		listaDirec.setForeground(Color.blue);
		Font fuente = new Font("Courier", Font.PLAIN, 12);
		listaDirec.setFont(fuente);
		listaDirec.removeAll();

		for (int i = 0; i < files.length; i++)
			modeloLista.addElement(files[i]);

		try {
			listaDirec.setModel(modeloLista);
		} catch (NullPointerException n) {	}

	}// Fin llenarLista



	// main---------------------------------------------------------------------
	public static void main(String[] args) throws IOException {
		int puerto = 44441;
		//"192.168.0.195" localhost
		Socket s = new Socket("127.0.0.1", puerto);
		clienteFicheros hiloC = new clienteFicheros(s);
		hiloC.setBounds(0, 0, 540, 500);
		hiloC.setVisible(true);
		new Thread(hiloC).start();
	}// ..FIN main

}// .fin clase
