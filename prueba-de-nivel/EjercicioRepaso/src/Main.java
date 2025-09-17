import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String name;
        int age;
        int finalYear;
        int actualYear = 2025;
        int yearPlus;

        System.out.println("Introduce tu nombre");
        name = sc.next();
        System.out.println("Introduce tu edad");
        age = sc. nextInt();

        yearPlus = 100-age;
        finalYear = actualYear + yearPlus;

        System.out.println("Hola " + name + ", cumplirás 100 años en el año " + finalYear);
    }
}