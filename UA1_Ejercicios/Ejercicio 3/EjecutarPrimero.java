
public class EjecutarPrimero {
    public static void main(String[] args) {

        try {

            //IMPORTANTE
            //Con java lanza por consola ejecutando el archivo pero no compila
            //Con esto podemos hacer que busque la clase concretamente y así ejecutarla
            //Esto se debe a que simula que una consola lo ejecuta y una consola, no es capaz de hacer eso
            String classpath = System.getProperty("java.class.path");

            ProcessBuilder pb = new ProcessBuilder("java","-cp",classpath, "Main", "1");
            pb.inheritIO();

            Process process = pb.start();

            int exitProcess=process.waitFor();
            System.out.println(exitProcess);

        } catch(Exception e){

        }



    }
}