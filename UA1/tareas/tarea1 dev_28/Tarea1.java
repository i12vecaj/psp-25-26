import java.io.*;

public class EjecutarComando {
    public static void main(String[] args) throws  IOException{
        ProcessBuilder pb = new ProcessBuilder("ping","google.es");

            Process p = pb.start();


            try {
                InputStream is = p.getInputStream();
                int c;
                String fichero = "salida.txt";
                    try(BufferedWriter bw = new BufferedWriter(new FileWriter(fichero,true))){
                        while ((c = is.read()) != -1)
                            bw.write(c);
                        bw.newLine();
                    }
                    catch (IOException e){
                        e.printStackTrace();
                    }
                is.close();

            } catch (Exception e) {
                e.printStackTrace();
        }

        int exitVal;
        try {
            exitVal = p.waitFor();
            System.out.println("Valor de Salida: " + exitVal);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
