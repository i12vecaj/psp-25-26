import java.io.FileReader;

public class newThread implements Runnable{

public void run(){

    //Llamo a la funcion de lectura por cada archivo
    int value1= reading("main/resources/files/Quijote.txt");
    int value2= reading("main/resources/files/QuijoteMasDiez.txt");
    int value3= reading("main/resources/files/QuijoteMenor.txt");


    System.out.println("Quijote.txt tiene "+value1+" letras");
    System.out.println("QuijoteMasDiez.txt tiene "+value2+" letras");
    System.out.println("QuijoteMenor.txt tiene "+value3+" letras");

}

//Funcion que recibe path y devuelve un contador con el numero de caracteres
    public static int reading(String filePath){

    int value=0;
        try{
            FileReader fr1;
            int caract1;
            fr1 = new FileReader(filePath);
            caract1 = fr1.read();
            while(caract1 != -1) {

                value++;
                caract1 = fr1.read();
                //Para leer cada letra
                //System.out.println((char) caract1);

            }
        } catch(Exception e){
            System.out.println("Error al leer el archivo "+ e.getMessage());
        }

        return value;
    }

}
