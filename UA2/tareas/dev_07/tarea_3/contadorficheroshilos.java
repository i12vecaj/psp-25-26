public class contadorficheroshilos implements Runnable {

    private String nombreFichero; //variable para nombre

    public contadorficheroshilos(String nombreFichero) { //constructor para la tarea
        this.nombreFichero = nombreFichero; //guardar nombre
    }

    @Override
    public void run() { //código que hará el hilo.
        try {
            java.io.FileReader fr; //lector de archivo
            int caract; //variable para carácter
            int contador = -1; //contador

            fr = new java.io.FileReader(nombreFichero); //abrir el archivo
            caract = fr.read(); //leer primer carácter

            while (caract != -1) { //repetir hasta fin
                caract = fr.read(); //leer el siguiente hasta el final
                contador++; //contar carácteres
            }

            System.out.println("Se leyó correctamente " + nombreFichero + ". El numero de caracteres es: " + contador); //mostrar resultado hilo
            fr.close(); //cerrar el archivo

        } catch (Exception e) {
            System.out.println("Algo falla en " + nombreFichero + ": " + e.getMessage()); //mostrar mensaje error si hay
        }
    }

    public static void main(String[] args) { //aquí comienza programa
        String[] ficheros = { "el_quijote.txt", "gasolina.txt", "suavemente.txt" }; //ficheros

        for (String fichero : ficheros) { //recorrer lista de archivos
            Thread hilo = new Thread(new contadorficheroshilos(fichero)); //crear un hilo nuevo
            hilo.start(); //iniciar ejecución
        }
    } 
} 