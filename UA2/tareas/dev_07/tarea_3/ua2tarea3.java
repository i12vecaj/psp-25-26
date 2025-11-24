import java.io.FileReader;

public class ua2tarea3 { 
    public static void main(String[] args) {
        try { 
            
            //variables
            FileReader fr, fr1, fr2; 
            int caract, caract1, caract2; 
            int contador= -1;
            int contador1= -1;
            int contador2= -1;

            
            fr = new FileReader("el_quijote.txt"); //abrir archivo Quijote
            caract = fr.read(); // leer primer carácter

            while (caract != -1) { //repetir hasta fin archivo
                caract = fr.read(); //leer el siguiente hasta el final
                contador++; //contar carácteres
            }

            System.out.println("Se leyó correctamente. el_quijote.txt" +" El numero de caracteres es: " + contador); //mostrar resultado Quijote
            fr.close(); //cerrar el archivo Quijote

            
            //repetir el mismo proceso para gasolina
            fr1 = new FileReader("gasolina.txt"); 
            caract1 = fr1.read(); 

            while (caract1 != -1) { 
                caract1 = fr1.read(); 
                contador1++;
            }

            System.out.println("Se leyó correctamente. gasolina.txt" +" El numero de caracteres es: " + contador1); 
            fr1.close();

            
            //repetir exactamente lo mismo pero con suavemente
            fr2 = new FileReader("suavemente.txt");
            caract2 = fr2.read(); 

            while (caract2 != -1) { 
                caract2 = fr2.read(); 
                contador2++; 
            }

            System.out.println("Se leyó correctamente. suavemente.txt" +" El numero de caracteres es: " + contador2); 
            fr2.close();

        } catch (Exception e) { 
            System.out.println("Algo falla: " + e.getMessage()); //mostrar mensaje de error si esq hay fallo
        }
    }
}