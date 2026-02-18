import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

public class Principal {
    
    // Rutas para almacenar las claves
    private static final String ARCHIVO_CLAVE_PUBLICA = "clavePublica.key";
    private static final String ARCHIVO_CLAVE_PRIVADA = "clavePrivada.key";
    
    public static void main(String[] args) {
        try {
            // PASO 1: Generar un nuevo par de claves RSA
            System.out.println("PASO 1: Generando par de claves RSA...");
            KeyPair parClaves = GeneradorParClaves.generarParClaves();
            System.out.println("Par de claves generado exitosamente!");
            
            // Mostrar información del par de claves
            GeneradorParClaves.mostrarInfoParClaves(parClaves);
            
            // PASO 2: Guardar las claves en archivos
            System.out.println("PASO 2: Guardando claves en archivos...");
            GeneradorParClaves.guardarParClaves(parClaves, ARCHIVO_CLAVE_PUBLICA, ARCHIVO_CLAVE_PRIVADA);
            System.out.println("Claves guardadas exitosamente!\n");
            
            // PASO 3: Cargar las claves desde archivos
            System.out.println("PASO 3: Cargando claves desde archivos...");
            PublicKey clavePublicaCargada = GeneradorParClaves.cargarClavePublica(ARCHIVO_CLAVE_PUBLICA);
            PrivateKey clavePrivadaCargada = GeneradorParClaves.cargarClavePrivada(ARCHIVO_CLAVE_PRIVADA);
            System.out.println("Clave pública cargada desde: " + ARCHIVO_CLAVE_PUBLICA);
            System.out.println("Clave privada cargada desde: " + ARCHIVO_CLAVE_PRIVADA);
            System.out.println("¡Claves cargadas exitosamente!\n");
            
            // PASO 4: Demostrar cifrado y descifrado
            System.out.println("PASO 4: Demostrando cifrado y descifrado...");
            String mensajePrueba = "Este es un mensaje secreto de PSP UA5 Tarea 2.";
            UtilidadesCripto.demostrarCifrado(clavePublicaCargada, clavePrivadaCargada, mensajePrueba);
            
            // Demostración adicional con otro mensaje
            String mensajePrueba2 = "El cifrado RSA funciona con pares de claves pública/privada.";
            UtilidadesCripto.demostrarCifrado(clavePublicaCargada, clavePrivadaCargada, mensajePrueba2);
            
            System.out.println("\n=======================================================");
            System.out.println("  Tarea completada exitosamente!");
            System.out.println("  Archivos generados:");
            System.out.println("  - " + ARCHIVO_CLAVE_PUBLICA + " (clave pública)");
            System.out.println("  - " + ARCHIVO_CLAVE_PRIVADA + " (clave privada)");
            System.out.println("=======================================================");
            
        } catch (Exception e) {
            System.err.println("\nERROR: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
