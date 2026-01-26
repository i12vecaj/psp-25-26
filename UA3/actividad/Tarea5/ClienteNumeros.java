import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ClienteNumeros {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try (Socket socket = new Socket("localhost", 6069)) {

            System.out.print("Introduce un numero entero: ");
            int num = scanner.nextInt();

            if (num < 0) {
                System.out.println("El numero debe ser mayor o igual que 0");
                return;
            }

            ObjectOutputStream salida = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream entrada = new ObjectInputStream(socket.getInputStream());

            Numeros numerin = new Numeros(num);
            salida.writeObject(numerin);

            numerin = (Numeros) entrada.readObject();

            System.out.println("El cuadrado de " + num + " es: " + numerin.getCuadrado());
            System.out.println("El cubo de " + num + " es: " + numerin.getCubo());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
}
