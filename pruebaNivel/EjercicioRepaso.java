    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Cual es tu nombre? ");
        String name = scanner.nextLine();
        System.out.print("Que edad tienes? ");
        int age = scanner.nextInt();
        date = LocalDate.now();
        int year = date.getYear() - edad;
        System.out.println("Hola " + name + ", cumpliras 100 años en el año " + (year + 100) + ".");
        scanner.close();
    }
