package TareaTres;

public class MainProgram {
    public static void main(String[] args) {
        try {
            //Verifico si tiene menos de un argumento, si lo tiene entonces devuelve 1
            if(args.length<1){
                System.exit(1);
            }

            //Variable para guardar el primer argumento
            String arg = args[0];

            try {
                //Para convertir arg en numeros y poder analizar si es menos de 0
                int num = Integer.parseInt(arg);
                if (num < 0) {
                    System.exit(3); //Número menor que 0
                }
            } catch (NumberFormatException e) {
                System.exit(2); //No es número es cadena
            }

            //Como es otro caso pues devuelve 0
            System.exit(0);


        } catch (Exception e) {
            System.err.println("Error: "+e.getMessage());
        }

    }
}
