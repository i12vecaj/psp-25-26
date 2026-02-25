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
1- La contraseña se guarda en texto plano y accedemos a la base de datos mediante la variable que la contiene. En mi opinión debería contener algún tipo de cifrado
que proteja la contraseña de nuestro usuario.

2- Yo implementaría un control de errores mediante un try & catch para dar acceso o mostrar excepción. (Me he dado cuenta que en la declaración del main incluye
throws exception, pero igualmente lo mejoraría).

3- Limpieza de buffers para un tema tan sensible como logins de usuarios me parece importante.

4- Considero también que es una mala práctica guardar en una base de datos una contraseña en texto plano sin cifrado ninguno, supongo que está asi porque se consulta
mediante la variable "pass" del programa y no lleva ningún cifrado.

5- Me parece también que no es el método mas correcto compartir entre sistemas, en este caso este sistema de login y una base de datos información sensible de los usuarios como en este caso una contraseña, o pueden llegar a ser incluso correos, información personal...

6- No se previene la Injección SQL
/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/

### Pregunta 1.2

Reescribe el fragmento vulnerable aplicando programación segura,
incluyendo:

-   Prevención de SQL Injection\
-   Buen manejo de credenciales\
-   Buen uso de recursos

``` java
import java.sql.*;
import java.util.Scanner;
import java.security.MessageDigest;
import java.util.Base64;

public class Login {

    // Método para hashear contraseña con SHA-256
    private static String hashPassword(String password) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(password.getBytes("UTF-8"));
        return Base64.getEncoder().encodeToString(hash);
    }

    public static void main(String[] args) {
        Scanner sc = null;
        
        try {
            sc = new Scanner(System.in);

            System.out.println("Usuario:");
            String user = sc.nextLine().trim();

            System.out.println("Password:");
            String pass = sc.nextLine();
            
            // Validar entrada no vacía
            if (user.isEmpty() || pass.isEmpty()) {
                System.out.println("Usuario o contraseña vacíos");
                return;
            }

            // Hash de la contraseña ingresada
            String passwordHash = hashPassword(pass);

            // Usar try para cierre automático
            try (Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost/app", "appuser", "apppass")) {

                // Usar PreparedStatement para evitar SQL Injection
                String query = "SELECT * FROM usuarios WHERE nombre = ? AND password = ?";
                try (PreparedStatement pst = con.prepareStatement(query)) {
                    pst.setString(1, user);
                    pst.setString(2, passwordHash);

                    try (ResultSet rs = pst.executeQuery()) {
                        if (rs.next()) {
                            System.out.println("Acceso concedido");
                        } else {
                            System.out.println("Acceso denegado");
                        }
                    }
                }
            }
            
            // Limpiar buffer sensible
            pass = null;
            
        } catch (SQLException e) {
            System.err.println("Error de base de datos: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (sc != null) {
                sc.close();
            }
        }
    }
}
```

/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/

## BLOQUE 2 -- Conceptos de programación segura

Responde a las siguientes preguntas:

### Pregunta 2.1

Explica qué es una vulnerabilidad de tipo SQL Injection y cómo se
previene en Java.
Una inyeccion sql es un tipo de ataque a una base de datos, mediante algun campo que nos pide por ejemplo un formulario de una página web, en el que el atacante introduce un código para obtener, manipular o eliminar información de dicho sistema, incluso se podrían hacer con todo el control de este.
Un método muy común es el siguiente: ' OR '1'='1   Mediante esta sentencia puedes omitir la autenticación en el campo del formulario al hacer que siempre sea verdadera.
Se suelen evitar utilizando prepared statements y validando que datos pueden entrar. A esto le sumamos una buena configuración a la base de datos para que si llegase a entrar, el daño fuese mínimo.
/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/

### Pregunta 2.2

¿Por qué es una mala práctica almacenar contraseñas en texto plano?\
¿Qué técnica se debe usar en su lugar?
En caso de querer transmitir esta información y que un atacante la interceptase, tendría total acceso a las credenciales de ese usuario. Por esto se utiliza el cifrado hash para que el emisor y receptor compartan clave privada y solo ellos puedan descifrarla.

/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/

### Pregunta 2.3

¿Qué es el principio de mínimo privilegio? Pon un ejemplo en Java o en
bases de datos.
Esto es algo que ya he comentado anteriormente en la pregunta de inyección sql. La base de datos tiene que estar configurada para que el usuario solo pueda hacer las tareas que necesita y ser muy estricto con ello. Para que se hace esto, si entra un atacante, que tenga los menores privilegios posibles. Además ya no es que pueda entrar una persona mediante cualquier técnica por el estilo, sino que un usuario de nuestro sistema no tenga la capacidad de destruirlo desde dentro.

/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/

### Pregunta 2.4

Indica si las siguientes afirmaciones son verdaderas o falsas y
justifica:

a)  PreparedStatement evita SQL Injection\
Verdadero, porque no envíamos directamente la contraseña con texto plano por ahi, sino una sentencia directa a la bbdd. Nos ayuda a la hora de separar las entradas del usuario como su usuario y contraseña a código SQL.
b)  RSA es criptografía simétrica\
Falso, el RSA es uno de los métodos más comunes de criptografía asimétrica. 
c)  La clave privada puede compartirse públicamente\ 
Falso, la clave privada única y exclusivamente pueden tenerla emisor y receptor.
d)  Un certificado digital contiene una clave pública
Verdadero, es un documento que certifica la clave pública de una entidad

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
Un certificado digital es un documento que certifica la clave pública de una entidad y puede estar destinado a un usuario, una máquina, proceso o dispositivo.
Se utiliza para identificarte de forma inequívoca y segura en internet. Te permite hacer trámites en administraciones públicas y entidades privadas sin desplazarte.


/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/

### Pregunta 3.2

¿Qué contiene un certificado digital?

Menciona al menos 3 elementos.

Número de serie: Identificador numérico único dentro del dominio de la CA.
Algoritmo de firma y parámetros.
Emisor del certificado.
Fechas de inicio y validez.
Nombre del propietario de la clave pública.
/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/

### Pregunta 3.3

Explica la diferencia entre:

-   KeyStore\
-   clave pública\
-   clave privada

KeyStore es un contenedor para guardar claves, certificados y secretos. Se utiliza para firmar archivos APK o AAB en el desarrollo de Android y garantizar la autenticidad de la app en la Google Play Store. La clave pública es un elemento que se utiliza en la comunicación asimétrica, en el que por ejemplo la clave pública para hablar contigo la tiene todo el mundo, pero para cada mensaje que te llega tienes una clave privada para desencriptar tu mensaje. La clave privada es algo que nos permite descifrar un mensaje, mediante comunicación asimétrica como he comentado aquí, o al contrario mediante comunicación simétrica donde ambos comparten la clave privada.
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

Utiliza dos números primos grandes para generar claves públicas y privadas. Cualquiera puede cifrar con la clave pública, pero solo el receptor con la clave privada puede descifrarlo. LLega a ser tan seguro porque al ser muy complejo factorizar dos números primos enormes, es inviable de hacer el cálculo para una computadora.
/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/

### Pregunta 4.2

Indica qué clave se utiliza para:

a)  Cifrar un mensaje confidencial\
Se suele utilizar clave pública, como en correos eléctronicos y protocolo https.
b)  Firmar digitalmente
Se utiliza la clave digital para así asegurarnos de que no nos puede suplantar la identidad ningún individuo

/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/

### Pregunta 4.3

Completa el siguiente código para cifrar un mensaje con RSA:

``` java
Cipher cipher = Cipher.getInstance("RSA");
//claveprivada sería una instancia de un objeto KeyPair al que le hemos generad una clave Pair con la instancia RSA. No sé si está bien explicado pero mirando el código de ejemplo de Ejemplo1CypherRSA.java del repo es lo que he podido entender.
c.init(Cipher.ENCRYPT_MODE, claveprivada);

byte[] cifrado = cipher.doFinal("Hola".getBytes());
```

/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/

### Pregunta 4.4

Indica si esta configuración es segura y justifica:

``` java
keyGen.initialize(512);
```
No es segura porque está obsoleta y es vulnerable a ataques de fuerza bruta, ya que se puede romper mediante factorización de números primos en cuestión de horas o días usando computadoras convencionales y herramientas públicas (como CADO-NFS).
/\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*/
