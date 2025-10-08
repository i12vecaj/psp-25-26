import java.io.*;
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public static void main(String[] args)throws  IOException {
    ProcessBuilder construirProceso = new ProcessBuilder("PING","GOOGLE.ES");
    //ProcessBuilder construirProceso = new ProcessBuilder("PING","DIRECCIONINEXISTENTE.TEST");


    Process proceso = construirProceso.start();



    try {
        InputStream inputStream = proceso.getInputStream();
        int line;
        String fichero = "salida.txt";
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(fichero,false))){
            while ((line = inputStream.read()) != -1){
                System.out.print((char)line);
                bw.write(line);
            }
            bw.newLine();

        }
        catch (IOException e){
            e.printStackTrace();
        }
        inputStream.close();

    } catch (Exception e) {
        e.printStackTrace();
    }

    int exitVal;
    try {
        exitVal = proceso.waitFor();
        System.out.println("Valor de Salida: " + exitVal);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }


    //No comprendo del todo esta parte del ejercicio
    ProcessBuilder construirPrimerProceso = new ProcessBuilder("CMD","/C","DIR");

    Process primerProceso = construirPrimerProceso.start();

    try{

        int contenidoCarpetas;
        InputStream primerInputStream = primerProceso.getInputStream();

            try{
                while ((contenidoCarpetas = primerInputStream.read()) != -1)
                    System.out.print((char)contenidoCarpetas);
            } catch (IOException e) {
                e.printStackTrace();
            }

        primerInputStream.close();

    } catch (IOException e) {
        e.printStackTrace();
    }

}
