import java.net.Socket;

public class ComunHilos {
	 int CONEXIONES; //NUMERO DE CONEXIONES TOTALES, OCUPADAS EN EL ARRAY
	 int ACTUALES;   //NUMERO DE CONEXIONES ACTUALES
	 int MAXIMO;     //MAXIMO DE CONEXIONES PERMITIDAS
	 Socket tabla[] = new Socket[MAXIMO];// SOCKETS CONECTADOS
	 String mensajes; //MENSAJES DEL CHAT
	 String ultimo; //ULTIMO MENSAJE DEL CHAT

	public ComunHilos(int maximo, int actuales, int conexiones, Socket[] tabla) {
		MAXIMO = maximo;
		ACTUALES = actuales;
		CONEXIONES = conexiones;
		this.tabla = tabla;
		mensajes="";
		ultimo="";
	}

	public ComunHilos() { super(); }

     public int getMAXIMO() { return MAXIMO;	}
	public void setMAXIMO(int maximo) { MAXIMO = maximo;}


	public int getCONEXIONES() { return CONEXIONES;	}
	public synchronized void  setCONEXIONES(int conexiones) {
		CONEXIONES = conexiones;
	}

	public String getMensajes() { return mensajes; }
	public synchronized void setMensajes(String mensajes) {
		this.mensajes = mensajes;
	}

	public String getUltimo() { return ultimo; }
	public synchronized void setUltimo(String ultimo) {
		this.ultimo = ultimo;
		this.setMensajes(this.getMensajes() + ultimo + "\n");
	}

	public int getACTUALES() { return ACTUALES; }
	public synchronized void setACTUALES(int actuales) {
		ACTUALES = actuales;
	}

	public synchronized void addTabla(Socket s, int i) {
		tabla[i] = s;
	}
	public Socket getElementoTabla(int i) { return tabla[i]; }

}//ComunHilos
