import java.io.*;
import java.util.Scanner;


// Apunte: como esto era un reto he hecho el código y hay partes donde le he pedido ayuda a la IA para mejorla
// o ayudarme a encontrar errores (ha sido un caos crear esto)
public class EjecutaComando5 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean continuar = true;

        // ejemplos comandos windows
        // dir, ipconfig, systeminfo, tasklist, echo "Hola mundo", time /t
        System.out.println("===========================================");
        System.out.println("SISTEMA DE COMANDOS");
        System.out.println("===========================================");

        while (continuar) {
            System.out.print("Introduce un comando para ejecutar (o 'salir' para terminar): ");
            String comandoUsuario = scanner.nextLine();

            // Opción para salir del programa
            if (comandoUsuario.equalsIgnoreCase("salir")) {
                System.out.println("Saliendo del programa...");
                break;
            }

            // Validar que no esté vacío
            if (comandoUsuario.trim().isEmpty()) {
                System.out.println("No has introducido ningún comando.\n");
                continue;
            }

            try {
                // Detectar multiplataforma
                String os = System.getProperty("os.name").toLowerCase();
                boolean isWindows = os.contains("win");

                Process proceso;

                // Ejecutar el comando según el sistema operativo
                if (isWindows) {
                    proceso = new ProcessBuilder("CMD", "/C", comandoUsuario).start();
                } else {
                    proceso = new ProcessBuilder("sh", "-c", comandoUsuario).start();
                }

                System.out.println("\n--- SALIDA DEL COMANDO ---");

                // Leer la salida
                InputStream is = proceso.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                String linea;

                while ((linea = reader.readLine()) != null) {
                    System.out.println(linea);
                }
                reader.close();

                // Leer la salida de error
                InputStream isError = proceso.getErrorStream();
                BufferedReader readerError = new BufferedReader(new InputStreamReader(isError));

                System.out.println("\n--- ERRORES (si los hay) ---");
                boolean hayErrores = false;
                while ((linea = readerError.readLine()) != null) {
                    System.err.println(linea);
                    hayErrores = true;
                }
                readerError.close();

                if (!hayErrores) {
                    System.out.println("(No hay errores)");
                }

                // Esperar a que termine y obtener el código de salida
                int exitCode = proceso.waitFor();

                System.out.println("\n===========================================");
                System.out.println("Código de salida: " + exitCode);
                if (exitCode == 0) {
                    System.out.println("El comando se ejecutó correctamente");
                } else {
                    System.out.println("El comando falló");
                }
                System.out.println("===========================================\n");

            } catch (IOException e) {
                System.err.println("Error de entrada/salida al ejecutar el comando: " + e.getMessage());
                System.err.println("Verifica que el comando sea válido.\n");
            } catch (Exception e) {
                System.err.println("Error inesperado: " + e.getMessage());
                e.printStackTrace();
            }
        }

        scanner.close();
        System.out.println("Saliendo del programa...");
    }
}

/*
CONCLUSIONES:
Permitir ejecutar cualquier comando es EXTREMADAMENTE PELIGROSO
sobretodo en un programa con permisos de administrador
debido a que das el control total del sistema
y literalmente podría rmdir /s /q C:/Windows/System32
y borrar el sistema

También podría añadir un backdoor remoto

En aplicaciones reales NUNCA se debería permitir este tipo de prácticas
 */