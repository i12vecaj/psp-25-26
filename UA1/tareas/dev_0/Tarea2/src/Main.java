import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        StringBuilder cadena=new StringBuilder();
        char c;
        System.out.println("Introduce caracteres: (termina con *)");

        while(true){
            try{
                String entrada = sc.nextLine();

                if(entrada==null){
                    System.out.println("Entrada vacia, intenta de nuevo.");
                    continue;
                }
                c=entrada.charAt(0);
                if(c=='*'){
                    break;
                }
                cadena.append(c);

            }catch (Exception e){
                System.out.println("Error al leer la entrada, intenta de nuevo.");
            }
        }
        System.out.println("La cadena leida es: "+cadena.toString());
        sc.close();
    }
}
