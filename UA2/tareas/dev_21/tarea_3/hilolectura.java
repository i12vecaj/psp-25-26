package tarea_3;

import java.io.FileReader;


//creamos la clase al hilo
public class hilolectura extends Thread{
    private String nombre;
    private String libro;

    public hilolectura(String nombre, String libro) {
        this.nombre = nombre;
        this.libro = libro;
    }

//creamos el run con la funcion del hilo
    @Override
    public void run() {
//contador para el numero de caracteres
        int contador = 0;
//inicio del contador en milisegundos
        long inicio = System.currentTimeMillis();
//lectura del fichero
       FileReader fr = null;
//try catch con el while para contar los caracteres
        try{
            fr = new FileReader(libro);

            int caracter = fr.read();

            while(caracter != -1){
                contador ++;
                caracter = fr.read();
            }

            System.out.println("---------------------------------");
            System.out.println("El libro : " + nombre +  " tiene : " + contador + " Caracteres");

            long fin = System.currentTimeMillis();
//tiempo en milisegundos
            System.out.println("tiempo de ejecucion: " + (fin - inicio) + " ms");
//prevencion de errores
        }catch(Exception e){
            System.err.println("fallo la maquinaria amigo");
        }
    }
}
