import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scname = new Scanner(System.in);
        Scanner scage = new Scanner(System.in);
        String name;
        int age;
        System.out.println("Dime tu nombre");
        name = scname.nextLine();
        System.out.println("Dime tu edad");
        age = scage.nextInt();

        int year;
        year = 2025+100-age;

        System.out.println("Te llamas " + name + " y cumpliras 100 años en " + year);
    }
}