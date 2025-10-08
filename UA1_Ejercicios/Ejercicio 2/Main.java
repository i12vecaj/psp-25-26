import java.io.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        String symbol="";
        Scanner sc = new Scanner(System.in);

        //Creo referencia de archivo
        File saveInfo = new File("./info.txt");


        while(!symbol.equals("*")){

            System.out.println("Escribe 1 símbolo, si quieres finalizar escribe * tras escribir todo lo que necesites");

            symbol = sc.nextLine();



            try {
                //Coloco true para decir que no sobreescriba todo asi lo añade al final

                if (!saveInfo.exists()) {
                    saveInfo.createNewFile();
                }

                //Creo archivo
                FileWriter writing = new FileWriter(saveInfo,true);

                //lineSeparator puede agregar una línea al final de cada escritura que hagamos
                writing.write(symbol+System.lineSeparator());
                writing.close();

            } catch(Exception e){

            }
        }

        try{

            System.out.println(saveInfo.getAbsolutePath());

            //Creo proceso de lectura
            ProcessBuilder pb = new ProcessBuilder("CMD","/C","TYPE",saveInfo.getAbsolutePath());

            Process readingFile=pb.start();

            InputStream inputStream = readingFile.getInputStream();
            //PARTE IMPORTANTE PARA QUE ME LEA
            //Creo buffered reader y dentro un inputstreamreader de manera que me lea la pipe de informacion(creo se llamaba pipe)
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            //Mientras tenga algo que leer, leerá lo creado
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }

            //Cierro el buffered para que no quede abierto
            bufferedReader.close();
            //NOTA PERSONAL aquí puedo poner IO ya que al crear el archivo puede tirar esa excepcion
        }catch(IOException e){
            System.out.println("Error: "+e.getMessage());
        }





    }

}