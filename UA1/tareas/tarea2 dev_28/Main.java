import java.util.Scanner;

public class Main{
    public static void main(String[]args){
        //FR1 y FR2
        Scanner sc = new Scanner(System.in);
        System.out.println("Introduzca una cadena, si desea finalizar escriba un *");
        String entrada = sc.nextLine();
        String cadena = "";
        
        while(!entrada.equalsIgnoreCase("*")){
            cadena += entrada + "\n";
            System.out.println("Introduzca una cadena, si desea finalizar escriba un *");
            entrada = sc.nextLine();
        }
        System.out.println(cadena);
    }
}