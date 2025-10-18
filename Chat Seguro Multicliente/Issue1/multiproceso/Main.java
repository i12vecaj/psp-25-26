import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;

public class ProcessSimulator {

    public static void main(String[] args) {


        try{

            //Cone sta función mido el tiempo al comienzo
            long inicio = System.currentTimeMillis();


            File file=new File("resultados_ping.txt");

            FileWriter fw=new FileWriter(file);

            String[] ping= {"google.com","bing.com","yahoo.com"};

            //Por cada dirección un ping

            for(String actualPing:ping){

                ProcessBuilder pb=new ProcessBuilder("ping",actualPing);
                Process p=pb.start();

                try{


                        BufferedReader br=new BufferedReader(new InputStreamReader(p.getInputStream()));

                        String line;
                    while ((line = br.readLine())!=null){
                        fw.write(line+"\n");
                    }

                    p.waitFor();

                    long fin = System.currentTimeMillis();

                    long duracion = fin - inicio;

                    System.out.println(duracion);
                    //Leo y escribo haciendo la diferencia entre el tiempo desde que comenzó hasta su fin

                    fw.write("El ping a "+actualPing+" es de "+duracion+" milisegundos"+"\n");
                    fw.close();


                }catch(Exception e){
                    System.out.println("Error: "+e.getMessage());
                }
            }

        }catch(Exception e){
            System.out.println("Error: "+e.getMessage());
        }


    }
}