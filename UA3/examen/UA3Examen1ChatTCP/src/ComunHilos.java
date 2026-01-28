// Alberto Nieto Lozano
// ComunHilos.java: clase que comparten todos los hilos para compartir los atributos

import java.net.Socket;

public class ComunHilos {

    private int CONEXIONES = 0; // Almacena el número de conexiones de cada cliente
    private int ACTUALES = 0; // Almacena el número de clientes conectados en ese momento
    private int MAXIMO; // Indica el número máximo de conexiones permitidas
    private Socket[] tabla; // Almacena los sockets de los clientes que se irán conectando
    private String mensajes = ""; // Mensajes del chat

    // --- Declaración de la clase ---
    public ComunHilos(int max) {
        MAXIMO = max;
        tabla = new Socket[MAXIMO];
    }

    // --- Getters y Setters ---
    public synchronized int getCONEXIONES() {
        return CONEXIONES;
    }

    public synchronized void setCONEXIONES(int c) {
        CONEXIONES = c;
    }

    public synchronized int getACTUALES() {
        return ACTUALES;
    }

    public synchronized void setACTUALES(int a) {
        ACTUALES = a;
    }

    public synchronized Socket[] getTabla() {
        return tabla;
    }

    public synchronized String getMensajes() {
        return mensajes;
    }

    public synchronized void setMensajes(String m) {
        mensajes = m;
    }
}
