
public class Principal {
    public static void main(String[] args) {
              if (args.length < 1) {
            System.exit(1);
        }

        try {
            
            int num = Integer.parseInt(args[0]);

            if (num < 0) {
                System.exit(3);
            } else {
                System.exit(0);
            }

        } catch (NumberFormatException e) {
            System.exit(2);
        }
    }
}
