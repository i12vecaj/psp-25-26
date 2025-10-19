
/*
 * proceso para añadir este archivo desde Git (no he podido porque uso SSH)
 * primeramente abririamos CMD en Windows o Bash en Linux
 * clonariamos el repo con git clone https://github.com/i12vecaj/psp-25-26.git
 * git branch -c dev_16
 * git switch dev_16
 * pegaría el archivito en algun lado
 * git add .
 * git commit -m "Actividad clase" 
 * git push -u upstream dev_X o git push -u origin dev_X
 * ya debería estar 
 */

import java.util.*; // importar librería típica

class main {

    public static void main(String[] args) {

        Scanner scannerNombre = new Scanner(System.in);
        System.out.println("Bienvenido! Introduce tu nombre");

        String nombre = scannerNombre.nextLine();
        System.out.println("Perfecto " + nombre + ", ahora introduce tu edad");

        Scanner scannerEdad = new Scanner(System.in);
        int edad = scannerEdad.nextInt(); // importante que aquí el scanner espere un int y no un string
        int edad100 = (100 - edad) + 2025; // aquí estamos suponiendo que estamos en 2025


        System.out.println("Que sepas mi pequeño " + nombre + " que cumplirás 100 años en el año " + edad100);

        /* 
        esta parte la he mirado pq me preguntaba cómo 
        se podría hacer el programa en condiciones
        si añadimos
        import java.time.*;
    
        y para la edad usamos esta linea
        int edad100 = (100 - edad) + Year.now().getValue();

        el resultado sería mejor debido a que no importaría la fecha
        */
    }
}
