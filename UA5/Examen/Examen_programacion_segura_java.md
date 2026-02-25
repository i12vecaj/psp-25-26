# EXAMEN -- PROGRAMACIÓN SEGURA EN JAVA

## 2º DAM -- Parte Teórico-Práctica

------------------------------------------------------------------------

/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/

## BLOQUE 1 -- Análisis de código inseguro

Analiza el siguiente código Java:

/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/

``` java
import java.sql.*;
import java.util.Scanner;

public class Login {

    public static void main(String[] args) throws Exception {

        Scanner sc = new Scanner(System.in);

        System.out.println("Usuario:");
        String user = sc.nextLine();

        System.out.println("Password:");
        String pass = sc.nextLine();

        Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost/app", "root", "root");

        Statement st = con.createStatement();

        String query = "SELECT * FROM usuarios WHERE nombre='"
                + user + "' AND password='" + pass + "'";

        ResultSet rs = st.executeQuery(query);

        if(rs.next()) {
            System.out.println("Acceso concedido");
        } else {
            System.out.println("Acceso denegado");
        }

        con.close();
    }
}
```

/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/

### Pregunta 1.1

Identifica al menos 4 vulnerabilidades o malas prácticas de seguridad en
el código.

Explica brevemente cada una.
-SQL Injection: la consulta se construye concatenando directamente user y pass en la cadena SQL; un usuario malicioso puede inyectar SQL (p. ej. "' OR '1'='1").
-Credenciales codificadas/gestión de credenciales insegura: la conexión usa "root","root" en el código; las credenciales no deben estar en el código fuente.
-Almacenamiento/uso de contraseñas en texto plano: el código asume que la columna password guarda la contraseña en texto claro, lo que es inseguro si la BD es comprometida.
-Mal manejo de recursos: no se usan try-with-resources; si ocurre una excepción algunas conexiones/objetos pueden quedar abiertos (fugas).
-Uso de Statement en vez de PreparedStatement: además de facilitar inyección, pierde optimizaciones y manejo de parámetros.
-Exposición de mensajes y falta de registro seguro: mensajes genéricos está bien, pero en un entorno real conviene un manejo de errores y registros que no filtren información sensible. 


/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/

### Pregunta 1.2

Reescribe el fragmento vulnerable aplicando programación segura,
incluyendo:

-   Prevención de SQL Injection\
-   Buen manejo de credenciales\
-   Buen uso de recursos

// Supuestos:
// - Tabla "usuarios" tiene columnas: nombre (VARCHAR), password_hash (VARBINARY), salt (VARBINARY)
// - Las credenciales DB se toman de variables de entorno: DB_URL, DB_USER, DB_PASS
// - Usamos PBKDF2 para verificar la contraseña

import java.sql.*;
import java.util.Base64;
import java.util.Scanner;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;
import java.util.Arrays;

public class SecureLogin {

    private static final String DB_URL = System.getenv("DB_URL");       
    private static final String DB_USER = System.getenv("DB_USER");
    private static final String DB_PASS = System.getenv("DB_PASS");
    private static final int PBKDF2_ITER = 100_000;
    private static final int HASH_BYTES = 32; // 256-bit

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.print("Usuario: ");
        String user = sc.nextLine().trim();
        System.out.print("Password: ");
        String pass = sc.nextLine();

        // Validaciones básicas de entrada (longitud, caracteres) pueden añadirse aquí.

        String sql = "SELECT password_hash, salt FROM usuarios WHERE nombre = ?";

        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, user);

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    byte[] storedHash = rs.getBytes("password_hash");
                    byte[] salt = rs.getBytes("salt");

                    if (verifyPassword(pass.toCharArray(), salt, storedHash)) {
                        System.out.println("Acceso concedido");
                    } else {
                        System.out.println("Acceso denegado");
                    }
                } else {
                    // No revelar si es usuario inexistente vs contraseña errónea.
                    System.out.println("Acceso denegado");
                }
            }
        } catch (SQLException ex) {
            // Manejo de errores: no mostrar stack traces en producción. Loguear de forma segura.
            System.err.println("Error de acceso. Contacte con el administrador.");
        }
    }

    // PBKDF2-HMAC-SHA256
    private static boolean verifyPassword(char[] password, byte[] salt, byte[] expectedHash) throws Exception {
        PBEKeySpec spec = new PBEKeySpec(password, salt, PBKDF2_ITER, HASH_BYTES * 8);
        Arrays.fill(password, '\0'); // limpiar password en memoria cuando ya no se necesita
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        byte[] computed = skf.generateSecret(spec).getEncoded();
        boolean matches = constantTimeEquals(computed, expectedHash);
        Arrays.fill(computed, (byte)0);
        return matches;
    }

    // Comparación en tiempo constante para evitar ataques por timing
    private static boolean constantTimeEquals(byte[] a, byte[] b) {
        if (a == null || b == null) return false;
        if (a.length != b.length) return false;
        int diff = 0;
        for (int i = 0; i < a.length; i++) {
            diff |= a[i] ^ b[i];
        }
        return diff == 0;
    }
}


/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/

## BLOQUE 2 -- Conceptos de programación segura

Responde a las siguientes preguntas:

### Pregunta 2.1

Explica qué es una vulnerabilidad de tipo SQL Injection y cómo se
previene en Java.

-Definición: SQL Injection es una vulnerabilidad que ocurre cuando entradas no confiables del usuario se interpretan directamente como parte de una sentencia SQL, permitiendo a un atacante modificar la consulta (leer, modificar o borrar datos, o ejecutar comandos arbitrarios).
-Prevención en Java:
   -Usar PreparedStatement con parámetros (?) en lugar de concatenar cadenas.
   -Validar y normalizar la entrada (longitud, formato) cuando sea aplicable.
   -Aplicar el principio de menor privilegio a la cuenta de BD (usuario con permisos mínimos).
   -Escapar inputs solo cuando no haya otra opción (no recomendable como primera línea).
   -Usar ORM con APIs que parametrizan automáticamente (ej. JPA/Hibernate) y revisar las APIs que permiten queries dinámicos.
   -Logs y detección de anomalías (tasa de errores/consultas sospechosas).

/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/

### Pregunta 2.2

¿Por qué es una mala práctica almacenar contraseñas en texto plano?\
¿Qué técnica se debe usar en su lugar?

-Por qué es mala: si la base de datos se compromete (fuga, backup mal protegido, acceso interno malintencionado), las contraseñas quedan expuestas y los atacantes pueden reutilizarlas (credential stuffing). Además los administradores y backups tendrían acceso a contraseñas.
-Técnica recomendada: almacenar hashes resistentes a fuerza bruta con salt único por usuario. Usar algoritmos de derivación de clave diseñados para contraseñas: Argon2 (recomendado), BCrypt o PBKDF2 con parámetros de costo altos. Nunca usar funciones de hash rápido (MD5, SHA-1, SHA-256) sin un esquema de derivación fuerte y salt.

/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/

### Pregunta 2.3

¿Qué es el principio de mínimo privilegio? Pon un ejemplo en Java o en
bases de datos.

-Definición: cada componente (usuario, proceso, servicio) debe tener solo los permisos estrictamente necesarios para realizar su tarea, nada más.
-Ejemplo en BD: la aplicación web usa un usuario app_user en la BD con permisos SELECT, INSERT en las tablas que necesita, pero no DROP/ALTER ni acceso a otras bases. El usuario administrativo db_admin tiene permisos para mantenimiento y sólo se usa en tareas administrativas, no por la app en runtime.
-Ejemplo en Java: si un módulo sólo necesita leer ficheros de un directorio, configurarle permisos de filesystem limitados; si se usa SecurityManager (antiguo) o políticas, dar solo los permisos necesarios.

/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/

### Pregunta 2.4

Indica si las siguientes afirmaciones son verdaderas o falsas y
justifica:

a)  PreparedStatement evita SQL Injection\
    — Parcialmente verdadero: si se usa correctamente con parámetros enlazados (setString, setInt, etc.), sí evita inyección SQL porque los parámetros no se concatenan en la consulta. Falso si se usan concatenaciones para construir partes dinámicas de la consulta (nombres de columna o table names) sin validación.
b)  RSA es criptografía simétrica\
    -Falso: RSA es criptografía asimétrica (clave pública/privada).
c)  La clave privada puede compartirse públicamente\
    — Falso: la clave privada debe mantenerse secreta; si se comparte, se rompe la seguridad (pérdida de autenticidad/confidencialidad).
d)  Un certificado digital contiene una clave pública
    — Verdadero: un certificado X.509 contiene (entre otros datos) la clave pública del sujeto certificado y está firmado por una CA.



/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/

## BLOQUE 3 -- Certificados digitales

Observa el siguiente código:

/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/

``` java
KeyStore ks = KeyStore.getInstance("JKS");
FileInputStream fis = new FileInputStream("certificado.jks");
ks.load(fis, "123456".toCharArray());

Certificate cert = ks.getCertificate("mi_certificado");
PublicKey publicKey = cert.getPublicKey();

System.out.println(publicKey);
```

/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/

### Pregunta 3.1

Explica qué es un certificado digital y para qué se utiliza.

-Un certificado digital (por ejemplo X.509) es un documento electrónico emitido por una autoridad de certificación (CA) que vincula una identidad (persona, servidor, organización) con una clave pública. Incluye una firma digital de la CA que permite verificar que la clave pública pertenece a esa identidad.
-Usos: autenticar identidades (p. ej. HTTPS/TLS), cifrar comunicaciones, verificar firmas digitales, establecer confianza en intercambios seguros.

/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/

### Pregunta 3.2

¿Qué contiene un certificado digital?

Menciona al menos 3 elementos.

-Clave pública del sujeto.
-Identidad del sujeto (subject) - nombre, organización, CN, etc.
-Identidad del emisor (issuer) - la CA que lo firmó.
-Período de validez (fecha de inicio y expiración).
-Firma digital de la CA (que firma todos los campos).
-Número de serie y algoritmo de firma (p. ej. sha256WithRSAEncryption).

/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/

### Pregunta 3.3

Explica la diferencia entre:

-   KeyStore\
Es un almacén (fichero/objeto) que guarda claves privadas, entradas de certificados y certificados públicos; en Java, KeyStore es la API para cargar y acceder a estos objetos (ej. JKS, PKCS12). El KeyStore puede proteger sus entradas con contraseña.

-   clave pública\
Parte de un par de claves asimétricas; se puede distribuir públicamente y se usa para verificar firmas o cifrar datos (que solo la clave privada correspondiente podrá descifrar, según el esquema).


-   clave privada

La otra parte del par asimétrico, debe mantenerse secreta; se usa para crear firmas digitales o para descifrar datos cifrados con la clave pública.




/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/

## BLOQUE 4 -- Criptografía RSA en Java

Analiza el siguiente código:

``` java
KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
keyGen.initialize(1024);

KeyPair pair = keyGen.generateKeyPair();

PublicKey pub = pair.getPublic();
PrivateKey priv = pair.getPrivate();
```

### Pregunta 4.1

¿Qué tipo de criptografía utiliza RSA? Explica brevemente cómo funciona.

-RSA es criptografía asimétrica (o de clave pública). 
-Funciona con un par de claves: una clave pública para cifrar o verificar firmas, y una clave privada para descifrar o firmar. La seguridad se basa en la dificultad de factorizar números grandes (producto de dos primos grandes). En la práctica se usa para cifrar datos pequeños, intercambiar claves simétricas (hybrid encryption) o firmar.

/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/

### Pregunta 4.2

Indica qué clave se utiliza para:

a)  Cifrar un mensaje confidencial\
- Se cifra con la clave pública del receptor; el receptor descifra con su clave privada.

b)  Firmar digitalmente
-Se firma con la clave privada del emisor; cualquiera puede verificar la firma con la clave pública del emisor.




/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/

### Pregunta 4.3

Completa el siguiente código para cifrar un mensaje con RSA:

``` java
Cipher cipher = Cipher.getInstance("RSA");

cipher.init(Cipher.ENCRYPT_MODE, pub);

byte[] cifrado = cipher.doFinal("Hola".getBytes());
```




/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/

### Pregunta 4.4

Indica si esta configuración es segura y justifica:

``` java
keyGen.initialize(512);
```

-No, no es segura. 512 bits para RSA es inseguro y puede romperse con recursos modestos. 
-Recomendación actual: al menos 2048 bits como mínimo; para requisitos mayores usar 3072 o 4096 bits. Además, para la mayoría de usos se emplea RSA para cifrar pequeñas claves y se usa cifrado simétrico (AES) para datos, con RSA para proteger la clave simétrica.

/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/
