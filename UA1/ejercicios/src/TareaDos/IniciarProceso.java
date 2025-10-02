package TareaDos;

public class IniciarProceso {
    public static void main(String[] args) {
        try{
            // Creamos un ProcessBuilder para ejecutar otra clase Java como proceso independiente
            ProcessBuilder pb = new ProcessBuilder("java","-cp","out/production/ejercicios","TareaDos.LecturaDeCadena");
            // "java"comando para ejecutar Java
            // "-cp"indica la carpeta donde están los .class compilados
            // "out/production/ejercicios"carpeta donde guarda los .class
            // "TareaDos.LecturaDeCadena" clase que queremos ejecutar

            //Esta funcion la utilizo para que proceso hijo utilice la misma consola que el padre
            //Redirije la entrada y salida del proceso hijo a la consola del proceso padre
            pb.inheritIO();

            Process p = pb.start();

            p.waitFor(); //Estoy haciendole esperar al proceso padre para que termine el hijo
        }catch (Exception e){
            //capturar error
            System.out.println("Error con el proceso: "+e.getMessage());
        }
    }
}
