package Ejercicio1;

import java.net.InetAddress;
import java.net.URL;
import java.util.Scanner;

/*
Autor: María Luisa Ortega Lucena
Fecha: 14/01/2026
Enunciado: Crea un programa en Java que admita desde la línea de comandos una URL
y visualice información sobre esta.
Modifica el programa para que admita continuamente nuevas IP o URL y muestre la información hasta que el
usuario inserte "localhost".
 */
public class PSP_UA3_EEjercicio_1_Visualizar_Informacion {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        //Variable que es donde voy a guardar la url
        String entradaDatos;

        do {
            //Leo la entrada de lo que escribe el cliente
            entradaDatos = sc.nextLine();
            if(!entradaDatos.equalsIgnoreCase("localhost")){

                try {
                    //Si es una URL
                    if (entradaDatos.startsWith("http://") || entradaDatos.startsWith("https://")){
                        URL url = new URL(entradaDatos);

                        //Ahora imprimo la informacion
                        System.out.println("URL completa: "+url.toString());
                        System.out.println("Host: "+url.getHost());
                        System.out.println("Puerto: "+url.getPort());
                        System.out.println("Ruta: "+url.getPath());

                        //Ip asociada con la url
                        InetAddress ip = InetAddress.getByName(url.getHost());
                        System.out.println("IP asociada: " + ip.getHostAddress());

                    }else {
                        //Es una ip
                        InetAddress direccion = InetAddress.getByName(entradaDatos);

                        System.out.println("Nombre del host: " + direccion.getHostName());
                        System.out.println("Dirección IP: " + direccion.getHostAddress());
                        System.out.println("Es alcanzable?: " + direccion.isReachable(3000));
                    }

                }catch (Exception e){
                    System.err.println("Error al leer entrada: "+e.getMessage());
                }
            }
            System.out.println("\nIntroduce otra IP o URL (localhost para salir):");
        }while (entradaDatos.equalsIgnoreCase("localhost")); //Cuando entrada sea igual a localhost se para

        System.out.println("Programa finalizado.");
        sc.close();
    }
}
