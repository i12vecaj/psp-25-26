import java.net.*;
import java.util.Scanner;

public class sockettarea1 {
    // Definimos el puerto de salida como una constante para localhost (opcional)
    private static final String PALABRA_SALIDA = "localhost";

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        String datoIntroducido = "";

        System.out.println("--- Consultor de Red v1.0 ---");
        System.out.println("Para cerrar el programa escribe la palabra: " + PALABRA_SALIDA + "\n");

        // Usamos una estructura do-while para variar el flujo
        do {
            System.out.print("Introduce una dirección (URL/IP): ");
            datoIntroducido = teclado.nextLine().trim();

            // Validación de contenido
            if (datoIntroducido.length() == 0) {
                continue;
            }

            // Si el usuario quiere salir, cortamos antes de procesar
            if (datoIntroducido.equalsIgnoreCase(PALABRA_SALIDA)) {
                System.out.println("-> Saliendo del sistema de consulta...");
            } else {
                // Si no es la palabra de salida, procesamos la dirección
                obtenerDatosRed(datoIntroducido);
            }

        } while (!datoIntroducido.equalsIgnoreCase(PALABRA_SALIDA));

        teclado.close();
    }

    private static void obtenerDatosRed(String textoUrl) {
        try {
            // Primero limpiamos el protocolo y las rutas
            String hostLimpio = depurarDireccion(textoUrl);

            // Intentamos localizar el servidor en la red
            InetAddress ipRemota = InetAddress.getByName(hostLimpio);

            System.out.println("\n[DATOS OBTENIDOS]");
            System.out.println("Host: " + ipRemota.getHostName());
            System.out.println("Host Real: " + ipRemota.getCanonicalHostName());
            System.out.println("IP asignada: " + ipRemota.getHostAddress());

            // Comprobamos conectividad (espera de 5000ms)
            boolean noResponde = !ipRemota.isReachable(5000);
            System.out.println("¿Sin respuesta del servidor?: " + noResponde);
            System.out.println("----------------------------------\n");

        } catch (UnknownHostException ex) {
            System.err.println("Fallo DNS: No existe el host '" + textoUrl + "'");
        } catch (Exception ex) {
            System.err.println("Error general al conectar con '" + textoUrl + "'");
        }
    }

    private static String depurarDireccion(String origen) {
        String resultado = origen;

        // Limpieza de prefijos de protocolo
        if (resultado.toLowerCase().startsWith("http://")) {
            resultado = resultado.substring(7);
        } else if (resultado.toLowerCase().startsWith("https://")) {
            resultado = resultado.substring(8);
        }

        // Si hay una barra (ruta), nos quedamos solo con la base
        int posicionBarra = resultado.indexOf('/');
        if (posicionBarra >= 0) {
            resultado = resultado.substring(0, posicionBarra);
        }

        return resultado;
    }
}